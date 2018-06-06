package com.fis.customs.api.ecustomsgw.handler;

import com.fis.customs.util.ecargo.ECARGOException;

import vn.fis.sof5.core.api.RequestData;

/**
 * @author ThangNM11 - 04/05/2018
 *
 */

public interface IWebApiBaseProcessorEcustomsGw {
	Object insertItem(RequestData arg0) throws ECARGOException;

	Object updateItem(RequestData arg0) throws ECARGOException;

	Object deleteItem(RequestData arg0) throws ECARGOException;

	Object selectItem(RequestData arg0) throws ECARGOException;

	Object selectList(RequestData arg0) throws ECARGOException;
}
