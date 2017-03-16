package com.ele.model.weixin;

/**
 * 图片消息
 *
 * @author oukailiang
 * @create 2016-09-19 下午2:47
 */

public class ImageMessage extends BaseMsg {
    /**
     * 图片链接（由系统生成）
     */
    private String PicUrl;
    /**
     * 图片消息媒体id，可以调用多媒体文件下载接口拉取数据。
     */
    private Long MediaId;

    /**
     * 语音识别结果，UTF8编码
     */
    private String Recognition;

    public String getPicUrl() {
        return PicUrl;
    }

    public void setPicUrl(String picUrl) {
        PicUrl = picUrl;
    }

    public Long getMediaId() {
        return MediaId;
    }

    public void setMediaId(Long mediaId) {
        MediaId = mediaId;
    }

    public String getRecognition() {
        return Recognition;
    }

    public void setRecognition(String recognition) {
        Recognition = recognition;
    }
}
