package com.uplooking.game.util;

import java.applet.Applet;
import java.applet.AudioClip;
import java.awt.Image;
import java.awt.Toolkit;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public final class GameUtil {
	
	private static String configfilename = "com/uplooking/game/config/tankconfig.properties";
	private static Properties p = new java.util.Properties();
	private static AudioClip start, hitme, hithe, tankdie, commanddie, hitwall, hit;
	
	static {
		InputStream is = null;
		try {
			is = GameUtil.class.getClassLoader().getResourceAsStream(configfilename);
			p = new Properties();
			p.load(is);
			start = Applet.newAudioClip(GameUtil.class.getClassLoader().getResource("com/uplooking/game/sounds/go.wav"));
			hitme = Applet.newAudioClip(GameUtil.class.getClassLoader().getResource("com/uplooking/game/sounds/hitme.wav"));
			hithe = Applet.newAudioClip(GameUtil.class.getClassLoader().getResource("com/uplooking/game/sounds/hithe.wav"));
			tankdie = Applet.newAudioClip(GameUtil.class.getClassLoader().getResource("com/uplooking/game/sounds/BOMB1.WAV"));
			commanddie = Applet.newAudioClip(GameUtil.class.getClassLoader().getResource("com/uplooking/game/sounds/die.wav"));
			hitwall = Applet.newAudioClip(GameUtil.class.getClassLoader().getResource("com/uplooking/game/sounds/hitwall.wav"));
			hit = Applet.newAudioClip(GameUtil.class.getClassLoader().getResource("com/uplooking/game/sounds/di.wav"));
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if ( is != null ) {
					is.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static final Toolkit tool = Toolkit.getDefaultToolkit();
	
	public static Image getImage(String filename){
		return tool.getImage(GameUtil.class.getClassLoader().getResource(filename));
	}
	
	public static String getProperty(String key){
		return p.getProperty(key);
	}
	
	public static void startPlay(){
		start.play();
	}
	
	public static void hitmePlay(){
		hitme.play();
	}
	public static void hithePlay(){
		hithe.play();
	}
	public static void tankdiePlay(){
		tankdie.play();
	}
	public static void commanddiePlay(){
		commanddie.play();
	}
	public static void hitwallPlay(){
		hitwall.play();
	}
	public static void hit(){
		hit.play();
	}
	public static void main(String[] args) throws InterruptedException {
		hitwall.play();
		Thread.sleep(4000);
	}

}
