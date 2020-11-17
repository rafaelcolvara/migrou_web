package com.migrou.implementacoes.campanha;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.migrou.interfaces.campanha.CampanhaInterface;
import com.migrou.interfaces.campanha.CampanhaJPARepository;
import com.migrou.types.dto.CampanhaDTO;
import com.migrou.types.entity.CampanhaEntity;

@Service("CampanhaService")
public class CampanhaImpl implements CampanhaInterface {

    @Autowired
    CampanhaJPARepository campanhaJPARepository;

    @Autowired
    CampanhaBO campanhaBO;
    @Override
    public CampanhaEntity cadastraCampanha(CampanhaEntity campanhaEntity) throws Exception {

        if (campanhaEntity.isFlgPercentualSobreCompras() && campanhaEntity.isFlgValorFixo()){
            throw new Exception("Só pode haver um tipo de campanha");
        }
        if (campanhaEntity.isFlgPercentualSobreCompras()==false && campanhaEntity.isFlgValorFixo()==false)
            throw new Exception("Deve escolher um tipo de campanha Percentual ou Quantidade de compras");

            return campanhaJPARepository.save(campanhaEntity);
    }

	@Override
	public List<CampanhaDTO> buscaTodos() {
		List<CampanhaDTO> dtos = new ArrayList<CampanhaDTO>();
		campanhaJPARepository.findAll().stream().forEach((campanha) -> {
			dtos.add(campanhaBO.parsePojoToDTO(campanha));
		});
		return dtos;
	}
    
    
    
    
}
