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
import org.iocaste.shell.common.TableColumn;
import org.iocaste.shell.common.TableItem;
import org.iocaste.shell.common.TextField;
import org.iocaste.shell.common.ViewData;

public class Request {
    private static final byte DEL_ITENS = 0;
    private static final byte ITENS = 1;
    private static final String[] QUERIES = {
        "delete custom_document_item where document_id = ?",
        "from custom_sd_document_item where document_id = ?"
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
        
        view.export("conditions", oconditions);
    }
    
    public static final void condadd(ViewData view) {
        TextField tfield;
        Table conditions = view.getElement("conditions");
        TableItem item = new TableItem(conditions);
        
        for (TableColumn column : conditions.getColumns()) {
            if (column.isMark())
                continue;
            
            tfield = new TextField(conditions, column.getName());
            tfield.setModelItem(column.getModelItem());
            
            item.add(tfield);
        }
    }
    
    public static final void conditions(ViewData view) {
        List<ExtendedObject> oldcond = new ArrayList<ExtendedObject>();
        
        view.setReloadableView(true);
        view.export("oldcond", oldcond);
        view.redirect(null, "condform");
    }
    
    public static final void condremove(ViewData view) {
        Table conditions = view.getElement("conditions");
        
        for (TableItem item :  conditions.getItens())
            if (item.isSelected())
                conditions.remove(item);
    }
    
    /**
     * 
     * @param view
     */
    public static final void create(ViewData view) {
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
        ExtendedObject[] itens;
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
        
        itens = documents.select(QUERIES[ITENS], ident);
        
        view.setReloadableView(true);
        view.export("mode", mode);
        view.export("header", header);
        view.export("itens", itens);
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
        DataForm header = view.getElement("header");
        Table itens = view.getElement("itens");
        Documents documents = new Documents(function);
        ExtendedObject oitem, oheader = header.getObject();
        byte mode = Common.getMode(view);
        
        if (mode == Common.CREATE) {
            docid = documents.getNextNumber("SD_DOCUMENT");
            oheader.setValue("ID", docid);
            header.get("ID").set(docid);
            view.setTitle(Common.TITLE[Common.UPDATE]);
            view.export("mode", Common.UPDATE);
            
            if (documents.save(oheader) == 0) {
                view.message(Const.ERROR, "invalid.document.header");
                return;
            }
            
        } else {
            documents.modify(oheader);

            docid = oheader.getValue("ID");
            documents.update(QUERIES[DEL_ITENS], docid);
        }
        
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
