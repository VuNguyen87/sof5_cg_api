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
import com.fis.customs.dao.ecustomsgw.HangMienThueDAO;
import com.fis.customs.entity.ecustomsgw.HangMienThueEntity;
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
public class TestQlDmc0202Processor extends TestCaseLogImpl {
	private JacksonEx jacksonEx = JacksonEx.getInstance();
	protected static final Logger log4j = Logger.getLogger(TestQlDmc0502Processor.class);
	
	@InjectMocks
	private QlDmc0202Processor dmc0202Processor;
	
	@Mock 
	private HangMienThueDAO hangMienThueDAO;
	
	private List<HangMienThueEntity> listHangMienTheEntities;
	
	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);

		dmc0202Processor.setbIsUnitTest(true);

		initdata();
		
		Mockito.when(hangMienThueDAO.selectAll(Mockito.any())).thenAnswer(new Answer<List<HangMienThueEntity>>() {
			public List<HangMienThueEntity> answer(InvocationOnMock invocation) throws Throwable {
				return listHangMienTheEntities;
			}
		});
		Mockito.when(hangMienThueDAO.selectByOther(Mockito.any(), Mockito.any()))
				.thenAnswer(new Answer<List<HangMienThueEntity>>() {
					public List<HangMienThueEntity> answer(InvocationOnMock invocation) {
						Object[] args = invocation.getArguments();
						Map<String, Object> mWhere = new HashMap<String, Object>();
						if (!StringEx.isNullOrEmpty(((EcustomsGwKeySearch) args[1]).getMaDn())) {
							mWhere.put("MA_DN", ((EcustomsGwKeySearch) args[1]).getMaDn());
						}
						return LinQEx.findAllEx(new HangMienThueEntity(), listHangMienTheEntities, mWhere);
					}
				});

		Mockito.when(hangMienThueDAO.insertItem(Mockito.any(), Mockito.any())).thenAnswer(new Answer<Boolean>() {
			public Boolean answer(InvocationOnMock invocation) {
				return listHangMienTheEntities.add(new HangMienThueEntity());
			}
		});
		Mockito.when(hangMienThueDAO.updateItem(Mockito.any(), Mockito.any())).thenAnswer(new Answer<Boolean>() {
			public Boolean answer(InvocationOnMock invocation) {
				return listHangMienTheEntities.contains(new HangMienThueEntity());
			}
		});
		Mockito.when(hangMienThueDAO.delete(Mockito.any(), Mockito.any())).thenAnswer(new Answer<Boolean>() {
			public Boolean answer(InvocationOnMock invocation) {
				return listHangMienTheEntities.remove(new HangMienThueEntity());
			}
		});
	}
	
	private void initdata() {
		listHangMienTheEntities = new ArrayList<HangMienThueEntity>();
		
	}
	
	@Test
	public void insertItem() {
		try {
			HangMienThueEntity objInput= new HangMienThueEntity();
			objInput.setMaDn("1111111111");
			objInput.setTenDn("dsdasda");
			objInput.setErrMsg("IDE");
			Boolean bRet = (Boolean) dmc0202Processor.insertItem(jacksonEx.object2RequestData(objInput));
			Assert.assertTrue(bRet);
		} catch (ECARGOException e) {
			log4j.error(OtherEx.getStackTrace(e));
		}
	}

	@Test
	public void updateItem() {
		try {
			HangMienThueEntity objInput= new HangMienThueEntity();
			objInput.setMaDn("1111111111");
			Boolean bRet = (Boolean) dmc0202Processor.updateItem(jacksonEx.object2RequestData(objInput));
			Assert.assertTrue(bRet);
		} catch (ECARGOException e) {
			log4j.error(OtherEx.getStackTrace(e));
		}
	}

	@Test
	public void delete() {
		try {
			HangMienThueEntity objInput= new HangMienThueEntity();
			
			Boolean bRet = (Boolean) dmc0202Processor.deleteItem(jacksonEx.object2RequestData(objInput));
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
			List<HangMienThueEntity> list = (List<HangMienThueEntity>) dmc0202Processor
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
			List<HangMienThueEntity> list = (List<HangMienThueEntity>) dmc0202Processor
					.selectList(jacksonEx.object2RequestData(objInput));
			Assert.assertNotNull(list);
		} catch (ECARGOException e) {
			log4j.error(OtherEx.getStackTrace(e));
		}
	}
}
