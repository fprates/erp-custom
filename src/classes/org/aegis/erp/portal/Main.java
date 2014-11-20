package org.aegis.erp.portal;

import org.aegis.erp.portal.install.CustomerInstall;
import org.aegis.erp.portal.install.DeliveryInstall;
import org.aegis.erp.portal.install.MaterialInstall;
import org.aegis.erp.portal.install.TextsInstall;
import org.aegis.erp.portal.install.SalesDocumentsInstall;
import org.iocaste.appbuilder.common.AppBuilderLink;
import org.iocaste.appbuilder.common.PageBuilderContext;
import org.iocaste.appbuilder.common.PageBuilderDefaultInstall;
import org.iocaste.appbuilder.common.ViewContext;
import org.iocaste.appbuilder.common.cmodelviewer.AbstractModelViewer;

public class Main extends AbstractModelViewer {

    @Override
    public void config(PageBuilderContext context) throws Exception {
        ViewContext view;
        AppBuilderLink link;
        
        context.addManager("sd", new SDManager(context));
        view = context.instance("portal");
        view.set(new PortalSpec());
        view.set(new PortalConfig());
        for (String name : new String[] {
                "quotation",
                "delivery",
                "customer",
                "material",
                "parameters"
        })
            view.put(name, new DashboardRedirect(name));
        
        link = getReceivedLink();
        if (link == null)
            return;

        link.customconfig = new CustomConfig();
        loadManagedModule(context, link);
    }

    @Override
    protected void installConfig(PageBuilderDefaultInstall defaultinstall) {
        AppBuilderLink link;
        
        defaultinstall.setLink("AEGIS_PORTAL", "erp @portal");
        defaultinstall.addToTaskGroup("AEGIS", "AEGIS_PORTAL");
        defaultinstall.setProfile("AEGIS_PORTAL");
        
        link = defaultinstall.builderLinkInstance();
        link.change = "AEGISSDDOCCH";
        link.create = "AEGISSDDOCCR";
        link.display = "AEGISSDDOCDS";
        link.cmodel = "AEGIS_SD_DOCUMENT";
        link.entity = "sddocument";
        link.number = "AEGIS_SD";
        link.appname = "erp";

        link = defaultinstall.builderLinkInstance();
        link.change = "AEGISDLVRYCH";
        link.create = "AEGISDLVRYCR";
        link.display = "AEGISDLVRYDS";
        link.cmodel = "AEGIS_DELIVERY";
        link.entity = "delivery";
        link.number = "AEGISDLVRY";
        
        link = defaultinstall.builderLinkInstance();
        link.change = "AEGISMATRLCH";
        link.create = "AEGISMATRLCR";
        link.display = "AEGISMATRLDS";
        link.cmodel = "AEGIS_MATERIAL";
        link.entity = "material";
        
        link = defaultinstall.builderLinkInstance();
        link.change = "AEGISCSTMRCH";
        link.create = "AEGISCSTMRCR";
        link.display = "AEGISCSTMRDS";
        link.cmodel = "AEGIS_CUSTOMER";
        link.entity = "customer";
        
        defaultinstall.setLink("AEGISPAYMNT",
                "iocaste-dataeditor model=AEGIS_PAYMENT action=edit");
        
        installObject("messages", new TextsInstall());
        installObject("material", new MaterialInstall());
        installObject("customer", new CustomerInstall());
        installObject("sd-documents", new SalesDocumentsInstall());
        installObject("delivery", new DeliveryInstall());
    }

}
