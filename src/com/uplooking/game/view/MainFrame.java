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
	
	private int ex, ey;				//反方坦克排列的起始坐标
	private int enemyCount;			//反方坦克数量
	private int wx, wy;				//第1排墙块的起始坐标
	private int wallCount;			//每排墙块的数量
	private int step;				//坦克每次移动的步长
	private int dieCount;			//被击毙的坦克数量
	private int shixue;				//击中坦克时失血量
	
	private GameInfo main = new GameInfo();							//游戏主界面
	
	/*********************坦克*********************/
	private TankAllies tankme = new TankAllies(Tank.Type.W, this);		//我方坦克
	private TankEnemy[] enemy ;										//敌方坦克/************/
	private List<Tank> tanklist = new ArrayList<Tank>();			/************/
	/*********************坦克*********************/
	
	
	/*********************墙*********************/
	private Wall[] walls1;											//1滴血的墙块的数组/************/
	private Wall[] walls2;											//2滴血的墙块的数组/************/
	private List<Wall> wallList = new ArrayList<Wall>();			/************/
	/*********************墙*********************/
	
	/*********************指挥官*********************/
	private Command cmd = new Command(1);
	/*********************指挥官*********************/
	
	private Yaobao yaobao = new Yaobao(this);			//医疗包
	
	public int getStep() {
		return step;
	}
	
	public MainFrame() {
		
		initParameters();
		
		this.add(main);
		main.setBackground(Color.GRAY);
		main.setOpaque(true);
		main.setLayout(null);
		
		initTank();		//敌我坦克显示
		
		initWalls();	//2排墙块显示
		
		main.add(yaobao);
		
		this.setTitle(GameUtil.getProperty("game.view.title"));
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setResizable(false);
		int screenw = GameUtil.tool.getScreenSize().width;
		int screenh = GameUtil.tool.getScreenSize().height;
		
		this.addKeyListener(new TankKeyListener());//添加按键监听
		
		this.setBounds((screenw - gameWidth) / 2 , (screenh - gameHeight) / 2, gameWidth, gameHeight);//居中
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
		
		enemy = new TankEnemy[enemyCount];			//初始化敌军坦克数量/************/
		walls1 = new Wall[wallCount];				//墙块的数量由配置文件中的值决定/************/
		walls2 = new Wall[wallCount];				//墙块的数量由配置文件中的值决定/************/
	}
	
	private void initTank(){
		main.add(tankme);
		tankme.setBounds(200,400, BaseUnit.TANK_WIDTH, BaseUnit.TANK_WIDTH);
		tanklist.add(tankme);
		for ( int i = 0; i < enemy.length; i ++ ){/************/
			enemy[i] = new TankEnemy(Tank.Type.D, this);		//构造一个敌方坦克
			main.add(enemy[i]);//添加到界面上
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
		//指挥官总部初始
		Wall w = new Wall(3);	//3滴血
		main.add(w);
		w.setBounds(x, y, BaseUnit.WALL_WIDTH, BaseUnit.WALL_HEIGHT);
		wallList.add(w);
		
		w = new Wall(3);	//3滴血
		main.add(w);
		w.setBounds(x, y - BaseUnit.WALL_HEIGHT, BaseUnit.WALL_WIDTH, BaseUnit.WALL_HEIGHT);
		wallList.add(w);
		
		w = new Wall(3);	//3滴血
		main.add(w);
		w.setBounds(x, y - BaseUnit.WALL_HEIGHT * 2, BaseUnit.WALL_WIDTH, BaseUnit.WALL_HEIGHT);
		wallList.add(w);
		
		w = new Wall(3);	//3滴血
		main.add(w);
		w.setBounds(x + BaseUnit.WALL_WIDTH, y - BaseUnit.WALL_HEIGHT * 2, BaseUnit.WALL_WIDTH, BaseUnit.WALL_HEIGHT);
		wallList.add(w);
		
		w = new Wall(3);	//3滴血
		main.add(w);
		w.setBounds(x + BaseUnit.WALL_WIDTH * 2, y - BaseUnit.WALL_HEIGHT * 2, BaseUnit.WALL_WIDTH, BaseUnit.WALL_HEIGHT);
		wallList.add(w);
		
		w = new Wall(3);	//3滴血
		main.add(w);
		w.setBounds(x + BaseUnit.WALL_WIDTH * 3, y - BaseUnit.WALL_HEIGHT * 2, BaseUnit.WALL_WIDTH, BaseUnit.WALL_HEIGHT);
		wallList.add(w);
		
		w = new Wall(3);	//3滴血
		main.add(w);
		w.setBounds(x + BaseUnit.WALL_WIDTH * 3, y - BaseUnit.WALL_HEIGHT, BaseUnit.WALL_WIDTH, BaseUnit.WALL_HEIGHT);
		wallList.add(w);
		
		w = new Wall(3);	//3滴血
		main.add(w);
		w.setBounds(x + BaseUnit.WALL_WIDTH * 3, y, BaseUnit.WALL_WIDTH, BaseUnit.WALL_HEIGHT);
		wallList.add(w);
		
		main.add(cmd);
		cmd.setBounds(x + BaseUnit.WALL_WIDTH, y - 20, BaseUnit.CMD_WIDTH, BaseUnit.CMD_HEIGHT);
	}/************/
	
	public boolean checkHit(BaseUnit unit){
		//先与所有墙块检查
		for ( Wall w : wallList ){
			if ( w.getLife() > 0 && w.getRectangle().intersects(unit.getRectangle())){
				w.setLife(w.getLife() - 1);		//被击中的墙，减血1
				if ( w.getLife() <= 0 ){
					GameUtil.hit();				//播放墙垮塌的声音
					w.setVisible(false);
				} else {
					GameUtil.hit();				//播放墙被击中的声音
					w.repaint();
				}
				return true;
			}
		}
		
		//接着再与所有坦克检查是否碰撞
		for ( Tank t : tanklist ){ 
			//如果当前子弹的发射坦克是当前坦克t，或者坦克的生命值为0，就跳过碰撞检查
			if ( t.getLife() <= 0 || t == ((Missile) unit).getTank() 
					|| t.getType() == ((Missile) unit).getTank().getType() ) continue;
			if ( t.getRectangle().intersects(unit.getRectangle())){
				int sx = t.isHero() ? shixue / 2 : shixue;
				if ( ((Missile) unit).getTank().isHero() ){
					t.setLife( t.getLife() - sx - 10 );			//英雄级坦克杀伤力更强，每弹多失10血
				} else 
					t.setLife(t.getLife() - sx);	//被击中的坦克，减血sx
				if ( t.getLife() <= 0 ){
					GameUtil.tankdiePlay();
					if ( t.getType() == t.type.D ) {
						dieCount ++;
						main.updateDieCount(dieCount);
						main.repaint();
						if ( dieCount >= enemyCount ){
							main.setState(1);//正方胜利
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
		
		//接着最后与指挥官检查
		if (cmd.getLife() > 0 && cmd.getRectangle().intersects(unit.getRectangle())){
			cmd.setLife(0);
			GameUtil.commanddiePlay();
			main.setState(2);			//指挥官被击毙
			tankme.setLife(0);
			main.repaint();
			return true;
		}
		
		//检查是否超出屏幕范围
		if ( unit.getX() < 0 || unit.getX() > gameWidth - 10 || unit.getY() < 0
				|| unit.getY() > gameHeight - 40){
			unit.setLife(0);
			return true;
		}
		return false;
	}
	
	public boolean check(BaseUnit unit){
		//先与所有墙块检查
		for ( Wall w : wallList ){
			if (w.getLife() > 0 && w.getRectangle().intersects(unit.getRectangle())){
				return true;
			}
		}
		
		//接着再与所有坦克检查是否碰撞
		for ( Tank t : tanklist ){ 
			if ( t == unit || t.getLife() <= 0 ) continue;
			if ( t.getRectangle().intersects(unit.getRectangle())){
				return true;
			}
		}
		
		//接着最后与指挥官检查
		if ( cmd.getLife() > 0 && cmd.getRectangle().intersects(unit.getRectangle())){
			return true;
		}
		
		//检查是否超出屏幕范围
		if ( unit.getX() < 0 || unit.getX() > gameWidth - 40 || unit.getY() < 0
				|| unit.getY() > gameHeight - 60){
			return true;
		}
		
		//与医疗包检查碰撞
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
			if ( main.isOver() ) return;			//游戏如果已经结束，就不能开火和移动
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
		main.add(blood);//把血条加入到主游戏界面
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
