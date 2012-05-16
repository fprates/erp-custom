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
import org.iocaste.shell.common.StandardContainer;
import org.iocaste.shell.common.TabbedPane;
import org.iocaste.shell.common.TabbedPaneItem;
import org.iocaste.shell.common.Table;
import org.iocaste.shell.common.ViewData;

public class Response {

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

            if (oaddresses != null) {
                itemdata = new ItemData();
                itemdata.container = addresscnt;
                itemdata.itens = addresses;
                itemdata.mark = "addressmark";
                addresses.setVisible(true);
                
                for (ExtendedObject oaddress : oaddresses) {
                    itemdata.object = oaddress;
                    Common.insertItem(itemdata);
                }
            }
            
            editaddress.setVisible(false);
            addaddress.setVisible(false);
            removeaddress.setVisible(false);
            
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
    
    private static final void buildContactTab(TabbedPane tabs,
            DocumentModel model, ViewData view) {
        ItemData itemdata;
        TabbedPaneItem tab;
        DataItem dataitem;
        Table contacts, addresses;
        Button addcontact, removecontact, editcontact;
        Container contactcnt = new StandardContainer(tabs, "contactscnt");
        DataForm contact = new DataForm(contactcnt, "contact");
        byte modo = Common.getMode(view);
        ExtendedObject[] ocontacts = view.getParameter("contacts");
        
        contact.importModel(model);
        contact.get("CODIGO").setEnabled(false);
        contact.get("PARTNER_ID").setVisible(false);
        
        dataitem = contact.get("ADDRESS");
        dataitem.getModelItem().setReference(null);
        dataitem.setComponentType(Const.LIST_BOX);
//
//        contactCommunication = new StandardContainer(
//                contactcnt, "contact.communic");
        
        editcontact = new Button(contactcnt, "editcontact");
        addcontact = new Button(contactcnt, "addcontact");
        removecontact = new Button(contactcnt, "removecontact");
        
        contacts = new Table(contactcnt, "contacts");
        contacts.importModel(model);
        contacts.getColumn("PARTNER_ID").setVisible(false);
        contacts.setVisible(false);
        contacts.setMark(true);

        Common.enableTableColumns(contacts, "CODIGO", "PNOME", "UNOME");
        
        tab = new TabbedPaneItem(tabs, "contacttab");
        tab.setContainer(contactcnt);
        
        switch (modo) {
        case Common.CREATE:
            editcontact.setVisible(false);
            removecontact.setVisible(false);
            
            break;
        
        case Common.SHOW:
            contacts.setMark(false);
            
            if (ocontacts != null) {
                itemdata = new ItemData();
                itemdata.container = contactcnt;
                itemdata.itens = contacts;
                itemdata.mark = "contactmark";
                contacts.setVisible(true);
                
                for (ExtendedObject ocontact : ocontacts) {
                    itemdata.object = ocontact;
                    Common.insertItem(itemdata);
                }
                
                dataitem = contact.get("ADDRESS");
                addresses = view.getElement("addresses");
                Common.loadListFromTable(dataitem, addresses, "LOGRADOURO",
                        "CODIGO");
            }
            
            editcontact.setVisible(false);
            addcontact.setVisible(false);
            removecontact.setVisible(false);
            
            break;
            
        case Common.UPDATE:
            contacts.setMark(true);
            
            if (ocontacts != null) {
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
            }
            
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
     * @param identitymodel
     * @param addressmodel
     */
    public static final void identity(ViewData view, DocumentModel[] models) {
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
        buildContactTab(tabs, models[Common.CONTACT], view);
        
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
