package logic;

import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JComponent;
import javax.swing.JFrame;

public class ImageTestR {

	public static void main(String[] args){
		EventQueue.invokeLater(new Runnable()
	        {
	            public void run(){
	                ImageFrame frame = new ImageFrame();
	                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	                frame.setVisible(true);


	            }
	        }
	        );
	    }
	}

	class ImageFrame extends JFrame{

	    public ImageFrame(){
	        setTitle("ImageTest");
	        setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);

	        ImageComponent component = new ImageComponent();
	        add(component);

	    }

	    public static final int DEFAULT_WIDTH = 500;
	    public static final int DEFAULT_HEIGHT = 300;
	}


	class ImageComponent extends JComponent{
	    /**
	     * 
	     */
	    private static final long serialVersionUID = 1L;
	    private Image image;
	    public ImageComponent(){
	        try{
	            File image2 = new File("gui-skisse2.png");
	            image = ImageIO.read(image2);

	        }
	        catch (IOException e){
	            e.printStackTrace();
	        }
	        
	   
	        
	    }
	    public void paintComponent (Graphics g){
	        if(image == null) return;
	        int imageWidth = image.getWidth(this);
	        int imageHeight = image.getHeight(this);

	        g.drawImage(image, 50, 50, this);

	        for (int i = 0; i*imageWidth <= getWidth(); i++)
	            for(int j = 0; j*imageHeight <= getHeight();j++)
	                if(i+j>0) g.copyArea(0, 0, imageWidth, imageHeight, i*imageWidth, j*imageHeight);
	
	    
	    }
	    
	    public static Image rotate(Image img, double angle){
		    double sin = Math.abs(Math.sin(Math.toRadians(angle))),
		           cos = Math.abs(Math.cos(Math.toRadians(angle)));

		    int w = img.getWidth(null), h = img.getHeight(null);

		    int neww = (int) Math.floor(w*cos + h*sin),
		        newh = (int) Math.floor(h*cos + w*sin);

		    BufferedImage bimg = toBufferedImage(getEmptyImage(neww, newh));
		    Graphics2D g = bimg.createGraphics();

		    g.translate((neww-w)/2, (newh-h)/2);
		    g.rotate(Math.toRadians(angle), w/2, h/2);
		    g.drawRenderedImage(toBufferedImage(img), null);
		    g.dispose();

		    return toImage(bimg);
		    
		}

	}
	

