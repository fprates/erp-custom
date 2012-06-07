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
    public static final byte CONDITIONS = 3;
    
    public static final String[] TITLE = {
        "document-create",
        "document-display",
        "document-update",
        "document-conditions"
    };
    
    public static final byte getMode(ViewData view) {
        return view.getParameter("mode");
    }
    
    public static final void insertCondition(Table itens,
            ExtendedObject object, ViewData view) {
        String name;
        TextField tfield;
        long l = 0;
        int i = itens.length();
        ExtendedObject last = (i == 0)? null : itens.get(i - 1).getObject();
        TableItem item = new TableItem(itens);
        byte mode = getMode(view);
        
        for (TableColumn column : itens.getColumns()) {
            if (column.isMark())
                continue;
            
            name = column.getName();
            tfield = new TextField(itens, name);
            tfield.setModelItem(column.getModelItem());
            tfield.setEnabled(name.equals("ID")? false : mode != Common.SHOW);
            
            if (name.equals("CONDICAO"))
                view.setFocus(tfield);
            
            item.add(tfield);
        }
        
        if (object == null) {
            object = item.getObject();
            
            if (last != null)
                l = last.getValue("ID");
            
            object.setValue("ID", l + 1);
        }
        
        item.setObject(object);
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
            i = object.getValue("ITEM_NUMBER");
            docid = object.getValue("DOCUMENT_ID");
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
            
            if (name.equals("MATERIAL")) {
                view.setFocus(tfield);
                tfield.setObligatory((mode == SHOW)? false: true);
            }
            
            tfield.setEnabled((mode == SHOW)? false : true);
            
            if (object != null)
                tfield.set(object.getValue(name));
        }
    }
}
