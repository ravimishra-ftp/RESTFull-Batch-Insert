package us.cordis.rest.controller;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import us.cordis.rest.common.AbstractCommonController;
import us.cordis.rest.common.RestResponse;
import us.cordis.rest.errorHandler.ErrorMessage;
import us.cordis.rest.model.ColumnList;
import us.cordis.rest.model.EntityData;
import us.cordis.rest.model.InsertRowsRequest;
import us.cordis.rest.model.UserList;
import us.cordis.rest.model.Users;
import us.cordis.service.SaveService;

@Controller
public class SaveController extends AbstractCommonController{
	
	private static final Log log = LogFactory.getLog(SaveController.class);

    @Resource(name = "saveService")
    public SaveService saveService;
	
	@RequestMapping(value = "/insertRows/{tableName}", method = { RequestMethod.POST })
	public @ResponseBody RestResponse insertRows(HttpServletRequest request, HttpServletResponse response,
			@RequestBody InsertRowsRequest insertRowsRequest,
			@PathVariable String tableName) throws Exception{
		
		long startTime = System.currentTimeMillis();
		String msg = "Request Recieved to insert rows into table";
		log.info(msg);
		
		validateInsertRequest(insertRowsRequest, tableName, request, response);
		msg = "Request validated to insert rows into table";
		log.info(msg);
		
		try {
			saveService.save(insertRowsRequest.getEntityData(), tableName);
			msg = "Successfully inserted rows into table: "+tableName;
			log.info(msg);
			return handleDefaultRestReponse(msg, request, response);
		} catch (Exception e) {
			msg = "Error occurred while inserting rows into the table: "+tableName;
			log.error(msg, e);
			throw handleBaseException(msg + ", Root Cause: " + ExceptionUtils.getRootCause(e));
		} finally {
			msg = "Insertion into table took " + (System.currentTimeMillis() - startTime) + " ms";
			log.info(msg);
		}
	}
	
	
	@RequestMapping(value = "/fetchRows/{tableName}", method = { RequestMethod.GET })
	public @ResponseBody RestResponse fetchRows(HttpServletRequest request, HttpServletResponse response,
			@PathVariable String tableName) throws Exception{
		
		long startTime = System.currentTimeMillis();
		String msg = "Request Recieved to fetch rows from table";
		log.info(msg);
		
		validateFetchingRequest(tableName, request, response);
		msg = "Request validated to fetch rows from table";
		log.info(msg);
		
		try {
			List<UserList> userList = saveService.fetch(tableName);
			if(userList!=null && userList.size()>0){
				Users users = new Users();
				users.setUserList(userList);
				msg = "Successfully fetched rows from table: "+tableName;
				log.info(msg);
				return handleSuccessRestReponse(users, msg, request, response);
			}else{
				msg = "No record available in the table" + tableName;
				return handleDefaultRestReponse(msg, request, response);
			}
		} catch (Exception e) {
			msg = "Error occurred while fetching rows from the table: "+tableName;
			log.error(msg, e);
			throw handleBaseException(msg + ", Root Cause: " + ExceptionUtils.getRootCause(e));
		} finally {
			msg = "fetching rows from table took " + (System.currentTimeMillis() - startTime) + " ms";
			log.info(msg);
		}
	}
	
	private boolean validateInsertRequest(InsertRowsRequest insertRowsRequest, String tableName
			, HttpServletRequest request, HttpServletResponse response) throws Exception{
        List<ErrorMessage> listOfAllErrors = new ArrayList<ErrorMessage>();
        
        if(tableName == null){
        	listOfAllErrors.add(new ErrorMessage(HttpStatus.BAD_REQUEST, "TABLE_NAME_NOT_AVAILABLE"));
        }
        if(insertRowsRequest == null || insertRowsRequest.getEntityData() == null || insertRowsRequest.getEntityData().size() == 0){
        	listOfAllErrors.add(new ErrorMessage(HttpStatus.BAD_REQUEST, "REQUEST_BODY_NOT_AVAILABLE"));
        }
        if(insertRowsRequest.getEntityData() != null && insertRowsRequest.getEntityData().size()>0){
        	for(EntityData entityData: insertRowsRequest.getEntityData()){
    			for(ColumnList column: entityData.getColumnList()){
    	            if(column.getKey() == null || column.getValue() == null){
    	                listOfAllErrors.add(new ErrorMessage(HttpStatus.BAD_REQUEST, "COLUMN_NAME_OR_VALUE_IS_NULL"));
    	                break;
    	            }
    			}
    		}
        }
        if(!listOfAllErrors.isEmpty()){
            handleValidationErrors(listOfAllErrors, request, response);
            return false;
        }
        return true;    
    }
	
	private boolean validateFetchingRequest(String tableName
			, HttpServletRequest request, HttpServletResponse response) throws Exception{
        List<ErrorMessage> listOfAllErrors = new ArrayList<ErrorMessage>();
        
        if(tableName == null){
        	listOfAllErrors.add(new ErrorMessage(HttpStatus.BAD_REQUEST, "TABLE_NAME_NOT_AVAILABLE"));
        }
        if(!listOfAllErrors.isEmpty()){
            handleValidationErrors(listOfAllErrors, request, response);
            return false;
        }
        return true;    
    }
	
}
