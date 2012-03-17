package org.erp.custom.sd.partner;

import org.iocaste.documents.common.DataElement;
import org.iocaste.documents.common.DataType;
import org.iocaste.documents.common.DocumentModel;
import org.iocaste.documents.common.DocumentModelItem;
import org.iocaste.packagetool.common.InstallData;

public class Install {
    private static final boolean IS_KEY = true;
    
    public static final InstallData self() {
        DataElement element;
        DocumentModelItem partnercode;
        InstallData data = new InstallData();
        DocumentModel address, partner = data.
                getModel("CUSTOM_PARTNER", "CPARTNER", "");
        
        element = data.getDataElement("CUSTOM_PARTNER.CODIGO", 0, 10,
                DataType.NUMC, false);
        partnercode = data.addModelItem(partner, "CODIGO", "ident", element,
                "codigo", IS_KEY);
        
        element = data.getDataElement("CUSTOM_PARTNER.RAZAO_SOCIAL", 0, 40,
                DataType.CHAR, DataType.UPPERCASE);
        data.addModelItem(partner, "RAZAO_SOCIAL", "NAME1", element,
                "razaoSocial", !IS_KEY);
        
        address = data.getModel("CUSTOM_PARTNER_ADDRESS", "CPARTNERADDR", "");
        
        element = data.getDataElement("CUSTOM_PARTNER_ADDRESS.ADDRESS_ID",
                0, 12, DataType.NUMC, false);
        data.addModelItem(address, "ADDRESS_ID", "IDENT", element, "idEndereco",
                IS_KEY);
        
        element = data.getDataElement("CUSTOM_PARTNER_ADDRESS.PARTNER_ID",
                0, 10, DataType.NUMC, false);
        data.addModelItem(address, "PARTNER_ID", "PRTNR", element, "idParceiro",
                !IS_KEY, partnercode);
        
        element = data.getDataElement("CUSTOM_PARTNER_ADDRESS.LOGRADOURO",
                0, 60, DataType.CHAR, DataType.UPPERCASE);
        data.addModelItem(address, "LOGRADOURO", "LOGRA", element, "logradouro",
                !IS_KEY);
        
        data.addNumberFactory("CUSTPARTNER");
        
        data.link("PARTNER", "erp-custom-sd.partner");
        data.link("XD01", "erp-custom-sd.partner");
        
        return data;
    }
}
