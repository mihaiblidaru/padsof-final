package gui.util;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

import javax.swing.JComponent;

/**
 * Esta es nuestra interfaz que llama a setname de los paneles de la interfaz para ayudar en la busqueda
 * 
 * @author Mihai Blidaru
 * @author Sergio Dominguez
 *
 */
public interface Nombrable {
	/**
	 * Funcion que define el nombre de los paneles para ayudar en la busqueda
	 */
	public default void setGlobalName() {
		JComponent c = ((JComponent) this);
		Field[] fields = c.getClass().getDeclaredFields();
		for (Field f : fields) {
			if (Modifier.isStatic(f.getModifiers()) && f.getName().equals("NAME")) {
				try {
					String value = (String) f.get(null);
					c.setName(value);
					return;
				} catch (IllegalArgumentException | IllegalAccessException e) {
					e.printStackTrace();
				}
			}
		}
		throw new NoNameFieldFoundException(c.getClass().getName());
	}
}
