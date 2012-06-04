package org.erp.custom.sd.partner;

import org.iocaste.documents.common.DocumentModel;
import org.iocaste.documents.common.Documents;
import org.iocaste.documents.common.ExtendedObject;
import org.iocaste.protocol.Function;
import org.iocaste.shell.common.Button;
import org.iocaste.shell.common.Const;
import org.iocaste.shell.common.Container;
import org.iocaste.shell.common.DataForm;
import org.iocaste.shell.common.DataItem;
import org.iocaste.shell.common.Element;
import org.iocaste.shell.common.Form;
import org.iocaste.shell.common.Frame;
import org.iocaste.shell.common.StandardContainer;
import org.iocaste.shell.common.TabbedPane;
import org.iocaste.shell.common.TabbedPaneItem;
import org.iocaste.shell.common.Table;
import org.iocaste.shell.common.ViewData;

public class Response {

    /**
     * 
     * @param tabs
     * @param model
     * @param view
     */
    private static final void buildAddressTab(TabbedPane tabs,
            DocumentModel model, ViewData view) {
        ItemData itemdata;
        TabbedPaneItem tab;
        Table addresses;
        Button editaddress, addaddress, removeaddress;
        Container addresscnt = new StandardContainer(tabs, "addresscnt");
        DataForm address = new DataForm(addresscnt, "address");
        byte modo = Common.getMode(view);
        ExtendedObject[] oaddresses = view.getParameter("addresses");
        
        address.importModel(model);
        address.get("CODIGO").setEnabled(false);
        address.get("PARTNER_ID").setVisible(false);
        
        editaddress = new Button(addresscnt, "editaddress");
        addaddress = new Button(addresscnt, "addaddress");
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
            if (oaddresses == null)
                break;
            
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
     * @throws Exception
     */
    private static final void buildContactTab(TabbedPane tabs,
            DocumentModel model, ViewData view, Function function)
                    throws Exception {
        ItemData itemdata;
        TabbedPaneItem tab;
        DataItem dataitem;
        Table contacts, addresses, communics;
        Button addcontact, removecontact, editcontact, addcommunic,
                removecommunic;
        Container communicscnt, contactcnt = new StandardContainer(
                tabs, "contactscnt");
        DataForm contact = new DataForm(contactcnt, "contact");
        byte modo = Common.getMode(view);
        ExtendedObject[] ocontacts = view.getParameter("contacts");
        ExtendedObject[] ocommunics = view.getParameter("communics");
        
        /*
         * Detalhe do item
         */
        contact.importModel(model);
        contact.get("PARTNER_ID").setVisible(false);
        
        for (Element element : contact.getElements())
            if (element.getName().equals("CODIGO"))
                element.setEnabled(false);
            else
                element.setEnabled((modo == Common.SHOW)? false : true);
        
        dataitem = contact.get("ADDRESS");
        dataitem.getModelItem().setReference(null);
        dataitem.setComponentType(Const.LIST_BOX);
        
        editcontact = new Button(contactcnt, "editcontact");
        addcontact = new Button(contactcnt, "addcontact");
        
        /*
         * itens de contato
         */
        removecontact = new Button(contactcnt, "removecontact");
        
        contacts = new Table(contactcnt, "contacts");
        contacts.importModel(model);
        contacts.getColumn("PARTNER_ID").setVisible(false);
        contacts.setVisible(false);

        Common.enableTableColumns(contacts, "CODIGO", "PNOME", "UNOME");
        
        /*
         * itens de comunicação
         */
        communicscnt = new Frame(contactcnt, "communicscnt");
        addcommunic = new Button(communicscnt, "addcommunic");
        removecommunic = new Button(communicscnt, "removecommunic");
        removecommunic.setVisible(false);
        
        communics = new Table(communicscnt, "communics");
        communics.importModel(new Documents(function).
                getModel("CUSTOM_PARTNER_COMM"));
        communics.getColumn("CONTACT_ID").setVisible(false);
        communics.getColumn("PARTNER_ID").setVisible(false);
        communics.setVisible(false);
        
        tab = new TabbedPaneItem(tabs, "contacttab");
        tab.setContainer(contactcnt);
        
        switch (modo) {
        case Common.CREATE:
            editcontact.setVisible(false);
            removecontact.setVisible(false);
            communics.setMark(true);
            
            break;
        
        case Common.SHOW:
            contacts.setMark(false);
            communics.setMark(false);
            
            editcontact.setVisible(false);
            addcontact.setVisible(false);
            removecontact.setVisible(false);
            addcommunic.setVisible(false);
            
            if (ocontacts == null)
                break;
            
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
            addresses = view.getElement("addresses");
            Common.loadListFromTable(dataitem, addresses, "LOGRADOURO",
                    "CODIGO");
            
            if (ocommunics == null)
                break;
            
            for (ExtendedObject ocommunic : ocommunics)
                Common.insertCommunic(communics, view, ocommunic);
            
            break;
            
        case Common.UPDATE:
            contacts.setMark(true);
            communics.setMark(true);
            
            if (ocontacts == null)
                break;
            
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
            addresses = view.getElement("addresses");
            Common.loadListFromTable(dataitem, addresses, "LOGRADOURO",
                    "CODIGO");
            
            contacts.setVisible(true);
            editcontact.setVisible(true);
            addcontact.setVisible(true);
            removecontact.setVisible(true);
            
            if (ocommunics == null)
                break;
            
            for (ExtendedObject ocommunic : ocommunics)
                Common.insertCommunic(communics, view, ocommunic);
            
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
            DocumentModel model, ViewData view) {
        TabbedPaneItem tab;
        DataItem dataitem;
        String name;
        DataForm partner = new DataForm(tabs, "identity");
        ExtendedObject opartner = view.getParameter("partner");
        byte modo = Common.getMode(view);
        
        partner.importModel(model);
        
        for (Element element : partner.getElements()) {
            if (!element.isDataStorable())
                continue;
            
            dataitem = (DataItem)element;
            name = dataitem.getName();
            
            if (name.equals("CODIGO")) {
                partner.get("CODIGO").setEnabled(false);
                continue;
            }
            
            if (name.equals("NOME_RAZAO"))
                view.setFocus(name);
            
            if (name.equals("TIPO_PESSOA")) {
                dataitem.setComponentType(Const.LIST_BOX);
                dataitem.add("fis", 0);
                dataitem.add("jur", 1);
            }
            
            dataitem.setEnabled((modo == Common.SHOW)? false : true);
        }
        
        tab = new TabbedPaneItem(tabs, "identitytab");
        tab.setContainer(partner);
        
        switch (modo) {
        case Common.UPDATE:
        case Common.SHOW:
            partner.setObject(opartner);
            
            break;
        }
    }
    
    /**
     * 
     * @param view
     * @param models
     * @param function
     * @throws Exception
     */
    public static final void identity(ViewData view, DocumentModel[] models,
            Function function) throws Exception {
        Container container = new Form(view, "main");
        TabbedPane tabs = new TabbedPane(container, "pane");
        Button save = new Button(container, "save");
        byte modo = Common.getMode(view);
        
        /*
         * Identity
         */
        buildIdentityTab(tabs, models[Common.IDENTITY], view);
        
        /*
         * Address
         */
        buildAddressTab(tabs, models[Common.ADDRESS], view);
        
        /*
         * Contacts
         */
        buildContactTab(tabs, models[Common.CONTACT], view, function);
        
        switch (modo) {
        case Common.UPDATE:
        case Common.CREATE:
            save.setVisible(true);
            
            break;
        
        case Common.SHOW:
            save.setVisible(false);
            
            break;
        }
        
        view.setNavbarActionEnabled("back", true);
        view.setTitle(Common.TITLE[modo]);
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
        DataItem partner = new DataItem(form, Const.TEXT_FIELD, "partner");
        
        partner.setModelItem(new Documents(function).getModel("CUSTOM_PARTNER").
                getModelItem("CODIGO"));
        new Button(container, "show");
        new Button(container, "create");
        new Button(container, "update");
        
        view.setFocus("partner");
        view.setNavbarActionEnabled("back", true);
        view.setTitle("partner-selection");
    }
}
