package com.migrou.implementacoes.pessoas;

import com.migrou.implementacoes.pessoas.bo.PessoaBO;
import com.migrou.interfaces.campanha.CampanhaJPARepository;
import com.migrou.interfaces.cliente.ClienteJPARepository;
import com.migrou.interfaces.pessoas.PessoaInterface;
import com.migrou.interfaces.pessoas.PessoaJPARpository;
import com.migrou.interfaces.vendedor.VendedorJPARepository;
import com.migrou.types.dto.PessoaDTO;
import com.migrou.types.dto.PessoaFotoDTO;
import com.migrou.types.entity.CampanhaEntity;
import com.migrou.types.entity.ClienteEntity;
import com.migrou.types.entity.PessoaEntity;
import com.migrou.types.entity.VendedorEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.mail.*;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.io.IOException;
import java.util.*;

@Service("pessoaService")
public class PessoaImpl implements PessoaInterface {

    @PersistenceContext
    EntityManager entityManager;

    @Value("${spring.mail.username}")
    private String usernameEmail;

    @Value("${spring.mail.password}")
    private String passwordEmail;

    @Autowired
    VendedorJPARepository vendedorJPARepository;

    @Autowired
    ClienteJPARepository clienteJPARepository;

    @Autowired
    CampanhaJPARepository campanhaJPARepository;

    @Autowired
    PessoaJPARpository pessoaJpa;

    @Autowired
    PessoaBO pessoaBO;

    @Override
    @Transactional
    public PessoaDTO cadastraPessoa(PessoaDTO pessoaDTO) throws Exception {


        PessoaDTO pessoaDTOReturn = new PessoaDTO();
        if (pessoaDTO.getTipoPessoa().equals("CLIENTE")) {
            ClienteEntity clienteEntity = new ClienteEntity();
            CampanhaEntity campanhaEntity = campanhaJPARepository.findByIdCampanha(1);
            clienteEntity.setDtCadastro(pessoaDTO.getDataCadastro());
            clienteEntity.setCpfCnpj(pessoaDTO.getCpfCnpj());
            clienteEntity.setDtNascimento(pessoaDTO.getDataNascimento());
            clienteEntity.setEmail(pessoaDTO.getEmail().toLowerCase());
            clienteEntity.setFlgEmailValido(true);
            clienteEntity.setNome(pessoaDTO.getNome());
            clienteEntity.setNrCelular(pessoaDTO.getNrCelular());
            clienteEntity.setSenha(pessoaDTO.getSenha());
            clienteEntity.setCampanha(campanhaEntity);
            pessoaDTOReturn = pessoaBO.parsePojoToDto(clienteJPARepository.findById(clienteJPARepository.save(clienteEntity).getIdPessoa()).orElseThrow(() -> new Exception("Não foi possivel salvar cliente")));
        } else if (pessoaDTO.getTipoPessoa().equals("VENDEDOR")) {

            if (Objects.isNull(pessoaDTO.getNomeNegocio()) || pessoaDTO.getNomeNegocio().isEmpty()) {
                throw new Exception("Nome do negócio deve ser informado");
            }
            if (Objects.isNull(pessoaDTO.getSegmentoComercial()) || pessoaDTO.getSegmentoComercial().isEmpty()) {
                throw new Exception("Segmento comercial deve ser informado");
            }
            VendedorEntity vendedorEntity = new VendedorEntity();
            vendedorEntity.setDtCadastro(pessoaDTO.getDataCadastro());
            vendedorEntity.setCpfCnpj(pessoaDTO.getCpfCnpj());
            vendedorEntity.setDtNascimento(pessoaDTO.getDataNascimento());
            vendedorEntity.setEmail(pessoaDTO.getEmail().toLowerCase());
            vendedorEntity.setFlgEmailValido(true);
            vendedorEntity.setNome(pessoaDTO.getNome());
            vendedorEntity.setNomeNegocio(pessoaDTO.getNomeNegocio());
            vendedorEntity.setNomeSegmento(pessoaDTO.getSegmentoComercial());
            vendedorEntity.setNrCelular(pessoaDTO.getNrCelular());
            vendedorEntity.setSenha(pessoaDTO.getSenha());
            pessoaDTOReturn = pessoaBO.parsePojoToDto(vendedorJPARepository.findById(vendedorJPARepository.save(vendedorEntity).getIdPessoa()).orElseThrow(() -> new Exception("Não foi possivel salvar vendedor")));
        }

        return pessoaDTOReturn;

    }

    @Override
    public PessoaDTO consultaPorEmaileSenha(String email, String senha, String tipoPessoa) throws Exception {

        ClienteEntity clienteEntity = null;
        VendedorEntity vendedorEntity = null;
        PessoaDTO pessoaDTO = new PessoaDTO();
        if (tipoPessoa.compareTo("CLIENTE") == 0) {
            clienteEntity = clienteJPARepository.findbyEmailIgnoreCaseAndSenha(email.toLowerCase(), senha);
            pessoaDTO = pessoaBO.parsePojoToDto(clienteEntity);
        }
        if (tipoPessoa.compareTo("VENDEDOR") == 0) {
            vendedorEntity = vendedorJPARepository.findbyEmailIgnoreCaseAndSenha(email, senha);
            pessoaDTO = pessoaBO.parsePojoToDto(vendedorEntity);
        }

        if (Objects.isNull(vendedorEntity) && Objects.isNull(clienteEntity)) {
            throw new Exception("ERROR_USER_NOT_FOUND");
        } else {
            return pessoaDTO;
        }
    }

    @Override
    @Transactional
    public void apagaPessoa(UUID idPessoa) throws Exception {

    }

    @Override
    public PessoaEntity consutaClientePorEmail(String email) {

        return pessoaJpa.findbyEmailIgnoreCase(email);
    }

    @Override
    @Transactional
    public void AtualizaFoto(PessoaFotoDTO pessoaFoto) throws Exception {

        PessoaEntity pessoa = pessoaJpa.findById(pessoaFoto.getIdPessoa()).orElseThrow(() -> new Exception("Id não encontrado"));
        pessoa.setFoto(org.apache.tomcat.util.codec.binary.Base64.decodeBase64(pessoaFoto.getByteArrayFoto()));
        pessoaJpa.save(pessoa);
    }

    @Override
    public PessoaFotoDTO consultaFotoPefilPorID(UUID idPessoa) throws Exception {
        PessoaFotoDTO pessoaReturn = new PessoaFotoDTO();
        pessoaReturn.setIdPessoa(idPessoa);
        PessoaEntity pess = new PessoaEntity();
        pess = pessoaJpa.findById(idPessoa).orElseThrow(() -> new Exception("nao encoutrou pessoa"));
        pessoaReturn.setByteArrayFoto(Base64.getEncoder().encodeToString(pess.getFoto()));
        pessoaReturn.setIdPessoa(pess.getIdPessoa());
        return pessoaReturn;
    }

    @Override
    public void EnviaEmail(PessoaDTO pessoaDTO) throws AddressException, MessagingException, IOException {

        String textoEmail = "<table cellspacing=\"0\" cellpadding=\"10\" border=\"0\">\n" +
                " <tr>\n" +
                "  <td width=\"600\" valign=\"top\">Olá, " + pessoaDTO.getNome() + ", bem vindo ao Migrou! </td>\n" +
                "  <td width=\"480\" valign=\"top\"></td>\n" +
                " </tr>\n" +
                " <tr></tr>" +
                " <tr></tr>" +
                " <tr>\n" +
                "  <td width=\"120\" valign=\"top\"></td>\n" +
                "  <td width=\"480\" valign=\"top\">Por favor, clique no link abaixo para ativar seu cadastro:</td>\n" +
                "  <td width=\"480\" valign=\"top\">http://192.168.1.166/ativacao/2</td>\n" +
                " </tr>\n" +
                "</table>";

        Properties properties = new Properties();
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "587");

        Session session = Session.getInstance(properties,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(usernameEmail, passwordEmail);
                    }
                });

        Message msg = new MimeMessage(session);
        msg.setFrom(new InternetAddress(usernameEmail, false));

        msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(pessoaDTO.getEmail()));
        msg.setSubject("Enviando e-mail pelo Migrou-Web");
        msg.setContent(textoEmail, "text/html");

        try {

            Transport.send(msg);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void AtivaViaEmail(PessoaDTO pessoaDTO) throws Exception {

        PessoaEntity pessoaEntity = pessoaBO.parseDtoToPojo(pessoaDTO);
        pessoaEntity.setFlgEmailValido(true);
        pessoaJpa.save(pessoaEntity);

    }


}

