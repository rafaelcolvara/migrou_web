package com.migrou.interfaces.campanha;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.migrou.types.entity.CampanhaEntity;

public interface CampanhaJPARepository extends JpaRepository<CampanhaEntity, Integer> {

	CampanhaEntity findByIdCampanha(Integer idCampanha);
	
	List<CampanhaEntity> findAll();
}
