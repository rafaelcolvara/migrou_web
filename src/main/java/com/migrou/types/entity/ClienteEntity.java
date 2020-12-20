package com.migrou.types.entity;

import lombok.*;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name ="CLIENTE")
@EqualsAndHashCode(of = {"username"})
public class ClienteEntity {

    @Id
    @Column(name = "usuario_id")
    private String username;

    @OneToOne
    @PrimaryKeyJoinColumn
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "campanha_id")
    private CampanhaEntity campanha;

    @OneToMany(
            mappedBy = "cliente",
            cascade = CascadeType.ALL,
            orphanRemoval = false
    )
    private List<ContaCorrenteEntity> contaCorrenteCliente;



    @Column(name = "NOME", nullable = false)
    private String nome;

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
