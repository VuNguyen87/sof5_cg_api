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
import com.fis.customs.dao.ecustomsgw.DnCamDAO;
import com.fis.customs.entity.ecustomsgw.DnCamEntity;
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
public class TestQlDmc01Processor extends TestCaseLogImpl {
	protected static final Logger log4j = Logger.getLogger(TestQlDmc03Processor.class);
	private JacksonEx jacksonEx= JacksonEx.getInstance();
	
	@InjectMocks
	QlDmc01Processor dmc01Processor;
	
	@Mock
	DnCamDAO camDAO;
	
	private List<DnCamEntity> listdncamentities;
	
	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);

		dmc01Processor.setbIsUnitTest(true);

		initdata();
		
		Mockito.when(camDAO.selectAll(Mockito.any())).thenAnswer(new Answer<List<DnCamEntity>>() {
			public List<DnCamEntity> answer(InvocationOnMock invocation) throws Throwable {
				return listdncamentities;
			}
		});
		Mockito.when(camDAO.selectByOther(Mockito.any(), Mockito.any()))
				.thenAnswer(new Answer<List<DnCamEntity>>() {
					public List<DnCamEntity> answer(InvocationOnMock invocation) {
						Object[] args = invocation.getArguments();
						Map<String, Object> mWhere = new HashMap<String, Object>();
						if (!StringEx.isNullOrEmpty(((EcustomsGwKeySearch) args[1]).getMaDn())) {
							mWhere.put("MA_DN", ((EcustomsGwKeySearch) args[1]).getMaDn());
						}
						return LinQEx.findAllEx(new DnCamEntity(), listdncamentities, mWhere);
					}
				});

		Mockito.when(camDAO.insertItem(Mockito.any(), Mockito.any())).thenAnswer(new Answer<Boolean>() {
			public Boolean answer(InvocationOnMock invocation) {
				return listdncamentities.add(new DnCamEntity());
			}
		});
		// Mockito.when(camDAO.insertItemHIS(Mockito.any(),
		// Mockito.any(),)).thenAnswer(new Answer<Boolean>() {
		// public Boolean answer(InvocationOnMock invocation) {
		// return listdncamentities.add(new ParamsEntity());
		// }
		// });
		Mockito.when(camDAO.updateItem(Mockito.any(), Mockito.any())).thenAnswer(new Answer<Boolean>() {
			public Boolean answer(InvocationOnMock invocation) {
				return listdncamentities.contains(new DnCamEntity());
			}
		});
		Mockito.when(camDAO.delete(Mockito.any(), Mockito.any())).thenAnswer(new Answer<Boolean>() {
			public Boolean answer(InvocationOnMock invocation) {
				return listdncamentities.remove(new DnCamEntity());
			}
		});
	}
	
	private void initdata() {
		listdncamentities = new ArrayList<DnCamEntity>();
		
	}
	@Test
	public void insertItem() {
		try {
			DnCamEntity objInput= new DnCamEntity();
			objInput.setMaDn("1111111111");
			objInput.setTenDn("dsdasda");
			objInput.setErrMsg("IDE");
			Boolean bRet = (Boolean) dmc01Processor.insertItem(jacksonEx.object2RequestData(objInput));
			Assert.assertTrue(bRet);
		} catch (ECARGOException e) {
			log4j.error(OtherEx.getStackTrace(e));
		}
	}

	@Test
	public void updateItem() {
		try {
			DnCamEntity objInput= new DnCamEntity();
			objInput.setMaDn("1111111111");
			Boolean bRet = (Boolean) dmc01Processor.updateItem(jacksonEx.object2RequestData(objInput));
			Assert.assertTrue(bRet);
		} catch (ECARGOException e) {
			log4j.error(OtherEx.getStackTrace(e));
		}
	}

	@Test
	public void delete() {
		try {
			DnCamEntity objInput= new DnCamEntity();
			
			Boolean bRet = (Boolean) dmc01Processor.deleteItem(jacksonEx.object2RequestData(objInput));
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
			List<DnCamEntity> list = (List<DnCamEntity>) dmc01Processor
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
			List<DnCamEntity> list = (List<DnCamEntity>) dmc01Processor
					.selectList(jacksonEx.object2RequestData(objInput));
			Assert.assertNotNull(list);
		} catch (ECARGOException e) {
			log4j.error(OtherEx.getStackTrace(e));
		}
	}
	
	
	
}
