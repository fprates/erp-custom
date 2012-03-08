package org.erp.custom.sd.documents;

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
        "erp-custom-documents-create",
        "erp-custom-documents-display",
        "erp-custom-documents-update"
    };
    
    public static final byte getMode(ViewData view) {
        return (Byte)view.getParameter("mode");
    }
    
    public static final void insertItem(Table itens) {
        String name;
        TextField tfield;
        TableItem item = new TableItem(itens);
        int i = 0;
        
        for (TableItem item_ : itens.getItens())
            i = Integer.parseInt(
                    ((InputComponent)item_.get("ITEM_NUMBER")).getValue());
        
        item = new TableItem(itens);
        i++;
        
        for (TableColumn column : itens.getColumns()) {
            if (column.isMark())
                continue;
            
            name = column.getName();
            
            tfield = new TextField(itens, name);
            tfield.setModelItem(column.getModelItem());
            item.add(tfield);
            
            if (column.getName().equals("ITEM_NUMBER"))
                tfield.setValue(Integer.toString(i));
        }
    }
}
