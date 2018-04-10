package com.uplooking.game.view;

import com.uplooking.game.util.GameUtil;
import com.uplooking.game.view.core.BaseUnit;

public class Tank extends BaseUnit {
	protected boolean b;
	public enum Type {
		D, W;
	}
	public enum Direction {
		U, D, L , R;
	}
	
	protected Type type;				//类别
	
	protected Thread thread;			//控制坦克的线程
	
	
	public Type getType(){
		return this.type;
	}
	
	public boolean isHero(){
		return b;
	}
	
	public void fire() { // 开火的行为
		Missile m = new Missile(this, mainview);
		mainview.addMissile(m);
		int mx = getX(), my = getY();
		if ( direct == Direction.L ){
			mx -= 11;
			my += 14;
		} else if ( direct == Direction.R ){
			mx += 34;
			my += 16;
		} else if ( direct == Direction.D ){
			mx += 10;
			my += 30;
		} else if ( direct == Direction.U ){
			mx += 13;
			my -= 10;
		} 
		m.setBounds(mx , my, m.getThisw(), m.getThish());
	}
	
	public void updateImage(){
		if ( type == Type.D ){
			if ( direct == direct.L ){
				if ( b ) {
					img = GameUtil.getImage("com/uplooking/game/images/dL.gif");
				} else {
					img = GameUtil.getImage("com/uplooking/game/images/tankL.gif");
				}
			} else if ( direct == direct.R ){
				if ( b ) {
					img = GameUtil.getImage("com/uplooking/game/images/dR.gif");
				} else {
					img = GameUtil.getImage("com/uplooking/game/images/tankR.gif");
				}
			} else if ( direct == direct.D ){
				if ( b ) {
					img = GameUtil.getImage("com/uplooking/game/images/dD.gif");
				} else {
					img = GameUtil.getImage("com/uplooking/game/images/tankD.gif");
				}
			} else if ( direct == direct.U ){
				if ( b ) {
					img = GameUtil.getImage("com/uplooking/game/images/dU.gif");
				} else {
					img = GameUtil.getImage("com/uplooking/game/images/tankU.gif");
				}
			}
		} else {
			if ( direct == direct.L ){
				img = GameUtil.getImage("com/uplooking/game/images/tL.gif");
			} else if ( direct == direct.R ){
				img = GameUtil.getImage("com/uplooking/game/images/tR.gif");
			} else if ( direct == direct.D ){
				img = GameUtil.getImage("com/uplooking/game/images/tD.gif");
			} else if ( direct == direct.U ){
				img = GameUtil.getImage("com/uplooking/game/images/tU.gif");
			}
		}
	}
	
	public void boom(){			//爆炸效果
		int i = 0;
		try {
			int t = 100;
			while ( i < 11 ){
				String fn = i + ".gif";
				img = GameUtil.getImage("com/uplooking/game/images/" + fn);
				repaint();
				Thread.sleep(t);
				t -= 7;
				i ++;
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
