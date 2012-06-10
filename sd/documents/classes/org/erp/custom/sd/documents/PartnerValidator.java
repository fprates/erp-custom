package org.erp.custom.sd.documents;

import org.iocaste.documents.common.Documents;
import org.iocaste.documents.common.ExtendedObject;
import org.iocaste.shell.common.AbstractValidator;
import org.iocaste.shell.common.InputComponent;
import org.iocaste.shell.common.ValidatorConfig;

public class PartnerValidator extends AbstractValidator {
    private static final long serialVersionUID = -5607215329992958644L;

    @Override
    public void validate(ValidatorConfig config) throws Exception {
        Documents documents;
        ExtendedObject partner;
        InputComponent receiver = config.getInput("RECEIVER");
        long partnerid = receiver.get();
        
        if (partnerid == 0)
            return;
        
        documents = new Documents(getFunction());
        partner = documents.getObject("CUSTOM_PARTNER", partnerid);
        
        if (partner == null)
            return;
        
        receiver.setText((String)partner.getValue("RAZAO_SOCIAL"));
    }

}
