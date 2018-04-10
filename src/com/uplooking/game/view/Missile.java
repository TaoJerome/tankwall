package com.uplooking.game.view;

import java.awt.Graphics;

import com.uplooking.game.util.GameUtil;
import com.uplooking.game.view.Tank.Direction;
import com.uplooking.game.view.core.BaseUnit;

public class Missile extends BaseUnit implements Runnable {

	private Tank tank;				//子弹的发射者
	private Thread thread;
	
	public Tank getTank(){
		return tank;
	}
	
	public Missile(Tank tank, MainFrame mainview) {
		this.setOpaque(false);
		this.mainview = mainview;
		this.tank = tank;
		this.w = MIS_WIDTH;
		this.h = MIS_HEIGHT;
		this.direct = tank.getDirect();
		this.life = 1;
		if ( direct == Direction.L ){
			img = GameUtil.getImage("com/uplooking/game/images/missileL.gif");
			h = 5;
		} else if ( direct == Direction.R ){
			img = GameUtil.getImage("com/uplooking/game/images/missileR.gif");
			h = 5;
		} else if ( direct == Direction.D ){
			img = GameUtil.getImage("com/uplooking/game/images/missileD.gif");
		} else if ( direct == Direction.U ){
			img = GameUtil.getImage("com/uplooking/game/images/missileU.gif");
		}
		thread = new Thread(this);
		thread.start();
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		g.drawImage(img, 0, 0, w, h, this);
	}
	
	@Override
	public void run() {
		try {
			int step = Integer.parseInt(GameUtil.getProperty("missle.move.step"));		//子弹每次飞行距离
			int mt = Integer.parseInt(GameUtil.getProperty("missle.move.time"));		//子弹每次飞行间隔时间
			while ( life > 0 ){
				Thread.sleep(mt);
				if ( direct == Direction.L ){
					updateMissileLocation(- step, 0);
				} else if ( direct == Direction.R ){
					updateMissileLocation( step, 0);
				} else if ( direct == Direction.D ){
					updateMissileLocation( 0, step);
				} else if ( direct == Direction.U ){
					updateMissileLocation( 0, - step);
				}
			}
			this.setVisible(false);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
