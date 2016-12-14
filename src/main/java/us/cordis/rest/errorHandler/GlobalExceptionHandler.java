package us.cordis.rest.errorHandler;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.TypeMismatchException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import org.springframework.web.servlet.mvc.multiaction.NoSuchRequestHandlingMethodException;

import us.cordis.rest.common.CustomResponse;
import us.cordis.rest.common.RestResponse;


@ControllerAdvice 
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    protected final Log log = LogFactory.getLog(GlobalExceptionHandler.class);
    
    
    @Override
    protected ResponseEntity<Object> handleHttpMediaTypeNotAcceptable(
            HttpMediaTypeNotAcceptableException ex, HttpHeaders headers,
            HttpStatus status, WebRequest request) {
        return new ResponseEntity<Object>(errorResponse(request,ex,status,headers), headers, status);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMediaTypeNotSupported(
            HttpMediaTypeNotSupportedException ex, HttpHeaders headers,
            HttpStatus status, WebRequest request) {
           return new ResponseEntity<Object>(errorResponse(request,ex,status,headers), headers, status);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(
            HttpMessageNotReadableException ex, HttpHeaders headers,
            HttpStatus status, WebRequest request) {
        return new ResponseEntity<Object>(errorResponse(request,ex,status,headers), headers, status);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex, HttpHeaders headers,
            HttpStatus status, WebRequest request) {
        return new ResponseEntity<Object>(errorResponse(request,ex,status,headers), headers, status);
    }

    @Override
    protected ResponseEntity<Object> handleMissingServletRequestParameter(
            MissingServletRequestParameterException ex, HttpHeaders headers,
            HttpStatus status, WebRequest request) {
        return new ResponseEntity<Object>(errorResponse(request,ex,status,headers), headers, status);
    }

    @Override
    protected ResponseEntity<Object> handleNoSuchRequestHandlingMethod(
            NoSuchRequestHandlingMethodException ex, HttpHeaders headers,
            HttpStatus status, WebRequest request) {
        return new ResponseEntity<Object>(errorResponse(request,ex,status,headers), headers, status);
    }

    @Override
    protected ResponseEntity<Object> handleTypeMismatch(
            TypeMismatchException ex, HttpHeaders headers, HttpStatus status,
            WebRequest request) {
        return new ResponseEntity<Object>(errorResponse(request,ex,status,headers), headers, status);
    }

    @Override
    protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(
            HttpRequestMethodNotSupportedException ex, HttpHeaders headers,
            HttpStatus status, WebRequest request) {
        return new ResponseEntity<Object>(errorResponse(request,ex,status,headers), headers, status);
    }
    
    private RestResponse errorResponse(WebRequest request,Exception ex,HttpStatus status,HttpHeaders headers){
        ServletWebRequest req = (ServletWebRequest)request;
        String logErrorMsg = "Error processing request at "+req.getRequest().getRequestURI()+":user="+request.getRemoteUser();
        log.error(logErrorMsg,ex);
        String errorMsg = ex.getMessage();
        CustomResponse error = new CustomResponse();
        error.setStatus(status);
        error.setMessage(errorMsg);
        headers.setContentType(getMediaType(request));
        return error;
    }
    
    private MediaType getMediaType(WebRequest request){
        if (request.getHeader("Accept").equalsIgnoreCase("application/json")){
            return MediaType.APPLICATION_JSON;
        }else{
            return MediaType.APPLICATION_XML;
        }
    }

    @ResponseBody 
    @ResponseStatus(value=HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(BaseException.class)
    public RestResponse handleException(HttpServletRequest request, HttpServletResponse response, BaseException e){
        String errorMsg = "Error processing request at "+request.getRequestURI()+":URL="+request.getRequestURL()+":user="+request.getRemoteUser();
        log.error(errorMsg,e);
        CustomResponse info = new CustomResponse();
        info.setStatus(e.getStatus());
        info.setMessage(e.getMessage());	
        return info;
    }
    
    @ResponseBody
    @ResponseStatus(value=HttpStatus.BAD_REQUEST)
    @ExceptionHandler(CustomValidationException.class)
    public RestResponse handleValidationException(HttpServletRequest request, HttpServletResponse response, CustomValidationException ex){
        String errorMsg = "Error validating request at "+request.getRequestURI()+":URL="+request.getRequestURL()+":user="+request.getRemoteUser();
        log.error(errorMsg,ex);
        return ex.getErrorInfo();
    }

}
