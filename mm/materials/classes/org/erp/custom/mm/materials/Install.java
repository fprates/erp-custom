package org.erp.custom.mm.materials;

import java.util.HashMap;
import java.util.Map;

import org.iocaste.documents.common.DataElement;
import org.iocaste.documents.common.DataType;
import org.iocaste.documents.common.DocumentModel;
import org.iocaste.documents.common.DocumentModelItem;
import org.iocaste.documents.common.DocumentModelKey;
import org.iocaste.packagetool.common.InstallData;
import org.iocaste.packagetool.common.SearchHelpData;
import org.iocaste.packagetool.common.TaskGroup;
import org.iocaste.protocol.user.Authorization;

public class Install {
    
    /**
     * 
     * @return
     */
    public static final InstallData init() {
    	TaskGroup taskgroup;
        Map<String, String> messages;
        Authorization authorization;
        InstallData data = new InstallData();
        CData cdata = new CData();
        
        installMaterialType(data, cdata);
        installBaseData(data, cdata);
        installPrices(data, cdata);
        installPromotions(data, cdata);
        installSubMaterial(data, cdata);
        
        /*
         * autorizações
         */
        authorization = new Authorization("MATERIAL.EXECUTE");
        authorization.setObject("APPLICATION");
        authorization.setAction("EXECUTE");
        authorization.add("APPNAME", "erp-custom-mm.materials");
        data.add(authorization);
        
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
        messages = new HashMap<String, String>();
        messages.put("ACTIVE", "Ativo");
        messages.put("addmaterial", "Adicionar");
        messages.put("addprice", "Adicionar");
        messages.put("addpromo", "Adicionar");
        messages.put("basepane", "Dados básicos");
        messages.put("create", "Criar");
        messages.put("DT_FINAL", "Data final");
        messages.put("DT_INICIAL", "Data inicial");
        messages.put("dtini.gt.dtfin",
                "Data final não pode ser maiorn que a data inicial");
        messages.put("ID", "Código");
        messages.put("MAT_TYPE", "Tipo de material");
        messages.put("material", "Material");
        messages.put("material.already.exists", "Material já existe.");
        messages.put("material.not.found", "Material não encontrado.");
        messages.put("material.saved.successfully",
                "Material salvo com sucesso.");
        messages.put("material-editor-create", "Criar material");
        messages.put("material-editor-update", "Atualizar material");
        messages.put("material-editor-show", "Exibir material");
        messages.put("material-selection", "Selecionar material");
        messages.put("MM01", "Mestre de materiais");
        messages.put("NAME", "Descrição");
        messages.put("pricespane", "Preços");
        messages.put("promotions", "Promoções");
        messages.put("removematerial", "Remover");
        messages.put("removeprice", "Remover");
        messages.put("removepromo", "Remover");
        messages.put("save", "Salvar");
        messages.put("SUB_MATERIAL", "Material");
        messages.put("submaterials", "Sub-materiais");
        messages.put("show", "Exibir");
        messages.put("update", "Atualizar");
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
        cdata.ematid = new DataElement();
        cdata.ematid.setName("MATERIAL.ID");
        cdata.ematid.setLength(20);
        cdata.ematid.setType(DataType.CHAR);
        cdata.ematid.setUpcase(true);
        data.add(cdata.ematid);
        
        cdata.imatid = new DocumentModelItem();
        cdata.imatid.setName("ID");
        cdata.imatid.setTableFieldName("IDENT");
        cdata.imatid.setDataElement(cdata.ematid);
        cdata.imatid.setSearchHelp("SH_MATERIAL");
        model.add(cdata.imatid);
        model.add(new DocumentModelKey(cdata.imatid));
        
        // descrição
        element = new DataElement();
        element.setName("MATERIAL.NAME");
        element.setLength(60);
        element.setType(DataType.CHAR);
        element.setUpcase(true);
        data.add(element);
        
        item = new DocumentModelItem();
        item.setName("NAME");
        item.setTableFieldName("NAME1");
        item.setDataElement(element);
        model.add(item);
        
        // ativo?
        element = new DataElement();
        element.setName("MATERIAL.ACTIVE");
        element.setType(DataType.BOOLEAN);
        data.add(element);
        
        item = new DocumentModelItem();
        item.setName("ACTIVE");
        item.setTableFieldName("ACTIV");
        item.setDataElement(element);
        model.add(item);
        
        // tipo de material
        item = new DocumentModelItem();
        item.setName("MAT_TYPE");
        item.setTableFieldName("TPMAT");
        item.setDataElement(cdata.mattypeid.getDataElement());
        item.setReference(cdata.mattypeid);
        item.setSearchHelp("SH_MAT_TYPE");
        model.add(item);
        
        /*
         * Ajuda de pesquisa para material
         */
        sh = new SearchHelpData();
        sh.setName("SH_MATERIAL");
        sh.setModel("MATERIAL");
        sh.setExport("ID");
        sh.add("ID");
        sh.add("NAME");
        data.add(sh);
    }
    
    private static final void installMaterialType(InstallData data, CData cdata)
    {
        DocumentModel model;
        DataElement element;
        SearchHelpData shd;
        
        /*
         * Tipo de material
         */
        model = data.getModel("MATERIAL_TYPE", "MATTYPE", null);
        
        // identificador
        element = new DataElement();
        element.setName("MATERIAL_TYPE.ID");
        element.setType(DataType.CHAR);
        element.setLength(4);
        element.setUpcase(true);
        
        cdata.mattypeid = new DocumentModelItem();
        cdata.mattypeid.setName("ID");
        cdata.mattypeid.setTableFieldName("IDENT");
        cdata.mattypeid.setDataElement(element);
        model.add(cdata.mattypeid);
        model.add(new DocumentModelKey(cdata.mattypeid));
        
        data.addValues(model, "PROD");
        data.addValues(model, "SERV");
        
        shd = new SearchHelpData();
        shd.setName("SH_MAT_TYPE");
        shd.setModel("MATERIAL_TYPE");
        shd.setExport("ID");
        shd.add("ID");
        data.add(shd);
    }
    
    private static final void installPrices(InstallData data, CData cdata) {
    	DataElement element;
    	DocumentModelItem item;
        DocumentModel model;
        
        model = data.getModel("PRECO_MATERIAL", "CMATPRICE", null);
        
        element = new DataElement();
        element.setName("PRECO_MATERIAL.ID");
        element.setLength(23);
        element.setType(DataType.CHAR);
        element.setUpcase(true);
        data.add(element);
        
        item = new DocumentModelItem();
        item.setName("ID");
        item.setTableFieldName("IDENT");
        item.setDataElement(element);
        
        model.add(item);
        model.add(new DocumentModelKey(item));
        
        item = new DocumentModelItem();
        item.setName("MATERIAL");
        item.setTableFieldName("MATCD");
        item.setDataElement(cdata.ematid);
        item.setReference(cdata.imatid);
        model.add(item);
        
        cdata.evalue = new DataElement();
        cdata.evalue.setName("PRECO_MATERIAL.VALOR");
        cdata.evalue.setDecimals(3);
        cdata.evalue.setLength(12);
        cdata.evalue.setType(DataType.DEC);
        data.add(element);
        
        item = new DocumentModelItem();
        item.setName("VL_VENDA");
        item.setTableFieldName("VLVND");
        item.setDataElement(cdata.evalue);
        model.add(item);
        
        item = new DocumentModelItem();
        item.setName("VL_CUSTO");
        item.setTableFieldName("VLCST");
        item.setDataElement(cdata.evalue);
        model.add(item);
        
        cdata.edate = new DataElement();
        cdata.edate.setName("PRECO_MATERIAL.DATA");
        cdata.edate.setType(DataType.DATE);
        data.add(element);
        
        item = new DocumentModelItem();
        item.setName("DT_INICIAL");
        item.setTableFieldName("DTINI");
        item.setDataElement(cdata.edate);
        model.add(item);
        
        item = new DocumentModelItem();
        item.setName("DT_FINAL");
        item.setTableFieldName("DTTRM");
        item.setDataElement(cdata.edate);
        model.add(item);
    }
    
    private static final void installPromotions(InstallData data, CData cdata) {
    	DataElement element;
    	DocumentModelItem item;
        DocumentModel model;
        
        model = data.getModel("PROMOCAO_MATERIAL", "CMATPROMO", null);
        
        element = new DataElement();
        element.setName("PROMOCAO_MATERIAL.ID");
        element.setType(DataType.CHAR);
        element.setLength(23);
        element.setUpcase(true);
        data.add(element);
        
        item = new DocumentModelItem();
        item.setName("ID");
        item.setTableFieldName("IDENT");
        item.setDataElement(element);
        
        model.add(item);
        model.add(new DocumentModelKey(item));
        
        item = new DocumentModelItem();
        item.setName("MATERIAL");
        item.setDataElement(cdata.ematid);
        item.setTableFieldName("MATCD");
        item.setReference(cdata.imatid);

        model.add(item);
        
        item = new DocumentModelItem();
        item.setName("VL_VENDA");
        item.setTableFieldName("VLVND");
        item.setDataElement(cdata.evalue);

        model.add(item);
        
        item = new DocumentModelItem();
        item.setName("VL_CUSTO");
        item.setTableFieldName("VLCST");
        item.setDataElement(cdata.evalue);

        model.add(item);
        
        item = new DocumentModelItem();
        item.setName("DT_INICIAL");
        item.setTableFieldName("DTINI");
        item.setDataElement(cdata.edate);

        model.add(item);
        
        item = new DocumentModelItem();
        item.setName("DT_FINAL");
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
    	element = new DataElement();
    	element.setName("SUB_MATERIAL.ID");
    	element.setLength(23);
    	element.setType(DataType.CHAR);
    	element.setUpcase(true);
    	
    	item = new DocumentModelItem();
    	item.setName("ID");
    	item.setTableFieldName("IDENT");
    	item.setDataElement(element);
    	
    	model.add(item);
    	model.add(new DocumentModelKey(item));
    	
    	// material referência
    	item = new DocumentModelItem();
    	item.setName("MATERIAL");
    	item.setTableFieldName("MATID");
    	item.setDataElement(cdata.ematid);
    	item.setReference(cdata.imatid);
    	
    	model.add(item);
    	
    	// sub-material
    	item = new DocumentModelItem();
    	item.setName("SUB_MATERIAL");
    	item.setTableFieldName("SUBMT");
    	item.setDataElement(cdata.ematid);
    	item.setReference(cdata.imatid);
    	item.setSearchHelp("SH_MATERIAL");
    	
    	model.add(item);
    	
    }
}

class CData {
    public DataElement evalue, edate, ematid;
    public DocumentModelItem imatid, item, mattypeid;
}
