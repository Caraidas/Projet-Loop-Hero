package graphics;

import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;

import javax.imageio.ImageIO;

public class ImageConstructor { // This Class is used in View.java to optimize the display.
	private final Path path;
	private int sizeW;
	private int sizeH;
	private BufferedImage img;
	private AffineTransformOp scaling;
	
	public ImageConstructor(Path path, int sizeW, int sizeH) {
		this.path = path;
		this.sizeW = sizeW;
		this.sizeH = sizeH;
		
		makeImage();
		makeScaling();
	}
	
	private void makeImage() {
		InputStream in;
		try {
			in = Files.newInputStream(path);
			img = ImageIO.read(in);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	private void makeScaling() {			
		if (sizeW == -1 && sizeW == -1) {
			sizeW = img.getWidth();
			sizeH = img.getHeight();
		}
		
		scaling = new AffineTransformOp(AffineTransform
				.getScaleInstance(sizeW / (double) img.getWidth(), sizeH / (double) img.getHeight()),
				AffineTransformOp.TYPE_BILINEAR);
	}
	
	// Getter :
	
	public AffineTransformOp scaling() {
		return scaling;
	}
	
	public BufferedImage img() {
		return img;
	}

}
