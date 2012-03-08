package org.erp.custom.sd.documents;

import org.iocaste.shell.common.AbstractPage;
import org.iocaste.shell.common.ViewData;

public class Main extends AbstractPage {

    public final void add(ViewData view) {
        Request.add(view);
    }
    
    public final void create(ViewData view) {
        Request.create(view);
    }
    
    public final void display(ViewData view) throws Exception {
        Request.display(view, this);
    }
    
    public final void document(ViewData view) throws Exception {
        Response.document(view, this);
    }
    
    public final void main(ViewData view) {
        Response.main(view);
    }
    
    public final void remove(ViewData view) {
        Request.remove(view);
    }
    
    public final void save(ViewData view) throws Exception {
        Request.save(view, this);
    }
    
    public final void update(ViewData view) throws Exception {
        Request.update(view, this);
    }
}
