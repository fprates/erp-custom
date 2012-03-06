package org.erp.custom.sd.documents;

import org.iocaste.documents.common.DocumentModel;
import org.iocaste.documents.common.Documents;
import org.iocaste.protocol.Function;
import org.iocaste.shell.common.Button;
import org.iocaste.shell.common.Const;
import org.iocaste.shell.common.Container;
import org.iocaste.shell.common.DataForm;
import org.iocaste.shell.common.DataItem;
import org.iocaste.shell.common.Form;
import org.iocaste.shell.common.Table;
import org.iocaste.shell.common.ViewData;

public class Response {
    public static final void document(ViewData view, Function function)
            throws Exception {
        Container container = new Form(null, "main");
        DataForm header = new DataForm(container, "header");
        Table itens = new Table(container, "itens");
        Documents documents = new Documents(function);
        DocumentModel model = documents.getModel("CUSTOM_DOCUMENT");
        byte mode = Common.getMode(view);
        
        header.importModel(model);
        header.get("ID").setEnabled(false);
        
        model = documents.getModel("CUSTOM_DOCUMENT_ITEM");
        itens.importModel(model);
        itens.getColumn("DOCUMENT_ID").setVisible(false);
        itens.setMark(true);
        
        Common.insertItem(itens);
        
        if (mode != Common.SHOW) {
            new Button(container, "save");
            new Button(container, "add");
            new Button(container, "remove");
        }
        
        view.setTitle(Common.TITLE[mode]);
        view.setNavbarActionEnabled("back", true);
        view.addContainer(container);
    }
    
    public static final void main(ViewData view) {
        Container container = new Form(null, "main");
        DataForm form = new DataForm(container, "selection");
        
        new DataItem(form, Const.TEXT_FIELD, "document");
        new Button(container, "display");
        new Button(container, "create");
        new Button(container, "update");
        
        view.setFocus("document");
        view.setNavbarActionEnabled("back", true);
        view.setTitle("document-selection");
        view.addContainer(container);
    }

}
