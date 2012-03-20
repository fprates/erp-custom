package org.erp.custom.sd.partner;

import org.iocaste.documents.common.DataElement;
import org.iocaste.documents.common.DataType;
import org.iocaste.documents.common.DocumentModel;
import org.iocaste.documents.common.DocumentModelItem;
import org.iocaste.documents.common.DocumentModelKey;
import org.iocaste.packagetool.common.InstallData;

public class Install {
    
    public static final InstallData self() {
        DataElement element;
        DocumentModelItem item, partnercode;
        InstallData data = new InstallData();
        DocumentModel address, partner = data.
                getModel("CUSTOM_PARTNER", "CPARTNER", "");
        
        element = new DataElement();
        element.setName("CUSTOM_PARTNER.CODIGO");
        element.setLength(10);
        element.setType(DataType.NUMC);
        
        partnercode = new DocumentModelItem();
        partnercode.setDocumentModel(partner);
        partnercode.setName("CODIGO");
        partnercode.setTableFieldName("IDENT");
        partnercode.setDataElement(element);
        partnercode.setIndex(0);
        
        partner.add(partnercode);
        partner.add(new DocumentModelKey(partnercode));
        
        element = new DataElement();
        element.setName("CUSTOM_PARTNER.RAZAO_SOCIAL");
        element.setLength(40);
        element.setType(DataType.CHAR);
        element.setUpcase(true);
        
        item = new DocumentModelItem();
        item.setDocumentModel(partner);
        item.setName("RAZAO_SOCIAL");
        item.setTableFieldName("NAME1");
        item.setDataElement(element);
        item.setIndex(1);
        
        partner.add(item);
        
        address = data.getModel("CUSTOM_PARTNER_ADDRESS", "CPARTNERADDR", "");
        
        element = new DataElement();
        element.setName("CUSTOM_PARTNER_ADDRESS.ADDRESS_ID");
        element.setLength(12);
        element.setType(DataType.NUMC);
        
        item = new DocumentModelItem();
        item.setDocumentModel(address);
        item.setName("ADDRESS_ID");
        item.setTableFieldName("IDENT");
        item.setDataElement(element);
        item.setIndex(0);
        
        address.add(item);
        address.add(new DocumentModelKey(item));
        
        element = new DataElement();
        element.setName("CUSTOM_PARTNER_ADDRESS.PARTNER_ID");
        element.setLength(10);
        element.setType(DataType.NUMC);
        
        item = new DocumentModelItem();
        item.setDocumentModel(address);
        item.setName("PARTNER_ID");
        item.setTableFieldName("PRTNR");
        item.setDataElement(element);
        item.setReference(partnercode);
        item.setIndex(1);
        
        address.add(item);
        
        element = new DataElement();
        element.setName("CUSTOM_PARTNER_ADDRESS.LOGRADOURO");
        element.setLength(60);
        element.setType(DataType.CHAR);
        element.setUpcase(true);
        
        item = new DocumentModelItem();
        item.setDocumentModel(address);
        item.setName("LOGRADOURO");
        item.setTableFieldName("LOGRA");
        item.setDataElement(element);
        item.setIndex(2);
        
        address.add(item);
        
        data.addNumberFactory("CUSTPARTNER");
        data.link("PARTNER", "erp-custom-sd.partner");
        data.link("XD01", "erp-custom-sd.partner");
        
        return data;
    }
}
