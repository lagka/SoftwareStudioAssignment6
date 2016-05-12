package main.java;

//import java.util.ArrayList;
import java.util.HashMap;
public class Character {

	private MainApplet parent;
	public String name;
	float cur_x, cur_y;
	float init_x, init_y; // Initial position of X and Y
	private int color;
	public int diameter = 40;
	boolean inBigCircle = false, hover = false;
	
	//private ArrayList<Character> targets; // store the target
	//private ArrayList<Character> sources; // store the source
	public int weight = 0; // the weight of line
	private HashMap<Character, Integer> relations;

	@SuppressWarnings("static-access")
	public Character(MainApplet parent, String name, String color, int x, int y) {
		this.parent = parent;
		this.name = name;
		this.cur_x = x + 30;
		this.cur_y = y + 30;
		this.init_x = this.cur_x;
		this.init_y = this.cur_y;
		this.color = this.parent.unhex(color.substring(1));
		//targets = new ArrayList<Character>();
		//sources = new ArrayList<Character>();
		this.relations = new HashMap<Character, Integer>();
	}

	public void display() {
		parent.noStroke();
		parent.fill(color);
		parent.ellipse(this.cur_x, this.cur_y, diameter, diameter);
		// if inBigCircle equals to true, do the network
		if (inBigCircle) {
			// For the draw two times.
			// I want to check the node have something to do with which nodes.
			// This is the reason why I draw the line from target to source
			// and from source to target.
			for (Character c : relations.keySet()) {
				parent.noFill();
				parent.stroke(0);
				if(hover)
					parent.strokeWeight(this.relations.get(c));
				else
					parent.strokeWeight(weight);
					
				float a = (600 + (cur_x + c.cur_x) / 2) / 2;
				float b = (355 + (cur_y + c.cur_y) / 2) / 2;
				if (c.inBigCircle)
					parent.bezier(cur_x, cur_y, a, b, a, b, c.cur_x, c.cur_y);
			}
		}
	}

	// return the node's name
	public String getName() {
		return this.name;
	}

	// return the initial position of X
	public float getInitX() {
		return init_x;
	}

	// return the initial position of Y
	public float getInitY() {
		return init_y;
	}
	
	public HashMap<Character, Integer> get_relations()
	{
		return this.relations;
	}

	// set the diameter of the node
	public void setDiameter(int diameter) {
		this.diameter = diameter;
	}

	// set the weight of line
	public void setWeight(int weight) {
		this.weight = weight;
	}
	
	public void set_hover(boolean b) {
		this.hover = b;
	}

	// add the target of JSONObject to the list(targets)
	public void addTarget(Character ch, int value) {
		this.relations.put(ch, value);
	}
}