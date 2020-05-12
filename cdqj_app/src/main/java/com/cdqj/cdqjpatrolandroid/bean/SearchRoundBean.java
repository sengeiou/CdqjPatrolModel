package com.cdqj.cdqjpatrolandroid.bean;

import java.util.List;

/**
 * Created by lyf on 2018/11/7 09:15
 *
 * @author lyf
 * desc：
 */
public class SearchRoundBean {

    /**
     * landmarkcount : 0
     * searchversion : 4.3.0
     * count : 3
     * engineversion : 20180412
     * resultType : 1
     * pois : [{"eaddress":"No.11 Qianmen E Avenue","ename":"Chairman Maozedong Memorial Hall","address":"前门东大街11号","phone":"010-65132277,010-65120909","name":"毛主席纪念堂","hotPointID":"906E64848A7D9F1E","url":"","lonlat":"116.391528 39.901084"},{"eaddress":"","ename":"","address":"前门东大街21号","phone":"010-65230463","name":"毛主席纪念堂服务部","hotPointID":"114240055BB80E57","url":"","lonlat":"116.395544 39.899508"},{"eaddress":"","ename":"","address":"前门东大街21号","phone":"010-65223520","name":"中共中央办公厅毛主席纪念堂管理局","hotPointID":"01786083A8C93BC9","url":"","lonlat":"116.395544 39.899508"}]
     * dataversion : 2018-10-31 10:31:28
     * prompt : [{"type":4,"admins":[{"name":"北京市","adminCode":156110000}]}]
     * mclayer :
     * keyWord : 毛主席
     */

    private int landmarkcount;
    private String searchversion;
    private String count;
    private String engineversion;
    private int resultType;
    private String dataversion;
    private String mclayer;
    private String keyWord;
    private List<PoisBean> pois;
    private List<PromptBean> prompt;

    public int getLandmarkcount() {
        return landmarkcount;
    }

    public void setLandmarkcount(int landmarkcount) {
        this.landmarkcount = landmarkcount;
    }

    public String getSearchversion() {
        return searchversion;
    }

    public void setSearchversion(String searchversion) {
        this.searchversion = searchversion;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public String getEngineversion() {
        return engineversion;
    }

    public void setEngineversion(String engineversion) {
        this.engineversion = engineversion;
    }

    public int getResultType() {
        return resultType;
    }

    public void setResultType(int resultType) {
        this.resultType = resultType;
    }

    public String getDataversion() {
        return dataversion;
    }

    public void setDataversion(String dataversion) {
        this.dataversion = dataversion;
    }

    public String getMclayer() {
        return mclayer;
    }

    public void setMclayer(String mclayer) {
        this.mclayer = mclayer;
    }

    public String getKeyWord() {
        return keyWord;
    }

    public void setKeyWord(String keyWord) {
        this.keyWord = keyWord;
    }

    public List<PoisBean> getPois() {
        return pois;
    }

    public void setPois(List<PoisBean> pois) {
        this.pois = pois;
    }

    public List<PromptBean> getPrompt() {
        return prompt;
    }

    public void setPrompt(List<PromptBean> prompt) {
        this.prompt = prompt;
    }

    public static class PoisBean {
        /**
         * eaddress : No.11 Qianmen E Avenue
         * ename : Chairman Maozedong Memorial Hall
         * address : 前门东大街11号
         * phone : 010-65132277,010-65120909
         * name : 毛主席纪念堂
         * hotPointID : 906E64848A7D9F1E
         * url :
         * lonlat : 116.391528 39.901084
         */

        private String eaddress;
        private String ename;
        private String address;
        private String phone;
        private String name;
        private String hotPointID;
        private String url;
        private String lonlat;

        public String getEaddress() {
            return eaddress;
        }

        public void setEaddress(String eaddress) {
            this.eaddress = eaddress;
        }

        public String getEname() {
            return ename;
        }

        public void setEname(String ename) {
            this.ename = ename;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getHotPointID() {
            return hotPointID;
        }

        public void setHotPointID(String hotPointID) {
            this.hotPointID = hotPointID;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getLonlat() {
            return lonlat;
        }

        public void setLonlat(String lonlat) {
            this.lonlat = lonlat;
        }
    }

    public static class PromptBean {
        /**
         * type : 4
         * admins : [{"name":"北京市","adminCode":156110000}]
         */

        private int type;
        private List<AdminsBean> admins;

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public List<AdminsBean> getAdmins() {
            return admins;
        }

        public void setAdmins(List<AdminsBean> admins) {
            this.admins = admins;
        }

        public static class AdminsBean {
            /**
             * name : 北京市
             * adminCode : 156110000
             */

            private String name;
            private int adminCode;

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public int getAdminCode() {
                return adminCode;
            }

            public void setAdminCode(int adminCode) {
                this.adminCode = adminCode;
            }
        }
    }
}
