package com.migrou.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.http.HttpStatus;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.Objects;

@Data
@EqualsAndHashCode(callSuper=false)
@ToString
public class NegocioException extends RuntimeException {

	private static final String MENSAGEM_NULL = "NULL";

	private static final String MENSAGEM_EXCLUSIVA = "EXCLUSIVA";

	private static final String TIMED_OUT = "TIMED OUT";

	private HttpStatus httpStatus;

	private Object obj;

	/**
	 * 
	 */
	private static final long serialVersionUID = 5040356885984905873L;
	
	public NegocioException(String msg) {
		super(msg);
		this.httpStatus = HttpStatus.BAD_REQUEST;
	}
	
	public NegocioException(String msg, HttpStatus status) {
		super(msg);
		this.httpStatus = status;
	}
	
	public NegocioException(String msg, Throwable ex, HttpStatus status) {
		super(msg, ex);
		this.httpStatus = status;
	}

	public NegocioException(Object o, String msg) {
		super(msg);
		this.httpStatus = HttpStatus.BAD_REQUEST;
		this.obj = o;
	}

	public NegocioException(Object o, String msg, HttpStatus status) {
		super(msg);
		this.httpStatus = status;
		this.obj = o;
	}

	public NegocioException(Object o, String msg, Throwable ex, HttpStatus status) {
		super(msg, ex);
		this.httpStatus = status;
		this.obj = o;
	}

	public boolean hasObject(){
		return Objects.nonNull(this.obj);
	}

	@Override
	public synchronized Throwable initCause(Throwable cause) {
		return super.initCause(cause);
	}


	public static boolean isExcecaoBancoNULL(Throwable ex) {
		return ex.getCause() != null && ex.getCause().getCause() != null &&
				((ex.getCause() instanceof SQLIntegrityConstraintViolationException
						&& ex.getCause().getMessage() != null
						&& ex.getCause().getMessage().toUpperCase().contains(MENSAGEM_NULL))
						|| (ex.getCause().getCause() instanceof SQLIntegrityConstraintViolationException
						&& ex.getCause().getCause().getMessage() != null
						&& ex.getCause().getCause().getMessage().toUpperCase().contains(MENSAGEM_NULL)));
	}

	public static boolean isExcecaoBancoChaveExclusiva(Throwable ex) {
		boolean isExcecaoChaveExclusiva = (ex != null && ex instanceof SQLIntegrityConstraintViolationException
				&& ex.getMessage() != null
				&& ex.getMessage().toUpperCase().contains(MENSAGEM_EXCLUSIVA));

		if (!isExcecaoChaveExclusiva){
			return (ex.getCause() != null && isExcecaoBancoChaveExclusiva(ex.getCause()));
		}
		return isExcecaoChaveExclusiva;
	}

	public static boolean isExcecaoNegocio(Throwable ex) {
		return ex instanceof NegocioException || (ex.getCause() != null ? isExcecaoNegocio(ex.getCause()) : false);
	}

	public static boolean isExcecaoTimeout(Throwable ex) {
		return ex.getCause() != null &&  ex.getCause().getMessage() != null && ex.getCause().getMessage().toUpperCase().contains(TIMED_OUT);
	}

	public static String gerarDescricaoException(Throwable e) {
		if (e.getCause() != null) {
			return "TRACE: Exceção["
					.concat(e.getCause().toString())
					.concat("][Classe ")
					.concat(e.getCause().getStackTrace()[0].getClassName())
					.concat(", metodo: ")
					.concat(e.getCause().getStackTrace()[0].getMethodName())
					.concat(", linha: ")
					.concat(String.valueOf(e.getCause().getStackTrace()[0]
							.getLineNumber()))
					.concat("] "+ (e.getCause().getMessage() != null ?
							" MensagemWS:{"+e.getCause().getMessage().substring(0, e.getCause().getMessage().length() > 500 ? 500 : e.getCause().getMessage().length()).concat("}")
							:""));
		}

		return "TRACE: Exceção["
				.concat(e.toString())
				.concat("][Classe ")
				.concat(e.getStackTrace()[0].getClassName())
				.concat(", metodo: ")
				.concat(e.getStackTrace()[0].getMethodName())
				.concat(", linha: ")
				.concat(String.valueOf(e.getStackTrace()[0]
						.getLineNumber()))
				.concat("] " + (e.getMessage() != null ?  " MensagemWS:{"+e.getMessage().substring(
						0,
						e.getMessage().length() > 500 ? 500 : e
								.getMessage().length()).concat("}") :""));


	}


	/**
	 * Captura Erro das exceções
	 *
	 * @param ex
	 * @return
	 */
	public static String capturarErro(Throwable ex) {
		StringWriter errors = new StringWriter();
		ex.printStackTrace(new PrintWriter(errors));
		return errors.toString();
	}




}
