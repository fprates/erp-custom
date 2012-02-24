package org.erp.custom.sd.partner;

import org.iocaste.documents.common.DataElement;
import org.iocaste.documents.common.DataType;
import org.iocaste.documents.common.DocumentModel;
import org.iocaste.documents.common.DocumentModelItem;
import org.iocaste.documents.common.ExtendedObject;
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
     * @param model
     * @param name
     * @param type
     */
    private static final void addModelItem(DocumentModel model, String name,
            int type) {
        DataElement dataelement;
        DocumentModelItem item = new DocumentModelItem();
        
        item.setName(name);
        item.setAttributeName(name);
        item.setDocumentModel(model);
        item.setIndex(model.getItens().size());
        
        dataelement = new DataElement();
        dataelement.setType(type);
        item.setDataElement(dataelement);
        
        model.add(item);
    }

    /**
     * 
     * @param view
     */
    public static final void identity(ViewData view, DocumentModel model) {
        DataForm form;
        TabbedPaneItem tab;
        ExtendedObject identityobject = (ExtendedObject)
                view.getParameter("identityobject");
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
        
        form.importModel(model);
        
        switch (modo) {
        case Common.UPDATE:
            setFormValue(form, "CODIGO", identityobject);
            break;
        }
        
        tab = new TabbedPaneItem(tabs, "addresstab");
        form = new DataForm(tabs, "address");
        tab.setContainer(form);
        
        model = new DocumentModel();
        model.setClassName("org.erp.custom.sd.partner.common.PartnerAddress");
        
        addModelItem(model, "codigo", DataType.NUMC);
//        addModelItem(model, "logradouro", DataType.CHAR);
//        addModelItem(model, "cep", DataType.NUMC);
//        addModelItem(model, "bairro", DataType.CHAR);
//        addModelItem(model, "cidade", DataType.CHAR);
//        addModelItem(model, "telefone", DataType.CHAR);
//        addModelItem(model, "email", DataType.CHAR);
//        addModelItem(model, "tipoEndereco", DataType.NUMC);
        
        form.importModel(model);
        
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
    
    /**
     * 
     * @param form
     * @param name
     * @param object
     */
    private static final void setFormValue(DataForm form, String name,
            ExtendedObject object) {
        form.get(name).setValue((String)object.getValue(name));
    }
}
