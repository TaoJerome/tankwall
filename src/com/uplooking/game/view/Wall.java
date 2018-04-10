package com.uplooking.game.view;

import java.awt.Graphics;

import com.uplooking.game.view.core.BaseUnit;

public class Wall extends BaseUnit {
	
	public Wall(int life) {
		this.life = life;
		this.w = BaseUnit.WALL_WIDTH;
		this.h = BaseUnit.WALL_HEIGHT;
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		if ( life == 1 ){
			g.setColor(C1);
		} else if ( life == 2 ){
			g.setColor(C2);
		} else if ( life == 3 ){
			g.setColor(C3);
		} 
		g.fill3DRect(0, 0,  WALL_WIDTH, WALL_HEIGHT, true);
	}

}
