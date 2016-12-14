package us.cordis.rest.common;

import javax.xml.bind.annotation.XmlElement;

import org.springframework.http.HttpStatus;

public abstract class RestResponse{

    private String message;
    private HttpStatus status;
    
    public void setStatus(HttpStatus status) {
        this.status = status;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @XmlElement(required = false)
    public HttpStatus getStatus() {
        return status;
    }

    @XmlElement(required = false)
    public String getMessage() {
        return message;
    }
}
