package org.erp.custom.sd.partner;

import org.erp.custom.sd.partner.common.PartnerData;
import org.iocaste.documents.common.Documents;
import org.iocaste.documents.common.ExtendedObject;
import org.iocaste.protocol.AbstractFunction;
import org.iocaste.protocol.Function;
import org.iocaste.protocol.Message;

public class Services extends AbstractFunction {

    public Services() {
        export("create", "create");
        export("load", "load");
    }
    
    public final long create(Message message) {
        PartnerData data = message.get("data");
        Documents documents = new Documents(this);
        ExtendedObject partner = new ExtendedObject(
                documents.getModel("CUSTOM_PARTNER"));
        
        partner.setInstance(data);
        return create(partner, this);
    }
    
    public final long create(ExtendedObject partner, Function function) {
        Documents documents = new Documents(function);
        long codigo = documents.getNextNumber("CUSTPARTNER");
        
        partner.setValue("CODIGO", codigo);
        if (documents.save(partner) == 0)
            return 0;
        
        return codigo;
    }
    
    public final PartnerData load(Message message) {
        long id = message.get("id");
        ExtendedObject object = load(id, this);
        
        if (object == null)
            return null;
        
        return object.newInstance();
    }
    
    public final ExtendedObject load(long id, Function function) {
        Documents documents = new Documents(function);
        
        return documents.getObject("CUSTOM_PARTNER", id);
    }
}
