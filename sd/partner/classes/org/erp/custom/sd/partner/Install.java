package org.erp.custom.sd.partner;

import java.util.HashMap;
import java.util.Map;

import org.iocaste.documents.common.DataElement;
import org.iocaste.documents.common.DataType;
import org.iocaste.documents.common.DocumentModel;
import org.iocaste.documents.common.DocumentModelItem;
import org.iocaste.documents.common.DocumentModelKey;
import org.iocaste.packagetool.common.InstallData;
import org.iocaste.packagetool.common.SearchHelpData;
import org.iocaste.protocol.user.Authorization;

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
        data.add(element);
        
        item = new DocumentModelItem();
        item.setName("CODIGO");
        item.setTableFieldName("IDENT");
        item.setDataElement(element);
        addresstype.add(item);
        addresstype.add(new DocumentModelKey(item));
        
        element = new DataElement();
        element.setName("CUSTOM_ADDRESS_TYPE.DESCRICAO");
        element.setLength(40);
        element.setType(DataType.CHAR);
        element.setUpcase(true);
        data.add(element);
        
        item = new DocumentModelItem();
        item.setName("DESCRICAO");
        item.setTableFieldName("DESCR");
        item.setDataElement(element);
        
        addresstype.add(item);
        
        data.addValues(addresstype, 0, "COMERCIAL");
        
        return addresstype;
    }
    
    /**
     * 
     * @param data
     * @return
     */
    private static final DocumentModel installCommunication(InstallData data) {
        DataElement element;
        DocumentModelItem item;
        DocumentModel model = data.getModel("CUSTOM_COMMUNICATION", "COMMUNIC",
                "");
        
        element = new DataElement();
        element.setName("CUSTOM_COMMUNICATION.CODIGO");
        element.setLength(3);
        element.setType(DataType.NUMC);
        data.add(element);
        
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
        data.add(element);
        
        item = new DocumentModelItem();
        item.setName("DESCRICAO");
        item.setTableFieldName("DESCR");
        item.setDataElement(element);
        
        model.add(item);
        
        data.addValues(model, 0, "TELEFONE");
        data.addValues(model, 1, "CELULAR");
        data.addValues(model, 2, "FAX");
        data.addValues(model, 3, "E-MAIL");
        
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
        
        element = new DataElement();
        element.setName("CUSTOM_PARTNER.CODIGO");
        element.setLength(10);
        element.setType(DataType.NUMC);
        data.add(element);
        
        item = new DocumentModelItem();
        item.setName("CODIGO");
        item.setTableFieldName("IDENT");
        item.setDataElement(element);
        item.setSearchHelp("SH_PARTNER");
        
        partner.add(item);
        partner.add(new DocumentModelKey(item));
        
        element = new DataElement();
        element.setName("CUSTOM_PARTNER.NAME");
        element.setLength(40);
        element.setType(DataType.CHAR);
        element.setUpcase(true);
        data.add(element);
        
        item = new DocumentModelItem();
        item.setName("RAZAO_SOCIAL");
        item.setTableFieldName("NAME1");
        item.setDataElement(element);
        
        partner.add(item);
        
        item = new DocumentModelItem();
        item.setName("NOME_FANTASIA");
        item.setTableFieldName("NAME2");
        item.setDataElement(element);
        
        partner.add(item);
        
        element = new DataElement();
        element.setName("CUSTOM_PARTNER.DOCUMENTO_FISCAL");
        element.setLength(22);
        element.setType(DataType.CHAR);
        data.add(element);
        
        item = new DocumentModelItem();
        item.setName("DOCUMENTO_FISCAL");
        item.setTableFieldName("DOCFS");
        item.setDataElement(element);
        
        partner.add(item);
        
        element = new DataElement();
        element.setName("CUSTOM_PARTNER.INSCR_ESTADUAL");
        element.setLength(18);
        element.setType(DataType.CHAR);
        element.setUpcase(true);
        data.add(element);
        
        item = new DocumentModelItem();
        item.setName("INSCR_ESTADUAL");
        item.setTableFieldName("IESTA");
        item.setDataElement(element);
        
        partner.add(item);
        
        element = new DataElement();
        element.setName("CUSTOM_PARTNER.INSCR_MUNICIPAL");
        element.setLength(20);
        element.setType(DataType.CHAR);
        element.setUpcase(true);
        data.add(element);
        
        item = new DocumentModelItem();
        item.setName("INSCR_MUNICIPAL");
        item.setTableFieldName("IMUNI");
        item.setDataElement(element);
        
        partner.add(item);
        
        element = new DataElement();
        element.setName("CUSTOM_PARTNER.TIPO_PESSOA");
        element.setLength(1);
        element.setType(DataType.NUMC);
        data.add(element);
        
        item = new DocumentModelItem();
        item.setName("TIPO_PESSOA");
        item.setTableFieldName("TPPES");
        item.setDataElement(element);
        
        partner.add(item);
        
        typecode = partnertype.getModelItem("CODIGO");
        
        item = new DocumentModelItem();
        item.setName("TIPO_PARCEIRO");
        item.setTableFieldName("TPPAR");
        item.setDataElement(typecode.getDataElement());
        item.setReference(typecode);
        item.setSearchHelp("SH_PARTNER_TYPE");
        
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
        data.add(element);
        
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
        data.add(element);
        
        item = new DocumentModelItem();
        item.setName("LOGRADOURO");
        item.setTableFieldName("LOGRA");
        item.setDataElement(element);
        
        address.add(item);
        
        element = new DataElement();
        element.setName("CUSTOM_PARTNER_ADDRESS.CEP");
        element.setLength(8);
        element.setType(DataType.NUMC);
        data.add(element);
        
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
        data.add(element);
        
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
        data.add(element);
        
        item = new DocumentModelItem();
        item.setName("TELEFONE");
        item.setTableFieldName("TEL01");
        item.setDataElement(element);
        
        address.add(item);
        
        element = new DataElement();
        element.setName("CUSTOM_PARTNER_ADDRESS.EMAIL");
        element.setLength(80);
        element.setType(DataType.CHAR);
        element.setUpcase(false);
        data.add(element);
        
        item = new DocumentModelItem();
        item.setName("EMAIL");
        item.setTableFieldName("EMAIL");
        item.setDataElement(element);
        
        address.add(item);
        
        element = new DataElement();
        element.setName("CUSTOM_PARTNER_ADDRESS.WEB_PAGE");
        element.setLength(80);
        element.setType(DataType.CHAR);
        element.setUpcase(false);
        data.add(element);
        
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
     * @param address
     * @param communication
     * @return
     */
    private static final DocumentModel installPartnerContact(InstallData data,
            DocumentModel address, DocumentModel communication) {
        DataElement element;
        DocumentModelItem item, addresscode;
        DocumentModel contact = data.getModel("CUSTOM_PARTNER_CONTACT",
                "CPRTNRCNTCT", "");
        
        element = new DataElement();
        element.setName("CUSTOM_PARTNER_CONTACT.CODIGO");
        element.setLength(12);
        element.setType(DataType.NUMC);
        data.add(element);
        
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
        data.add(element);
        
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
        
        return contact;
    }
    
    /**
     * 
     * @param data
     * @param address
     * @param communication
     */
    private static final void installContactCommunication(InstallData data,
            DocumentModel partner, DocumentModel contact, DocumentModel address,
            DocumentModel communication) {
        DataElement element;
        DocumentModelItem item, modelitem;
        DocumentModel model = data.getModel("CUSTOM_PARTNER_COMM",
                "CPARTNERCOMM", null);
        
        /*
         * comunição do contato
         */
        element = new DataElement();
        element.setName("CUSTOM_PARTNER_COMM.CODIGO");
        element.setLength(12);
        element.setType(DataType.NUMC);
        
        item = new DocumentModelItem();
        item.setName("CODIGO");
        item.setTableFieldName("IDENT");
        item.setDataElement(element);
        
        model.add(item);
        model.add(new DocumentModelKey(item));
        
        /*
         * parceiro
         */
        modelitem = partner.getModelItem("CODIGO");
        element = modelitem.getDataElement();
        
        item = new DocumentModelItem();
        item.setName("PARTNER_ID");
        item.setTableFieldName("PRTNR");
        item.setDataElement(element);
        item.setReference(modelitem);
        
        model.add(item);
        
        /*
         * contato
         */
        modelitem = contact.getModelItem("CODIGO");
        element = modelitem.getDataElement();
        
        item = new DocumentModelItem();
        item.setName("CONTACT_ID");
        item.setTableFieldName("CNTCT");
        item.setDataElement(element);
        item.setReference(modelitem);
        
        model.add(item);
        
        /*
         * tipo de comunicação
         */
        modelitem = communication.getModelItem("CODIGO");
        element = modelitem.getDataElement();
        
        item = new DocumentModelItem();
        item.setName("TP_COMMUNIC");
        item.setTableFieldName("TPCOM");
        item.setDataElement(element);
        item.setReference(modelitem);
        item.setSearchHelp("SH_COMMUNICATION");
        
        model.add(item);
        
        /*
         * descrição da comunicação
         */
        element = address.getModelItem("WEB_PAGE").getDataElement();
        
        item = new DocumentModelItem();
        item.setName("COMMUNICATION");
        item.setTableFieldName("COMMU");
        item.setDataElement(element);
        
        model.add(item);
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
        data.add(element);
        
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
        data.add(element);
        
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
        data.add(element);
        
        item = new DocumentModelItem();
        item.setName("DESCRICAO");
        item.setDataElement(element);
        item.setTableFieldName("DESCR");
        
        partnertype.add(item);
        
        data.addValues(partnertype, 0, "CL", "CLIENTE");
        data.addValues(partnertype, 1, "FR", "FORNECEDOR");
        data.addValues(partnertype, 2, "TR", "TRANSPORTADOR");
        
        return partnertype;
    }
    
    /**
     * 
     * @return
     */
    public static final InstallData self() {
        Map<String, String> messages;
        SearchHelpData shdata;
        Authorization authorization;
        InstallData data = new InstallData();
        DocumentModel address, partner, partnertype, addresstype, contact,
            communication;
        
        partnertype = installPartnerType(data);
        partner = installPartner(data, partnertype);
        addresstype = installAddressType(data);
        address = installPartnerAddress(data, partner, addresstype);
        communication = installCommunication(data);
        contact = installPartnerContact(data, address, communication);
        installContactCommunication(data, partner, contact, address,
                communication);
        
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
        data.link("XD01", "erp-custom-sd.partner");

        messages = new HashMap<String, String>();
        messages.put("partner", "Parceiro");
        messages.put("show", "Exibir");
        messages.put("create", "Criar");
        messages.put("update", "Atualizar");
        messages.put("partner-create", "Criar parceiro");
        messages.put("partner-display", "Exibir parceiro");
        messages.put("partner-update", "Atualizar parceiro");
        messages.put("identitytab", "Identificação");
        messages.put("addresstab", "Endereços");
        messages.put("contacttab", "Contatos");
        messages.put("save", "Salvar");
        messages.put("CODIGO", "Código");
        messages.put("DESCRICAO", "Descrição");
        messages.put("RAZAO_SOCIAL", "Razão social");
        messages.put("NOME_FANTASIA", "Nome fantasia");
        messages.put("DOCUMENTO_FISCAL", "Documento fiscal");
        messages.put("INSCR_ESTADUAL", "Inscrição estadual");
        messages.put("INSCR_MUNICIPAL", "Inscrição municipal");
        messages.put("TIPO_PESSOA", "Tipo de pessoa");
        messages.put("TIPO_PARCEIRO", "Tipo de parceiro");
        messages.put("TIPO_ENDERECO", "Tipo de endereço");
        messages.put("LOGRADOURO", "Logradouro");
        messages.put("CEP", "CEP");
        messages.put("BAIRRO", "Bairro");
        messages.put("CIDADE","Cidade");
        messages.put("TELEFONE", "Telefone");
        messages.put("EMAIL", "e-mail");
        messages.put("WEB_PAGE", "Página web");
        messages.put("addaddress", "Adicionar");
        messages.put("editaddress", "Editar");
        messages.put("removeaddress", "Remover");
        messages.put("PNOME", "Nome");
        messages.put("UNOME", "Sobrenome");
        messages.put("ADDRESS", "Endereço");
        messages.put("COMMUNICATION", "Comunicação");
        messages.put("addcontact", "Adicionar contato");
        messages.put("editcontact", "Editar contato");
        messages.put("removecontact", "Remover contato");
        messages.put("address.required", "Endereço obrigatório.");
        messages.put("partner.saved.successfuly",
                "Parceiro gravado com sucesso.");
        messages.put("partner-selection", "Selecionar parceiro");
        messages.put("invalid.partner", "Parceiro não encontrado.");
        messages.put("communicscnt", "Comunicação");
        messages.put("addcommunic", "Adicionar comunicação");
        messages.put("removecommunic", "Remover comunicação");
        messages.put("validate", "Validar");
        messages.put("TP_COMMUNIC", "Tipo comunic.");
        messages.put("XD01", "Cadastro de parceiros");
        data.setMessages("pt_BR", messages);
        
        authorization = new Authorization("PARTNER.EXECUTE");
        authorization.setAction("EXECUTE");
        authorization.setObject("APPLICATION");
        authorization.add("APPNAME", "erp-custom-sd.partner");
        data.add(authorization);
        
        data.addTaskGroup("ERP", "XD01");
        
        return data;
    }
}
