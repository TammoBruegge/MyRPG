package de.neuromechanics;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashSet;

import javax.imageio.ImageIO;

public class TileSet {
  public static final int TILEWIDTH = 64, TILEHEIGHT = 64;
  private BufferedImage[] tiles;
  private int sizeX;
  private int sizeY;

  @SuppressWarnings("rawtypes")
  public HashSet hs;

  public TileSet(String path, int sizeX, int sizeY, int border, @SuppressWarnings("rawtypes") HashSet hs) {
    this.hs = hs;
    this.sizeX = sizeX;
    this.sizeY = sizeY;
    tiles = new BufferedImage[sizeX*sizeY];
    BufferedImage tileSet;
    try {
      tileSet = ImageIO.read(TileSet.class.getResource(path));
    } catch (IOException e) {
      e.printStackTrace();
      return;
    }
    int i = 0;
    for(int y = 0; y < sizeY; y++) {
      for(int x = 0; x < sizeX; x++) {
         tiles[i++] = tileSet.getSubimage(x * (TILEWIDTH + border), y * (TILEHEIGHT + border), TILEWIDTH, TILEHEIGHT);
      }
    }
  }
  public void renderTile(Graphics g, int tileNum, int x, int y){
    g.drawImage(tiles[tileNum], x, y, TILEWIDTH, TILEHEIGHT, null);
  }
}