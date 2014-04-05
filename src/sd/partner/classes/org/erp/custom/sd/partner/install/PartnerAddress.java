package org.erp.custom.sd.partner.install;

import org.iocaste.documents.common.DataElement;
import org.iocaste.documents.common.DataType;
import org.iocaste.documents.common.DocumentModelItem;
import org.iocaste.documents.common.DocumentModelKey;

public class PartnerAddress {

    public static final void install(InstallContext context) {
        DataElement element;
        DocumentModelItem item, partnercode, addresstypecode;
        
        context.address = context.data.getModel(
                "CUSTOM_PARTNER_ADDRESS", "CPARTNERADDR", null);
        
        element = new DataElement("CUSTOM_PARTNER_ADDRESS.CODIGO");
        element.setLength(12);
        element.setType(DataType.NUMC);
        item = new DocumentModelItem("CODIGO");
        item.setTableFieldName("IDENT");
        item.setDataElement(element);
        context.address.add(item);
        context.address.add(new DocumentModelKey(item));
        
        partnercode = context.partner.getModelItem("CODIGO");
        item = new DocumentModelItem("PARTNER_ID");
        item.setTableFieldName("PRTNR");
        item.setDataElement(partnercode.getDataElement());
        item.setReference(partnercode);
        context.address.add(item);

        addresstypecode = context.addresstype.getModelItem("CODIGO");
        item = new DocumentModelItem("TIPO_ENDERECO");
        item.setTableFieldName("TPEND");
        item.setDataElement(addresstypecode.getDataElement());
        item.setReference(addresstypecode);
        item.setSearchHelp("SH_ADDRESS_TYPE");
        context.address.add(item);
        
        element = new DataElement("CUSTOM_PARTNER_ADDRESS.LOGRADOURO");
        element.setLength(60);
        element.setType(DataType.CHAR);
        element.setUpcase(true);
        item = new DocumentModelItem("LOGRADOURO");
        item.setTableFieldName("LOGRA");
        item.setDataElement(element);
        context.address.add(item);
        
        element = new DataElement("CUSTOM_PARTNER_ADDRESS.CEP");
        element.setLength(8);
        element.setType(DataType.NUMC);
        item = new DocumentModelItem("CEP");
        item.setTableFieldName("CPSTL");
        item.setDataElement(element);
        context.address.add(item);
        
        element = new DataElement("CUSTOM_PARTNER_ADDRESS.BAIRRO");
        element.setLength(30);
        element.setType(DataType.CHAR);
        element.setUpcase(true);
        item = new DocumentModelItem("BAIRRO");
        item.setTableFieldName("NEIGH");
        item.setDataElement(element);
        context.address.add(item);
        
        element = new DataElement("CUSTOM_PARTNER_ADDRESS.CIDADE");
        element.setLength(30);
        element.setType(DataType.CHAR);
        element.setUpcase(true);
        item = new DocumentModelItem("CIDADE");
        item.setTableFieldName("CITY");
        item.setDataElement(element);
        context.address.add(item);
        
        item = new DocumentModelItem("REGION");
        item.setTableFieldName("REGCD");
        item.setDataElement(context.region.getDataElement());
        item.setReference(context.region);
        item.setSearchHelp("SH_REGION");
        context.address.add(item);
        
        element = new DataElement("CUSTOM_PARTNER_ADDRESS.TELEFONE");
        element.setLength(16);
        element.setType(DataType.CHAR);
        element.setUpcase(true);
        item = new DocumentModelItem("TELEFONE");
        item.setTableFieldName("TEL01");
        item.setDataElement(element);
        context.address.add(item);
        
        element = new DataElement("CUSTOM_PARTNER_ADDRESS.EMAIL");
        element.setLength(80);
        element.setType(DataType.CHAR);
        element.setUpcase(false);
        item = new DocumentModelItem("EMAIL");
        item.setTableFieldName("EMAIL");
        item.setDataElement(element);
        context.address.add(item);
        
        element = new DataElement("CUSTOM_PARTNER_ADDRESS.WEB_PAGE");
        element.setLength(80);
        element.setType(DataType.CHAR);
        element.setUpcase(false);
        item = new DocumentModelItem("WEB_PAGE");
        item.setTableFieldName("WPAGE");
        item.setDataElement(element);
        context.address.add(item);
    }
}
