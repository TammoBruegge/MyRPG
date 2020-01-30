package de.neuromechanics;

import java.awt.Graphics;

public class Level {
	private TileSet[] ts;
	private int sizeX, sizeY;
	private int[][][] tileMap;
	private Game game;

	public Level(Game game, String[] path, TileSet[] ts) {
		  this.game = game;
		  this.ts = ts;
		  String[][] tokens = new String[ts.length][];
		  for(int i = 0; i < ts.length; i++) {
		    String file = Utils.loadFileAsString(path[i]);
		    tokens[i] = file.split("\\s+");
		  }
		  sizeX = Utils.parseInt(tokens[0][0]);
		  sizeY = Utils.parseInt(tokens[0][1]);
		  tileMap = new int[ts.length][sizeX][sizeY];
		  for(int z = 0; z < ts.length; z++) {
		    int i = 2;
		    for(int y = 0; y < sizeY; y++){
		      for(int x = 0; x < sizeX; x++){
		        tileMap[z][y][x] = Utils.parseInt(tokens[z][i++]);
		      }
		    }
		  }
		}

	public void render(Graphics g){
		  r(g, tileMap[0], ts[0]);
		  r(g, tileMap[1], ts[1]);
		}
		public void renderZ(Graphics g) {
		  r(g, tileMap[2], ts[2]);
		}
		private void r(Graphics g, int[][] tm, TileSet ts) {
		  int xStart = Math.max(0, game.getGameCamera().getxOffset() / TileSet.TILEWIDTH);
		  int xEnd = Math.min(sizeX, (game.getGameCamera().getxOffset() + Game.SCREEN_WIDTH) / TileSet.TILEWIDTH + 1);
		  int yStart = Math.max(0, game.getGameCamera().getyOffset() / TileSet.TILEHEIGHT);;
		  int yEnd = Math.min(sizeY, (game.getGameCamera().getyOffset() + Game.SCREEN_HEIGHT) / TileSet.TILEHEIGHT + 1);
		  for(int tileY = yStart; tileY < yEnd; tileY++){
		    for(int tileX = xStart; tileX < xEnd; tileX++){
		      if(tm[tileY][tileX] == -1) continue;
		      ts.renderTile(g, tm[tileY][tileX], tileX * TileSet.TILEWIDTH - game.getGameCamera().getxOffset(),
		            tileY * TileSet.TILEHEIGHT - game.getGameCamera().getyOffset());
		    }
		  }
		}
		

		public int[][] getTilesTouched(Creature player) {
			  int[][] ret = new int[ts.length][2];
			  int numX = (player.entityX + Player.MARGIN_HORIZ) / player.width;
			  int numY = (player.entityY + player.height - Player.MARGIN_VERT) / player.height;
			  for(int i = 0; i < 2; i++) {
			    for(int z = 0; z < ts.length; z++) {
			      ret[z][i] = tileMap[z][numY][numX];
			      if(ts[z].hs.contains(ret[z][i])) {
			        ret[z][i] <<= 16;
			      }
			    }
			    numX = (player.entityX + player.width - Player.MARGIN_HORIZ) / player.width;
			  }
			  return ret;
			}
		
	public int getSizeX() {
		return sizeX;
	}
	public int getSizeY() {
		return sizeY;
	}

}