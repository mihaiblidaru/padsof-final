package gui.util;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

import javax.swing.JComponent;

public interface Nombrable {
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
