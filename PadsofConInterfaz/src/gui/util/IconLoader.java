package gui.util;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

/**
 * Esta es nuestra clase que carga nuestro icono
 * 
 * @author Mihai Blidaru
 * @author Sergio Dominguez
 *
 */

public class IconLoader {
	/**
	 * Nuestra imagen icono
	 */
	private static BufferedImage errImg = null;

	/**
	 * Carga nuestra imagen icono
	 * @param path el path para la imagen
	 * @param width el ancho de la imagen
	 * @param height la altura de la imagen
	 * @return la imagen cargada
	 */
	public static ImageIcon load(String path, int width, int height) {
		if (errImg == null) {
			errImg = new BufferedImage(1, 1, BufferedImage.TYPE_INT_RGB);
			Graphics2D g = errImg.createGraphics();
			g.setColor(Color.MAGENTA);
			g.drawRect(0, 0, 1, 1);
		}

		try {
			return new ImageIcon(ImageIO.read(new File(path)).getScaledInstance(width, height, Image.SCALE_SMOOTH));
		} catch (IOException e1) {
			return new ImageIcon(errImg.getScaledInstance(width, height, Image.SCALE_FAST));
		}
	}
}
