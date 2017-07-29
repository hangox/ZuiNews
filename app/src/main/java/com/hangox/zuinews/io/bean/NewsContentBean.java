package com.hangox.zuinews.io.bean;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created With Android Studio
 * User hangox
 * Date 2017/7/27
 * Time 下午10:17
 */
public class NewsContentBean {
    /**
     * id : 4306afa3043adbbf74a7bb72e8fba322
     * havePic : true
     * pubDate : 2017-07-12 21:20:38
     * title : 菲律宾军方再发误炸事件 2名己方士兵丧生
     * channelName : 国际焦点
     * imageurls : [{"height":0,"width":0,"url":"http://n.sinaimg.cn/translate/20170712/9UAl-fyhwret3561141.jpg"}]
     * desc : 　　菲律宾军方在7月12日证实，当天菲律宾军方在空袭中再次发生误炸事件，导致至少2名己方士兵丧生，另有11名士兵受伤。　　军方报告显示，实施空袭的飞行员没有瞄准目标，而是将炸弹投掷在了距离目标250米之外的地方。爆炸导致附近建筑物垮塌，建筑物的重型碎片击中了己方士兵，....
     * source : 新浪
     * channelId : 5572a108b3cdc86cf39001ce
     * link : http://news.sina.com.cn/o/2017-07-12/doc-ifyiaewh8891227.shtml
     * hasAll : true
     */

    @SerializedName("id")
    private String id;
    @SerializedName("havePic")
    private boolean havePic;
    @SerializedName("pubDate")
    private String pubDate;
    @SerializedName("title")
    private String title;
    @SerializedName("channelName")
    private String channelName;
    @SerializedName("desc")
    private String desc;
    @SerializedName("source")
    private String source;
    @SerializedName("channelId")
    private String channelId;
    @SerializedName("link")
    private String link;
    @SerializedName("hasAll")
    private boolean hasAll;
    @SerializedName("imageurls")
    private List<ImageurlsBean> imageurls;
    @SerializedName("html")
    private String html;

    public String getHtml() {
        return html;
    }

    public NewsContentBean setHtml(String html) {
        this.html = html;
        return this;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public boolean isHavePic() {
        return havePic;
    }

    public void setHavePic(boolean havePic) {
        this.havePic = havePic;
    }

    public String getPubDate() {
        return pubDate;
    }

    public void setPubDate(String pubDate) {
        this.pubDate = pubDate;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getChannelName() {
        return channelName;
    }

    public void setChannelName(String channelName) {
        this.channelName = channelName;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getChannelId() {
        return channelId;
    }

    public void setChannelId(String channelId) {
        this.channelId = channelId;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public boolean isHasAll() {
        return hasAll;
    }

    public void setHasAll(boolean hasAll) {
        this.hasAll = hasAll;
    }

    public List<ImageurlsBean> getImageurls() {
        return imageurls;
    }

    public void setImageurls(List<ImageurlsBean> imageurls) {
        this.imageurls = imageurls;
    }

    public static class ImageurlsBean {
        /**
         * height : 0
         * width : 0
         * url : http://n.sinaimg.cn/translate/20170712/9UAl-fyhwret3561141.jpg
         */

        @SerializedName("height")
        private int height;
        @SerializedName("width")
        private int width;
        @SerializedName("url")
        private String url;

        public int getHeight() {
            return height;
        }

        public void setHeight(int height) {
            this.height = height;
        }

        public int getWidth() {
            return width;
        }

        public void setWidth(int width) {
            this.width = width;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("NewsContentBean{");
        sb.append("id='").append(id).append('\'');
        sb.append(", havePic=").append(havePic);
        sb.append(", pubDate='").append(pubDate).append('\'');
        sb.append(", title='").append(title).append('\'');
        sb.append(", channelName='").append(channelName).append('\'');
        sb.append(", desc='").append(desc).append('\'');
        sb.append(", source='").append(source).append('\'');
        sb.append(", channelId='").append(channelId).append('\'');
        sb.append(", link='").append(link).append('\'');
        sb.append(", hasAll=").append(hasAll);
        sb.append(", imageurls=").append(imageurls);
        sb.append('}');
        return sb.toString();
    }

}
