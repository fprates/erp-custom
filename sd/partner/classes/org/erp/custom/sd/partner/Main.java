package org.erp.custom.sd.partner;

import org.iocaste.documents.common.DocumentModel;
import org.iocaste.documents.common.DocumentModelItem;
import org.iocaste.shell.common.AbstractPage;
import org.iocaste.shell.common.Button;
import org.iocaste.shell.common.Const;
import org.iocaste.shell.common.Container;
import org.iocaste.shell.common.DataForm;
import org.iocaste.shell.common.DataItem;
import org.iocaste.shell.common.Form;
import org.iocaste.shell.common.Link;
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
    
    private final void addModelItem(DocumentModel model, String name) {
        DocumentModelItem item = new DocumentModelItem();
        
        item.setName(name);
        item.setDocumentModel(model);
        item.setIndex(model.getItens().size());
        
        model.add(item);
    }
    
    public final void address(ViewData view) {
        Container container = new Form(null, "main");
        byte modo = getMode(view);
        Link link = new Link(container, "identity", "toidentity");
        DataForm header = new DataForm(container, "header");
        DocumentModel model = new DocumentModel();
        
        addModelItem(model, "codigo");
        addModelItem(model, "logradouro");
        addModelItem(model, "cep");
        addModelItem(model, "bairro");
        addModelItem(model, "cidade");
        addModelItem(model, "telefone");
        addModelItem(model, "email");
        addModelItem(model, "tipoEndereco");
        
        header.importModel(model);
        
        view.setNavbarActionEnabled("back", true);
        view.setTitle(TITLE[modo]);
        view.addContainer(container);
    }
    
    public void create(ViewData view) {
        view.export("mode", CREATE);
        view.setReloadableView(true);
        view.redirect(null, "identity");
    }
    
    private final byte getMode(ViewData view) {
        return (Byte)view.getParameter("mode");
    }
    
    public void identity(ViewData view) {
        Container container = new Form(null, "main");
        byte modo = getMode(view);
        Link link = new Link(container, "address", "toaddress");
        DataForm header = new DataForm(container, "header");
        DocumentModel model = new DocumentModel();
        
        addModelItem(model, "codigo");
        addModelItem(model, "nomeRazao");
        addModelItem(model, "nomeFantasia");
        addModelItem(model, "docFiscal");
        addModelItem(model, "insEstadual");
        addModelItem(model, "insMunicipal");
        addModelItem(model, "pessoaFJ");
        addModelItem(model, "tipoParceiro");
        
        header.importModel(model);
        
        view.setFocus("nomeRazao");
        view.setNavbarActionEnabled("back", true);
        view.setTitle(TITLE[modo]);
        view.addContainer(container);
    }
    
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
    
    public final void toaddress(ViewData view) {
        view.export("mode", getMode(view));
        view.redirect(null, "address");
        view.dontPushPage();
    }
    
    public final void toidentity(ViewData view) {
        view.export("mode", getMode(view));
        view.redirect(null, "identity");
        view.dontPushPage();
    }
    
    private final int toInteger(String value) {
        return (value.equals("")?0:Integer.parseInt(value));
    }
    
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
