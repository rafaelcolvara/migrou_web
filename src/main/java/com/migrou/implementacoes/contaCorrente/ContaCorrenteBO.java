package com.migrou.implementacoes.contaCorrente;

import java.math.BigDecimal;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.migrou.implementacoes.pessoas.bo.ClienteBO;
import com.migrou.implementacoes.pessoas.bo.VendedorBO;
import com.migrou.types.dto.ContaCorrenteDTO;
import com.migrou.types.entity.ContaCorrenteEntity;

@Component
public class ContaCorrenteBO {

	@Autowired
	ClienteBO clienteBO;
	
	@Autowired
	VendedorBO vendedorBO;
	
	
	public ContaCorrenteDTO parsePojoToDTO(ContaCorrenteEntity entity) {
	
		ContaCorrenteDTO dto = new ContaCorrenteDTO();
		dto.setCliente(clienteBO.parsePojoToDTO(entity.getCliente()));
		dto.setVendedor(vendedorBO.parsePojoToDTO(entity.getVendedor()));
		dto.setDtLancamento(new Date());
		dto.setValorLancamento(entity.getValorLancamento());
		dto.setValorCashBack(this.getValorCashBack(entity));
		
		return dto;		
	}
	
	public ContaCorrenteEntity parseDTOtoPojo(ContaCorrenteDTO dto) {
		ContaCorrenteEntity cc = new ContaCorrenteEntity();
		cc.setCliente(clienteBO.parseDTOtoPojo(dto.getCliente()));
		cc.setVendedor(vendedorBO.parseDTOtoPojo(dto.getVendedor()));
		cc.setIdCliente(dto.getCliente().getIdCliente());
		cc.setIdVendedor(dto.getVendedor().getIdVendedor());
		cc.setDataLancamento(dto.getDtLancamento());
		cc.setValorLancamento(dto.getValorLancamento());
		cc.setValorCashBack(dto.getValorCashBack());		
		return cc;
	}
	
	
	private BigDecimal getValorCashBack(ContaCorrenteEntity cc) {
		
		return new BigDecimal(0);
		
	}
}
