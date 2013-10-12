package org.erp.custom.sd.partner;

import org.iocaste.documents.common.Documents;
import org.iocaste.documents.common.ExtendedObject;
import org.iocaste.shell.common.AbstractValidator;
import org.iocaste.shell.common.InputComponent;
import org.iocaste.shell.common.ValidatorConfig;

public class PartnerTypeValidator extends AbstractValidator {
    private static final long serialVersionUID = -2541659737662947619L;

    /*
     * (non-Javadoc)
     * @see org.iocaste.shell.common.AbstractValidator#validate(
     *     org.iocaste.shell.common.ValidatorConfig)
     */
    @Override
    public void validate(ValidatorConfig config) throws Exception {
        InputComponent input = config.getInput("TIPO_PARCEIRO");
        String partnertype = input.get();
        ExtendedObject object = new Documents(getFunction()).
                getObject("CUSTOM_PARTNER_TYPE", partnertype);
        
        if (object == null)
            return;
        
        input.setText((String)object.getValue("DESCRICAO"));
    }

}
