package org.aegis.erp.portal;

import org.iocaste.appbuilder.common.PageBuilderContext;
import org.iocaste.appbuilder.common.cmodelviewer.AbstractModelViewer;
import org.iocaste.appbuilder.common.cmodelviewer.SelectConfig;
import org.iocaste.shell.common.DataForm;
import org.iocaste.shell.common.InputComponent;

public class CreateSelectConfig extends SelectConfig {

    public CreateSelectConfig() {
        super(AbstractModelViewer.CREATE, "AEGIS_DELIVERY");
    }

    @Override
    protected void execute(PageBuilderContext context) {
        DataForm form;
        InputComponent input;
        
        super.execute(context);
        form = getElement("head");
        form.get("NUMBER").setVisible(false);
        
        input = form.get("REFERENCE");
        input.setVisible(true);
        input.setObligatory(true);
    }
}
