package org.erp.custom.sd.partner.install;

import org.iocaste.documents.common.DataElement;
import org.iocaste.documents.common.DataType;
import org.iocaste.documents.common.DocumentModelItem;
import org.iocaste.documents.common.DocumentModelKey;

public class PartnerContact {
    public static final void install(InstallContext context) {
        DataElement element;
        DocumentModelItem item, addresscode;
        
        context.contact = context.data.getModel("CUSTOM_PARTNER_CONTACT",
                "CPRTNRCNTCT", null);
        
        element = new DataElement("CUSTOM_PARTNER_CONTACT.CODIGO");
        element.setLength(12);
        element.setType(DataType.NUMC);
        item = new DocumentModelItem("CODIGO");
        item.setDataElement(element);
        item.setTableFieldName("IDENT");
        context.contact.add(item);
        context.contact.add(new DocumentModelKey(item));
        
        item = new DocumentModelItem("PARTNER_ID");
        item.setTableFieldName("PRTNR");
        item.setDataElement(context.address.getModelItem("PARTNER_ID").
                getDataElement());
        context.contact.add(item);
        
        element = new DataElement("CUSTOM_PARTNER_CONTACT.NOME");
        element.setLength(40);
        element.setType(DataType.CHAR);
        element.setUpcase(true);
        item = new DocumentModelItem("PNOME");
        item.setTableFieldName("NAME1");
        item.setDataElement(element);
        context.contact.add(item);
        
        item = new DocumentModelItem("UNOME");
        item.setTableFieldName("NAME2");
        item.setDataElement(element);
        context.contact.add(item);
        
        addresscode = context.address.getModelItem("CODIGO");
        item = new DocumentModelItem("ADDRESS");
        item.setTableFieldName("ADDRS");
        item.setDataElement(addresscode.getDataElement());
        item.setReference(addresscode);
        context.contact.add(item);
    }

}
