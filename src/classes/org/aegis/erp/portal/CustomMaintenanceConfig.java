package org.aegis.erp.portal;

import org.iocaste.appbuilder.common.PageBuilderContext;
import org.iocaste.appbuilder.common.cmodelviewer.MaintenanceConfig;
import org.iocaste.appbuilder.common.tabletool.TableToolColumn;
import org.iocaste.appbuilder.common.tabletool.TableToolData;
import org.iocaste.shell.common.DataForm;

public class CustomMaintenanceConfig extends MaintenanceConfig {

    @Override
    protected void execute(PageBuilderContext context) {
        TableToolData tabletool;
        DataForm base;
        
        super.execute(context);
        
        base = getElement("base");
        context.function.validate(base.get("EXPIRES"), "expire_date");
        
        tabletool = getTableTool("items_table");
        tabletool.itemcolumn = "ITEM";
        new TableToolColumn(tabletool, "ITEM").disabled = true;
    }

}
