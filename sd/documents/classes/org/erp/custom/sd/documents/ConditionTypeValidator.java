package org.erp.custom.sd.documents;

import org.iocaste.documents.common.Documents;
import org.iocaste.documents.common.ExtendedObject;
import org.iocaste.shell.common.AbstractValidator;
import org.iocaste.shell.common.InputComponent;
import org.iocaste.shell.common.ValidatorConfig;

public class ConditionTypeValidator extends AbstractValidator {
    private static final long serialVersionUID = -6934599843175460569L;

    @Override
    public void validate(ValidatorConfig config) throws Exception {
        InputComponent input = config.getInput("CONDICAO");
        int condition = input.get();
        ExtendedObject object = new Documents(getFunction()).
                getObject("CUSTOM_CONDITION", condition);
        
        if (object == null)
            return;
        
        input.setText((String)object.getValue("TEXT"));
    }

}
