package org.erp.custom.sd.documents;

import java.util.HashMap;
import java.util.Map;

import org.iocaste.documents.common.DataElement;
import org.iocaste.documents.common.DataType;
import org.iocaste.documents.common.DocumentModel;
import org.iocaste.documents.common.DocumentModelItem;
import org.iocaste.documents.common.DocumentModelKey;
import org.iocaste.documents.common.Documents;
import org.iocaste.packagetool.common.InstallData;
import org.iocaste.protocol.Function;
import org.iocaste.protocol.user.Authorization;

public class Install {
    
    public static final InstallData init(Function function) throws Exception {
        Map<String, String> messages;
        Authorization authorization;
        InstallData data = new InstallData();
        Documents documents = new Documents(function);
        CData cdata = new CData();
        
        installHeader(data, cdata, documents);
        installItens(data, cdata, documents);
        
        /*
         * autorizações
         */
        authorization = new Authorization("SALESDOC.EXECUTE");
        authorization.setObject("APPLICATION");
        authorization.setAction("EXECUTE");
        authorization.add("APPNAME", "erp-custom-sd.documents");
        data.add(authorization);
        
        /*
         * objeto de numeração
         */
        data.addNumberFactory("SD_DOCUMENT");
        
        /*
         * links
         */
        data.link("VA01", "erp-custom-sd.documents");
        data.addTaskGroup("ERP", "VA01");
        
        /*
         * mensagens
         */
        messages = new HashMap<String, String>();
        messages.put("document-selection", "Seleção de documento");
        messages.put("document-create", "Criar documento");
        messages.put("document-display", "Exibir documento");
        messages.put("document-update", "Editar documento");
        messages.put("document.number.required",
                "Número do documento obrigatório");
        messages.put("document", "Documento");
        messages.put("display", "Exibir");
        messages.put("create", "Criar");
        messages.put("update", "Atualizar");
        messages.put("ID", "Documento");
        messages.put("ITEM_NUMBER", "Item");
        messages.put("MATERIAL", "Produto");
        messages.put("RECEIVER", "Recebedor");
        messages.put("save", "Salvar");
        messages.put("add", "Adicionar");
        messages.put("remove", "Remover");
        messages.put("VA01", "Emissão de documento de venda");
        data.setMessages("pt_BR", messages);
        
        return data;
    }
    
    /**
     * 
     * @param data
     * @param cdata
     * @param documents
     * @throws Exception
     */
    private static final void installHeader(InstallData data, CData cdata,
            Documents documents) throws Exception {
        DocumentModel model;
        DocumentModelItem item, partnercode;
        DataElement element;
        
        model = data.getModel("CUSTOM_SD_DOCUMENT", "CSDDOCHDR", null);
        
        // identificador
        element = new DataElement();
        element.setName("CUSTOM_SD_DOCUMENT.ID");
        element.setLength(10);
        element.setType(DataType.NUMC);
        data.add(element);
        
        cdata.docid = new DocumentModelItem();
        cdata.docid.setName("ID");
        cdata.docid.setTableFieldName("IDENT");
        cdata.docid.setDataElement(element);
        
        model.add(cdata.docid);
        model.add(new DocumentModelKey(cdata.docid));
        
        // código do emissor
        partnercode = documents.getModel("CUSTOM_PARTNER").
                getModelItem("CODIGO");
        element = partnercode.getDataElement();
        
        item = new DocumentModelItem();
        item.setName("SENDER");
        item.setTableFieldName("SENDR");
        item.setDataElement(element);
        item.setReference(partnercode);
        item.setSearchHelp(partnercode.getSearchHelp());
        
        model.add(item);
        
        // código do recebedor
        item = new DocumentModelItem();
        item.setName("RECEIVER");
        item.setTableFieldName("RECVR");
        item.setDataElement(element);
        item.setReference(partnercode);
        item.setSearchHelp(partnercode.getSearchHelp());
        
        model.add(item);
    }
    
    /**
     * 
     * @param data
     * @param cdata
     * @param documents
     * @throws Exception
     */
    private static final void installItens(InstallData data, CData cdata,
            Documents documents) throws Exception {
        DocumentModel model;
        DocumentModelItem item, materialid;
        DataElement element;
        
        model = data.getModel("CUSTOM_SD_DOCUMENT_ITEM", "CSDDOCITM", null);
        
        // identificador
        element = new DataElement();
        element.setName("CUSTOM_SD_DOCUMENT_ITEM.ITEM_NUMBER");
        element.setLength(15);
        element.setType(DataType.NUMC);
        data.add(element);
        
        item = new DocumentModelItem();
        item.setName("ITEM_NUMBER");
        item.setTableFieldName("ITMNR");
        item.setDataElement(element);
        
        model.add(item);
        model.add(new DocumentModelKey(item));
        
        // documento referência
        element = cdata.docid.getDataElement();
        
        item = new DocumentModelItem();
        item.setName("DOCUMENT_ID");
        item.setTableFieldName("DOCID");
        item.setDataElement(element);
        item.setReference(cdata.docid);
        
        model.add(item);
        
        // material
        materialid = documents.getModel("MATERIAL").getModelItem("ID");
        element = materialid.getDataElement();
        
        item = new DocumentModelItem();
        item.setName("MATERIAL");
        item.setTableFieldName("MATCD");
        item.setDataElement(element);
        item.setReference(materialid);
        item.setSearchHelp("SH_MATERIAL");
        
        model.add(item);
    }
}

class CData {
    public DocumentModelItem docid;
}
