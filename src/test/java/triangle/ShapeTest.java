package triangle;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import org.junit.Test;

public class ShapeTest 
{
	@Test(expected=IllegalArgumentException.class)
    public void testNoSides() throws ShapeException {
		new Shape(null);
    }
	
	@Test(expected=InvalidShapeException.class)
    public void testOneSide() throws ShapeException {
		new Shape(sides(1.0d));
    }
	
	@Test(expected=InvalidShapeException.class)
    public void testTwoSides() throws ShapeException {
		new Shape(sides(1.0d, 2.0d));
    }
	
	@Test(expected=InvalidShapeException.class)
	public void testNullSide() throws ShapeException {
		new Shape(sides(1.0d, null, 2.0d));
	}
	
	@Test(expected=InvalidShapeException.class)
	public void testZeroSide() throws ShapeException {
		new Shape(sides(1.0d, 0.0d, 2.0d));
	}

	@Test(expected=InvalidShapeException.class)
	public void testNegativeSide() throws ShapeException {
		new Shape(sides(1.0d, -1.0d, 2.0d));
	}
	
	@Test(expected=InvalidShapeException.class)
	public void testSideTooLong() throws ShapeException {
		new Shape(sides(1.0d, 4.0d, 2.0d));
	}
	
	@Test
	public void testScalene() throws ShapeException {
		assertSideWiseType(SideWiseType.SCALENE, 2.0d, 3.0d, 4.0d);
		assertSideWiseType(SideWiseType.SCALENE, 32.0d, 29.0d, 15.0d);
	}
	
	@Test
	public void testEquilateral() throws ShapeException {
		assertSideWiseType(SideWiseType.EQUILATERAL, 2.0d, 2.0d, 2.0d);
		assertSideWiseType(SideWiseType.EQUILATERAL, 2.0e-6, 2.0e-6, 2.0e-6);
		assertSideWiseType(SideWiseType.EQUILATERAL, 2.0e+6, 2.0e+6, 2.0e+6);
	}
	
	private void assertSideWiseType(SideWiseType typeExpected, Double... sides) throws ShapeException {
		List<Double> sideList = sides(sides);
		assertEquals(String.format("Sides: %s.", String.valueOf(sideList)), typeExpected, new Shape(sideList).getSideWiseType());
	}
	
	private List<Double> sides(Double... sides) {
		List<Double> sideList = new ArrayList<Double>(sides.length);
		for (Double side : sides) {
			sideList.add(side);
		}
		return sideList;
	}
	
	
}
