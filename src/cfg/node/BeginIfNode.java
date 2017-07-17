package cfg.node;

import java.io.Serializable;

public class BeginIfNode extends BeginNode implements Serializable{
	
	public BeginIfNode(){

	}
	public void printNode() {
		System.out.println("BeginIfNode");
	}
}
