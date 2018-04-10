package com.uplooking.game.view;

import java.awt.Graphics;

import com.uplooking.game.util.GameUtil;
import com.uplooking.game.view.core.BaseUnit;

public class Yaobao extends BaseUnit implements Runnable {
	private int waittime = 10000;
	private int mylife ;
	
	public Yaobao(MainFrame mainview) {
		mylife = Integer.parseInt(GameUtil.getProperty("yaobao.life"));
		life = mylife;
		this.mainview = mainview;
		w = YB_WIDTH;
		h = YB_HEIGHT;
		img = GameUtil.getImage("com/uplooking/game/images/cy.gif");
		waittime = Integer.parseInt(GameUtil.getProperty("yaobao.show.time"));
		this.setOpaque(false);
		new Thread(this).start();
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		g.drawImage(img, 0, 0, w, h, this);
	}

	@Override
	public void run() {
		
		try {
			while ( !mainview.isover() ){
				Thread.sleep(waittime);
				life = mylife;
				int x  = (int)(Math.random()* 500 + 80);
				int y  = (int)(Math.random()* 400 + 80);
				setBounds(x,y , w, h);
				setVisible(true);
				Thread.sleep(6000);
				life = 0;
				setVisible(false);
			}
			this.setLife(0);
			this.setVisible(false);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	

}
