package org.erp.custom.mm.materials;

import java.util.Date;

import org.iocaste.shell.common.AbstractValidator;
import org.iocaste.shell.common.ValidatorConfig;

public class DataInicialValidator extends AbstractValidator {
    private static final long serialVersionUID = 8547533754211230557L;

    /*
     * (non-Javadoc)
     * @see org.iocaste.shell.common.Validator#validate(
     *     org.iocaste.shell.common.ValidatorConfig)
     */
    @Override
    public final void validate(ValidatorConfig config) throws Exception {
        Date dtini = (Date)config.get("DT_INICIAL");
        Date dtfin = (Date)config.get("DT_FINAL");
        
        if (dtini == null || dtfin == null)
            return;
        
        if (dtini.getTime() > dtfin.getTime())
            config.setMessage("dtini.gt.dtfin");
    }

}
