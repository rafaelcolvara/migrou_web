package com.migrou.types.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class CampanhaDTO implements Serializable {


    /**
     *
     */
    private static final long serialVersionUID = -376725679484482426L;

    private String nomeCampanha;

    private boolean flgPercentualSobreCompras;

    private BigDecimal prcTotalLancamentosPercentualSobreCompras;

    private Integer qtLancamentosPercentualSobreCompras;

    private boolean flgValorFixo;

    private BigDecimal vlrTotalComprasValorFixo;

    private BigDecimal vlrPremioValorFixo;


}
