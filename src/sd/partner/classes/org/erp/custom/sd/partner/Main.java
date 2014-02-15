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
import org.iocaste.shell.common.PageContext;
import org.iocaste.shell.common.Table;
import org.iocaste.shell.common.TableItem;
import org.iocaste.shell.common.View;


public class Main extends AbstractPage {
    private static final boolean VISIBLE = true;
    private Context context;
    
    public Main() {
        export("install", "install");
    }
    
    public final void addaddress() {
        Button button;
        ItemData itemdata = new ItemData();
        DataForm identity = context.view.getElement("identity");
        DataForm address = context.view.getElement("address");
        Table itens = context.view.getElement("addresses");
        
        if (itens.length() == 0) {
            itens.setVisible(true);
            button = context.view.getElement("removeaddress");
            button.setVisible(true);
            button = context.view.getElement("editaddress");
            button.setVisible(true);
        }

        itemdata.view = context.view;
        itemdata.itens = itens;
        itemdata.object = address.getObject();
        itemdata.partner = identity.get("CODIGO").getl();
        itemdata.container = context.view.getElement("addresscnt");
        itemdata.mark = "addressmark";
        itemdata.object.set("CODIGO", 0l);
        
        Common.insertItem(itemdata);
        updatePartnerView(context.view);
        address.clearInputs();
    }
    
    public final void addcommunic() {
        Table communics = context.view.getElement("communics");
        Button removecommunic = context.view.getElement("removecommunic");
        
        communics.setVisible(true);
        removecommunic.setVisible(true);
        
        Common.insertCommunic(communics, context.view, null);
    }
    
    public final void addcontact() {
        Button button;
        ItemData itemdata = new ItemData();
        DataForm identity = context.view.getElement("identity");
        DataForm contact = context.view.getElement("contact");
        Table itens = context.view.getElement("contacts");
        ExtendedObject object = contact.getObject();
        byte error = Request.checkContactAddress(object, context.view);
        
        switch (error) {
        case Common.NULL_ADDRESS:
            context.view.setFocus(contact.get("ADDRESS"));
            context.view.message(Const.ERROR, "address.required");
            return;
        case Common.INVALID_ADDRESS:
            context.view.setFocus(contact.get("ADDRESS"));
            context.view.message(Const.ERROR, "invalid.address");
            return;
        }
        
        if (itens.length() == 0) {
            itens.setVisible(true);
            
            button = context.view.getElement("removecontact");
            button.setVisible(true);
            button = context.view.getElement("editcontact");
            button.setVisible(true);
        }
        
        itemdata.view = context.view;
        itemdata.itens = itens;
        itemdata.object = object;
        itemdata.partner = identity.get("CODIGO").get();
        itemdata.container = context.view.getElement("contactcnt");
        itemdata.mark = "contactmark";
        itemdata.object.set("CODIGO", 0l);
        Common.insertItem(itemdata);
        
        contact.get("CODIGO").set(object.get("CODIGO"));
        updatePartnerView(context.view);
        updateCommunicsView(context.view, !VISIBLE);
        
        contact.clearInputs();
    }
    
    public final void addressmark() {
        Table itens = context.view.getElement("addresses");
        DataForm form = context.view.getElement("address");
        
        Request.itemmark(context.view, itens, form);
    }
    
    /*
     * (non-Javadoc)
     * @see org.iocaste.shell.common.AbstractPage#back(
     *    org.iocaste.shell.common.View)
     */
    @Override
    public final void back() {
        if (Common.getMode(context.view) == Common.UPDATE)
            unlock(context.view);
        
        super.back();
    }
    
    /**
     * 
     * @param view
     * @throws Exception
     */
    public final void contactmark() {
        Button button;
        Table itens = context.view.getElement("contacts");
        DataForm form = context.view.getElement("contact");
        long contactid_, contactid = Request.itemmark(
                context.view, itens, form);
        
        itens = context.view.getElement("communics");
        for (TableItem item : itens.getItems()) {
            contactid_ = Common.getValue(item.get("CONTACT_ID"));
            item.setVisible((contactid == contactid_)? true : false);
        }
        
        itens.setVisible(true);
        updateCommunicsView(context.view, VISIBLE);
        
        if (Common.getMode(context.view) == Common.SHOW)
            return;
        
        button = context.view.getElement("removecommunic");
        button.setVisible(true);
    }
    
    /**
     * 
     * @param view
     */
    public final void create() {
        Request.create(context.view);
    }
    
    /**
     * 
     * @param view
     * @throws Exception
     */
    public final void editaddress() {
        Table addresses = context.view.getElement("addresses");
        DataForm address = context.view.getElement("address");
        
        Request.edititem(context.view, addresses, address);
        updatePartnerView(context.view);
        
        address.clearInputs();
    }
    
    public final void editcontact() {
        Table contacts = context.view.getElement("contacts");
        DataForm contact = context.view.getElement("contact");
        ExtendedObject object = contact.getObject();
        byte error = Request.checkContactAddress(object, context.view);
        
        switch (error) {
        case Common.NULL_ADDRESS:
            context.view.setFocus(contact.get("ADDRESS"));
            context.view.message(Const.ERROR, "address.required");
            return;
        case Common.INVALID_ADDRESS:
            context.view.setFocus(contact.get("ADDRESS"));
            context.view.message(Const.ERROR, "invalid.address");
            return;
        }
        
        Request.edititem(context.view, contacts, contact);
        
        updatePartnerView(context.view);
        
        contact.clearInputs();
    }
    
    @Override
    public final void home() {
        if ((context.view.getPageName().equals("identity")) &&
                (Common.getMode(context.view) == Common.UPDATE))
            unlock(context.view);
        
        super.home();
    }
    
    /**
     * 
     * @param view
     */
    public final void identity() {
        Documents documents = new Documents(this);
        DocumentModel[] models = new DocumentModel[3];
        
        models[Common.IDENTITY] = documents.getModel("CUSTOM_PARTNER");
        models[Common.ADDRESS] = documents.getModel("CUSTOM_PARTNER_ADDRESS");
        models[Common.CONTACT] = documents.getModel("CUSTOM_PARTNER_CONTACT");
        
        Response.identity(context, models);
    }
    
    @Override
    public final PageContext init(View view) {
        context = new Context();
        
        return context;
    }
    
    /**
     * 
     * @param message
     * @return
     */
    public final InstallData install(Message message) {
        return Install.init();
    }
    
    public final void main() {
        Response.main(context);
    }
    
    /**
     * 
     * @param view
     */
    public final void removeaddress() {
        Button button;
        Table itens = context.view.getElement("addresses");
        DataForm address = context.view.getElement("address");
        
        Request.removeitem(context.view, "addresses");
        
        if (itens.length() > 0)
            return;
        
        itens.setVisible(false);
        
        button = context.view.getElement("removeaddress");
        button.setVisible(false);
        button = context.view.getElement("editaddress");
        button.setVisible(false);
        
        updatePartnerView(context.view);
        
        address.clearInputs();
    }
    
    /**
     * 
     * @param view
     */
    public final void removecommunic() {
        Button removecommunic;
        Table communics = context.view.getElement("communics");
        
        for (TableItem item : communics.getItems())
            if (item.isSelected())
                communics.remove(item);
        
        if (communics.length() > 0)
            return;
        
        removecommunic = context.view.getElement("removecommunic");
        removecommunic.setVisible(false);
        communics.setVisible(false);
    }
    
    public final void removecontact() {
        Button button;
        Table itens = context.view.getElement("contacts");
        DataForm contact = context.view.getElement("contact");
        
        Request.removeitem(context.view, "contacts");
        
        if (itens.length() > 0)
            return;
        
        itens.setVisible(false);
        
        button = context.view.getElement("editcontact");
        button.setVisible(false);
        button = context.view.getElement("removecontact");
        button.setVisible(false);
        
        updatePartnerView(context.view);
        
        contact.clearInputs();
    }
    
    public final void save() {
        Request.save(context);
    }
    
    /**
     * 
     * @param view
     */
    public final void show() {
        Request.load(context, Common.SHOW);
    }
    
    /**
     * 
     * @param view
     */
    private final void unlock(View view) {
        DataForm form = context.view.getElement("identity");
        long id = form.get("CODIGO").getl();
        
        new Documents(this).unlock("CUSTOM_PARTNER", Long.toString(id));
    }

    /**
     * 
     * @param view
     */
    public final void update() {
        Request.load(context, Common.UPDATE);
    }
    
    /**
     * 
     * @param view
     * @param visible
     */
    private final void updateCommunicsView(View view, boolean visible) {
        Container communicscnt = context.view.getElement("communicscnt");
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
        Table itens = context.view.getElement("addresses");
        DataForm form = context.view.getElement("contact");

        dataitem = form.get("ADDRESS");
        Common.loadListFromTable(dataitem, itens, "LOGRADOURO", "CODIGO");
        
        form = context.view.getElement("contact");
        object = form.getObject();
        
        itens = context.view.getElement("communics");
        for (TableItem item : itens.getItems()) {
            input = item.get("CONTACT_ID");
            
            contactid = Common.getValue(input);
            if (contactid > 0)
                continue;
            
            contactid = object.getl("CODIGO");
            input.set(contactid);
        }
    }
    
    @Override
    public final void validate() { }
}

class Context extends PageContext {}