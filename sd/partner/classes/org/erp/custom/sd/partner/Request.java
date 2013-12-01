package org.erp.custom.sd.partner;

import java.util.HashMap;
import java.util.Map;

import org.iocaste.documents.common.Documents;
import org.iocaste.documents.common.ExtendedObject;
import org.iocaste.documents.common.Query;
import org.iocaste.shell.common.Const;
import org.iocaste.shell.common.DataForm;
import org.iocaste.shell.common.DataItem;
import org.iocaste.shell.common.InputComponent;
import org.iocaste.shell.common.Link;
import org.iocaste.shell.common.Parameter;
import org.iocaste.shell.common.TabbedPane;
import org.iocaste.shell.common.Table;
import org.iocaste.shell.common.TableItem;
import org.iocaste.shell.common.View;

public class Request {    
    /**
     * 
     * @param contact
     * @param view
     * @return
     */
    public static final byte checkContactAddress(ExtendedObject contact,
            View view) {
        Link link;
        Table addresses;
        long taddress, faddress = contact.getl("ADDRESS");
        
        if (faddress == 0)
            return Common.NULL_ADDRESS;
        
        addresses = view.getElement("addresses");
        if (addresses.length() == 0)
            return Common.INVALID_ADDRESS;
        
        for (TableItem item : addresses.getItems()) {
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
    public static final void create(View view) {
        view.clearExports();
        view.export("mode", Common.CREATE);
        view.setReloadableView(true);
        view.redirect("identity");
    }
    
    /**
     * 
     * @param view
     * @param itens
     * @param form
     * @throws Exception
     */
    public static final void edititem(View view, Table itens,
            DataForm form) {
        Link link;
        long tcodigo, fcodigo;
        
        fcodigo = form.get("CODIGO").get();
        if (fcodigo == 0)
            return;
        
        for (TableItem item : itens.getItems()) {
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
    public static final long itemmark(View view, Table itens,
            DataForm form) {
        Parameter index = view.getElement("index");
        TableItem item = itens.get(Integer.parseInt((String)index.get()));
        ExtendedObject object = item.getObject();
        Link link = item.get("CODIGO");
        long codigo = Long.parseLong(link.getText());
        
        object.set("CODIGO", codigo);
        form.setObject(object);
        
        return codigo;
    }
    
    /**
     * 
     * @param view
     * @param function
     * @param mode
     */
    public static final void load(Context context, byte mode) {
        Query query;
        Documents documents;
        ExtendedObject partner;
        ExtendedObject[] objects;
        String strid;
        Services services;
        DataForm form = context.view.getElement("selection");
        long ident = form.get("partner").getl();
        
        if (ident == 0) {
            context.view.message(Const.ERROR, "field.is.required");
            return;
        }

        services = new Services();
        partner = services.load(ident, context.function);
        if (partner == null) {
            context.view.message(Const.ERROR, "invalid.partner");
            return;
        }
        
        documents = new Documents(context.function);
        strid = Long.toString(ident);
        if (mode == Common.UPDATE && documents.
                isLocked("CUSTOM_PARTNER", strid)) {
            context.view.message(Const.ERROR, "record.is.locked");
            return;
        }
        
        documents.lock("CUSTOM_PARTNER", strid);
        context.view.clearExports();
        query = new Query();
        query.setModel("CUSTOM_PARTNER_ADDRESS");
        query.andEqual("PARTNER_ID", ident);
        objects = documents.select(query);
        context.view.export("addresses", objects);
        
        query = new Query();
        query.setModel("CUSTOM_PARTNER_CONTACT");
        query.andEqual("PARTNER_ID", ident);
        objects = documents.select(query);
        context.view.export("contacts", objects);

        query = new Query();
        query.setModel("CUSTOM_PARTNER_COMM");
        query.andEqual("PARTNER_ID", ident);
        objects = documents.select(query);
        context.view.export("communics", objects);
        
        objects = new ExtendedObject[1];
        objects[0] = documents.getObject("CUSTOM_PARTNER_TYPE",
                partner.get("TIPO_PARCEIRO"));
        
        context.view.export("partnertype", objects[0]);
        context.view.export("partner", partner);
        context.view.export("mode", mode);
        context.view.setReloadableView(true);
        context.view.redirect("identity");
    }
    
    /**
     * 
     * @param view
     */
    public static final void removeitem(View view, String tablename) {
        Link link;
        Table itens = view.getElement(tablename);
        int i = 0;
        
        for (TableItem item : itens.getItems()) {
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
     */
    public static final void save(Context context) {
        Query[] queries;
        DataItem dataitem;
        InputComponent input;
        long codigo, addrfrom, addrto, contactid, contactid_, itemcode;
        Link link;
        Table itens, communics;
        Map<Long, Long> addrtransl;
        TabbedPane tpane = context.view.getElement("pane");
        DataForm form = tpane.get("identitytab").getContainer();
        ExtendedObject opartner = form.getObject();
        byte modo = Common.getMode(context.view);
        Documents documents = new Documents(context.function);
        Services services = new Services();
        
        switch (modo) {
        case Common.CREATE:
            codigo = services.create(opartner, context.function);
            if (codigo == 0) {
                context.view.message(Const.ERROR, "header.save.error");
                return;
            }
            
            form.get("CODIGO").set(codigo);
            documents.lock("CUSTOM_PARTNER", Long.toString(codigo));
            context.view.setTitle(Common.TITLE[Common.UPDATE]);
            context.view.export("mode", Common.UPDATE);
            
            break;
        default:
            documents.modify(opartner);
            codigo = opartner.getl("CODIGO");
            
            queries = new Query[3];
            queries[0] = new Query("delete");
            queries[0].setModel("CUSTOM_PARTNER_COMM");
            queries[0].andEqual("PARTNER_ID", codigo);
            
            queries[1] = new Query("delete");
            queries[1].setModel("CUSTOM_PARTNER_CONTACT");
            queries[1].andEqual("PARTNER_ID", codigo);
            
            queries[2] = new Query("delete");
            queries[2].setModel("CUSTOM_PARTNER_ADDRESS");
            queries[2].andEqual("PARTNER_ID", codigo);
            
            documents.update(queries);
            context.view.export("mode", Common.UPDATE);
            
            break;
        }
        
        addrtransl = (modo == Common.CREATE)? new HashMap<Long, Long>() : null;
        
        addrfrom = 0;
        addrto = 0;
        link = null;
        
        itens = context.view.getElement("addresses");
        for (TableItem address : itens.getItems()) {
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
        
        form = context.view.getElement("contact");
        dataitem = form.get("ADDRESS");
        Common.loadListFromTable(dataitem, itens, "LOGRADOURO", "CODIGO");
        
        itens = context.view.getElement("contacts");
        for (TableItem contact : itens.getItems()) {
            contactid = Long.parseLong(((Link)contact.get("CODIGO")).getText());
            if (modo == Common.CREATE) {
                input = contact.get("ADDRESS");
                addrfrom = input.get();
                addrto = addrtransl.get(addrfrom); 
                input.set(addrto);
            }
            
            itemcode = saveItem(documents, contact, codigo);
            
            communics = context.view.getElement("communics");
            for (TableItem communic : communics.getItems()) {
                contactid_ = Common.getValue(communic.get("CONTACT_ID"));
                if (contactid != contactid_)
                    continue;
                
                saveCommunicationItem(documents, communic, itemcode, codigo);
            }
        }
        
        context.view.message(Const.STATUS, "partner.saved.successfuly");
    }
    
    /**
     * 
     * @param documents
     * @param item
     */
    private static final void saveCommunicationItem(Documents documents,
            TableItem item, long contactid, long partner) {
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
     */
    private static final long saveItem(Documents documents, TableItem item,
            long partner) {
        ExtendedObject object = item.getObject();
        Link link = item.get("CODIGO");
        long codigo = Long.parseLong(link.getText());
        
        if (codigo < (partner * 100))
            codigo += (partner * 100);
        
        object.set("CODIGO", codigo);
        object.set("PARTNER_ID", partner);
        documents.save(object);
        
        link.setText(Long.toString(codigo));
        ((InputComponent)item.get("PARTNER_ID")).set(partner);
        
        return codigo;
    }
}
