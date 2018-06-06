package com.fis.customs.api.ecustomsgw.handler;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import com.fis.customs.util.ecargo.ECARGOException;
import com.fis.customs.util.ecargo.OtherEx;

import vn.fis.sof5.core.api.RequestData;
import vn.fis.sof5.core.common.ConstantEx;
import vn.fis.sof5.webapi.processor.WebApiBaseProcessorImpl;

/**
 * @author ThangNM11 - 04/05/2018
 *
 */

public abstract class WebApiBaseProcessorEcustomsGwImpl<S, E, D> extends WebApiBaseProcessorImpl<S, E, D>
		implements IWebApiBaseProcessorEcustomsGw {
	@Override
	public Object mapAction(RequestData requestData) throws ECARGOException {
		try {
			Method methodInvoke = WebApiBaseProcessorEcustomsGwImpl.class.getMethod(requestData.getAction(),
					RequestData.class);
			return methodInvoke.invoke(this, requestData);
		} catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException
				| InvocationTargetException e) {
			throw new ECARGOException(OtherEx.getStackTrace(e));
		}
	}

	// region Override
	@Override
	public Object insertItem(RequestData requestData) throws ECARGOException {
		throw new UnsupportedOperationException(ConstantEx.WEBAPI_THROW_EXCEPTION_NOT_IMPLEMENTED_YET);
	}

	@Override
	public Object updateItem(RequestData requestData) throws ECARGOException {
		throw new UnsupportedOperationException(ConstantEx.WEBAPI_THROW_EXCEPTION_NOT_IMPLEMENTED_YET);
	}

	@Override
	public Object deleteItem(RequestData requestData) throws ECARGOException {
		throw new UnsupportedOperationException(ConstantEx.WEBAPI_THROW_EXCEPTION_NOT_IMPLEMENTED_YET);
	}

	@Override
	public Object selectItem(RequestData requestData) throws ECARGOException {
		throw new UnsupportedOperationException(ConstantEx.WEBAPI_THROW_EXCEPTION_NOT_IMPLEMENTED_YET);
	}

	@Override
	public Object selectList(RequestData requestData) throws ECARGOException {
		throw new UnsupportedOperationException(ConstantEx.WEBAPI_THROW_EXCEPTION_NOT_IMPLEMENTED_YET);
	}
	
	public Object selectListError(RequestData requestData) throws ECARGOException {
		throw new UnsupportedOperationException(ConstantEx.WEBAPI_THROW_EXCEPTION_NOT_IMPLEMENTED_YET);
	}
	// endregion
}
