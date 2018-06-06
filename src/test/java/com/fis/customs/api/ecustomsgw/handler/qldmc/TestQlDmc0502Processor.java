package com.fis.customs.api.ecustomsgw.handler.qldmc;

import java.sql.SQLDataException;

import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import com.fis.customs.dao.ecustomsgw.MsgGiauLuongDAO;
import com.fis.customs.entity.ecustomsgw.MsgGiauLuongEntity;
import com.fis.customs.util.ecargo.CompareEx;
import com.fis.customs.util.ecargo.StringEx;

import vn.fis.sof5.core.common.JacksonEx;
import vn.fis.sof5.core.testcase.TestCaseLogImpl;

/**
 * @author ThangNM11 - 09/05/2018
 *
 */

@FixMethodOrder(org.junit.runners.MethodSorters.JVM)
public class TestQlDmc0502Processor extends TestCaseLogImpl {
	private JacksonEx jacksonEx = JacksonEx.getInstance();
	protected static final Logger log4j = Logger.getLogger(TestQlDmc0502Processor.class);

	@InjectMocks
	private QlDmc0502Processor qlDmc0502Processor;
	@Mock
	private MsgGiauLuongDAO dsMsgGiauLuongDao;

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);

		qlDmc0502Processor.setbIsUnitTest(true);

		Mockito.when(dsMsgGiauLuongDao.updateStatus(Mockito.any(), Mockito.any())).thenAnswer(new Answer<Boolean>() {
			public Boolean answer(InvocationOnMock invocation) throws SQLDataException {
				Object[] args = invocation.getArguments();
				MsgGiauLuongEntity objInput = (MsgGiauLuongEntity) args[1];
				if (!StringEx.isNullOrEmpty(objInput.getStatus())
						&& !CompareEx.existsInList("0,1,2", objInput.getStatus(), ",")) {
					return false;
				} else if (!StringEx.isNullOrEmpty(objInput.getTkId())
						&& Long.parseLong(objInput.getTkId()) > 101234567892L) {
					return false;
				} else if (!StringEx.trim(objInput.getTenDn()).endsWith(StringEx.trim(objInput.getMaDn()))) {
					return false;
				} else if (StringEx.trim(objInput.getServiceId()).length() > 5) {
					throw new SQLDataException("SERVICE_ID maxlength = 5");
				} else if (!CompareEx.existsInList("IDA,EDA", objInput.getServiceId(), ",")) {
					return false;
				} else {
					return true;
				}
			}
		});
	}

	@Test
	public void updateItem_Input_Null() throws Throwable {
		expectedThrowException.expect(NullPointerException.class);
		Assert.assertTrue((Boolean) qlDmc0502Processor.updateItem(jacksonEx.object2RequestData(null)));
	}

	@Test
	@Ignore
	public void updateItem_Input_NewObject() throws Throwable {
		Assert.assertFalse(
				(Boolean) qlDmc0502Processor.updateItem(jacksonEx.object2RequestData(new MsgGiauLuongEntity())));
	}

	@Test
	@Ignore
	public void updateItem_Input_NewObject_SetMaDn() throws Throwable {
		MsgGiauLuongEntity objDsMsgGiauLuongEntity = new MsgGiauLuongEntity();
		objDsMsgGiauLuongEntity.setMaDn("DN001");
		Assert.assertFalse(
				(Boolean) qlDmc0502Processor.updateItem(jacksonEx.object2RequestData(objDsMsgGiauLuongEntity)));
	}

	@Test
	public void updateItem_Input_NewObject_SetServiceId() throws Throwable {
		expectedThrowException.expect(SQLDataException.class);
		expectedThrowException.expectMessage("maxlength");
		MsgGiauLuongEntity objDsMsgGiauLuongEntity = new MsgGiauLuongEntity();
		objDsMsgGiauLuongEntity.setServiceId("IDAEDA");
		Assert.assertFalse(
				(Boolean) qlDmc0502Processor.updateItem(jacksonEx.object2RequestData(objDsMsgGiauLuongEntity)));
	}

	@Test
	@Ignore
	public void updateItem_Input_NewObject_SetStatus() throws Throwable {
		MsgGiauLuongEntity objDsMsgGiauLuongEntity = new MsgGiauLuongEntity();
		objDsMsgGiauLuongEntity.setStatus("5");
		Assert.assertFalse(
				(Boolean) qlDmc0502Processor.updateItem(jacksonEx.object2RequestData(objDsMsgGiauLuongEntity)));
	}
}
