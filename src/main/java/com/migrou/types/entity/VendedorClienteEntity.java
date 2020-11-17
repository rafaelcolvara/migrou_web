package com.migrou.types.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Null;
import java.util.UUID;

@Entity
@Table(name = "VENDEDOR_CLIENTE")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@JsonIgnoreProperties
@IdClass(VendedorClientePK.class)
public class VendedorClienteEntity {

    @Id
    private UUID idCliente;

    @Id
    private UUID idVendedor;


}
