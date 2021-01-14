package com.migrou.types.entity;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;
import java.util.List;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name ="VENDEDOR")
@EqualsAndHashCode(of = {"username"})
public class VendedorEntity implements Serializable {


	private static final long serialVersionUID = 1739768953447220347L;

	@Id
	@Column(name = "vendedor_id")
	private String username;

	@OneToOne
	@PrimaryKeyJoinColumn
	private Usuario usuario;

	@Column(name= "NOME_NEGOCIO")
	@NotNull
	private String nomeNegocio;
	
	@Column(name = "SEGMENTO")
	@NotNull
	private String nomeSegmento;

	@Column(name = "NOME", nullable = false)
	@NotNull
	private String nome;

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
	@NotNull
	private String nrCelular;
	
	@OneToMany(
	        mappedBy = "vendedor",
	        cascade = CascadeType.ALL,
	        fetch = FetchType.LAZY
	)
	private List<ContaCorrenteEntity> contaCorrenteVendedor ;
}
