--
-- Table structure for table `T_ARMS_FILEREPOSITORY`
--
CREATE TABLE IF NOT EXISTS `aRMS`.`T_ARMS_FILEREPOSITORY_LOG` (

    `C_ID` bigint(20) NOT NULL COMMENT '노드 아이디',
    `C_PARENTID` bigint(20) NOT NULL COMMENT '부모 노드 아이디',
    `C_POSITION` bigint(20) NOT NULL COMMENT '노드 포지션',
    `C_LEFT` bigint(20) NOT NULL COMMENT '노드 좌측 끝 포인트',
    `C_RIGHT` bigint(20) NOT NULL COMMENT '노드 우측 끝 포인트',
    `C_LEVEL` bigint(20) NOT NULL COMMENT '노드 DEPTH',
    `C_TITLE` VARCHAR(255) COMMENT '노드 명',
    `C_TYPE` VARCHAR(255) COMMENT '노드 타입',

    `C_METHOD`   text NULL COMMENT '노드 변경 행위',
    `C_STATE`    text NULL COMMENT '노드 상태값 ( 이전인지. 이후인지)',
    `C_DATE`     date NULL COMMENT '노드 변경 시',

    `C_FILE_ID_LINK` bigint(20) NULL,
    `C_FILE_NAME` text NULL,
    `C_CONTENT_TYPE` text NULL,
    `C_SERVER_SUB_PATH` text NULL,
    `C_PHYSICAL_NAME` text NULL,
    `C_SIZE` bigint(20) NULL,
    `C_NAME` text NULL,
    `C_URL` text NULL,
    `C_THUMBNAIL_URL` text NULL,
    `C_DELETE_URL` text NULL,
    `C_DELETE_TYPE` text NULL

) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin  COMMENT='기본 트리 스키마 트리거 로그';


CREATE TABLE IF NOT EXISTS `aRMS`.`T_ARMS_FILEREPOSITORY` (

    `C_ID` bigint(20) AUTO_INCREMENT primary key COMMENT '노드 아이디',
    `C_PARENTID` bigint(20) NOT NULL COMMENT '부모 노드 아이디',
    `C_POSITION` bigint(20) NOT NULL COMMENT '노드 포지션',
    `C_LEFT` bigint(20) NOT NULL COMMENT '노드 좌측 끝 포인트',
    `C_RIGHT` bigint(20) NOT NULL COMMENT '노드 우측 끝 포인트',
    `C_LEVEL` bigint(20) NOT NULL COMMENT '노드 DEPTH',
    `C_TITLE` VARCHAR(255) COMMENT '노드 명',
    `C_TYPE` VARCHAR(255) COMMENT '노드 타입',

    `C_FILE_ID_LINK` bigint(20) NULL,
    `C_FILE_NAME` text NULL,
    `C_CONTENT_TYPE` text NULL,
    `C_SERVER_SUB_PATH` text NULL,
    `C_PHYSICAL_NAME` text NULL,
    `C_SIZE` bigint(20) NULL,
    `C_NAME` text NULL,
    `C_URL` text NULL,
    `C_THUMBNAIL_URL` text NULL,
    `C_DELETE_URL` text NULL,
    `C_DELETE_TYPE` text NULL

) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='기본 트리 스키마 트리거 로그';


Insert into `aRMS`.`T_ARMS_FILEREPOSITORY` (C_ID, C_PARENTID, C_POSITION, C_LEFT, C_RIGHT, C_LEVEL, C_TITLE, C_TYPE)
Values (1, 0, 0, 1, 4, 0, 'T_ARMS_FILEREPOSITORY', 'root');
Insert into `aRMS`.`T_ARMS_FILEREPOSITORY` (C_ID, C_PARENTID, C_POSITION, C_LEFT, C_RIGHT, C_LEVEL, C_TITLE, C_TYPE)
Values (2, 1, 0, 2, 3, 1, '파일 레파지토리', 'drive');


DELIMITER $$
CREATE TRIGGER TG_INSERT_T_ARMS_FILEREPOSITORY
    BEFORE  INSERT ON T_ARMS_FILEREPOSITORY
    FOR EACH ROW
BEGIN
    insert into T_ARMS_FILEREPOSITORY_LOG (C_ID, C_PARENTID, C_POSITION, C_LEFT, C_RIGHT, C_LEVEL, C_TITLE, C_TYPE, C_METHOD, C_STATE, C_DATE)
    values (NEW.C_ID,NEW.C_PARENTID,NEW.C_POSITION,NEW.C_LEFT,NEW.C_RIGHT,NEW.C_LEVEL,NEW.C_TITLE,NEW.C_TYPE,'update','변경이전데이터',now());
END $$
DELIMITER ;

DELIMITER $$
CREATE TRIGGER TG_UPDATE_T_ARMS_FILEREPOSITORY
    BEFORE  UPDATE ON T_ARMS_FILEREPOSITORY
    FOR EACH ROW
BEGIN
    insert into T_ARMS_FILEREPOSITORY_LOG (C_ID, C_PARENTID, C_POSITION, C_LEFT, C_RIGHT, C_LEVEL, C_TITLE, C_TYPE, C_METHOD, C_STATE, C_DATE)
    values (OLD.C_ID,OLD.C_PARENTID,OLD.C_POSITION,OLD.C_LEFT,OLD.C_RIGHT,OLD.C_LEVEL,OLD.C_TITLE,OLD.C_TYPE,'update','변경이전데이터',now());
    insert into T_ARMS_FILEREPOSITORY_LOG (C_ID, C_PARENTID, C_POSITION, C_LEFT, C_RIGHT, C_LEVEL, C_TITLE, C_TYPE, C_METHOD, C_STATE, C_DATE)
    values (NEW.C_ID,NEW.C_PARENTID,NEW.C_POSITION,NEW.C_LEFT,NEW.C_RIGHT,NEW.C_LEVEL,NEW.C_TITLE,NEW.C_TYPE,'update','변경이후데이터',now());
END $$
DELIMITER ;

DELIMITER $$
CREATE TRIGGER TG_DELETE_T_ARMS_FILEREPOSITORY
    BEFORE  DELETE ON T_ARMS_FILEREPOSITORY
    FOR EACH ROW
BEGIN
    insert into T_ARMS_FILEREPOSITORY_LOG (C_ID, C_PARENTID, C_POSITION, C_LEFT, C_RIGHT, C_LEVEL, C_TITLE, C_TYPE, C_METHOD, C_STATE, C_DATE)
    values (OLD.C_ID,OLD.C_PARENTID,OLD.C_POSITION,OLD.C_LEFT,OLD.C_RIGHT,OLD.C_LEVEL,OLD.C_TITLE,OLD.C_TYPE,'delete','삭제된데이터',now());
END $$
DELIMITER ;

--
-- Table structure for table `T_ARMS_PDSERVICE`
--

CREATE TABLE IF NOT EXISTS `aRMS`.`T_ARMS_PDSERVICE_LOG` (

    `C_ID` bigint(20) NOT NULL COMMENT '노드 아이디',
    `C_PARENTID` bigint(20) NOT NULL COMMENT '부모 노드 아이디',
    `C_POSITION` bigint(20) NOT NULL COMMENT '노드 포지션',
    `C_LEFT` bigint(20) NOT NULL COMMENT '노드 좌측 끝 포인트',
    `C_RIGHT` bigint(20) NOT NULL COMMENT '노드 우측 끝 포인트',
    `C_LEVEL` bigint(20) NOT NULL COMMENT '노드 DEPTH',
    `C_TITLE` VARCHAR(255) COMMENT '노드 명',
    `C_TYPE` VARCHAR(255) COMMENT '노드 타입',

    `C_METHOD`   text NULL COMMENT '노드 변경 행위',
    `C_STATE`    text NULL COMMENT '노드 상태값 ( 이전인지. 이후인지)',
    `C_DATE`     date NULL COMMENT '노드 변경 시',

    `c_contents` longtext NULL,
    `c_etc` text NULL,

    `c_owner` text NULL,
    `c_reviewer01` text NULL,
    `c_reviewer02` text NULL,
    `c_reviewer03` text NULL,
    `c_reviewer04` text NULL,
    `c_reviewer05` text NULL,
    `c_writer_name` text NULL,
    `c_writer_cn` text NULL,
    `c_writer_mail` text NULL,
    `c_writer_date` text NULL,
    `c_fileid_link` text NULL

) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='기본 트리 스키마 트리거 로그';


CREATE TABLE IF NOT EXISTS `aRMS`.`T_ARMS_PDSERVICE` (

    `C_ID` bigint(20) AUTO_INCREMENT primary key COMMENT '노드 아이디',
    `C_PARENTID` bigint(20) NOT NULL COMMENT '부모 노드 아이디',
    `C_POSITION` bigint(20) NOT NULL COMMENT '노드 포지션',
    `C_LEFT` bigint(20) NOT NULL COMMENT '노드 좌측 끝 포인트',
    `C_RIGHT` bigint(20) NOT NULL COMMENT '노드 우측 끝 포인트',
    `C_LEVEL` bigint(20) NOT NULL COMMENT '노드 DEPTH',
    `C_TITLE` VARCHAR(255) COMMENT '노드 명',
    `C_TYPE` VARCHAR(255) COMMENT '노드 타입',

    `c_contents` longtext NULL,
    `c_etc` text NULL,

    `c_owner` text NULL,
    `c_reviewer01` text NULL,
    `c_reviewer02` text NULL,
    `c_reviewer03` text NULL,
    `c_reviewer04` text NULL,
    `c_reviewer05` text NULL,
    `c_writer_name` text NULL,
    `c_writer_cn` text NULL,
    `c_writer_mail` text NULL,
    `c_writer_date` text NULL,
    `c_fileid_link` text NULL

) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='기본 트리 스키마 트리거 로그';


Insert into `aRMS`.`T_ARMS_PDSERVICE` (C_ID, C_PARENTID, C_POSITION, C_LEFT, C_RIGHT, C_LEVEL, C_TITLE, C_TYPE)
Values (1, 0, 0, 1, 4, 0, 'T_ARMS_PDSERVICE', 'root');
Insert into `aRMS`.`T_ARMS_PDSERVICE` (C_ID, C_PARENTID, C_POSITION, C_LEFT, C_RIGHT, C_LEVEL, C_TITLE, C_TYPE)
Values (2, 1, 0, 2, 3, 1, '제품(서비스)', 'drive');


DELIMITER $$
CREATE TRIGGER TG_INSERT_T_ARMS_PDSERVICE
    BEFORE  INSERT ON T_ARMS_PDSERVICE
    FOR EACH ROW
BEGIN
    insert into T_ARMS_PDSERVICE_LOG (C_ID, C_PARENTID, C_POSITION, C_LEFT, C_RIGHT, C_LEVEL, C_TITLE, C_TYPE, C_METHOD, C_STATE, C_DATE)
    values (NEW.C_ID,NEW.C_PARENTID,NEW.C_POSITION,NEW.C_LEFT,NEW.C_RIGHT,NEW.C_LEVEL,NEW.C_TITLE,NEW.C_TYPE,'update','변경이전데이터',now());
END $$
DELIMITER ;

DELIMITER $$
CREATE TRIGGER TG_UPDATE_T_ARMS_PDSERVICE
    BEFORE  UPDATE ON T_ARMS_PDSERVICE
    FOR EACH ROW
BEGIN
    insert into T_ARMS_PDSERVICE_LOG (C_ID, C_PARENTID, C_POSITION, C_LEFT, C_RIGHT, C_LEVEL, C_TITLE, C_TYPE, C_METHOD, C_STATE, C_DATE)
    values (OLD.C_ID,OLD.C_PARENTID,OLD.C_POSITION,OLD.C_LEFT,OLD.C_RIGHT,OLD.C_LEVEL,OLD.C_TITLE,OLD.C_TYPE,'update','변경이전데이터',now());
    insert into T_ARMS_PDSERVICE_LOG (C_ID, C_PARENTID, C_POSITION, C_LEFT, C_RIGHT, C_LEVEL, C_TITLE, C_TYPE, C_METHOD, C_STATE, C_DATE)
    values (NEW.C_ID,NEW.C_PARENTID,NEW.C_POSITION,NEW.C_LEFT,NEW.C_RIGHT,NEW.C_LEVEL,NEW.C_TITLE,NEW.C_TYPE,'update','변경이후데이터',now());
END $$
DELIMITER ;

DELIMITER $$
CREATE TRIGGER TG_DELETE_T_ARMS_PDSERVICE
    BEFORE  DELETE ON T_ARMS_PDSERVICE
    FOR EACH ROW
BEGIN
    insert into T_ARMS_PDSERVICE_LOG (C_ID, C_PARENTID, C_POSITION, C_LEFT, C_RIGHT, C_LEVEL, C_TITLE, C_TYPE, C_METHOD, C_STATE, C_DATE)
    values (OLD.C_ID,OLD.C_PARENTID,OLD.C_POSITION,OLD.C_LEFT,OLD.C_RIGHT,OLD.C_LEVEL,OLD.C_TITLE,OLD.C_TYPE,'delete','삭제된데이터',now());
END $$
DELIMITER ;

--
-- Table structure for table `T_ARMS_PDSERVICEVERSION`
--
CREATE TABLE IF NOT EXISTS `aRMS`.`T_ARMS_PDSERVICEVERSION_LOG` (

    `C_ID` bigint(20) NOT NULL COMMENT '노드 아이디',
    `C_PARENTID` bigint(20) NOT NULL COMMENT '부모 노드 아이디',
    `C_POSITION` bigint(20) NOT NULL COMMENT '노드 포지션',
    `C_LEFT` bigint(20) NOT NULL COMMENT '노드 좌측 끝 포인트',
    `C_RIGHT` bigint(20) NOT NULL COMMENT '노드 우측 끝 포인트',
    `C_LEVEL` bigint(20) NOT NULL COMMENT '노드 DEPTH',
    `C_TITLE` VARCHAR(255) COMMENT '노드 명',
    `C_TYPE` VARCHAR(255) COMMENT '노드 타입',

    `C_METHOD`   text NULL COMMENT '노드 변경 행위',
    `C_STATE`    text NULL COMMENT '노드 상태값 ( 이전인지. 이후인지)',
    `C_DATE`     date NULL COMMENT '노드 변경 시',

    `c_pdservice_link` bigint(20) NULL,

    `c_contents` longtext NULL,
    `c_etc` text NULL,

    `c_start_date` text NULL,
    `c_end_date` text NULL


) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='기본 트리 스키마 트리거 로그';


CREATE TABLE IF NOT EXISTS `aRMS`.`T_ARMS_PDSERVICEVERSION` (

    `C_ID` bigint(20) AUTO_INCREMENT primary key COMMENT '노드 아이디',
    `C_PARENTID` bigint(20) NOT NULL COMMENT '부모 노드 아이디',
    `C_POSITION` bigint(20) NOT NULL COMMENT '노드 포지션',
    `C_LEFT` bigint(20) NOT NULL COMMENT '노드 좌측 끝 포인트',
    `C_RIGHT` bigint(20) NOT NULL COMMENT '노드 우측 끝 포인트',
    `C_LEVEL` bigint(20) NOT NULL COMMENT '노드 DEPTH',
    `C_TITLE` VARCHAR(255) COMMENT '노드 명',
    `C_TYPE` VARCHAR(255) COMMENT '노드 타입',

    `c_pdservice_link` bigint(20) NULL,

    `c_contents` longtext NULL,
    `c_etc` text NULL,

    `c_start_date` text NULL,
    `c_end_date` text NULL

) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='기본 트리 스키마 트리거 로그';


Insert into `aRMS`.`T_ARMS_PDSERVICEVERSION` (C_ID, C_PARENTID, C_POSITION, C_LEFT, C_RIGHT, C_LEVEL, C_TITLE, C_TYPE)
Values (1, 0, 0, 1, 4, 0, 'T_ARMS_PDSERVICEVERSION', 'root');
Insert into `aRMS`.`T_ARMS_PDSERVICEVERSION` (C_ID, C_PARENTID, C_POSITION, C_LEFT, C_RIGHT, C_LEVEL, C_TITLE, C_TYPE)
Values (2, 1, 0, 2, 3, 1, '제품(서비스) 버전', 'drive');


DELIMITER $$
CREATE TRIGGER TG_INSERT_T_ARMS_PDSERVICEVERSION
    BEFORE  INSERT ON T_ARMS_PDSERVICEVERSION
    FOR EACH ROW
BEGIN
    insert into T_ARMS_PDSERVICEVERSION_LOG (C_ID, C_PARENTID, C_POSITION, C_LEFT, C_RIGHT, C_LEVEL, C_TITLE, C_TYPE, C_METHOD, C_STATE, C_DATE)
    values (NEW.C_ID,NEW.C_PARENTID,NEW.C_POSITION,NEW.C_LEFT,NEW.C_RIGHT,NEW.C_LEVEL,NEW.C_TITLE,NEW.C_TYPE,'update','변경이전데이터',now());
END $$
DELIMITER ;

DELIMITER $$
CREATE TRIGGER TG_UPDATE_T_ARMS_PDSERVICEVERSION
    BEFORE  UPDATE ON T_ARMS_PDSERVICEVERSION
    FOR EACH ROW
BEGIN
    insert into T_ARMS_PDSERVICEVERSION_LOG (C_ID, C_PARENTID, C_POSITION, C_LEFT, C_RIGHT, C_LEVEL, C_TITLE, C_TYPE, C_METHOD, C_STATE, C_DATE)
    values (OLD.C_ID,OLD.C_PARENTID,OLD.C_POSITION,OLD.C_LEFT,OLD.C_RIGHT,OLD.C_LEVEL,OLD.C_TITLE,OLD.C_TYPE,'update','변경이전데이터',now());
    insert into T_ARMS_PDSERVICEVERSION_LOG (C_ID, C_PARENTID, C_POSITION, C_LEFT, C_RIGHT, C_LEVEL, C_TITLE, C_TYPE, C_METHOD, C_STATE, C_DATE)
    values (NEW.C_ID,NEW.C_PARENTID,NEW.C_POSITION,NEW.C_LEFT,NEW.C_RIGHT,NEW.C_LEVEL,NEW.C_TITLE,NEW.C_TYPE,'update','변경이후데이터',now());
END $$
DELIMITER ;

DELIMITER $$
CREATE TRIGGER TG_DELETE_T_ARMS_PDSERVICEVERSION
    BEFORE  DELETE ON T_ARMS_PDSERVICEVERSION
    FOR EACH ROW
BEGIN
    insert into T_ARMS_PDSERVICEVERSION_LOG (C_ID, C_PARENTID, C_POSITION, C_LEFT, C_RIGHT, C_LEVEL, C_TITLE, C_TYPE, C_METHOD, C_STATE, C_DATE)
    values (OLD.C_ID,OLD.C_PARENTID,OLD.C_POSITION,OLD.C_LEFT,OLD.C_RIGHT,OLD.C_LEVEL,OLD.C_TITLE,OLD.C_TYPE,'delete','삭제된데이터',now());
END $$
DELIMITER ;


--
-- Table structure for table `T_ARMS_JIRAPROJECT`
--
CREATE TABLE IF NOT EXISTS `aRMS`.`T_ARMS_JIRAPROJECT_LOG` (

    `C_ID`                      bigint(20) NOT NULL COMMENT '노드 아이디',
    `C_PARENTID`                bigint(20) NOT NULL COMMENT '부모 노드 아이디',
    `C_POSITION`                bigint(20) NOT NULL COMMENT '노드 포지션',
    `C_LEFT`                    bigint(20) NOT NULL COMMENT '노드 좌측 끝 포인트',
    `C_RIGHT`                   bigint(20) NOT NULL COMMENT '노드 우측 끝 포인트',
    `C_LEVEL`                   bigint(20) NOT NULL COMMENT '노드 DEPTH',
    `C_TITLE`                   VARCHAR(255) COMMENT '노드 명',
    `C_TYPE`                    VARCHAR(255) COMMENT '노드 타입',

    `C_METHOD`                  text NULL COMMENT '노드 변경 행위',
    `C_STATE`                   text NULL COMMENT '노드 상태값 ( 이전인지. 이후인지)',
    `C_DATE`                    date NULL COMMENT '노드 변경 시',

    `c_contents`                longtext NULL,
    `c_etc`                     text NULL,

    `c_jira_url`               text NULL,
    `c_jira_id`                 text NULL,
    `c_jira_key`                text NULL,
    `c_jira_name`               text NULL,

    `c_jira_avatar_48`          text NULL,
    `c_jira_avatar_32`          text NULL,
    `c_jira_avatar_24`          text NULL,
    `c_jira_avatar_16`          text NULL,

    `c_jira_category_url`      text NULL,
    `c_jira_category_id`        text NULL,
    `c_jira_category_name`      text NULL,
    `c_jira_category_desc`      text NULL

) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='기본 트리 스키마 트리거 로그';


CREATE TABLE IF NOT EXISTS `aRMS`.`T_ARMS_JIRAPROJECT` (

    `C_ID`                      bigint(20) AUTO_INCREMENT primary key COMMENT '노드 아이디',
    `C_PARENTID`                bigint(20) NOT NULL COMMENT '부모 노드 아이디',
    `C_POSITION`                bigint(20) NOT NULL COMMENT '노드 포지션',
    `C_LEFT`                    bigint(20) NOT NULL COMMENT '노드 좌측 끝 포인트',
    `C_RIGHT`                   bigint(20) NOT NULL COMMENT '노드 우측 끝 포인트',
    `C_LEVEL`                   bigint(20) NOT NULL COMMENT '노드 DEPTH',
    `C_TITLE`                   VARCHAR(255) COMMENT '노드 명',
    `C_TYPE`                    VARCHAR(255) COMMENT '노드 타입',

    `c_contents`                longtext NULL,
    `c_etc`                     text NULL,

    `c_jira_url`               text NULL,
    `c_jira_id`                 text NULL,
    `c_jira_key`                text NULL,
    `c_jira_name`               text NULL,

    `c_jira_avatar_48`          text NULL,
    `c_jira_avatar_32`          text NULL,
    `c_jira_avatar_24`          text NULL,
    `c_jira_avatar_16`          text NULL,

    `c_jira_category_url`      text NULL,
    `c_jira_category_id`        text NULL,
    `c_jira_category_name`      text NULL,
    `c_jira_category_desc`      text NULL

) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='기본 트리 스키마 트리거 로그';


Insert into `aRMS`.`T_ARMS_JIRAPROJECT` (C_ID, C_PARENTID, C_POSITION, C_LEFT, C_RIGHT, C_LEVEL, C_TITLE, C_TYPE)
Values (1, 0, 0, 1, 4, 0, 'T_ARMS_JIRAPROJECT', 'root');
Insert into `aRMS`.`T_ARMS_JIRAPROJECT` (C_ID, C_PARENTID, C_POSITION, C_LEFT, C_RIGHT, C_LEVEL, C_TITLE, C_TYPE)
Values (2, 1, 0, 2, 3, 1, '요구사항 - 이슈 결과 장표', 'drive');


DELIMITER $$
CREATE TRIGGER TG_INSERT_T_ARMS_JIRAPROJECT
    BEFORE  INSERT ON T_ARMS_JIRAPROJECT
    FOR EACH ROW
BEGIN
    insert into T_ARMS_JIRAPROJECT_LOG (C_ID, C_PARENTID, C_POSITION, C_LEFT, C_RIGHT, C_LEVEL, C_TITLE, C_TYPE, C_METHOD, C_STATE, C_DATE)
    values (NEW.C_ID,NEW.C_PARENTID,NEW.C_POSITION,NEW.C_LEFT,NEW.C_RIGHT,NEW.C_LEVEL,NEW.C_TITLE,NEW.C_TYPE,'update','변경이전데이터',now());
END $$
DELIMITER ;

DELIMITER $$
CREATE TRIGGER TG_UPDATE_T_ARMS_JIRAPROJECT
    BEFORE  UPDATE ON T_ARMS_JIRAPROJECT
    FOR EACH ROW
BEGIN
    insert into T_ARMS_JIRAPROJECT_LOG (C_ID, C_PARENTID, C_POSITION, C_LEFT, C_RIGHT, C_LEVEL, C_TITLE, C_TYPE, C_METHOD, C_STATE, C_DATE)
    values (OLD.C_ID,OLD.C_PARENTID,OLD.C_POSITION,OLD.C_LEFT,OLD.C_RIGHT,OLD.C_LEVEL,OLD.C_TITLE,OLD.C_TYPE,'update','변경이전데이터',now());
    insert into T_ARMS_JIRAPROJECT_LOG (C_ID, C_PARENTID, C_POSITION, C_LEFT, C_RIGHT, C_LEVEL, C_TITLE, C_TYPE, C_METHOD, C_STATE, C_DATE)
    values (NEW.C_ID,NEW.C_PARENTID,NEW.C_POSITION,NEW.C_LEFT,NEW.C_RIGHT,NEW.C_LEVEL,NEW.C_TITLE,NEW.C_TYPE,'update','변경이후데이터',now());
END $$
DELIMITER ;

DELIMITER $$
CREATE TRIGGER TG_DELETE_T_ARMS_JIRAPROJECT
    BEFORE  DELETE ON T_ARMS_JIRAPROJECT
    FOR EACH ROW
BEGIN
    insert into T_ARMS_JIRAPROJECT_LOG (C_ID, C_PARENTID, C_POSITION, C_LEFT, C_RIGHT, C_LEVEL, C_TITLE, C_TYPE, C_METHOD, C_STATE, C_DATE)
    values (OLD.C_ID,OLD.C_PARENTID,OLD.C_POSITION,OLD.C_LEFT,OLD.C_RIGHT,OLD.C_LEVEL,OLD.C_TITLE,OLD.C_TYPE,'delete','삭제된데이터',now());
END $$
DELIMITER ;



--
-- Table structure for table `T_ARMS_JIRAPROJECTVERSION`
--
CREATE TABLE IF NOT EXISTS `aRMS`.`T_ARMS_JIRAPROJECTVERSION_LOG` (

    `C_ID`                      bigint(20) NOT NULL COMMENT '노드 아이디',
    `C_PARENTID`                bigint(20) NOT NULL COMMENT '부모 노드 아이디',
    `C_POSITION`                bigint(20) NOT NULL COMMENT '노드 포지션',
    `C_LEFT`                    bigint(20) NOT NULL COMMENT '노드 좌측 끝 포인트',
    `C_RIGHT`                   bigint(20) NOT NULL COMMENT '노드 우측 끝 포인트',
    `C_LEVEL`                   bigint(20) NOT NULL COMMENT '노드 DEPTH',
    `C_TITLE`                   VARCHAR(255) COMMENT '노드 명',
    `C_TYPE`                    VARCHAR(255) COMMENT '노드 타입',

    `C_METHOD`                  text NULL COMMENT '노드 변경 행위',
    `C_STATE`                   text NULL COMMENT '노드 상태값 ( 이전인지. 이후인지)',
    `C_DATE`                    date NULL COMMENT '노드 변경 시',

    `c_jira_link`                  bigint(20) NULL,

    `c_jira_version_url`           text NULL,
    `c_jira_version_id`            text NULL,
    `c_jira_version_desc`          text NULL,
    `c_jira_version_name`          text NULL,
    `c_jira_version_projectid`     text NULL,
    `c_jira_version_archived`      text NULL,
    `c_jira_version_released`      text NULL,
    `c_jira_version_releaseDate`   text NULL

    ) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='기본 트리 스키마 트리거 로그';


CREATE TABLE IF NOT EXISTS `aRMS`.`T_ARMS_JIRAPROJECTVERSION` (

    `C_ID`                      bigint(20) AUTO_INCREMENT primary key COMMENT '노드 아이디',
    `C_PARENTID`                bigint(20) NOT NULL COMMENT '부모 노드 아이디',
    `C_POSITION`                bigint(20) NOT NULL COMMENT '노드 포지션',
    `C_LEFT`                    bigint(20) NOT NULL COMMENT '노드 좌측 끝 포인트',
    `C_RIGHT`                   bigint(20) NOT NULL COMMENT '노드 우측 끝 포인트',
    `C_LEVEL`                   bigint(20) NOT NULL COMMENT '노드 DEPTH',
    `C_TITLE`                   VARCHAR(255) COMMENT '노드 명',
    `C_TYPE`                    VARCHAR(255) COMMENT '노드 타입',

    `c_jira_link`                  bigint(20) NULL,

    `c_jira_version_url`           text NULL,
    `c_jira_version_id`            text NULL,
    `c_jira_version_desc`          text NULL,
    `c_jira_version_name`          text NULL,
    `c_jira_version_projectid`     text NULL,
    `c_jira_version_archived`      text NULL,
    `c_jira_version_released`      text NULL,
    `c_jira_version_releaseDate`   text NULL

) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='기본 트리 스키마 트리거 로그';


Insert into `aRMS`.`T_ARMS_JIRAPROJECTVERSION` (C_ID, C_PARENTID, C_POSITION, C_LEFT, C_RIGHT, C_LEVEL, C_TITLE, C_TYPE)
Values (1, 0, 0, 1, 4, 0, 'T_ARMS_JIRAPROJECTVERSION', 'root');
Insert into `aRMS`.`T_ARMS_JIRAPROJECTVERSION` (C_ID, C_PARENTID, C_POSITION, C_LEFT, C_RIGHT, C_LEVEL, C_TITLE, C_TYPE)
Values (2, 1, 0, 2, 3, 1, '요구사항 - 이슈 결과 장표', 'drive');


DELIMITER $$
CREATE TRIGGER TG_INSERT_T_ARMS_JIRAPROJECTVERSION
    BEFORE  INSERT ON T_ARMS_JIRAPROJECTVERSION
    FOR EACH ROW
BEGIN
    insert into T_ARMS_JIRAPROJECTVERSION_LOG (C_ID, C_PARENTID, C_POSITION, C_LEFT, C_RIGHT, C_LEVEL, C_TITLE, C_TYPE, C_METHOD, C_STATE, C_DATE)
    values (NEW.C_ID,NEW.C_PARENTID,NEW.C_POSITION,NEW.C_LEFT,NEW.C_RIGHT,NEW.C_LEVEL,NEW.C_TITLE,NEW.C_TYPE,'update','변경이전데이터',now());
END $$
DELIMITER ;

DELIMITER $$
CREATE TRIGGER TG_UPDATE_T_ARMS_JIRAPROJECTVERSION
    BEFORE  UPDATE ON T_ARMS_JIRAPROJECTVERSION
    FOR EACH ROW
BEGIN
    insert into T_ARMS_JIRAPROJECTVERSION_LOG (C_ID, C_PARENTID, C_POSITION, C_LEFT, C_RIGHT, C_LEVEL, C_TITLE, C_TYPE, C_METHOD, C_STATE, C_DATE)
    values (OLD.C_ID,OLD.C_PARENTID,OLD.C_POSITION,OLD.C_LEFT,OLD.C_RIGHT,OLD.C_LEVEL,OLD.C_TITLE,OLD.C_TYPE,'update','변경이전데이터',now());
    insert into T_ARMS_JIRAPROJECTVERSION_LOG (C_ID, C_PARENTID, C_POSITION, C_LEFT, C_RIGHT, C_LEVEL, C_TITLE, C_TYPE, C_METHOD, C_STATE, C_DATE)
    values (NEW.C_ID,NEW.C_PARENTID,NEW.C_POSITION,NEW.C_LEFT,NEW.C_RIGHT,NEW.C_LEVEL,NEW.C_TITLE,NEW.C_TYPE,'update','변경이후데이터',now());
END $$
DELIMITER ;

DELIMITER $$
CREATE TRIGGER TG_DELETE_T_ARMS_JIRAPROJECTVERSION
    BEFORE  DELETE ON T_ARMS_JIRAPROJECTVERSION
    FOR EACH ROW
BEGIN
    insert into T_ARMS_JIRAPROJECTVERSION_LOG (C_ID, C_PARENTID, C_POSITION, C_LEFT, C_RIGHT, C_LEVEL, C_TITLE, C_TYPE, C_METHOD, C_STATE, C_DATE)
    values (OLD.C_ID,OLD.C_PARENTID,OLD.C_POSITION,OLD.C_LEFT,OLD.C_RIGHT,OLD.C_LEVEL,OLD.C_TITLE,OLD.C_TYPE,'delete','삭제된데이터',now());
END $$
DELIMITER ;



--
-- Table structure for table `T_ARMS_JIRAISSUE`
--
CREATE TABLE IF NOT EXISTS `aRMS`.`T_ARMS_JIRAISSUE_LOG` (

    `C_ID` bigint(20) NOT NULL COMMENT '노드 아이디',
    `C_PARENTID` bigint(20) NOT NULL COMMENT '부모 노드 아이디',
    `C_POSITION` bigint(20) NOT NULL COMMENT '노드 포지션',
    `C_LEFT` bigint(20) NOT NULL COMMENT '노드 좌측 끝 포인트',
    `C_RIGHT` bigint(20) NOT NULL COMMENT '노드 우측 끝 포인트',
    `C_LEVEL` bigint(20) NOT NULL COMMENT '노드 DEPTH',
    `C_TITLE` VARCHAR(255) COMMENT '노드 명',
    `C_TYPE` VARCHAR(255) COMMENT '노드 타입',

    `C_METHOD`   text NULL COMMENT '노드 변경 행위',
    `C_STATE`    text NULL COMMENT '노드 상태값 ( 이전인지. 이후인지)',
    `C_DATE`     date NULL COMMENT '노드 변경 시',

    `c_issue_id`     text NULL,
    `c_issue_url`   text NULL,
    `c_issue_key`   text NULL,
    `c_issue_priority_url`    text NULL


    ) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='기본 트리 스키마 트리거 로그';


CREATE TABLE IF NOT EXISTS `aRMS`.`T_ARMS_JIRAISSUE` (

    `C_ID` bigint(20) AUTO_INCREMENT primary key COMMENT '노드 아이디',
    `C_PARENTID` bigint(20) NOT NULL COMMENT '부모 노드 아이디',
    `C_POSITION` bigint(20) NOT NULL COMMENT '노드 포지션',
    `C_LEFT` bigint(20) NOT NULL COMMENT '노드 좌측 끝 포인트',
    `C_RIGHT` bigint(20) NOT NULL COMMENT '노드 우측 끝 포인트',
    `C_LEVEL` bigint(20) NOT NULL COMMENT '노드 DEPTH',
    `C_TITLE` VARCHAR(255) COMMENT '노드 명',
    `C_TYPE` VARCHAR(255) COMMENT '노드 타입',

    `c_issue_priority_id`     text NULL,
    `c_issue_priority_desc`   text NULL,
    `c_issue_priority_name`   text NULL,
    `c_issue_priority_url`    text NULL

    ) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='기본 트리 스키마 트리거 로그';


Insert into `aRMS`.`T_ARMS_JIRAISSUE` (C_ID, C_PARENTID, C_POSITION, C_LEFT, C_RIGHT, C_LEVEL, C_TITLE, C_TYPE)
Values (1, 0, 0, 1, 4, 0, 'T_ARMS_JIRAISSUE', 'root');
Insert into `aRMS`.`T_ARMS_JIRAISSUE` (C_ID, C_PARENTID, C_POSITION, C_LEFT, C_RIGHT, C_LEVEL, C_TITLE, C_TYPE)
Values (2, 1, 0, 2, 3, 1, '제품(서비스) 버전', 'drive');


DELIMITER $$
CREATE TRIGGER TG_INSERT_T_ARMS_JIRAISSUE
    BEFORE  INSERT ON T_ARMS_JIRAISSUE
    FOR EACH ROW
BEGIN
    insert into T_ARMS_JIRAISSUE_LOG (C_ID, C_PARENTID, C_POSITION, C_LEFT, C_RIGHT, C_LEVEL, C_TITLE, C_TYPE, C_METHOD, C_STATE, C_DATE)
    values (NEW.C_ID,NEW.C_PARENTID,NEW.C_POSITION,NEW.C_LEFT,NEW.C_RIGHT,NEW.C_LEVEL,NEW.C_TITLE,NEW.C_TYPE,'update','변경이전데이터',now());
END $$
DELIMITER ;

DELIMITER $$
CREATE TRIGGER TG_UPDATE_T_ARMS_JIRAISSUE
    BEFORE  UPDATE ON T_ARMS_JIRAISSUE
    FOR EACH ROW
BEGIN
    insert into T_ARMS_JIRAISSUE_LOG (C_ID, C_PARENTID, C_POSITION, C_LEFT, C_RIGHT, C_LEVEL, C_TITLE, C_TYPE, C_METHOD, C_STATE, C_DATE)
    values (OLD.C_ID,OLD.C_PARENTID,OLD.C_POSITION,OLD.C_LEFT,OLD.C_RIGHT,OLD.C_LEVEL,OLD.C_TITLE,OLD.C_TYPE,'update','변경이전데이터',now());
    insert into T_ARMS_JIRAISSUE_LOG (C_ID, C_PARENTID, C_POSITION, C_LEFT, C_RIGHT, C_LEVEL, C_TITLE, C_TYPE, C_METHOD, C_STATE, C_DATE)
    values (NEW.C_ID,NEW.C_PARENTID,NEW.C_POSITION,NEW.C_LEFT,NEW.C_RIGHT,NEW.C_LEVEL,NEW.C_TITLE,NEW.C_TYPE,'update','변경이후데이터',now());
END $$
DELIMITER ;

DELIMITER $$
CREATE TRIGGER TG_DELETE_T_ARMS_JIRAISSUE
    BEFORE  DELETE ON T_ARMS_JIRAISSUE
    FOR EACH ROW
BEGIN
    insert into T_ARMS_JIRAISSUE_LOG (C_ID, C_PARENTID, C_POSITION, C_LEFT, C_RIGHT, C_LEVEL, C_TITLE, C_TYPE, C_METHOD, C_STATE, C_DATE)
    values (OLD.C_ID,OLD.C_PARENTID,OLD.C_POSITION,OLD.C_LEFT,OLD.C_RIGHT,OLD.C_LEVEL,OLD.C_TITLE,OLD.C_TYPE,'delete','삭제된데이터',now());
END $$
DELIMITER ;



--
-- Table structure for table `T_ARMS_JIRAISSUEPRIORITY`
--
CREATE TABLE IF NOT EXISTS `aRMS`.`T_ARMS_JIRAISSUEPRIORITY_LOG` (

    `C_ID` bigint(20) NOT NULL COMMENT '노드 아이디',
    `C_PARENTID` bigint(20) NOT NULL COMMENT '부모 노드 아이디',
    `C_POSITION` bigint(20) NOT NULL COMMENT '노드 포지션',
    `C_LEFT` bigint(20) NOT NULL COMMENT '노드 좌측 끝 포인트',
    `C_RIGHT` bigint(20) NOT NULL COMMENT '노드 우측 끝 포인트',
    `C_LEVEL` bigint(20) NOT NULL COMMENT '노드 DEPTH',
    `C_TITLE` VARCHAR(255) COMMENT '노드 명',
    `C_TYPE` VARCHAR(255) COMMENT '노드 타입',

    `C_METHOD`   text NULL COMMENT '노드 변경 행위',
    `C_STATE`    text NULL COMMENT '노드 상태값 ( 이전인지. 이후인지)',
    `C_DATE`     date NULL COMMENT '노드 변경 시',

    `c_issue_priority_id`     text NULL,
    `c_issue_priority_desc`   text NULL,
    `c_issue_priority_name`   text NULL,
    `c_issue_priority_url`    text NULL

    ) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='기본 트리 스키마 트리거 로그';


CREATE TABLE IF NOT EXISTS `aRMS`.`T_ARMS_JIRAISSUEPRIORITY` (

    `C_ID` bigint(20) AUTO_INCREMENT primary key COMMENT '노드 아이디',
    `C_PARENTID` bigint(20) NOT NULL COMMENT '부모 노드 아이디',
    `C_POSITION` bigint(20) NOT NULL COMMENT '노드 포지션',
    `C_LEFT` bigint(20) NOT NULL COMMENT '노드 좌측 끝 포인트',
    `C_RIGHT` bigint(20) NOT NULL COMMENT '노드 우측 끝 포인트',
    `C_LEVEL` bigint(20) NOT NULL COMMENT '노드 DEPTH',
    `C_TITLE` VARCHAR(255) COMMENT '노드 명',
    `C_TYPE` VARCHAR(255) COMMENT '노드 타입',

    `c_issue_priority_id`     text NULL,
    `c_issue_priority_desc`   text NULL,
    `c_issue_priority_name`   text NULL,
    `c_issue_priority_url`    text NULL

    ) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='기본 트리 스키마 트리거 로그';


Insert into `aRMS`.`T_ARMS_JIRAISSUEPRIORITY` (C_ID, C_PARENTID, C_POSITION, C_LEFT, C_RIGHT, C_LEVEL, C_TITLE, C_TYPE)
Values (1, 0, 0, 1, 4, 0, 'T_ARMS_JIRAISSUEPRIORITY', 'root');
Insert into `aRMS`.`T_ARMS_JIRAISSUEPRIORITY` (C_ID, C_PARENTID, C_POSITION, C_LEFT, C_RIGHT, C_LEVEL, C_TITLE, C_TYPE)
Values (2, 1, 0, 2, 3, 1, '제품(서비스) 버전', 'drive');


DELIMITER $$
CREATE TRIGGER TG_INSERT_T_ARMS_JIRAISSUEPRIORITY
    BEFORE  INSERT ON T_ARMS_JIRAISSUEPRIORITY
    FOR EACH ROW
BEGIN
    insert into T_ARMS_JIRAISSUEPRIORITY_LOG (C_ID, C_PARENTID, C_POSITION, C_LEFT, C_RIGHT, C_LEVEL, C_TITLE, C_TYPE, C_METHOD, C_STATE, C_DATE)
    values (NEW.C_ID,NEW.C_PARENTID,NEW.C_POSITION,NEW.C_LEFT,NEW.C_RIGHT,NEW.C_LEVEL,NEW.C_TITLE,NEW.C_TYPE,'update','변경이전데이터',now());
END $$
DELIMITER ;

DELIMITER $$
CREATE TRIGGER TG_UPDATE_T_ARMS_JIRAISSUEPRIORITY
    BEFORE  UPDATE ON T_ARMS_JIRAISSUEPRIORITY
    FOR EACH ROW
BEGIN
    insert into T_ARMS_JIRAISSUEPRIORITY_LOG (C_ID, C_PARENTID, C_POSITION, C_LEFT, C_RIGHT, C_LEVEL, C_TITLE, C_TYPE, C_METHOD, C_STATE, C_DATE)
    values (OLD.C_ID,OLD.C_PARENTID,OLD.C_POSITION,OLD.C_LEFT,OLD.C_RIGHT,OLD.C_LEVEL,OLD.C_TITLE,OLD.C_TYPE,'update','변경이전데이터',now());
    insert into T_ARMS_JIRAISSUEPRIORITY_LOG (C_ID, C_PARENTID, C_POSITION, C_LEFT, C_RIGHT, C_LEVEL, C_TITLE, C_TYPE, C_METHOD, C_STATE, C_DATE)
    values (NEW.C_ID,NEW.C_PARENTID,NEW.C_POSITION,NEW.C_LEFT,NEW.C_RIGHT,NEW.C_LEVEL,NEW.C_TITLE,NEW.C_TYPE,'update','변경이후데이터',now());
END $$
DELIMITER ;

DELIMITER $$
CREATE TRIGGER TG_DELETE_T_ARMS_JIRAISSUEPRIORITY
    BEFORE  DELETE ON T_ARMS_JIRAISSUEPRIORITY
    FOR EACH ROW
BEGIN
    insert into T_ARMS_JIRAISSUEPRIORITY_LOG (C_ID, C_PARENTID, C_POSITION, C_LEFT, C_RIGHT, C_LEVEL, C_TITLE, C_TYPE, C_METHOD, C_STATE, C_DATE)
    values (OLD.C_ID,OLD.C_PARENTID,OLD.C_POSITION,OLD.C_LEFT,OLD.C_RIGHT,OLD.C_LEVEL,OLD.C_TITLE,OLD.C_TYPE,'delete','삭제된데이터',now());
END $$
DELIMITER ;



--
-- Table structure for table `T_ARMS_REQADD`
--
CREATE TABLE IF NOT EXISTS `aRMS`.`T_ARMS_REQADD_LOG` (

    `C_ID`                  bigint(20) NOT NULL COMMENT '노드 아이디',
    `C_PARENTID`            bigint(20) NOT NULL COMMENT '부모 노드 아이디',
    `C_POSITION`            bigint(20) NOT NULL COMMENT '노드 포지션',
    `C_LEFT`                bigint(20) NOT NULL COMMENT '노드 좌측 끝 포인트',
    `C_RIGHT`               bigint(20) NOT NULL COMMENT '노드 우측 끝 포인트',
    `C_LEVEL`               bigint(20) NOT NULL COMMENT '노드 DEPTH',
    `C_TITLE`               VARCHAR(255) COMMENT '노드 명',
    `C_TYPE`                VARCHAR(255) COMMENT '노드 타입',

    `C_METHOD`              text NULL COMMENT '노드 변경 행위',
    `C_STATE`               text NULL COMMENT '노드 상태값 ( 이전인지. 이후인지)',
    `C_DATE`                date NULL COMMENT '노드 변경 시',

    `C_PDSERVICE_LINK`      bigint(20) NULL,
    `C_VERSION_LINK`        bigint(20) NULL,
    `C_JIRA_LINK`           bigint(20) NULL,
    `C_JIRA_VER_LINK`       bigint(20) NULL,
    `C_ISSUE_LINK`          bigint(20) NULL,

    `C_REVIEWER01`          text NULL,
    `C_REVIEWER01_STATUS`   text NULL,
    `C_REVIEWER02`          text NULL,
    `C_REVIEWER02_STATUS`   text NULL,
    `C_REVIEWER03`          text NULL,
    `C_REVIEWER03_STATUS`   text NULL,
    `C_REVIEWER04`          text NULL,
    `C_REVIEWER04_STATUS`   text NULL,
    `C_REVIEWER05`          text NULL,
    `C_REVIEWER05_STATUS`   text NULL,
    `C_WRITER`              text NULL,
    `C_WRITER_DATE`         text NULL,
    `C_PRIORITY_LINK`       bigint(20) NULL,
    `C_CONTENTS`            longtext NULL,
    `C_REQ_STATUS_LINK`     bigint(20) NULL

    ) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='기본 트리 스키마 트리거 로그';


CREATE TABLE IF NOT EXISTS `aRMS`.`T_ARMS_REQADD` (

    `C_ID` bigint(20) AUTO_INCREMENT primary key COMMENT '노드 아이디',
    `C_PARENTID` bigint(20) NOT NULL COMMENT '부모 노드 아이디',
    `C_POSITION` bigint(20) NOT NULL COMMENT '노드 포지션',
    `C_LEFT` bigint(20) NOT NULL COMMENT '노드 좌측 끝 포인트',
    `C_RIGHT` bigint(20) NOT NULL COMMENT '노드 우측 끝 포인트',
    `C_LEVEL` bigint(20) NOT NULL COMMENT '노드 DEPTH',
    `C_TITLE` VARCHAR(255) COMMENT '노드 명',
    `C_TYPE` VARCHAR(255) COMMENT '노드 타입',

    `C_PDSERVICE_LINK`      bigint(20) NULL,
    `C_VERSION_LINK`        bigint(20) NULL,
    `C_JIRA_LINK`           bigint(20) NULL,
    `C_JIRA_VER_LINK`       bigint(20) NULL,
    `C_ISSUE_LINK`          bigint(20) NULL,

    `C_REVIEWER01`          text NULL,
    `C_REVIEWER01_STATUS`   text NULL,
    `C_REVIEWER02`          text NULL,
    `C_REVIEWER02_STATUS`   text NULL,
    `C_REVIEWER03`          text NULL,
    `C_REVIEWER03_STATUS`   text NULL,
    `C_REVIEWER04`          text NULL,
    `C_REVIEWER04_STATUS`   text NULL,
    `C_REVIEWER05`          text NULL,
    `C_REVIEWER05_STATUS`   text NULL,
    `C_WRITER`              text NULL,
    `C_WRITER_DATE`         text NULL,
    `C_REQ_PRIORITY_LINK`   bigint(20) NULL,
    `C_CONTENTS`            longtext NULL,
    `C_ETC`              text NULL,
    `C_REQ_STATUS_LINK`     bigint(20) NULL

    ) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='기본 트리 스키마 트리거 로그';


Insert into `aRMS`.`T_ARMS_REQADD` (C_ID, C_PARENTID, C_POSITION, C_LEFT, C_RIGHT, C_LEVEL, C_TITLE, C_TYPE)
Values (1, 0, 0, 1, 4, 0, 'T_ARMS_REQADD', 'root');
Insert into `aRMS`.`T_ARMS_REQADD` (C_ID, C_PARENTID, C_POSITION, C_LEFT, C_RIGHT, C_LEVEL, C_TITLE, C_TYPE)
Values (2, 1, 0, 2, 3, 1, '요구사항', 'drive');


DELIMITER $$
CREATE TRIGGER TG_INSERT_T_ARMS_REQADD
    BEFORE  INSERT ON T_ARMS_REQADD
    FOR EACH ROW
BEGIN
    insert into T_ARMS_REQADD_LOG (C_ID, C_PARENTID, C_POSITION, C_LEFT, C_RIGHT, C_LEVEL, C_TITLE, C_TYPE, C_METHOD, C_STATE, C_DATE)
    values (NEW.C_ID,NEW.C_PARENTID,NEW.C_POSITION,NEW.C_LEFT,NEW.C_RIGHT,NEW.C_LEVEL,NEW.C_TITLE,NEW.C_TYPE,'update','변경이전데이터',now());
END $$
DELIMITER ;

DELIMITER $$
CREATE TRIGGER TG_UPDATE_T_ARMS_REQADD
    BEFORE  UPDATE ON T_ARMS_REQADD
    FOR EACH ROW
BEGIN
    insert into T_ARMS_REQADD_LOG (C_ID, C_PARENTID, C_POSITION, C_LEFT, C_RIGHT, C_LEVEL, C_TITLE, C_TYPE, C_METHOD, C_STATE, C_DATE)
    values (OLD.C_ID,OLD.C_PARENTID,OLD.C_POSITION,OLD.C_LEFT,OLD.C_RIGHT,OLD.C_LEVEL,OLD.C_TITLE,OLD.C_TYPE,'update','변경이전데이터',now());
    insert into T_ARMS_REQADD_LOG (C_ID, C_PARENTID, C_POSITION, C_LEFT, C_RIGHT, C_LEVEL, C_TITLE, C_TYPE, C_METHOD, C_STATE, C_DATE)
    values (NEW.C_ID,NEW.C_PARENTID,NEW.C_POSITION,NEW.C_LEFT,NEW.C_RIGHT,NEW.C_LEVEL,NEW.C_TITLE,NEW.C_TYPE,'update','변경이후데이터',now());
END $$
DELIMITER ;

DELIMITER $$
CREATE TRIGGER TG_DELETE_T_ARMS_REQADD
    BEFORE  DELETE ON T_ARMS_REQADD
    FOR EACH ROW
BEGIN
    insert into T_ARMS_REQADD_LOG (C_ID, C_PARENTID, C_POSITION, C_LEFT, C_RIGHT, C_LEVEL, C_TITLE, C_TYPE, C_METHOD, C_STATE, C_DATE)
    values (OLD.C_ID,OLD.C_PARENTID,OLD.C_POSITION,OLD.C_LEFT,OLD.C_RIGHT,OLD.C_LEVEL,OLD.C_TITLE,OLD.C_TYPE,'delete','삭제된데이터',now());
END $$
DELIMITER ;



--
-- Table structure for table `T_ARMS_REQCOMMENT`
--
CREATE TABLE IF NOT EXISTS `aRMS`.`T_ARMS_REQCOMMENT_LOG` (

    `C_ID`                  bigint(20) NOT NULL COMMENT '노드 아이디',
    `C_PARENTID`            bigint(20) NOT NULL COMMENT '부모 노드 아이디',
    `C_POSITION`            bigint(20) NOT NULL COMMENT '노드 포지션',
    `C_LEFT`                bigint(20) NOT NULL COMMENT '노드 좌측 끝 포인트',
    `C_RIGHT`               bigint(20) NOT NULL COMMENT '노드 우측 끝 포인트',
    `C_LEVEL`               bigint(20) NOT NULL COMMENT '노드 DEPTH',
    `C_TITLE`               VARCHAR(255) COMMENT '노드 명',
    `C_TYPE`                VARCHAR(255) COMMENT '노드 타입',

    `C_METHOD`              text NULL COMMENT '노드 변경 행위',
    `C_STATE`               text NULL COMMENT '노드 상태값 ( 이전인지. 이후인지)',
    `C_DATE`                date NULL COMMENT '노드 변경 시',

    `C_PDSERVICE_LINK`      bigint(20) NULL,
    `C_VERSION_LINK`        bigint(20) NULL,
    `C_JIRA_LINK`           bigint(20) NULL,
    `C_JIRA_VER_LINK`       bigint(20) NULL,
    `C_ISSUE_LINK`          bigint(20) NULL,

    `c_req_link`            bigint(20) NULL,
    `c_review_link`         bigint(20) NULL,

    `c_sender`              text NULL,
    `c_comment_date`        text NULL,
    `c_comment`             text NULL

    ) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='기본 트리 스키마 트리거 로그';


CREATE TABLE IF NOT EXISTS `aRMS`.`T_ARMS_REQCOMMENT` (

    `C_ID` bigint(20) AUTO_INCREMENT primary key COMMENT '노드 아이디',
    `C_PARENTID` bigint(20) NOT NULL COMMENT '부모 노드 아이디',
    `C_POSITION` bigint(20) NOT NULL COMMENT '노드 포지션',
    `C_LEFT` bigint(20) NOT NULL COMMENT '노드 좌측 끝 포인트',
    `C_RIGHT` bigint(20) NOT NULL COMMENT '노드 우측 끝 포인트',
    `C_LEVEL` bigint(20) NOT NULL COMMENT '노드 DEPTH',
    `C_TITLE` VARCHAR(255) COMMENT '노드 명',
    `C_TYPE` VARCHAR(255) COMMENT '노드 타입',

    `C_PDSERVICE_LINK`      bigint(20) NULL,
    `C_VERSION_LINK`        bigint(20) NULL,
    `C_JIRA_LINK`           bigint(20) NULL,
    `C_JIRA_VER_LINK`       bigint(20) NULL,
    `C_ISSUE_LINK`          bigint(20) NULL,

    `c_req_link`            bigint(20) NULL,
    `c_review_link`         bigint(20) NULL,

    `c_sender`              text NULL,
    `c_comment_date`        text NULL,
    `c_comment`             text NULL

    ) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='기본 트리 스키마 트리거 로그';


Insert into `aRMS`.`T_ARMS_REQCOMMENT` (C_ID, C_PARENTID, C_POSITION, C_LEFT, C_RIGHT, C_LEVEL, C_TITLE, C_TYPE)
Values (1, 0, 0, 1, 4, 0, 'T_ARMS_REQCOMMENT', 'root');
Insert into `aRMS`.`T_ARMS_REQCOMMENT` (C_ID, C_PARENTID, C_POSITION, C_LEFT, C_RIGHT, C_LEVEL, C_TITLE, C_TYPE)
Values (2, 1, 0, 2, 3, 1, '요구사항', 'drive');


DELIMITER $$
CREATE TRIGGER TG_INSERT_T_ARMS_REQCOMMENT
    BEFORE  INSERT ON T_ARMS_REQCOMMENT
    FOR EACH ROW
BEGIN
    insert into T_ARMS_REQCOMMENT_LOG (C_ID, C_PARENTID, C_POSITION, C_LEFT, C_RIGHT, C_LEVEL, C_TITLE, C_TYPE, C_METHOD, C_STATE, C_DATE)
    values (NEW.C_ID,NEW.C_PARENTID,NEW.C_POSITION,NEW.C_LEFT,NEW.C_RIGHT,NEW.C_LEVEL,NEW.C_TITLE,NEW.C_TYPE,'update','변경이전데이터',now());
END $$
DELIMITER ;

DELIMITER $$
CREATE TRIGGER TG_UPDATE_T_ARMS_REQCOMMENT
    BEFORE  UPDATE ON T_ARMS_REQCOMMENT
    FOR EACH ROW
BEGIN
    insert into T_ARMS_REQCOMMENT_LOG (C_ID, C_PARENTID, C_POSITION, C_LEFT, C_RIGHT, C_LEVEL, C_TITLE, C_TYPE, C_METHOD, C_STATE, C_DATE)
    values (OLD.C_ID,OLD.C_PARENTID,OLD.C_POSITION,OLD.C_LEFT,OLD.C_RIGHT,OLD.C_LEVEL,OLD.C_TITLE,OLD.C_TYPE,'update','변경이전데이터',now());
    insert into T_ARMS_REQCOMMENT_LOG (C_ID, C_PARENTID, C_POSITION, C_LEFT, C_RIGHT, C_LEVEL, C_TITLE, C_TYPE, C_METHOD, C_STATE, C_DATE)
    values (NEW.C_ID,NEW.C_PARENTID,NEW.C_POSITION,NEW.C_LEFT,NEW.C_RIGHT,NEW.C_LEVEL,NEW.C_TITLE,NEW.C_TYPE,'update','변경이후데이터',now());
END $$
DELIMITER ;

DELIMITER $$
CREATE TRIGGER TG_DELETE_T_ARMS_REQCOMMENT
    BEFORE  DELETE ON T_ARMS_REQCOMMENT
    FOR EACH ROW
BEGIN
    insert into T_ARMS_REQCOMMENT_LOG (C_ID, C_PARENTID, C_POSITION, C_LEFT, C_RIGHT, C_LEVEL, C_TITLE, C_TYPE, C_METHOD, C_STATE, C_DATE)
    values (OLD.C_ID,OLD.C_PARENTID,OLD.C_POSITION,OLD.C_LEFT,OLD.C_RIGHT,OLD.C_LEVEL,OLD.C_TITLE,OLD.C_TYPE,'delete','삭제된데이터',now());
END $$
DELIMITER ;



--
-- Table structure for table `T_ARMS_REQREVIEW`
--
CREATE TABLE IF NOT EXISTS `aRMS`.`T_ARMS_REQREVIEW_LOG` (

    `C_ID`                  bigint(20) NOT NULL COMMENT '노드 아이디',
    `C_PARENTID`            bigint(20) NOT NULL COMMENT '부모 노드 아이디',
    `C_POSITION`            bigint(20) NOT NULL COMMENT '노드 포지션',
    `C_LEFT`                bigint(20) NOT NULL COMMENT '노드 좌측 끝 포인트',
    `C_RIGHT`               bigint(20) NOT NULL COMMENT '노드 우측 끝 포인트',
    `C_LEVEL`               bigint(20) NOT NULL COMMENT '노드 DEPTH',
    `C_TITLE`               VARCHAR(255) COMMENT '노드 명',
    `C_TYPE`                VARCHAR(255) COMMENT '노드 타입',

    `C_METHOD`              text NULL COMMENT '노드 변경 행위',
    `C_STATE`               text NULL COMMENT '노드 상태값 ( 이전인지. 이후인지)',
    `C_DATE`                date NULL COMMENT '노드 변경 시',

    `C_PDSERVICE_LINK`      bigint(20) NULL,
    `C_VERSION_LINK`        bigint(20) NULL,
    `C_JIRA_LINK`           bigint(20) NULL,
    `C_JIRA_VER_LINK`       bigint(20) NULL,
    `C_ISSUE_LINK`          bigint(20) NULL,
    `c_req_link`            bigint(20) NULL,

    `c_review_sender`           text NULL,
    `c_review_responder`        text NULL,
    `c_review_creat_date`       text NULL,
    `c_review_result_state`     text NULL,
    `c_review_result_date`      text NULL

) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='기본 트리 스키마 트리거 로그';


CREATE TABLE IF NOT EXISTS `aRMS`.`T_ARMS_REQREVIEW` (

    `C_ID` bigint(20) AUTO_INCREMENT primary key COMMENT '노드 아이디',
    `C_PARENTID` bigint(20) NOT NULL COMMENT '부모 노드 아이디',
    `C_POSITION` bigint(20) NOT NULL COMMENT '노드 포지션',
    `C_LEFT` bigint(20) NOT NULL COMMENT '노드 좌측 끝 포인트',
    `C_RIGHT` bigint(20) NOT NULL COMMENT '노드 우측 끝 포인트',
    `C_LEVEL` bigint(20) NOT NULL COMMENT '노드 DEPTH',
    `C_TITLE` VARCHAR(255) COMMENT '노드 명',
    `C_TYPE` VARCHAR(255) COMMENT '노드 타입',

    `C_PDSERVICE_LINK`      bigint(20) NULL,
    `C_VERSION_LINK`        bigint(20) NULL,
    `C_JIRA_LINK`           bigint(20) NULL,
    `C_JIRA_VER_LINK`       bigint(20) NULL,
    `C_ISSUE_LINK`          bigint(20) NULL,
    `c_req_link`            bigint(20) NULL,

    `c_review_sender`           text NULL,
    `c_review_responder`        text NULL,
    `c_review_creat_date`       text NULL,
    `c_review_result_state`     text NULL,
    `c_review_result_date`      text NULL

) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='기본 트리 스키마 트리거 로그';


Insert into `aRMS`.`T_ARMS_REQREVIEW` (C_ID, C_PARENTID, C_POSITION, C_LEFT, C_RIGHT, C_LEVEL, C_TITLE, C_TYPE)
Values (1, 0, 0, 1, 4, 0, 'T_ARMS_REQREVIEW', 'root');
Insert into `aRMS`.`T_ARMS_REQREVIEW` (C_ID, C_PARENTID, C_POSITION, C_LEFT, C_RIGHT, C_LEVEL, C_TITLE, C_TYPE)
Values (2, 1, 0, 2, 3, 1, '요구사항', 'drive');


DELIMITER $$
CREATE TRIGGER TG_INSERT_T_ARMS_REQREVIEW
    BEFORE  INSERT ON T_ARMS_REQREVIEW
    FOR EACH ROW
BEGIN
    insert into T_ARMS_REQREVIEW_LOG (C_ID, C_PARENTID, C_POSITION, C_LEFT, C_RIGHT, C_LEVEL, C_TITLE, C_TYPE, C_METHOD, C_STATE, C_DATE)
    values (NEW.C_ID,NEW.C_PARENTID,NEW.C_POSITION,NEW.C_LEFT,NEW.C_RIGHT,NEW.C_LEVEL,NEW.C_TITLE,NEW.C_TYPE,'update','변경이전데이터',now());
END $$
DELIMITER ;

DELIMITER $$
CREATE TRIGGER TG_UPDATE_T_ARMS_REQREVIEW
    BEFORE  UPDATE ON T_ARMS_REQREVIEW
    FOR EACH ROW
BEGIN
    insert into T_ARMS_REQREVIEW_LOG (C_ID, C_PARENTID, C_POSITION, C_LEFT, C_RIGHT, C_LEVEL, C_TITLE, C_TYPE, C_METHOD, C_STATE, C_DATE)
    values (OLD.C_ID,OLD.C_PARENTID,OLD.C_POSITION,OLD.C_LEFT,OLD.C_RIGHT,OLD.C_LEVEL,OLD.C_TITLE,OLD.C_TYPE,'update','변경이전데이터',now());
    insert into T_ARMS_REQREVIEW_LOG (C_ID, C_PARENTID, C_POSITION, C_LEFT, C_RIGHT, C_LEVEL, C_TITLE, C_TYPE, C_METHOD, C_STATE, C_DATE)
    values (NEW.C_ID,NEW.C_PARENTID,NEW.C_POSITION,NEW.C_LEFT,NEW.C_RIGHT,NEW.C_LEVEL,NEW.C_TITLE,NEW.C_TYPE,'update','변경이후데이터',now());
END $$
DELIMITER ;

DELIMITER $$
CREATE TRIGGER TG_DELETE_T_ARMS_REQREVIEW
    BEFORE  DELETE ON T_ARMS_REQREVIEW
    FOR EACH ROW
BEGIN
    insert into T_ARMS_REQREVIEW_LOG (C_ID, C_PARENTID, C_POSITION, C_LEFT, C_RIGHT, C_LEVEL, C_TITLE, C_TYPE, C_METHOD, C_STATE, C_DATE)
    values (OLD.C_ID,OLD.C_PARENTID,OLD.C_POSITION,OLD.C_LEFT,OLD.C_RIGHT,OLD.C_LEVEL,OLD.C_TITLE,OLD.C_TYPE,'delete','삭제된데이터',now());
END $$
DELIMITER ;



--
-- Table structure for table `T_ARMS_REQSTATUS`
--
CREATE TABLE IF NOT EXISTS `aRMS`.`T_ARMS_REQSTATUS_LOG` (

    `C_ID`                      bigint(20) NOT NULL COMMENT '노드 아이디',
    `C_PARENTID`                bigint(20) NOT NULL COMMENT '부모 노드 아이디',
    `C_POSITION`                bigint(20) NOT NULL COMMENT '노드 포지션',
    `C_LEFT`                    bigint(20) NOT NULL COMMENT '노드 좌측 끝 포인트',
    `C_RIGHT`                   bigint(20) NOT NULL COMMENT '노드 우측 끝 포인트',
    `C_LEVEL`                   bigint(20) NOT NULL COMMENT '노드 DEPTH',
    `C_TITLE`                   VARCHAR(255) COMMENT '노드 명',
    `C_TYPE`                    VARCHAR(255) COMMENT '노드 타입',

    `C_METHOD`                  text NULL COMMENT '노드 변경 행위',
    `C_STATE`                   text NULL COMMENT '노드 상태값 ( 이전인지. 이후인지)',
    `C_DATE`                    date NULL COMMENT '노드 변경 시',

    `C_PDSERVICE_LINK`          bigint(20) NULL,
    `C_PDSERVICE_NAME`          text NULL,

    `C_PDSERVICE_VERSION_LINK`  bigint(20) NULL,
    `C_PDSERVICE_VERSION_NAME`  text NULL,

    `C_JIRA_PROJECT_LINK`       bigint(20) NULL,
    `C_JIRA_PROJECT_NAME`       text NULL,
    `C_JIRA_PROJECT_KEY`        text NULL,
    `C_JIRA_PROJECT_URL`        text NULL,

    `C_JIRA_VERSION_LINK`       bigint(20) NULL,
    `C_JIRA_VERSION_NAME`       text NULL,
    `C_JIRA_VERSION_URL`        text NULL,

    `C_REQ_LINK`                bigint(20) NULL,
    `C_REQ_NAME`                text NULL,

    `C_REQ_PRIORITY_LINK`       bigint(20) NULL,
    `C_REQ_PRIORITY_NAME`       text NULL,

    `C_REQ_STATUS_LINK`         bigint(20) NULL,
    `C_REQ_STATUS_NAME`         text NULL,

    `C_REQ_JIRA_ISSUE_ID`       text NULL,
    `C_REQ_JIRA_ISSUE_URL`      text NULL,

    `C_REQ_JIRA_ISSUE_STATUS_ID`       text NULL,
    `C_REQ_JIRA_ISSUE_STATUS_URL`      text NULL,

    `C_REQ_JIRA_REL_ISSUE`      text NULL,
    `C_REQ_JIRA_SUB_ISSUE`      text NULL

) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='기본 트리 스키마 트리거 로그';


CREATE TABLE IF NOT EXISTS `aRMS`.`T_ARMS_REQSTATUS` (

    `C_ID`                      bigint(20) AUTO_INCREMENT primary key COMMENT '노드 아이디',
    `C_PARENTID`                bigint(20) NOT NULL COMMENT '부모 노드 아이디',
    `C_POSITION`                bigint(20) NOT NULL COMMENT '노드 포지션',
    `C_LEFT`                    bigint(20) NOT NULL COMMENT '노드 좌측 끝 포인트',
    `C_RIGHT`                   bigint(20) NOT NULL COMMENT '노드 우측 끝 포인트',
    `C_LEVEL`                   bigint(20) NOT NULL COMMENT '노드 DEPTH',
    `C_TITLE`                   VARCHAR(255) COMMENT '노드 명',
    `C_TYPE`                    VARCHAR(255) COMMENT '노드 타입',

    `C_PDSERVICE_LINK`          bigint(20) NULL,
    `C_PDSERVICE_NAME`          text NULL,

    `C_PDSERVICE_VERSION_LINK`  bigint(20) NULL,
    `C_PDSERVICE_VERSION_NAME`  text NULL,

    `C_JIRA_PROJECT_LINK`       bigint(20) NULL,
    `C_JIRA_PROJECT_NAME`       text NULL,
    `C_JIRA_PROJECT_KEY`        text NULL,
    `C_JIRA_PROJECT_URL`        text NULL,

    `C_JIRA_VERSION_LINK`       bigint(20) NULL,
    `C_JIRA_VERSION_NAME`       text NULL,
    `C_JIRA_VERSION_URL`        text NULL,

    `C_REQ_LINK`                bigint(20) NULL,
    `C_REQ_NAME`                text NULL,

    `C_REQ_PRIORITY_LINK`       bigint(20) NULL,
    `C_REQ_PRIORITY_NAME`       text NULL,

    `C_REQ_STATUS_LINK`         bigint(20) NULL,
    `C_REQ_STATUS_NAME`         text NULL,

    `C_REQ_JIRA_ISSUE_ID`       text NULL,
    `C_REQ_JIRA_ISSUE_URL`      text NULL,

    `C_REQ_JIRA_ISSUE_STATUS_ID`       text NULL,
    `C_REQ_JIRA_ISSUE_STATUS_URL`      text NULL,

    `C_REQ_JIRA_REL_ISSUE`      text NULL,
    `C_REQ_JIRA_SUB_ISSUE`      text NULL

) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='기본 트리 스키마 트리거 로그';


Insert into `aRMS`.`T_ARMS_REQSTATUS` (C_ID, C_PARENTID, C_POSITION, C_LEFT, C_RIGHT, C_LEVEL, C_TITLE, C_TYPE)
Values (1, 0, 0, 1, 4, 0, 'T_ARMS_REQSTATUS', 'root');
Insert into `aRMS`.`T_ARMS_REQSTATUS` (C_ID, C_PARENTID, C_POSITION, C_LEFT, C_RIGHT, C_LEVEL, C_TITLE, C_TYPE)
Values (2, 1, 0, 2, 3, 1, '요구사항 - 이슈 결과 장표', 'drive');


DELIMITER $$
CREATE TRIGGER TG_INSERT_T_ARMS_REQSTATUS
    BEFORE  INSERT ON T_ARMS_REQSTATUS
    FOR EACH ROW
BEGIN
    insert into T_ARMS_REQSTATUS_LOG (C_ID, C_PARENTID, C_POSITION, C_LEFT, C_RIGHT, C_LEVEL, C_TITLE, C_TYPE, C_METHOD, C_STATE, C_DATE)
    values (NEW.C_ID,NEW.C_PARENTID,NEW.C_POSITION,NEW.C_LEFT,NEW.C_RIGHT,NEW.C_LEVEL,NEW.C_TITLE,NEW.C_TYPE,'update','변경이전데이터',now());
END $$
DELIMITER ;

DELIMITER $$
CREATE TRIGGER TG_UPDATE_T_ARMS_REQSTATUS
    BEFORE  UPDATE ON T_ARMS_REQSTATUS
    FOR EACH ROW
BEGIN
    insert into T_ARMS_REQSTATUS_LOG (C_ID, C_PARENTID, C_POSITION, C_LEFT, C_RIGHT, C_LEVEL, C_TITLE, C_TYPE, C_METHOD, C_STATE, C_DATE)
    values (OLD.C_ID,OLD.C_PARENTID,OLD.C_POSITION,OLD.C_LEFT,OLD.C_RIGHT,OLD.C_LEVEL,OLD.C_TITLE,OLD.C_TYPE,'update','변경이전데이터',now());
    insert into T_ARMS_REQSTATUS_LOG (C_ID, C_PARENTID, C_POSITION, C_LEFT, C_RIGHT, C_LEVEL, C_TITLE, C_TYPE, C_METHOD, C_STATE, C_DATE)
    values (NEW.C_ID,NEW.C_PARENTID,NEW.C_POSITION,NEW.C_LEFT,NEW.C_RIGHT,NEW.C_LEVEL,NEW.C_TITLE,NEW.C_TYPE,'update','변경이후데이터',now());
END $$
DELIMITER ;

DELIMITER $$
CREATE TRIGGER TG_DELETE_T_ARMS_REQSTATUS
    BEFORE  DELETE ON T_ARMS_REQSTATUS
    FOR EACH ROW
BEGIN
    insert into T_ARMS_REQSTATUS_LOG (C_ID, C_PARENTID, C_POSITION, C_LEFT, C_RIGHT, C_LEVEL, C_TITLE, C_TYPE, C_METHOD, C_STATE, C_DATE)
    values (OLD.C_ID,OLD.C_PARENTID,OLD.C_POSITION,OLD.C_LEFT,OLD.C_RIGHT,OLD.C_LEVEL,OLD.C_TITLE,OLD.C_TYPE,'delete','삭제된데이터',now());
END $$
DELIMITER ;