package org.erp.custom.sd.partner;

import org.iocaste.shell.common.InputComponent;
import org.iocaste.shell.common.Link;
import org.iocaste.shell.common.Parameter;
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
     * @param value
     * @return
     */
    public static final long getLong(Object value) {
        return (value == null)? 0l : (Long)value;
    }
    
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
    public static final void insertItem(byte mode, ItemData itemdata) {
        int i;
        long codigo;
        Link link;
        InputComponent input;
        Parameter index = new Parameter(itemdata.container, "index");
        TableItem item = new TableItem(itemdata.itens);
        String name, tablename = itemdata.itens.getName();
        
        for (TableColumn column : itemdata.itens.getColumns()) {
            if (column.isMark())
                continue;
            
            name = column.getName();
            if (tablename.equals("addresses") && name.equals("LOGRADOURO")) {
                link = new Link(itemdata.itens, name, "addressmark");
                link.setText((String)itemdata.object.getValue(name));
                link.add(index, itemdata.itens.length() - 1);
                
                item.add(link);
                
                continue;
            }
            
            input = new TextField(itemdata.itens, name);
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
        
        codigo = getLong(itemdata.object.getValue("CODIGO"));
        
        if (codigo == 0) {
            i = itemdata.itens.length();
            if (i > 1) {
                i -= 2;
                input = itemdata.itens.get(i).get("CODIGO");
                codigo = input.get();
            }
            
            itemdata.object.setValue("CODIGO", codigo + 1);
        }
        
        if (itemdata.object != null)
            item.setObject(itemdata.object);
    }

}
