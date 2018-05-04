package gui.util;

public interface PanelInterfazPrincipal {

	default void initialize() {
		setDimension();
		crearComponentes();
		colocarComponentes();
		registrarEventos();
	}

	void setDimension();

	void crearComponentes();

	void colocarComponentes();

	void registrarEventos();

}
