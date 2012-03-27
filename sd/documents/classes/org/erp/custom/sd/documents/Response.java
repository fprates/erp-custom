package org.erp.custom.sd.documents;

import org.iocaste.documents.common.DocumentModel;
import org.iocaste.documents.common.Documents;
import org.iocaste.documents.common.ExtendedObject;
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
    
    /**
     * 
     * @param view
     * @param function
     * @throws Exception
     */
    public static final void document(ViewData view, Function function)
            throws Exception {
        ExtendedObject oheader = view.getParameter("header");
        ExtendedObject[] oitens = view.getParameter("itens");
        Container container = new Form(view, "main");
        DataForm header = new DataForm(container, "header");
        Table itens = new Table(container, "itens");
        Documents documents = new Documents(function);
        DocumentModel model = documents.getModel("CUSTOM_SD_DOCUMENT");
        byte mode = Common.getMode(view);
        
        header.importModel(model);
        header.get("ID").setEnabled(false);
         
        model = documents.getModel("CUSTOM_SD_DOCUMENT_ITEM");
        itens.importModel(model);
        itens.getColumn("DOCUMENT_ID").setVisible(false);
        itens.setMark(true);
        
        switch (mode) {
        case Common.CREATE:
            header.get("SENDER").setObligatory(true);
            header.get("RECEIVER").setObligatory(true);
            
            Common.insertItem(mode, itens, null, null);
            
            new Button(container, "save");
            new Button(container, "add");
            new Button(container, "remove");
            
            break;
        case Common.SHOW:
            header.get("SENDER").setEnabled(false);
            header.get("RECEIVER").setEnabled(false);
            header.setObject(oheader);
            
            itens.setMark(false);
            
            for (ExtendedObject oitem : oitens)
                Common.insertItem(mode, itens, view, oitem);
            
            break;
        case Common.UPDATE:
            header.get("SENDER").setObligatory(true);
            header.get("RECEIVER").setObligatory(true);
            header.setObject(oheader);
            
            for (ExtendedObject oitem : oitens)
                Common.insertItem(mode, itens, view, oitem);

            new Button(container, "save");
            new Button(container, "add");
            new Button(container, "remove");
            
            break;
        }
        
        view.setFocus("SENDER");
        view.setTitle(Common.TITLE[mode]);
        view.setNavbarActionEnabled("back", true);
    }
    
    /**
     * 
     * @param view
     */
    public static final void main(ViewData view) {
        Container container = new Form(view, "main");
        DataForm form = new DataForm(container, "selection");
        
        new DataItem(form, Const.TEXT_FIELD, "document");
        new Button(container, "display");
        new Button(container, "create");
        new Button(container, "update");
        
        view.setFocus("document");
        view.setNavbarActionEnabled("back", true);
        view.setTitle("document-selection");
    }

}
