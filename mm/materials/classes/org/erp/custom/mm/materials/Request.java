package org.erp.custom.mm.materials;

import org.iocaste.documents.common.Documents;
import org.iocaste.documents.common.ExtendedObject;
import org.iocaste.protocol.Function;
import org.iocaste.shell.common.Const;
import org.iocaste.shell.common.DataForm;
import org.iocaste.shell.common.InputComponent;
import org.iocaste.shell.common.Table;
import org.iocaste.shell.common.TableItem;
import org.iocaste.shell.common.ViewData;

public class Request {

    /**
     * 
     * @param view
     */
    public static final void additem(ViewData view) {
        Table itens = view.getElement("prices");
        
        Common.insertItem(itens, view);
    }
    
    /**
     * 
     * @param view
     */
    public static final void create(ViewData view) {
        DataForm selection = view.getElement("selection");
        String matid = ((InputComponent)selection.get("material")).getValue();
        
        view.setReloadableView(true);
        view.export("matid", matid);
        view.export("mode", Common.CREATE);
        view.redirect(null, "material");
    }
    
    /**
     * 
     * @param view
     * @param function
     * @param mode
     * @throws Exception
     */
    private static final void load(ViewData view, Function function, byte mode)
            throws Exception {
        DataForm selection = view.getElement("selection");
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
     */
    public static final void removeitem(ViewData view) {
        Table itens = view.getElement("prices");
        
        for (TableItem item : itens.getItens())
            if (item.isSelected())
                itens.remove(item);
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
        DataForm base = view.getElement("base");
        Table prices = view.getElement("prices");
        ExtendedObject oprice, obase = base.getObject();
        byte mode = Common.getMode(view);
        int i = 0;
        String material = obase.getValue("ID");
        
        if (mode == Common.CREATE) {
            view.export("mode", Common.UPDATE);
            documents.save(obase);
        } else {
            documents.modify(obase);
            documents.update("delete from PRECO_MATERIAL where MATERIAL = ?",
                    material);
        }
        
        i = 0;
        for (TableItem item : prices.getItens()) {
            oprice = item.getObject();
            oprice.setValue("ID", new StringBuilder(i++).append(".").
                    append(material).toString());
            oprice.setValue("MATERIAL", material);
            documents.save(oprice);
        }
        
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
