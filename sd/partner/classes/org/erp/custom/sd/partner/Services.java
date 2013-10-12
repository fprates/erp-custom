package org.erp.custom.sd.partner;

import org.erp.custom.sd.partner.common.PartnerData;
import org.iocaste.documents.common.Documents;
import org.iocaste.documents.common.ExtendedObject;
import org.iocaste.protocol.AbstractFunction;
import org.iocaste.protocol.Message;

public class Services extends AbstractFunction {

    public Services() {
        export("create", "create");
    }
    
    public final long create(Message message) {
        PartnerData data = message.get("data");
        Documents documents = new Documents(this);
        ExtendedObject partner = new ExtendedObject(
                documents.getModel("CUSTOM_PARTNER"));
        
        partner.setValue("NOME_FANTASIA", data.getName());
        partner.setValue("TIPO_PARCEIRO", data.getType());
        return create(partner);
    }
    
    public final long create(ExtendedObject partner) {
        Documents documents = new Documents(this);
        long codigo = documents.getNextNumber("CUSTPARTNER");
        
        partner.setValue("CODIGO", codigo);
        if (documents.save(partner) == 0)
            return 0;
        
        return codigo;
    }
}
