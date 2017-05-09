package com.dailynews.dailynews.http.bean;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Administrator on 2017/5/7.
 */

public class MostPopular<T> {

    /**
     * status : OK
     * copyright : Copyright (c) 2017 The New York Times Company.  All Rights Reserved.
     * num_results : 50
     * results : [{"url":"https://www.nytimes.com/2017/05/04/books/review/10-new-books-we-recommend-this-week.html","adx_keywords":"Books and Literature","column":"","section":"Books","byline":"","type":"Article","title":"10 New Books We Recommend This Week","abstract":"Suggested reading from critics and editors at The New York Times.","published_date":"2017-05-04","source":"The New York Times","id":100000005053537,"asset_id":100000005053537,"views":1,"des_facet":["BOOKS AND LITERATURE"],"org_facet":"","per_facet":"","geo_facet":"","media":[{"type":"image","subtype":"photo","caption":"","copyright":"","approved_for_syndication":null,"media-metadata":[{"url":"https://static01.nyt.com/images/2017/05/04/books/review/04edchoice-covers/04edchoice-covers-thumbStandard.jpg","format":"Standard Thumbnail","height":75,"width":75},{"url":"https://static01.nyt.com/images/2017/05/04/books/review/04edchoice-covers/04edchoice-covers-mediumThreeByTwo210.jpg","format":"mediumThreeByTwo210","height":140,"width":210},{"url":"https://static01.nyt.com/images/2017/05/04/books/review/04edchoice-covers/04edchoice-covers-mediumThreeByTwo440.jpg","format":"mediumThreeByTwo440","height":293,"width":440}]}]}]
     */

    private String status;
    private String copyright;
    private int num_results;
    private List<ResultsBean<T>> results;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCopyright() {
        return copyright;
    }

    public void setCopyright(String copyright) {
        this.copyright = copyright;
    }

    public int getNum_results() {
        return num_results;
    }

    public void setNum_results(int num_results) {
        this.num_results = num_results;
    }

    public List<ResultsBean<T>> getResults() {
        return results;
    }

    public void setResults(List<ResultsBean<T>> results) {
        this.results = results;
    }

    public static class ResultsBean<T> {
        /**
         * url : https://www.nytimes.com/2017/05/04/books/review/10-new-books-we-recommend-this-week.html
         * adx_keywords : Books and Literature
         * column :
         * section : Books
         * byline :
         * type : Article
         * title : 10 New Books We Recommend This Week
         * abstract : Suggested reading from critics and editors at The New York Times.
         * published_date : 2017-05-04
         * source : The New York Times
         * id : 100000005053537
         * asset_id : 100000005053537
         * views : 1
         * des_facet : ["BOOKS AND LITERATURE"]
         * org_facet :
         * per_facet :
         * geo_facet :
         * media : [{"type":"image","subtype":"photo","caption":"","copyright":"","approved_for_syndication":null,"media-metadata":[{"url":"https://static01.nyt.com/images/2017/05/04/books/review/04edchoice-covers/04edchoice-covers-thumbStandard.jpg","format":"Standard Thumbnail","height":75,"width":75},{"url":"https://static01.nyt.com/images/2017/05/04/books/review/04edchoice-covers/04edchoice-covers-mediumThreeByTwo210.jpg","format":"mediumThreeByTwo210","height":140,"width":210},{"url":"https://static01.nyt.com/images/2017/05/04/books/review/04edchoice-covers/04edchoice-covers-mediumThreeByTwo440.jpg","format":"mediumThreeByTwo440","height":293,"width":440}]}]
         */

        private String url;
        private String adx_keywords;
        private String column;
        private String section;
        private String byline;
        private String type;
        private String title;
        @SerializedName("abstract")
        private String abstractX;
        private String published_date;
        private String source;
        private long id;
        private long asset_id;
        private int views;
        private T media;

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getAdx_keywords() {
            return adx_keywords;
        }

        public void setAdx_keywords(String adx_keywords) {
            this.adx_keywords = adx_keywords;
        }

        public String getColumn() {
            return column;
        }

        public void setColumn(String column) {
            this.column = column;
        }

        public String getSection() {
            return section;
        }

        public void setSection(String section) {
            this.section = section;
        }

        public String getByline() {
            return byline;
        }

        public void setByline(String byline) {
            this.byline = byline;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getAbstractX() {
            return abstractX;
        }

        public void setAbstractX(String abstractX) {
            this.abstractX = abstractX;
        }

        public String getPublished_date() {
            return published_date;
        }

        public void setPublished_date(String published_date) {
            this.published_date = published_date;
        }

        public String getSource() {
            return source;
        }

        public void setSource(String source) {
            this.source = source;
        }

        public long getId() {
            return id;
        }

        public void setId(long id) {
            this.id = id;
        }

        public long getAsset_id() {
            return asset_id;
        }

        public void setAsset_id(long asset_id) {
            this.asset_id = asset_id;
        }

        public int getViews() {
            return views;
        }

        public void setViews(int views) {
            this.views = views;
        }

        public T getMedia() {
            return media;
        }

        public void setMedia(T media) {
            this.media = media;
        }

        public static class MediaBean {
            /**
             * type : image
             * subtype : photo
             * caption :
             * copyright :
             * approved_for_syndication : null
             * media-metadata : [{"url":"https://static01.nyt.com/images/2017/05/04/books/review/04edchoice-covers/04edchoice-covers-thumbStandard.jpg","format":"Standard Thumbnail","height":75,"width":75},{"url":"https://static01.nyt.com/images/2017/05/04/books/review/04edchoice-covers/04edchoice-covers-mediumThreeByTwo210.jpg","format":"mediumThreeByTwo210","height":140,"width":210},{"url":"https://static01.nyt.com/images/2017/05/04/books/review/04edchoice-covers/04edchoice-covers-mediumThreeByTwo440.jpg","format":"mediumThreeByTwo440","height":293,"width":440}]
             */

            private String type;
            private String subtype;
            private String caption;
            private String copyright;
            private Object approved_for_syndication;
            @SerializedName("media-metadata")
            private List<MediametadataBean> mediametadata;

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }

            public String getSubtype() {
                return subtype;
            }

            public void setSubtype(String subtype) {
                this.subtype = subtype;
            }

            public String getCaption() {
                return caption;
            }

            public void setCaption(String caption) {
                this.caption = caption;
            }

            public String getCopyright() {
                return copyright;
            }

            public void setCopyright(String copyright) {
                this.copyright = copyright;
            }

            public Object getApproved_for_syndication() {
                return approved_for_syndication;
            }

            public void setApproved_for_syndication(Object approved_for_syndication) {
                this.approved_for_syndication = approved_for_syndication;
            }

            public List<MediametadataBean> getMediametadata() {
                return mediametadata;
            }

            public void setMediametadata(List<MediametadataBean> mediametadata) {
                this.mediametadata = mediametadata;
            }

            public static class MediametadataBean {
                /**
                 * url : https://static01.nyt.com/images/2017/05/04/books/review/04edchoice-covers/04edchoice-covers-thumbStandard.jpg
                 * format : Standard Thumbnail
                 * height : 75
                 * width : 75
                 */

                private String url;
                private String format;
                private int height;
                private int width;

                public String getUrl() {
                    return url;
                }

                public void setUrl(String url) {
                    this.url = url;
                }

                public String getFormat() {
                    return format;
                }

                public void setFormat(String format) {
                    this.format = format;
                }

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
            }
        }
    }
}
