package org.erp.custom.sd.partner.install;

import org.iocaste.documents.common.DataElement;
import org.iocaste.documents.common.DataType;
import org.iocaste.documents.common.DocumentModelItem;
import org.iocaste.documents.common.DocumentModelKey;

public class ContactCommunication {
    public static final void install(InstallContext context) {
        DataElement element;
        DocumentModelItem item, modelitem;
        
        context.communication = context.data.getModel("CUSTOM_PARTNER_COMM",
                "CPARTNERCOMM", null);
        
        /*
         * comunição do contato
         */
        element = new DataElement("CUSTOM_PARTNER_COMM.CODIGO");
        element.setLength(12);
        element.setType(DataType.NUMC);
        item = new DocumentModelItem("CODIGO");
        item.setTableFieldName("IDENT");
        item.setDataElement(element);
        context.communication.add(item);
        context.communication.add(new DocumentModelKey(item));
        
        /*
         * parceiro
         */
        modelitem = context.partner.getModelItem("CODIGO");
        element = modelitem.getDataElement();
        item = new DocumentModelItem("PARTNER_ID");
        item.setTableFieldName("PRTNR");
        item.setDataElement(element);
        item.setReference(modelitem);
        context.communication.add(item);
        
        /*
         * contato
         */
        modelitem = context.contact.getModelItem("CODIGO");
        element = modelitem.getDataElement();
        item = new DocumentModelItem("CONTACT_ID");
        item.setTableFieldName("CNTCT");
        item.setDataElement(element);
        item.setReference(modelitem);
        context.communication.add(item);
        
        /*
         * tipo de comunicação
         */
        modelitem = context.communication.getModelItem("CODIGO");
        element = modelitem.getDataElement();
        item = new DocumentModelItem("TP_COMMUNIC");
        item.setTableFieldName("TPCOM");
        item.setDataElement(element);
        item.setReference(modelitem);
        item.setSearchHelp("SH_COMMUNICATION");
        context.communication.add(item);
        
        /*
         * descrição da comunicação
         */
        element = context.address.getModelItem("WEB_PAGE").getDataElement();
        item = new DocumentModelItem("COMMUNICATION");
        item.setTableFieldName("COMMU");
        item.setDataElement(element);
        context.communication.add(item);
    }
}
