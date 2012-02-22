package org.erp.custom.sd.partner;

import org.erp.custom.sd.partner.common.Partner;
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
            documents.save(opartner);
            documents.save(oaddress);
            documents.commit();
            
            view.setReloadableView(true);
            view.export("mode", Common.UPDATE);
            
            break;
        case Common.UPDATE:
            documents.modify(opartner);
            documents.modify(oaddress);
            documents.commit();
            
            break;
        }
        
        view.message(Const.STATUS, "partner.saved.successfuly");
    }
    
    /**
     * 
     * @param view
     */
    public static final void show(ViewData view) {
        Partner partner;
        Partners partners;
        DataForm form = (DataForm)view.getElement("selection");
        int ident = toInteger(form.getValue("partner"));
        
        if (ident == 0) {
            view.message(Const.ERROR, "field.is.required");
            return;
        }
        
        partners = new Partners();
        partner = partners.get(ident);
        
        if (partner == null) {
            view.message(Const.ERROR, "invalid.partner");
            return;
        }
        
        view.export("partner", partner);
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
    public static final void update(ViewData view) {
        DataForm form = (DataForm)view.getElement("selection");
        int ident = toInteger(form.getValue("partner"));
        
        if (ident == 0) {
            view.message(Const.ERROR, "field.is.required");
            return;
        }
        
        view.export("mode", Common.UPDATE);
        view.setReloadableView(true);
        view.redirect(null, "identity");
    }
}
