package org.erp.custom.sd.partner;

import org.iocaste.documents.common.Documents;
import org.iocaste.documents.common.ExtendedObject;
import org.iocaste.shell.common.AbstractValidator;
import org.iocaste.shell.common.InputComponent;
import org.iocaste.shell.common.ValidatorConfig;

public class AddressTypeValidator extends AbstractValidator {
    private static final long serialVersionUID = 8830543096251078321L;

    @Override
    public void validate(ValidatorConfig config) throws Exception {
        InputComponent input = config.getInput("TIPO_ENDERECO");
        int addresstype = input.get();
        ExtendedObject object = new Documents(getFunction()).
                getObject("CUSTOM_ADDRESS_TYPE", addresstype);
        
        if (object == null)
            return;
        
        input.setText((String)object.getValue("DESCRICAO"));
    }

}
