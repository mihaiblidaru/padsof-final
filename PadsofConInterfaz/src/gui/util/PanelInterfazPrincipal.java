package gui.util;

public interface PanelInterfazPrincipal {

	default void initialize() {
		crearComponentes();
		colocarComponentes();
		setDimension();
		registrarEventos();
	}

	void setDimension();

	void crearComponentes();

	void colocarComponentes();

	void registrarEventos();

}
