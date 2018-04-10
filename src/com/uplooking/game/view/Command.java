package com.uplooking.game.view;

import java.awt.Graphics;

import com.uplooking.game.util.GameUtil;
import com.uplooking.game.view.core.BaseUnit;

public class Command extends BaseUnit {
	
	public Command(int life) {
		this.life = life;
		this.w = BaseUnit.CMD_WIDTH;
		this.h = BaseUnit.CMD_HEIGHT;
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		if ( life > 0 ){
			img = GameUtil.getImage("com/uplooking/game/images/hero.gif");
		} else {
			img = GameUtil.getImage("com/uplooking/game/images/die.png");
		}
		g.drawImage(img, 0, 0, CMD_WIDTH, CMD_HEIGHT, this);
	}

}
