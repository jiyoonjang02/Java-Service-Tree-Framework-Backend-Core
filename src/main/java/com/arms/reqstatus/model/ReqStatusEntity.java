/*
 * @author Dongmin.lee
 * @since 2023-03-21
 * @version 23.03.21
 * @see <pre>
 *  Copyright (C) 2007 by 313 DEV GRP, Inc - All Rights Reserved
 *  Unauthorized copying of this file, via any medium is strictly prohibited
 *  Proprietary and confidential
 *  Written by 313 developer group <313@313.co.kr>, December 2010
 * </pre>
 */
package com.arms.reqstatus.model;

import com.egovframework.ple.treeframework.model.TreeBaseEntity;
import com.egovframework.ple.treeframework.model.TreeSearchEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.*;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.*;
import java.io.Serializable;

@Entity
@Getter
@Setter
@Table(name = "T_ARMS_REQSTATUS")
@SelectBeforeUpdate(value=true)
@DynamicInsert(value=true)
@DynamicUpdate(value=true)
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class ReqStatusEntity extends TreeSearchEntity implements Serializable {

    public ReqStatusEntity() {
        super();
    }

    public ReqStatusEntity(Boolean copyBooleanValue) {
        super();
        this.copyBooleanValue = copyBooleanValue;
    }

 	@Override
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "c_id")
    public Long getC_id() {
        return super.getC_id();
    }
    //@Getter @Setter

    @Column(name = "c_pdservice_link")
    private Long c_pdservice_link;

    @Column(name = "c_pdservice_name")
    @Type(type="text")
    private String c_pdservice_name;

    @Column(name = "c_pdserviceversion_link")
    private Long c_version_link;

    @Column(name = "c_jira_link")
    private Long c_jira_link;

    @Column(name = "c_jira_ver_link")
    private Long c_jira_ver_link;

    //ReqStatus Issue Link
    @Column(name = "c_issue_link")
    private Long c_issue_link;

    @Column(name = "c_reviewer01")
    @Type(type="text")
    private String c_reviewer01;

    @Column(name = "c_reviewer02")
    private String c_reviewer02;

    @Column(name = "c_reviewer03")
    @Type(type="text")
    private String c_reviewer03;

    @Column(name = "c_reviewer04")
    @Type(type="text")
    private String c_reviewer04;

    @Column(name = "c_reviewer05")
    @Type(type="text")
    private String c_reviewer05;

    @Column(name = "c_reviewer01_status")
    @Type(type="text")
    private String c_reviewer01_status;

    @Column(name = "c_reviewer02_status")
    @Type(type="text")
    private String c_reviewer02_status;

    @Column(name = "c_reviewer03_status")
    @Type(type="text")
    private String c_reviewer03_status;

    @Column(name = "c_reviewer04_status")
    @Type(type="text")
    private String c_reviewer04_status;

    @Column(name = "c_reviewer05_status")
    @Type(type="text")
    private String c_reviewer05_status;

    @Column(name = "c_writer")
    @Type(type="text")
    private String c_writer;

    @Column(name = "c_writer_date")
    @Type(type="text")
    private String c_writer_date;

    @Column(name = "c_priority")
    @Type(type="text")
    private Long c_priority;

    @Column(name = "c_req_status")
    @Type(type="text")
    private String c_req_status;

    @Lob
    @Column(name = "c_contents")
    private String c_contents;
    /*
     * Extend Bean Field
     */
	@JsonIgnore
    private Boolean copyBooleanValue;

    @Transient
	@ApiModelProperty(hidden = true)
    public Boolean getCopyBooleanValue() {
        copyBooleanValue = false;
        if (this.getCopy() == 0) {
            copyBooleanValue = false;
        } else {
            copyBooleanValue = true;
        }
        return copyBooleanValue;
    }

    public void setCopyBooleanValue(Boolean copyBooleanValue) {
        this.copyBooleanValue = copyBooleanValue;
    }

    @Override
    public <T extends TreeSearchEntity> void setFieldFromNewInstance(T paramInstance) {
        if( paramInstance instanceof TreeBaseEntity){
            if(paramInstance.isCopied()) {
                this.setC_title("copy_" + this.getC_title());
            }
        }
    }
}
