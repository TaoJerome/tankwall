package com.uplooking.game.view;

import java.awt.Color;
import java.awt.Graphics;

import com.uplooking.game.view.core.BaseUnit;

public class Blood extends BaseUnit {
	
	private Thread thread;
	
	public Blood(int life, MainFrame mainview) {
		this.mainview = mainview;
		this.life = life;
		this.w = BaseUnit.TANK_WIDTH;
		this.h = 4;
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		double xx = life / 100.0;
		w = (int) (xx * 35);
		if ( life == 100 ){
			g.setColor(Color.GREEN);
		} else if ( life < 40 ){
			g.setColor(Color.RED);
		} else if ( life < 60 ){
			g.setColor(Color.ORANGE);
		} else if ( life < 80 ){
			g.setColor(Color.YELLOW);
		} else if ( life < 100 ){
			g.setColor(Color.CYAN);
		}
		g.fill3DRect(0, 0, w, h, true);
	}
	

}
