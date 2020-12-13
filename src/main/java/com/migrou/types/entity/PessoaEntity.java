package com.migrou.types.entity;

import java.io.Serializable;
import java.sql.Clob;
import java.util.Date;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Lob;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;
import org.springframework.context.annotation.Lazy;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Inheritance(strategy= InheritanceType.JOINED)
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "PESSOA")
@EqualsAndHashCode(of = "idPessoa")
@Getter
@Setter
public class PessoaEntity implements Serializable {
	
	@Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column(name = "ID_PESSOA")
    @Type(type="pg-uuid")
    private UUID idPessoa;

    @Column(name = "NOME", nullable = false)
    private String nome;

    @Column(name = "EMAIL", unique = true, nullable = false)
    private String email;
    
    @Column(name = "SENHA", nullable = false)
    private String senha;

    @Column(name = "CPFCNPJ", length = 14, scale = 0)
    private Long cpfCnpj;

    @Column(name = "DT_CADASTRO", nullable = false)
    private Date dtCadastro;

	@Column(name= "DT_NASCIMENTO")
    private Date dtNascimento;

    @Column(name = "URLFOTO")
    private String urlFoto;

	@Column(name = "EMAIL_VALIDO", columnDefinition = "boolean default false" )
    private Boolean flgEmailValido;

	@Column(name = "NR_CELULAR")
    private String nrCelular;


}
