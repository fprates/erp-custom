package org.erp.custom.sd.partner;

import java.util.ArrayList;
import java.util.List;

import org.iocaste.documents.common.Documents;
import org.iocaste.documents.common.ExtendedObject;
import org.iocaste.protocol.Function;
import org.iocaste.shell.common.Const;
import org.iocaste.shell.common.Container;
import org.iocaste.shell.common.DataForm;
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
     * @param view
     * @param itens
     * @param object
     * @param container
     */
    public static final void additem(ViewData view, Table itens,
            ExtendedObject object, Container container) {
        byte mode = Common.getMode(view);
        
        Common.insertItem(mode, itens, object, container);
    }
    
    /**
     * 
     * @param view
     */
    public static final void addressmark(ViewData view) throws Exception {
        Table addresses = view.getElement("addresses");
        DataForm address = view.getElement("address");
        Parameter index = view.getElement("index");
        TableItem addressitem = addresses.get(
                Integer.parseInt((String)index.get()));
        ExtendedObject object = addressitem.getObject();
        Link link = addressitem.get("LOGRADOURO");
        
        object.setValue("LOGRADOURO", link.getText());
        address.setObject(object);
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
        ExtendedObject[] addresses, oldaddresses, contacts;
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
        oldaddresses = new ExtendedObject[addresses.length];
        System.arraycopy(addresses, 0, oldaddresses, 0, addresses.length);
        
        query = "from CUSTOM_PARTNER_CONTACT where partner_id = ?";
        contacts = documents.select(query, ident);
        
        view.export("partner", partner);
        view.export("addresses", addresses);
        view.export("old_addresses", oldaddresses);
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
        Link link;
        Table itens = view.getElement(tablename);
        int i = 0;
        
        for (TableItem item : itens.getItens()) {
            if (item.isSelected()) {
                itens.remove(item);
                continue;
            }
            
            link = item.get("LOGRADOURO");
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
        Link link;
        String query;
        Object value;
        long item, olditem, codigo, i;
        ExtendedObject ocontact, oaddress;
        Table itens;
        List<ExtendedObject> oaddresses; 
        ExtendedObject[] oldaddresses = view.getParameter("old_addresses");
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
        
        itens = view.getElement("addresses");
        if (oldaddresses != null)
            i = oldaddresses[oldaddresses.length - 1].
                    getValue("CODIGO");
        else
            i = codigo * 100;
        
        i++;
        
        oaddresses = new ArrayList<ExtendedObject>();
        for (TableItem address : itens.getItens()) {
            oaddress = address.getObject();
            
            value = oaddress.getValue("CODIGO");
            item = (value == null)? 0 : (Long)value;
            
            if (item == 0) {
                link = address.get("LOGRADOURO");
                oaddress.setValue("CODIGO", i);
                oaddress.setValue("PARTNER_ID", codigo);
                oaddress.setValue("LOGRADOURO", link.getText());
                
                documents.save(oaddress);
                
                ((InputComponent)address.get("CODIGO")).set(i++);
                ((InputComponent)address.get("PARTNER_ID")).set(codigo);
                
                continue;
            }
            
            for (ExtendedObject oldaddress : oldaddresses) {
                olditem = oldaddress.getValue("CODIGO");
                
                if (item != olditem)
                    continue;
                
                oaddress.setValue("CODIGO", item);
                oaddress.setValue("PARTNER_ID", codigo);
                documents.save(oaddress);
                
                ((InputComponent)address.get("CODIGO")).set(item);
                ((InputComponent)address.get("PARTNER_ID")).set(codigo);
                
                break;
            }
        }
        
        itens = view.getElement("contacts");
        i = (codigo * 100) + 1;
        
        for (TableItem contact : itens.getItens()) {
            ocontact = contact.getObject();
            ocontact.setValue("CODIGO", i);
            ocontact.setValue("PARTNER_ID", codigo);
            documents.save(ocontact);
            
            ((InputComponent)contact.get("CODIGO")).set(i++);
            ((InputComponent)contact.get("PARTNER_ID")).set(codigo);
        }
        
        documents.commit();
        
        oldaddresses = oaddresses.toArray(new ExtendedObject[0]);
        view.export("old_addresses", oldaddresses);
        view.message(Const.STATUS, "partner.saved.successfuly");
    }
}
