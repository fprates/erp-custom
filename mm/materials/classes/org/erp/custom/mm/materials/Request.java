package org.erp.custom.mm.materials;

import org.iocaste.documents.common.Documents;
import org.iocaste.documents.common.ExtendedObject;
import org.iocaste.globalconfig.common.GlobalConfig;
import org.iocaste.shell.common.Const;
import org.iocaste.shell.common.DataForm;
import org.iocaste.shell.common.Table;
import org.iocaste.shell.common.TableItem;

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
     * @param function
     */
    public static final void create(Context context) {
        DataForm selection = context.view.getElement("material");
        Documents documents = new Documents(context.function);

        context.matid = selection.get("ID").get();
        if (documents.getObject("MATERIAL", context.matid) != null) {
            context.view.message(Const.ERROR, "material.already.exists");
            return;
        }
        
        context.mode = Context.CREATE;
        context.view.redirect("form");
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
    
    public static final void load(Context context) {
        DataForm selection = context.view.getElement("material");
        Documents documents = new Documents(context.function);

        context.matid = selection.get("ID").get();
        context.material = documents.getObject("MATERIAL", context.matid);
        if (context.material == null) {
            context.view.message(Const.ERROR, "material.not.found");
            return;
        }
        
        context.prices = documents.select(QUERIES[PRICES], context.matid);
        context.promos = documents.select(QUERIES[PROMOS], context.matid);
        context.submats = documents.select(QUERIES[SUBMATS], context.matid);
        
        context.view.redirect("form");
    }
    
    /**
     * 
     * @param view
     * @param function
     */
    public static final void save(Context context) {
        boolean autocode;
        GlobalConfig config;
        String material;
        Documents documents = new Documents(context.function);
        DataForm base = context.view.getElement("base");
        ExtendedObject obase = base.getObject();
        
        switch (context.mode) {
        case Context.CREATE:
            config = new GlobalConfig(context.function);
            autocode = config.get("MATERIAL_AUTOCODE");
            if (autocode) {
                material = Long.toString(documents.
                        getNextNumber("MATERIAL_ID"));
                obase.set("ID", material);
                base.setObject(obase);
            } else {
                material = obase.get("ID");
            }
            
            context.view.setTitle(Context.TITLE[Context.UPDATE]);
            context.mode = Context.UPDATE;
            documents.save(obase);
            break;
        default:
            material = obase.get("ID");
            documents.modify(obase);
            documents.update(QUERIES[DEL_SUBMAT], material);
            documents.update(QUERIES[DEL_PRICES], material);
            documents.update(QUERIES[DEL_PROMOS], material);
            break;
        }
        
        saveItens((Table)context.view.getElement("prices"), material, documents);
        saveItens((Table)context.view.getElement("promotions"), material, documents);
        saveItens((Table)context.view.getElement("submats"), material, documents);
        
        context.view.message(Const.STATUS, "material.saved.successfully");
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
        
        for (TableItem item : table.getItems()) {
            object = item.getObject();
            itemid = getItemId(i, material);
            i++;
            object.set("ID", itemid);
            object.set("MATERIAL", material);
            documents.save(object);
        }
    }
}
