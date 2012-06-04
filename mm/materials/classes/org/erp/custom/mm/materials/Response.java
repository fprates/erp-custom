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
import org.iocaste.shell.common.StandardContainer;
import org.iocaste.shell.common.TabbedPane;
import org.iocaste.shell.common.TabbedPaneItem;
import org.iocaste.shell.common.Table;
import org.iocaste.shell.common.TableColumn;
import org.iocaste.shell.common.ViewData;

public class Response {
	
	private static final void loadItens(ViewData view, byte mode) {
        ExtendedObject[] objects;
        Table itens;
        ExtendedObject material = view.getParameter("material");
        DataForm base = view.getElement("base");
        
        base.setObject(material);

        for (String name : new String[] {"prices", "promos", "submats"}) {
	        objects = view.getParameter(name);
	        if (objects == null)
	            continue;
	        
        	itens = view.getElement(name);
            for (ExtendedObject oprice : objects)
                Common.insertItem(mode, itens, view, oprice);
        }
	}

    /**
     * 
     * @param view
     * @param function
     * @throws Exception
     */
    public static final void main(ViewData view, Function function)
            throws Exception {
        Container container = new Form(view, "main");
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
        Button save, addpromo, removepromo, addmaterial, removematerial;
        Button addprice, removeprice;
        String matid, name;
        DataItem dataitem;
        Table prices, promos, submat;
        byte mode = Common.getMode(view);
        Container container = new Form(view, "main");
        TabbedPane tabs = new TabbedPane(container, "tabs");
        TabbedPaneItem tabitem = new TabbedPaneItem(tabs, "basepane");
        DataForm base = new DataForm(tabs, "base");
        StandardContainer pricescnt = new StandardContainer(tabs, "pricescnt");
        StandardContainer promocnt = new StandardContainer(tabs, "promocnt");
        StandardContainer submatcnt = new StandardContainer(tabs, "submatcnt");
        Documents documents = new Documents(function);
        
        /*
         * Base
         */
        tabitem.setContainer(base);
        base.importModel(documents.getModel("MATERIAL"));
        
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
        
        /*
         * Prices
         */
        addprice = new Button(pricescnt, "addprice");
        removeprice = new Button(pricescnt, "removeprice");
        prices = new Table(pricescnt, "prices");
        prices.setMark(true);
        prices.importModel(documents.getModel("PRECO_MATERIAL"));
        prices.getColumn("MATERIAL").setVisible(false);
        prices.getColumn("ID").setVisible(false);
        
        tabitem = new TabbedPaneItem(tabs, "pricespane");
        tabitem.setContainer(pricescnt);
        
        /*
         * Promotion
         */
        addpromo = new Button(promocnt, "addpromo");
        removepromo = new Button(promocnt, "removepromo");
        promos = new Table(promocnt, "promos");
        promos.setMark(true);
        promos.importModel(documents.getModel("PROMOCAO_MATERIAL"));
        promos.getColumn("MATERIAL").setVisible(false);
        promos.getColumn("ID").setVisible(false);
        
        tabitem = new TabbedPaneItem(tabs, "promotions");
        tabitem.setContainer(promocnt);
        
        /*
         * Sub-materials
         */
        addmaterial = new Button(submatcnt, "addmaterial");
        removematerial = new Button(submatcnt, "removematerial");
        submat = new Table(submatcnt, "submats");
        submat.setMark(true);
        submat.importModel(documents.getModel("SUB_MATERIAL"));
        
        for (TableColumn column : submat.getColumns())
            if (!column.isMark())
                column.setVisible(column.getName().equals("SUB_MATERIAL"));
            
        tabitem = new TabbedPaneItem(tabs, "submaterials");
        tabitem.setContainer(submatcnt);
        
        save = new Button(container, "save");
        
        switch (mode) {
        case Common.CREATE:
            matid = view.getParameter("matid");
            base.get("ID").set(matid);
            
            Common.insertItem(mode, prices, view, null);
            Common.insertItem(mode, promos, view, null);
            Common.insertItem(mode, submat, view, null);
            
            break;
            
        case Common.UPDATE:
            loadItens(view, mode);
            
            break;
            
        case Common.SHOW:
        	save.setVisible(false);
        	addprice.setVisible(false);
        	removeprice.setVisible(false);
        	addpromo.setVisible(false);
        	removepromo.setVisible(false);
        	addmaterial.setVisible(false);
        	removematerial.setVisible(false);
            
            prices.setMark(false);
            promos.setMark(false);
            submat.setMark(false);
            
            loadItens(view, mode);
            
            break;
        }
        
        view.setNavbarActionEnabled("back", true);
        view.setTitle(Common.TITLE[mode]);
    }
}
