package cfg.utils;

/**
  * new Variable(type, name, nameFunc)
  * new Variable(type, name, nameFunc, index)
  * Variable(other)
  * getVariableWithIndex()
  */
public class Variable {

	private String type; 
	private String name;
	
	private int index = -1; //mac dinh -1
	
	public Variable() {}
	
	public Variable(String type, String name, int index) {
		this.type = type;
		this.name = name;
		this.index = index;	
	}
	
	public Variable(String type, String name){
		this.type = type;
		this.name = name;
	}
	
	public Variable(Variable other) {
		name = other.name;
		type = other.type;
		index = other.index;
		
	}
	
	//public void reset(){
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getIndex() {
		return index;
	}
	public void setIndex(int index) {
		this.index = index;
	}
	
	public void increase() {
		index++;
	}
	
	public String getVariableWithIndex() {
		return name + "_" + index;
	}
	
	public Variable clone() {
		return new Variable(this);
	}
	
	public String toString() {
		return "type: " + type + ", name: " + name + ", index: " + index;
	}

	public boolean hasInitialized() {
		return index > -1;
	}
	
}
