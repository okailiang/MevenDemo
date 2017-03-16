package com.ele.model.weixin;

import java.util.List;

/**
 * 图片文本消息
 *
 * @author oukailiang
 * @create 2016-09-18 下午11:05
 */

public class NewsMessage extends BaseMsg {
    /**
     * 图文消息个数，限制为10条以内
     */
    private Integer ArticleCount;
    /**
     * 多条图文消息信息，默认第一个item为大图,注意，如果图文数超过10，则将会无响应
     */
    private List<Article> Articles;

    public Integer getArticleCount() {
        return ArticleCount;
    }

    public void setArticleCount(Integer articleCount) {
        ArticleCount = articleCount;
    }

    public List<Article> getArticles() {
        return Articles;
    }

    public void setArticles(List<Article> articles) {
        this.Articles = articles;
    }
}
