package com.migrou.implementacoes.contaCorrente;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

import javax.transaction.Transactional;

import com.migrou.implementacoes.campanha.CampanhaBO;
import com.migrou.interfaces.campanha.CampanhaJPARepository;
import com.migrou.interfaces.cliente.ClienteJPARepository;
import com.migrou.interfaces.vendedor.VendedorJPARepository;
import com.migrou.types.dto.ClienteDashDTO;
import com.migrou.types.entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.migrou.implementacoes.pessoas.bo.ClienteBO;
import com.migrou.implementacoes.pessoas.bo.VendedorBO;
import com.migrou.interfaces.contaCorrente.ContaCorrenteJPARepository;
import com.migrou.interfaces.contaCorrente.ContaCorrenteInterface;
import com.migrou.types.dto.ContaCorrenteDTO;

@Service("ContaCorrenteService")
public class ContaCorrenteImpl implements ContaCorrenteInterface {

	@Autowired
	CampanhaJPARepository campanhaJPARepository;

	@Autowired
	CampanhaBO campanhaBO;

	@Autowired
	ContaCorrenteJPARepository contaCorrenteJPA;

	@Autowired
	VendedorJPARepository vendedorJPARepository;

	@Autowired
	ClienteJPARepository clienteJPARepository;

	@Autowired
	ClienteBO clienteBO;

	@Autowired
	VendedorBO vendedorBO;
	
	@Autowired
	ContaCorrenteBO contaCorrenteBO;



	@Override
	@Transactional
	public ContaCorrenteDTO realizaLancamento(ContaCorrenteDTO contacorrente) throws Exception {

		ClienteEntity clienteEntity = new ClienteEntity();
		VendedorEntity vendedorEntity = new VendedorEntity();

		/*validações*/
		if ( Objects.isNull(contacorrente.getCliente()) || Objects.isNull(contacorrente.getVendedor())) {
			throw new Exception("Informe o vendedor ou cliente");
		}

		if (Objects.isNull(contacorrente.getCliente().getIdCliente()) || Objects.isNull(contacorrente.getVendedor().getIdVendedor()))
			throw new Exception("informe o codigo do vendedor ou cliente");

		if (Objects.isNull(contacorrente.getValorLancamento()))
			throw new Exception("Informa o valor de lançamento");

		if (Objects.isNull(contacorrente.getDtLancamento()))
			contacorrente.setDtLancamento(new Date());

		validaLancamento(contacorrente.getCliente().getIdCliente(), contacorrente.getVendedor().getIdVendedor());

		vendedorEntity = vendedorJPARepository.findById(contacorrente.getVendedor().getIdVendedor()).orElseThrow(() -> new Exception("Não encontrado"));
		clienteEntity = clienteJPARepository.findByIdPessoa(contacorrente.getCliente().getIdCliente());

		if (Objects.isNull(clienteEntity.getCampanha()))
			throw new Exception("Atribua uma campanha ao cliente antes de realizar lançamentos");

		contacorrente.setCliente(clienteBO.parsePojoToDTO(clienteEntity));
		contacorrente.setVendedor(vendedorBO.parsePojoToDTO(vendedorEntity));

		contacorrente = geraValorCashBack(contacorrente);

		ContaCorrenteEntity ccEntity;

		ccEntity =  contaCorrenteBO.parseDTOtoPojo(contacorrente);
		ccEntity = contaCorrenteJPA.save(ccEntity);

		return contaCorrenteBO.parsePojoToDTO(ccEntity);
	}

	@Override
	public ContaCorrenteDTO realizaResgate(UUID idCliente, UUID idVendedor) throws Exception {


	    ContaCorrenteEntity lancamento = this.buscaLancamentoElegivelParaResgate(idCliente, idVendedor);
		if (Objects.isNull(lancamento)) throw new Exception("Não existe nenhum lançamento elegivel para resgate para este cliente") ;
		List<ContaCorrenteEntity> lntos = this.buscaLancamentosNaoResgatados(idCliente, idVendedor);
		lntos.forEach(x -> {
			x.setDataPgCashBack(LocalDateTime.now());
			x.setFlgResgatado(true);
			contaCorrenteJPA.save(x);
		});
		return contaCorrenteBO.parsePojoToDTO(lancamento);
	}

	@Override
	public ContaCorrenteDTO geraValorCashBack(ContaCorrenteDTO contaCorrenteDTO) throws Exception {

		UUID idCliente = contaCorrenteDTO.getCliente().getIdCliente();
		UUID idVendedor = contaCorrenteDTO.getVendedor().getIdVendedor();
		List<ContaCorrenteEntity> lancamentos = this.buscaLancamentosNaoResgatados(idCliente, idVendedor);
		CampanhaEntity campanhaEntity = campanhaJPARepository.findById(contaCorrenteDTO.getCliente().getIdCampanha()).orElseThrow(() -> new Exception("Cliente sem campanha"));
		if (campanhaEntity.isFlgPercentualSobreCompras()){
			// verificar se já nao tem cashback para sacar
			if (lancamentos.stream().count()+1 > campanhaEntity.getQtLancamentosPercentualSobreCompras())
				throw new Exception("Existe lancamentos pendentes para cashback, de acordo com a regra para este cliente");
			else {
				contaCorrenteDTO.setValorCashBack(campanhaEntity.getPrcTotalLancamentosPercentualSobreCompras().multiply(contaCorrenteDTO.getValorLancamento()).divide(new BigDecimal(100)));
			}
		}
		BigDecimal vlrTotalJaRealizadas = new BigDecimal(0);

		if (campanhaEntity.isFlgValorFixo()){

			BigDecimal vlrTotal = lancamentos.stream()
					.map(x -> x.getValorLancamento())
					.reduce(BigDecimal.ZERO, BigDecimal::add );


			System.out.println(vlrTotalJaRealizadas);
			vlrTotalJaRealizadas = vlrTotalJaRealizadas.add(vlrTotal).add(contaCorrenteDTO.getValorLancamento());
			System.out.println(vlrTotalJaRealizadas);

			//  1  : vlrTotalJaRealizadas > campanha
			// -1  : vlrTotalJaRealizadas < campanha
			if (vlrTotalJaRealizadas.compareTo(campanhaEntity.getVlrTotalComprasValorFixo())>=0){
				contaCorrenteDTO.setValorCashBack(campanhaEntity.getVlrPremioValorFixo());
			}
		}

		return contaCorrenteDTO;
	}

	@Override
	public List<ContaCorrenteEntity> buscaLancamentosNaoResgatados(UUID idCliente, UUID idVendedor) {
		List<ContaCorrenteEntity> lancamentos = contaCorrenteJPA.findAllByClienteIdPessoaAndVendedorIdPessoaAndFlgResgatadoFalse(idCliente, idVendedor);
		return lancamentos;
	}

	public ContaCorrenteEntity buscaLancamentoElegivelParaResgate(UUID idCliente, UUID idVendedor) {
		ContaCorrenteEntity lancamento = buscaCashBack(idCliente, idVendedor);
		return lancamento;
	}

	@Override
	public ClienteDashDTO buscaDashCliente(UUID idCliente, UUID idVendedor) throws Exception {
        SimpleDateFormat frmdata = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
        Date dataUltCompra;
        try {
            dataUltCompra = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss").parse(frmdata.format(new Date()));
        } catch (Exception e) {
            dataUltCompra = null;
        }

		ClienteDashDTO retorno = new ClienteDashDTO();
		ClienteEntity clienteEntity = clienteJPARepository.findByIdPessoa(idCliente);
		CampanhaEntity campanhaEntity = campanhaJPARepository.findByIdCampanha(clienteEntity.getCampanha().getIdCampanha());
		VendedorEntity vendedorEntity = vendedorJPARepository.findByIdPessoa(idVendedor).orElseThrow(()-> new Exception("Vendedor não encontrado"));
		List<ContaCorrenteEntity> lancamentos =  buscaLancamentosNaoResgatados(idCliente,idVendedor);
		BigDecimal vlrGastosCliente = lancamentos.stream().map(x -> x.getValorLancamento()).reduce(BigDecimal.ZERO, BigDecimal::add);

		retorno.setVlrComprasRealizadas(vlrGastosCliente);
		retorno.setQtdComprasRealizadas( lancamentos.size());
		retorno.setVendedorDTO(vendedorBO.parsePojoToDTO(vendedorEntity));
		retorno.setCampanhaDTO(campanhaBO.parsePojoToDTO(campanhaEntity));
        retorno.setClienteDTO(clienteBO.parsePojoToDTO(clienteEntity));
		retorno.setDtUltimaCompra(dataUltCompra);
		retorno.setVlrCashBack(lancamentos.stream().map(x->x.getValorCashBack()).filter(Objects::nonNull).reduce(BigDecimal.ZERO, BigDecimal::add));

		return retorno;
	}

	@Override
	public void validaLancamento(UUID idCliente, UUID idVendedor) throws Exception {

		ClienteEntity clienteEntity = clienteJPARepository.findByIdPessoa(idCliente);
		List<ContaCorrenteEntity> lancamentos =  buscaLancamentosNaoResgatados(idCliente,idVendedor);
		if (clienteEntity.getCampanha().isFlgPercentualSobreCompras()){
			// Percentual sobre QUANTIDADE DE COMPRAS
			// Regra verifica se quantidade de compras realizadas já nao excedeu a quantidada da regra do cliente
			if (lancamentos.stream().count() + 1 > clienteEntity.getCampanha().getQtLancamentosPercentualSobreCompras())
				throw new Exception("Realize o saque do CashBack antes de realizar novos lançamentos.");

		}
		if (clienteEntity.getCampanha().isFlgValorFixo()){
			// Cliente atinge um VALOR PRE FIXADO de gastos e ganha direto ao cashback
			// O calculo de elegibilidade é realizado no momento do lançamento (vlrcashBack)
			BigDecimal vlrCashBack = lancamentos.stream()
					.map(x -> x.getValorCashBack())
					.filter(Objects::nonNull)
					.reduce(BigDecimal.ZERO, BigDecimal::add);
			if (vlrCashBack.longValue()>0){
				throw new Exception("Realize o saque do CashBack no valor de " + vlrCashBack + " antes de fazer novos lançamentos.");
			}
		}
	}

	@Override
	public ContaCorrenteEntity buscaCashBack(UUID idCliente, UUID idVendedor) {
		List<ContaCorrenteEntity> ccNaoSacado = contaCorrenteJPA.findAllCashBackNotWithdrawed( idCliente,  idVendedor);
		if (ccNaoSacado.size() > 0)  {System.out.println("Achou");}
		return ccNaoSacado.get(0);
	}
}
