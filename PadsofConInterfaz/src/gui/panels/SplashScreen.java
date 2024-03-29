package gui.panels;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SpringLayout;

/**
 * Esta clase nos sirve para trabajar con imagen con el logo
 * 
 * @author Mihai Blidaru
 * @author Sergio Dominguez
 */
public class SplashScreen extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 2131474593881451080L;
	/**
	 * La anchura del frame
	 */
	public static int FRAME_WIDTH = 500;

	/**
	 * La altura del frame
	 */
	public static int FRAME_HEIGHT = 300;

	/**
	 * La anchura de la imagen
	 */
	public static int IMAGE_WIDTH = 240;

	/**
	 * La altura de la imagen
	 */
	public static int IMAGE_HEIGHT = 240;

	/**
	 * Constructor de la clase SplashScreen
	 */
	public SplashScreen() {
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setUndecorated(true);
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		this.setBounds((int) (screenSize.getWidth() / 2 - FRAME_WIDTH / 2),
				(int) (screenSize.getHeight() / 2 - FRAME_HEIGHT / 2), FRAME_WIDTH, FRAME_HEIGHT);
		this.getContentPane().setBackground(Color.decode("#ffffff"));
		this.setResizable(false);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Container root = this.getContentPane();
		SpringLayout layout = new SpringLayout();
		root.setLayout(layout);
		root.setBackground(Color.decode("#fafafa"));
		root.setPreferredSize(new Dimension(FRAME_WIDTH, FRAME_HEIGHT));

		BufferedImage image = null;
		try {
			image = ImageIO.read(new File("res/img/logo.png"));
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		Image scaledImage = image.getScaledInstance(IMAGE_WIDTH, IMAGE_HEIGHT, Image.SCALE_SMOOTH);
		JLabel label = new JLabel(new ImageIcon(scaledImage));
		label.setPreferredSize(new Dimension(IMAGE_WIDTH, IMAGE_HEIGHT));
		root.add(label);

		layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, label, 0, SpringLayout.HORIZONTAL_CENTER, root);
		JLabel titulo = new JLabel("TuVacaPiso");
		layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, titulo, 0, SpringLayout.HORIZONTAL_CENTER, root);
		layout.putConstraint(SpringLayout.NORTH, titulo, 5, SpringLayout.SOUTH, label);
		Font font = new Font("Arial", Font.PLAIN, 40);
		titulo.setFont(font);
		root.add(titulo);

		this.pack();
		this.setVisible(true);
	}
}
