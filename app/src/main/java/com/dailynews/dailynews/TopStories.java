package com.dailynews.dailynews;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Administrator on 2017/5/6.
 */

public class TopStories {

    /**
     * status : OK
     * copyright : Copyright (c) 2017 The New York Times Company. All Rights Reserved.
     * section : home
     * last_updated : 2017-05-06T10:47:18-04:00
     * num_results : 29
     * results : [{"section":"World","subsection":"Europe","title":"Macron Campaign Says It Was Target of \u2018Massive\u2019 Hacking Attack","abstract":"A trove of internal campaign documents was posted online, campaign staff for the French presidential candidate said.","url":"https://www.nytimes.com/2017/05/05/world/europe/france-macron-hacking.html","byline":"By AURELIEN BREEDEN, SEWELL CHAN and NICOLE PERLROTH","item_type":"Article","updated_date":"2017-05-05T20:56:19-04:00","created_date":"2017-05-05T18:50:33-04:00","published_date":"2017-05-05T18:50:33-04:00","material_type_facet":"","kicker":"","des_facet":["Cyberattacks and Hackers","Elections"],"org_facet":[],"per_facet":["Macron, Emmanuel (1977- )","Le Pen, Marine"],"geo_facet":["France"],"multimedia":[{"url":"https://static01.nyt.com/images/2017/05/06/world/06FRANCE-a1/06FRANCE-a1-thumbStandard.jpg","format":"Standard Thumbnail","height":75,"width":75,"type":"image","subtype":"photo","caption":"The French presidential candidate Emmanuel Macron in Rodez, France, on Friday. His campaign staff said it was the target of a hacking operation.","copyright":"Regis Duvignau/Reuters"},{"url":"https://static01.nyt.com/images/2017/05/06/world/06FRANCE-a1/06FRANCE-a1-thumbLarge.jpg","format":"thumbLarge","height":150,"width":150,"type":"image","subtype":"photo","caption":"The French presidential candidate Emmanuel Macron in Rodez, France, on Friday. His campaign staff said it was the target of a hacking operation.","copyright":"Regis Duvignau/Reuters"},{"url":"https://static01.nyt.com/images/2017/05/06/world/06FRANCE-a1/06FRANCE-a1-articleInline.jpg","format":"Normal","height":127,"width":190,"type":"image","subtype":"photo","caption":"The French presidential candidate Emmanuel Macron in Rodez, France, on Friday. His campaign staff said it was the target of a hacking operation.","copyright":"Regis Duvignau/Reuters"},{"url":"https://static01.nyt.com/images/2017/05/06/world/06FRANCE-a1/06FRANCE-a1-mediumThreeByTwo210.jpg","format":"mediumThreeByTwo210","height":140,"width":210,"type":"image","subtype":"photo","caption":"The French presidential candidate Emmanuel Macron in Rodez, France, on Friday. His campaign staff said it was the target of a hacking operation.","copyright":"Regis Duvignau/Reuters"},{"url":"https://static01.nyt.com/images/2017/05/06/world/06FRANCE-a1/06FRANCE-a1-superJumbo.jpg","format":"superJumbo","height":1366,"width":2048,"type":"image","subtype":"photo","caption":"The French presidential candidate Emmanuel Macron in Rodez, France, on Friday. His campaign staff said it was the target of a hacking operation.","copyright":"Regis Duvignau/Reuters"}],"short_url":"https://nyti.ms/2pPuREe"}]
     */

    private String status;
    private String copyright;
    private String section;
    private String last_updated;
    private int num_results;
    private List<ResultsBean> results;

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

    public String getSection() {
        return section;
    }

    public void setSection(String section) {
        this.section = section;
    }

    public String getLast_updated() {
        return last_updated;
    }

    public void setLast_updated(String last_updated) {
        this.last_updated = last_updated;
    }

    public int getNum_results() {
        return num_results;
    }

    public void setNum_results(int num_results) {
        this.num_results = num_results;
    }

    public List<ResultsBean> getResults() {
        return results;
    }

    public void setResults(List<ResultsBean> results) {
        this.results = results;
    }

    public static class ResultsBean {
        /**
         * section : World
         * subsection : Europe
         * title : Macron Campaign Says It Was Target of ‘Massive’ Hacking Attack
         * abstract : A trove of internal campaign documents was posted online, campaign staff for the French presidential candidate said.
         * url : https://www.nytimes.com/2017/05/05/world/europe/france-macron-hacking.html
         * byline : By AURELIEN BREEDEN, SEWELL CHAN and NICOLE PERLROTH
         * item_type : Article
         * updated_date : 2017-05-05T20:56:19-04:00
         * created_date : 2017-05-05T18:50:33-04:00
         * published_date : 2017-05-05T18:50:33-04:00
         * material_type_facet :
         * kicker :
         * des_facet : ["Cyberattacks and Hackers","Elections"]
         * org_facet : []
         * per_facet : ["Macron, Emmanuel (1977- )","Le Pen, Marine"]
         * geo_facet : ["France"]
         * multimedia : [{"url":"https://static01.nyt.com/images/2017/05/06/world/06FRANCE-a1/06FRANCE-a1-thumbStandard.jpg","format":"Standard Thumbnail","height":75,"width":75,"type":"image","subtype":"photo","caption":"The French presidential candidate Emmanuel Macron in Rodez, France, on Friday. His campaign staff said it was the target of a hacking operation.","copyright":"Regis Duvignau/Reuters"},{"url":"https://static01.nyt.com/images/2017/05/06/world/06FRANCE-a1/06FRANCE-a1-thumbLarge.jpg","format":"thumbLarge","height":150,"width":150,"type":"image","subtype":"photo","caption":"The French presidential candidate Emmanuel Macron in Rodez, France, on Friday. His campaign staff said it was the target of a hacking operation.","copyright":"Regis Duvignau/Reuters"},{"url":"https://static01.nyt.com/images/2017/05/06/world/06FRANCE-a1/06FRANCE-a1-articleInline.jpg","format":"Normal","height":127,"width":190,"type":"image","subtype":"photo","caption":"The French presidential candidate Emmanuel Macron in Rodez, France, on Friday. His campaign staff said it was the target of a hacking operation.","copyright":"Regis Duvignau/Reuters"},{"url":"https://static01.nyt.com/images/2017/05/06/world/06FRANCE-a1/06FRANCE-a1-mediumThreeByTwo210.jpg","format":"mediumThreeByTwo210","height":140,"width":210,"type":"image","subtype":"photo","caption":"The French presidential candidate Emmanuel Macron in Rodez, France, on Friday. His campaign staff said it was the target of a hacking operation.","copyright":"Regis Duvignau/Reuters"},{"url":"https://static01.nyt.com/images/2017/05/06/world/06FRANCE-a1/06FRANCE-a1-superJumbo.jpg","format":"superJumbo","height":1366,"width":2048,"type":"image","subtype":"photo","caption":"The French presidential candidate Emmanuel Macron in Rodez, France, on Friday. His campaign staff said it was the target of a hacking operation.","copyright":"Regis Duvignau/Reuters"}]
         * short_url : https://nyti.ms/2pPuREe
         */

        private String section;
        private String subsection;
        private String title;
        @SerializedName("abstract")
        private String abstractX;
        private String url;
        private String byline;
        private String item_type;
        private String updated_date;
        private String created_date;
        private String published_date;
        private String material_type_facet;
        private String kicker;
        private String short_url;
        private List<String> des_facet;
        private List<?> org_facet;
        private List<String> per_facet;
        private List<String> geo_facet;
        private List<MultimediaBean> multimedia;

        public String getSection() {
            return section;
        }

        public void setSection(String section) {
            this.section = section;
        }

        public String getSubsection() {
            return subsection;
        }

        public void setSubsection(String subsection) {
            this.subsection = subsection;
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

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getByline() {
            return byline;
        }

        public void setByline(String byline) {
            this.byline = byline;
        }

        public String getItem_type() {
            return item_type;
        }

        public void setItem_type(String item_type) {
            this.item_type = item_type;
        }

        public String getUpdated_date() {
            return updated_date;
        }

        public void setUpdated_date(String updated_date) {
            this.updated_date = updated_date;
        }

        public String getCreated_date() {
            return created_date;
        }

        public void setCreated_date(String created_date) {
            this.created_date = created_date;
        }

        public String getPublished_date() {
            return published_date;
        }

        public void setPublished_date(String published_date) {
            this.published_date = published_date;
        }

        public String getMaterial_type_facet() {
            return material_type_facet;
        }

        public void setMaterial_type_facet(String material_type_facet) {
            this.material_type_facet = material_type_facet;
        }

        public String getKicker() {
            return kicker;
        }

        public void setKicker(String kicker) {
            this.kicker = kicker;
        }

        public String getShort_url() {
            return short_url;
        }

        public void setShort_url(String short_url) {
            this.short_url = short_url;
        }

        public List<String> getDes_facet() {
            return des_facet;
        }

        public void setDes_facet(List<String> des_facet) {
            this.des_facet = des_facet;
        }

        public List<?> getOrg_facet() {
            return org_facet;
        }

        public void setOrg_facet(List<?> org_facet) {
            this.org_facet = org_facet;
        }

        public List<String> getPer_facet() {
            return per_facet;
        }

        public void setPer_facet(List<String> per_facet) {
            this.per_facet = per_facet;
        }

        public List<String> getGeo_facet() {
            return geo_facet;
        }

        public void setGeo_facet(List<String> geo_facet) {
            this.geo_facet = geo_facet;
        }

        public List<MultimediaBean> getMultimedia() {
            return multimedia;
        }

        public void setMultimedia(List<MultimediaBean> multimedia) {
            this.multimedia = multimedia;
        }

        public static class MultimediaBean {
            /**
             * url : https://static01.nyt.com/images/2017/05/06/world/06FRANCE-a1/06FRANCE-a1-thumbStandard.jpg
             * format : Standard Thumbnail
             * height : 75
             * width : 75
             * type : image
             * subtype : photo
             * caption : The French presidential candidate Emmanuel Macron in Rodez, France, on Friday. His campaign staff said it was the target of a hacking operation.
             * copyright : Regis Duvignau/Reuters
             */

            private String url;
            private String format;
            private int height;
            private int width;
            private String type;
            private String subtype;
            private String caption;
            private String copyright;

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
        }
    }
}
