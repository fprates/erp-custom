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
    
    public void create(ViewData view) {
        
    }
    
    public void identity(ViewData view) {
        Container container = new Form(null, "main");
        byte modo = (Byte)view.getParameter("mode");
        DataForm header = new DataForm(container, "header");
        DocumentModel model = new DocumentModel();
        
        addModelItem(model, "codigo");
        addModelItem(model, "nomeRazao");
        addModelItem(model, "nomeFantasia");
        addModelItem(model, "docFiscal");
        addModelItem(model, "insEstadual");
        addModelItem(model, "insMunicipal");
        addModelItem(model, "pessoaFJ");
        addModelItem(model, "codigo");
        
        header.importModel(model);
        
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
        view.export("mode", SHOW);
        view.setReloadableView(true);
        view.redirect(null, "identity");
    }
    
    public final void update(ViewData view) {
        view.export("mode", UPDATE);
        view.setReloadableView(true);
        view.redirect(null, "identity");
    }
}
