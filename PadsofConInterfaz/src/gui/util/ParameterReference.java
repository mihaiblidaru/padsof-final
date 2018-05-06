package gui.util;

public class ParameterReference<T> {

	private T value;

	public void setValue(T k) {
		value = k;
	}

	public T getValue() {
		return value;
	}

	public ParameterReference(T value) {
		this.value = value;
	}

	public ParameterReference() {

	}
}
