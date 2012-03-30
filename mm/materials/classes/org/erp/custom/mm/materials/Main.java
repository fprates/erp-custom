package org.erp.custom.mm.materials;

import org.iocaste.packagetool.common.InstallData;
import org.iocaste.protocol.Message;
import org.iocaste.shell.common.AbstractPage;
import org.iocaste.shell.common.ViewData;

public class Main extends AbstractPage {

    public Main() {
        export("install", "install");
    }
    
    public final void addprice(ViewData view) {
        Request.additem(view, "prices");
    }
    
    public final void addpromo(ViewData view) {
        Request.additem(view, "promos");
    }
    
    /**
     * 
     * @param view
     * @throws Exception
     */
    public final void create(ViewData view) throws Exception {
        Request.create(view, this);
    }
    
    /**
     * 
     * @param message
     * @return
     */
    public final InstallData install(Message message) {
        return Install.self();
    }
    
    /**
     * 
     * @param view
     * @throws Exception
     */
    public final void main(ViewData view) throws Exception {
        Response.main(view, this);
    }
    
    /**
     * 
     * @param view
     * @throws Exception
     */
    public final void material(ViewData view) throws Exception {
        Response.material(view, this);
    }
    
    /**
     * 
     * @param view
     */
    public final void removeprice(ViewData view) {
        Request.removeitem(view, "prices");
    }
    
    /**
     * 
     * @param view
     */
    public final void removepromo(ViewData view) {
        Request.removeitem(view, "promos");
    }
    
    /**
     * 
     * @param view
     * @throws Exception
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
