package org.erp.custom.sd.partner.install;

import org.iocaste.documents.common.DataElement;
import org.iocaste.documents.common.DataType;
import org.iocaste.documents.common.DocumentModelItem;
import org.iocaste.documents.common.DocumentModelKey;
import org.iocaste.packagetool.common.SearchHelpData;

public class Partner {

    public static final void install(InstallContext context) {
        DataElement element;
        DocumentModelItem typecode, item;
        SearchHelpData shdata;
        
        context.partner = context.data.getModel(
                "CUSTOM_PARTNER", "CPARTNER",
                "org.erp.custom.sd.partner.common.PartnerData");
        
        element = new DataElement("CUSTOM_PARTNER.CODIGO");
        element.setLength(10);
        element.setType(DataType.NUMC);
        element.setAttributeType(DataType.LONG);
        item = new DocumentModelItem("CODIGO");
        item.setTableFieldName("IDENT");
        item.setAttributeName("id");
        item.setDataElement(element);
        item.setSearchHelp("SH_PARTNER");
        context.partner.add(item);
        context.partner.add(new DocumentModelKey(item));
        
        element = new DataElement("CUSTOM_PARTNER.NAME");
        element.setLength(40);
        element.setType(DataType.CHAR);
        element.setUpcase(true);
        item = new DocumentModelItem("RAZAO_SOCIAL");
        item.setTableFieldName("NAME1");
        item.setAttributeName("name1");
        item.setDataElement(element);
        context.partner.add(item);
        
        item = new DocumentModelItem("NOME_FANTASIA");
        item.setTableFieldName("NAME2");
        item.setAttributeName("name2");
        item.setDataElement(element);
        context.partner.add(item);
        
        element = new DataElement("CUSTOM_PARTNER.DOCUMENTO_FISCAL");
        element.setLength(22);
        element.setType(DataType.CHAR);
        item = new DocumentModelItem("DOCUMENTO_FISCAL");
        item.setTableFieldName("DOCFS");
        item.setDataElement(element);
        context.partner.add(item);
        
        element = new DataElement("CUSTOM_PARTNER.INSCR_ESTADUAL");
        element.setLength(18);
        element.setType(DataType.CHAR);
        element.setUpcase(true);
        item = new DocumentModelItem("INSCR_ESTADUAL");
        item.setTableFieldName("IESTA");
        item.setDataElement(element);
        context.partner.add(item);
        
        element = new DataElement("CUSTOM_PARTNER.INSCR_MUNICIPAL");
        element.setLength(20);
        element.setType(DataType.CHAR);
        element.setUpcase(true);
        item = new DocumentModelItem("INSCR_MUNICIPAL");
        item.setTableFieldName("IMUNI");
        item.setDataElement(element);
        context.partner.add(item);
        
        element = new DataElement("CUSTOM_PARTNER.TIPO_PESSOA");
        element.setLength(1);
        element.setType(DataType.NUMC);
        item = new DocumentModelItem("TIPO_PESSOA");
        item.setTableFieldName("TPPES");
        item.setAttributeName("entityType");
        item.setDataElement(element);
        context.partner.add(item);
        
        typecode = context.partnertype.getModelItem("CODIGO");
        item = new DocumentModelItem("TIPO_PARCEIRO");
        item.setTableFieldName("TPPAR");
        item.setAttributeName("type");
        item.setDataElement(typecode.getDataElement());
        item.setReference(typecode);
        item.setSearchHelp("SH_PARTNER_TYPE");
        context.partner.add(item);
        
        shdata = new SearchHelpData("SH_PARTNER");
        shdata.add("CODIGO");
        shdata.add("RAZAO_SOCIAL");
        shdata.setModel("CUSTOM_PARTNER");
        shdata.setExport("CODIGO");
        context.data.add(shdata);
    }
}
