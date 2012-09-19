package org.erp.custom.mm.materials;

import org.iocaste.documents.common.Documents;
import org.iocaste.documents.common.ExtendedObject;
import org.iocaste.shell.common.AbstractValidator;
import org.iocaste.shell.common.ValidatorConfig;

public class MaterialGroupValidator extends AbstractValidator {
    private static final long serialVersionUID = -6714653005922900192L;
    
    /*
     * (non-Javadoc)
     * @see org.iocaste.shell.common.AbstractValidator#
     *     validate(org.iocaste.shell.common.ValidatorConfig)
     */
    @Override
    public void validate(ValidatorConfig config) throws Exception {
        ExtendedObject object;
        String text = config.getInput("MAT_GROUP").get();
        
        if (text == null)
            return;
        
        object = new Documents(getFunction()).
                getObject("MATERIAL_GROUP", text);
        
        if (object == null)
            return;
        
        text = object.getValue("TEXT");
        config.getInput("MAT_GROUP").setText(text);
    }

}
