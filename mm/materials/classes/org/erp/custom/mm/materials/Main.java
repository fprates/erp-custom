package org.erp.custom.mm.materials;

import org.iocaste.packagetool.common.InstallData;
import org.iocaste.protocol.Message;
import org.iocaste.shell.common.AbstractPage;
import org.iocaste.shell.common.View;

public class Main extends AbstractPage {

    public Main() {
        export("install", "install");
    }
    
    /**
     * 
     * @param view
     */
    public final void addmaterial(View view) {
    	Request.additem(view, "submats");
    }
    
    /**
     * 
     * @param view
     */
    public final void addprice(View view) {
        Request.additem(view, "prices");
    }
    
    /**
     * 
     * @param view
     */
    public final void addpromo(View view) {
        Request.additem(view, "promos");
    }
    
    /**
     * 
     * @param view
     * @throws Exception
     */
    public final void create(View view) throws Exception {
        Request.create(view, this);
    }
    
    /**
     * 
     * @param message
     * @return
     */
    public final InstallData install(Message message) {
        return Install.init();
    }
    
    /**
     * 
     * @param view
     * @throws Exception
     */
    public final void main(View view) throws Exception {
        Response.main(view, this);
    }
    
    /**
     * 
     * @param view
     * @throws Exception
     */
    public final void material(View view) throws Exception {
        Response.material(view, this);
    }
    
    /**
     * 
     * @param view
     */
    public final void removematerial(View view) {
    	Request.removeitem(view, "submats");
    }
    
    /**
     * 
     * @param view
     */
    public final void removeprice(View view) {
        Request.removeitem(view, "prices");
    }
    
    /**
     * 
     * @param view
     */
    public final void removepromo(View view) {
        Request.removeitem(view, "promos");
    }
    
    /**
     * 
     * @param view
     * @throws Exception
     */
    public final void save(View view) throws Exception {
        Request.save(view, this);
    }
    
    /**
     * 
     * @param view
     * @throws Exception
     */
    public final void show(View view) throws Exception {
        Request.show(view, this);
    }
    
    /**
     * 
     * @param view
     * @throws Exception
     */
    public final void update(View view) throws Exception {
        Request.update(view, this);
    }
}
