package main.java;

import java.awt.event.KeyEvent;
import java.util.ArrayList;
import controlP5.ControlFont;
import controlP5.ControlP5;
import ddf.minim.AudioPlayer;
import ddf.minim.Minim;
import de.looksgood.ani.Ani;
import processing.core.PApplet;
import processing.data.JSONArray;
import processing.data.JSONObject;

@SuppressWarnings("serial")
public class MainApplet extends PApplet {
	
	private final static int width = 1200, height = 650;
	private String path = "main/resources/";
	private int level = 1;
	private String file;
	private JSONObject data;
	private JSONArray nodes, links;
	private ArrayList<Character> characters;
	
	private ControlP5 cp5;
	@SuppressWarnings("unused")
	private Ani ani;
	private Minim minim;
	private AudioPlayer bgMusic;
	private int m=0, n=0;
	private Character locked;// the Character object which is being to drag
	private Character isOverAndNotPress;// mouse is over the Character object but not press it
	private boolean isOver;// check if the mouse is over the Character object
	private ArrayList<Character> addOrder;// the order add to the BigCircle
	// check if List(addOrder) have the same one while mouseReleased in the big circle
	private boolean haveSame = false;
	
	public void setup() {
		size(width, height);
		Ani.init(this);
		minim = new Minim(this);
		//load a music to set the background music
		bgMusic = minim.loadFile("/main/resources/VOIA.mp3");
		bgMusic.play();
		cp5 = new ControlP5(this);
		//create button
		cp5.addButton("buttonA").setLabel("ADD ALL").setPosition(950,50).setSize(200,50);
		cp5.addButton("buttonB").setLabel("CLEAR").setPosition(950,150).setSize(200,50);
		// set the Font of word in the button
		ControlFont font = new ControlFont(createFont("Consolas",20,true),241);
		cp5.getController("buttonA").getCaptionLabel().setFont(font).setSize(24);
		cp5.getController("buttonB").getCaptionLabel().setFont(font).setSize(24);
		smooth();	
		loadData();
	}

	public void draw() {
		background(255);
		//set the Big Circle
		noFill();
		stroke(25,115,25);
		if (locked != null && dist(locked.cur_x, locked.cur_y, width/2, height/2+30) < 250) strokeWeight(10);
		else strokeWeight(5);
		ellipse(width/2, height/2+30, 500, 500);
		//set the text
		fill(111,94,54);
		textSize(34);
		text("Star Wars " + level, 500, 50);
		// if mouse is above the one of nodes and not pressed, set the new diameter of this node
		// and let the isOverAndNotPress point to the location same as this node
		for(Character i : characters) {
			if(dist(i.cur_x, i.cur_y, mouseX, mouseY) < 20 && !mousePressed) {
				isOverAndNotPress = i;
				isOverAndNotPress.setDiameter(50);
				isOverAndNotPress.setWeight(3);
				i.set_hover(true);
				System.out.println(i.get_relations().get(i));
			}
			else {
				i.set_hover(false);
				i.setDiameter(40);
				i.setWeight(0);
			}
			i.display();
		}
		// check whether the mouse is above one of nodes or not
		for (Character i : characters) {
			if (dist(i.cur_x, i.cur_y, mouseX, mouseY) < 20) {
				isOver = true;
				break;
			} 
			else isOver = false;
		}
		// if mouse is above one of nodes, show the name of this node
		if (isOverAndNotPress != null) {
			if (dist(isOverAndNotPress.cur_x, isOverAndNotPress.cur_y, mouseX, mouseY) < 20) {
				fill(74, 224, 89);
				rect(mouseX+10, mouseY-15, isOverAndNotPress.getName().length()*15, 30, 100);
				fill(255);
				textSize(16);
				text(isOverAndNotPress.getName(), mouseX+20, mouseY+5);
			}
		}
	}
	
	public void keyPressed(KeyEvent ke) {
		if(keyCode==KeyEvent.VK_1||(keyCode==KeyEvent.VK_UP&&level==2)||(keyCode==KeyEvent.VK_DOWN&&level==7))level=1;
		else if(keyCode==KeyEvent.VK_2||(keyCode==KeyEvent.VK_DOWN&&level==1)||(keyCode==KeyEvent.VK_UP&&level==3))level=2;
		else if(keyCode==KeyEvent.VK_3||(keyCode==KeyEvent.VK_DOWN&&level==2)||(keyCode==KeyEvent.VK_UP&&level==4))level=3;
		else if(keyCode==KeyEvent.VK_4||(keyCode==KeyEvent.VK_DOWN&&level==3)||(keyCode==KeyEvent.VK_UP&&level==5))level=4;
		else if(keyCode==KeyEvent.VK_5||(keyCode==KeyEvent.VK_DOWN&&level==4)||(keyCode==KeyEvent.VK_UP&&level==6))level=5;
		else if(keyCode==KeyEvent.VK_6||(keyCode==KeyEvent.VK_DOWN&&level==5)||(keyCode==KeyEvent.VK_UP&&level==7))level=6;
		else if(keyCode==KeyEvent.VK_7||(keyCode==KeyEvent.VK_DOWN&&level==6)||(keyCode==KeyEvent.VK_UP&&level==1))level=7;
		loadData();
	}
	// load the data what we want to use from JSON file 
	private void loadData(){
		addOrder = new ArrayList<Character>();
		characters = new ArrayList<Character>();
		file = "starwars-episode-" + level + "-interactions.json";
		data = loadJSONObject(path + file);
		nodes = data.getJSONArray("nodes");
		links = data.getJSONArray("links");
		
		for(int i=0; i<nodes.size(); i++) {
			JSONObject node = nodes.getJSONObject(i);
			String name = node.getString("name");
			String color = node.getString("colour");
			m = i/10;
			n = (i>9) ? i-i/10*10 : i;
			Character character = new Character(this,name,color,m*60,10+n*60);
			characters.add(character);
		}
		
		for(int i=0; i<links.size(); i++) {
			JSONObject link = links.getJSONObject(i);
			int source = link.getInt("source");
			int target = link.getInt("target");
			int value = link.getInt("value");
			characters.get(source).addTarget(characters.get(target), value);
			characters.get(target).addTarget(characters.get(source), value);
		}
	}
	// if press the buttonA(ADD ALL) add each node to the big circle
	public void buttonA() {
		for(Character i : characters) {
			if(!i.inBigCircle) {
				i.inBigCircle = true;
				addOrder.add(i);
			}
		}
		addToBigCircle();
	}
	// if press the buttonB(CLEAR) remove each node from the big circle
	public void buttonB() {
		for(Character i : characters) {
			ani = Ani.to(i, (float) 1, "x", i.getInitX());
			ani = Ani.to(i, (float) 1, "y", i.getInitY());
			i.inBigCircle = false;
		}
		addOrder.clear();
	}
	
	public void mousePressed() {
		if (isOver) {
			locked = isOverAndNotPress;
		}
	}
	
	public void mouseDragged() {
		if (locked != null) {
			locked.cur_x = mouseX;
			locked.cur_y = mouseY;
		}
	}

	public void mouseReleased() {
		haveSame = false;
		if(locked != null) {
			// add the node to the big circle
			if(dist(locked.cur_x, locked.cur_y , width/2, height/2+30) < 250) {
				locked.inBigCircle = true;
				// if the mouse is choose one of nodes in the big circle and the mouse is released
				// in the big circle, don't add the same node to the list(addOrder),so check whether 
				// the node mouse chose in the big circle have existed in the list(addOrder) or not
				for(Character i : addOrder) {
					if(i.getName() == locked.getName()) haveSame = true;
				}
				if(haveSame == false) addOrder.add(locked);
				addToBigCircle();
			}
			// remove the node from big circle
			else {
				ani = Ani.to(locked, (float) 1.0, "x", locked.getInitX());
				ani = Ani.to(locked, (float) 1.0, "y", locked.getInitY());
				locked.inBigCircle = false;
				addOrder.remove(locked);
				addToBigCircle();
			}
		}
		// reset the locked because my mouse is not pressed in this moment 
		locked = null;
	}
	
	// add the node to big circle and set the node's position in to the big circle to 
	// satisfy that n nodes make regular n-edged shape
	public void addToBigCircle() {
		float theta = 0;
		for(Character i : addOrder) {
			ani = Ani.to(i, (float) 1, "x", (width/2) + 250 * cos(theta));
			ani = Ani.to(i, (float) 1, "y", (height/2+30) + 250 * sin(theta));
			theta += (TWO_PI / addOrder.size());
		}
	}
}