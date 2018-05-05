package gui.util;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;

public class LimitedFlowLayout extends FlowLayout {

	private final int maxWidth;

	public LimitedFlowLayout(int maxWidth) {
		super();
		this.maxWidth = maxWidth;
	}

	public LimitedFlowLayout(int align, int hgap, int vgap, int maxWidth) {
		super(align, hgap, vgap);
		this.maxWidth = maxWidth;
	}

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
