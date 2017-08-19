package cfg.node;

/**
 * @author lenovo
 * Chứa biểu thức đồng bộ chỉ số của câu lệnh rẽ nhánh
 */
public class SyncNode extends CFGNode {
	private String leftHand; 	// biến với chỉ số hiện tại	
	private String rightHand;	// giá trị cần đồng bộ
	
	public SyncNode() { }
	
	public SyncNode(String leftHand, String rightHand) {
		this.leftHand = leftHand;
		this.rightHand = rightHand;
	}

	public String getLeftHand() {
		return leftHand;
	}

	public void setLeftHand(String leftHand) {
		this.leftHand = leftHand;
	}

	public String getRightHand() {
		return rightHand;
	}

	public void setRightHand(String rightHand) {
		this.rightHand = rightHand;
	}
	
	public String getConstraint() {
		return leftHand + " = " + rightHand;
	}
	
	public String getPrefixConstraint() {
		return String.format("(= %s %s)", leftHand, rightHand);
	}
	
	@Override
	public String getFormula() {
		if (leftHand != null && rightHand != null) 
		return String.format("(= %s %s)", leftHand, rightHand);
		else return null;
		
	}

	public void printNode(){
		System.out.println( "SyncNode: " + this.leftHand + " = " + this.rightHand);
	}
	
	public String toString(){
		return "(" + leftHand + " = " + rightHand + ")";
	}
}
