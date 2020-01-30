package de.neuromechanics;

import java.awt.image.BufferedImage;

public abstract class Item extends Entity {
	protected int weight;
	protected int value;
	protected boolean stackable;

	public Item(String name, BufferedImage image, int x, int y, int width, int height, int weight, int value, boolean stackable) {
		super(name, image, x, y, width, height);
		this.weight = weight;
		this.value = value;
		this.stackable = stackable;
	}
}