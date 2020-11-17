package com.migrou.types.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Size;

@Entity
@JsonIgnoreProperties
@Table(name = "PARAMETROS")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of = {"idParametro"})
public class ParametrosEntity {

    @Id
    @Column(name = "ID_PARAMETRO")
    private int idParametro;

    @Column(name = "NOME_PARAMETRO")
    @Size( max = 30)
    private String nomeParametro;

    @Column(name = "VALOR_PARAMETRO")
    @Size( max = 30)
    private String valorParametro;



    @Column(name = "DESCRICAO")
    @Size(max = 100)
    private String descricaoParametro;

}
