package com.hangox.zuinews.io.bean;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created With Android Studio
 * User hangox
 * Date 2017/7/27
 * Time 下午10:16
 */
public class NewsPageBean {

    @SerializedName("allPages")
    private int allPages;
    @SerializedName("currentPage")
    private int currentPage;
    @SerializedName("allNum")
    private int allNum;
    @SerializedName("maxResult")
    private int maxResult;
    @SerializedName("contentlist")
    private List<NewsContentBean> contentlist;

    public int getAllPages() {
        return allPages;
    }

    public void setAllPages(int allPages) {
        this.allPages = allPages;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getAllNum() {
        return allNum;
    }

    public void setAllNum(int allNum) {
        this.allNum = allNum;
    }

    public int getMaxResult() {
        return maxResult;
    }

    public void setMaxResult(int maxResult) {
        this.maxResult = maxResult;
    }

    public List<NewsContentBean> getContentlist() {
        return contentlist;
    }

    public void setContentlist(List<NewsContentBean> contentlist) {
        this.contentlist = contentlist;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("NewsPageBean{");
        sb.append("allPages=").append(allPages);
        sb.append(", currentPage=").append(currentPage);
        sb.append(", allNum=").append(allNum);
        sb.append(", maxResult=").append(maxResult);
        sb.append(", contentlist=").append(contentlist);
        sb.append('}');
        return sb.toString();
    }

}
