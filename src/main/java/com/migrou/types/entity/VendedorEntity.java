package com.migrou.types.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name ="VENDEDOR_TESTE")
@EqualsAndHashCode
public class VendedorTesteEntity implements Serializable {


	private static final long serialVersionUID = 1739768953447220347L;

	@Id
	@Column(name = "vendedor_id")
	private String username;

	@OneToOne
	@MapsId
	@JoinColumn(name = "vendedor_id")
	private Usuario usuario;

	@Column(name= "NOME_NEGOCIO")
	private String nomeNegocio;
	
	@Column(name = "SEGMENTO")
	private String nomeSegmento;

	@Column(name = "NOME", nullable = false)
	private String nome;

	@Column(name = "EMAIL", unique = true, nullable = false)
	private String email;

	@Column(name = "SENHA", nullable = false)
	private String senha;

	@Column(name = "CPFCNPJ", length = 14, scale = 0)
	private Long cpfCnpj;

	@Column(name = "DT_CADASTRO", nullable = false)
	private Date dtCadastro;

	@Column(name= "DT_NASCIMENTO")
	private Date dtNascimento;

	@Column(name = "URLFOTO")
	private String urlFoto;

	@Column(name = "EMAIL_VALIDO", columnDefinition = "boolean default false" )
	private Boolean flgEmailValido;

	@Column(name = "NR_CELULAR")
	private String nrCelular;
	
	@OneToMany(
	        mappedBy = "vendedor",
	        cascade = CascadeType.ALL,
	        fetch = FetchType.LAZY
	)
	private List<ContaCorrenteEntity> contaCorrenteVendedor ;
}
