package org.aegis.erp.portal;

import java.util.Date;

import org.iocaste.appbuilder.common.AbstractExtendedValidator;
import org.iocaste.appbuilder.common.PageBuilderContext;
import org.iocaste.shell.common.DataForm;

public class ExpireDateValidate extends AbstractExtendedValidator {
    private static final long serialVersionUID = 3239533886918646133L;

    @Override
    protected void validate(PageBuilderContext context) {
        DataForm form = getElement("base");
        Date documentdate = form.get("DATE").get();
        Date expiredate = form.get("EXPIRES").get();
        
        if (documentdate == null || expiredate == null)
            return;
        
        if (documentdate.after(expiredate))
            message("document.date.greater.than.expires");
    }

}
