package com.cdqj.cdqjpatrolandroid.bean;

public class UpImgResultBean {
    //(value="ID")
    private Long id;
    //(value = "状态")
    private Integer status;
    //(value="业务模块ID")
    private Long bizModuleId;
    //(value="源文件名")
    private String source;
    //(value="文件名")
    private String fileName;
    //(value="文件路径")
    private String filePath;
    //(value="文件类型")
    private String fileType;
    //(value="文件扩展名")
    private String fileExtendName;
    //(value="文件大小")
    private Integer fileSize;
    //(value="是否压缩")
    private Integer isCompress;
    //(value="压缩文件ID")
    private Long compressFileId;
    //(value="域ID")
    private Long domainId;
    //(value="创建人")
    private Long addUserId;
    //(value="创建时间")
    private String addTime;
    //(value="修改人")
    private Long updateUserId;
    //(value="修改时间")
    private String updateTime;

    @Override
    public String toString() {
        return "UpImgResultBean{" +
                "id=" + id +
                ", status=" + status +
                ", bizModuleId=" + bizModuleId +
                ", source='" + source + '\'' +
                ", fileName='" + fileName + '\'' +
                ", filePath='" + filePath + '\'' +
                ", fileType='" + fileType + '\'' +
                ", fileExtendName='" + fileExtendName + '\'' +
                ", fileSize=" + fileSize +
                ", isCompress=" + isCompress +
                ", compressFileId=" + compressFileId +
                ", domainId=" + domainId +
                ", addUserId=" + addUserId +
                ", addTime='" + addTime + '\'' +
                ", updateUserId=" + updateUserId +
                ", updateTime='" + updateTime + '\'' +
                '}';
    }

    public Long getId() {
        return id;
    }

    public Integer getStatus() {
        return status;
    }

    public Long getBizModuleId() {
        return bizModuleId;
    }

    public String getSource() {
        return source == null ? "" : source;
    }

    public String getFileName() {
        return fileName == null ? "" : fileName;
    }

    public String getFilePath() {
        return filePath == null ? "" : filePath;
    }

    public String getFileType() {
        return fileType == null ? "" : fileType;
    }

    public String getFileExtendName() {
        return fileExtendName == null ? "" : fileExtendName;
    }

    public Integer getFileSize() {
        return fileSize;
    }

    public Integer getIsCompress() {
        return isCompress;
    }

    public Long getCompressFileId() {
        return compressFileId;
    }

    public Long getDomainId() {
        return domainId;
    }

    public Long getAddUserId() {
        return addUserId;
    }

    public String getAddTime() {
        return addTime == null ? "" : addTime;
    }

    public Long getUpdateUserId() {
        return updateUserId;
    }

    public String getUpdateTime() {
        return updateTime == null ? "" : updateTime;
    }
}
