package org.erp.custom.sd.partner.install;

import org.iocaste.documents.common.DataElement;
import org.iocaste.documents.common.DataType;
import org.iocaste.documents.common.DocumentModelItem;
import org.iocaste.documents.common.DocumentModelKey;
import org.iocaste.packagetool.common.SearchHelpData;

public class PartnerType {
    public static final void install(InstallContext context) {
        DataElement element;
        DocumentModelItem item;
        SearchHelpData shdata;
        
        context.partnertype = context.data.getModel(
                "CUSTOM_PARTNER_TYPE", "CPRTNRTYPE", null);
        
        element = new DataElement("CUSTOM_PARTNER_TYPE.CODIGO");
        element.setLength(2);
        element.setType(DataType.CHAR);
        element.setUpcase(true);
        item = new DocumentModelItem("CODIGO");
        item.setDataElement(element);
        item.setTableFieldName("PRTCD");
        context.partnertype.add(item);
        context.partnertype.add(new DocumentModelKey(item));
        
        element = new DataElement("CUSTOM_PARTNER_TYPE.DESCRICAO");
        element.setLength(40);
        element.setType(DataType.CHAR);
        element.setUpcase(true);
        item = new DocumentModelItem("DESCRICAO");
        item.setDataElement(element);
        item.setTableFieldName("DESCR");
        context.partnertype.add(item);
        
        context.data.addValues(context.partnertype, "CL", "CLIENTE");
        context.data.addValues(context.partnertype, "FR", "FORNECEDOR");
        context.data.addValues(context.partnertype, "TR", "TRANSPORTADOR");
        
        shdata = new SearchHelpData("SH_PARTNER_TYPE");
        shdata.add("CODIGO");
        shdata.add("DESCRICAO");
        shdata.setModel("CUSTOM_PARTNER_TYPE");
        shdata.setExport("CODIGO");
        context.data.add(shdata);
    }

}
