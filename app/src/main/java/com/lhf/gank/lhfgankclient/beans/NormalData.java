package com.lhf.gank.lhfgankclient.beans;

import java.util.List;

/**
 * com.lhf.gank.lhfgankclient.fragments
 * Created by zeratel3000
 * on 2015 09 15/9/25 23 45
 * description
 */
public class NormalData {

    /**
     * error : false
     * results : [{"who":"张涵宇","publishedAt":"2015-09-25T03:35:19.839Z","desc":"9.25","type":"福利","url":"http://ww2.sinaimg.cn/large/7a8aed7bgw1ewees6m58qj20dw0kuadj.jpg","used":true,"objectId":"560499f500b086642fd02717","createdAt":"2015-09-25T00:48:53.430Z","updatedAt":"2015-09-25T03:35:22.491Z"},{"who":"张涵宇","publishedAt":"2015-09-24T07:27:21.368Z","desc":"9.24","type":"福利","url":"http://ww4.sinaimg.cn/large/7a8aed7bjw1ewdab2qvtmj20qo0hsdkg.jpg","used":true,"objectId":"560351c660b2ce30bf3038c7","createdAt":"2015-09-24T01:28:38.532Z","updatedAt":"2015-09-24T07:27:21.599Z"},{"who":"张涵宇","publishedAt":"2015-09-23T03:48:08.567Z","desc":"9.23","type":"福利","url":"http://ww1.sinaimg.cn/large/7a8aed7bgw1ewc4irf4syj20go0ltdjk.jpg","used":true,"objectId":"5601fee960b2521f38038f45","createdAt":"2015-09-23T01:22:49.568Z","updatedAt":"2015-09-23T03:48:08.944Z"},{"who":"张涵宇","publishedAt":"2015-09-22T03:53:01.583Z","desc":"9.22-可爱型！！！","type":"福利","url":"http://ww3.sinaimg.cn/large/7a8aed7bgw1ewb2ytx5okj20go0p0jva.jpg","used":true,"objectId":"5600ce6360b2b52ca716b3bc","createdAt":"2015-09-22T03:43:31.996Z","updatedAt":"2015-09-22T03:53:01.938Z"},{"who":"张涵宇","publishedAt":"2015-09-21T03:38:47.257Z","desc":"9.21","type":"福利","url":"http://ww4.sinaimg.cn/large/7a8aed7bgw1ew9t261psfj20p00gxq4r.jpg","used":true,"objectId":"55ff5a1500b0b3604a1e5131","createdAt":"2015-09-21T01:15:01.792Z","updatedAt":"2015-09-21T03:38:48.512Z"},{"who":"张涵宇","publishedAt":"2015-09-18T04:05:44.720Z","desc":"9.18","type":"福利","url":"http://ww2.sinaimg.cn/large/7a8aed7bgw1ew6boqjcdaj20qe11on2h.jpg","used":true,"objectId":"55fb616b00b0b36049b630c5","createdAt":"2015-09-18T00:57:15.110Z","updatedAt":"2015-09-18T04:05:45.115Z"},{"who":"dmj","publishedAt":"2015-09-17T03:56:51.346Z","desc":"9.17","type":"福利","url":"http://ww4.sinaimg.cn/large/610dc034gw1ew5b4ri9mxj20ic0qoq4t.jpg","used":true,"objectId":"55fa3906ddb2dd00269c9bf5","createdAt":"2015-09-17T03:52:38.846Z","updatedAt":"2015-09-17T03:56:51.772Z"},{"who":"张涵宇","publishedAt":"2015-09-16T03:34:05.100Z","desc":"9.16","type":"福利","url":"http://ww3.sinaimg.cn/large/7a8aed7bgw1ew38eojcpzj20p010kwjr.jpg","used":true,"objectId":"55f7db10ddb2e44a0ce0db2c","createdAt":"2015-09-15T08:47:12.714Z","updatedAt":"2015-09-16T03:34:05.637Z"},{"who":"张涵宇","publishedAt":"2015-09-15T05:06:02.964Z","desc":"9.15","type":"福利","url":"http://ww4.sinaimg.cn/large/7a8aed7bgw1ew2wolj6lvj20qo0hsq71.jpg","used":true,"objectId":"55f77bf4ddb23dadf51671c9","createdAt":"2015-09-15T02:01:24.754Z","updatedAt":"2015-09-15T05:06:03.334Z"},{"who":"张涵宇","publishedAt":"2015-09-14T03:55:03.714Z","desc":"9.14","type":"福利","url":"http://ww2.sinaimg.cn/large/7a8aed7bgw1ew1q26gazoj20gx0pfwgi.jpg","used":true,"objectId":"55f6226260b2521fb5a5e859","createdAt":"2015-09-14T01:26:58.789Z","updatedAt":"2015-09-14T03:55:04.088Z"}]
     * 数据类型： 福利 | Android | iOS | 休息视频 | 拓展资源 | 前端 | all
     * 请求个数： 数字，大于0
     * 第几页：数字，大于0
     */

    private boolean error;
    private List<ResultsEntity> results;

    public void setError(boolean error) {
        this.error = error;
    }

    public void setResults(List<ResultsEntity> results) {
        this.results = results;
    }

    public boolean getError() {
        return error;
    }

    public List<ResultsEntity> getResults() {
        return results;
    }

    public static class ResultsEntity {
        /**
         * who : 张涵宇
         * publishedAt : 2015-09-25T03:35:19.839Z
         * desc : 9.25
         * type : 福利
         * url : http://ww2.sinaimg.cn/large/7a8aed7bgw1ewees6m58qj20dw0kuadj.jpg
         * used : true
         * objectId : 560499f500b086642fd02717
         * createdAt : 2015-09-25T00:48:53.430Z
         * updatedAt : 2015-09-25T03:35:22.491Z
         */

        private String who;
        private String publishedAt;
        private String desc;
        private String type;
        private String url;
        private boolean used;
        private String objectId;
        private String createdAt;
        private String updatedAt;

        public void setWho(String who) {
            this.who = who;
        }

        public void setPublishedAt(String publishedAt) {
            this.publishedAt = publishedAt;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }

        public void setType(String type) {
            this.type = type;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public void setUsed(boolean used) {
            this.used = used;
        }

        public void setObjectId(String objectId) {
            this.objectId = objectId;
        }

        public void setCreatedAt(String createdAt) {
            this.createdAt = createdAt;
        }

        public void setUpdatedAt(String updatedAt) {
            this.updatedAt = updatedAt;
        }

        public String getWho() {
            return who;
        }

        public String getPublishedAt() {
            return publishedAt;
        }

        public String getDesc() {
            return desc;
        }

        public String getType() {
            return type;
        }

        public String getUrl() {
            return url;
        }

        public boolean getUsed() {
            return used;
        }

        public String getObjectId() {
            return objectId;
        }

        public String getCreatedAt() {
            return createdAt;
        }

        public String getUpdatedAt() {
            return updatedAt;
        }
    }
}
