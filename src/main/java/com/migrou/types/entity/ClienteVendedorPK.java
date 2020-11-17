package com.migrou.types.entity;

import java.io.Serializable;
import java.util.UUID;

import javax.annotation.Generated;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

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
@EqualsAndHashCode(of = {"IdCliente","idVendedor"})
public class ClienteVendedorPK implements Serializable{
	
	private static final long serialVersionUID = 7141517937743379996L;

	private Integer id;
	
	private UUID idCliente;
	
	private UUID idVendedor;
}
