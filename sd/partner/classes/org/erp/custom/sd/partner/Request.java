package org.erp.custom.sd.partner;

import org.erp.custom.sd.partner.common.Partner;
import org.erp.custom.sd.partner.common.PartnerAddress;
import org.iocaste.shell.common.Const;
import org.iocaste.shell.common.DataForm;
import org.iocaste.shell.common.TabbedPane;
import org.iocaste.shell.common.ViewData;

public class Request {

    public static final void create(ViewData view) {
        view.export("mode", Common.CREATE);
        view.setReloadableView(true);
        view.redirect(null, "identity");
    }
    
    public static final void save(ViewData view) throws Exception {
        PartnerAddress address;
        TabbedPane tpane = (TabbedPane)view.getElement("pane");
        DataForm identityform = (DataForm)tpane.get("identitytab").
                getContainer();
        DataForm addressform = (DataForm)tpane.get("addresstab").getContainer();
        Partner partner = (Partner)identityform.getObject().newLooseInstance();
        Partners partners = new Partners();
        byte modo = Common.getMode(view);
        
        address = (PartnerAddress)addressform.getObject().newLooseInstance();
        address.setPartner(partner);
        
        partner.add(address);
        
        switch (modo) {
        case Common.CREATE:
            partners.save(partner);
            
            view.setReloadableView(true);
            view.export("mode", Common.UPDATE);
            break;
        case Common.UPDATE:
            partners.update(partner);
            break;
        }
        
        view.message(Const.STATUS, "partner.saved.successfuly");
    }
    
    public static final void show(ViewData view) {
        DataForm form = (DataForm)view.getElement("selection");
        int ident = toInteger(form.getValue("partner"));
        
        if (ident == 0) {
            view.message(Const.ERROR, "field.is.required");
            return;
        }
        
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
