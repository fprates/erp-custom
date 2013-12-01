package org.erp.custom.sd.documents;

import org.iocaste.documents.common.ExtendedObject;
import org.iocaste.shell.common.DataForm;
import org.iocaste.shell.common.InputComponent;
import org.iocaste.shell.common.Table;
import org.iocaste.shell.common.TableColumn;
import org.iocaste.shell.common.TableItem;
import org.iocaste.shell.common.TextField;
import org.iocaste.shell.common.View;

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
    
    public static final byte getMode(View view) {
        return view.getParameter("mode");
    }
    
    public static final void insertCondition(Table itens,
            ExtendedObject object, View view) {
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
            
            if (name.equals("CONDICAO")) {
                tfield.setValidator(ConditionTypeValidator.class);
                view.setFocus(tfield);
            }
            
            item.add(tfield);
        }
        
        if (object == null) {
            object = item.getObject();
            
            if (last != null)
                l = last.getl("ID");
            
            object.set("ID", l + 1);
        }
        
        item.setObject(object);
    }
    
    /**
     * 
     * @param mode
     * @param itens
     * @param view
     * @param object
     */
    public static final void insertItem(Table itens, View view,
            ExtendedObject object) {
        String name;
        TextField tfield;
        TableItem item;
        byte mode = getMode(view);
        long docid = 0, i = 0;
        InputComponent headvalue = ((DataForm)view.getElement("header")).
                get("VALOR");
        
        if (object == null) {
            for (TableItem item_ : itens.getItems()) {
                i = ((InputComponent)item_.get("ITEM_NUMBER")).get();
                docid = ((InputComponent)item_.get("DOCUMENT_ID")).get(); 
            }
            
            i++;
        } else {
            i = object.getl("ITEM_NUMBER");
            docid = object.getl("DOCUMENT_ID");
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
                tfield.setObligatory(mode != SHOW);
                tfield.setEnabled(mode != SHOW);
                continue;
            }
            
            if (name.equals("QUANTITY")) {
                tfield.setEnabled(mode != SHOW);
                continue;
            }
            
            tfield.setEnabled(false);
        }
        
        tfield = item.get("MATERIAL");
        tfield.setValidator(MaterialValidator.class);
        tfield.addValidatorInput(headvalue);
        
        for (String tfname : new String[] {
                "PRECO_UNITARIO", "PRECO_TOTAL", "QUANTITY"})
            tfield.addValidatorInput((InputComponent)item.get(tfname));
        
        if (object != null)
            item.setObject(object);
    }
}
