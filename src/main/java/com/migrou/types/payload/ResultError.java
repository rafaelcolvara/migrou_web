package com.migrou.types.payload;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import org.springframework.http.HttpStatus;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown=true)
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@Data
public class ResultError<T> {

    private HttpStatus status;

    private List<String> erros;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm:ss")
    private LocalDateTime timestamp;
    private String message;
    private String debugMensagem;
    private T subErros;


    public ResultError(HttpStatus status, String message, List<String> errors) {
        super();
        this.status = status;
        this.message = message;
        this.erros = errors;
    }

    public ResultError(HttpStatus status, String message, String error) {
        super();
        this.status = status;
        this.message = message;
        erros = Arrays.asList(error);
    }

    public ResultError(HttpStatus status, String message, T subError) {
        super();
        this.status = status;
        this.message = message;
        this.subErros = subError;
    }
}