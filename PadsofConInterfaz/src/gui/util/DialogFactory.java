package gui.util;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class DialogFactory {

	public static void emptyFieldError(String fieldName) {
		JOptionPane.showMessageDialog(new JPanel(), "El campo '" + fieldName + "' no puede estar vacio", "Error",
				JOptionPane.ERROR_MESSAGE);
	}

	public static void internalError(String motivo) {
		JOptionPane.showMessageDialog(new JPanel(), "Se ha producido un error interno:" + motivo, "Error",
				JOptionPane.ERROR_MESSAGE);
	}

	public static void simpleErrorMessage(String message) {
		JOptionPane.showMessageDialog(new JPanel(), message, "Error", JOptionPane.ERROR_MESSAGE);
	}

	public static void invalidValueError(String string) {
		JOptionPane.showMessageDialog(new JPanel(), "El valor del campo '" + string + "' es inválido", "Error",
				JOptionPane.ERROR_MESSAGE);

	}

	public static void noPermisionError() {
		JOptionPane.showMessageDialog(new JPanel(), "No tienes permiso para realizar esta accion", "Error",
				JOptionPane.ERROR_MESSAGE);
	}

}
