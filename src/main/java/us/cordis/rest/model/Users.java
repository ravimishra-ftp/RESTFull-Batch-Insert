package us.cordis.rest.model;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import us.cordis.rest.common.RestResponse;

import com.fasterxml.jackson.annotation.JsonProperty;

@XmlRootElement(name="Users")
@XmlAccessorType(XmlAccessType.FIELD)
public class Users extends RestResponse{

    @XmlElement(name = "userList")
    @JsonProperty("userList")
	private List<UserList> userList;

	public List<UserList> getUserList() {
		return userList;
	}

	public void setUserList(List<UserList> userList) {
		this.userList = userList;
	}
}
