package com.ele.model.weixin;

/**
 * 语音消息
 *
 * @author oukailiang
 * @create 2016-09-19 下午2:51
 */

public class VoiceMessage extends BaseMsg {
    /**
     * 语音消息媒体id，可以调用多媒体文件下载接口拉取数据。
     */
    private Long MediaId;
    /**
     * 语音格式，如amr，speex等
     */
    private String Format;

    public Long getMediaId() {
        return MediaId;
    }

    public void setMediaId(Long mediaId) {
        MediaId = mediaId;
    }

    public String getFormat() {
        return Format;
    }

    public void setFormat(String format) {
        Format = format;
    }
}
