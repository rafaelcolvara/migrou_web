package com.migrou.types.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

@Entity
@JsonIgnoreProperties
@Table(name = "CAMPANHAS")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of = {"idCampanha"})
public class CampanhaEntity implements Serializable {

    private static final long serialVersionUID = -7849138440252331597L;

    @Id
    @Column(name = "ID_CAMPANHA")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer idCampanha;

    @Column(name = "NOME")
    private String nome;

    @Column(name = "FLG_PERC_SOBRE_COMPRAS")
    private boolean flgPercentualSobreCompras;

    @Column(name = "PERC_LANC_SOBRE_COMPRAS")
    private BigDecimal prcTotalLancamentosPercentualSobreCompras;

    @Column(name = "QT_LANC_SOBRE_COMPRAS")
    private Integer qtLancamentosPercentualSobreCompras;

    @Column(name = "FLG_VLR_FIXO")
    private boolean flgValorFixo;

    @Column(name = "VLR_TOTAL_COMPRAS_VLR_FIXO")
    private BigDecimal vlrTotalComprasValorFixo;

    @Column(name = "VLR_PREMIO_COMPRAS_VLR_FIXO")
    private BigDecimal vlrPremioValorFixo;

    @OneToMany(mappedBy = "campanha", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<ClienteEntity> pessoasCampanhas;



}
