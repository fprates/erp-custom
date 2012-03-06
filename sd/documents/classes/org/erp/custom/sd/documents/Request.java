package org.erp.custom.sd.documents;

import org.iocaste.shell.common.Table;
import org.iocaste.shell.common.TableItem;
import org.iocaste.shell.common.ViewData;

public class Request {

    public static final void add(ViewData view) {
        Table itens = (Table)view.getElement("itens");
        
        Common.insertItem(itens);
    }
    
    public static final void create(ViewData view) {
        view.setReloadableView(true);
        view.export("mode", Common.CREATE);
        view.redirect(null, "document");
    }
    
    public static final void display(ViewData view) {
        view.setReloadableView(true);
        view.export("mode", Common.SHOW);
        view.redirect(null, "document");
    }
    
    public static final void remove(ViewData view) {
        Table itens = (Table)view.getElement("itens");
        
        for (TableItem item : itens.getItens())
            if (item.isSelected())
                itens.remove(item);
    }
    
    public static final void save(ViewData view) {

    }
    
    public static final void update(ViewData view) {
        view.setReloadableView(true);
        view.export("mode", Common.UPDATE);
        view.redirect(null, "document");
    }
}
