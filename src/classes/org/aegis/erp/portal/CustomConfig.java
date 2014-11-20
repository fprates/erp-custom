package org.aegis.erp.portal;

import org.iocaste.appbuilder.common.AbstractViewConfig;
import org.iocaste.appbuilder.common.PageBuilderContext;
import org.iocaste.appbuilder.common.tabletool.TableToolColumn;
import org.iocaste.appbuilder.common.tabletool.TableToolData;

public class CustomConfig extends AbstractViewConfig {

    @Override
    protected void execute(PageBuilderContext context) {
        TableToolData tabletool;
        
        tabletool = getTableTool("items_table");
        tabletool.itemcolumn = "ITEM";
        new TableToolColumn(tabletool, "ITEM").disabled = true;
    }

}
