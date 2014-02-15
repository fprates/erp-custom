package org.erp.custom.sd.documents;

import org.iocaste.documents.common.ExtendedObject;
import org.iocaste.packagetool.common.InstallData;
import org.iocaste.protocol.Message;
import org.iocaste.shell.common.AbstractPage;
import org.iocaste.shell.common.PageContext;
import org.iocaste.shell.common.View;

public class Main extends AbstractPage {
    private Context context;
    
    public Main() {
        export("install", "install");
    }

    public final void add() {
        Request.add(context.view);
    }
    
    public final void condadd() {
        Request.condadd(context.view);
    }
    
    public final void condapply() {
        Request.condapply(context);
        back();
    }
    
    public final void condcancel() {
        back();
    }
    
    public final void condform() {
        Response.condform(context);
    }
    
    public final void conditions() {
        Request.conditions(context.view);
    }
    
    public final void condremove() {
        Request.condremove(context.view);
    }
    
    public final void create() {
        Request.create(context.view);
    }
    
    public final void display() {
        Request.display(context);
    }
    
    public final void document() {
        Response.document(context);
    }
    
    @Override
    public final PageContext init(View view) {
        context = new Context();
        
        return context;
    }
    public final InstallData install(Message message) {
        return Install.init(this);
    }
    
    public final void main() {
        Response.main(context);
    }
    
    public final void remove() {
        Request.remove(context.view);
    }
    
    public final void save() {
        Request.save(context);
    }
    
    public final void update() {
        Request.update(context);
    }
    
    public final void validate() {
        ExtendedObject[] objects;
        
        if (Common.getMode(context.view) == Common.SHOW)
            return;
        
        objects = context.view.getParameter("conditions");
        Request.totalAmountUpdate(context.view, objects);
    }
    
    public final void validatecond() { }
}

class Context extends PageContext { }