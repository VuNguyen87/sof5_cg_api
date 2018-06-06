package com.fis.customs.api.ecustomsgw.handler.example;

import org.springframework.stereotype.Service;

import com.fis.customs.api.ecustomsgw.handler.WebApiBaseProcessorEcustomsGwImpl;
import com.fis.customs.core.ecustomsgw.enums.EHibernateCfgEcustomsGw;
import com.fis.customs.dao.ecustomsgw.FileDinhkemBsDAO;
import com.fis.customs.entity.ecustomsgw.FileDinhkemBsEntity;
import com.fis.customs.model.ecargo.KeySearchList_Obj;
import com.fis.customs.util.ecargo.ECARGOException;

import vn.fis.sof5.core.api.RequestData;
import vn.fis.sof5.core.common.JacksonEx;

/**
 * @author ThangNM11 - 04/05/2018
 *
 */

@Service("QlFileDinhKem01Processor")
public class QlFileDinhKem01Processor
		extends WebApiBaseProcessorEcustomsGwImpl<KeySearchList_Obj, FileDinhkemBsEntity, FileDinhkemBsDAO> {

	private JacksonEx jacksonEx = JacksonEx.getInstance();

	public QlFileDinhKem01Processor() {
		daoBase = new FileDinhkemBsDAO();
	}

	@Override
	public Object insertItem(RequestData requestData) throws ECARGOException {
		objBaseEntity = jacksonEx.objectNode2Object(requestData.getData(), FileDinhkemBsEntity.class);
		daoBase.insertItem(cacheSession.get(EHibernateCfgEcustomsGw.ECUSTOMS_GW), objBaseEntity);
		return Boolean.TRUE;
	}

	@Override
	public Object updateItem(RequestData requestData) throws ECARGOException {
		objBaseEntity = jacksonEx.objectNode2Object(requestData.getData(), FileDinhkemBsEntity.class);
		daoBase.updateItem(cacheSession.get(EHibernateCfgEcustomsGw.ECUSTOMS_GW), objBaseEntity);
		return Boolean.TRUE;
	}

	@Override
	public Object deleteItem(RequestData requestData) throws ECARGOException {
		objBaseEntity = jacksonEx.objectNode2Object(requestData.getData(), FileDinhkemBsEntity.class);
		daoBase.deleteItem(cacheSession.get(EHibernateCfgEcustomsGw.ECUSTOMS_GW), objBaseEntity);
		return Boolean.TRUE;
	}

	@Override
	public Object selectItem(RequestData requestData) throws ECARGOException {
		objBaseEntity = jacksonEx.objectNode2Object(requestData.getData(), FileDinhkemBsEntity.class);
		return daoBase.selectItem(cacheSession.get(EHibernateCfgEcustomsGw.ECUSTOMS_GW), objBaseEntity);
	}

	@Override
	public Object selectList(RequestData requestData) throws ECARGOException {
		objBaseSearch = jacksonEx.objectNode2Object(requestData.getData(), KeySearchList_Obj.class);
		objBaseEntity = new FileDinhkemBsEntity();
		objBaseEntity.setLoaiCt(objBaseSearch.getLoai());
		return daoBase.selectByOther(cacheSession.get(EHibernateCfgEcustomsGw.ECUSTOMS_GW), objBaseEntity);
	}
}
