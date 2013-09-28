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
import org.iocaste.shell.common.TableTool;

public class Response {
    
    /**
     * 
     * @param view
     * @param function
     */
    public static final void form(Context context) {
        InputComponent input;
        Button validate;
        TabbedPaneItem tabitem;
        Form container = new Form(context.view, "main");
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
        ((Main)context.function).validate(input);
        
        input = base.get("MAT_GROUP");
        input.setValidator(MaterialGroupValidator.class);
        ((Main)context.function).validate(input);
        
        tabitem = new TabbedPaneItem(tabs, "basepane");
        tabitem.setContainer(base);
        
        /*
         * Prices
         */
        context.priceshelper = new TableTool(tabs, "prices");
        context.priceshelper.model(context.pricesmodel);
        context.priceshelper.setValidator(
                "VL_CUSTO", ValorCustoValidator.class, "VL_VENDA");
        context.priceshelper.setValidator(
                "DT_INICIAL", DataInicialValidator.class, "DT_FINAL");
        context.priceshelper.setVisibility(true,
                "VL_VENDA", "VL_CUSTO", "DT_INICIAL", "DT_FINAL");
        tabitem = new TabbedPaneItem(tabs, "pricespane");
        tabitem.setContainer(context.priceshelper.getContainer());
        
        /*
         * Promotion
         */
        context.promotionshelper = new TableTool(tabs, "promotions");
        context.promotionshelper.model(context.promotionsmodel);
        context.promotionshelper.setVisibility(true,
                "VL_VENDA", "VL_CUSTO", "DT_INICIAL", "DT_FINAL");
        
        tabitem = new TabbedPaneItem(tabs, "promotionspane");
        tabitem.setContainer(context.promotionshelper.getContainer());
        
        /*
         * Sub-materials
         */
        context.smaterialshelper = new TableTool(tabs, "submats");
        context.smaterialshelper.model(context.submatmodel);
        context.smaterialshelper.setVisibility(true, "SUB_MATERIAL");
            
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
            
            context.priceshelper.setMode(TableTool.UPDATE);
            context.promotionshelper.setMode(TableTool.UPDATE);
            context.smaterialshelper.setMode(TableTool.UPDATE);
            break;
            
        case Context.UPDATE:
            base.get("MAT_TYPE").setObligatory(true);
            base.get("MAT_GROUP").setObligatory(true);
            base.setObject(context.material);
            
            context.priceshelper.setMode(TableTool.UPDATE);
            context.promotionshelper.setMode(TableTool.UPDATE);
            context.smaterialshelper.setMode(TableTool.UPDATE);
            break;
            
        case Context.SHOW:
            validate.setVisible(false);
            base.setObject(context.material);
            
            context.priceshelper.setMode(TableTool.DISPLAY);
            context.promotionshelper.setMode(TableTool.DISPLAY);
            context.smaterialshelper.setMode(TableTool.DISPLAY);
            break;
        }
        
        context.view.setFocus(base.get("NAME"));
        context.view.setTitle(Context.TITLE[context.mode]);
    }

    /**
     * 
     * @param view
     * @param function
     */
    public static final void main(Context context) {
        InputComponent input;
        Form container = new Form(context.view, "main");
        PageControl pagecontrol = new PageControl(container);
        DataForm form = new DataForm(container, "material");

        pagecontrol.add("back");

        form.importModel(context.materialmodel);
        for (Element element : form.getElements())
            element.setVisible(false);
        
        input = form.get("ID");
        input.setVisible(true);
        context.view.setFocus(input);
        input.setObligatory(!context.autocode);
        
        new Button(container, "create");
        new Button(container, "show");
        new Button(container, "update");
        
        context.view.setTitle("material-selection");
    }
}
