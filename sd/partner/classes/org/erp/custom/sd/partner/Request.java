package org.erp.custom.sd.partner;

import org.iocaste.documents.common.Documents;
import org.iocaste.documents.common.ExtendedObject;
import org.iocaste.protocol.Function;
import org.iocaste.shell.common.Const;
import org.iocaste.shell.common.DataForm;
import org.iocaste.shell.common.InputComponent;
import org.iocaste.shell.common.Shell;
import org.iocaste.shell.common.TabbedPane;
import org.iocaste.shell.common.Table;
import org.iocaste.shell.common.TableItem;
import org.iocaste.shell.common.ViewData;

public class Request {

    public static final void addaddress(ViewData view) {
        Table addresses = view.getElement("addresses");
        
        Common.insertItem(addresses, null);
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
        DataForm form = view.getElement("selection");
        int ident = toInteger(form.get("partner").getValue());
        
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
        
        view.export("partner", partner);
        view.export("addresses", addresses);
        view.export("mode", mode);
        view.setReloadableView(true);
        view.redirect(null, "identity");
    }
    
    /**
     * 
     * @param view
     */
    public static final void removeaddress(ViewData view) {
        Table addresses = view.getElement("addresses");
        
        for (TableItem address : addresses.getItens())
            if (address.isSelected())
                addresses.remove(address);
    }
    
    /**
     * 
     * @param view
     * @throws Exception
     */
    public static final void save(ViewData view, Function function)
            throws Exception {
        String query, scodigo;
        long codigo, i;
        ExtendedObject oaddress;
        Table addresses;
        TabbedPane tpane = view.getElement("pane");
        DataForm identityform = tpane.get("identitytab").getContainer();
        ExtendedObject opartner = identityform.getObject();
        byte modo = Common.getMode(view);
        Documents documents = new Documents(function);
        
        switch (modo) {
        case Common.CREATE:
            codigo = documents.getNextNumber("CUSTPARTNER");
            scodigo = Long.toString(codigo);
            
            opartner.setValue("CODIGO", codigo);
            if (documents.save(opartner) == 0) {
                view.message(Const.ERROR, "header.save.error");
                return;
            }
            
            identityform.get("CODIGO").setValue(scodigo);
            
            view.setTitle(Common.TITLE[Common.UPDATE]);
            view.export("mode", Common.UPDATE);
            
            break;
        default:
            documents.modify(opartner);
            codigo = opartner.getValue("CODIGO");
            scodigo = Long.toString(codigo);
            
            query = "delete from CUSTOM_PARTNER_ADDRESS where PARTNER_ID = ?";
            documents.update(query, codigo);
            
            view.export("mode", Common.UPDATE);
            
            break;
        }
        
        addresses = view.getElement("addresses");
        i = (codigo * 100) + 1;
        
        for (TableItem address : addresses.getItens()) {
            oaddress = address.getObject();
            oaddress.setValue("ADDRESS_ID", i);
            oaddress.setValue("PARTNER_ID", codigo);
            documents.save(oaddress);
            
            Shell.setInputValue((InputComponent)address.get("ADDRESS_ID"), i);
            Shell.setInputValue((InputComponent)address.get("PARTNER_ID"),
                    codigo);
            
            i++;
        }
        
        documents.commit();
        
        view.message(Const.STATUS, "partner.saved.successfuly");
    }
    
    /**
     * 
     * @param value
     * @return
     */
    private static final int toInteger(String value) {
        return (value.equals("")?0:Integer.parseInt(value));
    }
}
