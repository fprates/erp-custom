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
        TabbedPane tabs = new TabbedPane(container, "tabs");
        TabbedPaneItem tabitem = new TabbedPaneItem(tabs, "basepane");
        DataForm base = new DataForm(tabs, "base");
        StandardContainer pricescnt = new StandardContainer(tabs, "pricescnt");
        Table prices = new Table(pricescnt, "prices");
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
        tabitem = new TabbedPaneItem(tabs, "pricespane");
        tabitem.setContainer(pricescnt);
        
        prices.importModel(documents.getModel("PRECO_MATERIAL"));
        for (TableColumn column : prices.getColumns())
            if (column.getName().equals("MATERIAL"))
                column.setVisible(false);
        
        switch (mode) {
        case Common.CREATE:
            matid = view.getParameter("matid");
            base.get("ID").setValue(matid);
            
            prices.setMark(true);
            Common.insertItem(prices, view);
            
            new Button(pricescnt, "additem");
            new Button(pricescnt, "removeitem");
            
            new Button(container, "save");
            
            break;
        case Common.UPDATE:
            material = view.getParameter("material");
            base.setObject(material);

            prices.setMark(true);
            
            new Button(pricescnt, "additem");
            new Button(pricescnt, "removeitem");
            
            new Button(container, "save");
            
            break;
        case Common.SHOW:
            material = view.getParameter("material");
            base.setObject(material);
            
            prices.setMark(false);
            
            break;
        }
        
        view.setNavbarActionEnabled("back", true);
        view.addContainer(container);
        view.setTitle(Common.TITLE[mode]);
    }
}
