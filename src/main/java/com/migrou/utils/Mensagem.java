package com.migrou.utils;

public enum Mensagem {
	
	SUCESSO("SUCESSO"),
	SUCESSO_ATUALIZACAO("Atualizado com sucesso. "),
	SUCESSO_REMOVIDO("Removido com sucesso. "),
	SUCESSO_CRIACAO("Criado com sucesso. "),
	ERRO_INTERNO("Erro Interno. "),
	DADO_IDENTIFICADOR_OBRIGATORIO("O Identificador é obrigatório "),
	DADO_NAO_ENCONTRADO("Dado não encontrado "),
	DADO_OBRIGATORIO("O campo é obrigatório "),
	DADO_INVALIDO("Dado informado não é válido. "),
	DADO_ID_EXISTENTE("Já existe um registro com o identificador informado. "),
	QUANTIDADE_REG_MAX_PAGINACAO_ULTRAPASSADA("Quantidade máxima de registro para paginação ultrapassada. "),
	BASIC_AUTH_INVALID("Autenticação Basic Auth esta nula ou invalida."),
	BANDEIRA_INVALIDA("Bandeira inválida, segue exemplos: DROGARAIA, DROGASIL, FARMASIL"),
	TIPO_LOGIN_INVALIDO("TipoLogin inválido, segue exemplos: CNPJ_CPF, EMAIL"),
	USUARIO_OU_SENHA_INVALIDA("Usuário ou Senha inválida"),
	PADRAO_INVALIDO("Padrão invalido, segue exemplos: LDAP/Seu Usuário, APPL/SeuUsuário, OMNI/Seu Usuario"),
	USUARIO_OBRIGATORIO("Usuário é obrigatório"),
	SENHA_OBRIGATORIA("Senha é obrigatória"),
	TOKEN_EXPIRADO_INVALIDO("Token expirado ou inválido."),
	TOKEN_VALIDO("Token válido"),
	SENHA_ALTERADA("Senha alterada com sucesso"),
	OBJETO_INVALIDO("Objeto informado no corpo esta inválido"),
	ERRO_GERAR_SENHA("Erro ao gerar senha."),
	ERRO_ALTERAR_SENHA("Erro ao tentar alterar a senha."),
	FORMATO_SENHA_INVALIDO("O formato da senha não esta válido."),
	ERRO_CRIPTOGRAFAR_SENHA("Erro ao criptografar senha. "),
	USUARIO_NAO_LOCALIZADO("Usuário não localizado em nossa base"),
	BODY_INVALIDO("Body não informado ou invalido"),
	FLUXO_INEXISTENTE("O fluxo informado não existe ou não foi implementado"),
	DATA_FORMATO_INVALIDO("Data com formato invalido. ");
	
	private String value;
	
	Mensagem(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

}
