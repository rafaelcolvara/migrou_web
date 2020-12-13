package com.migrou.types.payload;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.UUID;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown=true)
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@Data
public class ResultData<T> implements Serializable {

    /**
     * Serial UID
     */
    private static final long serialVersionUID = -92493142081236368L;

    private String idMensagem;

    @XmlAnyElement(lax=true)
    private T value;

    private String mensagem;

    private Long qtTotalRegistros;

    private BigDecimal soma;

    private Integer paginaAtual;

    private Integer qtRegistroPorPaginacao;

    private ResultError erro;

    private Integer qtPaginas;


    public ResultData(T t){
        this();
        this.value = t;
    }

    public ResultData(T t, String m){
        this();
        this.value = t;
        this.mensagem = m;
    }

    public ResultData(String m, ResultError e){
        this();
        this.mensagem = m;
        this.erro = e;
    }

    public ResultData(T t, ResultData resultData) {
        this();
        this.value = t;
        this.soma =resultData.getSoma();
        this.qtTotalRegistros=resultData.getQtTotalRegistros();
        this.qtRegistroPorPaginacao=resultData.getQtRegistroPorPaginacao();
        this.qtTotalRegistros = resultData.getQtTotalRegistros();
        this.qtPaginas=resultData.getQtPaginas();
        this.paginaAtual=resultData.getPaginaAtual();
        this.mensagem = resultData.getMensagem();
        this.erro = resultData.getErro();
    }

    public ResultData(T t, String mensagem, ResultData resultData) {
        this(t, resultData);
        this.mensagem = mensagem;

    }

    public ResultData(T t, String m, ResultError e){
        this();
        this.value = t;
        this.mensagem = m;
        this.erro = e;
    }

    public ResultData(String m){
        this();
        this.mensagem = m;

    }

    public ResultData(ResultError e){
        this();
        this.erro = e;
    }

    public ResultData(){
        this.idMensagem = UUID.randomUUID().toString();

    }

}
