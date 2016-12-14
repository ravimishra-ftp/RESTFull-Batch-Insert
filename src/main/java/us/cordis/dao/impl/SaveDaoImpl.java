package us.cordis.dao.impl;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import us.cordis.dao.SaveDao;
import us.cordis.entities.UserMaster;
import us.cordis.rest.model.ColumnList;
import us.cordis.rest.model.EntityData;

public class SaveDaoImpl implements SaveDao{
	
	private static final Log log = LogFactory.getLog(SaveDaoImpl.class);

	private SessionFactory sessionFactory;
	
	@Override
	public void save(List<EntityData> rowsList, String tableName) throws Exception {
		int count = 0;
		Session session = this.sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		for(EntityData entityData: rowsList){
			Query query = session.createSQLQuery(this.getHibernateNativeQuery(entityData.getColumnList(), tableName));
			for(ColumnList column: entityData.getColumnList()){
				query.setParameter(column.getKey(), column.getValue());
			}
			query.executeUpdate();
			count++;
			if (count % 50 == 0) {
				session.flush();
				session.clear();
			}
		}
		tx.commit();
		session.close();
		log.info("Batch insert finished and committed");
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<UserMaster> fetch(String tableName) throws Exception {
		Session session = this.sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		List<UserMaster> userMasterList = (List<UserMaster>) session.createQuery("FROM "+tableName).list();
		tx.commit();
		session.close();
		log.info(userMasterList.size()+" row fetched");
		return userMasterList;
	}
	
	private String getHibernateNativeQuery(List<ColumnList> list, String tableName){
		String columns = list.toString().substring(1, list.toString().length()-1);
		String bindParams = ":"+columns.replaceAll(", ", ",:");
	return "insert into "+tableName+"("+columns+")"+" values("+bindParams+")";
	}
		
	public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

}
