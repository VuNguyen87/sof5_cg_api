package com.fis.customs.api.ecustomsgw.handler.qldmc;

import org.springframework.stereotype.Service;

import com.fis.customs.api.ecustomsgw.handler.WebApiBaseProcessorEcustomsGwImpl;
import com.fis.customs.core.ecustomsgw.common.EcustomsGwKeySearch;
import com.fis.customs.core.ecustomsgw.enums.EHibernateCfgEcustomsGw;
import com.fis.customs.dao.ecustomsgw.MsgGiauLuongDAO;
import com.fis.customs.entity.ecustomsgw.MsgGiauLuongEntity;
import com.fis.customs.util.ecargo.ECARGOException;

import vn.fis.sof5.core.api.RequestData;
import vn.fis.sof5.core.common.JacksonEx;

/**
 * @author ThangNM11 - 08/05/2018
 *
 */

@Service("QlDmc0501Processor")
public class QlDmc0501Processor
		extends WebApiBaseProcessorEcustomsGwImpl<EcustomsGwKeySearch, MsgGiauLuongEntity, MsgGiauLuongDAO> {
	private JacksonEx jacksonEx = JacksonEx.getInstance();

	public QlDmc0501Processor() {
		daoBase = MsgGiauLuongDAO.getInstance();
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
}
