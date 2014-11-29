package org.aegis.erp.portal;

import org.iocaste.appbuilder.common.AbstractActionHandler;
import org.iocaste.appbuilder.common.PageBuilderContext;

public class CreateSelectValidate extends AbstractActionHandler {
    
    @Override
    protected void execute(PageBuilderContext context) throws Exception {
        long reference = getdfl("head", "REFERENCE");
        ERPContext extcontext = getExtendedContext();
        
        extcontext.reference = getDocument("sd", reference);
        init(extcontext.link.create1view, extcontext);
        redirect(extcontext.link.create1view);
    }

}
