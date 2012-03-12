package org.erp.custom.sd.documents;

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

    public static final void add(ViewData view) {
        Table itens = view.getElement("itens");
        
        Common.insertItem(itens, view, null);
    }
    
    public static final void create(ViewData view) {
        view.setReloadableView(true);
        view.export("mode", Common.CREATE);
        view.redirect(null, "document");
    }
    
    public static final void display(ViewData view, Function function)
            throws Exception {
        load(view, function, Common.SHOW);
    }
    
    private static final void load(ViewData view, Function function, byte mode)
            throws Exception {
        ExtendedObject header;
        ExtendedObject[] itens;
        long ident;
        Documents documents = new Documents(function);
        DataForm form = view.getElement("selection");
        String query, sident = form.get("document").getValue();
        
        if (sident == null || sident.equals("")) {
            view.message(Const.ERROR, "document.number.required");
            return;
        }
        
        ident = Long.parseLong(sident);
        header = documents.getObject("CUSTOM_DOCUMENT", ident);
        
        if (header == null) {
            view.message(Const.ERROR, "invalid.sd.document");
            return;
        }
        
        query = "from custom_document_item where document_id = ?";
        itens = documents.select(query, ident);
        
        view.setReloadableView(true);
        view.export("mode", mode);
        view.export("header", header);
        view.export("itens", itens);
        view.redirect(null, "document");
    }
    
    public static final void remove(ViewData view) {
        Table itens = view.getElement("itens");
        
        for (TableItem item : itens.getItens())
            if (item.isSelected())
                itens.remove(item);
    }
    
    public static final void save(ViewData view, Function function)
            throws Exception {
        String query;
        long docid, itemnr;
        DataForm header = view.getElement("header");
        Table itens = view.getElement("itens");
        Documents documents = new Documents(function);
        ExtendedObject oitem, oheader = header.getObject();
        byte mode = Common.getMode(view);
        
        if (mode == Common.CREATE) {
            docid = documents.getNextNumber("SD_DOCUMENT");
            oheader.setValue("ID", docid);
            header.get("ID").setValue(Long.toString(docid));
            
            documents.save(oheader);
        } else {
            docid = (Long)oheader.getValue("ID");
            
            documents.modify(oheader);
            
            query = "delete custom_document_item where document_id = ?";
            documents.update(query, docid);
        }
        
        for (TableItem item : itens.getItens()) {
            oitem = item.getObject();
            
            if (mode == Common.CREATE) {
                itemnr = ((Long)oitem.getValue("ITEM_NUMBER")) +
                        (docid * 100000);
                
                oitem.setValue("ITEM_NUMBER", itemnr);
            
                ((InputComponent)item.get("ITEM_NUMBER")).
                        setValue(Long.toString(itemnr));
            }
            
            oitem.setValue("DOCUMENT_ID", docid);
            
            documents.save(oitem);
        }
        
        documents.commit();
        
        if (mode == Common.CREATE) {
            view.setTitle(Common.TITLE[Common.UPDATE]);
            view.export("mode", Common.UPDATE);
        }
        
        view.message(Const.STATUS, "document.saved.successfully");
    }
    
    public static final void update(ViewData view, Function function)
            throws Exception {
        load(view, function, Common.UPDATE);
    }
}
