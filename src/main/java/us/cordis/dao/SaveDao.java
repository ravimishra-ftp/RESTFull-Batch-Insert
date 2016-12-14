package us.cordis.dao;

import java.util.List;

import us.cordis.entities.UserMaster;
import us.cordis.rest.model.EntityData;


public interface SaveDao {

	public void save(List<EntityData> rowsList, String tableName) throws Exception;
	public List<UserMaster> fetch(String tableName) throws Exception;
}
