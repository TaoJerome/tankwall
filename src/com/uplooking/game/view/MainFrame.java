package com.uplooking.game.view;

import java.awt.Color;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import com.uplooking.game.util.GameUtil;
import com.uplooking.game.view.Tank.Direction;
import com.uplooking.game.view.core.BaseUnit;

public class MainFrame extends JFrame {
	
	private int gameWidth, gameHeight;
	
	private int ex, ey;				//����̹�����е���ʼ����
	private int enemyCount;			//����̹������
	private int wx, wy;				//��1��ǽ�����ʼ����
	private int wallCount;			//ÿ��ǽ�������
	private int step;				//̹��ÿ���ƶ��Ĳ���
	private int dieCount;			//�����е�̹������
	private int shixue;				//����̹��ʱʧѪ��
	
	private GameInfo main = new GameInfo();							//��Ϸ������
	
	/*********************̹��*********************/
	private TankAllies tankme = new TankAllies(Tank.Type.W, this);		//�ҷ�̹��
	private TankEnemy[] enemy ;										//�з�̹��/************/
	private List<Tank> tanklist = new ArrayList<Tank>();			/************/
	/*********************̹��*********************/
	
	
	/*********************ǽ*********************/
	private Wall[] walls1;											//1��Ѫ��ǽ�������/************/
	private Wall[] walls2;											//2��Ѫ��ǽ�������/************/
	private List<Wall> wallList = new ArrayList<Wall>();			/************/
	/*********************ǽ*********************/
	
	/*********************ָ�ӹ�*********************/
	private Command cmd = new Command(1);
	/*********************ָ�ӹ�*********************/
	
	private Yaobao yaobao = new Yaobao(this);			//ҽ�ư�
	
	public int getStep() {
		return step;
	}
	
	public MainFrame() {
		
		initParameters();
		
		this.add(main);
		main.setBackground(Color.GRAY);
		main.setOpaque(true);
		main.setLayout(null);
		
		initTank();		//����̹����ʾ
		
		initWalls();	//2��ǽ����ʾ
		
		main.add(yaobao);
		
		this.setTitle(GameUtil.getProperty("game.view.title"));
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setResizable(false);
		int screenw = GameUtil.tool.getScreenSize().width;
		int screenh = GameUtil.tool.getScreenSize().height;
		
		this.addKeyListener(new TankKeyListener());//��Ӱ�������
		
		this.setBounds((screenw - gameWidth) / 2 , (screenh - gameHeight) / 2, gameWidth, gameHeight);//����
		this.setVisible(true);
		GameUtil.startPlay();
	}

	private void initParameters() {
		gameWidth = Integer.parseInt( GameUtil.getProperty("game.ui.width") 		) ;
		gameHeight = Integer.parseInt( GameUtil.getProperty("game.ui.height") 		) ;
		enemyCount = Integer.parseInt( GameUtil.getProperty("enemy.count") 			) ;
		wallCount = Integer.parseInt( GameUtil.getProperty("wall.level.one") 		) ;
		step = Integer.parseInt( GameUtil.getProperty("tank.move.step") 			) ;
		shixue = Integer.parseInt( GameUtil.getProperty("missile.hit.xue") 			) ;
		ex = 50;
		ey = 40;
		
		wx = (gameWidth - wallCount * BaseUnit.WALL_WIDTH) /2;		/************/
		wy = 120;
		
		enemy = new TankEnemy[enemyCount];			//��ʼ���о�̹������/************/
		walls1 = new Wall[wallCount];				//ǽ��������������ļ��е�ֵ����/************/
		walls2 = new Wall[wallCount];				//ǽ��������������ļ��е�ֵ����/************/
	}
	
	private void initTank(){
		main.add(tankme);
		tankme.setBounds(200,400, BaseUnit.TANK_WIDTH, BaseUnit.TANK_WIDTH);
		tanklist.add(tankme);
		for ( int i = 0; i < enemy.length; i ++ ){/************/
			enemy[i] = new TankEnemy(Tank.Type.D, this);		//����һ���з�̹��
			main.add(enemy[i]);//��ӵ�������
			enemy[i].setBounds(ex, ey, BaseUnit.TANK_WIDTH, BaseUnit.TANK_WIDTH);
			ex += (gameWidth - 50 - 50 ) / enemyCount;
			tanklist.add(enemy[i]);							/************/
		}
	}
	/************/
	private void initWalls(){
		for ( int i = 0; i < walls1.length; i ++ ){
			walls1[i] = new Wall(1);
			main.add(walls1[i]);
			walls1[i].setBounds(wx, wy, BaseUnit.WALL_WIDTH, BaseUnit.WALL_HEIGHT);
			wx += BaseUnit.WALL_WIDTH;
			wallList.add(walls1[i]);
		}
		wy += 100;	wx = (gameWidth - wallCount * BaseUnit.WALL_WIDTH) /2;
		for ( int i = 0; i < walls2.length; i ++ ){
			walls2[i] = new Wall(2);
			main.add(walls2[i]);
			walls2[i].setBounds(wx, wy, BaseUnit.WALL_WIDTH, BaseUnit.WALL_HEIGHT);
			wx += BaseUnit.WALL_WIDTH;
			wallList.add(walls2[i]);
		}
		
		int x = (gameWidth - BaseUnit.WALL_WIDTH * 5 ) / 2;
		int y = (gameHeight - BaseUnit.WALL_HEIGHT * 3 + 11);
		//ָ�ӹ��ܲ���ʼ
		Wall w = new Wall(3);	//3��Ѫ
		main.add(w);
		w.setBounds(x, y, BaseUnit.WALL_WIDTH, BaseUnit.WALL_HEIGHT);
		wallList.add(w);
		
		w = new Wall(3);	//3��Ѫ
		main.add(w);
		w.setBounds(x, y - BaseUnit.WALL_HEIGHT, BaseUnit.WALL_WIDTH, BaseUnit.WALL_HEIGHT);
		wallList.add(w);
		
		w = new Wall(3);	//3��Ѫ
		main.add(w);
		w.setBounds(x, y - BaseUnit.WALL_HEIGHT * 2, BaseUnit.WALL_WIDTH, BaseUnit.WALL_HEIGHT);
		wallList.add(w);
		
		w = new Wall(3);	//3��Ѫ
		main.add(w);
		w.setBounds(x + BaseUnit.WALL_WIDTH, y - BaseUnit.WALL_HEIGHT * 2, BaseUnit.WALL_WIDTH, BaseUnit.WALL_HEIGHT);
		wallList.add(w);
		
		w = new Wall(3);	//3��Ѫ
		main.add(w);
		w.setBounds(x + BaseUnit.WALL_WIDTH * 2, y - BaseUnit.WALL_HEIGHT * 2, BaseUnit.WALL_WIDTH, BaseUnit.WALL_HEIGHT);
		wallList.add(w);
		
		w = new Wall(3);	//3��Ѫ
		main.add(w);
		w.setBounds(x + BaseUnit.WALL_WIDTH * 3, y - BaseUnit.WALL_HEIGHT * 2, BaseUnit.WALL_WIDTH, BaseUnit.WALL_HEIGHT);
		wallList.add(w);
		
		w = new Wall(3);	//3��Ѫ
		main.add(w);
		w.setBounds(x + BaseUnit.WALL_WIDTH * 3, y - BaseUnit.WALL_HEIGHT, BaseUnit.WALL_WIDTH, BaseUnit.WALL_HEIGHT);
		wallList.add(w);
		
		w = new Wall(3);	//3��Ѫ
		main.add(w);
		w.setBounds(x + BaseUnit.WALL_WIDTH * 3, y, BaseUnit.WALL_WIDTH, BaseUnit.WALL_HEIGHT);
		wallList.add(w);
		
		main.add(cmd);
		cmd.setBounds(x + BaseUnit.WALL_WIDTH, y - 20, BaseUnit.CMD_WIDTH, BaseUnit.CMD_HEIGHT);
	}/************/
	
	public boolean checkHit(BaseUnit unit){
		//��������ǽ����
		for ( Wall w : wallList ){
			if ( w.getLife() > 0 && w.getRectangle().intersects(unit.getRectangle())){
				w.setLife(w.getLife() - 1);		//�����е�ǽ����Ѫ1
				if ( w.getLife() <= 0 ){
					GameUtil.hit();				//����ǽ����������
					w.setVisible(false);
				} else {
					GameUtil.hit();				//����ǽ�����е�����
					w.repaint();
				}
				return true;
			}
		}
		
		//������������̹�˼���Ƿ���ײ
		for ( Tank t : tanklist ){ 
			//�����ǰ�ӵ��ķ���̹���ǵ�ǰ̹��t������̹�˵�����ֵΪ0����������ײ���
			if ( t.getLife() <= 0 || t == ((Missile) unit).getTank() 
					|| t.getType() == ((Missile) unit).getTank().getType() ) continue;
			if ( t.getRectangle().intersects(unit.getRectangle())){
				int sx = t.isHero() ? shixue / 2 : shixue;
				if ( ((Missile) unit).getTank().isHero() ){
					t.setLife( t.getLife() - sx - 10 );			//Ӣ�ۼ�̹��ɱ������ǿ��ÿ����ʧ10Ѫ
				} else 
					t.setLife(t.getLife() - sx);	//�����е�̹�ˣ���Ѫsx
				if ( t.getLife() <= 0 ){
					GameUtil.tankdiePlay();
					if ( t.getType() == t.type.D ) {
						dieCount ++;
						main.updateDieCount(dieCount);
						main.repaint();
						if ( dieCount >= enemyCount ){
							main.setState(1);//����ʤ��
						}
					}
				} else {
					t.updateBlood(t.getLife());
					if ( t.getType() == Tank.Type.D ){
						GameUtil.hithePlay();
					} else {
						main.setLife(t.getLife());
						GameUtil.hitmePlay();
					}
				}
				return true;
			}
		}
		
		//���������ָ�ӹټ��
		if (cmd.getLife() > 0 && cmd.getRectangle().intersects(unit.getRectangle())){
			cmd.setLife(0);
			GameUtil.commanddiePlay();
			main.setState(2);			//ָ�ӹٱ�����
			tankme.setLife(0);
			main.repaint();
			return true;
		}
		
		//����Ƿ񳬳���Ļ��Χ
		if ( unit.getX() < 0 || unit.getX() > gameWidth - 10 || unit.getY() < 0
				|| unit.getY() > gameHeight - 40){
			unit.setLife(0);
			return true;
		}
		return false;
	}
	
	public boolean check(BaseUnit unit){
		//��������ǽ����
		for ( Wall w : wallList ){
			if (w.getLife() > 0 && w.getRectangle().intersects(unit.getRectangle())){
				return true;
			}
		}
		
		//������������̹�˼���Ƿ���ײ
		for ( Tank t : tanklist ){ 
			if ( t == unit || t.getLife() <= 0 ) continue;
			if ( t.getRectangle().intersects(unit.getRectangle())){
				return true;
			}
		}
		
		//���������ָ�ӹټ��
		if ( cmd.getLife() > 0 && cmd.getRectangle().intersects(unit.getRectangle())){
			return true;
		}
		
		//����Ƿ񳬳���Ļ��Χ
		if ( unit.getX() < 0 || unit.getX() > gameWidth - 40 || unit.getY() < 0
				|| unit.getY() > gameHeight - 60){
			return true;
		}
		
		//��ҽ�ư������ײ
		if ( unit.getRectangle().intersects(yaobao.getRectangle()) && yaobao.getLife() > 0 ) {
			unit.setLife( unit.getLife() + yaobao.getLife() > 100 ? 100 : unit.getLife() + yaobao.getLife() );
			unit.updateBlood(unit.getLife());
			if ( ((Tank)unit ).getType() == Tank.Type.W ) {
				main.setLife(unit.getLife());
			}
			yaobao.setLife(0);
			yaobao.setVisible(false);
		}
		return false;
	}
	
	
	class TankKeyListener extends KeyAdapter {
		@Override
		public void keyPressed (KeyEvent e) {
			if ( main.isOver() ) return;			//��Ϸ����Ѿ��������Ͳ��ܿ�����ƶ�
			int code = e.getKeyCode();
			if ( code == KeyEvent.VK_LEFT  ) {
				tankme.setDirect(Direction.L);
				tankme.setLeft(true);
			} else if ( code == KeyEvent.VK_RIGHT ) {
				tankme.setDirect(Direction.R);
				tankme.setRight(true);
			} else if ( code == KeyEvent.VK_UP ) {
				tankme.setDirect(Direction.U);
				tankme.setUp(true);
			} else if ( code == KeyEvent.VK_DOWN ) {
				tankme.setDirect(Direction.D);
				tankme.setDown(true);
			}
		}
		
		@Override
		public void keyReleased(KeyEvent e) {
			if ( main.isOver() ) return;
			int code = e.getKeyCode();
			if ( code == KeyEvent.VK_LEFT  ) {
				tankme.setLeft(false);
			} else if ( code == KeyEvent.VK_RIGHT ) {
				tankme.setRight(false);
			} else if ( code == KeyEvent.VK_UP ) {
				tankme.setUp(false);
			} else if ( code == KeyEvent.VK_DOWN ) {
				tankme.setDown(false);
			}
			if ( code == KeyEvent.VK_SPACE ){
				tankme.fire();
			}
		}
		
	}
	
	public void addBlood(Blood blood) {
		main.add(blood);//��Ѫ�����뵽����Ϸ����
	}
	
	public void addMissile(Missile m) {
		main.add(m);
	}
	
	public void setGameStae(int stat) {
		main.setState(stat);
	}
	
	public boolean isover(){
		return main.isOver();
	}
	
	public static void main(String[] args) {
		new MainFrame();
	}




}
