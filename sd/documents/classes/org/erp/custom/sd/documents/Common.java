package org.erp.custom.sd.documents;

import org.iocaste.documents.common.ExtendedObject;
import org.iocaste.shell.common.InputComponent;
import org.iocaste.shell.common.Table;
import org.iocaste.shell.common.TableColumn;
import org.iocaste.shell.common.TableItem;
import org.iocaste.shell.common.TextField;
import org.iocaste.shell.common.ViewData;

public class Common {
    public static final byte CREATE = 0;
    public static final byte SHOW = 1;
    public static final byte UPDATE = 2;
    
    public static final String[] TITLE = {
        "document-create",
        "document-display",
        "document-update"
    };
    
    public static final byte getMode(ViewData view) {
        return (Byte)view.getParameter("mode");
    }
    
    public static final void insertItem(byte mode, Table itens, ViewData view,
            ExtendedObject object) {
        String name;
        TextField tfield;
        TableItem item;
        long docid = 0, i = 0;
        
        if (object == null) {
            for (TableItem item_ : itens.getItens()) {
                i = ((InputComponent)item_.get("ITEM_NUMBER")).get();
            
                docid = ((InputComponent)item_.get("DOCUMENT_ID")).get(); 
            }
            
            i++;
        } else {
            i = (Long)object.getValue("ITEM_NUMBER");
            docid = (Long)object.getValue("DOCUMENT_ID");
        }
        
        item = new TableItem(itens);
        
        for (TableColumn column : itens.getColumns()) {
            if (column.isMark())
                continue;
            
            name = column.getName();
            
            tfield = new TextField(itens, name);
            tfield.setModelItem(column.getModelItem());
            item.add(tfield);
            
            if (name.equals("ITEM_NUMBER")) {
                tfield.set(i);
                tfield.setEnabled(false);
                continue;
            }
            
            if (name.equals("DOCUMENT_ID")) {
                tfield.getModelItem().setReference(null);
                tfield.set(docid);
                continue;
            }
            
            if (name.equals("MATERIAL") && view != null)
                view.setFocus(tfield);
            
            tfield.setObligatory((mode == SHOW)? false: true);
            tfield.setEnabled((mode == SHOW)? false : true);
            
            if (object != null)
                tfield.set(object.getValue(name));
        }
    }
}
