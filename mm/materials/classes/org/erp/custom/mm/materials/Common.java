package org.erp.custom.mm.materials;

import org.iocaste.documents.common.DocumentModel;
import org.iocaste.documents.common.DocumentModelItem;
import org.iocaste.documents.common.ExtendedObject;
import org.iocaste.shell.common.Table;
import org.iocaste.shell.common.TableItem;
import org.iocaste.shell.common.TextField;
import org.iocaste.shell.common.ValidatorConfig;
import org.iocaste.shell.common.ViewData;

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
    public static final byte getMode(ViewData view) {
        return view.getParameter("mode");
    }
    
    /**
     * 
     * @param itens
     * @param view
     * @param object
     */
    public static final void insertItem(byte mode, Table itens, ViewData view,
            ExtendedObject object) {
        TextField tfield;
        String name;
        TableItem item = new TableItem(itens);
        DocumentModel model = itens.getModel();
        ValidatorConfig vlvalidatorcfg = new ValidatorConfig();
        ValidatorConfig dtvalidatorcfg = new ValidatorConfig();
        
        vlvalidatorcfg.setValidator(ValorCustoValidator.class);
        dtvalidatorcfg.setValidator(DataInicialValidator.class);
        
        for (DocumentModelItem modelitem : model.getItens()) {
            tfield = new TextField(itens, modelitem.getName());
            tfield.setEnabled((mode == Common.SHOW)? false : true);
            item.add(tfield);
            
            name = modelitem.getName();
            if (name.equals("VL_CUSTO")) {
                vlvalidatorcfg.add(tfield);
                tfield.setValidatorConfig(vlvalidatorcfg);
            }
            
            if (name.equals("VL_VENDA")) {
                if (view.getFocus() == null)
                    view.setFocus(tfield);
                
                vlvalidatorcfg.add(tfield);
            }
            
            if (name.equals("DT_INICIAL")) {
                tfield.setValidatorConfig(dtvalidatorcfg);
                dtvalidatorcfg.add(tfield);
            }
            
            if (name.equals("DT_FINAL"))
                dtvalidatorcfg.add(tfield);
        }
        
        if (object != null)
            item.setObject(object);
        
    }
}
