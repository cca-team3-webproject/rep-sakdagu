package casestudy.business.service;
/**
 * 중복된 정보가 존재할 경우 발생하는 예외
 * @author ZAHANote
 *
 */
public class DataDuplicatedException extends Exception {
	private static final long serialVersionUID = 1L;

	public DataDuplicatedException() {
		super();
		// TODO Auto-generated constructor stub
	}

	public DataDuplicatedException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	public DataDuplicatedException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	public DataDuplicatedException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}

}
