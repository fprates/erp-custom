package org.erp.custom.mm.materials;

import java.util.Date;

import org.iocaste.shell.common.Validator;
import org.iocaste.shell.common.ValidatorConfig;

public class DataInicialValidator implements Validator {
    private static final long serialVersionUID = 8547533754211230557L;

    /*
     * (non-Javadoc)
     * @see org.iocaste.shell.common.Validator#validate(
     *     org.iocaste.shell.common.ValidatorConfig)
     */
    @Override
    public final String validate(ValidatorConfig config) throws Exception {
        Date dtini = (Date)config.get("DT_INICIAL");
        Date dtfin = (Date)config.get("DT_FINAL");
        
        if (dtini.getTime() > dtfin.getTime())
            return "dtini.gt.dtfin";
        
        return null;
    }

}
