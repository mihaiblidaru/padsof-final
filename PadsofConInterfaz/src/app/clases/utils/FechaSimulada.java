package app.clases.utils;

import java.time.LocalDate;
/**
 * Clase auxiliar para permitir pruebas con "fechas simuladas" y controladas
 * @author Profesores PADSOF
 *
 */
public class FechaSimulada {
	
	// inicializacion por defecto a una fecha fija, NO CAMBIAR
	private static LocalDate fechaSimulada 
	                     =  LocalDate.now().withDayOfMonth(1).withMonth(3).withYear(2017);

	/**
	 * Avanza numDias la fecha simulada actual, o la retrasa si numDias es negativo
	 * 
	 * @param numDias numero de dias
	 */
	public static void avanzar(int numDias) {
		fechaSimulada = fechaSimulada.plusDays(numDias);
	}
	
	/**
	 * Restablece la fecha simulada a la fecha real de hoy en ejecucion
	 */
	public static void restablecerHoyReal() {
		fechaSimulada =  LocalDate.now();
	}
	

	/**
	 * Retorna la fecha simulada actual o fecha simulada de "hoy".
	 * En general, no sera la fecha real de hoy,
	 * a menos que no se haya ejecutado ninguna llamada avanzar() desde la
	 * ultima ejecucion de restablecerHoyReal()
	 * @return fecha simulada actual
	 */
	public static LocalDate getHoy() {
		return fechaSimulada;
	}
	
	/**
	 * Se fija la fecha simulada con los valores de dia, mes y anyo dados como parametros
	 * @param dia dia del mes
	 * @param mes mes del a�o
	 * @param anyo a�o de la fecha
	 * @return fecha simulada actual con los valores dados como parametros
	 */
	public static LocalDate fijarFecha(int dia, int mes, int anyo) {
		fechaSimulada = fechaSimulada.withDayOfMonth(dia).withMonth(mes).withYear(anyo);
		return fechaSimulada;
	}

}