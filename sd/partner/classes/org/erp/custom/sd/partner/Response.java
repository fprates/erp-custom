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
import org.iocaste.shell.common.TableColumn;
import org.iocaste.shell.common.ViewData;

public class Response {

    /**
     * 
     * @param view
     * @param identitymodel
     * @param addressmodel
     */
    public static final void identity(ViewData view, DocumentModel[] models) {
        ItemData itemdata;
        String name;
        DataItem dataitem;
        DataForm partner, address;
        Table contacts, addresses;
        TabbedPaneItem tab;
        Button save, editaddress, addaddress, removeaddress, addcontact,
                removecontact, editcontact;
        Container contactcnt, addresscnt, container = new Form(view, "main");
        TabbedPane tabs = new TabbedPane(container, "pane");
        byte modo = Common.getMode(view);
        ExtendedObject opartner = view.getParameter("partner");
        ExtendedObject[] oaddresses = view.getParameter("addresses");
        ExtendedObject[] ocontacts = view.getParameter("contacts");
        String[] columns = {"CODIGO", "TIPO_ENDERECO", "LOGRADOURO"};
        
        /*
         * Identity
         */
        partner = new DataForm(tabs, "identity");
        partner.importModel(models[Common.IDENTITY]);
        
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
        
        /*
         * Address
         */
        addresscnt = new StandardContainer(tabs, "addresscnt");
        address = new DataForm(addresscnt, "address");
        address.importModel(models[Common.ADDRESS]);
        address.get("CODIGO").setEnabled(false);
        address.get("PARTNER_ID").setVisible(false);
        
        addresses = new Table(addresscnt, "addresses");
        addresses.importModel(models[Common.ADDRESS]);
        addresses.setVisible(false);
        addresses.setMark(true);
        
        for (TableColumn column : addresses.getColumns()) {
            if (column.isMark())
                continue;
            
            name = column.getName();
            column.setVisible(false);
            
            for (String colname : columns) {
                if (!colname.equals(name))
                    continue;
                    
                column.setVisible(true);
                break;
            }
        }
        
        save = new Button(container, "save");
        
        editaddress = new Button(addresscnt, "editaddress");
        addaddress = new Button(addresscnt, "addaddress");
        removeaddress = new Button(addresscnt, "removeaddress");
        
        tab = new TabbedPaneItem(tabs, "addresstab");
        tab.setContainer(addresscnt);
        
        /*
         * Contacts
         */
        contactcnt = new StandardContainer(tabs, "contactscnt");
        contacts = new Table(contactcnt, "contacts");
        contacts.importModel(models[Common.CONTACT]);
        contacts.getColumn("CODIGO").setVisible(false);
        contacts.getColumn("PARTNER_ID").setVisible(false);
        contacts.setVisible(false);
        contacts.setMark(true);
        

        editcontact = new Button(contactcnt, "editcontact");
        addcontact = new Button(contactcnt, "addcontact");
        removecontact = new Button(contactcnt, "removecontact");
        
        tab = new TabbedPaneItem(tabs, "contacttab");
        tab.setContainer(contactcnt);
        
        switch (modo) {
        case Common.CREATE:
            save.setVisible(true);
            editaddress.setVisible(false);
            removeaddress.setVisible(false);
            editcontact.setVisible(false);
            removecontact.setVisible(false);
            
            break;
        
        case Common.SHOW:
            addresses.setMark(false);
            contacts.setMark(false);
            
            partner.setObject(opartner);

            if (oaddresses != null) {
                itemdata = new ItemData();
                itemdata.container = addresscnt;
                itemdata.itens = addresses;
                
                for (ExtendedObject oaddress : oaddresses) {
                    itemdata.object = oaddress;
                    Common.insertItem(modo, itemdata);
                }
            } else {
                itemdata = null;
            }
            
            if (ocontacts != null) {
                if (itemdata == null)
                    itemdata = new ItemData();
                
                itemdata.container = contactcnt;
                itemdata.itens = contacts;
                
                for (ExtendedObject ocontact : ocontacts) {
                    itemdata.object = ocontact;
                    Common.insertItem(modo, itemdata);
                }
            }
            
            save.setVisible(false);
            editaddress.setVisible(false);
            addaddress.setVisible(false);
            removeaddress.setVisible(false);
            editcontact.setVisible(false);
            addcontact.setVisible(false);
            removecontact.setVisible(false);
            
            break;
            
        case Common.UPDATE:
            addresses.setMark(true);
            contacts.setMark(true);
            
            partner.setObject(opartner);
            
            if (oaddresses != null) {
                itemdata = new ItemData();
                itemdata.container = addresscnt;
                itemdata.itens = addresses;
                
                for (ExtendedObject oaddress : oaddresses) {
                    itemdata.object = oaddress;
                    Common.insertItem(Common.SHOW, itemdata);
                }
                
                addresses.setVisible(true);
                editaddress.setVisible(true);
                addaddress.setVisible(true);
                removeaddress.setVisible(true);
            } else {
                itemdata = null;
            }
            
            if (ocontacts != null) {
                if (itemdata == null)
                    itemdata = new ItemData();
                
                itemdata.container = contactcnt;
                itemdata.itens = contacts;
                
                for (ExtendedObject ocontact : ocontacts) {
                    itemdata.object = ocontact;
                    Common.insertItem(modo, itemdata);
                }
            }
            
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
