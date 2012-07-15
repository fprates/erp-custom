package org.erp.custom.sd.documents;

import org.iocaste.documents.common.ExtendedObject;
import org.iocaste.packagetool.common.InstallData;
import org.iocaste.protocol.Message;
import org.iocaste.shell.common.AbstractPage;
import org.iocaste.shell.common.View;

public class Main extends AbstractPage {
    
    public Main() {
        export("install", "install");
    }

    public final void add(View view) {
        Request.add(view);
    }
    
    public final void condadd(View view) {
        Request.condadd(view);
    }
    
    public final void condapply(View view) {
        Request.condapply(view, this);
        back(view);
    }
    
    public final void condcancel(View view) {
        back(view);
    }
    
    public final void condform(View view) {
        Response.condform(view, this);
    }
    
    public final void conditions(View view) {
        Request.conditions(view);
    }
    
    public final void condremove(View view) {
        Request.condremove(view);
    }
    
    public final void create(View view) {
        Request.create(view);
    }
    
    public final void display(View view) {
        Request.display(view, this);
    }
    
    public final void document(View view) {
        Response.document(view, this);
    }
    
    public final InstallData install(Message message) {
        return Install.init(this);
    }
    
    public final void main(View view) {
        Response.main(view, this);
    }
    
    public final void remove(View view) {
        Request.remove(view);
    }
    
    public final void save(View view) {
        Request.save(view, this);
    }
    
    public final void update(View view) {
        Request.update(view, this);
    }
    
    public final void validate(View view) {
        ExtendedObject[] objects;
        
        if (Common.getMode(view) == Common.SHOW)
            return;
        
        objects = view.getParameter("conditions");
        Request.totalAmountUpdate(view, objects);
    }
    
    public final void validatecond(View view) { }
}
