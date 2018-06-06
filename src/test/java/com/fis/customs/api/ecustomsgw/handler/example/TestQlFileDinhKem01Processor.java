package com.fis.customs.api.ecustomsgw.handler.example;

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

import com.fis.customs.dao.ecustomsgw.FileDinhkemBsDAO;
import com.fis.customs.entity.ecustomsgw.FileDinhkemBsEntity;
import com.fis.customs.model.ecargo.KeySearchList_Obj;
import com.fis.customs.util.ecargo.ECARGOException;
import com.fis.customs.util.ecargo.LinQEx;
import com.fis.customs.util.ecargo.OtherEx;

import vn.fis.sof5.core.common.JacksonEx;
import vn.fis.sof5.core.testcase.TestCaseLogImpl;

/**
 * @author ThangNM11 - 04/05/2018
 *
 */

@RunWith(Parameterized.class)
@FixMethodOrder(org.junit.runners.MethodSorters.JVM)
public class TestQlFileDinhKem01Processor extends TestCaseLogImpl {
	protected static final Logger log4j = Logger.getLogger(TestQlFileDinhKem01Processor.class);
	private JacksonEx jacksonEx = JacksonEx.getInstance();
	@InjectMocks
	private QlFileDinhKem01Processor qlFileDinhKem01Processor;
	@Mock
	private FileDinhkemBsDAO fileDinhkemDao;

	private List<FileDinhkemBsEntity> lstFileDinhKem;
	public long iFileDinhKemID;
	public String sLoaiCt;
	public long iLoaiCtCount;

	public TestQlFileDinhKem01Processor(long iFileDinhKemID, String sLoaiCt, long iLoaiCtCount) {
		this.iFileDinhKemID = iFileDinhKemID;
		this.sLoaiCt = sLoaiCt;
		this.iLoaiCtCount = iLoaiCtCount;
	}

	@Parameters(name = "{index}: test...({0})={1}")
	public static List<Object[]> data() {
		try {
			List<Object[]> lstParam = new ArrayList<Object[]>();
			lstParam.add(new Object[] { 1, "EMF", 3 });
			lstParam.add(new Object[] { 2, "TQDT_KBC", 4 });
			lstParam.add(new Object[] { 3, "DNC", 0 });
			lstParam.add(new Object[] { 9, "abc", 0 });
			return lstParam;
		} catch (Exception e) {
			log4j.error(OtherEx.getStackTrace(e));
		}
		return null;
	}

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);

		qlFileDinhKem01Processor.setbIsUnitTest(true);
		initDataTest();

		Mockito.when(fileDinhkemDao.insertItem(Mockito.any(), Mockito.any())).thenAnswer(new Answer<Boolean>() {
			public Boolean answer(InvocationOnMock invocation) {
				return lstFileDinhKem.add(new FileDinhkemBsEntity());
			}
		});
		Mockito.when(fileDinhkemDao.updateItem(Mockito.any(), Mockito.any())).thenAnswer(new Answer<Boolean>() {
			public Boolean answer(InvocationOnMock invocation) {
				return lstFileDinhKem.contains(new FileDinhkemBsEntity());
			}
		});
		Mockito.when(fileDinhkemDao.deleteItem(Mockito.any(), Mockito.any())).thenAnswer(new Answer<Boolean>() {
			public Boolean answer(InvocationOnMock invocation) {
				return lstFileDinhKem.remove(new FileDinhkemBsEntity());
			}
		});
		Mockito.when(fileDinhkemDao.selectItem(Mockito.any(), Mockito.any()))
				.thenAnswer(new Answer<FileDinhkemBsEntity>() {
					public FileDinhkemBsEntity answer(InvocationOnMock invocation) {
						Object[] args = invocation.getArguments();
						Map<String, Object> mWhere = new HashMap<String, Object>();
						mWhere.put("ID", ((FileDinhkemBsEntity) args[1]).getId());
						return LinQEx.findItemEx(new FileDinhkemBsEntity(), lstFileDinhKem, mWhere);
					}
				});
		Mockito.when(fileDinhkemDao.selectByOther(Mockito.any(), Mockito.any()))
				.thenAnswer(new Answer<List<FileDinhkemBsEntity>>() {
					public List<FileDinhkemBsEntity> answer(InvocationOnMock invocation) {
						Object[] args = invocation.getArguments();
						Map<String, Object> mWhere = new HashMap<String, Object>();
						mWhere.put("LOAI_CT", ((FileDinhkemBsEntity) args[1]).getLoaiCt());
						return LinQEx.findAllEx(new FileDinhkemBsEntity(), lstFileDinhKem, mWhere);
					}
				});
	}

	protected void initDataTest() {
		lstFileDinhKem = new ArrayList<FileDinhkemBsEntity>();
		FileDinhkemBsEntity obj;
		for (int i = 0; i < 10; i++) {
			obj = new FileDinhkemBsEntity();
			obj.setId(i + 1);
			if (obj.getId() % 3 == 0) {
				obj.setLoaiCt("EMF");
			} else if (obj.getId() % 2 == 0) {
				obj.setLoaiCt("TQDT_KBC");
			} else {
				obj.setLoaiCt("GROUP_" + i);
			}
			lstFileDinhKem.add(obj);
		}
	}

	@Test
	public void insertItem() {
		try {
			FileDinhkemBsEntity objInput = new FileDinhkemBsEntity();
			objInput.setId(12);
			objInput.setLoaiCt("KBC");
			Boolean bRet = (Boolean) qlFileDinhKem01Processor.insertItem(jacksonEx.object2RequestData(objInput));
			Assert.assertTrue(bRet);
		} catch (ECARGOException e) {
			log4j.error(OtherEx.getStackTrace(e));
		}
	}

	@Test
	public void updateItem() {
		try {
			FileDinhkemBsEntity objInput = new FileDinhkemBsEntity();
			objInput.setId(12);
			objInput.setLoaiCt("KBC");
			Boolean bRet = (Boolean) qlFileDinhKem01Processor.updateItem(jacksonEx.object2RequestData(objInput));
			Assert.assertTrue(bRet);
		} catch (ECARGOException e) {
			log4j.error(OtherEx.getStackTrace(e));
		}
	}

	@Test
	// @Ignore
	public void deleteItem() {
		try {
			FileDinhkemBsEntity objInput = new FileDinhkemBsEntity();
			objInput.setId(12);
			objInput.setLoaiCt("KBC");
			Boolean bRet = (Boolean) qlFileDinhKem01Processor.deleteItem(jacksonEx.object2RequestData(objInput));
			Assert.assertTrue(bRet);
		} catch (ECARGOException e) {
			log4j.error(OtherEx.getStackTrace(e));
		}
	}

	@Test
	public void selectItem() {
		try {
			FileDinhkemBsEntity objInput = new FileDinhkemBsEntity();
			objInput.setId(iFileDinhKemID);
			FileDinhkemBsEntity obj = (FileDinhkemBsEntity) qlFileDinhKem01Processor
					.selectItem(jacksonEx.object2RequestData(objInput));
			Assert.assertTrue(obj != null && obj.getId() > 0);
		} catch (ECARGOException e) {
			log4j.error(OtherEx.getStackTrace(e));
		}
	}

	@SuppressWarnings("unchecked")
	@Test
	public void selectList() {
		try {
			KeySearchList_Obj objKey = new KeySearchList_Obj();
			objKey.setLoai(sLoaiCt);
			List<FileDinhkemBsEntity> list = (List<FileDinhkemBsEntity>) qlFileDinhKem01Processor
					.selectList(jacksonEx.object2RequestData(objKey));
			Assert.assertTrue(list.size() == iLoaiCtCount);
		} catch (ECARGOException e) {
			log4j.error(OtherEx.getStackTrace(e));
		}
	}
}
