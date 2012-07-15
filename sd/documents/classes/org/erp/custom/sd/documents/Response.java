package org.erp.custom.sd.documents;

import java.util.Calendar;

import org.iocaste.documents.common.DocumentModel;
import org.iocaste.documents.common.Documents;
import org.iocaste.documents.common.ExtendedObject;
import org.iocaste.protocol.Function;
import org.iocaste.shell.common.Button;
import org.iocaste.shell.common.DataForm;
import org.iocaste.shell.common.Element;
import org.iocaste.shell.common.Form;
import org.iocaste.shell.common.InputComponent;
import org.iocaste.shell.common.PageControl;
import org.iocaste.shell.common.Table;
import org.iocaste.shell.common.View;

public class Response {
    
    public static final void condform(View view, Function function) {
        Button validate, condadd, condremove, condapply;
        Table conditions;
        Form container = new Form(view, "main");
        Documents documents = new Documents(function);
        ExtendedObject[] oconditions = view.getParameter("conditions");
        byte mode = Common.getMode(view);

        new PageControl(container);
        
        condadd = new Button(container, "condadd");
        condremove = new Button(container, "condremove");
        
        conditions = new Table(container, "conditions");
        conditions.setMark(true);
        conditions.importModel(documents.getModel("CUSTOM_SD_CONDITIONS"));
        conditions.getColumn("DOCUMENT").setVisible(false);
        
        validate = new Button(container, "validatecond");
        validate.setSubmit(true);
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
                validate.setVisible(false);
            }
            
            break;
            
        case Common.SHOW:
            condadd.setVisible(false);
            condremove.setVisible(false);
            condapply.setVisible(false);
            validate.setVisible(false);
            
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
     */
    public static final void document(View view, Function function) {
        Calendar calendar;
        Button add, remove, save, validate;
        Table itens;
        InputComponent receiver, tipo;
        DataForm header;
        ExtendedObject doctype, partner, oheader = view.getParameter("header");
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
        
        tipo = header.get("TIPO");
        tipo.setValidator(DocTypeValidator.class);
        tipo.setObligatory(true);
        
        header.get("VALOR").setEnabled(false);
        
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
            calendar = Calendar.getInstance(view.getLocale());
            header.get("DATA_CRIACAO").set(calendar.getTime());
            
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
            tipo.setObligatory(false);
            tipo.setEnabled(false);
            
        case Common.UPDATE:
            partner = view.getParameter("partner");
            receiver.setText((String)partner.getValue("RAZAO_SOCIAL"));
            
            doctype = view.getParameter("doctype");
            tipo.setText((String)doctype.getValue("TEXT"));
            
            header.setObject(oheader);
            
            for (ExtendedObject oitem : oitens)
                Common.insertItem(itens, view, oitem);
            
            break;
        }

        view.setFocus(receiver);
        view.setTitle(Common.TITLE[mode]);
    }
    
    /**
     * 
     * @param view
     * @param function
     */
    public static final void main(View view, Function function) {
        Form container = new Form(view, "main");
        PageControl pagecontrol = new PageControl(container);
        DataForm form = new DataForm(container, "selection");
        
        pagecontrol.add("home");
        form.importModel(new Documents(function).
                getModel("CUSTOM_SD_DOCUMENT"));
        
        for (Element element : form.getElements())
            if (element.getName().equals("ID"))
                view.setFocus(element);
            else
                if (element.isDataStorable())
                    element.setVisible(false);
        
        new Button(container, "display");
        new Button(container, "create");
        new Button(container, "update");

        view.setTitle("document-selection");
    }

}
