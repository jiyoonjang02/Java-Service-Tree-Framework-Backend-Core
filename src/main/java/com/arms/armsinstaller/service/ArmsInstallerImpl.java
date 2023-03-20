/*
 * @author Dongmin.lee
 * @since 2023-03-08
 * @version 23.03.08
 * @see <pre>
 *  Copyright (C) 2007 by 313 DEV GRP, Inc - All Rights Reserved
 *  Unauthorized copying of this file, via any medium is strictly prohibited
 *  Proprietary and confidential
 *  Written by 313 developer group <313@313.co.kr>, December 2010
 * </pre>
 */
package com.arms.armsinstaller.service;

import com.arms.armsinstaller.mapper.ArmsInstallerMybatisDao;
import com.arms.armsinstaller.model.ArmsInstallerEntity;
import com.arms.samplemybastis.mapper.MyBatisDao;
import com.egovframework.ple.treeframework.service.TreeServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import com.arms.armsinstaller.dao.ArmsInstallerRepository;

import java.util.List;


@AllArgsConstructor
@Service("armsInstaller")
public class ArmsInstallerImpl extends TreeServiceImpl implements ArmsInstaller{

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	private final ArmsInstallerRepository armsInstallerRepository;

	@Autowired
	ArmsInstallerMybatisDao armsInstallerMybatisDao;

	@Override
	public Integer makeDataBase() throws Exception {

		ArmsInstallerEntity armsInstallerEntity = new ArmsInstallerEntity();
		armsInstallerEntity.setC_title("T_ARMS_MAPPDSERVICENJIRA");

		Integer makeResult = armsInstallerMybatisDao.ddlExecute(armsInstallerEntity);

		armsInstallerMybatisDao.ddlSequenceExecute(armsInstallerEntity);

		armsInstallerMybatisDao.ddlExecute_comment_1(armsInstallerEntity);
		armsInstallerMybatisDao.ddlExecute_comment_2(armsInstallerEntity);
		armsInstallerMybatisDao.ddlExecute_comment_3(armsInstallerEntity);
		armsInstallerMybatisDao.ddlExecute_comment_4(armsInstallerEntity);
		armsInstallerMybatisDao.ddlExecute_comment_5(armsInstallerEntity);
		armsInstallerMybatisDao.ddlExecute_comment_6(armsInstallerEntity);
		armsInstallerMybatisDao.ddlExecute_comment_7(armsInstallerEntity);
		armsInstallerMybatisDao.ddlExecute_comment_8(armsInstallerEntity);
		armsInstallerMybatisDao.ddlExecute_comment_9(armsInstallerEntity);

		armsInstallerMybatisDao.dmlExecute_1(armsInstallerEntity);
		armsInstallerMybatisDao.dmlExecute_2(armsInstallerEntity);


		armsInstallerMybatisDao.ddlLogExecute(armsInstallerEntity);

		armsInstallerMybatisDao.ddlLogSequenceExecute(armsInstallerEntity);

		armsInstallerMybatisDao.ddlLogExecute_comment_1(armsInstallerEntity);
		armsInstallerMybatisDao.ddlLogExecute_comment_2(armsInstallerEntity);
		armsInstallerMybatisDao.ddlLogExecute_comment_3(armsInstallerEntity);
		armsInstallerMybatisDao.ddlLogExecute_comment_4(armsInstallerEntity);
		armsInstallerMybatisDao.ddlLogExecute_comment_5(armsInstallerEntity);
		armsInstallerMybatisDao.ddlLogExecute_comment_6(armsInstallerEntity);
		armsInstallerMybatisDao.ddlLogExecute_comment_7(armsInstallerEntity);
		armsInstallerMybatisDao.ddlLogExecute_comment_8(armsInstallerEntity);
		armsInstallerMybatisDao.ddlLogExecute_comment_9(armsInstallerEntity);
		armsInstallerMybatisDao.ddlLogExecute_comment_10(armsInstallerEntity);
		armsInstallerMybatisDao.ddlLogExecute_comment_11(armsInstallerEntity);
		armsInstallerMybatisDao.ddlLogExecute_comment_12(armsInstallerEntity);
		armsInstallerMybatisDao.ddlLogExecute_comment_13(armsInstallerEntity);

		this.make_trigger(armsInstallerEntity);
		return makeResult;
	}
	
	public void make_trigger(ArmsInstallerEntity armsInstallerEntity) {


		String addColums =",c_contents,c_pdservice_id,c_pdservice_version_id,c_pdservice_jira_ids";
		String addOldColums =",:old.c_contents,:old.c_pdservice_id,:old.c_pdservice_version_id,:old.c_pdservice_jira_ids";
		String addNewColums =",:new.c_contents,:new.c_pdservice_id,:new.c_pdservice_version_id,:new.c_pdservice_jira_ids";

		String sql =
				"CREATE OR REPLACE TRIGGER \"TRIG_" + armsInstallerEntity.getC_title() + "\"\n" +
						"BEFORE DELETE OR INSERT OR UPDATE\n" +
						"ON " + armsInstallerEntity.getC_title() + " REFERENCING NEW AS NEW OLD AS OLD\n" +
						"FOR EACH ROW\n" +
						"DECLARE\n" +
						"tmpVar NUMBER;\n" +
						"/******************************************************************************\n" +
						"   NAME:       TRIGGER_COMPREHENSIVETREE\n" +
						"   PURPOSE:    \n" +
						" \n" +
						"   REVISIONS:\n" +
						"   Ver        Date        Author           Description\n" +
						"   ---------  ----------  ---------------  ------------------------------------\n" +
						"   1.0        2012-08-29             1. Created this trigger.\n" +
						" \n" +
						"   NOTES:\n" +
						" \n" +
						"   Automatically available Auto Replace Keywords:\n" +
						"      Object Name:     TRIGGER_COMPREHENSIVETREE\n" +
						"      Sysdate:         2012-08-29\n" +
						"      Date and Time:   2012-08-29, 오후 5:26:44, and 2012-08-29 오후 5:26:44\n" +
						"      Username:         (set in TOAD Options, Proc Templates)\n" +
						"      Table Name:      T_ARMS_REQADD (set in the \"New PL/SQL Object\" dialog)\n" +
						"      Trigger Options:  (set in the \"New PL/SQL Object\" dialog)\n" +
						"******************************************************************************/\n" +
						"BEGIN\n" +
						"  tmpVar := 0;\n" +
						"   IF UPDATING  THEN    \n" +
						"       insert into " + armsInstallerEntity.getC_title() + "_LOG (C_ID,C_DATAID,C_PARENTID,C_POSITION,C_LEFT,C_RIGHT,C_LEVEL,C_TITLE,C_TYPE,C_METHOD,C_STATE,C_DATE" + addColums + ")\n" +
						"       values (S_" + armsInstallerEntity.getC_title() + "_LOG.NEXTVAL,:old.C_ID,:old.C_PARENTID,:old.C_POSITION,:old.C_LEFT,:old.C_RIGHT,:old.C_LEVEL,:old.C_TITLE,:old.C_TYPE,'update','변경이전데이터',sysdate" + addOldColums + ");\n" +
						"       insert into " + armsInstallerEntity.getC_title() + "_LOG (C_ID,C_DATAID,C_PARENTID,C_POSITION,C_LEFT,C_RIGHT,C_LEVEL,C_TITLE,C_TYPE,C_METHOD,C_STATE,C_DATE" + addColums + ")\n" +
						"       values (S_" + armsInstallerEntity.getC_title() + "_LOG.NEXTVAL,:new.C_ID,:new.C_PARENTID,:new.C_POSITION,:new.C_LEFT,:new.C_RIGHT,:new.C_LEVEL,:new.C_TITLE,:new.C_TYPE,'update','변경이후데이터',sysdate" + addNewColums + ");\n" +
						"    END IF;\n" +
						"   IF DELETING THEN\n" +
						"       insert into " + armsInstallerEntity.getC_title() + "_LOG (C_ID,C_DATAID,C_PARENTID,C_POSITION,C_LEFT,C_RIGHT,C_LEVEL,C_TITLE,C_TYPE,C_METHOD,C_STATE,C_DATE" + addColums + ")\n" +
						"       values (S_" + armsInstallerEntity.getC_title() + "_LOG.NEXTVAL,:old.C_ID,:old.C_PARENTID,:old.C_POSITION,:old.C_LEFT,:old.C_RIGHT,:old.C_LEVEL,:old.C_TITLE,:old.C_TYPE,'delete','삭제된데이터',sysdate" + addOldColums + ");\n" +
						"   END IF;   \n" +
						"   IF INSERTING  THEN\n" +
						"       insert into " + armsInstallerEntity.getC_title() + "_LOG (C_ID,C_DATAID,C_PARENTID,C_POSITION,C_LEFT,C_RIGHT,C_LEVEL,C_TITLE,C_TYPE,C_METHOD,C_STATE,C_DATE" + addColums + ")\n" +
						"       values (S_" + armsInstallerEntity.getC_title() + "_LOG.NEXTVAL,:new.C_ID,:new.C_PARENTID,:new.C_POSITION,:new.C_LEFT,:new.C_RIGHT,:new.C_LEVEL,:new.C_TITLE,:new.C_TYPE,'insert','삽입된데이터',sysdate" + addNewColums + ");\n" +
						"   END IF;\n" +
						" \n" +
						"  EXCEPTION\n" +
						"    WHEN OTHERS THEN\n" +
						"      -- Consider logging the error and then re-raise\n" +
						"      RAISE;\n" +
						"END TRIG_" + armsInstallerEntity.getC_title() + ";";

		armsInstallerMybatisDao.trigger_make(sql);
	}

}