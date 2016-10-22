package triangle;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class TriangleTest 
{
	@Test
	public void testListConstructor() throws ShapeException {
		new Triangle(sides(1.0d, 2.5d, 3.0d));
	}
	
	@Test(expected=InvalidShapeException.class)
	public void testOneSide() throws ShapeException {
		new Triangle(sides(1.0d));
	}
	
	@Test(expected=InvalidShapeException.class)
	public void testTwoSides() throws ShapeException {
		new Triangle(sides(1.0d, 2.0d));
	}
	
	@Test(expected=InvalidShapeException.class)
	public void testFourSides() throws ShapeException {
		new Triangle(sides(1.0d, 2.0d, 3.0d, 3.5d));
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
	
	@Test
	public void testIsosceles() throws ShapeException {
		assertSideWiseType(SideWiseType.ISOSCELES, 2.0d, 3.0d, 2.0d);
		assertSideWiseType(SideWiseType.ISOSCELES, 3.0d, 2.0d, 2.0d);
		assertSideWiseType(SideWiseType.ISOSCELES, 2.0d, 2.0d, 3.0d);
		assertSideWiseType(SideWiseType.ISOSCELES, 2.0e-6, 3.0e-6, 2.0e-6);
		assertSideWiseType(SideWiseType.ISOSCELES, 2.0e+6, 3.0e+6, 2.0e+6);
	}
	
	
	private void assertSideWiseType(SideWiseType typeExpected, Double... sides) throws ShapeException {
		List<Double> sideList = sides(sides);
		assertEquals(String.format("Sides: %s.", String.valueOf(sideList)), typeExpected, new Triangle(sideList).getSideWiseType());
	}
	
	
	private List<Double> sides(Double... sides) {
		List<Double> sideList = new ArrayList<Double>(sides.length);
		for (Double side : sides) {
			sideList.add(side);
		}
		return sideList;
	}
}
