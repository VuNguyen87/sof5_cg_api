/**
 * 
 */
package com.fis.customs.api.ecustomsgw.handler.qldmc;

import org.springframework.stereotype.Service;

import com.fis.customs.api.ecustomsgw.handler.WebApiBaseProcessorEcustomsGwImpl;
import com.fis.customs.core.ecustomsgw.common.EcustomsGwKeySearch;
import com.fis.customs.core.ecustomsgw.enums.EHibernateCfgEcustomsGw;
import com.fis.customs.dao.ecustomsgw.TkKhongDuDkDAO;
import com.fis.customs.entity.ecustomsgw.TkKhongDuDkEntity;
import com.fis.customs.util.ecargo.ECARGOException;

import vn.fis.sof5.core.api.RequestData;
import vn.fis.sof5.core.common.JacksonEx;

/**
 * @author QuyenNN - 08/05/2018
 *
 */

@Service("QlDmc03Processor")
public class QlDmc03Processor
		extends WebApiBaseProcessorEcustomsGwImpl<EcustomsGwKeySearch, TkKhongDuDkEntity, TkKhongDuDkDAO> {
	private JacksonEx jacksonEx = JacksonEx.getInstance();
	public QlDmc03Processor() {
		daoBase = TkKhongDuDkDAO.getInstance();
	}

	@Override
	public Object selectItem(RequestData requestData) throws ECARGOException {
		objBaseSearch = jacksonEx.objectNode2Object(requestData.getData(), EcustomsGwKeySearch.class);
		return daoBase.selectByDay(cacheSession.get(EHibernateCfgEcustomsGw.VNACCSVA), objBaseSearch);
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

}
