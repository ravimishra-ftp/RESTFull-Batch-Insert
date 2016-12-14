package us.cordis.rest.common;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="CustomSuccessCountResponse")
@XmlAccessorType(XmlAccessType.FIELD)
public class CustomSuccessCountResponse extends RestResponse{
    
    private int successCount;
    
    
    public int getSuccessCount() {
        return successCount;
    }
    
    public void setSuccessCount(int successCount) {
        this.successCount = successCount;
    }
}
