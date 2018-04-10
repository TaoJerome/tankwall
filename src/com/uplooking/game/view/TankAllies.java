package com.uplooking.game.view;

import java.awt.Graphics;

import com.uplooking.game.util.GameUtil;
import com.uplooking.game.view.core.BaseUnit;

public class TankAllies extends Tank  implements Runnable {
	
	private boolean left, right, up, down;		//�ֱ��ʾ�Ƿ������������Ҽ�
	//�о�̹�˵�����
	public TankAllies(Type type, MainFrame mainview) {
		this.setOpaque(false);
		this.mainview = mainview;
		this.type = type;
		this.direct = Direction.U;
		this.img = GameUtil.getImage("com/uplooking/game/images/tU.gif");
		this.w = TANK_WIDTH;
		this.h = TANK_HEIGHT;
		this.life = Integer.parseInt(GameUtil.getProperty("tank.self.blood"));		//����ֵ
		this.thread = new Thread(this);
		thread.start();
		blood = new Blood(life, mainview);
	}
	
//	public void fire(){	//�������Ϊ
//		Missile m = new Missile(this, mainview);
//		mainview.addMissile(m);
//		int mx = getX(), my = getY();
//		if ( direct == Direction.L ){
//			mx -= 11;
//			my += 14;
//		} else if ( direct == Direction.R ){
//			mx += 34;
//			my += 16;
//		} else if ( direct == Direction.D ){
//			mx += 10;
//			my += 30;
//		} else if ( direct == Direction.U ){
//			mx += 13;
//			my -= 10;
//		} 
//		m.setBounds(mx , my, m.getThisw(), m.getThish());
//	}
	
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
			int speed = Integer.parseInt(GameUtil.getProperty("tank.move.time"));
			mainview.addBlood(blood);
			blood.setBounds(getX(), getY() - blood.getThish(), blood.getThisw(), blood.getThish());
			while ( life > 0 ){
				doMove();
				Thread.sleep(speed);
			}
			this.blood.setVisible(false);
			boom();
			this.setVisible(false);
			mainview.setGameStae(2);			//��ʾ����ʤ��
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	private void doMove(){
		int cx = getX();
		int cy = getY();
		oldx = cx;
		oldy = cy;
		int step = mainview.getStep();  //���������Ҫ����������������
		if ( left ){
			cx += -step;
		} else if ( right ){
			cx += step;
		} else if ( up ){
			cy += -step;
		} else if ( down ){
			cy += step;
		}
		this.setBounds(cx, cy, w, h);
		if ( mainview.check(this) ) {		//�����������ײ���ͻָ��ƶ�ǰ������
			setBounds(oldx, oldy, w, h);
		} else {
			blood.setBounds(cx, cy - blood.getThish(), blood.getThisw(), blood.getThish());
		}
	}

	public void setLeft(boolean b) {
		this.left = b;
		if ( b ) {
			this.right = false;
			this.up = false;
			this.down = false;
		}
	}

	public void setRight(boolean b) {
		this.right = b;
		if ( b ) {
			this.left = false;
			this.up = false;
			this.down = false;
		}
	}
	
	public void setUp(boolean b ){
		this.up = b;
		if ( b ) {
			this.left = false;
			this.right = false;
			this.down = false;
		}
	}
	public void setDown(boolean b){
		this.down = b;
		if ( b ) {
			this.up = false;
			this.left = false;
			this.right = false;
		}
	}


}
