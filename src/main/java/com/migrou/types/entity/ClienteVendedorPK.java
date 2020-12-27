package com.migrou.types.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import java.io.Serializable;


@JsonIgnoreProperties
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of = {"IdCliente","idVendedor"})
public class ClienteVendedorPK implements Serializable{
	
	private static final long serialVersionUID = 7141517937743379996L;

	private Integer id;
	
	private String idCliente;
	
	private String idVendedor;
}
