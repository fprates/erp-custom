package org.erp.custom.sd.documents;

import org.iocaste.shell.common.AbstractPage;
import org.iocaste.shell.common.Button;
import org.iocaste.shell.common.Const;
import org.iocaste.shell.common.Container;
import org.iocaste.shell.common.DataForm;
import org.iocaste.shell.common.DataItem;
import org.iocaste.shell.common.Form;
import org.iocaste.shell.common.ViewData;

public class Main extends AbstractPage {

    public final void create(ViewData view) {
        
    }
    
    public final void display(ViewData view) {
        
    }
    
    public final void main(ViewData view) {
        Container container = new Form(null, "main");
        DataForm form = new DataForm(container, "selection");
        
        new DataItem(form, Const.TEXT_FIELD, "document");
        new Button(container, "display");
        new Button(container, "create");
        new Button(container, "update");
        
        view.setFocus("document");
        view.setNavbarActionEnabled("back", true);
        view.setTitle("document-selection");
        view.addContainer(container);
    }
    
    public final void update(ViewData view) {
        
    }
}
