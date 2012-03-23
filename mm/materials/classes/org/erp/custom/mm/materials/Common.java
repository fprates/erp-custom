package org.erp.custom.mm.materials;

import org.iocaste.documents.common.DocumentModel;
import org.iocaste.documents.common.DocumentModelItem;
import org.iocaste.shell.common.Table;
import org.iocaste.shell.common.TableItem;
import org.iocaste.shell.common.TextField;
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
     */
    public static final void insertItem(Table itens, ViewData view) {
        TextField tfield;
        String name;
        TableItem item = new TableItem(itens);
        DocumentModel model = itens.getModel();
        
        for (DocumentModelItem modelitem : model.getItens()) {
            tfield = new TextField(itens, modelitem.getName());
            tfield.setModelItem(modelitem);
            
            name = modelitem.getName();
            if (name.equals("ID"))
                tfield.setEnabled(false);
            
            if (name.equals("VL_VENDA") && view.getFocus() == null)
                view.setFocus(tfield);
            
            item.add(tfield);
        }
        
    }
}
