package cfg.build.index;

public class Variable {
	
	private String type; 
	private String name;
	private int index = -1; //mac dinh -1
	private boolean isDuplicated;
	
	public Variable() {}
	
	public Variable(String type, String name) {
		this.type = type;
		this.name = name;
		this.isDuplicated = false;
	}
	
	public Variable(String type, String name, int index) {
		this.type = type;
		this.name = name;
		this.index = index;
		this.isDuplicated = false;
	}
	
	public Variable(Variable other) {
		name = other.name;
		type = other.type;
		index = other.index;
		isDuplicated = other.isDuplicated;
	}
	
	public boolean getIsDuplicated(){
		return isDuplicated;
	}
	public void setIsDuplicated(boolean restart){
		this.isDuplicated = restart;
	}
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
	// -3 danh dau bien nhan gia tri tra ve cua ham
	public String getVariableWithIndex() {
		if (index == -3) return name;
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
