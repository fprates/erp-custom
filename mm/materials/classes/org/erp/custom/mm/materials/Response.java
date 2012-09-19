package org.erp.custom.mm.materials;

import org.iocaste.documents.common.DocumentModelItem;
import org.iocaste.documents.common.Documents;
import org.iocaste.documents.common.ExtendedObject;
import org.iocaste.protocol.Function;
import org.iocaste.shell.common.Button;
import org.iocaste.shell.common.Const;
import org.iocaste.shell.common.DataForm;
import org.iocaste.shell.common.DataItem;
import org.iocaste.shell.common.Element;
import org.iocaste.shell.common.Form;
import org.iocaste.shell.common.PageControl;
import org.iocaste.shell.common.StandardContainer;
import org.iocaste.shell.common.TabbedPane;
import org.iocaste.shell.common.TabbedPaneItem;
import org.iocaste.shell.common.Table;
import org.iocaste.shell.common.TableColumn;
import org.iocaste.shell.common.View;

public class Response {
	
	private static final void loadItens(View view, byte mode) {
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
    public static final void main(View view, Function function)
            throws Exception {
        DocumentModelItem matid;
        Form container = new Form(view, "main");
        PageControl pagecontrol = new PageControl(container);
        DataForm form = new DataForm(container, "selection");
        DataItem item = new DataItem(form, Const.TEXT_FIELD, "material");

        view.setFocus(item);
        pagecontrol.add("back");
        
        matid = new Documents(function).getModel("MATERIAL").getModelItem("ID");
        item.setModelItem(matid);
        item.setObligatory(true);
        
        new Button(container, "create");
        new Button(container, "show");
        new Button(container, "update");
        
        view.setTitle("material-selection");
    }
    
    /**
     * 
     * @param view
     * @param function
     */
    public static final void material(View view, Function function) {
        Button save, addpromo, removepromo, addmaterial, removematerial;
        Button addprice, removeprice, validate;
        String matid;
        Table prices, promos, submat;
        TabbedPaneItem tabitem;
        byte mode = Common.getMode(view);
        Form container = new Form(view, "main");
        PageControl pagecontrol = new PageControl(container);
        TabbedPane tabs = new TabbedPane(container, "tabs");
        DataForm base = new DataForm(tabs, "base");
        StandardContainer pricescnt = new StandardContainer(tabs, "pricescnt");
        StandardContainer promocnt = new StandardContainer(tabs, "promocnt");
        StandardContainer submatcnt = new StandardContainer(tabs, "submatcnt");
        Documents documents = new Documents(function);
        
        pagecontrol.add("back");
        validate = new Button(container, "validate");
        validate.setSubmit(true);
        
        /*
         * Base
         */
        base.importModel(documents.getModel("MATERIAL"));
        base.get("ACTIVE").setComponentType(Const.CHECKBOX);
        for (Element element : base.getElements())
            ((DataItem)element).setEnabled(mode != Common.SHOW);
        base.get("ID").setEnabled(false);
        base.get("MAT_TYPE").setValidator(MaterialTypeValidator.class);
        base.get("MAT_GROUP").setValidator(MaterialGroupValidator.class);
        
        tabitem = new TabbedPaneItem(tabs, "basepane");
        tabitem.setContainer(base);
        
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
            base.get("MAT_TYPE").setObligatory(true);
            base.get("MAT_GROUP").setObligatory(true);
            
            Common.insertItem(mode, prices, view, null);
            Common.insertItem(mode, promos, view, null);
            Common.insertItem(mode, submat, view, null);
            
            break;
            
        case Common.UPDATE:
            base.get("MAT_TYPE").setObligatory(true);
            base.get("MAT_GROUP").setObligatory(true);
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
            validate.setVisible(false);
            
            prices.setMark(false);
            promos.setMark(false);
            submat.setMark(false);
            
            loadItens(view, mode);
            
            break;
        }
        
        view.setTitle(Common.TITLE[mode]);
    }
}
