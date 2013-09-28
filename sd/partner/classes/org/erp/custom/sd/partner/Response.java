package org.erp.custom.sd.partner;

import org.iocaste.documents.common.DocumentModel;
import org.iocaste.documents.common.Documents;
import org.iocaste.documents.common.ExtendedObject;
import org.iocaste.shell.common.Button;
import org.iocaste.shell.common.Const;
import org.iocaste.shell.common.Container;
import org.iocaste.shell.common.DataForm;
import org.iocaste.shell.common.DataItem;
import org.iocaste.shell.common.Element;
import org.iocaste.shell.common.Form;
import org.iocaste.shell.common.Frame;
import org.iocaste.shell.common.PageControl;
import org.iocaste.shell.common.StandardContainer;
import org.iocaste.shell.common.TabbedPane;
import org.iocaste.shell.common.TabbedPaneItem;
import org.iocaste.shell.common.Table;
import org.iocaste.shell.common.View;

public class Response {

    /**
     * 
     * @param tabs
     * @param model
     * @param view
     */
    private static final void buildAddressTab(TabbedPane tabs,
            DocumentModel model, View view) {
        ItemData itemdata;
        TabbedPaneItem tab;
        Table addresses;
        DataForm address;
        Button editaddress, addaddress, removeaddress;
        Container addresscnt = new StandardContainer(tabs, "addresscnt");
        byte modo = Common.getMode(view);
        ExtendedObject[] oaddresses = view.getParameter("addresses");

        editaddress = new Button(addresscnt, "editaddress");
        addaddress = new Button(addresscnt, "addaddress");
        
        address = new DataForm(addresscnt, "address");
        address.importModel(model);
        address.get("CODIGO").setEnabled(false);
        address.get("PARTNER_ID").setVisible(false);
        address.get("TIPO_ENDERECO").setValidator(AddressTypeValidator.class);
        
        removeaddress = new Button(addresscnt, "removeaddress");
        
        addresses = new Table(addresscnt, "addresses");
        addresses.importModel(model);
        addresses.setVisible(false);
        addresses.setMark(true);
        
        Common.enableTableColumns(addresses,
                "CODIGO", "TIPO_ENDERECO", "LOGRADOURO");
        
        tab = new TabbedPaneItem(tabs, "addresstab");
        tab.setContainer(addresscnt);
        
        switch (modo) {
        case Common.CREATE:
            editaddress.setVisible(false);
            removeaddress.setVisible(false);
            
            break;
        
        case Common.SHOW:
            addresses.setMark(false);
            editaddress.setVisible(false);
            addaddress.setVisible(false);
            removeaddress.setVisible(false);

            if (oaddresses == null)
                break;
            
            itemdata = new ItemData();
            itemdata.container = addresscnt;
            itemdata.itens = addresses;
            itemdata.mark = "addressmark";
            addresses.setVisible(true);
            
            for (ExtendedObject oaddress : oaddresses) {
                itemdata.object = oaddress;
                Common.insertItem(itemdata);
            }
            
            break;
            
        case Common.UPDATE:
            if (oaddresses == null) {
                editaddress.setVisible(false);
                removeaddress.setVisible(false);
                break;
            }
            
            addresses.setMark(true);
            itemdata = new ItemData();
            itemdata.container = addresscnt;
            itemdata.itens = addresses;
            itemdata.mark = "addressmark";
            
            for (ExtendedObject oaddress : oaddresses) {
                itemdata.object = oaddress;
                Common.insertItem(itemdata);
            }

            addresses.setVisible(true);
            editaddress.setVisible(true);
            addaddress.setVisible(true);
            removeaddress.setVisible(true);
            
            break;
        }
    }
    
    /**
     * 
     * @param tabs
     * @param model
     * @param view
     * @param function
     */
    private static final void buildContactTab(TabbedPane tabs,
            DocumentModel model, Context context) {
        ItemData itemdata;
        TabbedPaneItem tab;
        DataItem dataitem;
        Table contacts, addresses, communics;
        DataForm contact;
        Button addcontact, removecontact, editcontact, addcommunic,
                removecommunic;
        Container communicscnt, contactcnt = new StandardContainer(
                tabs, "contactscnt");
        byte modo = Common.getMode(context.view);
        ExtendedObject[] ocontacts = context.view.getParameter("contacts");
        ExtendedObject[] ocommunics = context.view.getParameter("communics");
        
        /*
         * Detalhe do item
         */
        
        editcontact = new Button(contactcnt, "editcontact");
        addcontact = new Button(contactcnt, "addcontact");
        contact = new DataForm(contactcnt, "contact");
        contact.importModel(model);
        contact.get("PARTNER_ID").setVisible(false);
        
        for (Element element : contact.getElements())
            if (element.getName().equals("CODIGO"))
                element.setEnabled(false);
            else
                element.setEnabled(modo != Common.SHOW);
        
        dataitem = contact.get("ADDRESS");
        dataitem.getModelItem().setReference(null);
        dataitem.setComponentType(Const.LIST_BOX);
        
        /*
         * itens de contato
         */
        removecontact = new Button(contactcnt, "removecontact");
        contacts = new Table(contactcnt, "contacts");
        contacts.importModel(model);
        contacts.getColumn("PARTNER_ID").setVisible(false);
        contacts.setVisible(false);
        contacts.setMark(true);
        Common.enableTableColumns(contacts, "CODIGO", "PNOME", "UNOME");
        
        /*
         * itens de comunicação
         */
        communicscnt = new Frame(contactcnt, "communicscnt");
        addcommunic = new Button(communicscnt, "addcommunic");
        removecommunic = new Button(communicscnt, "removecommunic");
        removecommunic.setVisible(false);
        
        communics = new Table(communicscnt, "communics");
        communics.importModel(new Documents(context.function).
                getModel("CUSTOM_PARTNER_COMM"));
        communics.getColumn("CONTACT_ID").setVisible(false);
        communics.getColumn("PARTNER_ID").setVisible(false);
        communics.setVisible(false);
        communics.setMark(true);
        
        tab = new TabbedPaneItem(tabs, "contacttab");
        tab.setContainer(contactcnt);
        
        switch (modo) {
        case Common.CREATE:
            editcontact.setVisible(false);
            removecontact.setVisible(false);
            
            break;
            
        case Common.SHOW:
            contacts.setMark(false);
            communics.setMark(false);

            editcontact.setVisible(false);
            removecontact.setVisible(false);
            addcontact.setVisible(false);
            addcommunic.setVisible(false);
            
        case Common.UPDATE:
            if (ocontacts == null) {
                editcontact.setVisible(false);
                removecontact.setVisible(false);
                break;
            }
            
            contacts.setVisible(true);
            
            itemdata = new ItemData();
            itemdata.container = contactcnt;
            itemdata.itens = contacts;
            itemdata.mark = "contactmark";
            
            for (ExtendedObject ocontact : ocontacts) {
                itemdata.object = ocontact;
                Common.insertItem(itemdata);
            }
            
            dataitem = contact.get("ADDRESS");
            addresses = context.view.getElement("addresses");
            Common.loadListFromTable(dataitem, addresses, "LOGRADOURO",
                    "CODIGO");
            
            contacts.setVisible(true);
            addcontact.setVisible(true);
            
            if (ocommunics == null) {
                communics.setVisible(false);
                removecommunic.setVisible(false);
                break;
            }
            
            for (ExtendedObject ocommunic : ocommunics)
                Common.insertCommunic(communics, context.view, ocommunic);
            
            break;
        }
    }
    
    /**
     * 
     * @param tabs
     * @param model
     * @param view
     */
    private static final void buildIdentityTab(TabbedPane tabs,
            DocumentModel model, View view) {
        TabbedPaneItem tab;
        DataItem dataitem, partnertype = null;
        String name;
        DataForm partner = new DataForm(tabs, "identity");
        ExtendedObject object, opartner = view.getParameter("partner");
        byte modo = Common.getMode(view);
        
        partner.importModel(model);
        
        for (Element element : partner.getElements()) {
            if (!element.isDataStorable())
                continue;
            
            dataitem = (DataItem)element;
            dataitem.setEnabled(modo != Common.SHOW);
            name = dataitem.getName();
            
            if (name.equals("CODIGO")) {
                partner.get("CODIGO").setEnabled(false);
                continue;
            }
            
            if (name.equals("RAZAO_SOCIAL")) {
                view.setFocus(dataitem);
                continue;
            }
            
            if (name.equals("TIPO_PESSOA")) {
                dataitem.setComponentType(Const.LIST_BOX);
                dataitem.add("fis", 0);
                dataitem.add("jur", 1);
                continue;
            }
            
            if (name.equals("TIPO_PARCEIRO")) {
                partnertype = dataitem;
                partnertype.setValidator(PartnerTypeValidator.class);
                continue;
            }
        }
        
        tab = new TabbedPaneItem(tabs, "identitytab");
        tab.setContainer(partner);
        
        switch (modo) {
        case Common.UPDATE:
        case Common.SHOW:
            object = view.getParameter("partnertype");
            partnertype.setText((String)object.getValue("DESCRICAO"));
            partner.setObject(opartner);
            
            break;
        }
    }
    
    public static final void identity(Context context, DocumentModel[] models) {
        Form container = new Form(context.view, "main");
        PageControl pagecontrol = new PageControl(container);
        TabbedPane tabs = new TabbedPane(container, "pane");
        Button validate = new Button(container, "validate");
        Button save = new Button(container, "save");
        byte modo = Common.getMode(context.view);
        
        validate.setSubmit(true);
        
        pagecontrol.add("home");
        pagecontrol.add("back");
        
        buildIdentityTab(tabs, models[Common.IDENTITY], context.view);
        buildAddressTab(tabs, models[Common.ADDRESS], context.view);
        buildContactTab(tabs, models[Common.CONTACT], context);
        
        switch (modo) {
        case Common.SHOW:
            validate.setVisible(false);
            save.setVisible(false);
            
            break;
        }
        
        context.view.setTitle(Common.TITLE[modo]);
    }
    
    public static final void main(Context context) {
        Form container = new Form(context.view, "main");
        PageControl pagecontrol = new PageControl(container);
        DataForm form = new DataForm(container, "selection");
        DataItem partner = new DataItem(form, Const.TEXT_FIELD, "partner");
        
        pagecontrol.add("home");
        
        partner.setModelItem(new Documents(context.function).
                getModel("CUSTOM_PARTNER").getModelItem("CODIGO"));
        new Button(container, "show");
        new Button(container, "create");
        new Button(container, "update");
        
        context.view.setFocus(partner);
        context.view.setTitle("partner-selection");
    }
}
