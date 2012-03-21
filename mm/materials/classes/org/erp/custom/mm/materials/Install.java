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
        DataElement element;
        DocumentModelItem item;
        SearchHelpData sh;
        InstallData data = new InstallData();
        DocumentModel material = data.getModel("MATERIAL", "CMATERIAL", "");
        
        element = new DataElement();
        element.setName("MATERIAL.ID");
        element.setLength(20);
        element.setType(DataType.CHAR);
        element.setUpcase(true);
        
        item = new DocumentModelItem();
        item.setDocumentModel(material);
        item.setName("ID");
        item.setTableFieldName("IDENT");
        item.setDataElement(element);
        item.setSearchHelp("SH_MATERIAL");
        item.setIndex(0);
        
        material.add(item);
        material.add(new DocumentModelKey(item));
        
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
        
        data.add(sh);
        
        data.link("MATERIAL", "erp-custom-mm.materials");
        data.link("MM01", "erp-custom-mm.materials");
        
        return data;
    }
}
