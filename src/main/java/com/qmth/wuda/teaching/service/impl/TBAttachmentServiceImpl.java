package com.qmth.wuda.teaching.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qmth.wuda.teaching.config.DictionaryConfig;
import com.qmth.wuda.teaching.constant.SystemConstant;
import com.qmth.wuda.teaching.dao.TBAttachmentMapper;
import com.qmth.wuda.teaching.entity.TBAttachment;
import com.qmth.wuda.teaching.enums.ExceptionResultEnum;
import com.qmth.wuda.teaching.enums.UploadFileEnum;
import com.qmth.wuda.teaching.exception.BusinessException;
import com.qmth.wuda.teaching.service.TBAttachmentService;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;

/**
 * @Description: 附件 服务实现类
 * @Param:
 * @return:
 * @Author: wangliang
 * @Date: 2020-04-17
 */
@Service
public class TBAttachmentServiceImpl extends ServiceImpl<TBAttachmentMapper, TBAttachment>
        implements TBAttachmentService {
    private final static Logger log = LoggerFactory.getLogger(TBAttachmentServiceImpl.class);

    @Resource
    DictionaryConfig dictionaryConfig;

    /**
     * 保存附件
     *
     * @param file
     * @param md5
     * @param path
     * @param map
     * @param orgId
     * @param userId
     * @return
     * @throws IOException
     */
    @Override
    @Transactional
    public TBAttachment saveAttachment(MultipartFile file, String md5, String path,
                                       UploadFileEnum type, Long orgId, Long userId) throws IOException {
        TBAttachment tbAttachment = null;
        try {
            int temp = file.getOriginalFilename().indexOf(".");
            String fileName = file.getOriginalFilename().substring(0, temp);
            String format = file.getOriginalFilename().substring(temp, file.getOriginalFilename().length());
            List<String> attachmentTypeList = dictionaryConfig.sysDomain().getAttachmentType();
            if (Objects.nonNull(format)) {
                long count = attachmentTypeList.stream().filter(s -> format.equalsIgnoreCase(s)).count();
                if (count == 0) {
                    throw new BusinessException("文件格式只能为" + attachmentTypeList.toString());
                }
            }
            if (Objects.nonNull(fileName) && fileName.length() > 64) {
                throw new BusinessException("文件名长度不能超过64个字符");
            }
            long size = file.getSize();
            BigDecimal b = new BigDecimal(size);
            BigDecimal num = new BigDecimal(1024);
            b = b.divide(num, 2, BigDecimal.ROUND_HALF_UP).divide(num, 2, BigDecimal.ROUND_HALF_UP)
                    .setScale(2, BigDecimal.ROUND_HALF_UP);
            if (b.doubleValue() > 200) {
                throw new BusinessException("文件大小不能超过200MB");
            }
            log.info("fileName:{}", fileName);
            log.info("format:{}", format);
            log.info("size:{}", b);
            log.info("getOriginalFilename:{}", file.getOriginalFilename());
            String fileMd5 = DigestUtils.md5Hex(file.getBytes());
            log.info("fileMd5:{}", fileMd5);
            log.info("md5:{}", md5);
            if (Objects.isNull(md5)) {
                throw new BusinessException("request里的md5为空");
            }
            if (!Objects.equals(fileMd5, md5)) {
                throw new BusinessException("md5不一致");
            }
            tbAttachment = new TBAttachment(path, fileName, format, b, fileMd5);

            boolean oss = dictionaryConfig.sysDomain().isOss();
            LocalDateTime nowTime = LocalDateTime.now();
            StringJoiner stringJoiner = new StringJoiner("");
            if (!oss) {
                stringJoiner.add(SystemConstant.TEMP_FILES_DIR).add(File.separator);
            }

            String uploadType = UploadFileEnum.file.name();
            if (Objects.isNull(uploadType)) {
                throw new BusinessException(ExceptionResultEnum.ATTACHMENT_TYPE_IS_NULL);
            }
            stringJoiner.add(uploadType).add(File.separator);
            stringJoiner.add(String.valueOf(nowTime.getYear())).add(File.separator)
                    .add(String.format("%02d", nowTime.getMonthValue())).add(File.separator)
                    .add(String.format("%02d", nowTime.getDayOfMonth()));

            File finalFile = new File(
                    stringJoiner.add(File.separator).add(String.valueOf(UUID.randomUUID()).replaceAll("-", ""))
                            .add(tbAttachment.getType()).toString());
            if (!finalFile.exists()) {
                finalFile.getParentFile().mkdirs();
                finalFile.createNewFile();
            }
            FileUtils.copyInputStreamToFile(file.getInputStream(), finalFile);
            JSONObject jsonObject = new JSONObject();
            if (oss) {
                jsonObject.put(SystemConstant.TYPE, SystemConstant.OSS);
            } else {
                jsonObject.put(SystemConstant.TYPE, SystemConstant.LOCAL);
            }
            jsonObject.put(SystemConstant.PATH, stringJoiner.toString());
            jsonObject.put(SystemConstant.UPLOAD_TYPE, UploadFileEnum.valueOf(uploadType).ordinal());
            tbAttachment.setRemark(jsonObject.toJSONString());
            tbAttachment.setCreateId(userId);
            this.save(tbAttachment);
        } catch (Exception e) {
            log.error("请求出错", e);
            deleteAttachment(type, tbAttachment);
            if (e instanceof BusinessException) {
                throw new BusinessException(e.getMessage());
            } else {
                throw new RuntimeException(e);
            }
        }
        return tbAttachment;
    }

    @Override
    @Transactional
    public TBAttachment saveAttachment(File file, String md5, String path, UploadFileEnum type,
                                       Long orgId, Long userId) throws IOException {
        TBAttachment tbAttachment = null;
        try {
            int temp = file.getName().indexOf(".");
            String fileName = file.getName().substring(0, temp);
            String format = file.getName().substring(temp, file.getName().length());
            List<String> attachmentTypeList = dictionaryConfig.sysDomain().getAttachmentType();
            if (Objects.nonNull(format)) {
                long count = attachmentTypeList.stream().filter(s -> format.equalsIgnoreCase(s)).count();
                if (count == 0) {
                    throw new BusinessException("文件格式只能为" + attachmentTypeList.toString());
                }
            }
            long size = file.length();
            BigDecimal b = new BigDecimal(size);
            BigDecimal num = new BigDecimal(1024);
            b = b.divide(num).divide(num).setScale(2, BigDecimal.ROUND_HALF_UP);
            if (b.doubleValue() > 200) {
                throw new BusinessException("文件大小不能超过200MB");
            }
            log.info("fileName:{}", fileName);
            log.info("format:{}", format);
            log.info("size:{}", b);
            log.info("md5:{}", md5);
            tbAttachment = new TBAttachment(path, fileName, format, b, md5);

            boolean oss = dictionaryConfig.sysDomain().isOss();
            LocalDateTime nowTime = LocalDateTime.now();
            StringJoiner stringJoiner = new StringJoiner("");
            if (!oss) {
                stringJoiner.add(SystemConstant.TEMP_FILES_DIR).add(File.separator);
            }

            String uploadType = UploadFileEnum.file.name();
            if (Objects.isNull(uploadType)) {
                throw new BusinessException(ExceptionResultEnum.ATTACHMENT_TYPE_IS_NULL);
            }
            stringJoiner.add(uploadType).add(File.separator);
            stringJoiner.add(File.separator).add(String.valueOf(nowTime.getYear())).add(File.separator)
                    .add(String.format("%02d", nowTime.getMonthValue())).add(File.separator)
                    .add(String.format("%02d", nowTime.getDayOfMonth()));

            File finalFile = new File(
                    stringJoiner.add(File.separator).add(String.valueOf(UUID.randomUUID()).replaceAll("-", ""))
                            .add(tbAttachment.getType()).toString());
            if (!finalFile.exists()) {
                finalFile.getParentFile().mkdirs();
                finalFile.createNewFile();
            }
            FileUtils.copyFile(file, finalFile);
            JSONObject jsonObject = new JSONObject();
            if (oss) {
                jsonObject.put(SystemConstant.TYPE, SystemConstant.OSS);
            } else {
                jsonObject.put(SystemConstant.TYPE, SystemConstant.LOCAL);
            }
            jsonObject.put(SystemConstant.PATH, stringJoiner.toString());
            jsonObject.put(SystemConstant.UPLOAD_TYPE, UploadFileEnum.valueOf(uploadType).ordinal());
            tbAttachment.setRemark(jsonObject.toJSONString());
            tbAttachment.setCreateId(userId);
            this.save(tbAttachment);
        } catch (Exception e) {
            log.error("请求出错", e);
            deleteAttachment(type, tbAttachment);
            if (e instanceof BusinessException) {
                throw new BusinessException(e.getMessage());
            } else {
                throw new RuntimeException(e);
            }
        }
        return tbAttachment;
    }

    /**
     * 删除附件
     *
     * @param tbAttachment
     */
    @Override
    public void deleteAttachment(UploadFileEnum fileType, TBAttachment tbAttachment) {
        if (Objects.nonNull(tbAttachment) && Objects.nonNull(tbAttachment.getRemark())) {
            JSONObject jsonObject = JSONObject.parseObject(tbAttachment.getRemark());
            File file = new File(jsonObject.get(SystemConstant.PATH).toString());
            file.delete();
        }
    }

    /**
     * 批量删除附件
     *
     * @param list
     */
    @Override
    public void batchDeleteAttachment(UploadFileEnum fileType, List<Map> list) {
        List<String> paths = new ArrayList<>();
        for (Map m : list) {
            JSONObject jsonObject = JSONObject.parseObject((String) m.get("remark"));
            paths.add((String) jsonObject.get("path"));
        }
        for (String s : paths) {
            File file = new File(s);
            file.delete();
        }
    }
}