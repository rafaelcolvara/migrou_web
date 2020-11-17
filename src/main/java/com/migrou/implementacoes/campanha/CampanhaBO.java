package com.migrou.implementacoes.campanha;

import org.springframework.stereotype.Component;

import com.migrou.types.dto.CampanhaDTO;
import com.migrou.types.entity.CampanhaEntity;

@Component
public class CampanhaBO {

    public CampanhaEntity parseDTOtoPojo(CampanhaDTO campanhaDTO) {
        CampanhaEntity campanhaEntity = new CampanhaEntity();
        campanhaEntity.setNome(campanhaDTO.getNomeCampanha());
        /* -- valores de tipos de campanha */
        campanhaEntity.setFlgPercentualSobreCompras(campanhaDTO.isFlgPercentualSobreCompras());
        campanhaEntity.setPrcTotalLancamentosPercentualSobreCompras(campanhaDTO.getPrcTotalLancamentosPercentualSobreCompras());
        campanhaEntity.setQtLancamentosPercentualSobreCompras(campanhaDTO.getQtLancamentosPercentualSobreCompras());
        campanhaEntity.setFlgValorFixo(campanhaDTO.isFlgValorFixo());
        campanhaEntity.setVlrPremioValorFixo(campanhaDTO.getVlrPremioValorFixo());
        campanhaEntity.setVlrTotalComprasValorFixo(campanhaDTO.getVlrTotalComprasValorFixo());

        return campanhaEntity;
    }
    
    public CampanhaDTO parsePojoToDTO(CampanhaEntity campanha) {
    	CampanhaDTO dto = new CampanhaDTO();
    	dto.setFlgPercentualSobreCompras(campanha.isFlgPercentualSobreCompras());
    	dto.setFlgValorFixo(campanha.isFlgValorFixo());
    	dto.setNomeCampanha(campanha.getNome());
    	dto.setPrcTotalLancamentosPercentualSobreCompras(campanha.getPrcTotalLancamentosPercentualSobreCompras());
    	dto.setQtLancamentosPercentualSobreCompras(campanha.getQtLancamentosPercentualSobreCompras());
    	dto.setVlrPremioValorFixo(campanha.getVlrPremioValorFixo());
    	dto.setVlrTotalComprasValorFixo(campanha.getVlrTotalComprasValorFixo());
    	return dto;
    }
    
}
