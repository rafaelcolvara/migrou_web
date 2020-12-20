package com.migrou.types.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(value = JsonInclude.Include.NON_NULL)
@Builder
public class UltimoResgateDTO implements Serializable {

    String usernameCliente;
    String nomeCliente;
    String idVendedor;
    LocalDateTime DataUltimoResgate;
    BigDecimal vlrUltimoResgate;


}
