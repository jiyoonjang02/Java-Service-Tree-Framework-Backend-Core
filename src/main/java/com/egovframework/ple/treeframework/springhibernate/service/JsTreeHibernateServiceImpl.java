package com.egovframework.ple.treeframework.springhibernate.service;

import com.egovframework.ple.treeframework.springhibernate.dao.JsTreeHibernateDao;
import com.egovframework.ple.treeframework.springhibernate.interceptor.RouteTableInterceptor;
import com.egovframework.ple.treeframework.springhibernate.vo.JsTreeHibernateSearchDTO;
import com.egovframework.ple.treeframework.springhibernate.util.PaginationInfo;
import com.egovframework.ple.treeframework.springhibernate.util.StringUtils;
import org.apache.commons.collections15.CollectionUtils;
import org.apache.commons.collections15.Transformer;
import org.apache.commons.lang.math.NumberUtils;
import org.hibernate.CacheMode;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import org.unitils.util.ReflectionUtils;

import javax.annotation.Resource;
import javax.persistence.Table;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.lang.reflect.Field;
import java.util.*;

@Service("JsTreeHibernateService")
public class JsTreeHibernateServiceImpl implements JsTreeHibernateService {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@SuppressWarnings("rawtypes")
	@Resource(name = "jsTreeHibernateDao")
	private JsTreeHibernateDao jsTreeHibernateDao;

	@SuppressWarnings("unchecked")
	@Override
	public <T extends JsTreeHibernateSearchDTO> T getNode(T jsTreeHibernateDTO) throws Exception {

		logger.info("JsTreeHibernateServiceImpl :: getNode");
		jsTreeHibernateDao.setClazz(jsTreeHibernateDTO.getClass());
		jsTreeHibernateDTO.setWhere("c_id", jsTreeHibernateDTO.getC_id());
		Object uniqueObj = jsTreeHibernateDao.getUnique(jsTreeHibernateDTO);
		return (T) uniqueObj;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T extends JsTreeHibernateSearchDTO> List<T> getChildNodeWithoutPaging(T jsTreeHibernateDTO) throws Exception {
		jsTreeHibernateDao.setClazz(jsTreeHibernateDTO.getClass());
		List<T> list = jsTreeHibernateDao.getListWithoutPaging(jsTreeHibernateDTO);
		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T extends JsTreeHibernateSearchDTO> List<T> getChildNode(T jsTreeHibernateDTO) throws Exception {
		jsTreeHibernateDao.setClazz(jsTreeHibernateDTO.getClass());
		jsTreeHibernateDTO.setOrder(Order.desc("c_position"));
		List<T> list = jsTreeHibernateDao.getList(jsTreeHibernateDTO);
		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T extends JsTreeHibernateSearchDTO> List<T> getPaginatedChildNode(T jsTreeHibernateDTO) throws Exception {

		jsTreeHibernateDao.setClazz(jsTreeHibernateDTO.getClass());
		jsTreeHibernateDao.getCurrentSession().setCacheMode(CacheMode.IGNORE);
		int totalCount = jsTreeHibernateDao.getCount(jsTreeHibernateDTO);

		int autoPageSize = (int) Math.ceil(totalCount / jsTreeHibernateDTO.getPageUnit());

		/** paging */
		PaginationInfo paginationInfo = jsTreeHibernateDTO.getPaginationInfo();
		paginationInfo.setTotalRecordCount(totalCount);
		paginationInfo.setCurrentPageNo(jsTreeHibernateDTO.getPageIndex());
		paginationInfo.setRecordCountPerPage(jsTreeHibernateDTO.getPageUnit());
		paginationInfo.setPageSize(autoPageSize);

		jsTreeHibernateDTO.setFirstIndex(paginationInfo.getFirstRecordIndex());
		jsTreeHibernateDTO.setLastIndex(paginationInfo.getLastRecordIndex());
		jsTreeHibernateDTO.setRecordCountPerPage(paginationInfo.getRecordCountPerPage());

		jsTreeHibernateDTO.setOrder(Order.desc("c_left"));
		List<T> list = jsTreeHibernateDao.getList(jsTreeHibernateDTO);
		list.stream().forEach(data -> data.getPaginationInfo().setTotalRecordCount(totalCount));
		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T extends JsTreeHibernateSearchDTO> List<String> searchNode(T jsTreeHibernateDTO) throws Exception {
		jsTreeHibernateDao.setClazz(jsTreeHibernateDTO.getClass());
		jsTreeHibernateDao.getCurrentSession().setCacheMode(CacheMode.IGNORE);
		jsTreeHibernateDTO.setOrder(Order.asc("c_id"));
		List<T> collectionObjects = jsTreeHibernateDao.getList(jsTreeHibernateDTO);
		List<String> returnList = new ArrayList<String>();
		for (T rowObject : collectionObjects) {
			String rowData = "#node_" + rowObject.getC_id();
			returnList.add(rowData);
		}
		return returnList;
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional(rollbackFor = { Exception.class }, isolation = Isolation.SERIALIZABLE, propagation = Propagation.REQUIRED)
	public <T extends JsTreeHibernateSearchDTO> T addNode(T jsTreeHibernateDTO) throws Exception {

		jsTreeHibernateDao.setClazz(jsTreeHibernateDTO.getClass());
		jsTreeHibernateDao.getCurrentSession().setCacheMode(CacheMode.IGNORE);

		if (jsTreeHibernateDTO.getRef() < 0) {
			throw new RuntimeException("ref is minus");
		} else {

			T nodeByRef = (T) jsTreeHibernateDao.getUnique(jsTreeHibernateDTO.getRef());

			if ("default".equals(nodeByRef.getC_type())) {
				throw new RuntimeException("nodeByRef is default Type");
			}

			nodeByRef.setWhere("c_parentid", nodeByRef.getC_id());
			final long lastPosiotionOfNodeByRef = jsTreeHibernateDao.getCount(nodeByRef);

			jsTreeHibernateDTO.setC_position(lastPosiotionOfNodeByRef);

			long rightPointFromNodeByRef = nodeByRef.getC_right();
			rightPointFromNodeByRef = Math.max(rightPointFromNodeByRef, 1);

			long spaceOfTargetNode = 2;

			this.stretchLeftRightForMyselfFromJstree(spaceOfTargetNode, rightPointFromNodeByRef,
					jsTreeHibernateDTO.getCopy(), null, jsTreeHibernateDTO);

			long targetNodeLevel = jsTreeHibernateDTO.getRef() == 0 ? 0 : nodeByRef.getC_level() + 1;

			jsTreeHibernateDTO.setC_parentid(jsTreeHibernateDTO.getRef());
			jsTreeHibernateDTO.setC_left(rightPointFromNodeByRef);
			jsTreeHibernateDTO.setC_right(rightPointFromNodeByRef + 1);
			jsTreeHibernateDTO.setC_level(targetNodeLevel);

			long insertSeqResult = (long) jsTreeHibernateDao.insert(jsTreeHibernateDTO);
			if (insertSeqResult > 0) {
				final long SUCCESS = 1;
				jsTreeHibernateDTO.setStatus(SUCCESS);
				jsTreeHibernateDTO.setId(insertSeqResult);
			} else {
				throw new RuntimeException("심각한 오류 발생 - 삽입 노드");
			}
		}
		return jsTreeHibernateDTO;
	}

	@SuppressWarnings("unchecked")
	public <T extends JsTreeHibernateSearchDTO> void stretchLeftRightForMyselfFromJstree(long spaceOfTargetNode,
																						 long rightPositionFromNodeByRef, long copy, Collection<Long> c_idsByChildNodeFromNodeById,
																						 T jsTreeHibernateDTO) throws Exception {

		DetachedCriteria detachedLeftCriteria = DetachedCriteria.forClass(jsTreeHibernateDTO.getClass());
		stretchLeft(spaceOfTargetNode, rightPositionFromNodeByRef, copy, c_idsByChildNodeFromNodeById,
				detachedLeftCriteria);
		DetachedCriteria detachedRightCriteria = DetachedCriteria.forClass(jsTreeHibernateDTO.getClass());
		stretchRight(spaceOfTargetNode, rightPositionFromNodeByRef, copy, c_idsByChildNodeFromNodeById,
				detachedRightCriteria);
	}

	@SuppressWarnings("unchecked")
	public <T extends JsTreeHibernateSearchDTO> void stretchRight(long spaceOfTargetNode,
																  long rightPositionFromNodeByRef, long copy, Collection<Long> c_idsByChildNodeFromNodeById,
																  DetachedCriteria detachedCriteria) {
		logger.debug("-----------------------stretchRight 완료-----------------------");
		Criterion where = Restrictions.ge("c_right", rightPositionFromNodeByRef);
		detachedCriteria.add(where);
		if (copy == 0) {
			if (c_idsByChildNodeFromNodeById != null && c_idsByChildNodeFromNodeById.size() > 0) {
				detachedCriteria.add(Restrictions.and(Restrictions.not(Restrictions.in("c_id",
						c_idsByChildNodeFromNodeById))));
			}
		}
		detachedCriteria.addOrder(Order.asc("c_id"));
		List<T> updateTargetList = jsTreeHibernateDao.getListWithoutPaging(detachedCriteria);

		for (T perJsTreeHibernateDTO : updateTargetList) {
			perJsTreeHibernateDTO.setC_right(perJsTreeHibernateDTO.getC_right() + spaceOfTargetNode);
			try {
				jsTreeHibernateDao.update(perJsTreeHibernateDTO);
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
		}
	}

	@SuppressWarnings("unchecked")
	public <T extends JsTreeHibernateSearchDTO> void stretchLeft(long spaceOfTargetNode,
																 long rightPositionFromNodeByRef, long copy, Collection<Long> c_idsByChildNodeFromNodeById,
																 DetachedCriteria detachedCriteria) {

		logger.debug("-----------------------stretchLeft 완료-----------------------");
		Criterion where = Restrictions.ge("c_left", rightPositionFromNodeByRef);
		detachedCriteria.add(where);
		if (copy == 0) {
			if (c_idsByChildNodeFromNodeById != null && c_idsByChildNodeFromNodeById.size() > 0) {
				detachedCriteria.add(Restrictions.and(Restrictions.not(Restrictions.in("c_id",
						c_idsByChildNodeFromNodeById))));
			}
		}
		detachedCriteria.addOrder(Order.asc("c_id"));
		List<T> updateTargetList = jsTreeHibernateDao.getListWithoutPaging(detachedCriteria);

		for (T perJsTreeHibernateDTO : updateTargetList) {
			perJsTreeHibernateDTO.setC_left(perJsTreeHibernateDTO.getC_left() + spaceOfTargetNode);
			try {
				jsTreeHibernateDao.update(perJsTreeHibernateDTO);
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
		}
	}

	/**
	 * 파라미터로 넘겨진 인스턴스의 정보를 이용해 리플렉션하여 새로운 인스턴스를 만들어 반환한다.
	 * 리플렉션을 위한 타입 정보를 제공하기 위한 인스턴스
	 *
	 * @param jsTreeHibernateDTO
	 * @return T extends JsTreeHibernateSearchDTO
	 * @throws ClassNotFoundException
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 */
	@SuppressWarnings("unchecked")
	public <T extends JsTreeHibernateSearchDTO> T newInstance(T jsTreeHibernateDTO) throws Exception {
		Class<T> target = (Class<T>) Class.forName(jsTreeHibernateDTO.getClass().getCanonicalName());
		return target.newInstance();
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional(rollbackFor = { Exception.class }, isolation = Isolation.SERIALIZABLE, propagation = Propagation.REQUIRED)
	public <T extends JsTreeHibernateSearchDTO> int removeNode(T jsTreeHibernateDTO) throws Exception {

		jsTreeHibernateDao.setClazz(jsTreeHibernateDTO.getClass());
		jsTreeHibernateDao.getCurrentSession().setCacheMode(CacheMode.IGNORE);
		Criterion whereGetNode = Restrictions.eq("c_id", jsTreeHibernateDTO.getC_id());
		T removeNode = (T) jsTreeHibernateDao.getUnique(whereGetNode);

		long spaceOfTargetNode = removeNode.getC_right() - removeNode.getC_left() + 1;

		removeNode.setSpaceOfTargetNode(spaceOfTargetNode);

		DetachedCriteria detachedDeleteCriteria = DetachedCriteria.forClass(jsTreeHibernateDTO.getClass());
		Criterion where = Restrictions.ge("c_left", removeNode.getC_left());
		detachedDeleteCriteria.add(where);
		detachedDeleteCriteria.add(Restrictions.and(Restrictions.le("c_right", removeNode.getC_right())));
		detachedDeleteCriteria.addOrder(Order.asc("c_id"));
		try {
			List<T> deleteTargetList = jsTreeHibernateDao.getListWithoutPaging(detachedDeleteCriteria);
			for (T deleteJsTreeHibernateDTO : deleteTargetList) {
				jsTreeHibernateDao.delete(deleteJsTreeHibernateDTO);
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		DetachedCriteria detachedRemovedAfterLeftFixCriteria = DetachedCriteria.forClass(jsTreeHibernateDTO.getClass());
		Criterion whereRemovedAfterLeftFix = Restrictions.gt("c_left", removeNode.getC_right());
		detachedRemovedAfterLeftFixCriteria.add(whereRemovedAfterLeftFix);
		detachedRemovedAfterLeftFixCriteria.addOrder(Order.asc("c_id"));
		List<T> updateRemovedAfterLeftFixtList = jsTreeHibernateDao
				.getListWithoutPaging(detachedRemovedAfterLeftFixCriteria);
		for (T perLeftFixJsTreeHibernateDTO : updateRemovedAfterLeftFixtList) {
			perLeftFixJsTreeHibernateDTO.setC_left(perLeftFixJsTreeHibernateDTO.getC_left() - spaceOfTargetNode);
			jsTreeHibernateDao.update(perLeftFixJsTreeHibernateDTO);
		}

		DetachedCriteria detachedRemovedAfterRightFixCriteria = DetachedCriteria
				.forClass(jsTreeHibernateDTO.getClass());
		Criterion whereRemovedAfterRightFix = Restrictions.gt("c_right", removeNode.getC_left());
		detachedRemovedAfterRightFixCriteria.add(whereRemovedAfterRightFix);
		detachedRemovedAfterRightFixCriteria.addOrder(Order.asc("c_id"));
		List<T> updateRemovedAfterRightFixtList = jsTreeHibernateDao
				.getListWithoutPaging(detachedRemovedAfterRightFixCriteria);
		for (T perRightFixJsTreeHibernateDTO : updateRemovedAfterRightFixtList) {
			perRightFixJsTreeHibernateDTO.setC_right(perRightFixJsTreeHibernateDTO.getC_right() - spaceOfTargetNode);
			jsTreeHibernateDao.update(perRightFixJsTreeHibernateDTO);
		}

		DetachedCriteria detachedRemovedAfterPositionFixCriteria = DetachedCriteria.forClass(jsTreeHibernateDTO
				.getClass());
		Criterion whereRemovedAfterPositionFix = Restrictions.eq("c_parentid", removeNode.getC_parentid());
		detachedRemovedAfterPositionFixCriteria.add(whereRemovedAfterPositionFix);
		detachedRemovedAfterPositionFixCriteria.add(Restrictions.and(Restrictions.gt("c_position",
				removeNode.getC_position())));
		detachedRemovedAfterPositionFixCriteria.addOrder(Order.asc("c_id"));
		List<T> updateRemovedAfterPositionFixtList = jsTreeHibernateDao
				.getListWithoutPaging(detachedRemovedAfterPositionFixCriteria);
		for (T perPositionFixJsTreeHibernateDTO : updateRemovedAfterPositionFixtList) {
			perPositionFixJsTreeHibernateDTO.setC_position(perPositionFixJsTreeHibernateDTO.getC_position() - 1);
			jsTreeHibernateDao.update(perPositionFixJsTreeHibernateDTO);
		}
		return 0;
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional(rollbackFor = { Exception.class }, isolation = Isolation.SERIALIZABLE, propagation = Propagation.REQUIRED)
	public <T extends JsTreeHibernateSearchDTO> int updateNode(T jsTreeHibernateDTO) throws Exception {

		jsTreeHibernateDao.setClazz(jsTreeHibernateDTO.getClass());
		jsTreeHibernateDao.getCurrentSession().setCacheMode(CacheMode.IGNORE);
		T alterTargetNode = (T) jsTreeHibernateDao.getUnique(jsTreeHibernateDTO.getC_id());

		for (Field field : ReflectionUtils.getAllFields(jsTreeHibernateDTO.getClass())) {

			field.setAccessible(true);

			Object value = field.get(jsTreeHibernateDTO);

			if (!ObjectUtils.isEmpty(value)) {
				field.setAccessible(true);
				field.set(alterTargetNode, value);
			}

		}
		jsTreeHibernateDao.update(alterTargetNode);

		return 1;

	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional(rollbackFor = { Exception.class }, isolation = Isolation.SERIALIZABLE, propagation = Propagation.REQUIRED)
	public <T extends JsTreeHibernateSearchDTO> int alterNode(T jsTreeHibernateDTO) throws Exception {

		jsTreeHibernateDao.setClazz(jsTreeHibernateDTO.getClass());
		jsTreeHibernateDao.getCurrentSession().setCacheMode(CacheMode.IGNORE);
		T alterTargetNode = (T) jsTreeHibernateDao.getUnique(jsTreeHibernateDTO.getC_id());
		alterTargetNode.setC_title(jsTreeHibernateDTO.getC_title());
		alterTargetNode.setFieldFromNewInstance(jsTreeHibernateDTO);
		jsTreeHibernateDao.update(alterTargetNode);
		return 1;
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional(rollbackFor = { Exception.class }, isolation = Isolation.SERIALIZABLE, propagation = Propagation.REQUIRED)
	public <T extends JsTreeHibernateSearchDTO> int alterNodeType(T jsTreeHibernateDTO) throws Exception {

		jsTreeHibernateDao.setClazz(jsTreeHibernateDTO.getClass());
		jsTreeHibernateDao.getCurrentSession().setCacheMode(CacheMode.IGNORE);
		T nodeById = (T) jsTreeHibernateDao.getUnique(jsTreeHibernateDTO.getC_id());

		if (nodeById.getC_type().equals(jsTreeHibernateDTO.getC_type())) {
			return 1;
		} else if ("default".equals(jsTreeHibernateDTO.getC_type())) {
			nodeById.setWhere("c_parentid", nodeById.getC_id());
			List<T> childNodesFromNodeById = jsTreeHibernateDao.getList(nodeById);
			if (childNodesFromNodeById.size() != 0) {
				throw new RuntimeException("하위에 노드가 있는데 디폴트로 바꾸려고 함");
			} else {
				nodeById.setC_type(jsTreeHibernateDTO.getC_type());
				jsTreeHibernateDao.update(nodeById);
			}
		} else if ("folder".equals(jsTreeHibernateDTO.getC_type())) {
			nodeById.setC_type(jsTreeHibernateDTO.getC_type());
			jsTreeHibernateDao.update(nodeById);
			return 1;
		}
		return 1;
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional(rollbackFor = { Exception.class }, isolation = Isolation.SERIALIZABLE, propagation = Propagation.REQUIRED)
	public <T extends JsTreeHibernateSearchDTO> T moveNode(T jsTreeHibernateDTO, HttpServletRequest request)
			throws Exception {

		jsTreeHibernateDao.setClazz(jsTreeHibernateDTO.getClass());
		jsTreeHibernateDao.getCurrentSession().setCacheMode(CacheMode.IGNORE);

		logger.debug("***********************MoveNode***********************");
		logger.debug("-----------------------getNode 완료-----------------------");

		T nodeById = getNode(jsTreeHibernateDTO);
		if (nodeById == null) {
			throw new RuntimeException("nodeById is null");
		}
		Long nodeByIdLeft = nodeById.getC_left();

		logger.debug("-----------------------getChildNodeByLeftRight 완료-----------------------");
		DetachedCriteria getChildNodeByLeftRightCriteria = DetachedCriteria.forClass(jsTreeHibernateDTO.getClass());

		Criterion criterion = Restrictions.and(
				Restrictions.ge("c_left", nodeById.getC_left()),
				Restrictions.le("c_right", nodeById.getC_right())
		);
		getChildNodeByLeftRightCriteria.add(criterion);
		getChildNodeByLeftRightCriteria.addOrder(Order.asc("c_left"));
		List<T> childNodesFromNodeById = jsTreeHibernateDao.getListWithoutPaging(getChildNodeByLeftRightCriteria);

		logger.debug("-----------------------position 값이 over될때 방어코드-----------------------");
		DetachedCriteria getChildNodeByPositionCriteria = DetachedCriteria.forClass(jsTreeHibernateDTO.getClass());

		Criterion postion_criterion = Restrictions.eq("c_parentid", jsTreeHibernateDTO.getRef());
		getChildNodeByPositionCriteria.add(postion_criterion);
		int refChildCount = jsTreeHibernateDao.getListWithoutPaging(getChildNodeByPositionCriteria).size();
		if(jsTreeHibernateDTO.getC_position() > refChildCount){
			jsTreeHibernateDTO.setC_position(Long.valueOf(refChildCount));
		}

		logger.debug("-----------------------nodeByRef 완료-----------------------");
		T nodeByRef = (T) jsTreeHibernateDao.getUnique(jsTreeHibernateDTO.getRef());
		if(StringUtils.equals(nodeByRef.getC_type(),"default")){
			throw new RuntimeException("ref is not default type");
		}
		long rightPointFromNodeByRef = nodeByRef.getC_right();

		logger.debug("-----------------------childNodesFromNodeByRef 완료-----------------------");
		DetachedCriteria getNodeByRefCriteria = DetachedCriteria.forClass(jsTreeHibernateDTO.getClass());
		Criterion whereNodeByRef = Restrictions.eq("c_parentid", nodeByRef.getC_id());
		getNodeByRefCriteria.add(whereNodeByRef);
		List<T> childNodesFromNodeByRef = (List<T>) jsTreeHibernateDao.getListWithoutPaging(getNodeByRefCriteria);

		T t_ComprehensiveTree = newInstance(jsTreeHibernateDTO);

		long spaceOfTargetNode = 2;
		Collection<Long> c_idsByChildNodeFromNodeById = null;

		logger.debug("-----------------------c_idsByChildNodeFromNodeById 완료-----------------------");
		c_idsByChildNodeFromNodeById = CollectionUtils.collect(childNodesFromNodeById, new Transformer<T, Long>() {
			@Override
			public Long transform(T childNodePerNodeById) {
				return childNodePerNodeById.getC_id();
			}
		});

		if (c_idsByChildNodeFromNodeById.contains(jsTreeHibernateDTO.getRef())) {
			throw new RuntimeException("myself contains already refTargetNode");
		}

		spaceOfTargetNode = nodeById.getC_right() - nodeById.getC_left() + 1;

		if (!jsTreeHibernateDTO.isCopied()) {
			logger.debug("-----------------------cutMyself 완료-----------------------");
			this.cutMyself(nodeById, spaceOfTargetNode, c_idsByChildNodeFromNodeById);
		}

		logger.debug("-----------------------calculatePostion 완료-----------------------");

		//bug fix: 세션 값이 유지되므로, 구분자를 줘야 하는 문제를 테이블 명으로 잡았음.
		Table table = jsTreeHibernateDTO.getClass().getAnnotation(Table.class);
		String tableName = table.name();

		tableName = RouteTableInterceptor.setArmsReplaceTableName(request, tableName);
		this.calculatePostion(jsTreeHibernateDTO, nodeById, childNodesFromNodeByRef, request, tableName);

		if (rightPointFromNodeByRef < 1) {
			rightPointFromNodeByRef = 1;
		}

		if (!jsTreeHibernateDTO.isCopied()) {
			logger.debug("-----------------------stretchPositionForMyselfFromJstree 완료-----------------------");
			this.stretchPositionForMyselfFromJstree(c_idsByChildNodeFromNodeById, jsTreeHibernateDTO);

			int selfPosition = (nodeById.getC_parentid() == jsTreeHibernateDTO.getRef() && jsTreeHibernateDTO
					.getC_position() > nodeById.getC_position()) ? 1 : 0;

			for (T child : childNodesFromNodeByRef) {
				if (child.getC_position() - selfPosition == jsTreeHibernateDTO.getC_position()) {
					rightPointFromNodeByRef = child.getC_left();
					break;
				}
			}

			if (nodeById.getC_left() < rightPointFromNodeByRef) {
				rightPointFromNodeByRef -= spaceOfTargetNode;
			}
		}

		logger.debug("-----------------------stretchLeftRightForMyselfFromJstree 완료-----------------------");
		this.stretchLeftRightForMyselfFromJstree(spaceOfTargetNode, rightPointFromNodeByRef,
				jsTreeHibernateDTO.getCopy(), c_idsByChildNodeFromNodeById, jsTreeHibernateDTO);

		if (logger.isDebugEnabled()) {
			logger.debug(">>>>>>>>>>>>>>>>>>>>" + rightPointFromNodeByRef);
		}

		long targetNodeLevel = nodeById.getC_level() - (nodeByRef.getC_level() + 1);
		long comparePoint = nodeByIdLeft - rightPointFromNodeByRef;

		if (logger.isDebugEnabled()) {
			logger.debug(">>>>>>>>>>>>>>>>>>>>" + comparePoint);
		}

		if (jsTreeHibernateDTO.isCopied()) {
			logger.debug("-----------------------pasteMyselfFromJstree 완료-----------------------");
			long insertSeqResult = this
					.pasteMyselfFromJstree(jsTreeHibernateDTO.getRef(), comparePoint, spaceOfTargetNode,
							targetNodeLevel, c_idsByChildNodeFromNodeById, rightPointFromNodeByRef, nodeById);
			t_ComprehensiveTree.setId(insertSeqResult);
			logger.debug("-----------------------fixPositionParentIdOfCopyNodes-----------------------");
			this.fixPositionParentIdOfCopyNodes(insertSeqResult, jsTreeHibernateDTO.getC_position(), jsTreeHibernateDTO);
		} else {
			logger.debug("-----------------------enterMyselfFromJstree 완료-----------------------");
			this.enterMyselfFromJstree(jsTreeHibernateDTO.getRef(), jsTreeHibernateDTO.getC_position(),
					jsTreeHibernateDTO.getC_id(), comparePoint, targetNodeLevel, c_idsByChildNodeFromNodeById,
					jsTreeHibernateDTO);
			enterMyselfFixLeftRight(comparePoint, targetNodeLevel, c_idsByChildNodeFromNodeById, jsTreeHibernateDTO);
		}

		return t_ComprehensiveTree;
	}

	@SuppressWarnings("unchecked")
	public <T extends JsTreeHibernateSearchDTO> void enterMyselfFromJstree(long ref, long c_position, long c_id,
																		   long idif, long ldif, Collection<Long> c_idsByChildNodeFromNodeById, T jsTreeHibernateDTO) throws Exception {

		jsTreeHibernateDao.setClazz(jsTreeHibernateDTO.getClass());
		logger.debug("-----------------------enterMyselfFixPosition-----------------------");

		T childEnterMyselfFixPosition = (T) jsTreeHibernateDao.getUnique(jsTreeHibernateDTO.getC_id());
		childEnterMyselfFixPosition.setC_parentid(ref);
		childEnterMyselfFixPosition.setC_position(c_position);
		jsTreeHibernateDao.update(childEnterMyselfFixPosition);

	}

	@SuppressWarnings("unchecked")
	public <T extends JsTreeHibernateSearchDTO> void enterMyselfFixLeftRight(long idif, long ldif,
																			 Collection<Long> c_idsByChildNodeFromNodeById, T jsTreeHibernateDTO) {
		logger.debug("-----------------------enterMyselfFixLeftRight-----------------------");
		DetachedCriteria detachedEnterMyselfFixLeftRightCriteria = DetachedCriteria.forClass(jsTreeHibernateDTO
				.getClass());
		if (c_idsByChildNodeFromNodeById != null && c_idsByChildNodeFromNodeById.size() > 0) {
			Criterion whereEnterMyselfFixLeftRight = Restrictions.in("c_id", c_idsByChildNodeFromNodeById);
			detachedEnterMyselfFixLeftRightCriteria.add(whereEnterMyselfFixLeftRight);
			detachedEnterMyselfFixLeftRightCriteria.addOrder(Order.asc("c_id"));

			List<T> enterMyselfFixLeftRightList = jsTreeHibernateDao
					.getListWithoutPaging(detachedEnterMyselfFixLeftRightCriteria);
			for (T perEnterMyselfFixLeftRightList : enterMyselfFixLeftRightList) {
				logger.debug(perEnterMyselfFixLeftRightList.toString());
				perEnterMyselfFixLeftRightList.setC_left(perEnterMyselfFixLeftRightList.getC_left() - idif);
				perEnterMyselfFixLeftRightList.setC_right(perEnterMyselfFixLeftRightList.getC_right() - idif);
				perEnterMyselfFixLeftRightList.setC_level(perEnterMyselfFixLeftRightList.getC_level() - ldif);
				jsTreeHibernateDao.update(perEnterMyselfFixLeftRightList);
			}
		}
	}

	@SuppressWarnings("unchecked")
	public <T extends JsTreeHibernateSearchDTO> void fixPositionParentIdOfCopyNodes(long insertSeqResult,
																					long position, T jsTreeHibernateDTO) throws Exception {

		jsTreeHibernateDao.setClazz(jsTreeHibernateDTO.getClass());

		T node = (T) jsTreeHibernateDao.getUnique(insertSeqResult);

		logger.debug("-----------------------fixPositionParentIdOfCopyNodes 완료-----------------------");
		DetachedCriteria getChildNodeByLeftRightCriteria = DetachedCriteria.forClass(jsTreeHibernateDTO.getClass());
		Criterion whereChildNodeByLeftRight = Restrictions.ge("c_left", node.getC_left());
		getChildNodeByLeftRightCriteria.add(whereChildNodeByLeftRight);
		getChildNodeByLeftRightCriteria.add(Restrictions.and(Restrictions.le("c_right", node.getC_right())));
		getChildNodeByLeftRightCriteria.addOrder(Order.asc("c_left"));
		List<T> children = jsTreeHibernateDao.getListWithoutPaging(getChildNodeByLeftRightCriteria);

		Map<Long, Long> parentIds = new HashMap<Long, Long>();

		for (T child : children) {
			for (long i = child.getC_left() + 1; i < child.getC_right(); i++) {
				long parentId = child.getC_id();
				parentIds.put(i, parentId);
			}

			if (child.getC_id() == insertSeqResult) {
				if (logger.isDebugEnabled()) {
					logger.debug(">>>>>>>>>>>>>>>>> 기준노드가 잡혔음.");
					logger.debug("C_TITLE    = " + child.getC_title());
					logger.debug("C_ID       = " + insertSeqResult);
					logger.debug("C_POSITION = " + position);
				}

				node.setC_position(position);

				jsTreeHibernateDao.update(node);
				continue;
			}

			if (logger.isDebugEnabled()) {
				logger.debug(">>>>>>>>>>>>>>>>> 기준노드 아래 있는 녀석임");
				logger.debug("C_TITLE    = " + child.getC_title());
				logger.debug("C_ID       = " + child.getC_id());
				logger.debug("C_POSITION = " + child.getC_position());
				logger.debug("C_PARENTID = " + child.getC_parentid());
				logger.debug("부모아이디값 = " + parentIds.get(child.getC_left()));
			}

			child.setFixCopyId(parentIds.get(child.getC_left()));
			child.setC_parentid(parentIds.get(child.getC_left()));
			jsTreeHibernateDao.update(child);
		}
	}

	@SuppressWarnings("unchecked")
	public <T extends JsTreeHibernateSearchDTO> long pasteMyselfFromJstree(long ref, long idif,
																		   long spaceOfTargetNode, long ldif, Collection<Long> c_idsByChildNodeFromNodeById,
																		   long rightPositionFromNodeByRef, T nodeById) throws Exception {

		jsTreeHibernateDao.setClazz(nodeById.getClass());

		T onlyPasteMyselfFromJstree = getNode(nodeById);

		onlyPasteMyselfFromJstree.setRef(ref);
		onlyPasteMyselfFromJstree.setIdif(idif);
		onlyPasteMyselfFromJstree.setSpaceOfTargetNode(spaceOfTargetNode);
		onlyPasteMyselfFromJstree.setLdif(ldif);
		onlyPasteMyselfFromJstree.setC_idsByChildNodeFromNodeById(c_idsByChildNodeFromNodeById);
		onlyPasteMyselfFromJstree.setRightPositionFromNodeByRef(rightPositionFromNodeByRef);
		onlyPasteMyselfFromJstree.setNodeById(nodeById);

		onlyPasteMyselfFromJstree.setIdifLeft(idif
				+ (nodeById.getC_left() >= rightPositionFromNodeByRef ? spaceOfTargetNode : 0));
		onlyPasteMyselfFromJstree.setIdifRight(idif
				+ (nodeById.getC_left() >= rightPositionFromNodeByRef ? spaceOfTargetNode : 0));

		DetachedCriteria detachedPasteMyselfFromJstreeCriteria = DetachedCriteria.forClass(nodeById
				.getClass());
		if (c_idsByChildNodeFromNodeById != null && c_idsByChildNodeFromNodeById.size() > 0) {
			Criterion wherePasteMyselfFromJstree = Restrictions.in("c_id", c_idsByChildNodeFromNodeById);
			detachedPasteMyselfFromJstreeCriteria.add(wherePasteMyselfFromJstree);
			detachedPasteMyselfFromJstreeCriteria.addOrder(Order.desc("c_level"));

			List<T> pasteMyselfFromJstreeList = jsTreeHibernateDao
					.getListWithoutPaging(detachedPasteMyselfFromJstreeCriteria);
			for (T perPasteMyselfFromJstree : pasteMyselfFromJstreeList) {
				logger.debug("------pasteMyselfFromJstree------LOOP---" + perPasteMyselfFromJstree.getC_id());
				T addTarget = newInstance(perPasteMyselfFromJstree);

				addTarget.setC_parentid(onlyPasteMyselfFromJstree.getRef());
				addTarget.setC_position(perPasteMyselfFromJstree.getC_position());
				addTarget.setC_left(perPasteMyselfFromJstree.getC_left() - onlyPasteMyselfFromJstree.getIdifLeft());
				addTarget.setC_right(perPasteMyselfFromJstree.getC_right() - onlyPasteMyselfFromJstree.getIdifRight());
				addTarget.setC_level(perPasteMyselfFromJstree.getC_level() - onlyPasteMyselfFromJstree.getLdif());
				addTarget.setC_title(perPasteMyselfFromJstree.getC_title());
				addTarget.setC_type(perPasteMyselfFromJstree.getC_type());

				addTarget.setFieldFromNewInstance(perPasteMyselfFromJstree);
				logger.debug("여기에 추가적으로 확장한 필드에 대한 함수가 들어가야 한다 패턴을 쓰자");

				long insertSeqResult = (long) jsTreeHibernateDao.insert(addTarget);
				perPasteMyselfFromJstree.setId(insertSeqResult);

				if (insertSeqResult > 0) {
					return insertSeqResult;
				} else {
					throw new RuntimeException("심각한 오류 발생 - 삽입 노드");
				}
			}
		}

		return 0;
	}

	@SuppressWarnings("unchecked")
	public <T extends JsTreeHibernateSearchDTO> void stretchPositionForMyselfFromJstree(
			Collection<Long> c_idsByChildNodeFromNodeById, T jsTreeHibernateDTO) throws Exception {

		jsTreeHibernateDao.setClazz(jsTreeHibernateDTO.getClass());
		jsTreeHibernateDTO.setC_idsByChildNodeFromNodeById(c_idsByChildNodeFromNodeById);

		DetachedCriteria detachedStretchPositionForMyselfCriteria = DetachedCriteria.forClass(jsTreeHibernateDTO
				.getClass());
		Criterion whereStretchPositionForMyself = Restrictions.eq("c_parentid", jsTreeHibernateDTO.getRef());
		detachedStretchPositionForMyselfCriteria.add(whereStretchPositionForMyself);
		detachedStretchPositionForMyselfCriteria.add(Restrictions.and(Restrictions.ge("c_position",
				jsTreeHibernateDTO.getC_position())));
		if (jsTreeHibernateDTO.getCopy() == 0) {
			if (c_idsByChildNodeFromNodeById != null && c_idsByChildNodeFromNodeById.size() > 0) {
				detachedStretchPositionForMyselfCriteria.add(Restrictions.and(Restrictions.not(Restrictions.in("c_id",
						c_idsByChildNodeFromNodeById))));
			}
		}
		detachedStretchPositionForMyselfCriteria.addOrder(Order.asc("c_id"));

		List<T> stretchPositionForMyselfList = jsTreeHibernateDao
				.getListWithoutPaging(detachedStretchPositionForMyselfCriteria);
		for (T perStretchPositionForMyself : stretchPositionForMyselfList) {
			perStretchPositionForMyself.setC_position(perStretchPositionForMyself.getC_position() + 1);
			jsTreeHibernateDao.update(perStretchPositionForMyself);
		}

	}

	public <T extends JsTreeHibernateSearchDTO> void calculatePostion(T jsTreeHibernateDTO, T nodeById,
                                                                      List<T> childNodesFromNodeByRef, HttpServletRequest request, String tableName) throws Exception {
		HttpSession session = request.getSession();

		final boolean isMoveNodeInMyParent = (jsTreeHibernateDTO.getRef() == nodeById.getC_parentid());
		final boolean isMultiCounterZero = (jsTreeHibernateDTO.getMultiCounter() == 0);
		final boolean isBeyondTheCurrentToMoveNodes = (jsTreeHibernateDTO.getC_position() > nodeById.getC_position());

		if (isMoveNodeInMyParent) {
			if (logger.isDebugEnabled()) {
				logger.debug(">>>>>>>>>>>>>>>이동할 노드가 내 부모안에서 움직일때");
			}

			if (isMultiCounterZero) {
				if (isBeyondTheCurrentToMoveNodes) {
					if (logger.isDebugEnabled()) {
						logger.debug(">>>>>>>>>>>>>>>이동 할 노드가 현재보다 뒤일때");
						logger.debug("노드값=" + nodeById.getC_title());
						logger.debug("노드의 초기 위치값=" + nodeById.getC_position());
						logger.debug("노드의 요청받은 위치값=" + jsTreeHibernateDTO.getC_position());
						logger.debug("노드의 요청받은 멀티카운터=" + jsTreeHibernateDTO.getMultiCounter());
					}

					final boolean isFolderToMoveNodes = (jsTreeHibernateDTO.getC_position() > childNodesFromNodeByRef
							.size());

					if (isFolderToMoveNodes) {
						if (logger.isDebugEnabled()) {
							logger.debug("노드 이동시 폴더를 대상으로 했을때 생기는 버그 발생 =" + jsTreeHibernateDTO.getC_position());
						}
						long childNodesFromNodeByRefCnt = childNodesFromNodeByRef.size();
						jsTreeHibernateDTO.setC_position(childNodesFromNodeByRefCnt);
					} else {
						jsTreeHibernateDTO.setC_position(jsTreeHibernateDTO.getC_position() - 1);
					}
				}

				if (logger.isDebugEnabled()) {
					logger.debug("노드의 최종 위치값=" + jsTreeHibernateDTO.getC_position());
				}
				session.setAttribute(tableName + "_settedPosition", jsTreeHibernateDTO.getC_position());
			} else {
				if (logger.isDebugEnabled()) {
					logger.debug(">>>>>>>>>>>>>>>멀티 카운터가 0 이 아닐때");
					logger.debug("노드값=" + nodeById.getC_title());
					logger.debug("노드의 초기 위치값=" + nodeById.getC_position());
					logger.debug("노드의 요청받은 위치값=" + jsTreeHibernateDTO.getC_position());
					logger.debug("노드의 요청받은 멀티카운터=" + jsTreeHibernateDTO.getMultiCounter());
					logger.debug("0번 노드의 위치값=" + session.getAttribute(tableName + "_settedPosition"));
				}

				long increasePosition = 0;

				final boolean isMultiNodeOfPositionsAtZeroThanBehind = ((Integer) session
						.getAttribute("settedPosition") < nodeById.getC_position());

				if (isMultiNodeOfPositionsAtZeroThanBehind) {
					if (logger.isDebugEnabled()) {
						logger.debug(">>>>>>>>>>>>>>>멀티 노드의 위치가 0번 노드보다 뒤일때");
					}

					increasePosition = (Integer) session.getAttribute(tableName + "_settedPosition") + 1;
				} else {
					if (logger.isDebugEnabled()) {
						logger.debug(">>>>>>>>>>>>>>>멀티 노드의 위치가 0번 노드보다 앞일때");
					}

					if (jsTreeHibernateDTO.isCopied()) {
						increasePosition = (Integer) session.getAttribute(tableName + "_settedPosition") + 1;
					} else {
						increasePosition = (Integer) session.getAttribute(tableName + "_settedPosition");
					}

				}
				session.setAttribute(tableName + "_settedPosition", increasePosition);

				jsTreeHibernateDTO.setC_position(increasePosition);

				final boolean isSamePosition = (nodeById.getC_position() == jsTreeHibernateDTO.getC_position());

				if (isSamePosition) {
					if (logger.isDebugEnabled()) {
						logger.debug(">>>>>>>>>>>>>>>원래 노드 위치값과 최종 계산된 노드의 위치값이 동일한 경우");
					}

					session.setAttribute(tableName + "_settedPosition", increasePosition - 1);
				}

				if (logger.isDebugEnabled()) {
					logger.debug("노드의 최종 위치값=" + jsTreeHibernateDTO.getC_position());
				}
			}
		} else {
			if (logger.isDebugEnabled()) {
				logger.debug(">>>>>>>>>>>>>>>이동할 노드가 내 부모밖으로 움직일때");
			}

			if (isMultiCounterZero) {
				if (logger.isDebugEnabled()) {
					logger.debug(">>>>>>>>>>>>>>>멀티 카운터가 0 일때");
					logger.debug("노드값=" + nodeById.getC_title());
					logger.debug("노드의 초기 위치값=" + nodeById.getC_position());
					logger.debug("노드의 요청받은 위치값=" + jsTreeHibernateDTO.getC_position());
					logger.debug("노드의 요청받은 멀티카운터=" + jsTreeHibernateDTO.getMultiCounter());
					logger.debug("노드의 최종 위치값=" + jsTreeHibernateDTO.getC_position());
				}

				session.setAttribute(tableName + "_settedPosition", jsTreeHibernateDTO.getC_position());
			} else {
				if (logger.isDebugEnabled()) {
					logger.debug(">>>>>>>>>>>>>>>멀티 카운터가 0 이 아닐때");
					logger.debug("노드값=" + nodeById.getC_title());
					logger.debug("노드의 초기 위치값=" + nodeById.getC_position());
					logger.debug("노드의 요청받은 위치값=" + jsTreeHibernateDTO.getC_position());
					logger.debug("노드의 요청받은 멀티카운터=" + jsTreeHibernateDTO.getMultiCounter());
				}

				long increasePosition = 0;
				increasePosition = NumberUtils.toLong(session.getAttribute(tableName + "_settedPosition").toString()) + 1;
				jsTreeHibernateDTO.setC_position(increasePosition);
				session.setAttribute(tableName + "_settedPosition", increasePosition);

				if (logger.isDebugEnabled()) {
					logger.debug("노드의 최종 위치값=" + jsTreeHibernateDTO.getC_position());
				}
			}
		}
	}

	@SuppressWarnings("unchecked")
	public <T extends JsTreeHibernateSearchDTO> void cutMyself(T nodeById, long spaceOfTargetNode,
															   Collection<Long> c_idsByChildNodeFromNodeById) throws Exception {

		jsTreeHibernateDao.setClazz(nodeById.getClass());
		nodeById.setSpaceOfTargetNode(spaceOfTargetNode);
		nodeById.setC_idsByChildNodeFromNodeById(c_idsByChildNodeFromNodeById);

		logger.debug("***********************CutMyself***********************");
		logger.debug("-----------------------cutMyselfPositionFix-----------------------");
		DetachedCriteria cutMyselfPositionFixCriteria = DetachedCriteria.forClass(nodeById.getClass());
		Criterion whereCutMyselfPositionFix = Restrictions.eq("c_parentid", nodeById.getC_parentid());
		cutMyselfPositionFixCriteria.add(whereCutMyselfPositionFix);
		cutMyselfPositionFixCriteria.add(Restrictions.and(Restrictions.gt("c_position", nodeById.getC_position())));
		cutMyselfPositionFixCriteria.addOrder(Order.asc("c_id"));
		List<T> childCutMyselfPositionFix = jsTreeHibernateDao.getListWithoutPaging(cutMyselfPositionFixCriteria);
		for (T perNodeById : childCutMyselfPositionFix) {
			perNodeById.setC_position(perNodeById.getC_position() - 1);
			jsTreeHibernateDao.update(perNodeById);
		}

		logger.debug("-----------------------cutMyselfLeftFix-----------------------");
		DetachedCriteria cutMyselfLeftFixCriteria = DetachedCriteria.forClass(nodeById.getClass());
		Criterion whereCutMyselfLeftFix = Restrictions.gt("c_left", nodeById.getC_right());
		cutMyselfLeftFixCriteria.add(whereCutMyselfLeftFix);
		cutMyselfLeftFixCriteria.addOrder(Order.asc("c_id"));
		List<T> childCutMyselfLeftFix = jsTreeHibernateDao.getListWithoutPaging(cutMyselfLeftFixCriteria);
		for (T perCutMyselfLeftFix : childCutMyselfLeftFix) {
			perCutMyselfLeftFix.setC_left(perCutMyselfLeftFix.getC_left() - spaceOfTargetNode);
			jsTreeHibernateDao.update(perCutMyselfLeftFix);
		}

		logger.debug("-----------------------cutMyselfRightFix-----------------------");
		DetachedCriteria cutMyselfRightFixCriteria = DetachedCriteria.forClass(nodeById.getClass());
		Criterion whereCutMyselfRightFix = Restrictions.gt("c_right", nodeById.getC_left());
		cutMyselfRightFixCriteria.add(whereCutMyselfRightFix);
		if (c_idsByChildNodeFromNodeById != null && c_idsByChildNodeFromNodeById.size() > 0) {
			cutMyselfRightFixCriteria.add(Restrictions.and(Restrictions.not(Restrictions.in("c_id",
					c_idsByChildNodeFromNodeById))));
		}
		cutMyselfRightFixCriteria.addOrder(Order.asc("c_id"));
		List<T> childCutMyselfRightFix = jsTreeHibernateDao.getListWithoutPaging(cutMyselfRightFixCriteria);
		for (T perCutMyselfRightFix : childCutMyselfRightFix) {
			perCutMyselfRightFix.setC_right(perCutMyselfRightFix.getC_right() - spaceOfTargetNode);
			jsTreeHibernateDao.update(perCutMyselfRightFix);
		}

	}

}
