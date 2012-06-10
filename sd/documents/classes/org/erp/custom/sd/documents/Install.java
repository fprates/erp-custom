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
import org.iocaste.packagetool.common.SearchHelpData;
import org.iocaste.protocol.Function;
import org.iocaste.protocol.user.Authorization;

public class Install {
    
    public static final InstallData init(Function function) throws Exception {
        Map<String, String> messages;
        Authorization authorization;
        InstallData data = new InstallData();
        Documents documents = new Documents(function);
        CData cdata = new CData();

        data.setDependencies("erp-custom-mm.materials",
                "erp-custom-sd.partner");
                
        installHeader(data, cdata, documents);
        installItens(data, cdata, documents);
        installConditions(data, cdata);
        
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
        messages.put("document-conditions", "Condições de preço");
        messages.put("document.number.required",
                "Número do documento obrigatório.");
        messages.put("document.saved.successfully",
                "Documento salvo com sucesso.");
        messages.put("invalid.sd.document", "Documento inválido.");
        messages.put("invalid.document.header",
                "Cabeçalho do documento inválido.");
        messages.put("display", "Exibir");
        messages.put("create", "Criar");
        messages.put("update", "Atualizar");
        messages.put("ID", "Documento");
        messages.put("ITEM_NUMBER", "Item");
        messages.put("MATERIAL", "Produto");
        messages.put("RECEIVER", "Recebedor");
        messages.put("QUANTITY", "Quantidade");
        messages.put("DATA_CRIACAO", "Criado em");
        messages.put("PRECO_UNITARIO", "Preço unitário");
        messages.put("PRECO_TOTAL", "Preço total");
        messages.put("CONDICAO", "Condição");
        messages.put("VALOR", "Valor");
        messages.put("TIPO", "Tipo documento");
        messages.put("save", "Salvar");
        messages.put("add", "Adicionar");
        messages.put("remove", "Remover");
        messages.put("condadd", "Adicionar");
        messages.put("condremove", "Remover");
        messages.put("condapply", "Aplicar");
        messages.put("condcancel", "Cancelar");
        messages.put("conditions", "Condições");
        messages.put("validate", "Validar");
        messages.put("VA01", "Emissão de documento de venda");
        data.setMessages("pt_BR", messages);
        
        return data;
    }
    
    private static final void installConditions(InstallData data, CData cdata) {
        DocumentModelItem item, condid;
        DocumentModel model;
        DataElement element;
        SearchHelpData sh;
        
        /*
         * Condição
         */
        model = data.getModel("CUSTOM_CONDITION", "CCONDITION", null);
        
        // identificador
        element = new DataElement();
        element.setName("CUSTOM_CONDITION.ID");
        element.setType(DataType.NUMC);
        element.setLength(2);
        
        condid = new DocumentModelItem();
        condid.setName("ID");
        condid.setTableFieldName("IDENT");
        condid.setDataElement(element);
        
        model.add(condid);
        model.add(new DocumentModelKey(condid));
        
        // descrição
        element = new DataElement();
        element.setName("CUSTOM_CONDITION.TEXT");
        element.setType(DataType.CHAR);
        element.setLength(12);
        element.setUpcase(true);
        
        item = new DocumentModelItem();
        item.setName("TEXT");
        item.setTableFieldName("TEXT");
        item.setDataElement(element);
        
        model.add(item);
        
        // valores
        data.addValues(model, 0, "no.condition");
        data.addValues(model, 1, "increase");
        data.addValues(model, 2, "discount");
        
        // ajuda de pesquisa
        sh = new SearchHelpData();
        sh.setName("SH_SD_CONDITION");
        sh.setModel("CUSTOM_CONDITION");
        sh.setExport("ID");
        sh.add("ID");
        sh.add("TEXT");
        data.add(sh);
        
        /*
         * Condições para o documento
         */
        model = data.getModel("CUSTOM_SD_CONDITIONS", "CSDCONDITION", null);
        
        // identificador
        element = new DataElement();
        element.setName("CUSTOM_SD_CONDITIONS.ID");
        element.setLength(13);
        element.setType(DataType.NUMC);
        
        item = new DocumentModelItem();
        item.setName("ID");
        item.setTableFieldName("IDENT");
        item.setDataElement(element);
        
        model.add(item);
        model.add(new DocumentModelKey(item));
        
        // documento
        element = cdata.docid.getDataElement();
        
        item = new DocumentModelItem();
        item.setName("DOCUMENT");
        item.setTableFieldName("DOCID");
        item.setDataElement(element);
        item.setReference(cdata.docid);
        
        model.add(item);
        
        // tipo condição
        element = condid.getDataElement();
        
        item = new DocumentModelItem();
        item.setName("CONDICAO");
        item.setTableFieldName("TPCON");
        item.setDataElement(element);
        item.setReference(condid);
        item.setSearchHelp("SH_SD_CONDITION");
        
        model.add(item);
        
        // valor
        item = new DocumentModelItem();
        item.setName("VALOR");
        item.setTableFieldName("VLCON");
        item.setDataElement(cdata.eprice);
        
        model.add(item);
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
        DocumentModelItem item, partnercode, doctype;
        DataElement element;
        SearchHelpData sh;
        
        /*
         * Tipo de documento
         */
        model = data.getModel("CUSTOM_SD_DOCTYPE", "CSDDOCTYPE", null);
        
        // identificador
        element = new DataElement();
        element.setName("CUSTOM_SD_DOCTYPE.ID");
        element.setLength(4);
        element.setType(DataType.CHAR);
        element.setUpcase(true);
        
        doctype = new DocumentModelItem();
        doctype.setName("ID");
        doctype.setTableFieldName("IDENT");
        doctype.setDataElement(element);
        
        model.add(doctype);
        model.add(new DocumentModelKey(doctype));
        
        // descrição
        element = new DataElement();
        element.setName("CUSTOM_SD_DOCTYPE.TEXT");
        element.setLength(30);
        element.setType(DataType.CHAR);
        element.setUpcase(false);
        
        item = new DocumentModelItem();
        item.setName("TEXT");
        item.setTableFieldName("TEXT");
        item.setDataElement(element);
        
        model.add(item);
        
        data.addValues(model, "SERV", "service.order");
        data.addValues(model, "QUOT", "quotation");
        
        sh = new SearchHelpData();
        sh.setModel("CUSTOM_SD_DOCTYPE");
        sh.setExport("ID");
        sh.setName("SH_SD_DOCTYPE");
        sh.add("ID");
        sh.add("TEXT");
        data.add(sh);
        
        /*
         * Cabeçalho do documento
         */
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
        cdata.docid.setSearchHelp("SH_SD_DOC");
        
        model.add(cdata.docid);
        model.add(new DocumentModelKey(cdata.docid));
        
        // código do recebedor
        partnercode = documents.getModel("CUSTOM_PARTNER").
                getModelItem("CODIGO");
        element = partnercode.getDataElement();
        
        item = new DocumentModelItem();
        item.setName("RECEIVER");
        item.setTableFieldName("RECVR");
        item.setDataElement(element);
        item.setReference(partnercode);
        item.setSearchHelp(partnercode.getSearchHelp());
        
        model.add(item);
        
        // data de criação
        element = new DataElement();
        element.setName("CUSTOM_SD_DOCUMENT.DATA_CRIACAO");
        element.setType(DataType.DATE);
        
        item = new DocumentModelItem();
        item.setName("DATA_CRIACAO");
        item.setTableFieldName("DTREG");
        item.setDataElement(element);
        
        model.add(item);
        
        // tipo de documento
        element = doctype.getDataElement();
        
        item = new DocumentModelItem();
        item.setName("TIPO");
        item.setTableFieldName("TPDOC");
        item.setDataElement(element);
        item.setReference(doctype);
        item.setSearchHelp("SH_SD_DOCTYPE");
        
        model.add(item);
        
        // total do documento
        cdata.eprice = new DataElement();
        cdata.eprice.setName("CUSTOM_SD_DOCUMENT.PRECO");
        cdata.eprice.setDecimals(3);
        cdata.eprice.setLength(15);
        cdata.eprice.setType(DataType.DEC);
        
        item = new DocumentModelItem();
        item.setName("VALOR");
        item.setTableFieldName("VLTOT");
        item.setDataElement(cdata.eprice);
        
        model.add(item);
        
        sh = new SearchHelpData();
        sh.setModel("CUSTOM_SD_DOCUMENT");
        sh.setExport("ID");
        sh.setName("SH_SD_DOC");
        sh.add("ID");
        sh.add("RECEIVER");
        
        data.add(sh);
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
        
        // quantidade
        element = new DataElement();
        element.setName("CUSTOM_SD_DOCUMENT_ITEM.QUANTITY");
        element.setDecimals(3);
        element.setLength(12);
        element.setType(DataType.DEC);
        
        item = new DocumentModelItem();
        item.setName("QUANTITY");
        item.setTableFieldName("QUANT");
        item.setDataElement(element);
        
        model.add(item);
        
        // preço unitário
        item = new DocumentModelItem();
        item.setName("PRECO_UNITARIO");
        item.setTableFieldName("PUNIT");
        item.setDataElement(cdata.eprice);
        
        model.add(item);
        
        // preço total
        item = new DocumentModelItem();
        item.setName("PRECO_TOTAL");
        item.setTableFieldName("PTOTA");
        item.setDataElement(cdata.eprice);
        
        model.add(item);
    }
}

class CData {
    public DocumentModelItem docid;
    public DataElement eprice;
}
