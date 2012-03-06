package org.erp.custom.sd.partner;

import org.iocaste.documents.common.DocumentModel;
import org.iocaste.documents.common.Documents;
import org.iocaste.shell.common.AbstractPage;
import org.iocaste.shell.common.ViewData;


public class Main extends AbstractPage {
    
    /**
     * 
     * @param view
     */
    public void create(ViewData view) {
        Request.create(view);
    }
    
    /**
     * 
     * @param view
     * @throws Exception
     */
    public void identity(ViewData view) throws Exception {
        Documents documents = new Documents(this);
        DocumentModel identity = documents.getModel("CUSTOM_PARTNER");
        DocumentModel address = documents.getModel("CUSTOM_PARTNER_ADDRESS");
        
        Response.identity(view, identity, address);
    }
    
    /**
     * 
     * @param view
     * @throws Exception
     */
    public void main(ViewData view) throws Exception {
        Response.main(view);
    }
    
    /**
     * 
     * @param view
     */
    public final void save(ViewData view) throws Exception {
        Request.save(view, this);
    }
    
    /**
     * 
     * @param view
     * @throws Exception
     */
    public final void show(ViewData view) throws Exception {
        Request.show(view, this);
    }

    /**
     * 
     * @param view
     * @throws Exception
     */
    public final void update(ViewData view) throws Exception {
        Request.update(view, this);
    }
}
