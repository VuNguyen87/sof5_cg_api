package com.fis.customs.api.ecustomsgw.handler.qldmc;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import com.fis.customs.core.ecustomsgw.common.EcustomsGwKeySearch;
import com.fis.customs.dao.ecustomsgw.MsgGiauLuongDAO;
import com.fis.customs.entity.ecustomsgw.MsgGiauLuongEntity;
import com.fis.customs.util.ecargo.LinQEx;
import com.fis.customs.util.ecargo.StringEx;

import vn.fis.sof5.core.common.JacksonEx;
import vn.fis.sof5.core.testcase.ETestCaseResultType;
import vn.fis.sof5.core.testcase.TestCaseLogImpl;
import vn.fis.sof5.core.testcase.TestCaseResultObj;

/**
 * @author ThangNM11 - 09/05/2018
 *
 */

@RunWith(Parameterized.class)
@FixMethodOrder(org.junit.runners.MethodSorters.JVM)
public class TestQlDmc0501Processor extends TestCaseLogImpl {
	private JacksonEx jacksonEx = JacksonEx.getInstance();
	protected static final Logger log4j = Logger.getLogger(TestQlDmc0501Processor.class);

	@InjectMocks
	private QlDmc0501Processor qlDmc0501Processor;
	@Mock
	private MsgGiauLuongDAO dsMsgGiauLuongDao;

	private List<MsgGiauLuongEntity> lstDsMsgGiauLuongEntity;
	public EcustomsGwKeySearch objKeySearch;
	TestCaseResultObj objTestCaseResult;

	public TestQlDmc0501Processor(EcustomsGwKeySearch objKeySearch, TestCaseResultObj objTestCaseResult) {
		this.objKeySearch = objKeySearch;
		this.objTestCaseResult = objTestCaseResult;
	}

	@SuppressWarnings("serial")
	@Parameters(name = "{index}: test...({0})={1}")
	public static List<Object[]> data() {
		List<Object[]> lstParam = new ArrayList<Object[]>();
		lstParam.add(new Object[] { null,
				new TestCaseResultObj(ETestCaseResultType.EXCEPTION, new NullPointerException("")) });

		lstParam.add(new Object[] { new EcustomsGwKeySearch(), new TestCaseResultObj(ETestCaseResultType.TRUE, 0) });

		lstParam.add(new Object[] { new EcustomsGwKeySearch() {
			{
				this.setMaDn("DN001");
			}
		}, new TestCaseResultObj(ETestCaseResultType.TRUE, 3) });

		lstParam.add(new Object[] { new EcustomsGwKeySearch() {
			{
				this.setTenDn("Tên DN: DN001");
			}
		}, new TestCaseResultObj(ETestCaseResultType.FALSE, 5) });

		lstParam.add(new Object[] { new EcustomsGwKeySearch() {
			{
				this.setTkId("101234567890");
			}
		}, new TestCaseResultObj(ETestCaseResultType.TRUE, 1) });

		lstParam.add(new Object[] { new EcustomsGwKeySearch() {
			{
				this.setMaThongDiep("IDA");
			}
		}, new TestCaseResultObj(ETestCaseResultType.NOT_NULL, 3) });

		lstParam.add(new Object[] { new EcustomsGwKeySearch() {
			{
				this.setTrangThai("2");
			}
		}, new TestCaseResultObj(ETestCaseResultType.TRUE, 4) });

		lstParam.add(new Object[] { new EcustomsGwKeySearch() {
			{
				this.setMaDn("DN001");
				this.setTenDn("Tên DN: DN001");
				this.setTkId("101234567892");
				this.setMaThongDiep("IDA");
				this.setTrangThai("1");
			}
		}, new TestCaseResultObj(ETestCaseResultType.TRUE, 1) });

		return lstParam;
	}

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);

		qlDmc0501Processor.setbIsUnitTest(true);
		initDataTest();

		Mockito.when(dsMsgGiauLuongDao.selectByOther(Mockito.any(), Mockito.any()))
				.thenAnswer(new Answer<List<MsgGiauLuongEntity>>() {
					public List<MsgGiauLuongEntity> answer(InvocationOnMock invocation) {
						Object[] args = invocation.getArguments();
						Map<String, Object> mWhere = new HashMap<String, Object>();
						if (!StringEx.isNullOrEmpty(((EcustomsGwKeySearch) args[1]).getMaDn())) {
							mWhere.put("MA_DN", ((EcustomsGwKeySearch) args[1]).getMaDn());
						}
						if (!StringEx.isNullOrEmpty(((EcustomsGwKeySearch) args[1]).getTenDn())) {
							mWhere.put("TEN_DN", ((EcustomsGwKeySearch) args[1]).getTenDn());
						}
						if (!StringEx.isNullOrEmpty(((EcustomsGwKeySearch) args[1]).getTkId())) {
							mWhere.put("TK_ID", ((EcustomsGwKeySearch) args[1]).getTkId());
						}
						if (!StringEx.isNullOrEmpty(((EcustomsGwKeySearch) args[1]).getMaThongDiep())) {
							mWhere.put("SERVICE_ID", ((EcustomsGwKeySearch) args[1]).getMaThongDiep());
						}
						if (!StringEx.isNullOrEmpty(((EcustomsGwKeySearch) args[1]).getTrangThai())) {
							mWhere.put("STATUS", ((EcustomsGwKeySearch) args[1]).getTrangThai());
						}
						return LinQEx.findAllEx(new MsgGiauLuongEntity(), lstDsMsgGiauLuongEntity, mWhere);
					}
				});
	}

	protected void initDataTest() {
		lstDsMsgGiauLuongEntity = new ArrayList<MsgGiauLuongEntity>();
		MsgGiauLuongEntity obj;
		for (int i = 0; i < 10; i++) {
			obj = new MsgGiauLuongEntity();
			obj.setId(i + 1);
			if (obj.getId() % 3 == 0) {
				obj.setMaDn("DN001");
				obj.setServiceId("IDA");
				obj.setStatus("1");
			} else if (obj.getId() % 2 == 0) {
				obj.setMaDn("DN002");
				obj.setServiceId("EDA");
				obj.setStatus("2");
			} else {
				obj.setMaDn("DN_" + i);
				obj.setServiceId("AA");
				obj.setStatus("0");
			}
			obj.setTkId(Long.toString(101234567890L + i));
			obj.setTenDn("Tên DN: " + obj.getMaDn());
			lstDsMsgGiauLuongEntity.add(obj);
		}
	}

	@SuppressWarnings({ "unchecked" })
	@Test
	public void selectList() throws Throwable {
		List<MsgGiauLuongEntity> listTemp;
		switch (objTestCaseResult.geteResultType()) {
		case ARRAY_EQUALS:
			Assert.fail("Not config!");
			break;

		case EQUALS:
			Assert.fail("Not config!");
			break;

		case FALSE:
			listTemp = (List<MsgGiauLuongEntity>) qlDmc0501Processor
					.selectList(jacksonEx.object2RequestData(objKeySearch));
			Assert.assertFalse(listTemp.size() == (int) objTestCaseResult.getObjExpected());
			break;

		case NOT_EQUALS:
			Assert.fail("Not config!");
			break;

		case NOT_NULL:
			listTemp = (List<MsgGiauLuongEntity>) qlDmc0501Processor
					.selectList(jacksonEx.object2RequestData(objKeySearch));
			Assert.assertNotNull(listTemp);
			break;

		case NOT_SAME:
			Assert.fail("Not config!");
			break;

		case NULL:
			Assert.fail("Not config!");
			break;

		case SAME:
			Assert.fail("Not config!");
			break;

		case THAT:
			Assert.fail("Not config!");
			break;

		case TRUE:
			listTemp = (List<MsgGiauLuongEntity>) qlDmc0501Processor
					.selectList(jacksonEx.object2RequestData(objKeySearch));
			Assert.assertTrue(listTemp.size() == (int) objTestCaseResult.getObjExpected());
			break;

		case EXCEPTION:
			Exception e = (Exception) objTestCaseResult.getObjExpected();
			expectedThrowException.expect(e.getClass());
			if (!StringEx.isNullOrEmpty(e.getMessage())) {
				expectedThrowException.expectMessage(e.getMessage());
			}
			listTemp = (List<MsgGiauLuongEntity>) qlDmc0501Processor
					.selectList(jacksonEx.object2RequestData(objKeySearch));
			break;

		default:
			Assert.fail("Not config!");
			break;
		}
	}
}
