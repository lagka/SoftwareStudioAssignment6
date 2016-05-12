

import java.awt.event.KeyEvent;
import java.util.ArrayList;

import controlP5.ControlP5;
import ddf.minim.AudioPlayer;
import ddf.minim.Minim;
import de.looksgood.ani.Ani;
import processing.core.PApplet;
import processing.data.JSONArray;
import processing.data.JSONObject;

/**
* This class is for sketching outcome using Processing
* You can do major UI control and some visualization in this class.  
*/
@SuppressWarnings("serial")
public class MainApplet extends PApplet{
	private String path = "main/resources/";
	private String file = "starwars-episode-";
	private String file2 ="-interactions.json";
	private int level =1;
	private JSONObject[] datas;
	private JSONArray[] nodes;
	private JSONArray[] links;
	private final static int width = 1000, height = 670;
	private ArrayList<Character> characters=new  ArrayList<Character>();
	private boolean mt;
	private  ControlP5 button;
	private Minim minim;
	private AudioPlayer media;
	private Ani animation;
	public void setup() {

		size(width, height);
		smooth();
		loadData();
		//minim=new Minim(this);
		//media=minim.loadFile(this.getClass().getResource("main/resources/VOIA.mp3").getPath());
		//media.play();
		Ani.init(this);
		button=new ControlP5(this);
		datas=new JSONObject[8];
		links=new JSONArray[8];
		nodes=new JSONArray[8];
		button.addButton("ButtonAdd").setLabel("ADD ALL Nodes")
									 .setPosition(600, 40) 	
									 .setSize(150, 40);
		button.addButton("ButtonClear").setLabel("Clear")
		 							   .setPosition(600, 80) 	
		 							   .setSize(150, 40);
		
	}

	public void draw() {
			this.background(255);
			this.fill(0);
			this.textSize(40);
			this.text("Star War "+this.level,300,40);
			for(Character c:characters){
				c.display();
			}
	}
	public void ButtonAdd(){
		for(Character c:characters){
			//c.setInCircle(true);
		}
			AddToCircle();
	}
	public void ButtonClear(){
		
		for(Character c:characters){
			//c.setInCircle(false);
			//animation = Ani.to(c, (float) 0.2, "x", c.get_init_x(),Ani.LINEAR);
			//animation = Ani.to(c, (float) 0.2, "y", c.get_init_y(),Ani.LINEAR);
		}
		
	}
	public void mouseReleased(){
		
	}
	public void mouseDragged(){
		
	}
	public void keyPressed(){
		if(keyCode==KeyEvent.VK_1||(keyCode==KeyEvent.VK_UP&&level==2)||(keyCode==KeyEvent.VK_DOWN&&level==7))level=1;
		else if(keyCode==KeyEvent.VK_2||(keyCode==KeyEvent.VK_DOWN&&level==1)||(keyCode==KeyEvent.VK_UP&&level==3))level=2;
		else if(keyCode==KeyEvent.VK_3||(keyCode==KeyEvent.VK_DOWN&&level==2)||(keyCode==KeyEvent.VK_UP&&level==4))level=3;
		else if(keyCode==KeyEvent.VK_4||(keyCode==KeyEvent.VK_DOWN&&level==3)||(keyCode==KeyEvent.VK_UP&&level==5))level=4;
		else if(keyCode==KeyEvent.VK_5||(keyCode==KeyEvent.VK_DOWN&&level==4)||(keyCode==KeyEvent.VK_UP&&level==6))level=5;
		else if(keyCode==KeyEvent.VK_6||(keyCode==KeyEvent.VK_DOWN&&level==5)||(keyCode==KeyEvent.VK_UP&&level==7))level=6;
		else if(keyCode==KeyEvent.VK_7||(keyCode==KeyEvent.VK_DOWN&&level==6)||(keyCode==KeyEvent.VK_UP&&level==1))level=7;
	
	}
	

	private void loadData(){
		/*for(int i=1;i<=7;i++){
			datas[i]=loadJSONObject(path + file + Integer.toString(i) + file2);
			nodes[i]=datas[i].getJSONArray("nodes");
			links[i]=datas[i].getJSONArray("links");
		}*/
	}
	private void AddToCircle(){
		
		
	}

}