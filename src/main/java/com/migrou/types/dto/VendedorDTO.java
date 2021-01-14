package com.migrou.types.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.UUID;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(value = JsonInclude.Include.NON_NULL)
public class VendedorDTO {

	private String username;

	@NotNull
	private String nomeNegocio;

	@NotNull
	private String nomeSegmento;

	@NotNull
	private String segmentoComercial;

	@NotNull
	private String nome;

	@Size(max = 14)
	private Long cpfCnpj;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy", timezone = "GMT-03")
	private Date dataCadastro;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy", timezone = "GMT-03")
	private Date dataNascimento;

	@NotNull
	private String nrCelular;

	private boolean flgEmailValido;

	private String urlFoto;

	private String tipoPessoa;

	private String token;

	private UsuarioDTO usuarioDTO;

}
