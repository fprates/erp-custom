package org.erp.custom.sd.documents;

import java.util.ArrayList;
import java.util.List;

import org.iocaste.documents.common.Documents;
import org.iocaste.documents.common.ExtendedObject;
import org.iocaste.documents.common.Query;
import org.iocaste.shell.common.Const;
import org.iocaste.shell.common.DataForm;
import org.iocaste.shell.common.InputComponent;
import org.iocaste.shell.common.PageContext;
import org.iocaste.shell.common.Shell;
import org.iocaste.shell.common.Table;
import org.iocaste.shell.common.TableItem;
import org.iocaste.shell.common.View;

public class Request {
    
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
        List<ExtendedObject> oconditions = new ArrayList<>();
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
        Query query;
        ExtendedObject header;
        ExtendedObject[] objects;
        Documents documents = new Documents(context.function);
        DataForm form = context.view.getElement("selection");
        long ident = form.get("ID").getl();
        
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
        query = new Query();
        query.setModel("custom_sd_document_item");
        query.andEqual("document_id", ident);
        objects = documents.select(query);
        context.view.export("itens", objects);
        
        query = new Query();
        query.setModel("custom_sd_conditions");
        query.andEqual("document", ident);
        objects = documents.select(query);
        context.view.export("conditions", objects);

        objects = new ExtendedObject[1];
        objects[0] = documents.getObject("CUSTOM_PARTNER",
                header.get("RECEIVER"));
        context.view.export("partner", objects[0]);
        
        objects[0] = documents.getObject("CUSTOM_SD_DOCTYPE",
                header.get("TIPO"));
        context.view.export("doctype", objects[0]);
        context.view.setReloadableView(true);
        context.view.export("mode", mode);
        context.view.export("header", header);
        context.view.redirect("document");
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
        Query[] queries;
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
            oheader.set("ID", docid);
            header.get("ID").set(docid);
            context.view.setTitle(Common.TITLE[Common.UPDATE]);
            context.view.export("mode", Common.UPDATE);
            
            if (documents.save(oheader) != 0)
                break;
            
            context.view.message(Const.ERROR, "invalid.document.header");
            return;
            
        default:
            documents.modify(oheader);
            docid = oheader.getl("ID");
            
            queries = new Query[2];
            queries[0] = new Query("delete");
            queries[0].setModel("custom_sd_conditions");
            queries[0].andEqual("document", docid);
            queries[1] = new Query("delete");
            queries[1].setModel("custom_sd_document_item");
            queries[1].andEqual("document_id", docid);
            documents.update(queries);
            break;
        }

        itens = context.view.getElement("itens");
        for (TableItem item : itens.getItems()) {
            oitem = item.getObject();
            
            itemnr = oitem.getl("ITEM_NUMBER");
            if (itemnr < (docid * 100000)) {
                itemnr += (docid * 100000);
                oitem.set("ITEM_NUMBER", itemnr);
                ((InputComponent)item.get("ITEM_NUMBER")).set(itemnr);
            }
            
            oitem.set("DOCUMENT_ID", docid);
            documents.save(oitem);
        }
        
        conditions = context.view.getParameter("conditions");
        if (conditions != null)
            for (ExtendedObject condition : conditions) {
                itemnr = condition.getl("ID");
                if (itemnr < (docid * 1000)) {
                    itemnr += (docid * 1000);
                    condition.set("ID", itemnr);
                }
                
                condition.set("DOCUMENT", docid);
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
            valor += input.getd();
        }

        if (objects != null)
            for (ExtendedObject object : objects)
                valor += object.getd("VALOR");
        
        input = header.get("VALOR");
        input.set(valor);
    }
    
    public static final void update(PageContext context) {
        load(context, Common.UPDATE);
    }
}
