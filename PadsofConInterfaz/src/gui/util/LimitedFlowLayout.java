package gui.util;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;

/**
 * FlowLayout con limite máximo de ancho
 * para forzar que los componentes se coloquen uno debajo del otro
 * 
 * 
 * @author Mihai Blidaru
 * @author Sergio Dominguez
 *
 */
public class LimitedFlowLayout extends FlowLayout {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7650175240188524575L;

	/**
	 * Ancho maximo permitido
	 */
	private final int maxWidth;

	/**
	 * Inicializa un nuevo Layout
	 * 
	 * @param maxWidth
	 *            ancho maximo permitido
	 */
	public LimitedFlowLayout(int maxWidth) {
		super();
		this.maxWidth = maxWidth;
	}

	/**
	 * Inicializa un nuevo Layout
	 * 
	 * @param align
	 *            alineacion de los componentes
	 * @param hgap
	 *            separacion horizontal
	 * @param vgap
	 *            separacion vertical
	 * @param maxWidth
	 *            ancho maximo
	 */
	public LimitedFlowLayout(int align, int hgap, int vgap, int maxWidth) {
		super(align, hgap, vgap);
		this.maxWidth = maxWidth;
	}

	/**
	 * Inicializa un nuevo Layout
	 * 
	 * @param align
	 *            alineacion de los componentes
	 * @param maxWidth
	 *            ancho maximo
	 */
	public LimitedFlowLayout(int align, int maxWidth) {
		super(align);
		this.maxWidth = maxWidth;
	}

	@Override
	public Dimension preferredLayoutSize(Container arg0) {
		Dimension sd = super.preferredLayoutSize(arg0);

		sd.width = Math.min(this.maxWidth, sd.width);

		return sd;
	}

}
