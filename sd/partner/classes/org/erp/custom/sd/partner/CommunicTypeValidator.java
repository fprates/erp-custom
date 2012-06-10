package org.erp.custom.sd.partner;

import org.iocaste.documents.common.Documents;
import org.iocaste.documents.common.ExtendedObject;
import org.iocaste.shell.common.AbstractValidator;
import org.iocaste.shell.common.InputComponent;
import org.iocaste.shell.common.ValidatorConfig;

public class CommunicTypeValidator extends AbstractValidator {
    private static final long serialVersionUID = 5004765898531809356L;

    @Override
    public void validate(ValidatorConfig config) throws Exception {
        InputComponent input = config.getInput("TP_COMMUNIC");
        int communictype = input.get();
        ExtendedObject object = new Documents(getFunction()).
                getObject("CUSTOM_COMMUNICATION", communictype);
        
        if (object == null)
            return;
        
        input.setText((String)object.getValue("DESCRICAO"));
    }

}
