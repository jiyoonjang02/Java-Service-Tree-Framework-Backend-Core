package com.egovframework.ple.treeframework.util;

import com.arms.filerepository.model.FileRepositoryEntity;
import com.arms.filerepository.service.FileRepository;
import org.slf4j.Logger;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.util.HashMap;
import java.util.List;

public class FileHandler {

    public static HashMap<String, List<EgovFormBasedFileVo>> upload(MultipartHttpServletRequest multiRequest,
                                                                    String c_title,
                                                                    FileRepository fileRepository,
                                                                    Logger logger) throws Exception {

        logger.info("FileHandler :: upload :: c_title -> " + c_title);

        // Spring multipartResolver 미사용 시 (commons-fileupload 활용)
        //List<EgovFormBasedFileVo> list = EgovFormBasedFileUtil.uploadFiles(request, uploadDir, maxFileSize);

        // Spring multipartResolver 사용시
        PropertiesReader propertiesReader = new PropertiesReader("com/egovframework/property/globals.properties");
        String uploadDir = propertiesReader.getProperty("Globals.fileStorePath");
        long maxFileSize = new Long(313);
        List<EgovFormBasedFileVo> list = EgovFileUploadUtil.uploadFiles(multiRequest, uploadDir, maxFileSize);

        for (EgovFormBasedFileVo egovFormBasedFileVo : list) {

            FileRepositoryEntity fileRepositoryEntity = new FileRepositoryEntity();
            fileRepositoryEntity.setFileName(egovFormBasedFileVo.getFileName());
            fileRepositoryEntity.setContentType(egovFormBasedFileVo.getContentType());
            fileRepositoryEntity.setServerSubPath(egovFormBasedFileVo.getServerSubPath());
            fileRepositoryEntity.setPhysicalName(egovFormBasedFileVo.getPhysicalName());
            fileRepositoryEntity.setSize(egovFormBasedFileVo.getSize());
            fileRepositoryEntity.setName(egovFormBasedFileVo.getName());

            fileRepositoryEntity.setUrl(egovFormBasedFileVo.getUrl());
            //TODO: 썸네일 개발 필요
            fileRepositoryEntity.setThumbnailUrl(egovFormBasedFileVo.getThumbnailUrl());

            fileRepositoryEntity.setDelete_url(egovFormBasedFileVo.getDelete_url());
            fileRepositoryEntity.setDelete_type(egovFormBasedFileVo.getDelete_type());

            fileRepositoryEntity.setRef(new Long(2));
            fileRepositoryEntity.setC_title(c_title);
            fileRepositoryEntity.setC_type("default");

            FileRepositoryEntity returnFileRepositoryEntity = fileRepository.addNode(fileRepositoryEntity);
            //delete 파라미터인 id 값을 업데이트 치기 위해서.
            fileRepositoryEntity.setUrl("/auth-user/api/arms/fileRepository" + "/downloadFileByNode/" + returnFileRepositoryEntity.getId());
            fileRepositoryEntity.setThumbnailUrl("/auth-user/api/arms/fileRepository" + "/thumbnailUrlFileToNode/" + returnFileRepositoryEntity.getId());
            fileRepositoryEntity.setDelete_url("/auth-user/api/arms/fileRepository" + "/deleteFileByNode/" + returnFileRepositoryEntity.getId());

            fileRepository.updateNode(fileRepositoryEntity);

            egovFormBasedFileVo.setUrl("/auth-user/api/arms/fileRepository" + "/downloadFileByNode/" + returnFileRepositoryEntity.getId());
            egovFormBasedFileVo.setThumbnailUrl("/auth-user/api/arms/fileRepository" + "/thumbnailUrlFileToNode/" + returnFileRepositoryEntity.getId());
            egovFormBasedFileVo.setDelete_url("/auth-user/api/arms/fileRepository" + "/deleteFileByNode/" + returnFileRepositoryEntity.getId());

        }

        HashMap<String, List<EgovFormBasedFileVo>> map = new HashMap();
        map.put("files", list);
        return map;
    }
}
