package us.cordis.rest.common;

import java.io.StringWriter;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.http.HttpStatus;

import us.cordis.rest.errorHandler.BaseException;
import us.cordis.rest.errorHandler.CustomValidationException;
import us.cordis.rest.errorHandler.ErrorMessage;
import us.cordis.rest.errorHandler.ValidationInfo;

import com.fasterxml.jackson.databind.ObjectMapper;

public abstract class AbstractCommonController {
	
    protected final Log log = LogFactory.getLog(this.getClass());

    //can be used for acknowledgement of POST methods
    protected RestResponse handleDefaultRestReponse(String msg, HttpServletRequest request, HttpServletResponse response){
        CustomResponse info = new CustomResponse();
        info.setStatus(HttpStatus.OK);
        info.setMessage(msg);
        return info;
    }

	//can be used for acknowledgement with success count of POST methods
    protected RestResponse handleDefaultRestReponse(HttpServletRequest request,
            HttpServletResponse response, String msg, int successCount){
        CustomSuccessCountResponse info = new CustomSuccessCountResponse();
        info.setStatus(HttpStatus.OK);
        info.setMessage(msg);
        info.setSuccessCount(successCount);
        return info;
    }

    //can be used for GET method
	protected RestResponse handleSuccessRestReponse(RestResponse restResponse, String msg, HttpServletRequest request, HttpServletResponse response) {
		restResponse.setStatus(HttpStatus.OK);
		restResponse.setMessage(msg);
		return restResponse;
	}
	
    //can be used for jsonp response or any return type of controller 
	protected Object returnValue(String jsonpCallback, RestResponse restResponse,String msg,
			HttpServletRequest request,HttpServletResponse response) throws Exception {
	     String retStr = "";
	     if (jsonpCallback != null && jsonpCallback.length() > 0) {
	         retStr = convertToJson(restResponse);
	         retStr = jsonpCallback + "(" + retStr + ")";
	         response.setHeader("Content-Type", "text/javascript;charset=UTF-8");
	         response.setHeader("Cache-Control", "no-cache"); 
	         response.getWriter().write(retStr);
	     } else if (request.getHeader("Accept").equalsIgnoreCase("application/json")) {
	         retStr = convertToJson(restResponse);
	         response.setHeader("Cache-Control", "no-cache"); 
	         response.getWriter().write(retStr);
	     } else {
	    	 retStr = convertToXml(restResponse);
	    	 response.getWriter().write(retStr);
	         //return handleSuccessRestReponse(restResponse, msg, request, response);
	     }
	     return null;
	 }
	 
	//can be used for validation error
	protected Object handleValidationErrors(List<ErrorMessage> errors, HttpServletRequest request, HttpServletResponse response) {
		ValidationInfo validationInfo = new ValidationInfo();
		validationInfo.setStatus(HttpStatus.BAD_REQUEST);
		validationInfo.setMessage("VALIDATION_ERROR");
		validationInfo.setErrors(errors);
		for (ErrorMessage errorMessage : errors) {
			log.error("Error: " + errorMessage.getErrorMessage());
		}
		throw new CustomValidationException(validationInfo);
    }
	
	//can be used for custom exception
	protected BaseException handleBaseException(String errorMsg){
        BaseException be = new BaseException();
        be.setMessage(errorMsg);
        be.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
        return be;
       }
	
	private String convertToJson(RestResponse obj) throws Exception {
		ObjectMapper mapper = new ObjectMapper();
		String jsonString = mapper.writeValueAsString(obj);
		return jsonString;
	}
	 
	private String convertToXml(Object obj) throws Exception {
		JAXBContext jaxbContext = JAXBContext.newInstance(RestResponse.class);
		Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
		jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
		StringWriter sw = new StringWriter();
		jaxbMarshaller.marshal(obj, sw);
		return sw.toString();
	}

}
