package us.cordis.rest.errorHandler;


public class CustomValidationException extends BaseException{

	private static final long serialVersionUID = 1L;
	
	private ValidationInfo errorInfo;

	public CustomValidationException(ValidationInfo errorInfo) {
		this.errorInfo = errorInfo;
	}

	public ValidationInfo getErrorInfo() {
		return errorInfo;
	}

	public void setErrorInfo(ValidationInfo errorInfo) {
		this.errorInfo = errorInfo;
	}
    
}
