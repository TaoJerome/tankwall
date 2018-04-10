package com.uplooking.game.view.core;

import java.awt.Color;
import java.awt.Image;
import java.awt.Rectangle;

import javax.swing.JPanel;

import com.uplooking.game.view.Blood;
import com.uplooking.game.view.MainFrame;
import com.uplooking.game.view.Tank.Direction;

public class BaseUnit extends JPanel {
	
	public final static int TANK_WIDTH = 35;
	public final static int TANK_HEIGHT = 35;
	
	public final static int WALL_WIDTH = 20;
	public final static int WALL_HEIGHT = 20;
	
	public final static int CMD_WIDTH = 40;
	public final static int CMD_HEIGHT = 40;

	public final static int MIS_WIDTH = 12;
	public final static int MIS_HEIGHT = 12;
	
	public final static int YB_WIDTH = 24;
	public final static int YB_HEIGHT = 24;
	
	public final static Color C1 = new Color(0xFF, 0x99, 0x33);//��ɫ
	public final static Color C2 = new Color(0xFF, 0x66, 0x00);//��ɫ
	public final static Color C3 = new Color(0xFF, 0x33, 0x00);//���
	
	
	protected int w, h;			//��͸�
	protected Image img;		//ͼƬ
	protected int life;			//����ֵ
	
	protected MainFrame mainview;		//���������
	protected int oldx, oldy;			//�ƶ�ǰ������
	protected Direction direct;			//����
	protected Blood blood;
	
	public void setDirect(Direction direct){
		this.direct = direct;
	}
	
	public Direction getDirect(){
		return this.direct;
	}
	
	public void updateBlood(int life){
		blood.setLife(life);
		blood.repaint();
	}
	
	public void setWidth(int w){
		this.w = w;
	}
	public void setHeight(int h){
		this.h = h;
	}
	
	public int getThisw(){
		return w;
	}
	public int getThish(){
		return h;
	}
	
	public void setImage(Image img){
		this.img = img;
	}
	
	public void setLife(int life){
		this.life = life;
	}
	public int getLife(){
		return this.life;
	}
	
	public void updateMissileLocation(int x, int y){
		int cx = getX();
		int cy = getY();
		oldx = cx;
		oldy = cy;
		cx += x;
		cy += y;
		this.setBounds(cx, cy, w, h);
		if ( mainview.checkHit(this) ) {		//�����������ײ���ͻָ��ƶ�ǰ������
			life = 0;
		}
	}
	public void updateLocation(int x, int y){
		int cx = getX();
		int cy = getY();
		oldx = cx;
		oldy = cy;
		cx += x;
		cy += y;
		this.setBounds(cx, cy, w, h);
		if ( mainview.check(this) ) {		//�����������ײ���ͻָ��ƶ�ǰ������
			setBounds(oldx, oldy, w, h);
		}
	}
	
	public Rectangle getRectangle(){
		return new Rectangle(getX(), getY(), w, h);
	}
}
