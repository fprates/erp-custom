package org.erp.custom.sd.partner;

import org.iocaste.documents.common.ExtendedObject;
import org.iocaste.shell.common.Container;
import org.iocaste.shell.common.InputComponent;
import org.iocaste.shell.common.Link;
import org.iocaste.shell.common.Parameter;
import org.iocaste.shell.common.Table;
import org.iocaste.shell.common.TableColumn;
import org.iocaste.shell.common.TableItem;
import org.iocaste.shell.common.TextField;
import org.iocaste.shell.common.ViewData;

public class Common {
    public final static byte SHOW = 0;
    public final static byte CREATE = 1;
    public final static byte UPDATE = 2;
    public final static byte IDENTITY = 0;
    public final static byte ADDRESS = 1;
    public final static byte CONTACT = 2;
    
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
        return view.getParameter("mode");
    }
    
    /**
     * 
     * @param itens
     * @param object
     */
    public static final void insertItem(byte mode, Table itens,
            ExtendedObject object, Container container) {
        Link link;
        InputComponent input;
        Parameter index = new Parameter(container, "index");
        TableItem item = new TableItem(itens);
        String name, tablename = itens.getName();
        
        for (TableColumn column : itens.getColumns()) {
            if (column.isMark())
                continue;
            
            name = column.getName();
            if (tablename.equals("addresses") && name.equals("LOGRADOURO")) {
                link = new Link(itens, name, "addressmark");
                link.setText((String)object.getValue(name));
                link.add(index, itens.length() - 1);
                
                item.add(link);
                
                continue;
            }
            
            input = new TextField(itens, name);
            input.setEnabled((mode == Common.SHOW)? false : true);
            
            item.add(input);
            
            if (tablename.equals("addresses"))
                if (name.equals("TIPO_ENDERECO") || name.equals("CODIGO")) {
                    input.setObligatory(false);
                    input.setEnabled(false);
                    
                    continue;
                }
            
            if (tablename.equals("contacts"))
                if (name.equals("COMMUNICATION"))
                    input.setObligatory((mode == Common.SHOW)? false : true);
        }
        
        if (object != null)
            item.setObject(object);
    }

}
