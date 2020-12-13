package com.migrou.interfaces.pessoas;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import com.migrou.types.dto.PessoaDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.migrou.types.entity.PessoaEntity;
import org.springframework.data.repository.query.Param;

import javax.mail.internet.InternetAddress;
import javax.transaction.Transactional;

public interface PessoaJPARpository extends JpaRepository<PessoaEntity, UUID>{

	@Query("select p FROM PessoaEntity p where LOWER(p.email) = ?1")
	PessoaEntity findbyEmailIgnoreCase(String email);

	@Query("select p FROM PessoaEntity p where p.nome like %?1%")
	List<PessoaEntity> findbyLikeNomeIgnoreCase(String nome);

	@Modifying
	@Query(value = "insert into PESSOA " +
			"(id_pessoa, dt_cadastro, cpfcnpj, dt_nascimento, email, foto, nome, nr_celular, senha)" +
			" VALUES (" +
			":id_pessoa, :dt_cadastro, :cpfcnpj, :dt_nascimento, :email, :foto, :nome, :nr_celular, :senha )", nativeQuery = true)
	@Transactional
	void salvaPessoa(@Param("id_pessoa") UUID id_pessoa,
					 @Param("dt_cadastro") Date dt_cadastro,
					 @Param("cpfcnpj") Long cpfcnpj,
					 @Param("dt_nascimento") Date dt_nascimento,
					 @Param("email") String email,
					 @Param("foto") String foto,
					 @Param("nome") String nome,
					 @Param("nr_celular") String nr_celular,
					 @Param("senha") String senha);
	
}
