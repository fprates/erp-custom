package org.erp.custom.sd.partner.install;

import org.iocaste.documents.common.DataElement;
import org.iocaste.documents.common.DataType;
import org.iocaste.documents.common.DocumentModelItem;
import org.iocaste.documents.common.DocumentModelKey;
import org.iocaste.packagetool.common.SearchHelpData;

public class AddressType {

    public static final void install(InstallContext context) {
        DataElement element;
        DocumentModelItem item;
        SearchHelpData shdata;
        
        context.addresstype = context.data.getModel(
                "CUSTOM_ADDRESS_TYPE", "CADDRTYPE", null);
        element = new DataElement("CUSTOM_ADDRESS_TYPE.CODIGO");
        element.setLength(3);
        element.setType(DataType.NUMC);
        item = new DocumentModelItem("CODIGO");
        item.setTableFieldName("IDENT");
        item.setDataElement(element);
        context.addresstype.add(item);
        context.addresstype.add(new DocumentModelKey(item));
        
        element = new DataElement("CUSTOM_ADDRESS_TYPE.DESCRICAO");
        element.setLength(40);
        element.setType(DataType.CHAR);
        element.setUpcase(true);
        item = new DocumentModelItem("DESCRICAO");
        item.setTableFieldName("DESCR");
        item.setDataElement(element);
        context.addresstype.add(item);
        
        context.data.addValues(context.addresstype, 0, "COMERCIAL");
        
        shdata = new SearchHelpData("SH_ADDRESS_TYPE");
        shdata.add("CODIGO");
        shdata.add("DESCRICAO");
        shdata.setModel("CUSTOM_ADDRESS_TYPE");
        shdata.setExport("CODIGO");
        context.data.add(shdata);
    }
}
