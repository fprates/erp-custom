package org.erp.custom.sd.partner;

import java.util.HashMap;
import java.util.Map;

import org.iocaste.documents.common.Documents;
import org.iocaste.documents.common.ExtendedObject;
import org.iocaste.protocol.Function;
import org.iocaste.shell.common.Const;
import org.iocaste.shell.common.DataForm;
import org.iocaste.shell.common.DataItem;
import org.iocaste.shell.common.InputComponent;
import org.iocaste.shell.common.Link;
import org.iocaste.shell.common.Parameter;
import org.iocaste.shell.common.TabbedPane;
import org.iocaste.shell.common.Table;
import org.iocaste.shell.common.TableItem;
import org.iocaste.shell.common.ViewData;

public class Request {
    
    /**
     * 
     * @param contact
     * @param view
     * @return
     */
    public static final byte checkContactAddress(ExtendedObject contact,
            ViewData view) {
        Link link;
        Table addresses;
        long taddress, faddress = Common.getLong(contact.getValue("ADDRESS"));
        
        if (faddress == 0)
            return Common.NULL_ADDRESS;
        
        addresses = view.getElement("addresses");
        if (addresses.length() == 0)
            return Common.INVALID_ADDRESS;
        
        for (TableItem item : addresses.getItens()) {
            link = item.get("CODIGO");
            taddress = Long.parseLong(link.getText());
            
            if (faddress != taddress)
                continue;
            
            return 0;
        }
        
        return Common.INVALID_ADDRESS;
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
     * @param itens
     * @param form
     * @throws Exception
     */
    public static final void edititem(ViewData view, Table itens,
            DataForm form) {
        Link link;
        long tcodigo, fcodigo;
        
        fcodigo = Common.getLong(form.get("CODIGO").get());
        if (fcodigo == 0)
            return;
        
        for (TableItem item : itens.getItens()) {
            link = item.get("CODIGO");
            tcodigo = Long.parseLong(link.getText());
            
            if (fcodigo != tcodigo)
                continue;
            
            form.get("CODIGO").set(Long.parseLong(link.getText()));
            item.setObject(form.getObject());
            break;
        }
    }
    
    /**
     * 
     * @param view
     * @param itens
     * @param form
     * @return
     * @throws Exception
     */
    public static final long itemmark(ViewData view, Table itens,
            DataForm form) {
        Parameter index = view.getElement("index");
        TableItem item = itens.get(Integer.parseInt((String)index.get()));
        ExtendedObject object = item.getObject();
        Link link = item.get("CODIGO");
        long codigo = Long.parseLong(link.getText());
        
        object.setValue("CODIGO", codigo);
        form.setObject(object);
        
        return codigo;
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
        ExtendedObject[] addresses, contacts, communics;
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

        query = "from CUSTOM_PARTNER_COMM where partner_id = ?";
        communics = documents.select(query, ident);
        
        view.clearParameters();
        view.export("partner", partner);
        view.export("addresses", addresses);
        view.export("contacts", contacts);
        view.export("communics", communics);
        view.export("mode", mode);
        view.setReloadableView(true);
        view.redirect(null, "identity");
    }
    
    /**
     * 
     * @param view
     */
    public static final void removeitem(ViewData view, String tablename) {
        Link link;
        Table itens = view.getElement(tablename);
        int i = 0;
        
        for (TableItem item : itens.getItens()) {
            if (item.isSelected()) {
                itens.remove(item);
                continue;
            }
            
            link = item.get("CODIGO");
            link.setValue("index", i++);
        }
    }
    
    /**
     * 
     * @param view
     * @throws Exception
     */
    public static final void save(ViewData view, Function function)
            throws Exception {
        DataItem dataitem;
        InputComponent input;
        String query;
        long codigo, addrfrom, addrto, contactid, contactid_, itemcode;
        Link link;
        Table itens, communics;
        Map<Long, Long> addrtransl;
        TabbedPane tpane = view.getElement("pane");
        DataForm form = tpane.get("identitytab").getContainer();
        ExtendedObject opartner = form.getObject();
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
            
            form.get("CODIGO").set(codigo);
            
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
        
        addrtransl = (modo == Common.CREATE)? new HashMap<Long, Long>() : null;
        
        addrfrom = 0;
        addrto = 0;
        link = null;
        
        itens = view.getElement("addresses");
        for (TableItem address : itens.getItens()) {
            if (modo == Common.CREATE) {
                link = (Link)address.get("CODIGO");
                addrfrom = Long.parseLong(link.getText());
            }
            
            saveItem(documents, address, codigo);
            
            if (modo == Common.CREATE) {
                addrto = Long.parseLong(link.getText());
                addrtransl.put(addrfrom, addrto);
            }
        }
        
        form = view.getElement("contact");
        dataitem = form.get("ADDRESS");
        Common.loadListFromTable(dataitem, itens, "LOGRADOURO", "CODIGO");
        
        itens = view.getElement("contacts");
        for (TableItem contact : itens.getItens()) {
            contactid = Long.parseLong(((Link)contact.get("CODIGO")).getText());
            if (modo == Common.CREATE) {
                input = contact.get("ADDRESS");
                addrfrom = input.get();
                addrto = addrtransl.get(addrfrom); 
                input.set(addrto);
            }
            
            itemcode = saveItem(documents, contact, codigo);
            
            communics = view.getElement("communics");
            for (TableItem communic : communics.getItens()) {
                contactid_ = Common.getValue(communic.get("CONTACT_ID"));
                if (contactid != contactid_)
                    continue;
                
                saveCommunicationItem(documents, communic, itemcode, codigo);
            }
        }
        
        documents.commit();
        
        view.message(Const.STATUS, "partner.saved.successfuly");
    }
    
    /**
     * 
     * @param documents
     * @param item
     * @throws Exception
     */
    private static final void saveCommunicationItem(Documents documents,
            TableItem item, long contactid, long partner) throws Exception {
        InputComponent input;
        long codigo = Common.getValue(item.get("CODIGO"));
        
        if (codigo < (contactid * 100)) {
            codigo += (contactid * 100);
            input = item.get("CODIGO");
            input.set(codigo);
        }
        
        input = item.get("CONTACT_ID");
        input.set(contactid);
        input = item.get("PARTNER_ID");
        input.set(partner);
        
        documents.save(item.getObject());
    }
    
    /**
     * 
     * @param documents
     * @param item
     * @param partner
     * @return
     * @throws Exception
     */
    private static final long saveItem(Documents documents, TableItem item,
            long partner) throws Exception {
        ExtendedObject object = item.getObject();
        Link link = item.get("CODIGO");
        long codigo = Long.parseLong(link.getText());
        
        if (codigo < (partner * 100))
            codigo += (partner * 100);
        
        object.setValue("CODIGO", codigo);
        object.setValue("PARTNER_ID", partner);
        documents.save(object);
        
        link.setText(Long.toString(codigo));
        ((InputComponent)item.get("PARTNER_ID")).set(partner);
        
        return codigo;
    }
}
