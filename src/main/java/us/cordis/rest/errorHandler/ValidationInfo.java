package us.cordis.rest.errorHandler;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import us.cordis.rest.common.RestResponse;

import com.fasterxml.jackson.annotation.JsonProperty;

@XmlRootElement(name="ValidationInfo")
@XmlAccessorType(XmlAccessType.FIELD)
public class ValidationInfo extends RestResponse{

    @XmlElement(name = "Errors")
    @JsonProperty("Errors")
    List<ErrorMessage> errors;

    public List<ErrorMessage> getErrors() {
        return errors;
    }

    public void setErrors(List<ErrorMessage> errors) {
        this.errors = errors;
    }
    
    
    
}
