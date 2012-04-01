package org.erp.custom.sd.partner;

import org.iocaste.documents.common.ExtendedObject;
import org.iocaste.shell.common.InputComponent;
import org.iocaste.shell.common.Table;
import org.iocaste.shell.common.TableColumn;
import org.iocaste.shell.common.TableItem;
import org.iocaste.shell.common.TextField;
import org.iocaste.shell.common.ViewData;

public class Common {
    public final static byte SHOW = 0;
    public final static byte CREATE = 1;
    public final static byte UPDATE = 2;
    
    public final static String[] TITLE = {
            "partner-display",
            "partner-create",
            "partner-update"
    };
    
    /**
     * 
     * @param view
     * @return
     */
    public static final byte getMode(ViewData view) {
        return (Byte)view.getParameter("mode");
    }
    
    /**
     * 
     * @param itens
     * @param object
     */
    public static final void insertItem(byte mode, Table itens,
            ExtendedObject object) {
        InputComponent input;
        TableItem item = new TableItem(itens);
        
        for (TableColumn column : itens.getColumns())
            if (!column.isMark()) {
                input = new TextField(itens, column.getName());
                input.setEnabled((mode == Common.SHOW)? false : true);
                
                item.add(input);
            }
        
        if (object != null)
            item.setObject(object);
    }

}
