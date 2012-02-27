package org.erp.custom.sd.partner;

import org.iocaste.documents.common.DocumentModel;
import org.iocaste.shell.common.Button;
import org.iocaste.shell.common.Const;
import org.iocaste.shell.common.Container;
import org.iocaste.shell.common.DataForm;
import org.iocaste.shell.common.DataItem;
import org.iocaste.shell.common.Form;
import org.iocaste.shell.common.TabbedPane;
import org.iocaste.shell.common.TabbedPaneItem;
import org.iocaste.shell.common.ViewData;

public class Response {

    /**
     * 
     * @param view
     * @param identitymodel
     * @param addressmodel
     */
    public static final void identity(ViewData view,
            DocumentModel identitymodel, DocumentModel addressmodel) {
        DataForm form;
        TabbedPaneItem tab;
        Container container = new Form(null, "main");
        TabbedPane tabs = new TabbedPane(container, "pane");
        byte modo = Common.getMode(view);

        tab = new TabbedPaneItem(tabs, "identitytab");
        form = new DataForm(tabs, "identity");
        tab.setContainer(form);
        
//        addModelItem(model, "codigo", DataType.NUMC);
//        addModelItem(model, "nomeRazao", DataType.CHAR);
//        addModelItem(model, "nomeFantasia", DataType.CHAR);
//        addModelItem(model, "docFiscal", DataType.CHAR);
//        addModelItem(model, "insEstadual", DataType.CHAR);
//        addModelItem(model, "insMunicipal", DataType.CHAR);
//        addModelItem(model, "pessoaFJ", DataType.NUMC);
//        addModelItem(model, "tipoParceiro", DataType.NUMC);
        
        form.importModel(identitymodel);
        
        tab = new TabbedPaneItem(tabs, "addresstab");
        form = new DataForm(tabs, "address");
        tab.setContainer(form);
        
//        addModelItem(model, "codigo", DataType.NUMC);
//        addModelItem(model, "logradouro", DataType.CHAR);
//        addModelItem(model, "cep", DataType.NUMC);
//        addModelItem(model, "bairro", DataType.CHAR);
//        addModelItem(model, "cidade", DataType.CHAR);
//        addModelItem(model, "telefone", DataType.CHAR);
//        addModelItem(model, "email", DataType.CHAR);
//        addModelItem(model, "tipoEndereco", DataType.NUMC);
        
        form.importModel(addressmodel);
        
        switch (modo) {
        case Common.CREATE:
        case Common.UPDATE:
            new Button(container, "save");
            break;
        }
        
        view.setFocus("nomeRazao");
        view.setNavbarActionEnabled("back", true);
        view.setTitle(Common.TITLE[modo]);
        view.addContainer(container);
    }
    
    /**
     * 
     * @param view
     */
    public static final void main(ViewData view) {
        Container container = new Form(null, "main");
        DataForm form = new DataForm(container, "selection");
        
        new DataItem(form, Const.TEXT_FIELD, "partner");
        new Button(container, "show");
        new Button(container, "create");
        new Button(container, "update");
        
        view.setFocus("partner");
        view.setNavbarActionEnabled("back", true);
        view.setTitle("partner-selection");
        view.addContainer(container);
    }
}
