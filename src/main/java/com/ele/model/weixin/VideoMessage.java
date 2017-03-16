package com.ele.model.weixin;

/**
 * 视频和小视频消息
 *
 * @author oukailiang
 * @create 2016-09-19 下午2:54
 */

public class VideoMessage extends BaseMsg {

    /**
     * 视频消息媒体id，可以调用多媒体文件下载接口拉取数据。
     */
    private Long MediaId;
    /**
     * 视频消息缩略图的媒体id，可以调用多媒体文件下载接口拉取数据。
     */
    private Long ThumbMediaId;

    public Long getMediaId() {
        return MediaId;
    }

    public void setMediaId(Long mediaId) {
        MediaId = mediaId;
    }

    public Long getThumbMediaId() {
        return ThumbMediaId;
    }

    public void setThumbMediaId(Long thumbMediaId) {
        ThumbMediaId = thumbMediaId;
    }
}
