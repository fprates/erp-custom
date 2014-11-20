package org.aegis.erp.portal;

import org.iocaste.appbuilder.common.AbstractViewSpec;

public class PortalSpec extends AbstractViewSpec {

    @Override
    protected void execute() {
        form("main");
        navcontrol("main");
        dashboard("main", "portal");
        dashboardgroup("portal", "quotation");
        dashboardgroup("portal", "delivery");
        dashboardgroup("portal", "customer");
        dashboardgroup("portal", "material");
        dashboardgroup("portal", "parameters");
    }

}
