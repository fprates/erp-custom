package org.erp.custom.sd.partner.common;

import org.iocaste.protocol.AbstractServiceInterface;
import org.iocaste.protocol.Function;
import org.iocaste.protocol.Message;

public class Partner extends AbstractServiceInterface {
    private static final String SERVERNAME =
            "/erp-custom-sd.partner/services.html";
    
    public Partner(Function function) {
        initService(function, SERVERNAME);
    }
    
    public final long create(PartnerData data) {
        Message message = new Message();
        
        message.setId("create");
        message.add("data", data);
        return call(message);
    }
    
    public final PartnerData load(long id) {
        Message message = new Message();
        
        message.setId("load");
        message.add("id", id);
        return call(message);
    }
}
