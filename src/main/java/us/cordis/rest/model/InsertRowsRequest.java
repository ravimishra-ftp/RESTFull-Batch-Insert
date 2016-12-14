package us.cordis.rest.model;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import us.cordis.rest.common.RestResponse;

import com.fasterxml.jackson.annotation.JsonProperty;

@XmlRootElement(name="InsertRowsRequest")
@XmlAccessorType(XmlAccessType.FIELD)
public class InsertRowsRequest extends RestResponse{

    @XmlElement(name = "entityData")
    @JsonProperty("entityData")
	private List<EntityData> entityData;

	public List<EntityData> getEntityData() {
		return entityData;
	}

	public void setEntityData(List<EntityData> entityData) {
		this.entityData = entityData;
	}

}
