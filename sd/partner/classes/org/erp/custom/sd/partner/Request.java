package org.erp.custom.sd.partner;

import org.iocaste.documents.common.Documents;
import org.iocaste.documents.common.ExtendedObject;
import org.iocaste.protocol.Function;
import org.iocaste.shell.common.Const;
import org.iocaste.shell.common.DataForm;
import org.iocaste.shell.common.InputComponent;
import org.iocaste.shell.common.TabbedPane;
import org.iocaste.shell.common.Table;
import org.iocaste.shell.common.TableItem;
import org.iocaste.shell.common.ViewData;

public class Request {

    /**
     * 
     * @param view
     * @param tablename
     */
    public static final void additem(ViewData view, Table itens,
            ExtendedObject object) {
        byte mode = Common.getMode(view);
        
        Common.insertItem(mode, itens, object);
    }
    
    /**
     * 
     * @param view
     */
    public static final void addressmark(ViewData view) throws Exception {
        Table addresses = view.getElement("addresses");
        DataForm address = view.getElement("address");
        
        for (TableItem addressitem : addresses.getItens()) {
            if (!addressitem.isSelected())
                continue;
            
            address.setObject(addressitem.getObject());
            break;
        }
    }
    
    /**
     * 
     * @param view
     */
    public static final void create(ViewData view) {
        view.export("mode", Common.CREATE);
        view.setReloadableView(true);
        view.redirect(null, "identity");
    }
    
    /**
     * 
     * @param view
     * @param function
     * @param mode
     * @throws Exception
     */
    public static final void load(ViewData view, Function function, byte mode)
            throws Exception {
        String query;
        Documents documents;
        ExtendedObject partner;
        ExtendedObject[] addresses;
        ExtendedObject[] contacts;
        DataForm form = view.getElement("selection");
        long ident = form.get("partner").get();
        
        if (ident == 0) {
            view.message(Const.ERROR, "field.is.required");
            return;
        }
        
        documents = new Documents(function);
        partner = documents.getObject("CUSTOM_PARTNER", ident);
        if (partner == null) {
            view.message(Const.ERROR, "invalid.partner");
            return;
        }
        
        query = "from CUSTOM_PARTNER_ADDRESS where partner_id = ?";
        addresses = documents.select(query, ident);
        
        query = "from CUSTOM_PARTNER_CONTACT where partner_id = ?";
        contacts = documents.select(query, ident);
        
        view.export("partner", partner);
        view.export("addresses", addresses);
        view.export("contacts", contacts);
        view.export("mode", mode);
        view.setReloadableView(true);
        view.redirect(null, "identity");
    }
    
    /**
     * 
     * @param view
     */
    public static final void removeitem(ViewData view, String tablename) {
        Table itens = view.getElement(tablename);
        
        for (TableItem item : itens.getItens())
            if (item.isSelected())
                itens.remove(item);
    }
    
    /**
     * 
     * @param view
     * @throws Exception
     */
    public static final void save(ViewData view, Function function)
            throws Exception {
        String query;
        long codigo, i;
        ExtendedObject ocontact, oaddress;
        Table contacts, addresses;
        TabbedPane tpane = view.getElement("pane");
        DataForm identityform = tpane.get("identitytab").getContainer();
        ExtendedObject opartner = identityform.getObject();
        byte modo = Common.getMode(view);
        Documents documents = new Documents(function);
        
        switch (modo) {
        case Common.CREATE:
            codigo = documents.getNextNumber("CUSTPARTNER");
            
            opartner.setValue("CODIGO", codigo);
            if (documents.save(opartner) == 0) {
                view.message(Const.ERROR, "header.save.error");
                return;
            }
            
            identityform.get("CODIGO").set(codigo);
            
            view.setTitle(Common.TITLE[Common.UPDATE]);
            view.export("mode", Common.UPDATE);
            
            break;
        default:
            documents.modify(opartner);
            codigo = opartner.getValue("CODIGO");
            
            query = "delete from CUSTOM_PARTNER_ADDRESS where PARTNER_ID = ?";
            documents.update(query, codigo);
            
            query = "delete from CUSTOM_PARTNER_CONTACT where PARTNER_ID = ?";
            documents.update(query, codigo);
            
            view.export("mode", Common.UPDATE);
            
            break;
        }
        
        addresses = view.getElement("addresses");
        i = (codigo * 100) + 1;
        
        for (TableItem address : addresses.getItens()) {
            oaddress = address.getObject();
            oaddress.setValue("CODIGO", i);
            oaddress.setValue("PARTNER_ID", codigo);
            documents.save(oaddress);
            
            ((InputComponent)address.get("CODIGO")).set(i++);
            ((InputComponent)address.get("PARTNER_ID")).set(codigo);
        }
        
        contacts = view.getElement("contacts");
        i = (codigo * 100) + 1;
        
        for (TableItem contact : contacts.getItens()) {
            ocontact = contact.getObject();
            ocontact.setValue("CODIGO", i);
            ocontact.setValue("PARTNER_ID", codigo);
            documents.save(ocontact);
            
            ((InputComponent)contact.get("CODIGO")).set(i++);
            ((InputComponent)contact.get("PARTNER_ID")).set(codigo);
        }
        
        documents.commit();
        
        view.message(Const.STATUS, "partner.saved.successfuly");
    }
}
