package us.cordis.rest.errorHandler;


import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import org.springframework.http.HttpStatus;

@XmlRootElement(name="ErrorMessage")
@XmlAccessorType(XmlAccessType.FIELD)
public class ErrorMessage {

    private  HttpStatus errorStatus;
    private  String errorMessage;

    public ErrorMessage(){
    }
    
    public ErrorMessage(HttpStatus errorStatus, String errorMessage) {
        if (errorStatus == null) {
            errorStatus = HttpStatus.BAD_REQUEST;
        }
        this.errorStatus = errorStatus;
        this.errorMessage = errorMessage;
    }

    public HttpStatus getErrorStatus() {
        return errorStatus;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((errorMessage == null) ? 0 : errorMessage.hashCode());
        result = prime * result + ((errorStatus == null) ? 0 : errorStatus.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        ErrorMessage other = (ErrorMessage) obj;
        if (errorMessage == null) {
            if (other.errorMessage != null)
                return false;
        } else if (!errorMessage.equals(other.errorMessage))
            return false;
        if (errorStatus != other.errorStatus)
            return false;
        return true;
    }

   
}
