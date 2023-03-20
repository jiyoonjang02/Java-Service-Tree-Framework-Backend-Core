package com.arms.armsinstaller.mapper;

import com.arms.armsinstaller.model.ArmsInstallerEntity;
import com.arms.samplemybastis.model.MyBatisEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.annotations.UpdateProvider;

import java.util.List;

@Mapper
public interface ArmsInstallerMybatisDao {

    static class PureSqlProvider {
        public String sql(String sql) {
            return sql;
        }
    }

    public Integer ddlExecute(ArmsInstallerEntity armsInstallerEntity);

    public void ddlSequenceExecute(ArmsInstallerEntity armsInstallerEntity);

    public void ddlExecute_comment_1(ArmsInstallerEntity armsInstallerEntity);

    public void ddlExecute_comment_2(ArmsInstallerEntity armsInstallerEntity);

    public void ddlExecute_comment_3(ArmsInstallerEntity armsInstallerEntity);

    public void ddlExecute_comment_4(ArmsInstallerEntity armsInstallerEntity);

    public void ddlExecute_comment_5(ArmsInstallerEntity armsInstallerEntity);

    public void ddlExecute_comment_6(ArmsInstallerEntity armsInstallerEntity);

    public void ddlExecute_comment_7(ArmsInstallerEntity armsInstallerEntity);

    public void ddlExecute_comment_8(ArmsInstallerEntity armsInstallerEntity);

    public void ddlExecute_comment_9(ArmsInstallerEntity armsInstallerEntity);

    public void dmlExecute_1(ArmsInstallerEntity armsInstallerEntity);

    public void dmlExecute_2(ArmsInstallerEntity armsInstallerEntity);

    // Log Make

    public Integer ddlLogExecute(ArmsInstallerEntity armsInstallerEntity);

    public void ddlLogSequenceExecute(ArmsInstallerEntity armsInstallerEntity);

    public void ddlLogExecute_comment_1(ArmsInstallerEntity armsInstallerEntity);

    public void ddlLogExecute_comment_2(ArmsInstallerEntity armsInstallerEntity);

    public void ddlLogExecute_comment_3(ArmsInstallerEntity armsInstallerEntity);

    public void ddlLogExecute_comment_4(ArmsInstallerEntity armsInstallerEntity);

    public void ddlLogExecute_comment_5(ArmsInstallerEntity armsInstallerEntity);

    public void ddlLogExecute_comment_6(ArmsInstallerEntity armsInstallerEntity);

    public void ddlLogExecute_comment_7(ArmsInstallerEntity armsInstallerEntity);

    public void ddlLogExecute_comment_8(ArmsInstallerEntity armsInstallerEntity);

    public void ddlLogExecute_comment_9(ArmsInstallerEntity armsInstallerEntity);

    public void ddlLogExecute_comment_10(ArmsInstallerEntity armsInstallerEntity);

    public void ddlLogExecute_comment_11(ArmsInstallerEntity armsInstallerEntity);

    public void ddlLogExecute_comment_12(ArmsInstallerEntity armsInstallerEntity);

    public void ddlLogExecute_comment_13(ArmsInstallerEntity armsInstallerEntity);

    @UpdateProvider(type = PureSqlProvider.class, method = "sql")
    public void trigger_make(String sql);

}
