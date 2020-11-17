package com.migrou.interfaces.campanha;

import java.util.List;

import com.migrou.types.dto.CampanhaDTO;
import com.migrou.types.entity.CampanhaEntity;

public interface CampanhaInterface {
    CampanhaEntity cadastraCampanha(CampanhaEntity campanhaEntity) throws Exception;
    List<CampanhaDTO> buscaTodos(); 
}
