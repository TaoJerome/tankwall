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
	
	private String info1 = "�о�����: ";
	private String info2 = "��������: ";
	private String info3 = "�ҵ�����ֵ: ";
	
	private String resultMessage;
	
	private Font font = new Font("����", 0, 12);
	private Font font2 = new Font("����", 0, 16);
	
	public GameInfo() {
		JOptionPane.showMessageDialog(
				this, 
				"1����Ϸ��ʱ��Ϊ100��\n2����ɫΪӢ�ۼ�̹�ˣ�ɱ����30��������ʱÿ��ʧѪ10\n" +
				"3���ڰ�ɫΪ��̹ͨ�ˣ���ɱ��20��������ʱÿ��ʧѪ20" +
				"\n4���ҷ�̹��ɱ����20\n5���õ�Ц���ɼ�Ѫ20\n6��δ����Ϸʱ���������о���ָ�ӹ�������Ϊ��\n7��������з�̹��Ϊʤ��", 
				"��Ϸ����", 
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
			g.drawString("ʣ��ʱ��:" + overtime , 370, 20);
		} else {
			g.setFont(new Font("����", 0, 50));
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
			setState(2);		//ʱ�䵽�ˣ����㷴��ʤ��
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
