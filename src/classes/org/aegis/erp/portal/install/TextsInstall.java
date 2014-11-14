package org.aegis.erp.portal.install;

import org.iocaste.appbuilder.common.AbstractInstallObject;
import org.iocaste.appbuilder.common.MessagesInstall;
import org.iocaste.appbuilder.common.StandardInstallContext;

public class TextsInstall extends AbstractInstallObject {

    @Override
    protected void execute(StandardInstallContext context) throws Exception {
        MessagesInstall messages = messageInstance("pt_BR");
        
        messages.put("AEGIS_PORTAL", "Portal");
        
        messages.put("AEGISCSTMRCH", "Editar");
        messages.put("AEGISCSTMRCR", "Criar");
        messages.put("AEGISCSTMRDS", "Exibir");
        
        messages.put("AEGISMATRLCH", "Editar");
        messages.put("AEGISMATRLCR", "Criar");
        messages.put("AEGISMATRLDS", "Exibir");
        
        messages.put("AEGISPAYMNT", "Condições de pagamento");
        
        messages.put("AEGISSDDOCCH", "Editar");
        messages.put("AEGISSDDOCCR", "Criar");
        messages.put("AEGISSDDOCDS", "Exibir");
        
        messages.put("budget", "Orçamento");
        messages.put("customer", "Clientes");
        messages.put("material", "Materiais");
        messages.put("parameters", "Parâmetros");
        
    }

}
