package com.migrou.types.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@JsonIgnoreProperties
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "VENDEDOR")
@EqualsAndHashCode
@PrimaryKeyJoinColumn()
public class VendedorEntity extends PessoaEntity implements Serializable {

	private static final long serialVersionUID = -3281305417575394484L;

	@Column(name= "NOME_NEGOCIO")
	private String nomeNegocio;
	
	@Column(name = "SEGMENTO")
	private String nomeSegmento;
	
	@OneToMany(
	        mappedBy = "vendedor",
	        cascade = CascadeType.ALL,
	        fetch = FetchType.LAZY
	    )
	private List<ContaCorrenteEntity> contaCorrenteVendedor ;
}
