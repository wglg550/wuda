package com.qmth.wuda.teaching.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.qmth.wuda.teaching.entity.TBAttachment;
import com.qmth.wuda.teaching.enums.UploadFileEnum;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * @Description: 附件 服务类
 * @Param:
 * @return:
 * @Author: wangliang
 * @Date: 2020-04-17
 */
public interface TBAttachmentService extends IService<TBAttachment> {

    /**
     * 保存附件
     *
     * @param file
     * @param md5
     * @param path
     * @param orgId
     * @param userId
     * @return
     * @throws IOException
     */
    TBAttachment saveAttachment(MultipartFile file, String md5, String path, UploadFileEnum type, Long orgId, Long userId) throws IOException;

    /**
     * 删除附件
     *
     * @param tbAttachment
     */
    void deleteAttachment(UploadFileEnum type, TBAttachment tbAttachment);

    /**
     * 批量删除附件
     *
     * @param list
     */
    void batchDeleteAttachment(UploadFileEnum type, List<Map> list);

    TBAttachment saveAttachment(File file, String md5, String path, UploadFileEnum type,
            Long orgId, Long userId) throws IOException;
}