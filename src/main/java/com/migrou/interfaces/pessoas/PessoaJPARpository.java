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

	@Query("select p FROM PessoaEntity p where Upper(p.email) = ?1")
	PessoaEntity findbyEmailIgnoreCase(String email);

	@Query(value = "select " +
			"cast(p.ID_PESSOA as VARCHAR(50)) id_pessoa, " +
			"p.NOME ," +
			"p.EMAIL ," +
			"p.SENHA, " +
			"p.CPFCNPJ ," +
			"p.DT_CADASTRO, " +
			"p.DT_NASCIMENTO, " +
			"p.NR_CELULAR, " +
			"p.EMAIL_VALIDO, " +
			"p.FOTO ," +
			"case " +
			"when v.ID_PESSOA is not null then 'VENDEDOR' " +
			"when c.ID_PESSOA is not null then 'CLIENTE' " +
			"when v.ID_PESSOA is not null and c.ID_PESSOA is not null then 'AMBOS' " +
			"when p.id_pessoa is not null and v.ID_PESSOA is null and c.ID_PESSOA is null then 'NENHUMA'  " +
			"end as TIPOPESSOA , " +
			"COALESCE (v.segmento, '0' ) as segmentoComercial, " +
			"COALESCE (v.nome_negocio, '0' ) as nomeNegocio " +
			"from " +
			"PESSOA p " +
			"left outer join VENDEDOR v on p.ID_PESSOA = v.ID_PESSOA " +
			"left outer join CLIENTE c on p.ID_PESSOA = c.ID_PESSOA " +
			" where UPPER(p.email) = UPPER(?1) and p.senha = ?2 ", nativeQuery = true)
	Object[][] findbyEmailIgnoreCaseAndSenha(String email, String senha);

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
