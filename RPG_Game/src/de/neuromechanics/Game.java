package de.neuromechanics;

import java.awt.Canvas;
import java.awt.Point;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.util.Arrays;
import java.util.HashSet;

public class Game implements Runnable {
	public static final int FPS = 60;
	public static final long maxLoopTime = 1000 / FPS;
	public static final int SCREEN_WIDTH = 1000;
	public static final int SCREEN_HEIGHT = 800;

	public Screen screen;
	Player player;
	Level level;
	KeyManager keyManager;
	private Camera gameCamera;
	BufferStrategy bs;
	Graphics g;
	
	private boolean level1Aktiv;
	private boolean level2Aktiv;
	boolean freshGame = true;

	public static void main(String[] arg) {
		Game game = new Game();
		new Thread(game).start();
	}
	
	public void level1() {
		if(!freshGame) {
			g.clearRect(0, 0, SCREEN_WIDTH, SCREEN_HEIGHT);
		}
		level1Aktiv = true;
		level2Aktiv = false;
		TileSet[] tileSet = new TileSet[3];
		
		// Ground layer tileset with blocking tiles
		HashSet hs = new HashSet(Arrays.asList(0, 1, 2, 12, 14, 24, 25, 26));
		tileSet[0] = new TileSet("/tiles/rpg.png", 12 /*sizeX*/, 12/*sizeY*/, 3 /*border*/, hs);
		
		
		// Second layer tileset with blocking tiles
		hs = new HashSet(Arrays.asList(108, 109, 110, 111, 92, 93, 94, 95, 76, 77, 78, 79, 160, 161,194,195));
		tileSet[1] = new TileSet("/tiles/tileb.png", 16, 16, 0, hs);
		
		// Transparent Z / foreground layer tileset, no blocking tiles
		tileSet[2] = new TileSet("/tiles/tileb.png", 16, 16, 0, hs);
		
		
		String[] paths = new String[3];
		paths[0] = "/level/level1.txt";
		paths[1] = "/level/level1a.txt";
		paths[2] = "/level/level1b.txt";
		level = new Level(this, paths, tileSet);
	}
	
	public void level2() {
		level1Aktiv = false;
		level2Aktiv = true;
		g.clearRect(0, 0, SCREEN_WIDTH, SCREEN_HEIGHT);;
		
		
		
		//Spieler teleportieren
		player.setEntityX(100);
		player.setEntityY(100);
		
		
		
		//Die 3 ebenen des Neuen Levels
		TileSet[] tileSet = new TileSet[3];
		
		// Ground layer tileset with blocking tiles
		HashSet hs = new HashSet(Arrays.asList());
		tileSet[0] = new TileSet("/tiles/tileb.png", 16 /*sizeX*/, 16/*sizeY*/, 0 /*border*/, hs);
		
		
		// Second layer tileset with blocking tiles
		hs = new HashSet(Arrays.asList(41, 58 ,42 ,56 ,57, 60,41,42,56,57,58));
		tileSet[1] = new TileSet("/tiles/tileb.png", 16, 16, 0, hs);
		
		// Transparent Z / foreground layer tileset, no blocking tiles
		tileSet[2] = new TileSet("/tiles/tileb.png", 16, 16, 0, hs);
		
		
		String[] paths = new String[3];
		paths[0] = "/level/level2.txt";
		paths[1] = "/level/level2a.txt";
		paths[2] = "/level/level2b.txt";
		level = new Level(this, paths, tileSet);
		player.setLevel(level);
	}

	
	public void run() {
		long timestamp;
		long oldTimestamp;

		screen = new Screen("Game", SCREEN_WIDTH, SCREEN_HEIGHT);
		keyManager = new KeyManager();
		screen.getFrame().addKeyListener(keyManager);
		level1();
		
		freshGame = false;
		SpriteSheet playerSprite = new SpriteSheet("/sprites/player.png", 3 /*moves*/, 4 /*directions*/, 64 /*width*/, 64 /*height*/);
		player = new Player(this, level, 100, 100, playerSprite);
		gameCamera = new Camera(level.getSizeX(), level.getSizeY());
		
		
		
		while(true) {
			oldTimestamp = System.currentTimeMillis();
			if(player.getEntityX() == 482 && !level2Aktiv) {
				level2();
				System.out.println("switch");
			}
			
			
			if(player.getEntityX() == 284 && player.getEntityY() == 386 && !level1Aktiv) {
				level1();
			}
			
			update();
			timestamp = System.currentTimeMillis();
			if(timestamp-oldTimestamp > maxLoopTime) {
				System.out.println("Wir sind zu spät!");
				continue;
			}
			render();
			System.out.println("X = " + player.entityX + "Y= " + player.entityY); // x = 482 und y = 230

			timestamp = System.currentTimeMillis();
		//	System.out.println(maxLoopTime + " : " + (timestamp-oldTimestamp));
			if(timestamp-oldTimestamp <= maxLoopTime) {
				try {
					Thread.sleep(maxLoopTime - (timestamp-oldTimestamp) );
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}
	void update() {
		keyManager.update();
		player.setMove(getInput());
		player.update();
	}
	void render() {
		  Canvas c = screen.getCanvas();
		  bs = c.getBufferStrategy();
		  if(bs == null){
		    screen.getCanvas().createBufferStrategy(3);
		    return;
		  }
		  g = bs.getDrawGraphics();
		  //Clear Screen
		  g.clearRect(0, 0, SCREEN_WIDTH, SCREEN_HEIGHT);
		  level.render(g);
		  player.render(g);
		  level.renderZ(g);
		  bs.show();
		  g.dispose();
		}
	
	
	private Point getInput(){
		int xMove = 0;
		int yMove = 0;
		if(keyManager.up)
			yMove = -1;
		if(keyManager.down)
			yMove = 1;
		if(keyManager.left)
			xMove = -1;
		if(keyManager.right)
			xMove = 1;
		return new Point(xMove, yMove);
	}
	public Camera getGameCamera(){
		return gameCamera;
	}
}