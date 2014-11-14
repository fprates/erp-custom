package org.aegis.erp.portal;

import org.iocaste.appbuilder.common.AbstractActionHandler;
import org.iocaste.appbuilder.common.PageBuilderContext;

public class DashboardRedirect extends AbstractActionHandler {
    private String dash;
    
    public DashboardRedirect(String dash) {
        this.dash = dash;
    }
    
    @Override
    protected void execute(PageBuilderContext context) throws Exception {
        String task = dbactionget("portal", dash);
        taskredirect(task);
    }

}
