package triangle;

/**
 * Shape property according to shape sides 
 */
public enum SideWiseType {
	
	NONE(""),
	EQUILATERAL("equilateral"),
	ISOSCELES("isosceles"),		// applicable for triangle only
	SCALENE("scalene");			// applicable for triangle only
	
	private String description = null;
	
	private SideWiseType(String description) {
		this.description = description;
	}
	
	public String getDescription() {
		return description;
	}
}
