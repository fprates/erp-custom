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
        item.setName("DESCRICAO");
        item.setTableFieldName("DESCR");
        item.setDataElement(element);
        item.setIndex(1);
        
        addresstype.add(item);
        
        data.addValues(addresstype, 1, "COMERCIAL");
        
        return addresstype;
    }
    
    private static final DocumentModel installCommunication(InstallData data) {
        DataElement element;
        DocumentModelItem item;
        DocumentModel model = data.getModel("CUSTOM_COMMUNICATION", "COMMUNIC",
                "");
        
        element = new DataElement();
        element.setName("CUSTOM_COMMUNICATION.CODIGO");
        element.setLength(3);
        element.setType(DataType.NUMC);
        
        item = new DocumentModelItem();
        item.setName("CODIGO");
        item.setTableFieldName("IDENT");
        item.setDataElement(element);
        
        model.add(item);
        model.add(new DocumentModelKey(item));
        
        element = new DataElement();
        element.setName("CUSTOM_COMMUNICATION.DESCRICAO");
        element.setLength(40);
        element.setType(DataType.CHAR);
        element.setUpcase(true);
        
        item = new DocumentModelItem();
        item.setName("DESCRICAO");
        item.setTableFieldName("DESCR");
        item.setDataElement(element);
        
        model.add(item);
        
        return model;
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
        item.setName("RAZAO_SOCIAL");
        item.setTableFieldName("NAME1");
        item.setDataElement(element);
        item.setIndex(i++);
        
        partner.add(item);
        
        item = new DocumentModelItem();
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
        item.setName("TIPO_PESSOA");
        item.setTableFieldName("TPPES");
        item.setDataElement(element);
        item.setIndex(i++);
        
        partner.add(item);
        
        typecode = partnertype.getModelItem("CODIGO");
        
        item = new DocumentModelItem();
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
     * @param addresstype
     * @return
     */
    private static final DocumentModel installPartnerAddress(InstallData data, 
            DocumentModel partner, DocumentModel addresstype) {
        DataElement element;
        DocumentModelItem item, partnercode, addresstypecode;
        DocumentModel address = data.
                getModel("CUSTOM_PARTNER_ADDRESS", "CPARTNERADDR", "");
        
        element = new DataElement();
        element.setName("CUSTOM_PARTNER_ADDRESS.CODIGO");
        element.setLength(12);
        element.setType(DataType.NUMC);
        
        item = new DocumentModelItem();
        item.setName("CODIGO");
        item.setTableFieldName("IDENT");
        item.setDataElement(element);
        
        address.add(item);
        address.add(new DocumentModelKey(item));
        
        partnercode = partner.getModelItem("CODIGO");
        
        item = new DocumentModelItem();
        item.setName("PARTNER_ID");
        item.setTableFieldName("PRTNR");
        item.setDataElement(partnercode.getDataElement());
        item.setReference(partnercode);
        
        address.add(item);

        addresstypecode = addresstype.getModelItem("CODIGO");
        
        item = new DocumentModelItem();
        item.setName("TIPO_ENDERECO");
        item.setTableFieldName("TPEND");
        item.setDataElement(addresstypecode.getDataElement());
        item.setReference(addresstypecode);
        item.setSearchHelp("SH_ADDRESS_TYPE");
        
        address.add(item);
        
        element = new DataElement();
        element.setName("CUSTOM_PARTNER_ADDRESS.LOGRADOURO");
        element.setLength(60);
        element.setType(DataType.CHAR);
        element.setUpcase(true);
        
        item = new DocumentModelItem();
        item.setName("LOGRADOURO");
        item.setTableFieldName("LOGRA");
        item.setDataElement(element);
        
        address.add(item);
        
        element = new DataElement();
        element.setName("CUSTOM_PARTNER_ADDRESS.CEP");
        element.setLength(8);
        element.setType(DataType.NUMC);
        
        item = new DocumentModelItem();
        item.setName("CEP");
        item.setTableFieldName("CPSTL");
        item.setDataElement(element);
        
        address.add(item);
        
        element = new DataElement();
        element.setName("CUSTOM_PARTNER_ADDRESS.BAIRRO");
        element.setLength(30);
        element.setType(DataType.CHAR);
        element.setUpcase(true);
        
        item = new DocumentModelItem();
        item.setName("BAIRRO");
        item.setTableFieldName("NEIGH");
        item.setDataElement(element);
        
        address.add(item);
        
        element = new DataElement();
        element.setName("CUSTOM_PARTNER_ADDRESS.CIDADE");
        element.setLength(30);
        element.setType(DataType.CHAR);
        element.setUpcase(true);
        
        item = new DocumentModelItem();
        item.setName("CIDADE");
        item.setTableFieldName("CITY");
        item.setDataElement(element);
        
        address.add(item);
        
        element = new DataElement();
        element.setName("CUSTOM_PARTNER_ADDRESS.TELEFONE");
        element.setLength(16);
        element.setType(DataType.CHAR);
        element.setUpcase(true);
        
        item = new DocumentModelItem();
        item.setName("TELEFONE");
        item.setTableFieldName("TEL01");
        item.setDataElement(element);
        
        address.add(item);
        
        element = new DataElement();
        element.setName("CUSTOM_PARTNER_ADDRESS.EMAIL");
        element.setLength(128);
        element.setType(DataType.CHAR);
        element.setUpcase(false);
        
        item = new DocumentModelItem();
        item.setName("EMAIL");
        item.setTableFieldName("EMAIL");
        item.setDataElement(element);
        
        address.add(item);
        
        element = new DataElement();
        element.setName("CUSTOM_PARTNER_ADDRESS.WEB_PAGE");
        element.setLength(128);
        element.setType(DataType.CHAR);
        element.setUpcase(false);
        
        item = new DocumentModelItem();
        item.setName("WEB_PAGE");
        item.setTableFieldName("WPAGE");
        item.setDataElement(element);
        
        address.add(item);
        
        return address;
    }
    
    /**
     * 
     * @param data
     * @param partner
     */
    private static final void installPartnerContact(InstallData data,
            DocumentModel address, DocumentModel communication) {
        DataElement element;
        DocumentModelItem item, communicationcode, addresscode;
        DocumentModel contact = data.getModel("CUSTOM_PARTNER_CONTACT",
                "CPRTNRCNTCT", "");
        
        element = new DataElement();
        element.setName("CUSTOM_PARTNER_CONTACT.CODIGO");
        element.setLength(12);
        element.setType(DataType.NUMC);
        
        item = new DocumentModelItem();
        item.setName("CODIGO");
        item.setDataElement(element);
        item.setTableFieldName("IDENT");
        
        contact.add(item);
        contact.add(new DocumentModelKey(item));
        
        item = new DocumentModelItem();
        item.setName("PARTNER_ID");
        item.setTableFieldName("PRTNR");
        item.setDataElement(address.getModelItem("PARTNER_ID").
                getDataElement());
        
        contact.add(item);
        
        element = new DataElement();
        element.setName("CUSTOM_PARTNER_CONTACT.NOME");
        element.setLength(40);
        element.setType(DataType.CHAR);
        element.setUpcase(true);
        
        item = new DocumentModelItem();
        item.setName("PNOME");
        item.setTableFieldName("NAME1");
        item.setDataElement(element);
        
        contact.add(item);
        
        item = new DocumentModelItem();
        item.setName("UNOME");
        item.setTableFieldName("NAME2");
        item.setDataElement(element);
        
        contact.add(item);
        
        addresscode = address.getModelItem("CODIGO");
        item = new DocumentModelItem();
        item.setName("ADDRESS");
        item.setTableFieldName("ADDRS");
        item.setDataElement(addresscode.getDataElement());
        item.setReference(addresscode);
        
        contact.add(item);
        
        communicationcode = communication.getModelItem("CODIGO");
        item = new DocumentModelItem();
        item.setName("COMMUNICATION");
        item.setTableFieldName("COMMU");
        item.setDataElement(communicationcode.getDataElement());
        item.setReference(communicationcode);
        item.setSearchHelp("SH_COMMUNICATION");
        
        contact.add(item);
        
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
        item.setTableFieldName("IDENT");
        
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
        item.setTableFieldName("SIGLA");
        
        partnertype.add(item);
        
        element = new DataElement();
        element.setName("CUSTOM_PARTNER_TYPE.DESCRICAO");
        element.setLength(40);
        element.setType(DataType.CHAR);
        element.setUpcase(true);
        
        item = new DocumentModelItem();
        item.setName("DESCRICAO");
        item.setDataElement(element);
        item.setTableFieldName("DESCR");
        
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
        DocumentModel address, partner, partnertype, addresstype, communication;
        
        partnertype = installPartnerType(data);
        partner = installPartner(data, partnertype);
        addresstype = installAddressType(data);
        address = installPartnerAddress(data, partner, addresstype);
        communication = installCommunication(data);
        installPartnerContact(data, address, communication);
        
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
        
        shdata = new SearchHelpData();
        shdata.add("CODIGO");
        shdata.add("DESCRICAO");
        shdata.setName("SH_COMMUNICATION");
        shdata.setModel("CUSTOM_COMMUNICATION");
        shdata.setExport("CODIGO");
        
        data.add(shdata);
        
        data.addNumberFactory("CUSTPARTNER");
        data.link("PARTNER", "erp-custom-sd.partner");
        data.link("XD01", "erp-custom-sd.partner");
        
        return data;
    }
}
