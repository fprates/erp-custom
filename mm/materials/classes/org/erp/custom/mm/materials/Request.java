package org.erp.custom.mm.materials;

import org.iocaste.documents.common.Documents;
import org.iocaste.documents.common.ExtendedObject;
import org.iocaste.protocol.Function;
import org.iocaste.shell.common.Const;
import org.iocaste.shell.common.DataForm;
import org.iocaste.shell.common.Table;
import org.iocaste.shell.common.TableItem;
import org.iocaste.shell.common.View;

public class Request {
    private static final byte PRICES = 0;
    private static final byte PROMOS = 1;
    private static final byte SUBMATS = 2;
    private static final byte DEL_SUBMAT = 3;
    private static final byte DEL_PRICES = 4;
    private static final byte DEL_PROMOS = 5;
    private static final String[] QUERIES = {
        "from PRECO_MATERIAL where MATERIAL = ?",
        "from PROMOCAO_MATERIAL where MATERIAL = ?",
        "from SUB_MATERIAL where MATERIAL = ?",
        "delete from SUB_MATERIAL where MATERIAL = ?",
        "delete from PRECO_MATERIAL where MATERIAL = ?",
        "delete from PROMOCAO_MATERIAL where MATERIAL = ?"
    };
    
    /**
     * 
     * @param view
     */
    public static final void additem(View view, String tablename) {
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
    public static final void create(View view, Function function)
            throws Exception {
        DataForm selection = view.getElement("selection");
        String matid = selection.get("material").get();
        Documents documents = new Documents(function);
        
        if (documents.getObject("MATERIAL", matid) != null) {
            view.message(Const.ERROR, "material.already.exists");
            return;
        }
        
        view.clearExports();
        view.setReloadableView(true);
        view.export("matid", matid);
        view.export("mode", Common.CREATE);
        view.redirect("material");
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
     */
    private static final void load(View view, Function function, byte mode) {
        ExtendedObject[] objects;
        DataForm selection = view.getElement("selection");
        String matid = selection.get("material").get();
        Documents documents = new Documents(function);
        ExtendedObject material = documents.getObject("MATERIAL", matid);
        
        if (material == null) {
            view.message(Const.ERROR, "material.not.found");
            return;
        }
        
        view.clearExports();
        objects = documents.select(QUERIES[PRICES], matid);
        view.export("prices", objects);
        
        objects = documents.select(QUERIES[PROMOS], matid);
        view.export("promos", objects);
        
        objects = documents.select(QUERIES[SUBMATS], matid);
        view.export("submats", objects);
        
        view.setTitle(Common.TITLE[mode]);
        view.setReloadableView(true);
        view.export("material", material);
        view.export("mode", mode);
        view.redirect("material");
    }
    
    /**
     * 
     * @param view
     */
    public static final void removeitem(View view, String tablename) {
        Table itens = view.getElement(tablename);
        
        for (TableItem item : itens.getItens())
            if (item.isSelected())
                itens.remove(item);
    }
    
    /**
     * 
     * @param view
     * @param function
     */
    public static final void save(View view, Function function) {
        Documents documents = new Documents(function);
        DataForm base = view.getElement("base");
        ExtendedObject obase = base.getObject();
        byte mode = Common.getMode(view);
        String material = obase.getValue("ID");
        
        if (mode == Common.CREATE) {
            view.setTitle(Common.TITLE[Common.UPDATE]);
            view.export("mode", Common.UPDATE);
            documents.save(obase);
        } else {
            documents.modify(obase);
            documents.update(QUERIES[DEL_SUBMAT], material);
            documents.update(QUERIES[DEL_PRICES], material);
            documents.update(QUERIES[DEL_PROMOS], material);
        }
        
        saveItens((Table)view.getElement("prices"), material, documents);
        saveItens((Table)view.getElement("promos"), material, documents);
        saveItens((Table)view.getElement("submats"), material, documents);
        
        view.message(Const.STATUS, "material.saved.successfully");
    }
    
    /**
     * 
     * @param table
     * @param material
     * @param documents
     */
    private static final void saveItens(Table table, String material,
            Documents documents) {
        String itemid;
        ExtendedObject object;
        int i = 0;
        
        for (TableItem item : table.getItens()) {
            object = item.getObject();
            itemid = getItemId(i, material);
            i++;
            object.setValue("ID", itemid);
            object.setValue("MATERIAL", material);
            documents.save(object);
        }
    }
    /**
     * 
     * @param view
     * @param function
     */
    public static final void show(View view, Function function) {
        load(view, function, Common.SHOW);
    }
    
    /**
     * 
     * @param view
     * @param function
     */
    public static final void update(View view, Function function) {
        load(view, function, Common.UPDATE);
    }
}
