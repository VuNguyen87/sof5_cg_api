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
import com.fis.customs.dao.ecustomsgw.HangCoDkDAO;
import com.fis.customs.entity.ecustomsgw.HangCoDkEntity;
import com.fis.customs.util.ecargo.ECARGOException;
import com.fis.customs.util.ecargo.LinQEx;
import com.fis.customs.util.ecargo.OtherEx;
import com.fis.customs.util.ecargo.StringEx;

import vn.fis.sof5.core.common.JacksonEx;
import vn.fis.sof5.core.testcase.TestCaseLogImpl;

/**
 * @author QuyenNN - 29/05/2018
 *
 */

@FixMethodOrder(org.junit.runners.MethodSorters.JVM)
public class TestQlDmc0201Processor extends TestCaseLogImpl {

	private JacksonEx jacksonEx = JacksonEx.getInstance();
	protected static final Logger log4j = Logger.getLogger(TestQlDmc0502Processor.class);
	
	@InjectMocks
	private QlDmc0201Processor dmc0201Processor;

	@Mock
	private HangCoDkDAO hangCoDkDAO;

	private List<HangCoDkEntity> listHangMienTheEntities;

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);

		dmc0201Processor.setbIsUnitTest(true);

		initdata();

		Mockito.when(hangCoDkDAO.selectAll(Mockito.any())).thenAnswer(new Answer<List<HangCoDkEntity>>() {
			public List<HangCoDkEntity> answer(InvocationOnMock invocation) throws Throwable {
				return listHangMienTheEntities;
			}
		});
		Mockito.when(hangCoDkDAO.selectByOther(Mockito.any(), Mockito.any()))
				.thenAnswer(new Answer<List<HangCoDkEntity>>() {
					public List<HangCoDkEntity> answer(InvocationOnMock invocation) {
						Object[] args = invocation.getArguments();
						Map<String, Object> mWhere = new HashMap<String, Object>();
						if (!StringEx.isNullOrEmpty(((EcustomsGwKeySearch) args[1]).getMaDn())) {
							mWhere.put("MA_DN", ((EcustomsGwKeySearch) args[1]).getMaDn());
						}
						return LinQEx.findAllEx(new HangCoDkEntity(), listHangMienTheEntities, mWhere);
					}
				});

		Mockito.when(hangCoDkDAO.insertItem(Mockito.any(), Mockito.any())).thenAnswer(new Answer<Boolean>() {
			public Boolean answer(InvocationOnMock invocation) {
				return listHangMienTheEntities.add(new HangCoDkEntity());
			}
		});
		Mockito.when(hangCoDkDAO.updateItem(Mockito.any(), Mockito.any())).thenAnswer(new Answer<Boolean>() {
			public Boolean answer(InvocationOnMock invocation) {
				return listHangMienTheEntities.contains(new HangCoDkEntity());
			}
		});
		Mockito.when(hangCoDkDAO.delete(Mockito.any(), Mockito.any())).thenAnswer(new Answer<Boolean>() {
			public Boolean answer(InvocationOnMock invocation) {
				return listHangMienTheEntities.remove(new HangCoDkEntity());
			}
		});
	}

	private void initdata() {
		listHangMienTheEntities = new ArrayList<HangCoDkEntity>();

	}

	@Test
	public void insertItem() {
		try {
			HangCoDkEntity objInput = new HangCoDkEntity();
			objInput.setErrMsg("IDE");
			Boolean bRet = (Boolean) dmc0201Processor.insertItem(jacksonEx.object2RequestData(objInput));
			Assert.assertTrue(bRet);
		} catch (ECARGOException e) {
			log4j.error(OtherEx.getStackTrace(e));
		}
	}

	@Test
	public void updateItem() {
		try {
			HangCoDkEntity objInput = new HangCoDkEntity();
			Boolean bRet = (Boolean) dmc0201Processor.updateItem(jacksonEx.object2RequestData(objInput));
			Assert.assertTrue(bRet);
		} catch (ECARGOException e) {
			log4j.error(OtherEx.getStackTrace(e));
		}
	}

	@Test
	public void delete() {
		try {
			HangCoDkEntity objInput = new HangCoDkEntity();

			Boolean bRet = (Boolean) dmc0201Processor.deleteItem(jacksonEx.object2RequestData(objInput));
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
			List<HangCoDkEntity> list = (List<HangCoDkEntity>) dmc0201Processor
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
			List<HangCoDkEntity> list = (List<HangCoDkEntity>) dmc0201Processor
					.selectList(jacksonEx.object2RequestData(objInput));
			Assert.assertNotNull(list);
		} catch (ECARGOException e) {
			log4j.error(OtherEx.getStackTrace(e));
		}
	}
}
