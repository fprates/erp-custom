package org.erp.custom.sd.partner;

import org.erp.custom.sd.partner.common.Partner;
import org.erp.custom.sd.partner.common.PartnerAddress;
import org.iocaste.documents.common.DataElement;
import org.iocaste.documents.common.DataType;
import org.iocaste.documents.common.DocumentModel;
import org.iocaste.documents.common.DocumentModelItem;
import org.iocaste.shell.common.AbstractPage;
import org.iocaste.shell.common.Button;
import org.iocaste.shell.common.Const;
import org.iocaste.shell.common.Container;
import org.iocaste.shell.common.DataForm;
import org.iocaste.shell.common.DataItem;
import org.iocaste.shell.common.Form;
import org.iocaste.shell.common.TabbedPane;
import org.iocaste.shell.common.TabbedPaneItem;
import org.iocaste.shell.common.ViewData;


public class Main extends AbstractPage {
    private final static byte SHOW = 0;
    private final static byte CREATE = 1;
    private final static byte UPDATE = 2;
    
    private String[] TITLE = {
            "partner-display",
            "partner-create",
            "partner-update"
    };
    
    private final void addModelItem(DocumentModel model, String name,
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
    
    public void create(ViewData view) {
        view.export("mode", CREATE);
        view.setReloadableView(true);
        view.redirect(null, "identity");
    }
    
    private final byte getMode(ViewData view) {
        return (Byte)view.getParameter("mode");
    }
    
    /**
     * 
     * @param view
     */
    public void identity(ViewData view) {
        DataForm form;
        TabbedPaneItem tab;
        Container container = new Form(null, "main");
        TabbedPane tabs = new TabbedPane(container, "pane");
        byte modo = getMode(view);
        DocumentModel model;

        tab = new TabbedPaneItem(tabs, "identitytab");
        form = new DataForm(tabs, "identity");
        tab.setContainer(form);
        
        model = new DocumentModel();
        model.setClassName("org.erp.custom.sd.partner.common.Partner");
        
        addModelItem(model, "codigo", DataType.NUMC);
        addModelItem(model, "nomeRazao", DataType.CHAR);
        addModelItem(model, "nomeFantasia", DataType.CHAR);
        addModelItem(model, "docFiscal", DataType.CHAR);
        addModelItem(model, "insEstadual", DataType.CHAR);
        addModelItem(model, "insMunicipal", DataType.CHAR);
        addModelItem(model, "pessoaFJ", DataType.NUMC);
        addModelItem(model, "tipoParceiro", DataType.NUMC);
        
        form.importModel(model);
        
        tab = new TabbedPaneItem(tabs, "addresstab");
        form = new DataForm(tabs, "address");
        tab.setContainer(form);
        
        model = new DocumentModel();
        model.setClassName("org.erp.custom.sd.partner.common.PartnerAddress");
        
        addModelItem(model, "codigo", DataType.NUMC);
        addModelItem(model, "logradouro", DataType.CHAR);
        addModelItem(model, "cep", DataType.NUMC);
        addModelItem(model, "bairro", DataType.CHAR);
        addModelItem(model, "cidade", DataType.CHAR);
        addModelItem(model, "telefone", DataType.CHAR);
        addModelItem(model, "email", DataType.CHAR);
        addModelItem(model, "tipoEndereco", DataType.NUMC);
        
        form.importModel(model);
        
        switch (modo) {
        case CREATE:
        case UPDATE:
            new Button(container, "save");
            break;
        }
        
        view.setFocus("nomeRazao");
        view.setNavbarActionEnabled("back", true);
        view.setTitle(TITLE[modo]);
        view.addContainer(container);
    }
    
    /**
     * 
     * @param view
     */
    public void main(ViewData view) {
        Container container = new Form(null, "main");
        DataForm form = new DataForm(container, "selection");
        
        new DataItem(form, Const.TEXT_FIELD, "partner");
        new Button(container, "show");
        new Button(container, "create");
        new Button(container, "update");
        
        view.setNavbarActionEnabled("back", true);
        view.setTitle("partner-selection");
        view.addContainer(container);
    }
    
    /**
     * 
     * @param view
     */
    public final void save(ViewData view) throws Exception {
        PartnerAddress address;
        TabbedPane tpane = (TabbedPane)view.getElement("pane");
        DataForm identityform = (DataForm)tpane.get("identitytab").
                getContainer();
        DataForm addressform = (DataForm)tpane.get("addresstab").getContainer();
        Partner partner = (Partner)identityform.getObject().newLooseInstance();
        Partners partners = new Partners();
        byte modo = getMode(view);
        
        address = (PartnerAddress)addressform.getObject().newLooseInstance();
        address.setPartner(partner);
        
        partner.add(address);
        
        switch (modo) {
        case CREATE:
            partners.save(partner);
            
            view.setReloadableView(true);
            view.export("mode", UPDATE);
            break;
        case UPDATE:
            partners.update(partner);
            break;
        }
        
        view.message(Const.STATUS, "partner.saved.successfuly");
    }
    
    /**
     * 
     * @param view
     */
    public final void show(ViewData view) {
        DataForm form = (DataForm)view.getElement("selection");
        int ident = toInteger(form.getValue("partner"));
        
        if (ident == 0) {
            view.message(Const.ERROR, "field.is.required");
            return;
        }
        
        view.export("mode", SHOW);
        view.setReloadableView(true);
        view.redirect(null, "identity");
    }
    
    /**
     * 
     * @param view
     */
    public final void toaddress(ViewData view) {
        view.export("mode", getMode(view));
        view.redirect(null, "address");
        view.dontPushPage();
    }
    
    /**
     * 
     * @param view
     */
    public final void toidentity(ViewData view) {
        view.export("mode", getMode(view));
        view.redirect(null, "identity");
        view.dontPushPage();
    }
    
    /**
     * 
     * @param value
     * @return
     */
    private final int toInteger(String value) {
        return (value.equals("")?0:Integer.parseInt(value));
    }
    
    /**
     * 
     * @param view
     */
    public final void update(ViewData view) {
        DataForm form = (DataForm)view.getElement("selection");
        int ident = toInteger(form.getValue("partner"));
        
        if (ident == 0) {
            view.message(Const.ERROR, "field.is.required");
            return;
        }
        
        view.export("mode", UPDATE);
        view.setReloadableView(true);
        view.redirect(null, "identity");
    }
}
