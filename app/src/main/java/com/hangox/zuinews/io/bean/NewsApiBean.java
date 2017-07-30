package com.hangox.zuinews.io.bean;

import com.google.gson.annotations.SerializedName;

/**
 * Created With Android Studio
 * User hangox
 * Date 2017/7/12
 * Time 下午10:29
 */

public class NewsApiBean extends ShowApiBean<NewsApiBean.ShowapiResBodyBean> {

    public static class ShowapiResBodyBean extends ShowApiBody{

        @SerializedName("pagebean")
        private NewsPageBean pagebean;

        public NewsPageBean getPagebean() {
            return pagebean;
        }

        public ShowapiResBodyBean setPagebean(NewsPageBean pagebean) {
            this.pagebean = pagebean;
            return this;
        }

        @Override
        public String toString() {
            final StringBuilder sb = new StringBuilder("ShowapiResBodyBean{");
            sb.append("pagebean=").append(pagebean);
            sb.append('}');
            return sb.toString();
        }

    }


}
