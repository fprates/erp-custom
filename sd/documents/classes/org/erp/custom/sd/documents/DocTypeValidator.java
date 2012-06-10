package org.erp.custom.sd.documents;

import org.iocaste.documents.common.Documents;
import org.iocaste.documents.common.ExtendedObject;
import org.iocaste.shell.common.AbstractValidator;
import org.iocaste.shell.common.InputComponent;
import org.iocaste.shell.common.ValidatorConfig;

public class DocTypeValidator extends AbstractValidator {
    private static final long serialVersionUID = 5002180489843804316L;

    @Override
    public void validate(ValidatorConfig config) throws Exception {
        ExtendedObject object;
        InputComponent input = config.getInput("TIPO");
        String tipo = input.get();
        
        if (tipo == null)
            return;
        
        object = new Documents(getFunction()).
                getObject("CUSTOM_SD_DOCTYPE", tipo);
        
        if (object == null)
            return;
        
        input.setText((String)object.getValue("TEXT"));
    }

}
