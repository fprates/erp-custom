package org.erp.custom.sd.partner;

import org.iocaste.documents.common.DocumentModel;
import org.iocaste.documents.common.Documents;
import org.iocaste.documents.common.ExtendedObject;
import org.iocaste.packagetool.common.InstallData;
import org.iocaste.protocol.Message;
import org.iocaste.shell.common.AbstractPage;
import org.iocaste.shell.common.Button;
import org.iocaste.shell.common.Const;
import org.iocaste.shell.common.Container;
import org.iocaste.shell.common.DataForm;
import org.iocaste.shell.common.DataItem;
import org.iocaste.shell.common.InputComponent;
import org.iocaste.shell.common.Table;
import org.iocaste.shell.common.TableItem;
import org.iocaste.shell.common.View;


public class Main extends AbstractPage {
    private static final boolean VISIBLE = true;
    
    public Main() {
        export("install", "install");
    }
    
    /**
     * 
     * @param view
     */
    public final void addaddress(View view) throws Exception {
        Button button;
        ItemData itemdata = new ItemData();
        DataForm identity = view.getElement("identity");
        DataForm address = view.getElement("address");
        Table itens = view.getElement("addresses");
        
        if (itens.length() == 0) {
            itens.setVisible(true);
            
            button = view.getElement("removeaddress");
            button.setVisible(true);
            button = view.getElement("editaddress");
            button.setVisible(true);
        }

        itemdata.view = view;
        itemdata.itens = itens;
        itemdata.object = address.getObject();
        itemdata.partner = identity.get("CODIGO").get();
        itemdata.container = view.getElement("addresscnt");
        itemdata.mark = "addressmark";
        itemdata.object.setValue("CODIGO", 0l);
        Common.insertItem(itemdata);
        
        updatePartnerView(view);
        
        address.clearInputs();
    }
    
    /**
     * 
     * @param view
     */
    public final void addcommunic(View view) {
        Table communics = view.getElement("communics");
        Button removecommunic = view.getElement("removecommunic");
        
        communics.setVisible(true);
        removecommunic.setVisible(true);
        
        Common.insertCommunic(communics, view, null);
    }
    
    /**
     * 
     * @param view
     * @throws Exception
     */
    public final void addcontact(View view) throws Exception {
        Button button;
        ItemData itemdata = new ItemData();
        DataForm identity = view.getElement("identity");
        DataForm contact = view.getElement("contact");
        Table itens = view.getElement("contacts");
        ExtendedObject object = contact.getObject();
        byte error = Request.checkContactAddress(object, view);
        
        switch (error) {
        case Common.NULL_ADDRESS:
            view.setFocus(contact.get("ADDRESS"));
            view.message(Const.ERROR, "address.required");
            return;
        case Common.INVALID_ADDRESS:
            view.setFocus(contact.get("ADDRESS"));
            view.message(Const.ERROR, "invalid.address");
            return;
        }
        
        if (itens.length() == 0) {
            itens.setVisible(true);
            
            button = view.getElement("removecontact");
            button.setVisible(true);
            button = view.getElement("editcontact");
            button.setVisible(true);
        }
        
        itemdata.view = view;
        itemdata.itens = itens;
        itemdata.object = object;
        itemdata.partner = identity.get("CODIGO").get();
        itemdata.container = view.getElement("contactcnt");
        itemdata.mark = "contactmark";
        itemdata.object.setValue("CODIGO", 0l);
        Common.insertItem(itemdata);
        
        contact.get("CODIGO").set(object.getValue("CODIGO"));
        updatePartnerView(view);
        updateCommunicsView(view, !VISIBLE);
        
        contact.clearInputs();
    }
    
    /**
     * 
     * @param view
     * @throws Exception
     */
    public final void addressmark(View view) throws Exception {
        Table itens = view.getElement("addresses");
        DataForm form = view.getElement("address");
        
        Request.itemmark(view, itens, form);
    }
    
    /**
     * 
     * @param view
     * @throws Exception
     */
    public final void contactmark(View view) {
        Button button;
        Table itens = view.getElement("contacts");
        DataForm form = view.getElement("contact");
        long contactid_, contactid = Request.itemmark(view, itens, form);
        
        itens = view.getElement("communics");
        for (TableItem item : itens.getItens()) {
            contactid_ = Common.getValue(item.get("CONTACT_ID"));
            item.setVisible((contactid == contactid_)? true : false);
        }
        
        itens.setVisible(true);
        updateCommunicsView(view, VISIBLE);
        
        if (Common.getMode(view) == Common.SHOW)
            return;
        
        button = view.getElement("removecommunic");
        button.setVisible(true);
    }
    
    /**
     * 
     * @param view
     */
    public final void create(View view) {
        Request.create(view);
    }
    
    /**
     * 
     * @param view
     * @throws Exception
     */
    public final void editaddress(View view) {
        Table addresses = view.getElement("addresses");
        DataForm address = view.getElement("address");
        
        Request.edititem(view, addresses, address);
        updatePartnerView(view);
        
        address.clearInputs();
    }
    
    /**
     * 
     * @param view
     * @throws Exception
     */
    public final void editcontact(View view) {
        Table contacts = view.getElement("contacts");
        DataForm contact = view.getElement("contact");
        ExtendedObject object = contact.getObject();
        byte error = Request.checkContactAddress(object, view);
        
        switch (error) {
        case Common.NULL_ADDRESS:
            view.setFocus(contact.get("ADDRESS"));
            view.message(Const.ERROR, "address.required");
            return;
        case Common.INVALID_ADDRESS:
            view.setFocus(contact.get("ADDRESS"));
            view.message(Const.ERROR, "invalid.address");
            return;
        }
        
        Request.edititem(view, contacts, contact);
        
        updatePartnerView(view);
        
        contact.clearInputs();
    }
    
    /**
     * 
     * @param view
     * @throws Exception
     */
    public final void identity(View view) throws Exception {
        Documents documents = new Documents(this);
        DocumentModel[] models = new DocumentModel[3];
        
        models[Common.IDENTITY] = documents.getModel("CUSTOM_PARTNER");
        models[Common.ADDRESS] = documents.getModel("CUSTOM_PARTNER_ADDRESS");
        models[Common.CONTACT] = documents.getModel("CUSTOM_PARTNER_CONTACT");
        
        Response.identity(view, models, this);
    }
    
    /**
     * 
     * @param message
     * @return
     */
    public final InstallData install(Message message) {
        return Install.self();
    }
    
    /**
     * 
     * @param view
     * @throws Exception
     */
    public final void main(View view) throws Exception {
        Response.main(view, this);
    }
    
    /**
     * 
     * @param view
     */
    public final void removeaddress(View view) {
        Button button;
        Table itens = view.getElement("addresses");
        DataForm address = view.getElement("address");
        
        Request.removeitem(view, "addresses");
        
        if (itens.length() > 0)
            return;
        
        itens.setVisible(false);
        
        button = view.getElement("removeaddress");
        button.setVisible(false);
        button = view.getElement("editaddress");
        button.setVisible(false);
        
        updatePartnerView(view);
        
        address.clearInputs();
    }
    
    /**
     * 
     * @param view
     */
    public final void removecommunic(View view) {
        Button removecommunic;
        Table communics = view.getElement("communics");
        
        for (TableItem item : communics.getItens())
            if (item.isSelected())
                communics.remove(item);
        
        if (communics.length() > 0)
            return;
        
        removecommunic = view.getElement("removecommunic");
        removecommunic.setVisible(false);
        communics.setVisible(false);
    }
    
    /**
     * 
     * @param view
     */
    public final void removecontact(View view) {
        Button button;
        Table itens = view.getElement("contacts");
        DataForm contact = view.getElement("contact");
        
        Request.removeitem(view, "contacts");
        
        if (itens.length() > 0)
            return;
        
        itens.setVisible(false);
        
        button = view.getElement("editcontact");
        button.setVisible(false);
        button = view.getElement("removecontact");
        button.setVisible(false);
        
        updatePartnerView(view);
        
        contact.clearInputs();
    }
    
    /**
     * 
     * @param view
     */
    public final void save(View view) throws Exception {
        Request.save(view, this);
    }
    
    /**
     * 
     * @param view
     * @throws Exception
     */
    public final void show(View view) throws Exception {
        Request.load(view, this, Common.SHOW);
    }

    /**
     * 
     * @param view
     * @throws Exception
     */
    public final void update(View view) throws Exception {
        Request.load(view, this, Common.UPDATE);
    }
    
    /**
     * 
     * @param view
     * @param visible
     */
    private final void updateCommunicsView(View view, boolean visible) {
        Container communicscnt = view.getElement("communicscnt");
        
        communicscnt.setVisible(visible);
    }
    
    /**
     * 
     * @param view
     */
    private final void updatePartnerView(View view) {
        long contactid;
        DataItem dataitem;
        InputComponent input;
        ExtendedObject object;
        Table itens = view.getElement("addresses");
        DataForm form = view.getElement("contact");

        dataitem = form.get("ADDRESS");
        Common.loadListFromTable(dataitem, itens, "LOGRADOURO", "CODIGO");
        
        form = view.getElement("contact");
        object = form.getObject();
        
        itens = view.getElement("communics");
        for (TableItem item : itens.getItens()) {
            input = item.get("CONTACT_ID");
            
            contactid = Common.getValue(input);
            if (contactid > 0)
                continue;
            
            contactid = object.getValue("CODIGO");
            input.set(contactid);
        }
    }
    
    public final void validate(View view) { }
}
