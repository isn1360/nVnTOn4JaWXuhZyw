package triangle;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Immutable object, represents a shape. Should be extended for specific shapes. 
 */
public class Shape {

	private SideWiseType sideWiseType;	// set by analyzeShape()
	private List<Double> sides;
	
	public Shape(final List<Double> sides) throws ShapeException {
		if (sides == null) throw new IllegalArgumentException("sides must not be null.");
		
		this.sides = Collections.unmodifiableList(sides);	// make the list unmodifiable
		
		// check if the shape is valid
		validateShape(getSides());
		
		// determine shape properties
		analyzeShape(getSides());
	}
	
	public List<Double> getSides() {
		return sides;
	}
	
	public SideWiseType getSideWiseType() {
		return sideWiseType;
	}
	
	/**
	 * Checks if the shape is possible according to specified sides.
	 * The most general checks available here are:
	 * <ul>
	 * <li>there must be at least 3 sides</li>
	 * <li>all sides must be greater than zero</li>
	 * <li>one single side must not be longer than the sum of all other sides</li>
	 * </ul>
	 * Additional checks may be implemented in subclass' override of this method.
	 * 
	 * @param sides
	 * @throws ShapeException If shape is not valid
	 */
	protected void validateShape(final List<Double> sides) throws ShapeException {
		// at least 3 sides
		if (sides.size() < 3) throw new InvalidShapeException("There must be at least 3 sides for a shape.");
		
		// all sides must be positive
		if (sides.stream().anyMatch(side -> side == null || side <= 0.0)) {
			throw new InvalidShapeException("All sides must have positive length.");
		}
		
		// to make the shape possible not one single side must be equal or longer than the sum of all other sides
		double totalSideLength = sides.stream().mapToDouble(side -> side).sum();
		double maxSide = sides.stream().mapToDouble(side -> side).max().orElse(Double.MAX_VALUE);	// optional should always have a value - but just in case
		
		if (maxSide > totalSideLength - maxSide) {
			// the largest side is longer than the sum of all other sides
			throw new InvalidShapeException(String.format("Invalid shape: side '%f' is too long.", maxSide));
		}
	}
	
	private void analyzeShape(final List<Double> sides) {
		// let the subclass determine type if it overrides determineSideWiseType() but don't let it change the type later
		this.sideWiseType = determineSideWiseType(sides);
	}
	
	/**
	 * Determines shape type according to sides. 
	 * Should be overridden by subclass if other specific types are possible. 
	 * 
	 * @param sides Shape sides
	 * @return Either {@link SideWiseType.EQUILATERAL} or {@link SideWiseType.SCALENE}
	 */
	protected SideWiseType determineSideWiseType(final List<Double> sides) {
		if (sides == null) throw new IllegalArgumentException("sides must not be null.");
		
		Set<Double> sidesByLength =  sides.stream().collect(Collectors.toSet());
		if (sidesByLength.size() == 1) {
			// all sides are of equal length -> equilateral
			return SideWiseType.EQUILATERAL;
		} 
		
		// not equilateral, any other type cannot be determined at the moment 
		return SideWiseType.NONE;
	}
	

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(sideWiseType.getDescription());
		sb.append(" shape { sides:").append(sides).append(" }");
		return sb.toString();
	}
}
