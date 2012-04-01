package org.erp.custom.mm.materials;

import org.iocaste.documents.common.Documents;
import org.iocaste.documents.common.ExtendedObject;
import org.iocaste.protocol.Function;
import org.iocaste.shell.common.Const;
import org.iocaste.shell.common.DataForm;
import org.iocaste.shell.common.Table;
import org.iocaste.shell.common.TableItem;
import org.iocaste.shell.common.ViewData;

public class Request {

    /**
     * 
     * @param view
     */
    public static final void additem(ViewData view, String tablename) {
        Table itens = view.getElement(tablename);
        byte mode = Common.getMode(view);
        
        Common.insertItem(mode, itens, view, null);
    }
    
    /**
     * 
     * @param view
     * @param function
     * @throws Exception
     */
    public static final void create(ViewData view, Function function)
            throws Exception {
        DataForm selection = view.getElement("selection");
        String matid = selection.get("material").get();
        Documents documents = new Documents(function);
        
        if (documents.getObject("MATERIAL", matid) != null) {
            view.message(Const.ERROR, "material.already.exists");
            return;
        }
        
        view.setReloadableView(true);
        view.export("matid", matid);
        view.export("mode", Common.CREATE);
        view.redirect(null, "material");
    }
    
    /**
     * 
     * @param i
     * @param material
     * @return
     */
    private static final String getItemId(int i, String material) {
        return new StringBuilder(Integer.toString(i)).append(".").
        append(material).toString();
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
        ExtendedObject[] prices, promos;
        DataForm selection = view.getElement("selection");
        String query, matid = selection.get("material").get();
        Documents documents = new Documents(function);
        ExtendedObject material = documents.getObject("MATERIAL", matid);
        
        if (material == null) {
            view.message(Const.ERROR, "material.not.found");
            return;
        }
        
        query = "from PRECO_MATERIAL where MATERIAL = ?";
        prices = documents.select(query, matid);
        
        query = "from PROMOCAO_MATERIAL where MATERIAL = ?";
        promos = documents.select(query, matid);
        
        view.setTitle(Common.TITLE[mode]);
        view.setReloadableView(true);
        view.export("material", material);
        view.export("prices", prices);
        view.export("promos", promos);
        view.export("mode", mode);
        view.redirect(null, "material");
    }
    
    /**
     * 
     * @param view
     */
    public static final void removeitem(ViewData view, String tablename) {
        Table itens = view.getElement(tablename);
        
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
        Table promos = view.getElement("promos");
        ExtendedObject opromo, oprice, obase = base.getObject();
        byte mode = Common.getMode(view);
        int i = 0;
        String itemid, material = obase.getValue("ID");
        
        if (mode == Common.CREATE) {
            view.setTitle(Common.TITLE[Common.UPDATE]);
            view.export("mode", Common.UPDATE);
            documents.save(obase);
        } else {
            documents.modify(obase);
            documents.update("delete from PRECO_MATERIAL where MATERIAL = ?",
                    material);
            
            documents.update("delete from PROMOCAO_MATERIAL where MATERIAL = ?",
                    material);
        }
        
        i = 0;
        for (TableItem item : prices.getItens()) {
            oprice = item.getObject();
            itemid = getItemId(i, material);
            i++;
            oprice.setValue("ID", itemid);
            oprice.setValue("MATERIAL", material);
            documents.save(oprice);
        }
        
        i = 0;
        for (TableItem item : promos.getItens()) {
            opromo = item.getObject();
            itemid = getItemId(i, material);
            i++;
            opromo.setValue("ID", itemid);
            opromo.setValue("MATERIAL", material);
            documents.save(opromo);
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
