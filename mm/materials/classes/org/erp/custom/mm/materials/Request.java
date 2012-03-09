package org.erp.custom.mm.materials;

import org.iocaste.documents.common.Documents;
import org.iocaste.documents.common.ExtendedObject;
import org.iocaste.protocol.Function;
import org.iocaste.shell.common.Const;
import org.iocaste.shell.common.DataForm;
import org.iocaste.shell.common.InputComponent;
import org.iocaste.shell.common.ViewData;

public class Request {

    public static final void create(ViewData view) {
        DataForm selection = (DataForm)view.getElement("selection");
        String matid = ((InputComponent)selection.get("material")).getValue();
        
        view.setReloadableView(true);
        view.export("matid", matid);
        view.export("mode", Common.CREATE);
        view.redirect(null, "material");
    }
    
    private static final void load(ViewData view, Function function, byte mode)
            throws Exception {
        DataForm selection = (DataForm)view.getElement("selection");
        String matid = selection.get("material").getValue();
        Documents documents = new Documents(function);
        ExtendedObject material = documents.getObject("MATERIAL", matid);
        
        if (material == null) {
            view.message(Const.ERROR, "material.not.found");
            return;
        }
        
        view.setReloadableView(true);
        view.export("material", material);
        view.export("mode", mode);
        view.redirect(null, "material");
    }
    
    /**
     * 
     * @param view
     * @param function
     * @throws Exception
     */
    public static final void save(ViewData view, Function function)
            throws Exception {
        Documents documents = new Documents(function);
        DataForm base = (DataForm)view.getElement("base");
        ExtendedObject obase = base.getObject();
        byte mode = Common.getMode(view);
        
        if (mode == Common.CREATE) {
            view.export("mode", Common.UPDATE);
        }
        
        documents.save(obase);
        documents.commit();
        
        view.message(Const.STATUS, "material.saved.successfully");
    }
    
    /**
     * 
     * @param view
     * @param function
     * @throws Exception
     */
    public static final void show(ViewData view, Function function)
            throws Exception {
        load(view, function, Common.SHOW);
    }
    
    /**
     * 
     * @param view
     * @param function
     * @throws Exception
     */
    public static final void update(ViewData view, Function function)
            throws Exception {
        load(view, function, Common.UPDATE);
    }
}
