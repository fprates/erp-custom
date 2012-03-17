package org.erp.custom.mm.materials;

import org.iocaste.documents.common.DataElement;
import org.iocaste.documents.common.DataType;
import org.iocaste.documents.common.DocumentModel;
import org.iocaste.packagetool.common.InstallData;

public class Install {
    private static final boolean IS_KEY = true;
    
    public static final InstallData self() {
        DataElement element;
        InstallData data = new InstallData();
        DocumentModel material = data.getModel("MATERIAL", "CMATERIAL", "");
        
        element = data.getDataElement("MATERIAL.ID", 0, 20, DataType.CHAR,
                DataType.UPPERCASE);
        data.addModelItem(material, "ID", "IDENT", element, "id", IS_KEY);
        
        element = data.getDataElement("MATERIAL.NAME", 0, 60, DataType.CHAR,
                DataType.UPPERCASE);
        data.addModelItem(material, "NAME", "NAME1", element, "name", !IS_KEY);
        
        element = data.getDataElement("MATERIAL.ACTIVE", 0, 1, DataType.NUMC,
                DataType.KEEPCASE);
        data.addModelItem(material, "ACTIVE", "ACTIV", element, "active",
                !IS_KEY);
        
        data.link("MATERIAL", "erp-custom-mm.materials");
        data.link("MM01", "erp-custom-mm.materials");
        
        return data;
    }
}
