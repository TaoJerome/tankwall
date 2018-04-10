package com.uplooking.game.view;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

import com.uplooking.game.util.GameUtil;

public class GameInfo extends JPanel implements Runnable {
	
	private int enemyCount;
	private int dieCount;
	private int life;
	private boolean isover;
	
	private int overtime;
	
	private String info1 = "敌军数量: ";
	private String info2 = "歼灭数量: ";
	private String info3 = "我的生命值: ";
	
	private String resultMessage;
	
	private Font font = new Font("宋体", 0, 12);
	private Font font2 = new Font("黑体", 0, 16);
	
	public GameInfo() {
		JOptionPane.showMessageDialog(
				this, 
				"1、游戏总时间为100秒\n2、黄色为英雄级坦克，杀伤力30，被攻击时每次失血10\n" +
				"3、黑白色为普通坦克，伤杀力20，被攻击时每次失血20" +
				"\n4、我方坦克杀伤力20\n5、得到笑脸可加血20\n6、未在游戏时间内消灭光敌军或指挥官死亡均为输\n7、消灭完敌方坦克为胜利", 
				"游戏规则", 
				JOptionPane.INFORMATION_MESSAGE);
		resultMessage = GameUtil.getProperty("enemy.win");
		overtime = Integer.parseInt(GameUtil.getProperty("game.over.time"));
		enemyCount = Integer.parseInt( GameUtil.getProperty("enemy.count")) ;
		dieCount = 0;
		life = Integer.parseInt( GameUtil.getProperty("tank.self.blood")) ;
		isover = false;
		new Thread(this).start();
	}
	
	
	public void updateDieCount(int count){
		this.dieCount = count;
	}
	
	public boolean isOver(){
		return isover;
	}
	
	public void setLife(int life){
		this.life = life;
	}
	public void setState(int stat){
		if ( stat == 1 ){
			resultMessage = GameUtil.getProperty("self.win");
		}
		isover = stat != 0 ;
		repaint();
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		if ( !isover ){
			g.setFont(font);
			g.setColor(Color.WHITE);
			g.drawString(info1 + (enemyCount-dieCount) + "     " + info2 + dieCount + "      " + info3 + life, 20, 20);
			g.setFont(font2);
			if ( overtime > 30 )
				g.setColor(Color.GREEN);
			else
				g.setColor(Color.red);
			g.drawString("剩余时间:" + overtime , 370, 20);
		} else {
			g.setFont(new Font("黑体", 0, 50));
			g.setColor(Color.RED);
			g.drawString(resultMessage, 300, 400);
		}
	}


	@Override
	public void run() {
		try {
			while ( overtime > 0 ){
				Thread.sleep(1000);
				overtime --;
				repaint();
			}
			isover = true;
			setState(2);		//时间到了，就算反方胜利
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
