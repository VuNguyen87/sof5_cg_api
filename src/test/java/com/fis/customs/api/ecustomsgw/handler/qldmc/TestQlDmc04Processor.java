package com.fis.customs.api.ecustomsgw.handler.qldmc;

import static org.junit.Assert.assertEquals;

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
import com.fis.customs.dao.ecustomsgw.DnUuTienDAO;
import com.fis.customs.entity.ecustomsgw.DnUuTienEntity;
import com.fis.customs.util.ecargo.DateTimeEx;
import com.fis.customs.util.ecargo.ECARGOException;
import com.fis.customs.util.ecargo.LinQEx;
import com.fis.customs.util.ecargo.OtherEx;
import com.fis.customs.util.ecargo.StringEx;

import vn.fis.sof5.core.common.JacksonEx;
import vn.fis.sof5.core.testcase.ETestCaseResultType;
import vn.fis.sof5.core.testcase.TestCaseLogImpl;
import vn.fis.sof5.core.testcase.TestCaseResultObj;

/**
 * @author QuyenNN - 21/05/2018
 *
 */
@RunWith(Parameterized.class)
@FixMethodOrder(org.junit.runners.MethodSorters.JVM)
public class TestQlDmc04Processor extends TestCaseLogImpl {

	protected static final Logger log4j = Logger.getLogger(TestQlDmc03Processor.class);
	private JacksonEx jacksonEx = JacksonEx.getInstance();

	@InjectMocks
	private QlDmc04Processor dmc04Processor;

	@Mock
	private DnUuTienDAO dsDnUuTienDao;

	private List<DnUuTienEntity> listdsDnUuTienEntities;
	public EcustomsGwKeySearch objKeySearch;
	TestCaseResultObj objTestCaseResult;

	public TestQlDmc04Processor(EcustomsGwKeySearch objKeySearch, TestCaseResultObj objTestCaseResult) {
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
				this.setLogtime(DateTimeEx.string2DateTime("01/01/1990"));
			}
		}, new TestCaseResultObj(ETestCaseResultType.TRUE, 3) });
		lstParam.add(new Object[] { new EcustomsGwKeySearch() {
			{
				this.setMaDn("1111111111");
				this.setTenDn("tendn");
				this.setMoTa("dsfsdfdds");
				this.setLogtime(DateTimeEx.string2DateTime("01/01/1990"));
				this.setCreateBy("quyennn");

			}
		}, new TestCaseResultObj(ETestCaseResultType.TRUE, 1) });
		lstParam.add(new Object[] { new EcustomsGwKeySearch() {
		}, new TestCaseResultObj(ETestCaseResultType.TRUE, 1) });

		return lstParam;
	}

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);

		dmc04Processor.setbIsUnitTest(true);
		initDataTest();
		Mockito.when(dsDnUuTienDao.selectAll(Mockito.any())).thenAnswer(new Answer<List<DnUuTienEntity>>() {
			public List<DnUuTienEntity> answer(InvocationOnMock invocation) throws Throwable {
				return listdsDnUuTienEntities;
			}
		});
		Mockito.when(dsDnUuTienDao.insertItem(Mockito.any(), Mockito.any())).thenAnswer(new Answer<Boolean>() {
			public Boolean answer(InvocationOnMock invocation) {
				return listdsDnUuTienEntities.add(new DnUuTienEntity());
			}
		});
		Mockito.when(dsDnUuTienDao.updateItem(Mockito.any(), Mockito.any())).thenAnswer(new Answer<Boolean>() {
			public Boolean answer(InvocationOnMock invocation) {
				return listdsDnUuTienEntities.contains(new DnUuTienEntity());
			}
		});
		Mockito.when(dsDnUuTienDao.delete(Mockito.any(), Mockito.any())).thenAnswer(new Answer<Boolean>() {
			public Boolean answer(InvocationOnMock invocation) {
				return listdsDnUuTienEntities.remove(new DnUuTienEntity());
			}
		});
		Mockito.when(dsDnUuTienDao.selectByOther(Mockito.any(), Mockito.any()))
				.thenAnswer(new Answer<List<DnUuTienEntity>>() {
					public List<DnUuTienEntity> answer(InvocationOnMock invocation) {
						Object[] args = invocation.getArguments();
						Map<String, Object> mWhere = new HashMap<String, Object>();
						if (!StringEx.isNullOrEmpty(((EcustomsGwKeySearch) args[1]).getMaDn())) {
							mWhere.put("MA_DN", ((EcustomsGwKeySearch) args[1]).getMaDn());
						}
						return LinQEx.findAllEx(new DnUuTienEntity(), listdsDnUuTienEntities, mWhere);
					}
				});
	}

	protected void initDataTest() {
		listdsDnUuTienEntities = new ArrayList<DnUuTienEntity>();
		DnUuTienEntity obj = new DnUuTienEntity();
		obj.setMaDn("5568654");
		obj.setTenDn("tendn");
		obj.setLogtime(DateTimeEx.string2DateTime("01/01/1990"));
		listdsDnUuTienEntities.add(obj);
	}

	@Test
	public void insertItem() {
		try {
			DnUuTienEntity objInput = new DnUuTienEntity();
			objInput.setMaDn("5568654");
			objInput.setTenDn("tendn");
			objInput.setMoTa("dsfsdfdds");
			objInput.setLogtime(DateTimeEx.string2DateTime("01/01/1990"));
			objInput.setCreateBy("quyennn");
			Boolean bRet = (Boolean) dmc04Processor.insertItem(jacksonEx.object2RequestData(objInput));
			Assert.assertTrue(bRet);
		} catch (ECARGOException e) {
			log4j.error(OtherEx.getStackTrace(e));
		}
	}

	@Test
	public void updateItem() {
		try {
			DnUuTienEntity objInput = new DnUuTienEntity();
			objInput.setMaDn("5568654");
			objInput.setTenDn("tendn");
			objInput.setMoTa("dsfsdfdds");
			objInput.setLogtime(DateTimeEx.string2DateTime("01/01/1990"));
			objInput.setCreateBy("quyennn");
			Boolean bRet = (Boolean) dmc04Processor.updateItem(jacksonEx.object2RequestData(objInput));
			System.out.println(bRet);
			assertEquals(Boolean.FALSE, bRet);
		} catch (ECARGOException e) {
			log4j.error(OtherEx.getStackTrace(e));
		}
	}

	@Test
	public void delete() {
		try {
			DnUuTienEntity objInput = new DnUuTienEntity();
			objInput.setMaDn("5568654");
			Boolean bRet = (Boolean) dmc04Processor.deleteItem(jacksonEx.object2RequestData(objInput));
			assertEquals(Boolean.FALSE, bRet);
		} catch (ECARGOException e) {
			log4j.error(OtherEx.getStackTrace(e));
		}
	}

	@SuppressWarnings("unchecked")
	@Test
	public void selectlist() {
		try {
			EcustomsGwKeySearch objInput = new EcustomsGwKeySearch();
			objInput.setFlag(Boolean.TRUE);
			List<DnUuTienEntity> list = (List<DnUuTienEntity>) dmc04Processor
					.selectList(jacksonEx.object2RequestData(objInput));
			Assert.assertNotNull(list);
		} catch (ECARGOException e) {
			log4j.error(OtherEx.getStackTrace(e));
		}
	}

	@SuppressWarnings("unchecked")
	@Test
	public void selectlist1() {
		try {
			EcustomsGwKeySearch objInput = new EcustomsGwKeySearch();
			objInput.setFlag(Boolean.FALSE);
			List<DnUuTienEntity> list = (List<DnUuTienEntity>) dmc04Processor
					.selectList(jacksonEx.object2RequestData(objInput));
			Assert.assertNotNull(list);
		} catch (ECARGOException e) {
			log4j.error(OtherEx.getStackTrace(e));
		}
	}

}
