package org.erp.custom.sd.partner;

import org.iocaste.documents.common.Documents;
import org.iocaste.documents.common.ExtendedObject;
import org.iocaste.protocol.Function;
import org.iocaste.shell.common.Const;
import org.iocaste.shell.common.DataForm;
import org.iocaste.shell.common.TabbedPane;
import org.iocaste.shell.common.ViewData;

public class Request {

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
     * @throws Exception
     */
    public static final void save(ViewData view, Function function)
            throws Exception {
        String scodigo;
        long codigo;
        TabbedPane tpane = (TabbedPane)view.getElement("pane");
        DataForm identityform = (DataForm)tpane.get("identitytab").
                getContainer();
        DataForm addressform = (DataForm)tpane.get("addresstab").getContainer();
        ExtendedObject opartner = identityform.getObject();
        ExtendedObject oaddress = addressform.getObject();
        byte modo = Common.getMode(view);
        Documents documents = new Documents(function);
        
        switch (modo) {
        case Common.CREATE:
            codigo = documents.getNextNumber("CUSTPARTNER");
            scodigo = Long.toString(codigo);
            
            opartner.setValue("CODIGO", codigo);
            documents.save(opartner);
            identityform.get("CODIGO").setValue(scodigo);
            
            codigo = (codigo * 100) + 1;
            oaddress.setValue("ADDRESS_ID", codigo);
            documents.save(oaddress);
            
            addressform.get("ADDRESS_ID").setValue(Long.toString(codigo));
            addressform.get("PARTNER_ID").setValue(scodigo);
            
            documents.commit();
            
            view.setTitle(Common.TITLE[Common.UPDATE]);
            view.export("mode", Common.UPDATE);
            
            break;
        case Common.UPDATE:
            documents.modify(opartner);
            documents.modify(oaddress);
            documents.commit();
            
            view.export("mode", Common.UPDATE);
            
            break;
        }
        
        view.message(Const.STATUS, "partner.saved.successfuly");
    }
    
    /**
     * 
     * @param view
     * @param function
     * @throws Exception
     */
    public static final void show(ViewData view, Function function) 
            throws Exception {
        String query;
        Documents documents;
        ExtendedObject partner;
        ExtendedObject[] addresses;
        DataForm form = (DataForm)view.getElement("selection");
        int ident = toInteger(form.get("partner").getValue());
        
        if (ident == 0) {
            view.message(Const.ERROR, "field.is.required");
            return;
        }
        
        documents = new Documents(function);
        partner = documents.getObject("CUSTOM_PARTNER", ident);
        
        query = "from CUSTOM_PARTNER_ADDRESS where partner_id = ?";
        addresses = documents.select(query, new Integer[] {ident});

        
        if (partner == null) {
            view.message(Const.ERROR, "invalid.partner");
            return;
        }
        
        view.export("partner", partner);
        view.export("address", addresses[0]);
        view.export("mode", Common.SHOW);
        view.setReloadableView(true);
        view.redirect(null, "identity");
    }
    
    /**
     * 
     * @param value
     * @return
     */
    private static final int toInteger(String value) {
        return (value.equals("")?0:Integer.parseInt(value));
    }
    
    /**
     * 
     * @param view
     */
    public static final void update(ViewData view, Function function)
            throws Exception {
        Documents documents;
        ExtendedObject object;
        DataForm form = (DataForm)view.getElement("selection");
        int ident = toInteger(form.get("partner").getValue());
        
        if (ident == 0) {
            view.message(Const.ERROR, "field.is.required");
            return;
        }
        
        documents = new Documents(function);
        object = documents.getObject("CUSTOM_PARTNER", ident);
        
        view.export("partner", object);
        view.export("mode", Common.UPDATE);
        view.setReloadableView(true);
        view.redirect(null, "identity");
    }
}
