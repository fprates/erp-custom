package org.erp.custom.sd.documents;

import java.util.HashMap;
import java.util.Map;

import org.iocaste.documents.common.DataElement;
import org.iocaste.documents.common.DataType;
import org.iocaste.documents.common.DocumentModel;
import org.iocaste.documents.common.DocumentModelItem;
import org.iocaste.documents.common.DocumentModelKey;
import org.iocaste.documents.common.DummyElement;
import org.iocaste.documents.common.DummyModelItem;
import org.iocaste.packagetool.common.InstallData;
import org.iocaste.packagetool.common.SearchHelpData;
import org.iocaste.packagetool.common.TaskGroup;
import org.iocaste.protocol.Function;
import org.iocaste.protocol.user.Authorization;
import org.iocaste.protocol.user.UserProfile;

public class Install {
    
    public static final InstallData init(Function function) {
        UserProfile profile;
        TaskGroup taskgroup;
        Map<String, String> messages;
        Authorization authorization;
        InstallData data = new InstallData();
        CData cdata = new CData();

        data.setDependencies("erp-custom-mm.materials",
                "erp-custom-sd.partner");
                
        installHeader(data, cdata);
        installItens(data, cdata);
        installConditions(data, cdata);
        
        /*
         * autorizações
         */
        authorization = new Authorization("SALESDOC.EXECUTE");
        authorization.setObject("APPLICATION");
        authorization.setAction("EXECUTE");
        authorization.add("APPNAME", "erp-custom-sd.documents");
        data.add(authorization);
        
        profile = new UserProfile("SALES");
        profile.add(authorization);
        data.add(profile);
        
        /*
         * objeto de numeração
         */
        data.addNumberFactory("SD_DOCUMENT");
        
        /*
         * links
         */
        data.link("VA01", "erp-custom-sd.documents");
        taskgroup = new TaskGroup("ERP");
        taskgroup.add("VA01");
        data.add(taskgroup);
        
        /*
         * mensagens
         */
        messages = new HashMap<String, String>();
        messages.put("add", "Adicionar");
        messages.put("condadd", "Adicionar");
        messages.put("condapply", "Aplicar");
        messages.put("condcancel", "Cancelar");
        messages.put("CONDICAO", "Condição");
        messages.put("conditions", "Condições");
        messages.put("condremove", "Remover");
        messages.put("create", "Criar");
        messages.put("DATA_CRIACAO", "Criado em");
        messages.put("display", "Exibir");
        messages.put("document-conditions", "Condições de preço");
        messages.put("document-create", "Criar documento");
        messages.put("document-display", "Exibir documento");
        messages.put("document-selection", "Seleção de documento");
        messages.put("document-update", "Editar documento");
        messages.put("document.number.required",
                "Número do documento obrigatório.");
        messages.put("document.saved.successfully",
                "Documento salvo com sucesso.");
        messages.put("ID", "Documento");
        messages.put("invalid.document.header",
                "Cabeçalho do documento inválido.");
        messages.put("invalid.sd.document", "Documento inválido.");
        messages.put("ITEM_NUMBER", "Item");
        messages.put("MATERIAL", "Produto");
        messages.put("PRECO_UNITARIO", "Preço unitário");
        messages.put("PRECO_TOTAL", "Preço total");
        messages.put("QUANTITY", "Quantidade");
        messages.put("RECEIVER", "Recebedor");
        messages.put("remove", "Remover");
        messages.put("save", "Salvar");
        messages.put("TIPO", "Tipo documento");
        messages.put("update", "Atualizar");
        messages.put("VA01", "Emissão de documento de venda");
        messages.put("validate", "Validar");
        messages.put("validatecond", "Validar");
        messages.put("VALOR", "Valor");
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
        element = new DataElement("CUSTOM_CONDITION.ID");
        element.setType(DataType.NUMC);
        element.setLength(2);
        
        condid = new DocumentModelItem("ID");
        condid.setTableFieldName("IDENT");
        condid.setDataElement(element);
        
        model.add(condid);
        model.add(new DocumentModelKey(condid));
        
        // descrição
        element = new DataElement("CUSTOM_CONDITION.TEXT");
        element.setType(DataType.CHAR);
        element.setLength(12);
        element.setUpcase(true);
        
        item = new DocumentModelItem("TEXT");
        item.setTableFieldName("TEXT");
        item.setDataElement(element);
        
        model.add(item);
        
        // valores
        data.addValues(model, 0, "no.condition");
        data.addValues(model, 1, "increase");
        data.addValues(model, 2, "discount");
        
        // ajuda de pesquisa
        sh = new SearchHelpData("SH_SD_CONDITION");
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
        element = new DataElement("CUSTOM_SD_CONDITIONS.ID");
        element.setLength(13);
        element.setType(DataType.NUMC);
        
        item = new DocumentModelItem("ID");
        item.setTableFieldName("IDENT");
        item.setDataElement(element);
        
        model.add(item);
        model.add(new DocumentModelKey(item));
        
        // documento
        element = cdata.docid.getDataElement();
        
        item = new DocumentModelItem("DOCUMENT");
        item.setTableFieldName("DOCID");
        item.setDataElement(element);
        item.setReference(cdata.docid);
        
        model.add(item);
        
        // tipo condição
        element = condid.getDataElement();
        
        item = new DocumentModelItem("CONDICAO");
        item.setTableFieldName("TPCON");
        item.setDataElement(element);
        item.setReference(condid);
        item.setSearchHelp("SH_SD_CONDITION");
        
        model.add(item);
        
        // valor
        item = new DocumentModelItem("VALOR");
        item.setTableFieldName("VLCON");
        item.setDataElement(cdata.eprice);
        
        model.add(item);
    }
    
    /**
     * 
     * @param data
     * @param cdata
     */
    private static final void installHeader(InstallData data, CData cdata) {
        DocumentModel model;
        DocumentModelItem item, partnercode, doctype;
        DataElement element;
        SearchHelpData sh;
        
        /*
         * Tipo de documento
         */
        model = data.getModel("CUSTOM_SD_DOCTYPE", "CSDDOCTYPE", null);
        
        // identificador
        element = new DataElement("CUSTOM_SD_DOCTYPE.ID");
        element.setLength(4);
        element.setType(DataType.CHAR);
        element.setUpcase(true);
        
        doctype = new DocumentModelItem("ID");
        doctype.setTableFieldName("IDENT");
        doctype.setDataElement(element);
        
        model.add(doctype);
        model.add(new DocumentModelKey(doctype));
        
        // descrição
        element = new DataElement("CUSTOM_SD_DOCTYPE.TEXT");
        element.setLength(30);
        element.setType(DataType.CHAR);
        element.setUpcase(false);
        
        item = new DocumentModelItem("TEXT");
        item.setTableFieldName("TEXT");
        item.setDataElement(element);
        
        model.add(item);
        
        data.addValues(model, "SERV", "service.order");
        data.addValues(model, "QUOT", "quotation");
        
        sh = new SearchHelpData("SH_SD_DOCTYPE");
        sh.setModel("CUSTOM_SD_DOCTYPE");
        sh.setExport("ID");
        sh.add("ID");
        sh.add("TEXT");
        data.add(sh);
        
        /*
         * Cabeçalho do documento
         */
        model = data.getModel("CUSTOM_SD_DOCUMENT", "CSDDOCHDR", null);
        
        // identificador
        element = new DataElement("CUSTOM_SD_DOCUMENT.ID");
        element.setLength(10);
        element.setType(DataType.NUMC);
        data.add(element);
        
        cdata.docid = new DocumentModelItem("ID");
        cdata.docid.setTableFieldName("IDENT");
        cdata.docid.setDataElement(element);
        cdata.docid.setSearchHelp("SH_SD_DOC");
        
        model.add(cdata.docid);
        model.add(new DocumentModelKey(cdata.docid));
        
        // código do recebedor
        partnercode = new DummyModelItem("CUSTOM_PARTNER","CODIGO");
        element = new DummyElement("CUSTOM_PARTNER.CODIGO");
        
        item = new DocumentModelItem("RECEIVER");
        item.setTableFieldName("RECVR");
        item.setDataElement(element);
        item.setReference(partnercode);
        item.setSearchHelp(partnercode.getSearchHelp());
        
        model.add(item);
        
        // data de criação
        element = new DataElement("CUSTOM_SD_DOCUMENT.DATA_CRIACAO");
        element.setType(DataType.DATE);
        
        item = new DocumentModelItem("DATA_CRIACAO");
        item.setTableFieldName("DTREG");
        item.setDataElement(element);
        
        model.add(item);
        
        // tipo de documento
        element = doctype.getDataElement();
        
        item = new DocumentModelItem("TIPO");
        item.setTableFieldName("TPDOC");
        item.setDataElement(element);
        item.setReference(doctype);
        item.setSearchHelp("SH_SD_DOCTYPE");
        
        model.add(item);
        
        // total do documento
        cdata.eprice = new DataElement("CUSTOM_SD_DOCUMENT.PRECO");
        cdata.eprice.setDecimals(3);
        cdata.eprice.setLength(15);
        cdata.eprice.setType(DataType.DEC);
        
        item = new DocumentModelItem("VALOR");
        item.setTableFieldName("VLTOT");
        item.setDataElement(cdata.eprice);
        
        model.add(item);
        
        sh = new SearchHelpData("SH_SD_DOC");
        sh.setModel("CUSTOM_SD_DOCUMENT");
        sh.setExport("ID");
        sh.add("ID");
        sh.add("RECEIVER");
        
        data.add(sh);
    }
    
    /**
     * 
     * @param data
     * @param cdata
     */
    private static final void installItens(InstallData data, CData cdata) {
        DocumentModel model;
        DocumentModelItem item, materialid;
        DataElement element;
        
        model = data.getModel("CUSTOM_SD_DOCUMENT_ITEM", "CSDDOCITM", null);
        
        // identificador
        element = new DataElement("CUSTOM_SD_DOCUMENT_ITEM.ITEM_NUMBER");
        element.setLength(15);
        element.setType(DataType.NUMC);
        data.add(element);
        
        item = new DocumentModelItem("ITEM_NUMBER");
        item.setTableFieldName("ITMNR");
        item.setDataElement(element);
        model.add(item);
        model.add(new DocumentModelKey(item));
        
        // documento referência
        element = cdata.docid.getDataElement();
        
        item = new DocumentModelItem("DOCUMENT_ID");
        item.setTableFieldName("DOCID");
        item.setDataElement(element);
        item.setReference(cdata.docid);
        model.add(item);
        
        // material
        materialid = new DummyModelItem("MATERIAL", "ID");
        element = new DummyElement("MATERIAL.ID");
        
        item = new DocumentModelItem("MATERIAL");
        item.setTableFieldName("MATCD");
        item.setDataElement(element);
        item.setReference(materialid);
        item.setSearchHelp("SH_MATERIAL");
        model.add(item);
        
        // quantidade
        element = new DataElement("CUSTOM_SD_DOCUMENT_ITEM.QUANTITY");
        element.setDecimals(3);
        element.setLength(12);
        element.setType(DataType.DEC);
        
        item = new DocumentModelItem("QUANTITY");
        item.setTableFieldName("QUANT");
        item.setDataElement(element);
        model.add(item);
        
        // preço unitário
        item = new DocumentModelItem("PRECO_UNITARIO");
        item.setTableFieldName("PUNIT");
        item.setDataElement(cdata.eprice);
        model.add(item);
        
        // preço total
        item = new DocumentModelItem("PRECO_TOTAL");
        item.setTableFieldName("PTOTA");
        item.setDataElement(cdata.eprice);
        model.add(item);
    }
}

class CData {
    public DocumentModelItem docid;
    public DataElement eprice;
}
