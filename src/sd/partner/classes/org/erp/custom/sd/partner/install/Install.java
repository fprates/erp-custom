package org.erp.custom.sd.partner.install;

import java.util.HashMap;
import java.util.Map;

import org.iocaste.packagetool.common.InstallData;
import org.iocaste.packagetool.common.TaskGroup;
import org.iocaste.protocol.user.Authorization;
import org.iocaste.protocol.user.UserProfile;

public class Install {
    
    /**
     * 
     * @return
     */
    public static final InstallData init() {
        UserProfile profile;
    	TaskGroup taskgroup;
        Map<String, String> messages;
        Authorization authorization;
        InstallContext context = new InstallContext();

        MasterData.install(context);
        PartnerType.install(context);
        Partner.install(context);
        AddressType.install(context);
        PartnerAddress.install(context);
        Communication.install(context);
        PartnerContact.install(context);
        ContactCommunication.install(context);
        
        context.data.addNumberFactory("CUSTPARTNER");
        context.data.link("XD01", "erp-custom-sd.partner");
        context.data.link("COUNTRY-CONFIG", "iocaste-dataeditor "
                + "model=COUNTRIES action=edit");
        context.data.link("REGION-CONFIG", "iocaste-dataeditor "
                + "model=REGION action=edit");
        messages = new HashMap<>();
        messages.put("addaddress", "Adicionar");
        messages.put("addcommunic", "Adicionar comunicação");
        messages.put("addcontact", "Adicionar contato");
        messages.put("ADDRESS", "Endereço");
        messages.put("address.required", "Endereço obrigatório.");
        messages.put("addresstab", "Endereços");
        messages.put("BAIRRO", "Bairro");
        messages.put("CEP", "CEP");
        messages.put("CIDADE","Cidade");
        messages.put("CODIGO", "Código");
        messages.put("COMMUNICATION", "Comunicação");
        messages.put("communicscnt", "Comunicação");
        messages.put("contacttab", "Contatos");
        messages.put("COUNTRY-CONFIG", "Países");
        messages.put("create", "Criar");
        messages.put("DESCRICAO", "Descrição");
        messages.put("DOCUMENTO_FISCAL", "Documento fiscal");
        messages.put("editaddress", "Editar");
        messages.put("editcontact", "Editar contato");
        messages.put("EMAIL", "e-mail");
        messages.put("field.is.required ",
                "Preenchimento do campo é obrigatório.");
        messages.put("identitytab", "Identificação");
        messages.put("INSCR_ESTADUAL", "Inscrição estadual");
        messages.put("INSCR_MUNICIPAL", "Inscrição municipal");
        messages.put("invalid.partner", "Parceiro não encontrado.");
        messages.put("LOGRADOURO", "Logradouro");
        messages.put("NOME_FANTASIA", "Nome fantasia");
        messages.put("partner", "Parceiro");
        messages.put("partner.saved.successfuly",
                "Parceiro gravado com sucesso.");
        messages.put("partner-create", "Criar parceiro");
        messages.put("partner-display", "Exibir parceiro");
        messages.put("partner-selection", "Selecionar parceiro");
        messages.put("partner-update", "Atualizar parceiro");
        messages.put("PNOME", "Nome");
        messages.put("RAZAO_SOCIAL", "Razão social");
        messages.put("record.is.locked",
                "Registro bloqueado para atualização por outro usuário.");
        messages.put("REGION", "Estado");
        messages.put("REGION-CONFIG", "Regiões/Estados");
        messages.put("removeaddress", "Remover");
        messages.put("removecommunic", "Remover comunicação");
        messages.put("removecontact", "Remover contato");
        messages.put("save", "Salvar");
        messages.put("show", "Exibir");
        messages.put("TELEFONE", "Telefone");
        messages.put("TIPO_ENDERECO", "Tipo de endereço");
        messages.put("TIPO_PARCEIRO", "Tipo de parceiro");
        messages.put("TIPO_PESSOA", "Tipo de pessoa");
        messages.put("TP_COMMUNIC", "Tipo comunic.");
        messages.put("UNOME", "Sobrenome");
        messages.put("update", "Atualizar");
        messages.put("validate", "Validar");
        messages.put("WEB_PAGE", "Página web");
        messages.put("XD01", "Cadastro de parceiros");
        context.data.setMessages("pt_BR", messages);
        
        authorization = new Authorization("PARTNER.EXECUTE");
        authorization.setAction("EXECUTE");
        authorization.setObject("APPLICATION");
        authorization.add("APPNAME", "erp-custom-sd.partner");
        context.data.add(authorization);
        
        profile = new UserProfile("SALES");
        profile.add(authorization);
        context.data.add(profile);
        
        taskgroup = new TaskGroup("ERP");
        taskgroup.add("XD01");
        taskgroup.add("COUNTRY-CONFIG");
        taskgroup.add("REGION-CONFIG");
        context.data.add(taskgroup);
        
        return context.data;
    }
}
