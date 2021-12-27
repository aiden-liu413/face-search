package com.visual.face.search.model;

import java.util.ArrayList;
import java.util.List;
import java.io.Serializable;

/***
 * 集合信息对象
 */
public class Collect<ExtendsVo extends Collect<ExtendsVo>> implements Serializable {
    /**集合描述**/
    private String collectionComment;
    /**数据分片中最大的文件个数,仅对Proxima引擎生效**/
    private Long maxDocsPerSegment = 0L;
    /**要创建的集合的分片数,仅对Milvus引擎生效**/
    private Integer shardsNum = 0;
    /**自定义的样本字段**/
    private List<FiledColumn> sampleColumns = new ArrayList<>();
    /**自定义的人脸字段**/
    private List<FiledColumn> faceColumns = new ArrayList<>();
    /**启用binlog同步**/
    private Boolean syncBinLog = false;

    /**
     * 构建集合对象
     * @return
     */
    public static Collect build(){
        return new Collect();
    }

    public String getCollectionComment() {
        return collectionComment;
    }

    public ExtendsVo setCollectionComment(String collectionComment) {
        this.collectionComment = collectionComment;
        return (ExtendsVo) this;
    }

    public Long getMaxDocsPerSegment() {
        return maxDocsPerSegment;
    }

    public ExtendsVo setMaxDocsPerSegment(Long maxDocsPerSegment) {
        if(null != maxDocsPerSegment && maxDocsPerSegment >= 0){
            this.maxDocsPerSegment = maxDocsPerSegment;
        }
        return (ExtendsVo) this;
    }

    public Integer getShardsNum() {
        return shardsNum;
    }

    public ExtendsVo setShardsNum(Integer shardsNum) {
        if(null != shardsNum && shardsNum >= 0){
            this.shardsNum = shardsNum;
        }
        return (ExtendsVo) this;
    }

    public List<FiledColumn> getSampleColumns() {
        return sampleColumns;
    }

    public ExtendsVo setSampleColumns(List<FiledColumn> sampleColumns) {
        if(null != sampleColumns){
            this.sampleColumns = sampleColumns;
        }
        return (ExtendsVo) this;
    }

    public List<FiledColumn> getFaceColumns() {
        return faceColumns;
    }

    public ExtendsVo setFaceColumns(List<FiledColumn> faceColumns) {
        if(null != faceColumns){
            this.faceColumns = faceColumns;
        }
        return (ExtendsVo) this;
    }

    public boolean isSyncBinLog() {
        return null == syncBinLog ? false : syncBinLog;
    }

    public ExtendsVo setSyncBinLog(Boolean syncBinLog) {
        if(null != syncBinLog){
            this.syncBinLog = syncBinLog;
        }
        return (ExtendsVo) this;
    }
}
