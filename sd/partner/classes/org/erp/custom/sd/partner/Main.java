package org.erp.custom.sd.partner;

import org.iocaste.documents.common.DocumentModel;
import org.iocaste.documents.common.Documents;
import org.iocaste.packagetool.common.InstallData;
import org.iocaste.protocol.Message;
import org.iocaste.shell.common.AbstractPage;
import org.iocaste.shell.common.Button;
import org.iocaste.shell.common.Container;
import org.iocaste.shell.common.DataForm;
import org.iocaste.shell.common.Table;
import org.iocaste.shell.common.ViewData;


public class Main extends AbstractPage {
    
    public Main() {
        export("install", "install");
    }
    
    /**
     * 
     * @param view
     */
    public final void addaddress(ViewData view) throws Exception {
        Button button;
        Container container;
        DataForm address = view.getElement("address");
        Table itens = view.getElement("addresses");
        
        if (itens.length() == 0) {
            itens.setVisible(true);
            
            button = view.getElement("removeaddress");
            button.setVisible(true);
            button = view.getElement("editaddress");
            button.setVisible(true);
        }

        container = view.getElement("addresscnt");
        Request.additem(view, itens, address.getObject(), container);
        
        address.clearInputs();
    }
    
    /**
     * 
     * @param view
     */
    public final void addcontact(ViewData view) {
        Button button;
        Container container;
        DataForm contact = view.getElement("contact");
        Table itens = view.getElement("contacts");
        
        if (itens.length() == 0) {
            itens.setVisible(true);
            
            button = view.getElement("removecontact");
            button.setVisible(true);
            button = view.getElement("editcontact");
            button.setVisible(true);
        }
        
        container = view.getElement("contactcnt");
        Request.additem(view, itens, null, container);
        
        contact.clearInputs();
    }
    
    /**
     * 
     * @param view
     * @throws Exception
     */
    public final void addressmark(ViewData view) throws Exception {
        Request.addressmark(view);
    }
    
    /**
     * 
     * @param view
     */
    public final void create(ViewData view) {
        Request.create(view);
    }
    
    /**
     * 
     * @param view
     * @throws Exception
     */
    public final void identity(ViewData view) throws Exception {
        Documents documents = new Documents(this);
        DocumentModel[] models = new DocumentModel[3];
        
        models[Common.IDENTITY] = documents.getModel("CUSTOM_PARTNER");
        models[Common.ADDRESS] = documents.getModel("CUSTOM_PARTNER_ADDRESS");
        models[Common.CONTACT] = documents.getModel("CUSTOM_PARTNER_CONTACT");
        
        Response.identity(view, models);
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
     */
    public final void removeaddress(ViewData view) {
        Button button;
        Table itens = view.getElement("addresses");
        DataForm address = view.getElement("address");
        
        Request.removeitem(view, "addresses");
        
        if (itens.length() > 0)
            return;
        
        itens.setVisible(false);
        
        button = view.getElement("removeaddress");
        button.setVisible(false);
        button = view.getElement("editaddress");
        button.setVisible(false);
        
        address.clearInputs();
    }
    
    /**
     * 
     * @param view
     */
    public final void removecontact(ViewData view) {
        Button button;
        Table itens = view.getElement("contacts");
        DataForm contact = view.getElement("contact");
        
        Request.removeitem(view, "contacts");
        
        if (itens.length() > 0)
            return;
        
        itens.setVisible(false);
        
        button = view.getElement("removecontact");
        button.setVisible(false);
        button = view.getElement("removecontact");
        button.setVisible(false);
        
        contact.clearInputs();
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
        Request.load(view, this, Common.SHOW);
    }

    /**
     * 
     * @param view
     * @throws Exception
     */
    public final void update(ViewData view) throws Exception {
        Request.load(view, this, Common.UPDATE);
    }
}
