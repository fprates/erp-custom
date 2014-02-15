package org.erp.custom.sd.partner.install;

import org.iocaste.documents.common.DataElement;
import org.iocaste.documents.common.DataType;
import org.iocaste.documents.common.DocumentModel;
import org.iocaste.documents.common.DocumentModelItem;
import org.iocaste.documents.common.DocumentModelKey;

public class ContactCommunication {
    public static final void install(InstallContext context) {
        DataElement element;
        DocumentModel model;
        DocumentModelItem item, reference;
        
        model = context.data.getModel("CUSTOM_PARTNER_COMM",
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
        model.add(item);
        model.add(new DocumentModelKey(item));
        
        /*
         * parceiro
         */
        reference = context.partner.getModelItem("CODIGO");
        element = reference.getDataElement();
        item = new DocumentModelItem("PARTNER_ID");
        item.setTableFieldName("PRTNR");
        item.setDataElement(element);
        item.setReference(reference);
        model.add(item);
        
        /*
         * contato
         */
        reference = context.contact.getModelItem("CODIGO");
        element = reference.getDataElement();
        item = new DocumentModelItem("CONTACT_ID");
        item.setTableFieldName("CNTCT");
        item.setDataElement(element);
        item.setReference(reference);
        model.add(item);
        
        /*
         * tipo de comunicação
         */
        reference = context.communication.getModelItem("CODIGO");
        element = reference.getDataElement();
        item = new DocumentModelItem("TP_COMMUNIC");
        item.setTableFieldName("TPCOM");
        item.setDataElement(element);
        item.setReference(reference);
        item.setSearchHelp("SH_COMMUNICATION");
        model.add(item);
        
        /*
         * descrição da comunicação
         */
        element = context.address.getModelItem("WEB_PAGE").getDataElement();
        item = new DocumentModelItem("COMMUNICATION");
        item.setTableFieldName("COMMU");
        item.setDataElement(element);
        model.add(item);
    }
}
