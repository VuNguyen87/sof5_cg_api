/**
 * 
 */
package com.fis.customs.api.ecustomsgw.handler.qldmc;

import org.springframework.stereotype.Service;

import com.fis.customs.api.ecustomsgw.handler.WebApiBaseProcessorEcustomsGwImpl;
import com.fis.customs.core.ecustomsgw.common.EcustomsGwKeySearch;
import com.fis.customs.core.ecustomsgw.enums.EHibernateCfgEcustomsGw;
import com.fis.customs.dao.ecustomsgw.ParamsDAO;
import com.fis.customs.entity.ecustomsgw.ParamsEntity;
import com.fis.customs.util.ecargo.ECARGOException;

import vn.fis.sof5.core.api.RequestData;
import vn.fis.sof5.core.common.JacksonEx;

/**
 * @author QuyenNN - 08/05/2018
 *
 */

@Service("QlDmc06Processor")
public class QlDmc06Processor extends WebApiBaseProcessorEcustomsGwImpl<EcustomsGwKeySearch, ParamsEntity, ParamsDAO> {

	private JacksonEx jacksonEx = JacksonEx.getInstance();

	public QlDmc06Processor() {
		daoBase = ParamsDAO.getInstance();
	}

	@Override
	public Object insertItem(RequestData requestData) throws ECARGOException {
		objBaseEntity = jacksonEx.objectNode2Object(requestData.getData(), ParamsEntity.class);
		daoBase.insertItem(cacheSession.get(EHibernateCfgEcustomsGw.VNACCSVA), objBaseEntity);
		daoBase.insertItemHIS(cacheSession.get(EHibernateCfgEcustomsGw.VNACCSVA), objBaseEntity, "INSERT");
		return true;
	}

	@Override
	public Object updateItem(RequestData requestData) throws ECARGOException {
		objBaseEntity = jacksonEx.objectNode2Object(requestData.getData(), ParamsEntity.class);
		daoBase.updateItem(cacheSession.get(EHibernateCfgEcustomsGw.VNACCSVA), objBaseEntity);
		daoBase.insertItemHIS(cacheSession.get(EHibernateCfgEcustomsGw.VNACCSVA), objBaseEntity, "UPDATE");
		return true;
	}

	@Override
	public Object deleteItem(RequestData requestData) throws ECARGOException {
		objBaseEntity = jacksonEx.objectNode2Object(requestData.getData(), ParamsEntity.class);
		daoBase.delete(cacheSession.get(EHibernateCfgEcustomsGw.VNACCSVA), objBaseEntity);
		daoBase.insertItemHIS(cacheSession.get(EHibernateCfgEcustomsGw.VNACCSVA), objBaseEntity, "DELETE");
		return true;
	}

	@Override
	public Object selectList(RequestData requestData) throws ECARGOException {
		objBaseSearch = jacksonEx.objectNode2Object(requestData.getData(), EcustomsGwKeySearch.class);
		if (objBaseSearch.isFlag()) {
			return daoBase.selectAll(cacheSession.get(EHibernateCfgEcustomsGw.VNACCSVA));
		} else {
			return daoBase.selectByOther(cacheSession.get(EHibernateCfgEcustomsGw.VNACCSVA), objBaseSearch);
		}
	}

	@Override
	public Object selectItem(RequestData requestData) throws ECARGOException {
		objBaseEntity = jacksonEx.objectNode2Object(requestData.getData(), ParamsEntity.class);
		return daoBase.selectItemByKey(cacheSession.get(EHibernateCfgEcustomsGw.VNACCSVA), objBaseEntity);
	}
	
	
}
