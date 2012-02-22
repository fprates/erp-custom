package org.erp.custom.sd.partner;

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
     */
    public void identity(ViewData view) {
        Response.identity(view);
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
     */
    public final void update(ViewData view) {
        Request.update(view);
    }
}
