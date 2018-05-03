package gui.util;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class DialogFactory {

	public static void emptyFieldError(String fieldName) {
		JOptionPane.showMessageDialog(new JPanel(), "El campo '" + fieldName + "' no puede estar vacio", "Error",
				JOptionPane.ERROR_MESSAGE);
	}

}
