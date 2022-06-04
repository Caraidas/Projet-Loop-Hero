package graphics;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.geom.Point2D;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.nio.file.Path;
import java.util.ArrayList;

import collectable.Card;
import collectable.Oblivion;
import collectable.ZoneCard;
import data.GameData;
import data.GridPosition;
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
	private ApplicationContext context;

	double width = 0.0;
	double height = 0.0;
	int caseSize = 0;

	public View(Player player, TimeData timeData, GameData gameData) {
		this.player = player;
		this.timeData = timeData;
		this.gameData = gameData;
	}

	public void initContext(ApplicationContext context) {
		this.context = context;
		this.width = getScreenWidth();
		this.height = getScreenHeight();
		this.caseSize = (int) ((60 * width) / 1536);
	}

	private float getScreenWidth() {
		ScreenInfo screenInfo = context.getScreenInfo();
		return screenInfo.getWidth();
	}

	private float getScreenHeight() {
		ScreenInfo screenInfo = context.getScreenInfo();
		return screenInfo.getHeight();
	}

	public void drawPlayer(Graphics2D graphics) {
		ImageConstructor playerImage = new ImageConstructor(
				Path.of("ressources/Entities-Sprite/outOfBattle/Player.png"), (int) (caseSize * 0.5),
				(int) (caseSize * 0.5));
		
		int column = gameData.map().loop().get(player.position()).column();
		int line = gameData.map().loop().get(player.position()).line();
		
		drawImage(graphics, (int) ((column * caseSize) + (caseSize * 0.25)),
				(int) ((line * caseSize) + (caseSize * 1.25)), playerImage);
	}

	private void drawImage(Graphics2D graphics, int i, int j, ImageConstructor imgConstructor) {
		graphics.drawImage(imgConstructor.img(), imgConstructor.scaling(), i, j);
	}

	public void drawMonsters(Graphics2D graphics, int i, int j, Cell c) {
		int newI;
		int newJ;
		ArrayList<Monster> monsters = ((RoadCell) (c)).getEntities();
		for (int index = 0; index < monsters.size(); index++) {
			ImageConstructor monsterImage = new ImageConstructor(
					Path.of("ressources/Entities-Sprite/outOfBattle/monsters/" + monsters.get(index).getSprite() + ".png"),
					(int) (caseSize * 0.25), (int) (caseSize * 0.25));
			
			if (index == 4) {
				newI = (int)(i + (caseSize * 0.5) - 5);
				newJ = (int)(j + (caseSize * 0.5) - 5);
			} else {
				if (index % 2 != 0) {
					newI = (int) (i + (caseSize * 0.75)) - 5;
				} else {
					newI = i + 5;
				}

				if (index <= 1) {
					newJ = j + 5;
				} else {
					newJ = (int) (j + (caseSize * 0.75)) - 5;
				}
			}
			
			drawImage(graphics, newI, newJ, monsterImage);
		}
	}

	public void drawCell(Graphics2D graphics, int i, int j, Cell c) {

		if (c.card() == null) {
			graphics.setColor(Color.BLACK);
			graphics.fillRect(i, j, caseSize, caseSize);
		} else {
			ImageConstructor cellImage = new ImageConstructor(
					Path.of("ressources/Map-Sprite/Card-Sprites/" + c.sprite()), caseSize, caseSize);
			drawImage(graphics, i, j, cellImage);
		}

		if (c instanceof RoadCell) {
			
			ImageConstructor cellImage = new ImageConstructor(
					Path.of("ressources/Map-Sprite/Card-Sprites/Road"+((RoadCell)c).direction()+".png"), caseSize, caseSize);
			drawImage(graphics, i, j, cellImage);
			
			if (c.card() != null) {
				cellImage = new ImageConstructor(
						Path.of("ressources/Map-Sprite/Card-Sprites/" + c.sprite()), caseSize, caseSize);
				drawImage(graphics, i, j, cellImage);
			}

			drawMonsters(graphics, i, j, c);

		}
		
		if (c.inZone()) {
			ImageConstructor image = new ImageConstructor(
					Path.of("ressources/Map-Sprite/zone.png"), caseSize, caseSize);
			drawImage(graphics, i, j, image);
		}
	}

	public void drawMap(Graphics2D graphics) {
		graphics.setColor(Color.black);
		for (int i = 0; i < gameData.map().lines(); i++) {
			for (int j = 0; j < gameData.map().columns(); j++) {
				graphics.fillRect(j * caseSize, i * caseSize + caseSize, caseSize, caseSize);
				drawCell(graphics, j * caseSize, i * caseSize + caseSize, gameData.map().getCell(i, j));
			}
		}
	}

	public void drawHealthBar(Graphics2D graphics) {
		int HudWidth = (int) (width - (21 * caseSize));
		graphics.setColor(Color.DARK_GRAY);
		graphics.fillRect((21 * caseSize) + 20, 120, HudWidth - 35, 20);

		graphics.setColor(Color.decode("#A5B750"));
		double hpWidth = ((player.getHp() / player.getHpMax())) * (HudWidth - 45);
		graphics.fillRect((21 * caseSize) + 25, 125, (int) hpWidth, 10);
	}

	public void drawHudRight(Graphics2D graphics) {
		int HudWidth = (int) (width - (21 * caseSize));
		ImageConstructor image = new ImageConstructor(Path.of("ressources/Utility-Sprite/HUD/HudRight.png"), HudWidth, height);
		drawImage(graphics, (21 * caseSize), 0, image);

		String playerHp = (int) (player.getHp()) + " / " + (int) (player.getHpMax());
		graphics.setColor(Color.decode("#A5B750"));
		graphics.setFont(new Font("TimesRoman", Font.BOLD, 30));
		graphics.drawString(playerHp, (21 * caseSize) + 75, 115);

		drawHealthBar(graphics);

		graphics.setColor(Color.black);
		graphics.setFont(new Font("TimesRoman", Font.BOLD, 20));

		drawItems(graphics);
		drawInventory(graphics);

		if (gameData.selectedItem() != -1) {
			drawItemStats(graphics);
		} else {
			drawPlayerStats(graphics);
		}
	}

	public void drawPlayerStats(Graphics2D graphics) {
		int HudWidth = (int) (width - (21 * caseSize));

		int i = 6;
		graphics.setColor(Color.white);
		for (String s : player.basicStats().keySet()) {
			if (s != "hp" && s != "hpMax") {
				graphics.drawString(s + " : " + (round((player.basicStats().get(s)), 1)), (21 * caseSize) + 50,
						(int) (550 + (20 * i)));
				i++;
			}
		}

		graphics.setFont(new Font("TimesRoman", Font.BOLD, 21));
		String playerDamage = "damage : " + player.damageString();
		graphics.drawString(playerDamage, (21 * caseSize) + 50, (int) (height - 215));

		graphics.setFont(new Font("TimesRoman", Font.BOLD, 35));
		graphics.drawString("- Statistiques -", (21 * caseSize) + 30, (int) (height - 270));
	}

	public void drawItemStats(Graphics2D graphics) {
		int HudWidth = (int) (width - (21 * caseSize));

		graphics.setColor(Color.white);
		ImageConstructor image = new ImageConstructor(Path.of("ressources/Utility-Sprite/HUD/Button.png"), 175, 50);
		drawImage(graphics, (21 * caseSize) + 50, (int) (height - (height / 7)), image);
		graphics.drawString("Equiper", (21 * caseSize) + 100, (int) (height - (height / 7)) + 30);

		// stats
		graphics.setFont(new Font("TimesRoman", Font.BOLD, 25));
		int compt = 2;
		for (String stat : player.items().get(gameData.selectedItem()).stats().keySet()) {
			String line = stat + " : " + round(player.items().get(gameData.selectedItem()).stats().get(stat), 1);
			graphics.drawString(line, (21 * caseSize) + 55, (int) (height - (height / 3)) + (compt * 20));
			compt++;
		}
	}

	public boolean deposedItem(Point2D.Float location) {
		return (location.x >= (21 * caseSize) - 50 && location.x < width - 50
				&& location.y >= (int) (height - (height / 7)) && location.y < height)
				&& (gameData.selectedItem() != -1) && (player.items().get(gameData.selectedItem()) != null);
	}

	public void drawItems(Graphics2D graphics) {
		int rarity;
		ImageConstructor image = new ImageConstructor(Path.of("ressources/Utility-Sprite/Gray.png"),
				(double) (caseSize - 1), (double) (caseSize - 1));

		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 4; j++) {
				if (!(player.items().get((i * 4) + j) == null)) {
					rarity = player.items().get((i * 4) + j).stats().size();

					if (rarity == 2) {
						image = new ImageConstructor(Path.of("ressources/Utility-Sprite/Blue.png"),
								(double) (caseSize - 1), (double) (caseSize - 1));
					} else if (rarity == 3) {
						image = new ImageConstructor(Path.of("ressources/Utility-Sprite/Yellow.png"),
								(double) (caseSize - 1), (double) (caseSize - 1));
					} else if (rarity == 4) {
						image = new ImageConstructor(Path.of("ressources/Utility-Sprite/Orange.png"),
								(double) (caseSize - 1), (double) (caseSize - 1));
					} else if (rarity == 1) {
						image = new ImageConstructor(Path.of("ressources/Utility-Sprite/Gray.png"),
								(double) (caseSize - 1), (double) (caseSize - 1));
					}
				} else {
					image = new ImageConstructor(Path.of("ressources/Utility-Sprite/Gray.png"),
							(double) (caseSize - 1), (double) (caseSize - 1));
				}

				drawImage(graphics, (21 * caseSize + (j * caseSize)) + 20, (int) (200 + (caseSize * i)), image);
				if (!(player.items().get((i * 4) + j) == null)) {
					ImageConstructor itemImage = new ImageConstructor(Path.of(player.items().get((i * 4) + j).sprite()),
							(double) (caseSize - 1), (double) (caseSize - 1));
					drawImage(graphics, (21 * caseSize + (j * caseSize)) + 20, (int) (200 + (caseSize * i)), itemImage);
				}
			}
		}
	}

	public ImageConstructor determinateColor(int rarity) {
		if (rarity == 1) {
			return new ImageConstructor(Path.of("ressources/Utility-Sprite/Gray.png"), (double) (caseSize - 1),
					(double) (caseSize - 1));
		} else if (rarity == 2) {
			return new ImageConstructor(Path.of("ressources/Utility-Sprite/Blue.png"), (double) (caseSize - 1),
					(double) (caseSize - 1));
		} else if (rarity == 3) {
			return new ImageConstructor(Path.of("ressources/Utility-Sprite/Yellow.png"), (double) (caseSize - 1),
					(double) (caseSize - 1));
		} else {
			return new ImageConstructor(Path.of("ressources/Utility-Sprite/Orange.png"), (double) (caseSize - 1),
					(double) (caseSize - 1));
		}
	}

	public void drawInventory(Graphics2D graphics) {
		int rarity;
		ImageConstructor image = new ImageConstructor(Path.of("ressources/Utility-Sprite/Gray.png"),
				(double) (caseSize - 1), (double) (caseSize - 1));
		;
		for (int i = 0; i < 4; i++) {
			drawImage(graphics, (21 * caseSize + (i * caseSize)) + 20, 400 + caseSize, image);
		}

		if (player.inventory().hasWeapon()) {
			rarity = player.inventory().weapon().stats().size();
			ImageConstructor bg = determinateColor(rarity);
			ImageConstructor itemImage = new ImageConstructor(Path.of(player.inventory().weapon().sprite()),
					(double) (caseSize), (double) (caseSize));
			drawImage(graphics, (21 * caseSize + (0 * caseSize)) + 20, 400 + caseSize, bg);
			drawImage(graphics, (21 * caseSize + (0 * caseSize)) + 20, 400 + caseSize, itemImage);
		}

		if (player.inventory().hasArmor()) {
			rarity = player.inventory().armor().stats().size();
			ImageConstructor bg = determinateColor(rarity);
			ImageConstructor itemImage = new ImageConstructor(Path.of(player.inventory().armor().sprite()),
					(double) (caseSize), (double) (caseSize));
			drawImage(graphics, (21 * caseSize + (1 * caseSize)) + 20, 400 + caseSize, bg);
			drawImage(graphics, (21 * caseSize + (1 * caseSize)) + 20, 400 + caseSize, itemImage);
		}

		if (player.inventory().hasShield()) {
			rarity = player.inventory().shield().stats().size();
			ImageConstructor bg = determinateColor(rarity);
			ImageConstructor itemImage = new ImageConstructor(Path.of(player.inventory().shield().sprite()),
					(double) (caseSize), (double) (caseSize));
			drawImage(graphics, (21 * caseSize + (2 * caseSize)) + 20, 400 + caseSize, bg);
			drawImage(graphics, (21 * caseSize + (2 * caseSize)) + 20, 400 + caseSize, itemImage);
		}

		if (player.inventory().hasRing()) {
			rarity = player.inventory().ring().stats().size();
			ImageConstructor bg = determinateColor(rarity);
			ImageConstructor itemImage = new ImageConstructor(Path.of(player.inventory().ring().sprite()),
					(double) (caseSize), (double) (caseSize));
			drawImage(graphics, (21 * caseSize + (3 * caseSize)) + 20, 400 + caseSize, bg);
			drawImage(graphics, (21 * caseSize + (3 * caseSize)) + 20, 400 + caseSize, itemImage);
		}
	}

	public void drawHudTop(Graphics2D graphics) {
		graphics.setColor(Color.gray);
		graphics.fillRect(0, 0, (int) width, caseSize);
	}

	public void drawTimeBar(Graphics2D graphics) {
		graphics.setColor(Color.DARK_GRAY);
		graphics.fillRect(10, 10, 400, 20);
		graphics.setColor(Color.decode("#A5B750"));
		graphics.fillRect(15, 15, (int) (390 * timeData.timeFraction()), 10);
		graphics.setFont(new Font("TimesRoman", Font.BOLD, 20));
		graphics.setColor(Color.BLACK);
		graphics.drawString(gameData.getLoopCount() + " loops", 420, 27);
		graphics.drawString("day " + timeData.dayCount(), 500, 27);
	}

	public void drawHud(Graphics2D graphics) {
		drawHudTop(graphics);
		drawHudRight(graphics);
		drawTimeBar(graphics);
	}

	public void drawDeck(Graphics2D graphics) {
		for (int i = 0; i < player.deckSize(); i++) {
			Path path = Path.of("ressources/Card-Sprite/" + player.deck().getCard(i).sprite());
			ImageConstructor cardImage = new ImageConstructor(path, -1, -1);
			if (i == gameData.selectedCard()) {
				drawImage(graphics, (int) (i * caseSize * 1.62), (int) (12 * caseSize), cardImage);
			} else {
				drawImage(graphics, (int) (i * caseSize * 1.62), (int) (12 * caseSize + caseSize * 1.25), cardImage);
			}
		}
	}

	public boolean deposedCard(Point2D.Float location) {

		if (!(location.x >= 0 * caseSize && location.x < 21 * caseSize && location.y >= caseSize
				&& location.y < 12 * caseSize + caseSize && gameData.selectedCard() != -1)) {
			return false;
		}

		Card card = player.selectedCard(gameData.selectedCard());
		if (!(card.acceptCardState(toCellPos(location), gameData))) {
			return false;
		}

		if (card instanceof Oblivion) { 
			return true;
		}

		if (gameData.map().getCell(toCellPos(location)).card() != null || gameData.map()
				.getCell(toCellPos(location)) == gameData.map().getCell(gameData.map().loop().get(0))) {
			return false;
		}

		return true;
	}

	public void drawCliquableCells(Graphics2D graphics) {
		ImageConstructor selectable = new ImageConstructor(Path.of("ressources/Map-Sprite/selectable.png"), caseSize,
				caseSize);
		if (gameData.selectedCard() != -1) {
			for (int i = 0; i < gameData.map().lines(); i++) {
				for (int j = 0; j < gameData.map().columns(); j++) {		
					if (player.deck().getCard(gameData.selectedCard()).acceptCardState(new GridPosition(i, j), gameData)) {
						drawImage(graphics, j * caseSize, i * caseSize + caseSize, selectable);
					} 
				}
			}
		}
	}

	public void drawBattle(Graphics2D graphics) {
		
		ImageConstructor image = new ImageConstructor(Path.of("ressources/Utility-Sprite/WindowFight.png"), -2.7, -2.7);
		drawImage(graphics, (int)(width / 5), (int) (height / 27), image);
		drawMonstersInBattle(graphics, (RoadCell) (gameData.map().getCell(gameData.map().getPlayerPos(player))));
	}

	public void drawMonstersInBattle(Graphics2D graphics, RoadCell c) {
		int i = 0;
		for (Monster m : c.getEntities()) {
			ImageConstructor mnstrImage = new ImageConstructor(
					Path.of("ressources/Entities-Sprite/InBattle/monsters/" + m.getSprite() +  m.battleState() + ".png"), -0.15, -0.15);
			drawImageInBattle(graphics, i, mnstrImage);
			drawMonsterStat(graphics, i, m);
			i++;
		}

		ImageConstructor pImage = new ImageConstructor(
				Path.of("ressources/Entities-Sprite/InBattle/" + player.getSprite() + player.battleState() + ".png"), -0.2, -0.2);
		drawImageInBattle(graphics, -1, pImage);
	}

	public void drawMonsterStat(Graphics2D graphics, int i, Monster m) {
		int index = 1;
		int x, y;
		
		if (i != 4) {
			x = (int) (3 * (width / 6)) + 3 * caseSize;
			y = (int) (i * 2 * caseSize + (height / 6) + caseSize);
		} else {
			x = (int) (3 * (width / 7));
			y = (int)(caseSize + (height / 6));
		}
		
		for (String stat : m.basicStats().keySet()) {
			if (stat != "hpMax" && stat != "undead") {
				if (stat == "hp") {
					String s = stat + " : " + round(m.basicStats().get(stat), 2) + "/" + m.getHpMax();
					graphics.setColor(Color.red);
					graphics.setFont(new Font("TimesRoman", Font.BOLD, 20));
					graphics.drawString(s, x, y);
				} else {
					String s = stat + " : " + round(m.basicStats().get(stat), 2);
					graphics.setColor(Color.white);
					graphics.setFont(new Font("TimesRoman", Font.BOLD, 20));
					graphics.drawString(s, x, y + index * 20);
					index++;
				}

			}
		}
		String s = "Strength : " + m.strength();
		graphics.setColor(Color.white);
		graphics.setFont(new Font("TimesRoman", Font.BOLD, 20));
		graphics.drawString(s, x, y + index * 20);
	}

	public void drawImageInBattle(Graphics2D graphics, int i, ImageConstructor imgConstructor) {
		if (i == -1) {
			graphics.drawImage(imgConstructor.img(), imgConstructor.scaling(), (int) (1.5 * (width / 6)),
					(int) (height / 2));
		} else if (i < 4) {
			graphics.drawImage(imgConstructor.img(), imgConstructor.scaling(), (int) (3 * (width / 6)),
					(int) (i * 2 * caseSize + (height / 6) + caseSize));
		} else {
			graphics.drawImage(imgConstructor.img(), imgConstructor.scaling(), (int)(2 * (width / 6)),
					(int)((height / 6) + caseSize));
		}
	}
	
	public void drawIntro(Graphics2D graphics) {
		ImageConstructor image = new ImageConstructor(Path.of("ressources/Utility-Sprite/bg.png"), width, height);
		drawImage(graphics, 0, 0, image);
		
		image = new ImageConstructor(Path.of("ressources/Utility-Sprite/introMenu.png"), (2 * (width / 10)), height);
		drawImage(graphics, (int)(4 * (width / 10)), 0, image);
		
		image = new ImageConstructor(Path.of("ressources/Utility-Sprite/HUD/Button.png"), -2, -2);
		drawImage(graphics, (int)(4 * (width / 10)) + 53, (int)(3 * (height /8)), image);
		
		image = new ImageConstructor(Path.of("ressources/Utility-Sprite/logo.png"), -1.5, -1.5);
		drawImage(graphics, (int)(4 * (width / 10) + 10), 30, image);
		
		graphics.setColor(Color.white);
		graphics.setFont(new Font("TimesRoman", Font.BOLD, 20));
		graphics.drawString("Commencer", (int)(4 * (width / 10)) + 100, (int)(3 * (height /8) + 23));
	}
	
	public void drawRessourceMenu(Graphics2D graphics) {
		int HudWidth = (int) (width - (21 * caseSize));
		ImageConstructor image = new ImageConstructor(Path.of("ressources/Utility-Sprite/WindowRessource.png"), HudWidth, height);
		drawImage(graphics, (21 * caseSize), 0, image);
		
		int i = 1;
		graphics.setColor(Color.white);
		for (String s : player.ressources().keySet()) {
			String line = "¤ " + s + " : " + player.ressources().get(s);
			graphics.drawString(line, (21 * caseSize) + 45, 200 + (i * 25));
			i++;
		}
		
		graphics.setFont(new Font("TimesRoman", Font.BOLD, 35));
		graphics.drawString("- Ressources -", (21 * caseSize) + 30, 180);
	}
	
	public void drawRessourceButton(Graphics2D graphics) {
		ImageConstructor image = new ImageConstructor(Path.of("ressources/Utility-Sprite/HUD/ButtonR.png"), -1, -1);
		drawImage(graphics, (20 * caseSize), 15, image);
	}

	public void drawScreen() {

		context.renderFrame(graphics -> {
			
			if (gameData.inGame()) {
				if (gameData.inBattle()) {
					drawHud(graphics);
					drawBattle(graphics);
				} else {
					drawMap(graphics);
					drawCliquableCells(graphics);
					drawHud(graphics);
					drawPlayer(graphics);
					drawDeck(graphics);
					drawRessourceButton(graphics);
					
					if (gameData.inRessourceMenu()) {
						drawRessourceMenu(graphics);
					}
				}
			} else {
				drawIntro(graphics);
			}
		});
	}

	public void blackScreen() {
		context.renderFrame(graphics -> {
			graphics.setColor(Color.black);
			graphics.fillRect(0, (int) (12 * caseSize + caseSize), (int) (13 * caseSize * 1.62), (int) height);
		});

	}

	public boolean clickedOnCards(Point2D.Float location) {
		return location.x >= 0 && location.x < (int) (player.deckSize() * caseSize * 1.62)
				&& location.y >= (int) (12 * caseSize + caseSize * 1.25) && location.y < height;
	}

	public boolean clickedOnItems(Point2D.Float location) {
		return (location.x >= (21 * caseSize + (0 * caseSize)) + 20 && location.x < width
				&& location.y >= (int) (200 + (caseSize * 0)) && location.y < (int) (200 + (caseSize * 3)))
				&& player.items().get(toItemPos(location)) != null;
	}

	public int toCardPos(Point2D.Float location) {
		return (int) (location.x / (caseSize * 1.62));
	}

	public int toItemPos(Point2D.Float location) {
		int x = (int) (location.x - ((21 * caseSize) + 20)) / (caseSize);
		int y = (int) (location.y - (int) (200)) / (caseSize);
		return (int) (x + (y * 4));
	}

	public GridPosition toCellPos(Point2D.Float location) {
		return new GridPosition((int) ((location.y / caseSize) - 1), (int) (location.x / caseSize));
	}
	  
	public boolean clickedOnPlay(Point2D.Float location) {
		return (location.x >= ((4 * (width / 10)) + 53) && location.x < (6 * (width / 10) - 50)
				&& location.y >= (3 * (height /8)) && location.y < (3 * (height /8) + 30));
	}
	
	public boolean clickedOnRessources(Point2D.Float location) {
		return (location.x >= (20 * caseSize) && location.x < (20 * caseSize) + 100
				&& location.y >= 15 && location.y < 60);
	}

	public static double round(double value, int places) {
		if (places < 0)
			throw new IllegalArgumentException();

		BigDecimal bd = BigDecimal.valueOf(value);
		bd = bd.setScale(places, RoundingMode.HALF_UP);
		return bd.doubleValue();
	}
}
