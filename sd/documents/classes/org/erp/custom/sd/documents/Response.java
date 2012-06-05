package org.erp.custom.sd.documents;

import org.iocaste.documents.common.DocumentModel;
import org.iocaste.documents.common.Documents;
import org.iocaste.documents.common.ExtendedObject;
import org.iocaste.protocol.Function;
import org.iocaste.shell.common.Button;
import org.iocaste.shell.common.Container;
import org.iocaste.shell.common.DataForm;
import org.iocaste.shell.common.Element;
import org.iocaste.shell.common.Form;
import org.iocaste.shell.common.InputComponent;
import org.iocaste.shell.common.Table;
import org.iocaste.shell.common.ViewData;

public class Response {
    
    /**
     * 
     * @param view
     * @param function
     * @throws Exception
     */
    public static final void document(ViewData view, Function function)
            throws Exception {
        Button add, remove, save;
        Table itens;
        InputComponent receiver;
        ExtendedObject oheader = view.getParameter("header");
        ExtendedObject[] oitens = view.getParameter("itens");
        Container container = new Form(view, "main");
        DataForm header = new DataForm(container, "header");
        Documents documents = new Documents(function);
        DocumentModel model = documents.getModel("CUSTOM_SD_DOCUMENT");
        byte mode = Common.getMode(view);
        
        header.importModel(model);
        header.get("ID").setEnabled(false);
        header.get("SENDER").setVisible(false);
        
        receiver = header.get("RECEIVER");
        receiver.setObligatory(true);
        
        add = new Button(container, "add");
        remove = new Button(container, "remove");
        
        model = documents.getModel("CUSTOM_SD_DOCUMENT_ITEM");
        itens = new Table(container, "itens");
        itens.importModel(model);
        itens.getColumn("DOCUMENT_ID").setVisible(false);
        itens.setMark(true);
        
        save = new Button(container, "save");
        
        switch (mode) {
        case Common.CREATE:
            Common.insertItem(mode, itens, view, null);
            
            break;
        case Common.SHOW:
            add.setVisible(false);
            remove.setVisible(false);
            save.setVisible(false);
            itens.setMark(false);
            receiver.setObligatory(false);
            receiver.setEnabled(false);
            
            header.setObject(oheader);
            
            for (ExtendedObject oitem : oitens)
                Common.insertItem(mode, itens, view, oitem);
            
            break;
        case Common.UPDATE:
            header.setObject(oheader);
            
            for (ExtendedObject oitem : oitens)
                Common.insertItem(mode, itens, view, oitem);
            
            break;
        }
        
        view.setFocus("RECEIVER");
        view.setTitle(Common.TITLE[mode]);
        view.setNavbarActionEnabled("home", true);
        view.setNavbarActionEnabled("back", true);
    }
    
    /**
     * 
     * @param view
     * @param function
     * @throws Exception
     */
    public static final void main(ViewData view, Function function)
            throws Exception {
        Container container = new Form(view, "main");
        DataForm form = new DataForm(container, "selection");
        
        form.importModel(new Documents(function).
                getModel("CUSTOM_SD_DOCUMENT"));
        
        for (Element element : form.getElements())
            if (element.isDataStorable() && !element.getName().equals("ID"))
                element.setVisible(false);
        
        new Button(container, "display");
        new Button(container, "create");
        new Button(container, "update");
        
        view.setFocus("ID");
        view.setNavbarActionEnabled("back", true);
        view.setTitle("document-selection");
    }

}
