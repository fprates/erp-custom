package org.erp.custom.mm.materials;

import org.iocaste.documents.common.DataElement;
import org.iocaste.documents.common.DataType;
import org.iocaste.documents.common.DocumentModel;
import org.iocaste.documents.common.DocumentModelItem;
import org.iocaste.documents.common.DocumentModelKey;
import org.iocaste.packagetool.common.InstallData;
import org.iocaste.packagetool.common.SearchHelpData;

public class Install {
    
    /**
     * 
     * @return
     */
    public static final InstallData self() {
        DataElement evalue, edate, ematid, element;
        DocumentModelItem imatid, item;
        SearchHelpData sh;
        InstallData data = new InstallData();
        DocumentModel promocaoMaterial, precoMaterial, material =
                data.getModel("MATERIAL", "CMATERIAL", "");
        
        /*
         * MATERIAL
         */
        ematid = new DataElement();
        ematid.setName("MATERIAL.ID");
        ematid.setLength(20);
        ematid.setType(DataType.CHAR);
        ematid.setUpcase(true);
        
        imatid = new DocumentModelItem();
        imatid.setDocumentModel(material);
        imatid.setName("ID");
        imatid.setTableFieldName("IDENT");
        imatid.setDataElement(ematid);
        imatid.setSearchHelp("SH_MATERIAL");
        imatid.setIndex(0);
        
        material.add(imatid);
        material.add(new DocumentModelKey(imatid));
        
        element = new DataElement();
        element.setName("MATERIAL.NAME");
        element.setLength(60);
        element.setType(DataType.CHAR);
        element.setUpcase(true);
        
        item = new DocumentModelItem();
        item.setDocumentModel(material);
        item.setName("NAME");
        item.setTableFieldName("NAME1");
        item.setDataElement(element);
        item.setIndex(1);
        
        material.add(item);
        
        element = new DataElement();
        element.setName("MATERIAL.ACTIVE");
        element.setLength(1);
        element.setType(DataType.NUMC);
        
        item = new DocumentModelItem();
        item.setDocumentModel(material);
        item.setName("ACTIVE");
        item.setTableFieldName("ACTIV");
        item.setDataElement(element);
        item.setIndex(2);
        
        material.add(item);
        
        sh = new SearchHelpData();
        sh.setName("SH_MATERIAL");
        sh.setModel("MATERIAL");
        sh.setExport("ID");
        sh.add("ID");
        sh.add("NAME");
        
        /*
         * PRECO_MATERIAL
         */
        precoMaterial = data.getModel("PRECO_MATERIAL", "CMATPRICE", "");
        
        element = new DataElement();
        element.setName("PRECO_MATERIAL.ID");
        element.setLength(23);
        element.setType(DataType.CHAR);
        element.setUpcase(true);
        
        item = new DocumentModelItem();
        item.setName("ID");
        item.setTableFieldName("IDENT");
        item.setDocumentModel(precoMaterial);
        item.setDataElement(element);
        item.setIndex(0);
        
        precoMaterial.add(item);
        precoMaterial.add(new DocumentModelKey(item));
        
        item = new DocumentModelItem();
        item.setName("MATERIAL");
        item.setTableFieldName("MATCD");
        item.setDocumentModel(precoMaterial);
        item.setDataElement(ematid);
        item.setReference(imatid);
        item.setIndex(1);
        
        precoMaterial.add(item);
        
        evalue = new DataElement();
        evalue.setName("PRECO_MATERIAL.VALOR");
        evalue.setDecimals(3);
        evalue.setLength(12);
        evalue.setType(DataType.DEC);
        
        item = new DocumentModelItem();
        item.setName("VL_VENDA");
        item.setTableFieldName("VLVND");
        item.setDataElement(evalue);
        item.setDocumentModel(precoMaterial);
        item.setIndex(2);
        
        precoMaterial.add(item);
        
        item = new DocumentModelItem();
        item.setName("VL_CUSTO");
        item.setTableFieldName("VLCST");
        item.setDataElement(evalue);
        item.setDocumentModel(precoMaterial);
        item.setIndex(3);
        
        precoMaterial.add(item);
        
        edate = new DataElement();
        edate.setName("PRECO_MATERIAL.DATA");
        edate.setType(DataType.DATE);
        
        item = new DocumentModelItem();
        item.setName("DT_INICIAL");
        item.setTableFieldName("DTINI");
        item.setDataElement(edate);
        item.setDocumentModel(precoMaterial);
        item.setIndex(4);
        
        precoMaterial.add(item);
        
        item = new DocumentModelItem();
        item.setName("DT_FINAL");
        item.setTableFieldName("DTTRM");
        item.setDocumentModel(precoMaterial);
        item.setDataElement(edate);
        item.setIndex(5);
        
        precoMaterial.add(item);
        
        /*
         * PROMOCAO_MATERIAL
         */
        promocaoMaterial = data.getModel("PROMOCAO_MATERIAL", "CMATPROMO", "");
        
        element = new DataElement();
        element.setName("PROMOCAO_MATERIAL.ID");
        element.setType(DataType.CHAR);
        element.setLength(23);
        element.setUpcase(true);
        
        item = new DocumentModelItem();
        item.setName("ID");
        item.setTableFieldName("IDENT");
        item.setDocumentModel(promocaoMaterial);
        item.setDataElement(element);
        item.setIndex(0);
        
        promocaoMaterial.add(item);
        promocaoMaterial.add(new DocumentModelKey(item));
        
        item = new DocumentModelItem();
        item.setName("MATERIAL");
        item.setDocumentModel(promocaoMaterial);
        item.setDataElement(ematid);
        item.setTableFieldName("MATCD");
        item.setReference(imatid);
        item.setIndex(1);

        promocaoMaterial.add(item);
        
        item = new DocumentModelItem();
        item.setName("VL_VENDA");
        item.setTableFieldName("VLVND");
        item.setDataElement(evalue);
        item.setDocumentModel(promocaoMaterial);
        item.setIndex(2);

        promocaoMaterial.add(item);
        
        item = new DocumentModelItem();
        item.setName("VL_CUSTO");
        item.setTableFieldName("VLCST");
        item.setDataElement(evalue);
        item.setDocumentModel(promocaoMaterial);
        item.setIndex(3);

        promocaoMaterial.add(item);
        
        item = new DocumentModelItem();
        item.setName("DT_INICIAL");
        item.setTableFieldName("DTINI");
        item.setDataElement(edate);
        item.setDocumentModel(promocaoMaterial);
        item.setIndex(4);

        promocaoMaterial.add(item);
        
        item = new DocumentModelItem();
        item.setName("DT_FINAL");
        item.setTableFieldName("DTTRM");
        item.setDataElement(edate);
        item.setDocumentModel(promocaoMaterial);
        item.setIndex(5);
        
        promocaoMaterial.add(item);
        
        data.add(sh);
        data.link("MATERIAL", "erp-custom-mm.materials");
        data.link("MM01", "erp-custom-mm.materials");
        
        return data;
    }
}
