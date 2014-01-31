package gui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.GridLayout;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

public class ImagePanel extends JPanel {
	
	private static final long serialVersionUID = 1L;

	public ImagePanel(int gridRows, int gridColumns) {
		
		setBorder(new LineBorder(Color.RED));
		
		GridLayout imgGridLayout = new GridLayout(gridRows, gridColumns);
		this.setLayout(imgGridLayout);	
		
	}
	
	protected void paintComponent(Graphics g) {
		
		super.paintComponent(g);
		/*
		Dimension size = getPreferredSize();
		
		setSize(size);
		
		BufferedImage img = null;
		try  {
			img = ImageIO.read(new File("C:\\Users\\Ronnie\\Documents\\GitHub\\TestGUI\\res\\031.jpg"));
		} catch (IOException e) {
			System.out.println("Couldn't load image...");
			e.printStackTrace();
		}
		
		g.drawImage(img, 0, 0, null);
		*/
	}

}
