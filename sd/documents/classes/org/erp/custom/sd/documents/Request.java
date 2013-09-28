package org.erp.custom.sd.documents;

import java.util.ArrayList;
import java.util.List;

import org.iocaste.documents.common.Documents;
import org.iocaste.documents.common.ExtendedObject;
import org.iocaste.shell.common.Const;
import org.iocaste.shell.common.DataForm;
import org.iocaste.shell.common.InputComponent;
import org.iocaste.shell.common.PageContext;
import org.iocaste.shell.common.Shell;
import org.iocaste.shell.common.Table;
import org.iocaste.shell.common.TableItem;
import org.iocaste.shell.common.View;

public class Request {
    private static final byte DEL_ITENS = 0;
    private static final byte ITENS = 1;
    private static final byte DEL_CONDITIONS = 2;
    private static final byte CONDITIONS = 3;
    private static final String[] QUERIES = {
        "delete from custom_sd_document_item where document_id = ?",
        "from custom_sd_document_item where document_id = ?",
        "delete from custom_sd_conditions where document = ?",
        "from custom_sd_conditions where document = ?"
    };
    
    /**
     * 
     * @param view
     */
    public static final void add(View view) {
        Table itens = view.getElement("itens");
        Common.insertItem(itens, view, null);
    }
    
    public static final void condapply(PageContext context) {
        ExtendedObject[] conditions_;
        Table conditions = context.view.getElement("conditions");
        List<ExtendedObject> oconditions = new ArrayList<ExtendedObject>();
        Shell shell = new Shell(context.function);
        View document = shell.getView(context.view, "document");
        
        for (TableItem item : conditions.getItems())
            oconditions.add(item.getObject());
        
        conditions_ = oconditions.toArray(new ExtendedObject[0]);
        context.view.export("conditions", conditions_);
        
        totalAmountUpdate(document, conditions_);
        shell.updateView(document);
    }
    
    public static final void condadd(View view) {
        Table conditions = view.getElement("conditions");
        
        view.getElement("validatecond").setVisible(true);
        view.getElement("condremove").setVisible(true);
        view.getElement("condapply").setVisible(true);
        conditions.setVisible(true);
        
        Common.insertCondition(conditions, null, view);
    }
    
    public static final void conditions(View view) {
        view.setReloadableView(true);
        view.redirect(null, "condform");
    }
    
    public static final void condremove(View view) {
        Table conditions = view.getElement("conditions");
        
        for (TableItem item :  conditions.getItems())
            if (item.isSelected())
                conditions.remove(item);
        
        if (conditions.length() > 0)
            return;
        
        view.getElement("validatecond").setVisible(false);
        view.getElement("condremove").setVisible(false);
        conditions.setVisible(false);
    }
    
    /**
     * 
     * @param view
     */
    public static final void create(View view) {
        view.clearExports();
        view.setReloadableView(true);
        view.export("mode", Common.CREATE);
        view.redirect("document");
    }
    
    /**
     * 
     * @param view
     * @param function
     */
    public static final void display(PageContext context) {
        load(context, Common.SHOW);
    }
    
    /**
     * 
     * @param view
     * @param function
     * @param mode
     */
    private static final void load(PageContext context, byte mode) {
        ExtendedObject header;
        ExtendedObject[] objects;
        Documents documents = new Documents(context.function);
        DataForm form = context.view.getElement("selection");
        long ident = form.get("ID").get();
        
        if (ident == 0) {
            context.view.message(Const.ERROR, "document.number.required");
            return;
        }
        
        header = documents.getObject("CUSTOM_SD_DOCUMENT", ident);
        if (header == null) {
            context.view.message(Const.ERROR, "invalid.sd.document");
            return;
        }
        
        context.view.clearExports();
        objects = documents.select(QUERIES[ITENS], ident);
        context.view.export("itens", objects);
        
        objects = documents.select(QUERIES[CONDITIONS], ident);
        context.view.export("conditions", objects);

        objects = new ExtendedObject[1];
        objects[0] = documents.getObject("CUSTOM_PARTNER",
                header.getValue("RECEIVER"));
        context.view.export("partner", objects[0]);
        
        objects[0] = documents.getObject("CUSTOM_SD_DOCTYPE",
                header.getValue("TIPO"));
        context.view.export("doctype", objects[0]);
        
        context.view.setReloadableView(true);
        context.view.export("mode", mode);
        context.view.export("header", header);
        context.view.redirect(null, "document");
    }
    
    /**
     * 
     * @param view
     */
    public static final void remove(View view) {
        Table itens = view.getElement("itens");
        
        for (TableItem item : itens.getItems())
            if (item.isSelected())
                itens.remove(item);
    }
    
    /**
     * 
     * @param view
     * @param function
     */
    public static final void save(PageContext context) {
        long docid, itemnr;
        Table itens;
        ExtendedObject[] conditions;
        DataForm header = context.view.getElement("header");
        Documents documents = new Documents(context.function);
        ExtendedObject oitem, oheader = header.getObject();
        byte mode = Common.getMode(context.view);
        
        switch (mode) {
        case Common.CREATE:
            docid = documents.getNextNumber("SD_DOCUMENT");
            oheader.setValue("ID", docid);
            header.get("ID").set(docid);
            context.view.setTitle(Common.TITLE[Common.UPDATE]);
            context.view.export("mode", Common.UPDATE);
            
            if (documents.save(oheader) != 0)
                break;
            
            context.view.message(Const.ERROR, "invalid.document.header");
            return;
            
        default:
            documents.modify(oheader);

            docid = oheader.getValue("ID");
            documents.update(QUERIES[DEL_CONDITIONS], docid);
            documents.update(QUERIES[DEL_ITENS], docid);
            
            break;
        }

        itens = context.view.getElement("itens");
        for (TableItem item : itens.getItems()) {
            oitem = item.getObject();
            
            itemnr = oitem.getValue("ITEM_NUMBER");
            if (itemnr < (docid * 100000)) {
                itemnr += (docid * 100000);
                oitem.setValue("ITEM_NUMBER", itemnr);
                ((InputComponent)item.get("ITEM_NUMBER")).set(itemnr);
            }
            
            oitem.setValue("DOCUMENT_ID", docid);
            documents.save(oitem);
        }
        
        conditions = context.view.getParameter("conditions");
        if (conditions != null)
            for (ExtendedObject condition : conditions) {
                itemnr = condition.getValue("ID");
                if (itemnr < (docid * 1000)) {
                    itemnr += (docid * 1000);
                    condition.setValue("ID", itemnr);
                }
                
                condition.setValue("DOCUMENT", docid);
                documents.save(condition);
            }
        
        context.view.message(Const.STATUS, "document.saved.successfully");
    }
    
    /**
     * 
     * @param view
     * @param objects
     */
    public static final void totalAmountUpdate(View view, 
            ExtendedObject[] objects) {
        InputComponent input;
        double valor = 0;
        Table itens = view.getElement("itens");
        DataForm header = view.getElement("header");
        
        for (TableItem item : itens.getItems()) {
            input = item.get("PRECO_TOTAL");
            valor += (Double)input.get();
        }

        if (objects != null)
            for (ExtendedObject object : objects)
                valor += (Double)object.getValue("VALOR");
        
        input = header.get("VALOR");
        input.set(valor);
    }
    
    public static final void update(PageContext context) {
        load(context, Common.UPDATE);
    }
}
