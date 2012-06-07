package org.erp.custom.sd.documents;

import java.util.ArrayList;
import java.util.List;

import org.iocaste.documents.common.Documents;
import org.iocaste.documents.common.ExtendedObject;
import org.iocaste.protocol.Function;
import org.iocaste.shell.common.Const;
import org.iocaste.shell.common.DataForm;
import org.iocaste.shell.common.InputComponent;
import org.iocaste.shell.common.Table;
import org.iocaste.shell.common.TableItem;
import org.iocaste.shell.common.ViewData;

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
    public static final void add(ViewData view) {
        Table itens = view.getElement("itens");
        
        Common.insertItem(Common.getMode(view), itens, view, null);
    }
    
    public static final void condapply(ViewData view) {
        Table conditions = view.getElement("conditions");
        List<ExtendedObject> oconditions = new ArrayList<ExtendedObject>();
        
        for (TableItem item : conditions.getItens())
            oconditions.add(item.getObject());
        
        view.export("conditions", oconditions.toArray(new ExtendedObject[0]));
    }
    
    public static final void condadd(ViewData view) {
        Table conditions = view.getElement("conditions");
        byte mode = Common.getMode(view);
        
        view.getElement("condremove").setVisible(true);
        view.getElement("condapply").setVisible(true);
        conditions.setVisible(true);
        
        Common.insertCondition(conditions, null, mode);
    }
    
    public static final void conditions(ViewData view) {
        view.setReloadableView(true);
        view.redirect(null, "condform");
    }
    
    public static final void condremove(ViewData view) {
        Table conditions = view.getElement("conditions");
        
        for (TableItem item :  conditions.getItens())
            if (item.isSelected())
                conditions.remove(item);
        
        if (conditions.length() > 0)
            return;
        
        view.getElement("condremove").setVisible(false);
        conditions.setVisible(false);
    }
    
    /**
     * 
     * @param view
     */
    public static final void create(ViewData view) {
        view.clearParameters();
        view.setReloadableView(true);
        view.export("mode", Common.CREATE);
        view.redirect(null, "document");
    }
    
    /**
     * 
     * @param view
     * @param function
     * @throws Exception
     */
    public static final void display(ViewData view, Function function)
            throws Exception {
        load(view, function, Common.SHOW);
    }
    
    /**
     * 
     * @param view
     * @param function
     * @param mode
     * @throws Exception
     */
    private static final void load(ViewData view, Function function, byte mode)
            throws Exception {
        ExtendedObject header;
        ExtendedObject[] objects;
        Documents documents = new Documents(function);
        DataForm form = view.getElement("selection");
        long ident = form.get("ID").get();
        
        if (ident == 0) {
            view.message(Const.ERROR, "document.number.required");
            return;
        }
        
        header = documents.getObject("CUSTOM_SD_DOCUMENT", ident);
        if (header == null) {
            view.message(Const.ERROR, "invalid.sd.document");
            return;
        }
        
        view.clearParameters();
        
        objects = documents.select(QUERIES[ITENS], ident);
        view.export("itens", objects);
        
        objects = documents.select(QUERIES[CONDITIONS], ident);
        view.export("conditions", objects);
        
        view.setReloadableView(true);
        view.export("mode", mode);
        view.export("header", header);
        view.redirect(null, "document");
    }
    
    /**
     * 
     * @param view
     */
    public static final void remove(ViewData view) {
        Table itens = view.getElement("itens");
        
        for (TableItem item : itens.getItens())
            if (item.isSelected())
                itens.remove(item);
    }
    
    /**
     * 
     * @param view
     * @param function
     * @throws Exception
     */
    public static final void save(ViewData view, Function function)
            throws Exception {
        long docid, itemnr;
        Table itens;
        ExtendedObject[] conditions;
        DataForm header = view.getElement("header");
        Documents documents = new Documents(function);
        ExtendedObject oitem, oheader = header.getObject();
        byte mode = Common.getMode(view);
        
        switch (mode) {
        case Common.CREATE:
            docid = documents.getNextNumber("SD_DOCUMENT");
            oheader.setValue("ID", docid);
            header.get("ID").set(docid);
            view.setTitle(Common.TITLE[Common.UPDATE]);
            view.export("mode", Common.UPDATE);
            
            if (documents.save(oheader) != 0)
                break;
            
            view.message(Const.ERROR, "invalid.document.header");
            return;
            
        default:
            documents.modify(oheader);

            docid = oheader.getValue("ID");
            documents.update(QUERIES[DEL_CONDITIONS], docid);
            documents.update(QUERIES[DEL_ITENS], docid);
            
            break;
        }

        itens = view.getElement("itens");
        for (TableItem item : itens.getItens()) {
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
        
        conditions = view.getParameter("conditions");
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
        
        view.message(Const.STATUS, "document.saved.successfully");
    }
    
    /**
     * 
     * @param view
     * @param function
     * @throws Exception
     */
    public static final void update(ViewData view, Function function)
            throws Exception {
        load(view, function, Common.UPDATE);
    }
}
