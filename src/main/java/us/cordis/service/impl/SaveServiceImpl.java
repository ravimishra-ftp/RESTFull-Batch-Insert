package us.cordis.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import us.cordis.dao.SaveDao;
import us.cordis.entities.UserMaster;
import us.cordis.rest.model.EntityData;
import us.cordis.rest.model.UserList;
import us.cordis.service.SaveService;

@Service("saveService")
public class SaveServiceImpl implements SaveService{
	
	private static final Log log = LogFactory.getLog(SaveServiceImpl.class);
	
	@Resource(name = "SaveDao")
	SaveDao saveDao;

	@Override
	public void save(List<EntityData> rowsList, String tableName) throws Exception{
		log.info("Number of rows to insert: "+rowsList.size());
		saveDao.save(rowsList, tableName);
	}

	@Override
	public List<UserList> fetch(String tableName) throws Exception {
		List<UserMaster> userMasterList = saveDao.fetch(tableName);
		List<UserList> userList = new ArrayList<UserList>();
		UserList user = null;
		for(UserMaster userMaster : userMasterList){
			user = new UserList();
			user.setId(userMaster.getId());
			user.setUsername(userMaster.getUsername());
			user.setPassword(userMaster.getPassword());
			userList.add(user);
		}
		return userList;
	}

}
