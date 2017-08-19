package cfg.node;

import java.io.Serializable;

@SuppressWarnings("serial")
public class BeginForNode extends BeginNode implements Serializable{
	public BeginForNode(){}
	
	public void printNode() {
		System.out.println("BeginForNode ");
	}
	
}
