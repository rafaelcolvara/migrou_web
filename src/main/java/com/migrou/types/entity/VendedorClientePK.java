package com.migrou.types.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import java.io.Serializable;
import java.util.UUID;

@JsonIgnoreProperties
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of = {"idCliente","idVendedor"})
public class VendedorClientePK implements Serializable {


    private UUID idCliente;

    private UUID idVendedor;
}
