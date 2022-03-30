package graphics;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import data.GameData;
import entities.Monster;
import entities.Player;
import fr.umlv.zen5.ApplicationContext;
import fr.umlv.zen5.ScreenInfo;
import map.Cell;
import map.RoadCell;
import time.TimeData;

public class View {
	private final Player player;
	private final TimeData timeData;
	private final GameData gameData;
	
	public View(Player player, TimeData timeData, GameData gameData) {
		this.player = player;
		this.timeData = timeData;
		this.gameData = gameData;
	}
	
	private  float getScreenWidth(ApplicationContext context) {
		ScreenInfo screenInfo = context.getScreenInfo();
		return screenInfo.getWidth();
	}
	
	private  float getScreenHeight(ApplicationContext context) {
		ScreenInfo screenInfo = context.getScreenInfo();
		return screenInfo.getHeight();
	}
	
	public  void drawPlayer(ApplicationContext context, Graphics2D graphics, int caseSize) {
		Path path = Path.of("ressources/Entities-Sprite/Player.png");
		int column = gameData.map().loop().get(player.position()).column();
		int line = gameData.map().loop().get(player.position()).line();
		drawImage(context, graphics, (int)((line * caseSize) + (caseSize * 0.25)), (int)((column * caseSize) + (caseSize * 1.25)), path, 30, 30);
	}
	
	private  void drawImage(ApplicationContext context, Graphics2D graphics, int i, int j, Path path, int sizeW, int sizeH) {
		try (InputStream in = Files.newInputStream(path)) {
			BufferedImage img = ImageIO.read(in);
			
			if (sizeW == -1 && sizeW == -1) {
				sizeW = img.getWidth();
				sizeH = img.getHeight();
			}
			
			AffineTransformOp scaling = new AffineTransformOp(AffineTransform
					.getScaleInstance(sizeW / (double) img.getWidth(), sizeH / (double) img.getHeight()),
					AffineTransformOp.TYPE_BILINEAR);
			graphics.drawImage(img, scaling, i, j);
		} catch (IOException e) {
			throw new RuntimeException("problème d'affichage : " + path.getFileName());
		}
	}
	
	public  void drawRoadCell(ApplicationContext context, Graphics2D graphics, int i, int j, int caseSize, Cell c) {
		String pictureName = "ressources/Map-Sprite/horizontal-road.png";
		Path path = Path.of(pictureName);
		drawImage(context, graphics, i, j, path, caseSize, caseSize);
		
		int newI;
		int newJ;
		ArrayList<Monster> monsters = ((RoadCell)(c)).getEntities();
		for (int index = 0; index < monsters.size(); index++) { // ((RoadCase)(c)).getEntities()
			if (index % 2 != 0) {
				newI = (int)(i + (caseSize * 0.75)) - 5;
			} else {
				newI = i + 5;
			}
			
			if (index <= 1) { 
				newJ = j + 5;
			} else {
				newJ = (int)(j + (caseSize * 0.75)) - 5;
			}
			drawImage(context, graphics, newI, newJ, Path.of(monsters.get(index).getSprite()), (int)(caseSize * 0.25), (int)(caseSize * 0.25));
		}
	}
	
	public  void drawCell(ApplicationContext context, Graphics2D graphics, int i, int j, Cell c, int caseSize) {
		
		if (c instanceof RoadCell) {
			drawRoadCell(context, graphics, i, j, caseSize, c);
		} else {
			graphics.setColor(Color.white);
			graphics.drawRect(i, j, 60, 60);
		}
	}
	
	public  void drawMap(ApplicationContext context, Graphics2D graphics, int caseSize) {
		for (int i = 0; i < gameData.map().lines(); i++) {
			for (int j = 0; j < gameData.map().columns(); j++) {
				drawCell(context, graphics, j * caseSize, i * caseSize + caseSize, gameData.map().getCase(i, j), caseSize);
			}
		}
	}
	
	public void drawHudRight(ApplicationContext context, Graphics2D graphics, float width, int caseSize) {
		int HudWidth = (int)(width - (21 * caseSize));
		float height = getScreenHeight(context);
		
		graphics.setColor(Color.gray);
		graphics.fillRect((21 * caseSize), 0, HudWidth, (int) height);
		
		
		String playerHp = player.getHp() + " / " + ((int)player.getHpMax());
		graphics.setColor(Color.red);
		graphics.setFont (new Font("TimesRoman", Font.BOLD, 40));
		graphics.drawString(playerHp, (21 * caseSize) + 30, 150);
	}
	
	public void drawHudTop(ApplicationContext context, Graphics2D graphics, float width, int caseSize) {
		graphics.setColor(Color.gray);
		graphics.fillRect(0, 0, (int) width, caseSize);
	}
	
	public void drawTimeBar(ApplicationContext context, Graphics2D graphics) {
		graphics.setColor(Color.LIGHT_GRAY);
		graphics.fillRect(10, 10, 400, 20);
		graphics.setColor(Color.GREEN);
		graphics.fillRect(15, 15, (int)(390 * timeData.timeFraction()), 10);
		graphics.setFont(new Font("TimesRoman", Font.BOLD, 20));
		graphics.setColor(Color.BLACK);
		graphics.drawString(gameData.getLoopCount() + " loops", 420, 30);
	}
	
	public void drawHud(ApplicationContext context, Graphics2D graphics, float width, int caseSize) {
		drawHudRight(context, graphics, width, caseSize);
		drawHudTop(context, graphics, width, caseSize);
		drawTimeBar(context, graphics);
	}
	
	public void drawDeck(ApplicationContext context, Graphics2D graphics, int caseSize) {
		for (int i = 0; i < player.deckSize(); i++) {
			Path path = Path.of(player.deck().getCard(i).sprite());
			drawImage(context, graphics, (int)(i * caseSize * 1.62), (int)(12 * caseSize + caseSize * 1.25), path, -1, -1);
		}	
	}
	
	public void drawScreen(ApplicationContext context) {
		float width = getScreenWidth(context);
		int caseSize = (int) ((60 * width) / 1536);
		
		context.renderFrame(graphics -> {
			drawMap(context, graphics, caseSize);
			drawHud(context, graphics, width, caseSize);
			drawPlayer(context, graphics, caseSize);
			drawDeck(context, graphics, caseSize);
		});
	}
}
