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
import com.fis.customs.dao.ecustomsgw.TkKhongDuDkDAO;
import com.fis.customs.entity.ecustomsgw.TkKhongDuDkEntity;
import com.fis.customs.util.ecargo.DateTimeEx;
import com.fis.customs.util.ecargo.ECARGOException;
import com.fis.customs.util.ecargo.LinQEx;
import com.fis.customs.util.ecargo.OtherEx;

import vn.fis.sof5.core.common.JacksonEx;
import vn.fis.sof5.core.testcase.ETestCaseResultType;
import vn.fis.sof5.core.testcase.TestCaseLogImpl;
import vn.fis.sof5.core.testcase.TestCaseResultObj;

/**
 * @author QuyenNN - 16/05/2018
 *
 */
@RunWith(Parameterized.class)
@FixMethodOrder(org.junit.runners.MethodSorters.JVM)
public class TestQlDmc03Processor extends TestCaseLogImpl {
	protected static final Logger log4j = Logger.getLogger(TestQlDmc03Processor.class);
	private JacksonEx jacksonEx = JacksonEx.getInstance();
	@InjectMocks
	private QlDmc03Processor dmc03Processor;

	@Mock
	private TkKhongDuDkDAO tkKhongDuDkDAO;

	private List<TkKhongDuDkEntity> listDsTkKhongDuDkEntities;
	public EcustomsGwKeySearch objKeySearch;
	TestCaseResultObj objTestCaseResult;

	public TestQlDmc03Processor(EcustomsGwKeySearch objKeySearch, TestCaseResultObj objTestCaseResult) {
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
				this.setTungay(DateTimeEx.string2DateTime("01/01/1990"));
			}
		}, new TestCaseResultObj(ETestCaseResultType.TRUE, 3) });

		lstParam.add(new Object[] { new EcustomsGwKeySearch() {
			{
				this.setTungay(DateTimeEx.string2DateTime("01/01/1990"));
				this.setDenngay(DateTimeEx.string2DateTime("31/12/9999"));
				this.setErr1("VN001-0000-0000");
				this.setErr2("VN002-0000-0000");
				this.setErr3("VN003-0000-0000");
				this.setErr4("VN004-0000-0000");
				this.setErr5("VN005-0000-0000");

			}
		}, new TestCaseResultObj(ETestCaseResultType.TRUE, 1) });

		lstParam.add(new Object[] { new EcustomsGwKeySearch() {
		}, new TestCaseResultObj(ETestCaseResultType.TRUE, 1) });
		lstParam.add(new Object[] { new EcustomsGwKeySearch() {
			{
				this.setFlag(false);
			}
		}, new TestCaseResultObj(ETestCaseResultType.TRUE, 1) });
		lstParam.add(new Object[] { new EcustomsGwKeySearch() {
			{
				this.setFlag(true);
			}
		}, new TestCaseResultObj(ETestCaseResultType.TRUE, 1) });
		return lstParam;
	}

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);

		dmc03Processor.setbIsUnitTest(true);
		initDataTest();

		Mockito.when(tkKhongDuDkDAO.selectByDay(Mockito.any(), Mockito.any()))
				.thenAnswer(new Answer<List<TkKhongDuDkEntity>>() {
					public List<TkKhongDuDkEntity> answer(InvocationOnMock invocation) {
						Object[] args = invocation.getArguments();
						Map<String, Object> mWhere = new HashMap<String, Object>();
						mWhere.put("LOGTIME", ((EcustomsGwKeySearch) args[1]).getLogtime());
						return LinQEx.findAllEx(new TkKhongDuDkEntity(), listDsTkKhongDuDkEntities, mWhere);
					}
				});
		Mockito.when(tkKhongDuDkDAO.selectAll(Mockito.any())).thenAnswer(new Answer<List<TkKhongDuDkEntity>>() {
			public List<TkKhongDuDkEntity> answer(InvocationOnMock invocation) throws Throwable {
				return listDsTkKhongDuDkEntities;
			}
		});
	}

	protected void initDataTest() {
		listDsTkKhongDuDkEntities = new ArrayList<TkKhongDuDkEntity>();
		TkKhongDuDkEntity obj = new TkKhongDuDkEntity();

		obj.setId(123);
		obj.setError("VN004-0000-0000");
		obj.setLogtime(DateTimeEx.string2DateTime("01/01/1990"));
		listDsTkKhongDuDkEntities.add(obj);
	}

	@SuppressWarnings("unchecked")
	@Test
	public void selectList() {
		try {
			EcustomsGwKeySearch objInput = new EcustomsGwKeySearch();
			objInput.setFlag(Boolean.FALSE);
			List<TkKhongDuDkEntity> list = (List<TkKhongDuDkEntity>) dmc03Processor
					.selectList(jacksonEx.object2RequestData(objInput));
			Assert.assertNotNull(list);
		} catch (ECARGOException e) {
			log4j.error(OtherEx.getStackTrace(e));
		}
	}

	@SuppressWarnings("unchecked")
	@Test
	public void selectList2() {
		try {
			EcustomsGwKeySearch objInput = new EcustomsGwKeySearch();
			objInput.setFlag(Boolean.TRUE);
			List<TkKhongDuDkEntity> list = (List<TkKhongDuDkEntity>) dmc03Processor
					.selectList(jacksonEx.object2RequestData(objInput));
			Assert.assertNotNull(list);
		} catch (ECARGOException e) {
			log4j.error(OtherEx.getStackTrace(e));
		}
	}

	@SuppressWarnings("unchecked")
	@Test
	public void selectItem() {
		try {
			EcustomsGwKeySearch objInput = new EcustomsGwKeySearch();
			List<TkKhongDuDkEntity> list = (List<TkKhongDuDkEntity>) dmc03Processor
					.selectItem(jacksonEx.object2RequestData(objInput));
			Assert.assertNotNull(list);
		} catch (ECARGOException e) {
			log4j.error(OtherEx.getStackTrace(e));
		}
	}
}
