package triangle;

/**
 * Thrown when {@link #Shape} object is being created sides that cannot comprise a valid 2D object.
 */
public class InvalidShapeException extends ShapeException {
	private static final long serialVersionUID = 1L;

	public InvalidShapeException(String message) {
		super(message);
	}
}
