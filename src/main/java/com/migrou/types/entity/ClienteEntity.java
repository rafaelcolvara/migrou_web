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
@Table(name ="CLIENTE_TESTE")
@EqualsAndHashCode
public class ClienteTesteEntity {

    @Id
    @Column(name = "usuario_id")
    private String username;

    @OneToOne
    @MapsId
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "campanha_id")
    private CampanhaEntity campanha;

    @OneToMany(
            mappedBy = "cliente",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<ContaCorrenteEntity> contaCorrenteCliente;



    @Column(name = "NOME", nullable = false)
    private String nome;

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
