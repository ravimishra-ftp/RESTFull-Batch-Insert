package us.cordis.service;

import java.util.List;

import us.cordis.rest.model.EntityData;
import us.cordis.rest.model.UserList;

public interface SaveService {

	public void save(List<EntityData> rowsList, String tableName) throws Exception;
	public List<UserList> fetch(String tableName) throws Exception;
}
