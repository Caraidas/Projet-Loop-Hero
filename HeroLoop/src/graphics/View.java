package graphics;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Iterator;
import javax.imageio.ImageIO;
import entities.Monster;
import entities.Player;
import fr.umlv.zen5.ApplicationContext;
import fr.umlv.zen5.ScreenInfo;
import map.Cell;
import map.Map;
import map.RoadCell;
import time.TimeData;

public class View {
	
	private static float getScreenWidth(ApplicationContext context) {
		ScreenInfo screenInfo = context.getScreenInfo();
		return screenInfo.getWidth();
	}
	
	private static float getScreenHeight(ApplicationContext context) {
		ScreenInfo screenInfo = context.getScreenInfo();
		return screenInfo.getHeight();
	}
	
	public static void drawPlayer(ApplicationContext context, Graphics2D graphics, Player player, Map m, int caseSize) {
		Path path = Path.of("ressources/Entities-Sprite/Player.png");
		int column = m.loop().get(player.position()).column();
		int line = m.loop().get(player.position()).line();
		drawImage(context, graphics, (int)((line * caseSize) + (caseSize * 0.25)), (int)((column * caseSize) + (caseSize * 1.25)), path, 30);
	}
	
	private static void drawImage(ApplicationContext context, Graphics2D graphics, int i, int j, Path path, int size) {
		try (InputStream in = Files.newInputStream(path)) {
			BufferedImage img = ImageIO.read(in);
			AffineTransformOp scaling = new AffineTransformOp(AffineTransform
					.getScaleInstance(size / (double) img.getWidth(), size / (double) img.getHeight()),
					AffineTransformOp.TYPE_BILINEAR);
			graphics.drawImage(img, scaling, i, j);
		} catch (IOException e) {
			throw new RuntimeException("problème d'affichage : " + path.getFileName());
		}
	}
	
	public static void drawRoadCell(ApplicationContext context, Graphics2D graphics, int i, int j, int caseSize, Cell c) {
		String pictureName = "ressources/Map-Sprite/horizontal-road.png";
		Path path = Path.of(pictureName);
		drawImage(context, graphics, i, j, path, caseSize);
		
		int newI;	
		int newJ;
		ArrayList<Monster> monsters = ((RoadCell)(c)).getEntities();
		for (int index = 0; index < monsters.size(); index++) { // ((RoadCase)(c)).getEntities()
			if (index % 2 != 0) {
				newI = (int)(i + (caseSize * 0.5)) - 5;
			} else {
				newI = i + 5;
			}
			
			if (index <= 1) { 
				newJ = j + 5;
			} else {
				newJ = (int)(j + (caseSize * 0.5)) - 5;
			}
			drawImage(context, graphics, newI, newJ, Path.of(monsters.get(index).getSprite()), (int)(caseSize * 0.35));
		}
	}
	
	public static void drawCell(ApplicationContext context, Graphics2D graphics, int i, int j, Cell c, int caseSize) {
		
		if (c instanceof RoadCell) {
			drawRoadCell(context, graphics, i, j, caseSize, c);
		} else {
			graphics.setColor(Color.white);
			graphics.drawRect(i, j, 60, 60);
		}
	}
	
	public static void drawMap(ApplicationContext context, Graphics2D graphics, Map map, int caseSize) {
		for (int i = 0; i < map.lines(); i++) {
			for (int j = 0; j < map.columns(); j++) {
				drawCell(context, graphics, j * caseSize, i * caseSize + caseSize, map.getCase(i, j), caseSize);
			}
		}
	}
	
	public static void drawHudRight(ApplicationContext context, Graphics2D graphics, float width, int caseSize) {
		int HudWidth = (int)(width - (21 * caseSize));
		float height = getScreenHeight(context);
		
		graphics.setColor(Color.gray);
		graphics.fillRect((21 * caseSize), 0, HudWidth, (int) height);
	}
	
	public static void drawHudTop(ApplicationContext context, Graphics2D graphics, float width, int caseSize) {
		graphics.setColor(Color.gray);
		graphics.fillRect(0, 0, (int) width, caseSize);
	}
	
	public static void drawTimeBar(ApplicationContext context, Graphics2D graphics, double timeFraction) {
		graphics.setColor(Color.LIGHT_GRAY);
		graphics.fillRect(10, 10, 400, 20);
		graphics.setColor(Color.GREEN);
		graphics.fillRect(15, 15, (int)(390 * timeFraction), 10);
	}
	
	public static void drawHud(ApplicationContext context, Graphics2D graphics, float width, int caseSize, TimeData timeData) {
		drawHudRight(context, graphics, width, caseSize);
		drawHudTop(context, graphics, width, caseSize);
		drawTimeBar(context, graphics, timeData.timeFraction());
	}
	
	public static void drawScreen(ApplicationContext context, Map m, Player player, TimeData timeData) {
		float width = getScreenWidth(context);
		int caseSize = (int) ((60 * width) / 1536);
		
		context.renderFrame(graphics -> {
			drawMap(context, graphics, m, caseSize);
			drawHud(context, graphics, width, caseSize, timeData);
			drawPlayer(context, graphics, player, m, caseSize);
		});
	}
}
