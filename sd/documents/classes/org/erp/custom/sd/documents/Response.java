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
import org.iocaste.shell.common.PageControl;
import org.iocaste.shell.common.Table;
import org.iocaste.shell.common.View;

public class Response {
    
    public static final void condform(View view, Function function)
            throws Exception {
        Button condadd, condremove, condapply;
        Table conditions;
        Container container = new Form(view, "main");
        Documents documents = new Documents(function);
        ExtendedObject[] oconditions = view.getParameter("conditions");
        byte mode = Common.getMode(view);
        
        condadd = new Button(container, "condadd");
        condremove = new Button(container, "condremove");
        
        conditions = new Table(container, "conditions");
        conditions.setMark(true);
        conditions.importModel(documents.getModel("CUSTOM_SD_CONDITIONS"));
        conditions.getColumn("DOCUMENT").setVisible(false);
        
        condapply = new Button(container, "condapply");
        new Button(container, "condcancel");
        
        switch (mode) {
        case Common.UPDATE:
        case Common.CREATE:
            if (oconditions != null) {
                for (ExtendedObject ocondition : oconditions)
                    Common.insertCondition(conditions, ocondition, view);
            } else {
                conditions.setVisible(false);
                condadd.setVisible(true);
                condremove.setVisible(false);
                condapply.setVisible(false);
            }
            
            break;
            
        case Common.SHOW:
            condadd.setVisible(false);
            condremove.setVisible(false);
            condapply.setVisible(false);
            
            if (oconditions != null)
                for (ExtendedObject ocondition : oconditions)
                    Common.insertCondition(conditions, ocondition, view);
            else
                conditions.setVisible(false);
            
            break;
        }
        
        view.setTitle(Common.TITLE[Common.CONDITIONS]);
    }
    
    /**
     * 
     * @param view
     * @param function
     * @throws Exception
     */
    public static final void document(View view, Function function)
            throws Exception {
        Button add, remove, save, validate;
        Table itens;
        InputComponent receiver;
        DataForm header;
        ExtendedObject oheader = view.getParameter("header");
        ExtendedObject[] oitens = view.getParameter("itens");
        Form container = new Form(view, "main");
        PageControl pagecontrol = new PageControl(container);
        Documents documents = new Documents(function);
        DocumentModel model = documents.getModel("CUSTOM_SD_DOCUMENT");
        byte mode = Common.getMode(view);

        pagecontrol.add("home");
        pagecontrol.add("back");
        
        new Button(container, "conditions");
        
        header = new DataForm(container, "header");
        header.importModel(model);
        header.get("ID").setEnabled(false);
        header.setColumns((byte)2);
        header.addLine("ID", "TIPO");
        header.addLine("RECEIVER", "VALOR");
        header.addLine("DATA_CRIACAO", null);
        
        receiver = header.get("RECEIVER");
        receiver.setObligatory(true);
        receiver.setValidator(PartnerValidator.class);
        
        add = new Button(container, "add");
        remove = new Button(container, "remove");
        
        model = documents.getModel("CUSTOM_SD_DOCUMENT_ITEM");
        itens = new Table(container, "itens");
        itens.importModel(model);
        itens.getColumn("DOCUMENT_ID").setVisible(false);
        itens.setMark(true);
        
        validate = new Button(container, "validate");
        validate.setSubmit(true);
        
        save = new Button(container, "save");
        
        switch (mode) {
        case Common.CREATE:
            Common.insertItem(itens, view, null);
            
            break;
        case Common.SHOW:
            add.setVisible(false);
            remove.setVisible(false);
            save.setVisible(false);
            validate.setVisible(false);
            itens.setMark(false);
            receiver.setObligatory(false);
            receiver.setEnabled(false);
            
            header.setObject(oheader);
            
            for (ExtendedObject oitem : oitens)
                Common.insertItem(itens, view, oitem);
            
            break;
        case Common.UPDATE:
            header.setObject(oheader);
            
            for (ExtendedObject oitem : oitens)
                Common.insertItem(itens, view, oitem);
            
            break;
        }
        
        view.setFocus("RECEIVER");
        view.setTitle(Common.TITLE[mode]);
    }
    
    /**
     * 
     * @param view
     * @param function
     * @throws Exception
     */
    public static final void main(View view, Function function)
            throws Exception {
        Form container = new Form(view, "main");
        PageControl pagecontrol = new PageControl(container);
        DataForm form = new DataForm(container, "selection");
        
        pagecontrol.add("back");
        form.importModel(new Documents(function).
                getModel("CUSTOM_SD_DOCUMENT"));
        
        for (Element element : form.getElements())
            if (element.isDataStorable() && !element.getName().equals("ID"))
                element.setVisible(false);
        
        new Button(container, "display");
        new Button(container, "create");
        new Button(container, "update");

        view.setFocus("ID");
        view.setTitle("document-selection");
    }

}
