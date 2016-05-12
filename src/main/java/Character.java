package main.java;

//import java.util.ArrayList;
import java.util.HashMap;
public class Character {

	private MainApplet parent;
	private String name;
	public float cur_x, cur_y;
	private float init_x, init_y;
	private int color;
	private int radius = 40;
	private boolean in_circle = false, hover = false;
	private int weight = 0;
	private HashMap<Character, Integer> relations;

	@SuppressWarnings("static-access")
	public Character(MainApplet parent, String name, String color, int x, int y) {
		this.parent = parent;
		this.name = name;
		this.cur_x = x + 30;
		this.cur_y = y + 30;
		this.init_x = this.cur_x;
		this.init_y = this.cur_y;
		this.color = (int) Long.parseLong(color.replace("#", ""), 16);
		this.relations = new HashMap<Character, Integer>();
	}

	public void display() {
		parent.noStroke();
		parent.fill(color);
		parent.ellipse(this.cur_x, this.cur_y, radius, radius);
		
		if (in_circle) {
			for (Character c : relations.keySet()) {
				parent.noFill();
				parent.stroke(0);
				if(hover)
					parent.strokeWeight(this.relations.get(c));
				else
					parent.strokeWeight(weight);
					
				float a = (600 + (cur_x + c.cur_x) / 2) / 2;
				float b = (355 + (cur_y + c.cur_y) / 2) / 2;
				if (c.in_circle)
					parent.bezier(cur_x, cur_y, a, b, a, b, c.cur_x, c.cur_y);
			}
		}
	}

	
	public String get_name() {
		return this.name;
	}

	
	public float get_init_x() {
		return init_x;
	}

	
	public float get_init_y() {
		return init_y;
	}
	
	public boolean get_in_circle() {
		return this.in_circle;
	}
	
	public HashMap<Character, Integer> get_relations()
	{
		return this.relations;
	}


	public void set_radius(int radius) {
		this.radius = radius;
	}

	
	public void set_weight(int weight) {
		this.weight = weight;
	}
	
	public void set_hover(boolean b) {
		this.hover = b;
	}
	
	public void set_in_circle(boolean b) {
		this.in_circle = b;
	}

	
	public void add_target(Character ch, int value) {
		this.relations.put(ch, value);
	}

}