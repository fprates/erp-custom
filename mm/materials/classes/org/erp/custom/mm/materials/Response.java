package org.erp.custom.mm.materials;

import org.iocaste.documents.common.Documents;
import org.iocaste.documents.common.ExtendedObject;
import org.iocaste.protocol.Function;
import org.iocaste.shell.common.Button;
import org.iocaste.shell.common.Const;
import org.iocaste.shell.common.Container;
import org.iocaste.shell.common.DataForm;
import org.iocaste.shell.common.DataItem;
import org.iocaste.shell.common.Element;
import org.iocaste.shell.common.Form;
import org.iocaste.shell.common.ViewData;

public class Response {

    /**
     * 
     * @param view
     * @param function
     * @throws Exception
     */
    public static final void main(ViewData view, Function function)
            throws Exception {
        Container container = new Form(null, "main");
        DataForm form = new DataForm(container, "selection");
        DataItem item = new DataItem(form, Const.TEXT_FIELD, "material");
        
        item.setModelItem(new Documents(function).getModel("MATERIAL").
                getModelItem("ID"));
        item.setObligatory(true);
        
        new Button(container, "create");
        new Button(container, "show");
        new Button(container, "update");
        
        view.setFocus("material");
        view.setTitle("material-selection");
        view.addContainer(container);
        view.setNavbarActionEnabled("back", true);
    }
    
    /**
     * 
     * @param view
     * @param function
     * @throws Exception
     */
    public static final void material(ViewData view, Function function)
            throws Exception {
        String matid, name;
        ExtendedObject material;
        DataItem dataitem;
        byte mode = Common.getMode(view);
        Container container = new Form(null, "main");
        DataForm base = new DataForm(container, "base");
        
        base.importModel(new Documents(function).getModel("MATERIAL"));
        
        for (Element element : base.getElements()) {
            dataitem = (DataItem)element;
            
            name = dataitem.getName();
            if (name.equals("ID")) {
                dataitem.setEnabled(false);
                continue;
            }
            
            if (name.equals("ACTIVE"))
                dataitem.setComponentType(Const.CHECKBOX);
            
            dataitem.setEnabled((mode == Common.SHOW)? false : true);
        }
        
        switch (mode) {
        case Common.CREATE:
            matid = view.getParameter("matid");
            base.get("ID").setValue(matid);
            
            new Button(container, "save");
            
            break;
        case Common.UPDATE:
            material = view.getParameter("material");
            base.setObject(material);
            
            new Button(container, "save");
            
            break;
        case Common.SHOW:
            material = view.getParameter("material");
            base.setObject(material);
            
            break;
        }
        
        view.setNavbarActionEnabled("back", true);
        view.addContainer(container);
        view.setTitle(Common.TITLE[mode]);
    }
}
