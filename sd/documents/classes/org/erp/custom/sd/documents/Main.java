package org.erp.custom.sd.documents;

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
    
    public final void condapply(View view) throws Exception {
        Request.condapply(view);
        back(view);
    }
    
    public final void condcancel(View view) throws Exception {
        back(view);
    }
    
    public final void condform(View view) throws Exception {
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
    
    public final void display(View view) throws Exception {
        Request.display(view, this);
    }
    
    public final void document(View view) throws Exception {
        Response.document(view, this);
    }
    
    public final InstallData install(Message message) throws Exception {
        return Install.init(this);
    }
    
    public final void main(View view) throws Exception {
        Response.main(view, this);
    }
    
    public final void remove(View view) {
        Request.remove(view);
    }
    
    public final void save(View view) throws Exception {
        Request.save(view, this);
    }
    
    public final void update(View view) throws Exception {
        Request.update(view, this);
    }
    
    public final void validate(View view) { }
}
