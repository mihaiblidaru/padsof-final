package gui.util;

public class NoNameFieldFoundException extends RuntimeException {

	private String className;

	private static final long serialVersionUID = 1949577404334944372L;

	public NoNameFieldFoundException(String className) {
		super();
		this.className = className;
	}

	@Override
	public String toString() {
		return String.format(
				"NoNameFieldFoundException [className=%s] necesita un atributo no nulo de tipo String llamado NAME",
				className);
	}

}
