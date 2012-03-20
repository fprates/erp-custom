package org.erp.custom.sd.documents;

import org.iocaste.documents.common.DataElement;
import org.iocaste.documents.common.DataType;
import org.iocaste.documents.common.DocumentModel;
import org.iocaste.documents.common.DocumentModelItem;
import org.iocaste.documents.common.DocumentModelKey;
import org.iocaste.documents.common.Documents;
import org.iocaste.packagetool.common.InstallData;
import org.iocaste.protocol.Function;

public class Install {
    private static final String HEADER = "CUSTOM_SD_DOCUMENT";
    private static final String ITEM = "CUSTOM_SD_DOCUMENT_ITEM";
    
    public static final InstallData self(Function function) throws Exception {
        DocumentModelItem materialid, partnercode, docid, item;
        DataElement edocid, element;
        Documents documents = new Documents(function);
        InstallData data = new InstallData();
        DocumentModel docitem, dochead = data.getModel(HEADER, "CSDDOCHDR", "");
        
        /*
         * Header
         */
        edocid = new DataElement();
        edocid.setName(HEADER+".ID");
        edocid.setLength(10);
        edocid.setType(DataType.NUMC);
        
        docid = new DocumentModelItem();
        docid.setDocumentModel(dochead);
        docid.setName("ID");
        docid.setTableFieldName("IDENT");
        docid.setDataElement(edocid);
        docid.setIndex(0);
        
        dochead.add(docid);
        dochead.add(new DocumentModelKey(docid));
        
        partnercode = documents.getModel("CUSTOM_PARTNER").
                getModelItem("CODIGO");
        element = partnercode.getDataElement();
        item = new DocumentModelItem();
        item.setDocumentModel(dochead);
        item.setName("SENDER");
        item.setTableFieldName("SENDR");
        item.setDataElement(element);
        item.setReference(partnercode);
        item.setIndex(1);
        
        dochead.add(item);
        
        item = new DocumentModelItem();
        item.setDocumentModel(dochead);
        item.setName("RECEIVER");
        item.setTableFieldName("RECVR");
        item.setDataElement(element);
        item.setReference(partnercode);
        item.setIndex(2);
        
        dochead.add(item);
        
        /*
         * Item
         */
        docitem = data.getModel(ITEM, "CSDDOCITM", "");
        
        element = new DataElement();
        element.setName(ITEM+".ITEM_NUMBER");
        element.setLength(15);
        element.setType(DataType.NUMC);
        
        item = new DocumentModelItem();
        item.setDocumentModel(docitem);
        item.setName("ITEM_NUMBER");
        item.setTableFieldName("ITMNR");
        item.setDataElement(element);
        item.setIndex(0);
        
        docitem.add(item);
        docitem.add(new DocumentModelKey(item));
        
        item = new DocumentModelItem();
        item.setDocumentModel(docitem);
        item.setName("DOCUMENT_ID");
        item.setTableFieldName("DOCID");
        item.setDataElement(edocid);
        item.setReference(docid);
        item.setIndex(1);
        
        docitem.add(item);
        
        materialid = documents.getModel("MATERIAL").getModelItem("ID");
        item = new DocumentModelItem();
        item.setDocumentModel(docitem);
        item.setName("MATERIAL");
        item.setTableFieldName("MATCD");
        item.setDataElement(materialid.getDataElement());
        item.setReference(materialid);
        item.setIndex(2);
        
        docitem.add(item);
        
        data.addNumberFactory("SD_DOCUMENT");
        data.link("VA01", "erp-custom-sd.documents");
        data.link("DOCUMENTS", "erp-custom-sd.documents");
        
        return data;
    }
}
