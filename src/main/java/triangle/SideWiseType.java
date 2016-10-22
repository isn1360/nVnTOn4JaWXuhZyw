package triangle;

/**
 * Shape property according to shape sides 
 */
public enum SideWiseType {
	
	EQUILATERAL("equilateral"),
	ISOSCELES("isosceles"),		// applicable for triangle only
	SCALENE("scalene");
	
	private String description = null;
	
	private SideWiseType(String description) {
		this.description = description;
	}
	
	public String getDescription() {
		return description;
	}
}
