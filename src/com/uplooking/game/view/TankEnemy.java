package com.uplooking.game.view;

import java.awt.Graphics;

import com.uplooking.game.util.GameUtil;
import com.uplooking.game.view.Tank.Direction;

public class TankEnemy extends Tank implements Runnable {
	
	// 敌军坦克的类型
	public TankEnemy(Type type, MainFrame mainview) {
		this.type = type;
		int k = (int)(Math.random() * 10);
		int heros = Integer.parseInt(GameUtil.getProperty("enemy.hero.num"));
		b = k % heros == 0;
		this.mainview = mainview;
		this.direct = Direction.D;
		this.img = GameUtil.getImage("com/uplooking/game/images/dD.gif");
		this.w = TANK_WIDTH;
		this.h = TANK_HEIGHT;
		this.life = Integer.parseInt(GameUtil.getProperty("tank.enemy.blood")); // 生命值
		this.setOpaque(false);
		this.thread = new Thread(this);// 初始化线程
		thread.start(); // 自动 调用run方法，如果run方法运行完，则线程自然死亡
		blood = new Blood(life, mainview);
	}

	

	@Override
	protected void paintComponent(Graphics g) {
		if ( life > 0 )
			updateImage();
		g.drawImage(img, 0, 0, w, h, this);
	}

	@Override
	public void run() {
		try {
			Thread.sleep(1000);
			int num = Integer.parseInt(GameUtil.getProperty("enemy.direction.change"));
			int step = Integer.parseInt(GameUtil.getProperty("tank.move.step"));
			int hz = Integer.parseInt(GameUtil.getProperty("enemy.fire.time"));
			mainview.addBlood(blood);
			blood.setBounds(getX(), getY() - blood.getThish(), blood.getThisw(), blood.getThish());
			while (life > 0) {
				changeAndMove(num, step);
				int n = (int)(Math.random()*100);
				if ( n % hz == 0 )
					fire();
				Thread.sleep(Integer.parseInt(GameUtil.getProperty("enemy.move.time")));
			}
			//先爆炸特效一下
			blood.setVisible(false);
			boom();
			setVisible(false);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	private void changeAndMove(int num, int step) {
		int n = (int) (Math.random() * 100);
		if (n % num == 0) {
			int m = (int) (Math.random() * 4); // 0 = L 1 = U 2 = R 3 = D
			if (m == 0) {
				setDirect(Direction.L);
			} else if (m == 1) {
				setDirect(Direction.U);
			} else if (m == 2) {
				setDirect(Direction.R);
			} else if (m == 3) {
				setDirect(Direction.D);
			}
		}
		doMove(step);
	}

	private void doMove(int step) {
		// 上面是方向 改变，下面开始改变位置
		if (direct == Direction.L) {
			updateLocation(-step, 0);
		} else if (direct == Direction.R) {
			updateLocation(step, 0);

		} else if (direct == Direction.U) {
			updateLocation(0, - step);

		} else if (direct == Direction.D) {
			updateLocation(0, step);
		}
		blood.setBounds(getX(), getY() - blood.getThish(), blood.getThisw(), blood.getThish());
	}
	
}
