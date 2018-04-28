package app.clases.data;

import java.time.LocalDate;
/**
 * Esta clase nos da las columnas que tendran las tablas de  la base de datos
 * 
 * @author Mihai Blidaru 
 * @author Sergio Dominguez
 *
 */
public enum Columna {
	ID(Integer.class),
	CLIENTE_IDENTIFICADOR(String.class),
	CLIENTE_PASSWORD(String.class),
	CLIENTE_FULL_NAME(String.class),
	CLIENTE_ROL(String.class),
	CLIENTE_IMPORTE_PENDIENTE(Float.class),
	CLIENTE_PROBLEMA_PAGOS(Boolean.class),
	CLIENTE_CCARD(String.class),
	
	CARACTERISTICA_CLAVE(String.class),
	CARACTERISTICA_VALOR(String.class),
	CARACTERISTICA_INMUEBLE(Integer.class),
	
	OFERTA_FECHAINICIO(LocalDate.class),
	OFERTA_FECHAFIN(LocalDate.class),
	OFERTA_DESCRIPCION(String.class),
	OFERTA_PRECIO(Float.class),
	OFERTA_FIANZA(Float.class),
	OFERTA_ESTADO(String.class),
	OFERTA_TIPO(String.class),
	OFERTA_NUM_MESES(Integer.class),
	OFERTA_INMUEBLE(Integer.class),
	OFERTA_DEMANDANTE(Integer.class),
	OFERTA_RESERVA(Integer.class),
	
	RESERVA_FECHAINICIO(LocalDate.class),
	RESERVA_CLIENTE(Integer.class),
	RESERVA_OFERTA(Integer.class), 
	RESERVA_FECHACADUCAR(LocalDate.class),
	
	OPINION_VALOR(Integer.class),
	OPINION_USUARIO(Integer.class),
	OPINION_OFERTA(Integer.class),
	OPINION_PADRE(Integer.class),
	OPINION_COMENTARIO(String.class),
	OPINION_FECHA(LocalDate.class),
	
	INMUEBLE_DIRECCION(String.class),
	INMUEBLE_LOCALIDAD(String.class),
	INMUEBLE_CLIENTE(Integer.class),
	INMUEBLE_CP(Integer.class);
	
		
	/**
	 * El tipo de dato de la columna
	 */
	private Class<?> type;
	
	/**
	 * Asigna un tipo de dato a la columna
	 * @param type tipo de dato
	 */
	private Columna(Class<?> type) {
		this.type = type; 
	}
	
	/**
	 * Devuelve el tipo de dato
	 * @return type tipo de dato
	 */
	public Class<?> getType() {
		return type;
	}
	
	/** 
	 * Devuelve el nombre de la columna como cadena
	 */
	@Override
	public String toString() {
		String[] vals = this.name().split("_");
		if(vals.length == 1) {
			return vals[0];
		}else {
			String tName = vals[1];
			for(int i = 2; i<vals.length;i++) {
				tName += "_" + vals[i];
			}
			return tName;
		}
	}
	
	/**
	 * Compara los tipos
	 * @param obj ojeto con el que comparamos
	 * @return true si son iguales, si no false
	 */
	public boolean matchTypes(Object obj) {
		return type.isInstance(obj);
	}
}
