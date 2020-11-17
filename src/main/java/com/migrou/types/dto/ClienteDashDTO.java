package com.migrou.types.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(value = JsonInclude.Include.NON_NULL)
public class ClienteDashDTO {

    ClienteDTO clienteDTO;

    VendedorDTO vendedorDTO;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy", timezone = "GMT-03")
    Date dtUltimaCompra;

    BigDecimal vlrComprasRealizadas;

    Integer qtdComprasRealizadas;

    BigDecimal vlrCashBack;

    CampanhaDTO campanhaDTO;
    
}
