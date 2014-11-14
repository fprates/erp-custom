package org.aegis.erp.portal;

import org.iocaste.appbuilder.common.PageBuilderContext;
import org.iocaste.docmanager.common.AbstractManager;

public class SDManager extends AbstractManager {

    public SDManager(PageBuilderContext context) {
        super("AEGIS_SD_DOCUMENT", context.function);
        String[] messages = new String[3];
        
        messages[EEXISTS] = "document.exists";
        messages[EINVALID] = "invalid.document";
        messages[SAVED] = "document.saved";
        setMessages(messages);
    }

}
