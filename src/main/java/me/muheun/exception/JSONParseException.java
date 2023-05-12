package me.muheun.exception;

public class JSONParseException extends RuntimeException {
	static final long serialVersionUID = -4086729973971783390L;

	/**
	 * Creates exception with the specified message. If you are wrapping another exception, consider using {@link #JSONParseException(String, Throwable)} instead.
	 * 
	 * @param msg
	 *            error message describing a possible cause of this exception.
	 */
	public JSONParseException(String msg) {
		super(msg);
	}

	/**
	 * Creates exception with the specified message and cause.
	 * 
	 * @param msg
	 *            error message describing what happened.
	 * @param cause
	 *            root exception that caused this exception to be thrown.
	 */
	public JSONParseException(String msg, Throwable cause) {
		super(msg, cause);
	}

	/**
	 * Creates exception with the specified cause. Consider using {@link #JSONParseException(String, Throwable)} instead if you can describe what happened.
	 * 
	 * @param cause
	 *            root exception that caused this exception to be thrown.
	 */
	public JSONParseException(Throwable cause) {
		super(cause);
	}
}
