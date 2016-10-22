package triangle;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Immutable object, represents a triangle.
 */
public class Triangle extends Shape {
	
	public Triangle(double sideA, double sideB, double sideC) throws ShapeException {
		this(Arrays.asList(sideA, sideB, sideC));
	}
	
	public Triangle(final List<Double> sides) throws ShapeException {
		super(sides);
	}
	
	@Override
	protected void validateShape(List<Double> sides) throws ShapeException {
		super.validateShape(sides);	// basic checks by superclass
		
		// not less not more than 3 sides must be specified
		if (sides.size() != 3) throw new InvalidShapeException(String.format("Triangle must have exactly 3 sides. Found %d sides.", sides.size()));
	}

	@Override
	protected SideWiseType determineSideWiseType(final List<Double> sides) {
		// covers isoscalene and euqilateral
		SideWiseType shapeType = super.determineSideWiseType(sides);	// first see what superclass found out
		
		if (!SideWiseType.EQUILATERAL.equals(shapeType)) {
			// not equilateral, may still be isosceles
			Set<Double> sidesByLength = sides.stream().collect(Collectors.toSet());
			if (sidesByLength.size() == 2) {
				// two sides of the same length, one of another length -> isosceles
				shapeType = SideWiseType.ISOSCELES;
			}
		}
		
		// return whatever was determined
		return shapeType;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(getSideWiseType().getDescription());
		sb.append(" triangle { sides:").append(getSides()).append(" }");
		return sb.toString();
	}
}
