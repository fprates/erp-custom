package org.erp.custom.sd.documents;

import org.iocaste.documents.common.DataType;
import org.iocaste.documents.common.ExtendedObject;
import org.iocaste.shell.common.InputComponent;
import org.iocaste.shell.common.Shell;
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
        "erp-custom-documents-create",
        "erp-custom-documents-display",
        "erp-custom-documents-update"
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
                i = Long.parseLong(((InputComponent)item_.get("ITEM_NUMBER")).
                        getValue());
            
                docid = Long.parseLong(((InputComponent)item_.get("DOCUMENT_ID")).
                        getValue()); 
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
                tfield.setValue(Long.toString(i));
                tfield.setEnabled(false);
                continue;
            }
            
            if (name.equals("DOCUMENT_ID")) {
                tfield.setReferenceValidable(false);
                tfield.setValue(Long.toString(docid));
                continue;
            }
            
            if (name.equals("MATERIAL") && view != null)
                view.setFocus(tfield);
            
            tfield.setObligatory((mode == SHOW)? false: true);
            tfield.setEnabled((mode == SHOW)? false : true);
            
            if (object != null) {
                if (Shell.getDataElement(tfield).getType() == DataType.NUMC)
                    tfield.setValue(Long.toString((Long)object.getValue(name)));
                else
                    tfield.setValue((String)object.getValue(name));
            }
        }
    }
}
