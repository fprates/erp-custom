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
        DocumentModel model = new Documents(this).getModel("CUSTOM_PARTNER");
        
        Response.identity(view, model);
    }
    
    /**
     * 
     * @param view
     */
    public void main(ViewData view) {
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
     */
    public final void show(ViewData view) {
        Request.show(view);
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
