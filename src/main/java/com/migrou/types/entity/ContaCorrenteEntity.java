package com.migrou.types.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Table(name = "CONTA_CORRENTE")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@JsonIgnoreProperties
@IdClass(ClienteVendedorPK.class)
public class ContaCorrenteEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	
	@Id
	private String idCliente;
	
	@Id
	private String idVendedor;

	@Column(name = "DT_LANCAMENTO", nullable = false)
	private Date dataLancamento;

	@Column(name = "VL_VENDA", nullable = false, precision = 10, scale = 2)
	private BigDecimal valorLancamento;

	@Column(name = "VL_CASHBACK", precision = 10, scale = 2)
	private BigDecimal valorCashBack;

	@Column(name = "DT_VENCIMENTO_SAQUE")
	private Date dataVencimentoSaque;

	@Column(name = "DT_SAQUE_CASH_BACK")
	private LocalDateTime dataPgCashBack;

	@Column(name = "FLG_RESGATADO", columnDefinition = "boolean default false")
	private boolean flgResgatado;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idCliente", updatable = false, insertable = false)
	private ClienteEntity cliente;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idVendedor", updatable = false, insertable = false)
	private VendedorEntity vendedor;

}
