package org.erp.custom.sd.partner;

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
     * @param data
     * @return
     */
    private static final DocumentModel installAddressType(InstallData data) {
        DataElement element;
        DocumentModelItem item;
        DocumentModel addresstype = data.
                getModel("CUSTOM_ADDRESS_TYPE", "CADDRTYPE", "");

        element = new DataElement();
        element.setName("CUSTOM_ADDRESS_TYPE.CODIGO");
        element.setLength(3);
        element.setType(DataType.NUMC);
        
        item = new DocumentModelItem();
        item.setDocumentModel(addresstype);
        item.setName("CODIGO");
        item.setTableFieldName("IDENT");
        item.setDataElement(element);
        item.setIndex(0);
        
        addresstype.add(item);
        addresstype.add(new DocumentModelKey(item));
        
        element = new DataElement();
        element.setName("CUSTOM_ADDRESS_TYPE.DESCRICAO");
        element.setLength(40);
        element.setType(DataType.CHAR);
        element.setUpcase(true);
        
        item = new DocumentModelItem();
        item.setDocumentModel(addresstype);
        item.setName("DESCRICAO");
        item.setTableFieldName("DESCR");
        item.setDataElement(element);
        item.setIndex(1);
        
        addresstype.add(item);
        
        data.addValues(addresstype, 1, "COMERCIAL");
        
        return addresstype;
    }
    
    /**
     * 
     * @param data
     * @param partnertype
     * @return
     */
    private static final DocumentModel installPartner(InstallData data,
            DocumentModel partnertype) {
        DataElement element;
        DocumentModelItem typecode, item;
        DocumentModel partner = data.getModel("CUSTOM_PARTNER", "CPARTNER", "");
        int i = 0;
        
        element = new DataElement();
        element.setName("CUSTOM_PARTNER.CODIGO");
        element.setLength(10);
        element.setType(DataType.NUMC);
        
        item = new DocumentModelItem();
        item.setDocumentModel(partner);
        item.setName("CODIGO");
        item.setTableFieldName("IDENT");
        item.setDataElement(element);
        item.setIndex(i++);
        item.setSearchHelp("SH_PARTNER");
        
        partner.add(item);
        partner.add(new DocumentModelKey(item));
        
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
        item.setIndex(i++);
        
        partner.add(item);
        
        item = new DocumentModelItem();
        item.setDocumentModel(partner);
        item.setName("NOME_FANTASIA");
        item.setTableFieldName("NAME2");
        item.setDataElement(element);
        item.setIndex(i++);
        
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
        item.setIndex(i++);
        
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
        item.setIndex(i++);
        
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
        item.setIndex(i++);
        
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
        item.setIndex(i++);
        
        partner.add(item);
        
        typecode = partnertype.getModelItem("CODIGO");
        
        item = new DocumentModelItem();
        item.setDocumentModel(partner);
        item.setName("TIPO_PARCEIRO");
        item.setTableFieldName("TPPAR");
        item.setDataElement(typecode.getDataElement());
        item.setReference(typecode);
        item.setSearchHelp("SH_PARTNER_TYPE");
        item.setIndex(i++);
        
        partner.add(item);
        
        return partner;
    }
    
    /**
     * 
     * @param data
     * @param partner
     */
    private static final void installPartnerAddress(InstallData data, 
            DocumentModel partner, DocumentModel addresstype) {
        DataElement element;
        DocumentModelItem item, partnercode, addresstypecode;
        DocumentModel address = data.
                getModel("CUSTOM_PARTNER_ADDRESS", "CPARTNERADDR", "");
        int i = 0;
        
        element = new DataElement();
        element.setName("CUSTOM_PARTNER_ADDRESS.ADDRESS_ID");
        element.setLength(12);
        element.setType(DataType.NUMC);
        
        item = new DocumentModelItem();
        item.setDocumentModel(address);
        item.setName("ADDRESS_ID");
        item.setTableFieldName("IDENT");
        item.setDataElement(element);
        item.setIndex(i++);
        
        address.add(item);
        address.add(new DocumentModelKey(item));
        
        element = new DataElement();
        element.setName("CUSTOM_PARTNER_ADDRESS.PARTNER_ID");
        element.setLength(10);
        element.setType(DataType.NUMC);
        
        partnercode = partner.getModelItem("CODIGO");
        
        item = new DocumentModelItem();
        item.setDocumentModel(address);
        item.setName("PARTNER_ID");
        item.setTableFieldName("PRTNR");
        item.setDataElement(partnercode.getDataElement());
        item.setReference(partnercode);
        item.setIndex(i++);
        
        address.add(item);

        addresstypecode = addresstype.getModelItem("CODIGO");
        item = new DocumentModelItem();
        item.setDocumentModel(address);
        item.setName("TIPO_ENDERECO");
        item.setTableFieldName("TPEND");
        item.setDataElement(element);
        item.setReference(addresstypecode);
        item.setSearchHelp("SH_ADDRESS_TYPE");
        item.setIndex(i++);
        
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
        item.setIndex(i++);
        
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
        item.setIndex(i++);
        
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
        item.setIndex(i++);
        
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
        item.setIndex(i++);
        
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
        item.setIndex(i++);
        
        address.add(item);
        
        element = new DataElement();
        element.setName("CUSTOM_PARTNER_ADDRESS.EMAIL");
        element.setLength(128);
        element.setType(DataType.CHAR);
        element.setUpcase(false);
        
        item = new DocumentModelItem();
        item.setDocumentModel(address);
        item.setName("EMAIL");
        item.setTableFieldName("EMAIL");
        item.setDataElement(element);
        item.setIndex(i++);
        
        address.add(item);
        
        element = new DataElement();
        element.setName("CUSTOM_PARTNER_ADDRESS.WEB_PAGE");
        element.setLength(128);
        element.setType(DataType.CHAR);
        element.setUpcase(false);
        
        item = new DocumentModelItem();
        item.setDocumentModel(address);
        item.setName("WEB_PAGE");
        item.setTableFieldName("WPAGE");
        item.setDataElement(element);
        item.setIndex(i);
        
        address.add(item);
    }
    
    /**
     * 
     * @param data
     * @return
     */
    private static final DocumentModel installPartnerType(InstallData data) {
        DataElement element;
        DocumentModel partnertype;
        DocumentModelItem item;
        
        partnertype = data.getModel("CUSTOM_PARTNER_TYPE", "CPRTNRTYPE", "");
        
        element = new DataElement();
        element.setName("CUSTOM_PARTNER_TYPE.CODIGO");
        element.setLength(3);
        element.setType(DataType.NUMC);
        
        item = new DocumentModelItem();
        item.setName("CODIGO");
        item.setDataElement(element);
        item.setDocumentModel(partnertype);
        item.setTableFieldName("IDENT");
        item.setIndex(0);
        
        partnertype.add(item);
        partnertype.add(new DocumentModelKey(item));
        
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
        
        return partnertype;
    }
    
    /**
     * 
     * @return
     */
    public static final InstallData self() {
        SearchHelpData shdata;
        InstallData data = new InstallData();
        DocumentModel partner, partnertype, addresstype;
        
        partnertype = installPartnerType(data);
        partner = installPartner(data, partnertype);
        addresstype = installAddressType(data);
        installPartnerAddress(data, partner, addresstype);
        
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
        
        shdata = new SearchHelpData();
        shdata.add("CODIGO");
        shdata.add("DESCRICAO");
        shdata.setName("SH_ADDRESS_TYPE");
        shdata.setModel("CUSTOM_ADDRESS_TYPE");
        shdata.setExport("CODIGO");
        
        data.add(shdata);
        
        data.addNumberFactory("CUSTPARTNER");
        data.link("PARTNER", "erp-custom-sd.partner");
        data.link("XD01", "erp-custom-sd.partner");
        
        return data;
    }
}
