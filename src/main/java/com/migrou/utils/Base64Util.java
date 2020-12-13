package com.migrou.utils;

import java.util.Base64;

public class Base64Util {

	public static String geraEncodeBase64(String texto) {
		return Base64.getEncoder().withoutPadding().encodeToString(texto.getBytes());
	}

	public static String geraDencodeBase64(String encoded) {
		return new String(Base64.getDecoder().decode(encoded));
	}
	
	public static String geraEncodeBase64Autenticacao(String fluxo, String usuario, String senha) {
    	
    	String fluxoUsuarioSenha = fluxo+"/"+usuario+":"+senha;
    	
    	return Base64.getEncoder().withoutPadding().encodeToString(fluxoUsuarioSenha.getBytes());
    }

	public static String geraEncodeBase64AlteraSenha(String fluxo, String usuario, String senhaAtual, String senhaNova) {
    	
    	String fluxoUsuarioSenhaAtualsenhaNova = fluxo+"/"+usuario+":"+senhaAtual+"/"+senhaNova;
    	
    	return Base64.getEncoder().withoutPadding().encodeToString(fluxoUsuarioSenhaAtualsenhaNova.getBytes());
    }

}
