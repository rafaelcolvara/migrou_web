package com.migrou.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public enum DataFormater {
	
	DATA("dd/MM/yyyy"),
	DATA_YYYY_MM_DD("yyyy-MM-dd"),
	DATA_HORA_12("dd/MM/yyyy hh:mm:ss"),
	DATA_HORA_24("dd/MM/yyyy HH:mm:ss.SSSZ"),
	DATA_HORA_24_SEM_ZONE("dd/MM/yyyy HH:mm:ss"),
	HORA_24_MINUTO_SEM_ZONE("HH:mm"),
	DATA_HORA_24_SEM_BARRA("ddMMyyyy HH:mm:ss.SSSZ"),
	DATA_HORA_24_SEM_BARRA_SEM_ZONE("ddMMyyyy HH:mm:ss.SSS"),
	DATA_HORA_24_JSON("yyyy-MM-dd'T'HH:mm:ss.SSSZ"),
	DATA_HORA_24_JSON_SEM_ZONE("yyyy-MM-dd'T'HH:mm:ss"),
	DATA_HORA_24_JUNTO("yyyyMMddHHmmss"),
	DATA_HORA_24_SQL("yyyy-MM-dd HH:mm:ss");
	
	private String mascara;

	
	
	private DataFormater(String mascara) {
		this.setMascara(mascara);
	}

	public String getMascara() {
		return mascara;
	}

	private void setMascara(String mascara) {
		this.mascara = mascara;
	}
	
	public String getString(Date date){
		if (date == null){
			return null;
		}
		
		return (new SimpleDateFormat(this.getMascara())).format(date);
	}
	
	public Date getDate(String date){
		if (date == null || date.trim().isEmpty()){
			return null;
		}
		try {
			return (new SimpleDateFormat(this.getMascara())).parse(date);
		} catch (ParseException e) {
			throw new RuntimeException(e);
		}
	}
	
	public static long diferencaDias(Date dataInicio, Date dataFim){
		
		if (dataInicio.compareTo(dataFim) > 0){
			Date auxiliar = dataInicio; 
			dataInicio = dataFim;
			dataFim = auxiliar;
		}
		
		long startTime = dataInicio.getTime();
		long endTime = dataFim.getTime();
		long diffTime = endTime - startTime;
		long diffDays = diffTime / (1000 * 60 * 60 * 24);
		
		return diffDays;
		
	}
	
	public static long diferencaAnos(Date dataInicio, Date dataFim){
		if (dataInicio.compareTo(dataFim) > 0){
			Date auxiliar = dataInicio; 
			dataInicio = dataFim;
			dataFim = auxiliar;
		}
		
		long startTime = dataInicio.getTime();
		long endTime = dataFim.getTime();
		long diffTime = endTime - startTime;
		long diffDays = diffTime / (1000 * 60 * 60 * 24);
		
		return diffDays/365;
		
	}
	

}
