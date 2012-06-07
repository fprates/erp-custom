package org.erp.custom.sd.documents;

import java.math.BigDecimal;

import org.iocaste.documents.common.Documents;
import org.iocaste.documents.common.ExtendedObject;
import org.iocaste.shell.common.AbstractValidator;
import org.iocaste.shell.common.Shell;
import org.iocaste.shell.common.ValidatorConfig;

public class MaterialValidator extends AbstractValidator {
    private static final long serialVersionUID = 3262190318862757379L;

    /*
     * (non-Javadoc)
     * @see org.iocaste.shell.common.AbstractValidator#validate(
     *     org.iocaste.shell.common.ValidatorConfig)
     */
    @Override
    public final void validate(ValidatorConfig config) throws Exception {
        BigDecimal uprice;
        Documents documents;
        ExtendedObject[] objects;
        String query;
        String material = config.get("MATERIAL");
        
        if (Shell.isInitial(material))
            return;
        
        documents = new Documents(getFunction());
        query = "from PRECO_MATERIAL where MATERIAL = ?";
        objects = documents.select(query, material);
        if (objects == null)
            return;
        
        uprice = objects[0].getValue("VL_VENDA");
        config.set("PRECO_UNITARIO", uprice.floatValue());
    }

}
