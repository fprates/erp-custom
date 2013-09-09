package org.erp.custom.mm.materials;

import org.iocaste.shell.common.Button;
import org.iocaste.shell.common.Const;
import org.iocaste.shell.common.DataForm;
import org.iocaste.shell.common.Element;
import org.iocaste.shell.common.Form;
import org.iocaste.shell.common.InputComponent;
import org.iocaste.shell.common.PageControl;
import org.iocaste.shell.common.TabbedPane;
import org.iocaste.shell.common.TabbedPaneItem;
import org.iocaste.shell.common.Table;
import org.iocaste.shell.common.TableTool;
import org.iocaste.shell.common.View;

public class Response {
    
    /**
     * 
     * @param view
     * @param function
     */
    public static final void form(View view, Context context) {
        InputComponent input;
        Button validate;
        Table prices, promos, submat;
        TabbedPaneItem tabitem;
        Form container = new Form(view, "main");
        PageControl pagecontrol = new PageControl(container);
        TabbedPane tabs = new TabbedPane(container, "tabs");
        DataForm base = new DataForm(tabs, "base");
        
        pagecontrol.add("back");
        if (context.mode != Context.SHOW)
            pagecontrol.add("save", PageControl.REQUEST);
        
        /*
         * Base
         */
        base.importModel(context.materialmodel);
        base.get("ACTIVE").setComponentType(Const.CHECKBOX);
        for (Element element : base.getElements())
            element.setEnabled(context.mode != Context.SHOW);
        
        base.get("ID").setEnabled(false);
        input = base.get("MAT_TYPE");
        input.setValidator(MaterialTypeValidator.class);
        context.function.validate(input);
        
        input = base.get("MAT_GROUP");
        input.setValidator(MaterialGroupValidator.class);
        context.function.validate(input);
        
        tabitem = new TabbedPaneItem(tabs, "basepane");
        tabitem.setContainer(base);
        
        /*
         * Prices
         */
        context.priceshelper = new TableTool(tabs, "prices");
        prices = context.priceshelper.getTable();
        prices.importModel(context.pricesmodel);
        context.priceshelper.visible(
                "VL_VENDA", "VL_CUSTO", "DT_INICIAL", "DT_FINAL");
        tabitem = new TabbedPaneItem(tabs, "pricespane");
        tabitem.setContainer(context.priceshelper.getContainer());
        
        /*
         * Promotion
         */
        context.promotionshelper = new TableTool(tabs, "promotions");
        promos = context.promotionshelper.getTable();
        promos.importModel(context.promotionsmodel);
        context.promotionshelper.visible(
                "VL_VENDA", "VL_CUSTO", "DT_INICIAL", "DT_FINAL");
        
        tabitem = new TabbedPaneItem(tabs, "promotionspane");
        tabitem.setContainer(context.promotionshelper.getContainer());
        
        /*
         * Sub-materials
         */
        context.smaterialshelper = new TableTool(tabs, "submats");
        submat = context.smaterialshelper.getTable();
        submat.importModel(context.submatmodel);
        context.smaterialshelper.visible("SUB_MATERIAL");
            
        tabitem = new TabbedPaneItem(tabs, "submatspane");
        tabitem.setContainer(context.smaterialshelper.getContainer());

        context.priceshelper.setObjects(context.prices);
        context.promotionshelper.setObjects(context.promos);
        context.smaterialshelper.setObjects(context.submats);
        
        validate = new Button(container, "validate");
        validate.setSubmit(true);
        
        switch (context.mode) {
        case Context.CREATE:
            base.get("ID").set(context.matid);
            base.get("MAT_TYPE").setObligatory(true);
            base.get("MAT_GROUP").setObligatory(true);
            
            context.priceshelper.setMode(TableTool.UPDATE, view);
            context.promotionshelper.setMode(TableTool.UPDATE, view);
            context.smaterialshelper.setMode(TableTool.UPDATE, view);
            break;
            
        case Context.UPDATE:
            base.get("MAT_TYPE").setObligatory(true);
            base.get("MAT_GROUP").setObligatory(true);
            base.setObject(context.material);
            
            context.priceshelper.setMode(TableTool.UPDATE, view);
            context.promotionshelper.setMode(TableTool.UPDATE, view);
            context.smaterialshelper.setMode(TableTool.UPDATE, view);
            break;
            
        case Context.SHOW:
            validate.setVisible(false);
            base.setObject(context.material);
            
            context.priceshelper.setMode(TableTool.DISPLAY, view);
            context.promotionshelper.setMode(TableTool.DISPLAY, view);
            context.smaterialshelper.setMode(TableTool.DISPLAY, view);
            break;
        }
        
        view.setFocus(base.get("NAME"));
        view.setTitle(Context.TITLE[context.mode]);
    }

    /**
     * 
     * @param view
     * @param function
     */
    public static final void main(View view, Context context) {
        InputComponent input;
        Form container = new Form(view, "main");
        PageControl pagecontrol = new PageControl(container);
        DataForm form = new DataForm(container, "material");

        pagecontrol.add("back");

        form.importModel(context.materialmodel);
        for (Element element : form.getElements())
            element.setVisible(false);
        
        input = form.get("ID");
        input.setVisible(true);
        view.setFocus(input);
        input.setObligatory(!context.autocode);
        
        new Button(container, "create");
        new Button(container, "show");
        new Button(container, "update");
        
        view.setTitle("material-selection");
    }
}
