package com.fis.customs.api.ecustomsgw.handler.qldmc;

import org.springframework.stereotype.Service;

import com.fis.customs.api.ecustomsgw.handler.WebApiBaseProcessorEcustomsGwImpl;
import com.fis.customs.core.ecustomsgw.common.EcustomsGwKeySearch;
import com.fis.customs.core.ecustomsgw.enums.EHibernateCfgEcustomsGw;
import com.fis.customs.dao.ecustomsgw.HangCoDkDAO;
import com.fis.customs.entity.ecustomsgw.HangCoDkEntity;
import com.fis.customs.util.ecargo.ECARGOException;

import vn.fis.sof5.core.api.RequestData;
import vn.fis.sof5.core.common.JacksonEx;

/**
 * @author AnhNH70 - 8 May 2018
 */

@Service("QlDmc0201Processor")
public class QlDmc0201Processor
		extends WebApiBaseProcessorEcustomsGwImpl<EcustomsGwKeySearch, HangCoDkEntity, HangCoDkDAO> {
	private JacksonEx jacksonEx = JacksonEx.getInstance();
	public QlDmc0201Processor() {
		daoBase = HangCoDkDAO.getInstance();
	}

	@Override
	public Object selectList(RequestData requestData) throws ECARGOException {
		objBaseSearch = jacksonEx.objectNode2Object(requestData.getData(), EcustomsGwKeySearch.class);
		if (objBaseSearch == null) {
			return daoBase.selectAll(cacheSession.get(EHibernateCfgEcustomsGw.VNACCSVA));
		} else {
			return daoBase.selectByOther(cacheSession.get(EHibernateCfgEcustomsGw.VNACCSVA), objBaseSearch);
		}
	}

	@Override
	public Object updateItem(RequestData requestData) throws ECARGOException {
		objBaseEntity = jacksonEx.objectNode2Object(requestData.getData(), HangCoDkEntity.class);
		daoBase.updateItem(cacheSession.get(EHibernateCfgEcustomsGw.VNACCSVA), objBaseEntity);
		daoBase.insertInHis(cacheSession.get(EHibernateCfgEcustomsGw.VNACCSVA), objBaseEntity, "UPDATE");
		return Boolean.TRUE;
	}

	@Override
	public Object insertItem(RequestData requestData) throws ECARGOException {
		objBaseEntity = jacksonEx.objectNode2Object(requestData.getData(), HangCoDkEntity.class);
		daoBase.insertItem(cacheSession.get(EHibernateCfgEcustomsGw.VNACCSVA), objBaseEntity);
		daoBase.insertInHis(cacheSession.get(EHibernateCfgEcustomsGw.VNACCSVA), objBaseEntity, "INSERT");
		return Boolean.TRUE;
	}

	@Override
	public Object deleteItem(RequestData requestData) throws ECARGOException {
		objBaseEntity = jacksonEx.objectNode2Object(requestData.getData(), HangCoDkEntity.class);
		daoBase.delete(cacheSession.get(EHibernateCfgEcustomsGw.VNACCSVA), objBaseEntity);
		daoBase.insertInHis(cacheSession.get(EHibernateCfgEcustomsGw.VNACCSVA), objBaseEntity, "DELETE");
		return Boolean.TRUE;
	}
	@Override
	public Object selectItem(RequestData requestData) throws ECARGOException {
		objBaseEntity = jacksonEx.objectNode2Object(requestData.getData(), HangCoDkEntity.class);
		return daoBase.selectItemByKey(cacheSession.get(EHibernateCfgEcustomsGw.VNACCSVA), objBaseEntity);
	}
}
