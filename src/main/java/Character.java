//package main.java;

import java.util.ArrayList;
import java.util.HashMap;
import processing.core.PApplet;

/**
* This class is used to store states of the characters in the program.
* You will need to declare other variables depending on your implementation.
*/
public class Character {
	
	private MainApplet parent;
	private String name;
	private int color, weight, radius;
	private float cur_x, cur_y, init_x, init_y;
	//private ArrayList<Character> targets;
	private HashMap<Character, Integer> relations;
	private boolean in_circle;

	public Character(MainApplet parent, String name, int color, float x, float y){

		this.parent = parent;
		this.name = name;
		this.color = color;
		this.init_x = x;
		this.init_y = y;
		this.cur_x = this.init_x;
		this.cur_y = this.init_y;
		this.radius = 40;
		//this.targets = new ArrayList<Character>();
		this.relations = new HashMap<Character, Integer>();
	}

	public void display(){
		this.parent.fill(this.color);
		this.parent.ellipse(this.cur_x, this.cur_y, this.radius, this.radius);
		
		if (in_circle) {
			for (Character c : relations.keySet()) {
				this.parent.noFill();
				this.parent.stroke(0);
				this.parent.strokeWeight(this.relations.get(c));
				float a = (600 + (cur_x + c.cur_x) / 2) / 2;
				float b = (355 + (cur_y + c.cur_y) / 2) / 2;
				if (c.in_circle)
					parent.bezier(cur_x, cur_y, a, b, a, b, c.cur_x, c.cur_y);
			}
		}
	}
	
	public void return_orign_palce()
	{
		this.cur_x = this.init_x;
		this.cur_y = this.init_y;
	}
	
	public void add_target(Character ch, int weight) {
		//targets.add(ch);
		//weights.add(weight);
		this.relations.put(ch, weight);
	}
	
	public float get_init_x()
	{
		return this.init_x;
	}
	
	public float get_init_y()
	{
		return this.init_x;
	}
	
	public String get_name()
	{
		return this.name;
	}
}