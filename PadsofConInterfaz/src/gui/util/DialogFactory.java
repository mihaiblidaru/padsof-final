package gui.util;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 * Esta es nuestra clase que genera mensajes de error en la interfaz gráfica en caso de que haya alguno
 * 
 * @author Mihai Blidaru
 * @author Sergio Dominguez
 *
 */
public class DialogFactory {

	/**
	 * Genera un mensaje de error si un campo esta vacio
	 * @param fieldName nombre del campo
	 */
	public static void emptyFieldError(String fieldName) {
		JOptionPane.showMessageDialog(new JPanel(), "El campo '" + fieldName + "' no puede estar vacio", "Error",
				JOptionPane.ERROR_MESSAGE);
	}

	/**
	 * Genera un mensaje de error si se ha producido un error
	 * @param motivo motivo de error
	 */
	public static void internalError(String motivo) {
		JOptionPane.showMessageDialog(new JPanel(), "Se ha producido un error interno:" + motivo, "Error",
				JOptionPane.ERROR_MESSAGE);
	}

	/**
	 * Genera un mensaje de error 
	 * @param message mensaje de error
	 */
	public static void simpleErrorMessage(String message) {
		JOptionPane.showMessageDialog(new JPanel(), message, "Error", JOptionPane.ERROR_MESSAGE);
	}

	/**
	 * Genera un mensaje de error si un campo tiene un valor invalido
	 * @param string nombre del campo
	 */
	public static void invalidValueError(String string) {
		JOptionPane.showMessageDialog(new JPanel(), "El valor del campo '" + string + "' es inválido", "Error",
				JOptionPane.ERROR_MESSAGE);

	}
	/**
	 * Genera un mensaje de error si no tienes permiso para realizar una accion
	 */
	public static void noPermisionError() {
		JOptionPane.showMessageDialog(new JPanel(), "No tienes permiso para realizar esta accion", "Error",
				JOptionPane.ERROR_MESSAGE);
	}

}
