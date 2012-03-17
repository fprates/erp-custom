package org.erp.custom.sd.documents;

import org.iocaste.documents.common.DataElement;
import org.iocaste.documents.common.DataType;
import org.iocaste.documents.common.DocumentModel;
import org.iocaste.documents.common.DocumentModelItem;
import org.iocaste.documents.common.Documents;
import org.iocaste.packagetool.common.InstallData;
import org.iocaste.protocol.Function;

public class Install {
    private static final boolean IS_KEY = true;
    private static final String HEADER = "CUSTOM_SD_DOCUMENT";
    private static final String ITEM = "CUSTOM_SD_DOCUMENT_ITEM";
    
    public static final InstallData self(Function function) throws Exception {
        DocumentModelItem materialid, partnercode, docid;
        DataElement edocid, element;
        Documents documents = new Documents(function);
        InstallData data = new InstallData();
        
        /*
         * cabeçalho documento vendas
         */
        DocumentModel docitem, dochead = data.getModel(HEADER, "CSDDOCHDR", "");
        
        // id
        edocid = data.getDataElement(HEADER+".ID", 0, 10, DataType.NUMC, false);
        docid = data.addModelItem(dochead, "ID", "IDENT", edocid, "id", IS_KEY);
        
        // sender, receiver
        partnercode = documents.getModel("CUSTOM_PARTNER").
                getModelItem("CODIGO");
        element = partnercode.getDataElement();
        data.addModelItem(dochead, "SENDER", "SENDR", element, "sender",
                !IS_KEY, partnercode);
        data.addModelItem(dochead, "RECEIVER", "RECVR", element, "receiver",
                !IS_KEY, partnercode);
        
        /*
         * itens do documento de vendas
         */
        docitem = data.getModel(ITEM, "CSDDOCITM", "");
        
        // id
        element = data.getDataElement(ITEM+".ITEM_NUMBER", 0, 15, DataType.NUMC,
                false);
        data.addModelItem(docitem, ITEM+".ITEM_NUMBER", "ITMNR", element,
                "item", IS_KEY);
        
        // id documento
        data.addModelItem(docitem, ITEM+".DOCUMENT_ID", "DOCID", edocid,
                "document", !IS_KEY, docid);
        
        // material
        materialid = documents.getModel("MATERIAL").getModelItem("ID");
        data.addModelItem(docitem, ITEM+".MATERIAL", "MATCD",
                materialid.getDataElement(), "material", !IS_KEY, materialid);
        
        /*
         * objetos de numeração, links
         */
        data.addNumberFactory("SD_DOCUMENT");
        
        data.link("VA01", "erp-custom-sd.documents");
        data.link("DOCUMENTS", "erp-custom-sd.documents");
        
        return data;
    }
}
