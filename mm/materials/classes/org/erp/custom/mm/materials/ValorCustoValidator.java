package org.erp.custom.mm.materials;

import org.iocaste.shell.common.AbstractValidator;
import org.iocaste.shell.common.ValidatorConfig;

public class ValorCustoValidator extends AbstractValidator {
    private static final long serialVersionUID = 7760956874809247964L;

    /*
     * (non-Javadoc)
     * @see org.iocaste.shell.common.Validator#validate(
     *     org.iocaste.shell.common.ValidatorConfig)
     */
    @Override
    public final String validate(ValidatorConfig config) throws Exception {
        double vlcusto = (Double)config.get("VL_CUSTO");
        double vlvenda = (Double)config.get("VL_VENDA");
        
        if (vlcusto > vlvenda)
            return "vlcusto.gt.vlvenda";
        
        return null;
    }

}
