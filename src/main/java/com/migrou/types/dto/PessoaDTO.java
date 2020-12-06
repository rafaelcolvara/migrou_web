package com.migrou.types.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.*;

import javax.validation.constraints.Size;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(value = JsonInclude.Include.NON_NULL)
public class PessoaDTO implements Serializable {

    /**
     * não incluir campos novos pois impacta no login
     */

    private UUID id;
    
    private String nome;
    
    private String email;

    private String senha;

    @Size(max = 14)
    private Long cpfCnpj;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy", timezone = "GMT-03")
    private Date dataCadastro;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy", timezone = "GMT-03")
    private Date dataNascimento;

    private String nrCelular;

    private boolean flgEmailValido;

    private String base64Foto;

    private String tipoPessoa;

    private String segmentoComercial;

    private String nomeNegocio;

}
