package com.fis.customs.api.ecustomsgw.handler.qldmc;

import org.springframework.stereotype.Service;

import com.fis.customs.api.ecustomsgw.handler.WebApiBaseProcessorEcustomsGwImpl;
import com.fis.customs.core.ecustomsgw.common.EcustomsGwKeySearch;
import com.fis.customs.core.ecustomsgw.enums.EHibernateCfgEcustomsGw;
import com.fis.customs.dao.ecustomsgw.DnCamDAO;
import com.fis.customs.entity.ecustomsgw.DnCamEntity;
import com.fis.customs.util.ecargo.ECARGOException;

import vn.fis.sof5.core.api.RequestData;
import vn.fis.sof5.core.common.JacksonEx;

/**
 * @author AnhNH70 - 8 May 2018
 */

@Service("QlDmc01Processor")
public class QlDmc01Processor
		extends WebApiBaseProcessorEcustomsGwImpl<EcustomsGwKeySearch, DnCamEntity, DnCamDAO> {
	private JacksonEx jacksonEx = JacksonEx.getInstance();

	public QlDmc01Processor() {
		daoBase = DnCamDAO.getInstance();
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
	public Object updateItem(RequestData requestData) throws ECARGOException {
		objBaseEntity = jacksonEx.objectNode2Object(requestData.getData(), DnCamEntity.class);
		daoBase.updateItem(cacheSession.get(EHibernateCfgEcustomsGw.VNACCSVA), objBaseEntity);
		daoBase.insertInHis(cacheSession.get(EHibernateCfgEcustomsGw.VNACCSVA), objBaseEntity, "UPDATE");
		return Boolean.TRUE;
	}

	@Override
	public Object insertItem(RequestData requestData) throws ECARGOException {
		objBaseEntity = jacksonEx.objectNode2Object(requestData.getData(), DnCamEntity.class);
		daoBase.insertItem(cacheSession.get(EHibernateCfgEcustomsGw.VNACCSVA), objBaseEntity);
		daoBase.insertInHis(cacheSession.get(EHibernateCfgEcustomsGw.VNACCSVA), objBaseEntity, "INSERT");
		return Boolean.TRUE;
	}

	@Override
	public Object deleteItem(RequestData requestData) throws ECARGOException {
		objBaseEntity = jacksonEx.objectNode2Object(requestData.getData(), DnCamEntity.class);
		daoBase.delete(cacheSession.get(EHibernateCfgEcustomsGw.VNACCSVA), objBaseEntity);
		daoBase.insertInHis(cacheSession.get(EHibernateCfgEcustomsGw.VNACCSVA), objBaseEntity, "DELETE");
		return Boolean.TRUE;
	}
	@Override
	public Object selectItem(RequestData requestData) throws ECARGOException {
		objBaseEntity = jacksonEx.objectNode2Object(requestData.getData(), DnCamEntity.class);
		return daoBase.selectItemByKey(cacheSession.get(EHibernateCfgEcustomsGw.VNACCSVA), objBaseEntity);
	}

	@Override
	public Object selectListError(RequestData requestData) throws ECARGOException {
		return daoBase.selectListError(cacheSession.get(EHibernateCfgEcustomsGw.VNACCSVA));
	}
	
}
