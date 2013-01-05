package org.erp.custom.mm.materials;

import java.util.HashMap;
import java.util.Map;

import org.iocaste.documents.common.DataElement;
import org.iocaste.documents.common.DataType;
import org.iocaste.documents.common.DocumentModel;
import org.iocaste.documents.common.DocumentModelItem;
import org.iocaste.documents.common.DocumentModelKey;
import org.iocaste.packagetool.common.GlobalConfigData;
import org.iocaste.packagetool.common.InstallData;
import org.iocaste.packagetool.common.SearchHelpData;
import org.iocaste.packagetool.common.TaskGroup;
import org.iocaste.protocol.user.Authorization;
import org.iocaste.protocol.user.UserProfile;

public class Install {
    
    /**
     * 
     * @param function
     * @return
     */
    public static final InstallData init() {
        UserProfile profile;
        GlobalConfigData config;
    	TaskGroup taskgroup;
        Map<String, String> messages;
        Authorization authorization;
        InstallData data = new InstallData();
        CData cdata = new CData();
        
        installMaterialGroup(data, cdata);
        installMaterialType(data, cdata);
        installBaseData(data, cdata);
        installPrices(data, cdata);
        installPromotions(data, cdata);
        installSubMaterial(data, cdata);
        
        /*
         * dados de configuração
         */
        config = new GlobalConfigData();
        config.define("MATERIAL_AUTOCODE", Boolean.class, true);
        data.add(config);
        
        data.addNumberFactory("MATERIAL_ID");
        
        /*
         * autorizações
         */
        authorization = new Authorization("MATERIAL.EXECUTE");
        authorization.setObject("APPLICATION");
        authorization.setAction("EXECUTE");
        authorization.add("APPNAME", "erp-custom-mm.materials");
        data.add(authorization);
        
        profile = new UserProfile("SALES");
        profile.add(authorization);
        data.add(profile);
        
        /*
         * links
         */
        data.link("MM01", "erp-custom-mm.materials");
        taskgroup = new TaskGroup("ERP");
        taskgroup.add("MM01");
        data.add(taskgroup);
        
        /*
         * mensagens
         */
        messages = new HashMap<>();
        messages.put("ACTIVE", "Ativo");
        messages.put("acceptprices", "Aceitar");
        messages.put("acceptpromotions", "Aceitar");
        messages.put("acceptsubmats", "Aceitar");
        messages.put("addprices", "Adicionar");
        messages.put("addpromotions", "Adicionar");
        messages.put("addsubmats", "Adicionar");
        messages.put("basepane", "Dados básicos");
        messages.put("create", "Criar");
        messages.put("DT_FINAL", "Data final");
        messages.put("DT_INICIAL", "Data inicial");
        messages.put("dtini.gt.dtfin",
                "Data final não pode ser maiorn que a data inicial");
        messages.put("ID", "Código");
        messages.put("MATERIAL_AUTOCODE", "Auto-numeração");
        messages.put("MAT_GROUP", "Grupo de materiais");
        messages.put("MAT_TYPE", "Tipo de material");
        messages.put("material", "Material");
        messages.put("material.already.exists", "Material já existe.");
        messages.put("material.not.found", "Material não encontrado.");
        messages.put("material.saved.successfully",
                "Material salvo com sucesso.");
        messages.put("material-editor-create", "Criar material");
        messages.put("material-editor-show", "Exibir material");
        messages.put("material-editor-update", "Atualizar material");
        messages.put("material-selection", "Selecionar material");
        messages.put("MM01", "Mestre de materiais");
        messages.put("NAME", "Descrição");
        messages.put("pricespane", "Preços");
        messages.put("promotionspane", "Promoções");
        messages.put("removesubmats", "Remover");
        messages.put("removeprices", "Remover");
        messages.put("removepromotions", "Remover");
        messages.put("save", "Salvar");
        messages.put("SUB_MATERIAL", "Material");
        messages.put("submatspane", "Sub-materiais");
        messages.put("show", "Exibir");
        messages.put("update", "Atualizar");
        messages.put("validate", "Validar");
        messages.put("VL_CUSTO", "Preço custo");
        messages.put("VL_VENDA", "Preço venda");
        messages.put("vlcusto.gt.vlvenda",
                "Valor de custo não pode ser maior que valor de venda.");
        data.setMessages("pt_BR", messages);
        
        return data;
    }
    
    private static final void installBaseData(InstallData data, CData cdata) {
    	DataElement element;
    	DocumentModelItem item;
        SearchHelpData sh;
        DocumentModel model;

        /*
         * MATERIAL
         */
        model = data.getModel("MATERIAL", "CMATERIAL", null);
        
        // identificador
        cdata.ematid = new DataElement("MATERIAL.ID");
        cdata.ematid.setLength(20);
        cdata.ematid.setType(DataType.CHAR);
        cdata.ematid.setUpcase(true);
        data.add(cdata.ematid);
        
        cdata.imatid = new DocumentModelItem("ID");
        cdata.imatid.setTableFieldName("IDENT");
        cdata.imatid.setDataElement(cdata.ematid);
        cdata.imatid.setSearchHelp("SH_MATERIAL");
        model.add(cdata.imatid);
        model.add(new DocumentModelKey(cdata.imatid));
        
        // descrição
        element = new DataElement("MATERIAL.NAME");
        element.setLength(60);
        element.setType(DataType.CHAR);
        element.setUpcase(true);
        data.add(element);
        
        item = new DocumentModelItem("NAME");
        item.setTableFieldName("NAME1");
        item.setDataElement(element);
        model.add(item);
        
        // ativo?
        element = new DataElement("MATERIAL.ACTIVE");
        element.setType(DataType.BOOLEAN);
        data.add(element);
        
        item = new DocumentModelItem("ACTIVE");
        item.setTableFieldName("ACTIV");
        item.setDataElement(element);
        model.add(item);
        
        // tipo de material
        item = new DocumentModelItem("MAT_TYPE");
        item.setTableFieldName("TPMAT");
        item.setDataElement(cdata.mattypeid.getDataElement());
        item.setReference(cdata.mattypeid);
        item.setSearchHelp("SH_MAT_TYPE");
        model.add(item);
        
        // grupo de materiais
        item = new DocumentModelItem("MAT_GROUP");
        item.setTableFieldName("GRMAT");
        item.setDataElement(cdata.matgrpid.getDataElement());
        item.setReference(cdata.matgrpid);
        item.setSearchHelp("SH_MAT_GROUP");
        model.add(item);
        
        /*
         * Ajuda de pesquisa para material
         */
        sh = new SearchHelpData("SH_MATERIAL");
        sh.setModel("MATERIAL");
        sh.setExport("ID");
        sh.add("ID");
        sh.add("NAME");
        data.add(sh);
    }
    
    private static final void installMaterialGroup(InstallData data,
            CData cdata) {
        DocumentModelItem item;
        DocumentModel model;
        DataElement element;
        SearchHelpData shd;
        
        /*
         * Grupo de materiais
         */
        model = data.getModel("MATERIAL_GROUP", "MATGROUP", null);
        
        // identificador
        element = new DataElement("MATERIAL_GROUP.ID");
        element.setType(DataType.CHAR);
        element.setLength(4);
        element.setUpcase(true);
        
        cdata.matgrpid = new DocumentModelItem("ID");
        cdata.matgrpid.setTableFieldName("IDENT");
        cdata.matgrpid.setDataElement(element);
        model.add(cdata.matgrpid);
        model.add(new DocumentModelKey(cdata.matgrpid));
        
        // descrição
        element = new DataElement("MATERIAL_GROUP.TEXT");
        element.setType(DataType.CHAR);
        element.setLength(40);
        element.setUpcase(true);
        
        item = new DocumentModelItem("TEXT");
        item.setTableFieldName("TEXT");
        item.setDataElement(element);
        model.add(item);
        
        data.addValues(model, "ALMT", "ALIMENTAÇÃO");
        data.addValues(model, "FRMC", "FARMACEUTICO");
        data.addValues(model, "VEST", "VESTUARIO");
        
        shd = new SearchHelpData("SH_MAT_GROUP");
        shd.setModel("MATERIAL_GROUP");
        shd.setExport("ID");
        shd.add("ID");
        shd.add("TEXT");
        data.add(shd);
    }
    
    private static final void installMaterialType(InstallData data,
            CData cdata) {
        DocumentModelItem item;
        DocumentModel model;
        DataElement element;
        SearchHelpData shd;
        
        /*
         * Tipo de material
         */
        model = data.getModel("MATERIAL_TYPE", "MATTYPE", null);
        
        // identificador
        element = new DataElement("MATERIAL_TYPE.ID");
        element.setType(DataType.CHAR);
        element.setLength(4);
        element.setUpcase(true);
        
        cdata.mattypeid = new DocumentModelItem("ID");
        cdata.mattypeid.setTableFieldName("IDENT");
        cdata.mattypeid.setDataElement(element);
        model.add(cdata.mattypeid);
        model.add(new DocumentModelKey(cdata.mattypeid));
        
        // descrição
        element = new DataElement("MATERIAL_TYPE.TEXT");
        element.setType(DataType.CHAR);
        element.setLength(40);
        element.setUpcase(true);
        
        item = new DocumentModelItem("TEXT");
        item.setTableFieldName("TEXT");
        item.setDataElement(element);
        model.add(item);
        
        data.addValues(model, "PROD", "PRODUTO");
        data.addValues(model, "SERV", "SERVICO");
        
        shd = new SearchHelpData("SH_MAT_TYPE");
        shd.setModel("MATERIAL_TYPE");
        shd.setExport("ID");
        shd.add("ID");
        shd.add("TEXT");
        data.add(shd);
    }
    
    private static final void installPrices(InstallData data, CData cdata) {
    	DataElement element;
    	DocumentModelItem item;
        DocumentModel model;
        
        model = data.getModel("PRECO_MATERIAL", "CMATPRICE", null);
        
        // identificador
        element = new DataElement("PRECO_MATERIAL.ID");
        element.setLength(23);
        element.setType(DataType.CHAR);
        element.setUpcase(true);
        data.add(element);
        
        item = new DocumentModelItem("ID");
        item.setTableFieldName("IDENT");
        item.setDataElement(element);
        model.add(item);
        model.add(new DocumentModelKey(item));
        
        // material
        item = new DocumentModelItem("MATERIAL");
        item.setTableFieldName("MATCD");
        item.setDataElement(cdata.ematid);
        item.setReference(cdata.imatid);
        model.add(item);
        
        cdata.evalue = new DataElement("PRECO_MATERIAL.VALOR");
        cdata.evalue.setDecimals(3);
        cdata.evalue.setLength(12);
        cdata.evalue.setType(DataType.DEC);
        data.add(element);
        
        // valor venda
        item = new DocumentModelItem("VL_VENDA");
        item.setTableFieldName("VLVND");
        item.setDataElement(cdata.evalue);
        model.add(item);
        
        item = new DocumentModelItem("VL_CUSTO");
        item.setTableFieldName("VLCST");
        item.setDataElement(cdata.evalue);
        model.add(item);
        
        cdata.edate = new DataElement("PRECO_MATERIAL.DATA");
        cdata.edate.setType(DataType.DATE);
        data.add(element);
        
        // data inicial
        item = new DocumentModelItem("DT_INICIAL");
        item.setTableFieldName("DTINI");
        item.setDataElement(cdata.edate);
        model.add(item);
        
        // data final
        item = new DocumentModelItem("DT_FINAL");
        item.setTableFieldName("DTTRM");
        item.setDataElement(cdata.edate);
        model.add(item);
    }
    
    private static final void installPromotions(InstallData data, CData cdata) {
    	DataElement element;
    	DocumentModelItem item;
        DocumentModel model;
        
        model = data.getModel("PROMOCAO_MATERIAL", "CMATPROMO", null);
        
        element = new DataElement("PROMOCAO_MATERIAL.ID");
        element.setType(DataType.CHAR);
        element.setLength(23);
        element.setUpcase(true);
        data.add(element);
        
        item = new DocumentModelItem("ID");
        item.setTableFieldName("IDENT");
        item.setDataElement(element);
        
        model.add(item);
        model.add(new DocumentModelKey(item));
        
        item = new DocumentModelItem("MATERIAL");
        item.setDataElement(cdata.ematid);
        item.setTableFieldName("MATCD");
        item.setReference(cdata.imatid);

        model.add(item);
        
        item = new DocumentModelItem("VL_VENDA");
        item.setTableFieldName("VLVND");
        item.setDataElement(cdata.evalue);

        model.add(item);
        
        item = new DocumentModelItem("VL_CUSTO");
        item.setTableFieldName("VLCST");
        item.setDataElement(cdata.evalue);

        model.add(item);
        
        item = new DocumentModelItem("DT_INICIAL");
        item.setTableFieldName("DTINI");
        item.setDataElement(cdata.edate);

        model.add(item);
        
        item = new DocumentModelItem("DT_FINAL");
        item.setTableFieldName("DTTRM");
        item.setDataElement(cdata.edate);
        
        model.add(item);
    }
    
    private static final void installSubMaterial(InstallData data, CData cdata)
    {
    	DocumentModelItem item;
    	DataElement element;
    	DocumentModel model = data.getModel("SUB_MATERIAL", "CSUBMAT", null);
    	
    	// identificador
    	element = new DataElement("SUB_MATERIAL.ID");
    	element.setLength(23);
    	element.setType(DataType.CHAR);
    	element.setUpcase(true);
    	
    	item = new DocumentModelItem("ID");
    	item.setTableFieldName("IDENT");
    	item.setDataElement(element);
    	
    	model.add(item);
    	model.add(new DocumentModelKey(item));
    	
    	// material referência
    	item = new DocumentModelItem("MATERIAL");
    	item.setTableFieldName("MATID");
    	item.setDataElement(cdata.ematid);
    	item.setReference(cdata.imatid);
    	
    	model.add(item);
    	
    	// sub-material
    	item = new DocumentModelItem("SUB_MATERIAL");
    	item.setTableFieldName("SUBMT");
    	item.setDataElement(cdata.ematid);
    	item.setReference(cdata.imatid);
    	item.setSearchHelp("SH_MATERIAL");
    	
    	model.add(item);
    	
    }
}

class CData {
    public DataElement evalue, edate, ematid;
    public DocumentModelItem imatid, item, mattypeid, matgrpid;
}
