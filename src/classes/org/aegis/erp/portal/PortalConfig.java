package org.aegis.erp.portal;

import org.iocaste.appbuilder.common.AbstractViewConfig;
import org.iocaste.appbuilder.common.PageBuilderContext;
import org.iocaste.appbuilder.common.dashboard.DashboardComponent;
import org.iocaste.appbuilder.common.dashboard.DashboardFactory;

public class PortalConfig extends AbstractViewConfig {

    @Override
    protected void execute(PageBuilderContext context) {
        DashboardComponent component;
        DashboardFactory factory;
        
        factory = getDashboard("portal");
        factory.setArea(100, 100, "%");
        factory.setPadding(1, "%");
        factory.setColor("white");
        factory.isometricGrid();
        
        component = getDashboardItem("portal", "quotation");
        component.setBorderColor("#ffffff");
        component.setColor("#aaaaff");
        component.addText("budget");
        component.add("AEGISSDDOCCR");
        component.add("AEGISSDDOCDS");
        component.add("AEGISSDDOCCH");
        
        component = getDashboardItem("portal", "customer");
        component.setBorderColor("#ffffff");
        component.setColor("#aaaaaa");
        component.addText("customer");
        component.add("AEGISCSTMRCR");
        component.add("AEGISCSTMRDS");
        component.add("AEGISCSTMRCH");
        
        component = getDashboardItem("portal", "material");
        component.setBorderColor("#ffffff");
        component.setColor("#aaaa44");
        component.addText("material");
        component.add("AEGISMATRLCR");
        component.add("AEGISMATRLDS");
        component.add("AEGISMATRLCH");
        
        component = getDashboardItem("portal", "parameters");
        component.setBorderColor("#ffffff");
        component.setColor("#aaaa00");
        component.addText("parameters");
        component.add("AEGISPAYMNT");
    }

}
