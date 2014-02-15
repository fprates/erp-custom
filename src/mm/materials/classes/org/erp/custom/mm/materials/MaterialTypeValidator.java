package org.erp.custom.mm.materials;

import org.iocaste.documents.common.Documents;
import org.iocaste.documents.common.ExtendedObject;
import org.iocaste.shell.common.AbstractValidator;
import org.iocaste.shell.common.ValidatorConfig;

public class MaterialTypeValidator extends AbstractValidator {
    private static final long serialVersionUID = -1891813409804445743L;

    @Override
    public void validate(ValidatorConfig config) throws Exception {
        ExtendedObject object;
        String text = config.getInput("MAT_TYPE").get();
        
        if (text == null)
            return;
        
        object = new Documents(getFunction()).
                getObject("MATERIAL_TYPE", text);
        
        if (object == null)
            return;
        
        text = object.get("TEXT");
        config.getInput("MAT_TYPE").setText(text);
    }

}
