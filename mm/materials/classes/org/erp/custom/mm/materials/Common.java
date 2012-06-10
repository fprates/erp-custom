package org.erp.custom.mm.materials;

import org.iocaste.documents.common.DocumentModel;
import org.iocaste.documents.common.DocumentModelItem;
import org.iocaste.documents.common.ExtendedObject;
import org.iocaste.shell.common.InputComponent;
import org.iocaste.shell.common.Table;
import org.iocaste.shell.common.TableItem;
import org.iocaste.shell.common.TextField;
import org.iocaste.shell.common.View;

public class Common {

    public static final byte CREATE = 0;
    public static final byte SHOW = 1;
    public static final byte UPDATE = 2;
    public static final String[] TITLE = {
        "material-editor-create",
        "material-editor-show",
        "material-editor-update"
    };
    
    /**
     * 
     * @param view
     * @return
     */
    public static final byte getMode(View view) {
        return view.getParameter("mode");
    }
    
    /**
     * 
     * @param itens
     * @param view
     * @param object
     */
    public static final void insertItem(byte mode, Table itens, View view,
            ExtendedObject object) {
        TextField tfield;
        String name, tablename;
        TableItem item = new TableItem(itens);
        DocumentModel model = itens.getModel();
        
        tablename = itens.getName();        
        for (DocumentModelItem modelitem : model.getItens()) {
            name = modelitem.getName();
            tfield = new TextField(itens, name);
            tfield.setEnabled(mode != Common.SHOW);
            item.add(tfield);
            
            if (tablename.equals("submats") && !name.equals("VL_VENDA"))
                continue;
            
            if (view.getFocus() == null)
                view.setFocus(tfield);
        }
        
        if (object != null)
            item.setObject(object);
        
        if (tablename.equals("submats"))
            return;
        
        tfield = (TextField)item.get("VL_CUSTO");
        tfield.setValidator(ValorCustoValidator.class);
        tfield.addValidatorInput((InputComponent)item.get("VL_VENDA"));

        tfield = (TextField)item.get("DT_INICIAL");
        tfield.setValidator(DataInicialValidator.class);
        tfield.addValidatorInput((InputComponent)item.get("DT_FINAL"));
    }
}
