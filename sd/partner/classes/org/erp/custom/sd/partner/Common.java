package org.erp.custom.sd.partner;

import org.iocaste.documents.common.DocumentModelItem;
import org.iocaste.documents.common.ExtendedObject;
import org.iocaste.shell.common.DataForm;
import org.iocaste.shell.common.DataItem;
import org.iocaste.shell.common.Element;
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
    public final static byte NULL_ADDRESS = 1;
    public final static byte INVALID_ADDRESS = 2;
    
    public final static String[] TITLE = {
            "partner-display",
            "partner-create",
            "partner-update"
    };
    
    /**
     * 
     * @param itens
     * @param columns
     */
    public static final void enableTableColumns(Table itens, String... columns)
    {
        String name;
        
        for (TableColumn column : itens.getColumns()) {
            if (column.isMark())
                continue;
            
            name = column.getName();
            column.setVisible(false);
            
            for (String colname : columns) {
                if (!colname.equals(name))
                    continue;
                    
                column.setVisible(true);
                break;
            }
        }
    }
    
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
     * @param element
     * @return
     */
    public static final <T> T getValue(Element element) {
        return ((InputComponent)element).get();
    }
    
    /**
     * 
     * @param itens
     * @param object
     */
    public static final void insertItem(ItemData itemdata) {
        long codigo;
        Link link;
        InputComponent input;
        String name;
        Parameter index = new Parameter(itemdata.container, "index");
        TableItem item = new TableItem(itemdata.itens);
        int i = itemdata.itens.length();
        
        for (TableColumn column : itemdata.itens.getColumns()) {
            if (column.isMark())
                continue;
            
            name = column.getName();
            if (name.equals("CODIGO")) {
                link = new Link(itemdata.itens, name, itemdata.mark);
                item.add(link);
                
                continue;
            }
            
            input = new TextField(itemdata.itens, name);
            input.setObligatory(false);
            input.setEnabled(false);
            
            item.add(input);
        }
        
        codigo = getLong(itemdata.object.getValue("CODIGO"));
        
        if (codigo == 0) {
            if (i > 1) {
                i -= 2;
                link = itemdata.itens.get(i).get("CODIGO");
                codigo = Long.parseLong(link.getText());
                i += 2;
            } else {
                codigo = itemdata.partner * 100;
            }
            
            codigo++;
        }
        
        i--;
        itemdata.object.setValue("CODIGO", codigo);
        link = itemdata.itens.get(i).get("CODIGO");
        link.setText(Long.toString(codigo));
        link.add(index, i);
        
        item.setObject(itemdata.object);
    }
    
    public static final void insertCommunic(Table communics, ViewData view,
            ExtendedObject object) {
        DataForm contact;
        long codigo, contactid;
        int i;
        TextField tfield;
        String name;
        DocumentModelItem modelitem;
        TableItem item = new TableItem(communics);
        
        for (TableColumn column : communics.getColumns()) {
            if (column.isMark())
                continue;
            
            name = column.getName();
            modelitem = column.getModelItem();
            modelitem.setReference(null);
            tfield = new TextField(communics, name);
            tfield.setModelItem(modelitem);
            tfield.setEnabled((name.equals("CODIGO"))? false : true);
            
            item.add(tfield);
        }
        
        if (object != null) {
            item.setObject(object);
            return;
        }
        
        i = communics.length() - 1;
        contact = view.getElement("contact");
        contactid = Common.getLong(Common.getValue(contact.get("CODIGO")));
        
        if (i == 0) {
            codigo = contactid * 100;
        } else {
            item = communics.get(i - 1);
            codigo = Common.getLong(Common.getValue(item.get("CODIGO")));
        }
        
        item = communics.get(i);
        tfield = item.get("CODIGO");
        tfield.set(codigo + 1);
        
        tfield = item.get("CONTACT_ID");
        tfield.set(contactid);
        
    }
    
    /**
     * 
     * @param input
     * @param itens
     * @param display
     * @param value
     */
    public static final void loadListFromTable(DataItem input,
            Table itens, String display, String value) {
        String codigo, lograd;
        
        input.clear();
        
        for (TableItem item : itens.getItens()) {
            codigo = ((Link)item.get(value)).getText();
            lograd = Common.getValue(item.get(display));
            
            input.add(lograd, Long.parseLong(codigo));
        }
    }

}
