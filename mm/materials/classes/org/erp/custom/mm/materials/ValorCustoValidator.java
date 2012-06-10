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
    public final void validate(ValidatorConfig config) throws Exception {
        double vlcusto = config.getInput("VL_CUSTO").get();
        double vlvenda = config.getInput("VL_VENDA").get();
        
        if (vlcusto > vlvenda)
            config.setMessage("vlcusto.gt.vlvenda");
    }

}
