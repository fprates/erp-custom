package org.erp.custom.sd.partner;

import org.iocaste.documents.common.DataElement;
import org.iocaste.documents.common.DataType;
import org.iocaste.documents.common.DocumentModel;
import org.iocaste.documents.common.DocumentModelItem;
import org.iocaste.documents.common.DocumentModelKey;
import org.iocaste.packagetool.common.InstallData;
import org.iocaste.packagetool.common.SearchHelpData;

public class Install {
    
    public static final InstallData self() {
        DataElement element;
        SearchHelpData shdata;
        DocumentModelItem item, partnercode, typecode;
        InstallData data = new InstallData();
        DocumentModel address, partner, partnertype;
        
        /*
         * Partner type
         */
        partnertype = data.getModel("CUSTOM_PARTNER_TYPE", "CPRTNRTYPE", "");
        
        element = new DataElement();
        element.setName("CUSTOM_PARTNER_TYPE.CODIGO");
        element.setLength(3);
        element.setType(DataType.NUMC);
        
        typecode = new DocumentModelItem();
        typecode.setName("CODIGO");
        typecode.setDataElement(element);
        typecode.setDocumentModel(partnertype);
        typecode.setTableFieldName("IDENT");
        typecode.setIndex(0);
        
        partnertype.add(typecode);
        partnertype.add(new DocumentModelKey(typecode));
        
        element = new DataElement();
        element.setName("CUSTOM_PARTNER_TYPE.SIGLA");
        element.setLength(2);
        element.setType(DataType.CHAR);
        element.setUpcase(true);
        
        item = new DocumentModelItem();
        item.setName("SIGLA");
        item.setDataElement(element);
        item.setDocumentModel(partnertype);
        item.setTableFieldName("SIGLA");
        item.setIndex(1);
        
        partnertype.add(item);
        
        element = new DataElement();
        element.setName("CUSTOM_PARTNER_TYPE.DESCRICAO");
        element.setLength(40);
        element.setType(DataType.CHAR);
        element.setUpcase(true);
        
        item = new DocumentModelItem();
        item.setName("DESCRICAO");
        item.setDocumentModel(partnertype);
        item.setDataElement(element);
        item.setTableFieldName("DESCR");
        item.setIndex(2);
        
        partnertype.add(item);
        
        data.addValues(partnertype, 1, "CL", "CLIENTE");
        data.addValues(partnertype, 2, "FR", "FORNECEDOR");
        data.addValues(partnertype, 3, "TR", "TRANSPORTADOR");
        
        /*
         * Partner
         */
        partner = data.getModel("CUSTOM_PARTNER", "CPARTNER", "");
        
        element = new DataElement();
        element.setName("CUSTOM_PARTNER.CODIGO");
        element.setLength(10);
        element.setType(DataType.NUMC);
        
        partnercode = new DocumentModelItem();
        partnercode.setDocumentModel(partner);
        partnercode.setName("CODIGO");
        partnercode.setTableFieldName("IDENT");
        partnercode.setDataElement(element);
        partnercode.setIndex(0);
        partnercode.setSearchHelp("SH_PARTNER");
        
        partner.add(partnercode);
        partner.add(new DocumentModelKey(partnercode));
        
        element = new DataElement();
        element.setName("CUSTOM_PARTNER.NAME");
        element.setLength(40);
        element.setType(DataType.CHAR);
        element.setUpcase(true);
        
        item = new DocumentModelItem();
        item.setDocumentModel(partner);
        item.setName("RAZAO_SOCIAL");
        item.setTableFieldName("NAME1");
        item.setDataElement(element);
        item.setIndex(1);
        
        partner.add(item);
        
        item = new DocumentModelItem();
        item.setDocumentModel(partner);
        item.setName("NOME_FANTASIA");
        item.setTableFieldName("NAME2");
        item.setDataElement(element);
        item.setIndex(2);
        
        partner.add(item);
        
        element = new DataElement();
        element.setName("CUSTOM_PARTNER.DOCUMENTO_FISCAL");
        element.setLength(22);
        element.setType(DataType.CHAR);
        
        item = new DocumentModelItem();
        item.setDocumentModel(partner);
        item.setName("DOCUMENTO_FISCAL");
        item.setTableFieldName("DOCFS");
        item.setDataElement(element);
        item.setIndex(3);
        
        partner.add(item);
        
        element = new DataElement();
        element.setName("CUSTOM_PARTNER.INSCR_ESTADUAL");
        element.setLength(18);
        element.setType(DataType.CHAR);
        element.setUpcase(true);
        
        item = new DocumentModelItem();
        item.setDocumentModel(partner);
        item.setName("INSCR_ESTADUAL");
        item.setTableFieldName("IESTA");
        item.setDataElement(element);
        item.setIndex(4);
        
        partner.add(item);
        
        element = new DataElement();
        element.setName("CUSTOM_PARTNER.INSCR_MUNICIPAL");
        element.setLength(20);
        element.setType(DataType.CHAR);
        element.setUpcase(true);
        
        item = new DocumentModelItem();
        item.setDocumentModel(partner);
        item.setName("INSCR_MUNICIPAL");
        item.setTableFieldName("IMUNI");
        item.setDataElement(element);
        item.setIndex(5);
        
        partner.add(item);
        
        element = new DataElement();
        element.setName("CUSTOM_PARTNER.TIPO_PESSOA");
        element.setLength(1);
        element.setType(DataType.NUMC);
        
        item = new DocumentModelItem();
        item.setDocumentModel(partner);
        item.setName("TIPO_PESSOA");
        item.setTableFieldName("TPPES");
        item.setDataElement(element);
        item.setIndex(6);
        
        partner.add(item);
        
        item = new DocumentModelItem();
        item.setDocumentModel(partner);
        item.setName("TIPO_PARCEIRO");
        item.setTableFieldName("TPPAR");
        item.setDataElement(typecode.getDataElement());
        item.setReference(typecode);
        item.setSearchHelp("SH_PARTNER_TYPE");
        item.setIndex(7);
        
        partner.add(item);
        
        /*
         * Partner address
         */
        address = data.getModel("CUSTOM_PARTNER_ADDRESS", "CPARTNERADDR", "");
        
        element = new DataElement();
        element.setName("CUSTOM_PARTNER_ADDRESS.ADDRESS_ID");
        element.setLength(12);
        element.setType(DataType.NUMC);
        
        item = new DocumentModelItem();
        item.setDocumentModel(address);
        item.setName("ADDRESS_ID");
        item.setTableFieldName("IDENT");
        item.setDataElement(element);
        item.setIndex(0);
        
        address.add(item);
        address.add(new DocumentModelKey(item));
        
        element = new DataElement();
        element.setName("CUSTOM_PARTNER_ADDRESS.PARTNER_ID");
        element.setLength(10);
        element.setType(DataType.NUMC);
        
        item = new DocumentModelItem();
        item.setDocumentModel(address);
        item.setName("PARTNER_ID");
        item.setTableFieldName("PRTNR");
        item.setDataElement(partnercode.getDataElement());
        item.setReference(partnercode);
        item.setIndex(1);
        
        address.add(item);
        
        element = new DataElement();
        element.setName("CUSTOM_PARTNER_ADDRESS.LOGRADOURO");
        element.setLength(60);
        element.setType(DataType.CHAR);
        element.setUpcase(true);
        
        item = new DocumentModelItem();
        item.setDocumentModel(address);
        item.setName("LOGRADOURO");
        item.setTableFieldName("LOGRA");
        item.setDataElement(element);
        item.setIndex(2);
        
        address.add(item);
        
        element = new DataElement();
        element.setName("CUSTOM_PARTNER_ADDRESS.CEP");
        element.setLength(8);
        element.setType(DataType.NUMC);
        
        item = new DocumentModelItem();
        item.setDocumentModel(address);
        item.setName("CEP");
        item.setTableFieldName("CPSTL");
        item.setDataElement(element);
        item.setIndex(3);
        
        address.add(item);
        
        element = new DataElement();
        element.setName("CUSTOM_PARTNER_ADDRESS.BAIRRO");
        element.setLength(30);
        element.setType(DataType.CHAR);
        element.setUpcase(true);
        
        item = new DocumentModelItem();
        item.setDocumentModel(address);
        item.setName("BAIRRO");
        item.setTableFieldName("NEIGH");
        item.setDataElement(element);
        item.setIndex(4);
        
        address.add(item);
        
        element = new DataElement();
        element.setName("CUSTOM_PARTNER_ADDRESS.CIDADE");
        element.setLength(30);
        element.setType(DataType.CHAR);
        element.setUpcase(true);
        
        item = new DocumentModelItem();
        item.setDocumentModel(address);
        item.setName("CIDADE");
        item.setTableFieldName("CITY");
        item.setDataElement(element);
        item.setIndex(5);
        
        address.add(item);
        
        element = new DataElement();
        element.setName("CUSTOM_PARTNER_ADDRESS.TELEFONE");
        element.setLength(16);
        element.setType(DataType.CHAR);
        element.setUpcase(true);
        
        item = new DocumentModelItem();
        item.setDocumentModel(address);
        item.setName("TELEFONE");
        item.setTableFieldName("TEL01");
        item.setDataElement(element);
        item.setIndex(6);
        
        address.add(item);
        
        shdata = new SearchHelpData();
        shdata.add("CODIGO");
        shdata.add("RAZAO_SOCIAL");
        shdata.setName("SH_PARTNER");
        shdata.setModel("CUSTOM_PARTNER");
        shdata.setExport("CODIGO");
        
        data.add(shdata);
        
        shdata = new SearchHelpData();
        shdata.add("CODIGO");
        shdata.add("SIGLA");
        shdata.add("DESCRICAO");
        shdata.add("DOCUMENTO_FISCAL");
        shdata.setName("SH_PARTNER_TYPE");
        shdata.setModel("CUSTOM_PARTNER_TYPE");
        shdata.setExport("CODIGO");
        
        data.add(shdata);
        
        data.addNumberFactory("CUSTPARTNER");
        data.link("PARTNER", "erp-custom-sd.partner");
        data.link("XD01", "erp-custom-sd.partner");
        
        return data;
    }
}
