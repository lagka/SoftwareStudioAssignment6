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
	
	private final static int width = 1000, height = 500;
	private ControlP5 cp5;
	@SuppressWarnings("unused")
	private Ani ani;
	private Minim minim;
	private AudioPlayer bgMusic;
	
	private String path = "main/resources/";
	private String file;
	private JSONObject data;
	private JSONArray nodes, links;
	private int level = 1;
	private ArrayList<Character> characters;
	private Character locked, hovered;
	private boolean is_hover;

	
	public void setup() {
		size(width, height);
		cp5 = new ControlP5(this);
		cp5.addButton("button_add").setLabel("ADD ALL").setPosition(800,50).setSize(200,50);
		cp5.addButton("button_clear").setLabel("CLEAR").setPosition(800,100).setSize(200,50);
		ControlFont font = new ControlFont(createFont("Consolas",20,true),241);
		cp5.getController("button_add").getCaptionLabel().setFont(font).setSize(24);
		cp5.getController("button_clear").getCaptionLabel().setFont(font).setSize(24);
		minim = new Minim(this);
		bgMusic = minim.loadFile("/main/resources/VOIA.mp3");
		bgMusic.play();
		Ani.init(this);
		smooth();	
		loadData();
	}

	public void draw() {
		background(255);
		noFill();
		int i = level;
		stroke(255 - i * 10, 15 + i * 10, 225 / i);
		
		if (locked != null && dist(locked.cur_x, locked.cur_y, width / 2, height / 2 + 30) < 250)
			strokeWeight(10);
		else
			strokeWeight(5);
		
		ellipse(width / 2, height / 2 + 30, 400, 400);

		fill(0,0,0);
		textSize(34);
		text("Star Wars " + level, 400, 50);

		for(Character c : characters) {
			if(dist(c.cur_x, c.cur_y, mouseX, mouseY) < 20 && !mousePressed) {
				hovered = c;
				hovered.set_radius(50);
				hovered.set_weight(3);
				c.set_hover(true);
			
			}
			else {
				c.set_hover(false);
				c.set_radius(40);
				c.set_weight(0);
			}
			c.display();
		}

		for (Character c : characters) {
			if (dist(c.cur_x, c.cur_y, mouseX, mouseY) < 20) {
				is_hover = true;
				break;
			} 
			else is_hover = false;
		}

		if (hovered != null) {
			if (dist(hovered.cur_x, hovered.cur_y, mouseX, mouseY) < 20) {
				fill(99, 156, 245);
				rect(mouseX + 10, mouseY - 15, hovered.get_name().length() * 15, 30, 100);
				fill(66);
				textSize(16);
				text(hovered.get_name(), mouseX + 20, mouseY + 5);
			}
		}
	}
	
	public void button_add() {
		for(Character c : characters) {
			if(!c.get_in_circle()) {
				c.set_in_circle(true);
				
			}
		}
		add_to_circle();
	}

	public void button_clear() {
		for(Character c : characters) {
			ani = Ani.to(c, (float) 1, "cur_x", c.get_init_x());
			ani = Ani.to(c, (float) 1, "cur_y", c.get_init_y());
			c.set_in_circle(false);
		}
		
	}
	
	private void loadData(){
		
		characters = new ArrayList<Character>();
		file = "starwars-episode-" + level + "-interactions.json";
		data = loadJSONObject(path + file);
		nodes = data.getJSONArray("nodes");
		links = data.getJSONArray("links");
		
		for(int i = 0; i < nodes.size(); i++) {
			JSONObject node = nodes.getJSONObject(i);
			String name = node.getString("name");
			String color = node.getString("colour");
			int x = i / 10;
			int y = (i > 9) ? i - i / 10 * 10 : i;
			Character character = new Character(this, name, color, x * 50,  y * 50);
			characters.add(character);
		}
		
		for(int i = 0; i < links.size(); i++) {
			JSONObject link = links.getJSONObject(i);
			int source = link.getInt("source");
			int target = link.getInt("target");
			int value = link.getInt("value");
			characters.get(source).add_target(characters.get(target), value);
			characters.get(target).add_target(characters.get(source), value);
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
	
	public void add_to_circle() {
		float angle = 0;
		int in_circle_num = 0;
		for(Character c : characters) {
			if (c.get_in_circle())
				in_circle_num++;
		}
		for(Character c : characters) {
			if(c.get_in_circle()) {
				ani = Ani.to(c, (float) 1, "cur_x", (width / 2) + 200 * cos(angle));
				ani = Ani.to(c, (float) 1, "cur_y", (height / 2 + 30) + 200 * sin(angle));
				angle += (TWO_PI / in_circle_num);
			}
		}
	}
	
	public void mousePressed() {
		if (is_hover) {
			locked = hovered;
		}
	}
	
	public void mouseDragged() {
		if (locked != null) {
			locked.cur_x = mouseX;
			locked.cur_y = mouseY;
		}
	}

	
	
	
	
	public void mouseReleased() {
		
		if(locked != null) {
			if(dist(locked.cur_x, locked.cur_y , width / 2, height / 2 + 30) < 250) {
				
				locked.set_in_circle(true);
				
				add_to_circle();
			}
			else {
				locked.set_in_circle(false);
				ani = Ani.to(locked, (float)1.0, "cur_x", locked.get_init_x());
				ani = Ani.to(locked, (float)1.0, "cur_y", locked.get_init_y());
			
				add_to_circle();
			}
		}
		locked = null;
	}
}