package com.migrou.types.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

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
@Table(name ="CLIENTE")
@EqualsAndHashCode
@PrimaryKeyJoinColumn()
public class ClienteEntity extends PessoaEntity implements Serializable{

	private static final long serialVersionUID = -36109617597033538L;

	@OneToMany(
	        mappedBy = "cliente",
	        cascade = CascadeType.ALL,
	        orphanRemoval = true
	    )
	private List<ContaCorrenteEntity> contaCorrenteCliente;
	
	@ManyToOne
    @JoinColumn(name = "campanha_id")
    private CampanhaEntity campanha;
    
}
