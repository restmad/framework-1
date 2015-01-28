/*
 * Copyright (c)  2013, Newtouch
 * All rights reserved. 
 *
 * $id: CodeTypeServiceImpl.java 9552 2013-1-12 下午8:35:50 WangLijun$
 */
package com.newtouch.lion.service.system.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.newtouch.lion.common.Assert;
import com.newtouch.lion.common.sql.HqlUtils;
import com.newtouch.lion.dao.system.CodeTypeDao;
import com.newtouch.lion.json.JSONParser;
import com.newtouch.lion.model.system.CodeType;
import com.newtouch.lion.page.PageResult;
import com.newtouch.lion.query.QueryCriteria;
import com.newtouch.lion.service.AbstractService;
import com.newtouch.lion.service.datagrid.DataColumnService;
import com.newtouch.lion.service.system.CodeTypeService;

/**
 * <p>
 * Title:
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: Copyright (c) 2013
 * </p>
 * <p>
 * Company: Newtouch
 * </p>
 * 
 * @author WangLijun
 * @version 1.0
 */
@Service("codeTypeService")
public class CodeTypeServiceImpl extends AbstractService implements  CodeTypeService {

	@Autowired
	private CodeTypeDao codeTypeDao;
	
	@Autowired
	private  DataColumnService dataColumnService;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.lion.framework.service.system.CodeTypeService#doCreateCodeType
	 * (com.lion.framework.model.system.CodeType)
	 */
	@Override
	public void doCreateCodeType(CodeType codeType) {
		codeTypeDao.save(codeType);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.lion.framework.service.system.CodeTypeService#doFindAll()
	 */
	@Override
	public List<CodeType> doFindAll() {
		return codeTypeDao.findAll();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.lion.framework.service.system.CodeTypeService#doFindById(java.
	 * lang.Long)
	 */
	@Override
	public CodeType doFindById(Long id) {
		return this.codeTypeDao.findById(id);
	}
	
	

	/* (non-Javadoc)
	 * @see com.lion.framework.service.system.CodeTypeService#doGetById(java.lang.Long)
	 */
	@Override
	public CodeType doGetById(Long id) {
		return this.codeTypeDao.getById(id);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.lion.framework.service.system.CodeTypeService#doDelete(com.lion
	 * .framework.model.system.CodeType)
	 */
	@Override
	public void doDelete(CodeType codeType) {
		this.codeTypeDao.remove(codeType);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.lion.framework.service.system.CodeTypeService#doDeleteById(java
	 * .lang.Long)
	 */
	@Override
	public int doDeleteById(Long id) {
		CodeType codeType = this.doFindById(id);
		if (codeType == null)
			return 0;
		this.doDelete(codeType);
		return 1;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.lion.framework.service.system.CodeTypeService#doFindByCriteria
	 * (com.lion.framework.common.QueryCriteria)
	 */
	@Override
	public PageResult<CodeType> doFindByCriteria(QueryCriteria criteria) {
		String queryEntry = "from CodeType";
		
		String[] whereBodies = {"type =:type","isDeleted=false"," nameZh  like :nameZh " };
		
		String fromJoinSubClause = "";
		
		Map<String, Object> params = criteria.getQueryCondition();
		
		String orderField = criteria.getOrderField();
		
		String orderDirection = criteria.getOrderDirection();
		
		String hql = HqlUtils.generateHql(queryEntry, fromJoinSubClause, whereBodies, orderField, orderDirection, params);
		
		int pageSize = criteria.getPageSize();
		
		int startIndex = criteria.getStartIndex();
		
		PageResult<CodeType> pageResult = this.codeTypeDao.query(hql, HqlUtils.generateCountHql(hql, null), params, startIndex, pageSize);
		
		return pageResult;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.lion.framework.service.system.CodeTypeService#doUpdate(com.lion
	 * .framework.model.system.CodeType)
	 */
	@Override
	public CodeType doUpdate(CodeType codeType) {
		this.codeTypeDao.save(codeType);
		return codeType;
	}
	
	
	

	/* (non-Javadoc)
	 * @see com.lion.framework.service.system.CodeTypeService#doUpdateByParams(java.lang.Long, java.lang.String, java.lang.String, java.lang.String, java.lang.Integer, java.lang.Boolean)
	 */
	@Override
	public CodeType doUpdateByParams(Long id, String type, String nameEn,
			String nameZh, Integer codeLenLimit, Boolean editable) {
		// TODO Auto-generated method stub
		CodeType codeType=this.codeTypeDao.findById(id);
		codeType.setType(type);
		codeType.setCodeLenLimit(codeLenLimit);
		codeType.setNameEn(nameEn);
		codeType.setNameZh(nameZh);
		codeType.setEditable(editable);
		this.codeTypeDao.save(codeType);
		return codeType;
	}
	
	
	

	/* (non-Javadoc)
	 * @see com.lion.framework.service.system.CodeTypeService#doFindByCriteria(com.lion.framework.common.QueryCriteria, java.lang.String)
	 */
	@Override
	public String doFindByCriteria(QueryCriteria criteria, String tableId) {
		PageResult<CodeType> pageResult=this.doFindByCriteria(criteria);
		Set<String> properties=this.dataColumnService.doFindColumnsByTableId(tableId);
		return JSONParser.toJSONDataGridString(pageResult, properties);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.lion.framework.service.system.CodeTypeService#doLogicalDelete(
	 * java.lang.Long)
	 */
	@Override
	public void doLogicalDelete(Long id) {
		CodeType codeType = this.doFindById(id);
		codeType.setDeleteDate(new Date());
		codeType.setIsDeleted(Boolean.TRUE);
		this.doUpdate(codeType);
	}

	/* (non-Javadoc)
	 * @see com.newtouch.lion.service.system.CodeTypeService#doIsExistByNameEn(java.lang.String)
	 */
	@Override
	public boolean doIsExistByNameEn(String nameEn) {
		// TODO Auto-generated method stub
		Assert.notNull(nameEn);
		CodeType codeType = this.doFindTypeByNameEn(nameEn);
		if (codeType != null)
			return true;
		return false;
	}

	/* (non-Javadoc)
	 * @see com.newtouch.lion.service.system.CodeTypeService#doFindTypeByNameEn(java.lang.String)
	 */
	@Override
	public CodeType doFindTypeByNameEn(String nameEn) {
		Assert.notNull(nameEn);
		String hql = "from CodeType  where nameEn=:nameEn";
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("nameEn", nameEn);
		java.util.List<CodeType> codeTypes = codeTypeDao.query(hql, params);
		if (codeTypes != null && codeTypes.size() > 0) {
			return codeTypes.get(0);
		}
		return null;
	}

	/* (non-Javadoc)
	 * @see com.newtouch.lion.service.system.CodeTypeService#doCreate(com.newtouch.lion.model.system.Group)
	 */
	@Override
	public void doCreate(CodeType codeType) {
		// TODO Auto-generated method stub
		Assert.notNull(codeType);
		codeTypeDao.save(codeType);
	}
	
}
