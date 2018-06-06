/**
 * 
 */
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
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import com.fis.customs.core.ecustomsgw.common.EcustomsGwKeySearch;
import com.fis.customs.dao.ecustomsgw.ParamsDAO;
import com.fis.customs.entity.ecustomsgw.DnUuTienEntity;
import com.fis.customs.entity.ecustomsgw.ParamsEntity;
import com.fis.customs.util.ecargo.ECARGOException;
import com.fis.customs.util.ecargo.LinQEx;
import com.fis.customs.util.ecargo.OtherEx;
import com.fis.customs.util.ecargo.StringEx;

import vn.fis.sof5.core.common.JacksonEx;
import vn.fis.sof5.core.testcase.TestCaseLogImpl;
import vn.fis.sof5.core.testcase.TestCaseResultObj;

/**
 * @author QuyenNN - 25/05/2018
 *
 */
@FixMethodOrder(org.junit.runners.MethodSorters.JVM)
public class TestQlDmc06Processor extends TestCaseLogImpl {
	protected static final Logger log4j = Logger.getLogger(TestQlDmc0502Processor.class);
	private JacksonEx jacksonEx = JacksonEx.getInstance();

	@InjectMocks
	private QlDmc06Processor qlDmc06Processor;

	@Mock
	private ParamsDAO paramsDAO;

	private List<ParamsEntity> paramsEntities = new ArrayList<>();
	public EcustomsGwKeySearch objKeySearch = new EcustomsGwKeySearch();
	TestCaseResultObj objTestCaseResult;

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);

		qlDmc06Processor.setbIsUnitTest(true);

		Mockito.when(paramsDAO.selectAll(Mockito.any())).thenAnswer(new Answer<List<ParamsEntity>>() {
			public List<ParamsEntity> answer(InvocationOnMock invocation) throws Throwable {
				return paramsEntities;
			}
		});
		Mockito.when(paramsDAO.selectByOther(Mockito.any(), Mockito.any()))
				.thenAnswer(new Answer<List<ParamsEntity>>() {
					public List<ParamsEntity> answer(InvocationOnMock invocation) {
						Object[] args = invocation.getArguments();
						Map<String, Object> mWhere = new HashMap<String, Object>();
						if (!StringEx.isNullOrEmpty(((EcustomsGwKeySearch) args[1]).getName())) {
							mWhere.put("NAME", ((EcustomsGwKeySearch) args[1]).getName());
						}
						return LinQEx.findAllEx(new ParamsEntity(), paramsEntities, mWhere);
					}
				});

		Mockito.when(paramsDAO.insertItem(Mockito.any(), Mockito.any())).thenAnswer(new Answer<Boolean>() {
			public Boolean answer(InvocationOnMock invocation) {
				return paramsEntities.add(new ParamsEntity());
			}
		});
		Mockito.when(paramsDAO.updateItem(Mockito.any(), Mockito.any())).thenAnswer(new Answer<Boolean>() {
			public Boolean answer(InvocationOnMock invocation) {
				return paramsEntities.contains(new ParamsEntity());
			}
		});
		Mockito.when(paramsDAO.delete(Mockito.any(), Mockito.any())).thenAnswer(new Answer<Boolean>() {
			public Boolean answer(InvocationOnMock invocation) {
				return paramsEntities.remove(new ParamsEntity());
			}
		});
	}

	@Test
	public void insertItem() {
		try {
			ParamsEntity objInput = new ParamsEntity();

			Boolean bRet = (Boolean) qlDmc06Processor.insertItem(jacksonEx.object2RequestData(objInput));
			Assert.assertTrue(bRet);
		} catch (ECARGOException e) {
			log4j.error(OtherEx.getStackTrace(e));
		}
	}

	@Test
	public void updateItem() {
		try {
			ParamsEntity objInput = new ParamsEntity();

			Boolean bRet = (Boolean) qlDmc06Processor.updateItem(jacksonEx.object2RequestData(objInput));
			Assert.assertTrue(bRet);
		} catch (ECARGOException e) {
			log4j.error(OtherEx.getStackTrace(e));
		}
	}

	@Test
	public void delete() {
		try {
			ParamsEntity objInput = new ParamsEntity();

			Boolean bRet = (Boolean) qlDmc06Processor.deleteItem(jacksonEx.object2RequestData(objInput));
			Assert.assertTrue(bRet);
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
			List<DnUuTienEntity> list = (List<DnUuTienEntity>) qlDmc06Processor
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
			List<DnUuTienEntity> list = (List<DnUuTienEntity>) qlDmc06Processor
					.selectList(jacksonEx.object2RequestData(objInput));
			Assert.assertNotNull(list);
		} catch (ECARGOException e) {
			log4j.error(OtherEx.getStackTrace(e));
		}
	}
}
