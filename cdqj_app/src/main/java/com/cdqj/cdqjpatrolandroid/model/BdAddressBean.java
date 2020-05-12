package com.cdqj.cdqjpatrolandroid.model;

import java.util.List;

public class BdAddressBean {
    /**
     * status : 0
     * result : {"location":{"lng":104.03086589999998,"lat":30.649777411427497},"formatted_address":"四川省成都市武侯区二环路西1段-90号-2-、3","business":"双楠,红牌楼,燃灯寺","addressComponent":{"country":"中国","country_code":0,"country_code_iso":"CHN","country_code_iso2":"CN","province":"四川省","city":"成都市","city_level":2,"district":"武侯区","town":"","adcode":"510107","street":"二环路","street_number":"西1段-90号-2-、3","direction":"附近","distance":"17"},"pois":[],"roads":[],"poiRegions":[{"direction_desc":"内","name":"四川交投大厦","tag":"房地产;写字楼","uid":"b7021bf837d2fdfbf4e73be9"}],"sematic_description":"四川交投大厦内,高速大厦南56米","cityCode":75}
     */

    private int status;
    private ResultBean result;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public ResultBean getResult() {
        return result;
    }

    public void setResult(ResultBean result) {
        this.result = result;
    }

    @Override
    public String toString() {
        return "BdAddressBean{" +
                "status=" + status +
                ", result=" + result +
                '}';
    }

    public static class ResultBean {
        /**
         * location : {"lng":104.03086589999998,"lat":30.649777411427497}
         * formatted_address : 四川省成都市武侯区二环路西1段-90号-2-、3
         * business : 双楠,红牌楼,燃灯寺
         * addressComponent : {"country":"中国","country_code":0,"country_code_iso":"CHN","country_code_iso2":"CN","province":"四川省","city":"成都市","city_level":2,"district":"武侯区","town":"","adcode":"510107","street":"二环路","street_number":"西1段-90号-2-、3","direction":"附近","distance":"17"}
         * pois : []
         * roads : []
         * poiRegions : [{"direction_desc":"内","name":"四川交投大厦","tag":"房地产;写字楼","uid":"b7021bf837d2fdfbf4e73be9"}]
         * sematic_description : 四川交投大厦内,高速大厦南56米
         * cityCode : 75
         */

        private LocationBean location;
        private String formatted_address;
        private String business;
        private AddressComponentBean addressComponent;
        private String sematic_description;
        private int cityCode;
        private List<String> pois;
        private List<String> roads;
        private List<PoiRegionsBean> poiRegions;

        public LocationBean getLocation() {
            return location;
        }

        public void setLocation(LocationBean location) {
            this.location = location;
        }

        public String getFormatted_address() {
            return formatted_address;
        }

        public void setFormatted_address(String formatted_address) {
            this.formatted_address = formatted_address;
        }

        public String getBusiness() {
            return business;
        }

        public void setBusiness(String business) {
            this.business = business;
        }

        public AddressComponentBean getAddressComponent() {
            return addressComponent;
        }

        public void setAddressComponent(AddressComponentBean addressComponent) {
            this.addressComponent = addressComponent;
        }

        public String getSematic_description() {
            return sematic_description;
        }

        public void setSematic_description(String sematic_description) {
            this.sematic_description = sematic_description;
        }

        public int getCityCode() {
            return cityCode;
        }

        public void setCityCode(int cityCode) {
            this.cityCode = cityCode;
        }

        public List<?> getPois() {
            return pois;
        }

        public void setPois(List<String> pois) {
            this.pois = pois;
        }

        public List<?> getRoads() {
            return roads;
        }

        public void setRoads(List<String> roads) {
            this.roads = roads;
        }

        public List<PoiRegionsBean> getPoiRegions() {
            return poiRegions;
        }

        public void setPoiRegions(List<PoiRegionsBean> poiRegions) {
            this.poiRegions = poiRegions;
        }

        @Override
        public String toString() {
            return "ResultBean{" +
                    "location=" + location +
                    ", formatted_address='" + formatted_address + '\'' +
                    ", business='" + business + '\'' +
                    ", addressComponent=" + addressComponent +
                    ", sematic_description='" + sematic_description + '\'' +
                    ", cityCode=" + cityCode +
                    ", pois=" + pois +
                    ", roads=" + roads +
                    ", poiRegions=" + poiRegions +
                    '}';
        }

        public static class LocationBean {
            /**
             * lng : 104.03086589999998
             * lat : 30.649777411427497
             */

            private double lng;
            private double lat;

            public double getLng() {
                return lng;
            }

            public void setLng(double lng) {
                this.lng = lng;
            }

            public double getLat() {
                return lat;
            }

            public void setLat(double lat) {
                this.lat = lat;
            }

            @Override
            public String toString() {
                return "LocationBean{" +
                        "lng=" + lng +
                        ", lat=" + lat +
                        '}';
            }
        }

        public static class AddressComponentBean {
            /**
             * country : 中国
             * country_code : 0
             * country_code_iso : CHN
             * country_code_iso2 : CN
             * province : 四川省
             * city : 成都市
             * city_level : 2
             * district : 武侯区
             * town :
             * adcode : 510107
             * street : 二环路
             * street_number : 西1段-90号-2-、3
             * direction : 附近
             * distance : 17
             */

            private String country;
            private int country_code;
            private String country_code_iso;
            private String country_code_iso2;
            private String province;
            private String city;
            private int city_level;
            private String district;
            private String town;
            private String adcode;
            private String street;
            private String street_number;
            private String direction;
            private String distance;

            public String getCountry() {
                return country;
            }

            public void setCountry(String country) {
                this.country = country;
            }

            public int getCountry_code() {
                return country_code;
            }

            public void setCountry_code(int country_code) {
                this.country_code = country_code;
            }

            public String getCountry_code_iso() {
                return country_code_iso;
            }

            public void setCountry_code_iso(String country_code_iso) {
                this.country_code_iso = country_code_iso;
            }

            public String getCountry_code_iso2() {
                return country_code_iso2;
            }

            public void setCountry_code_iso2(String country_code_iso2) {
                this.country_code_iso2 = country_code_iso2;
            }

            public String getProvince() {
                return province;
            }

            public void setProvince(String province) {
                this.province = province;
            }

            public String getCity() {
                return city;
            }

            public void setCity(String city) {
                this.city = city;
            }

            public int getCity_level() {
                return city_level;
            }

            public void setCity_level(int city_level) {
                this.city_level = city_level;
            }

            public String getDistrict() {
                return district;
            }

            public void setDistrict(String district) {
                this.district = district;
            }

            public String getTown() {
                return town;
            }

            public void setTown(String town) {
                this.town = town;
            }

            public String getAdcode() {
                return adcode;
            }

            public void setAdcode(String adcode) {
                this.adcode = adcode;
            }

            public String getStreet() {
                return street;
            }

            public void setStreet(String street) {
                this.street = street;
            }

            public String getStreet_number() {
                return street_number;
            }

            public void setStreet_number(String street_number) {
                this.street_number = street_number;
            }

            public String getDirection() {
                return direction;
            }

            public void setDirection(String direction) {
                this.direction = direction;
            }

            public String getDistance() {
                return distance;
            }

            public void setDistance(String distance) {
                this.distance = distance;
            }

            @Override
            public String toString() {
                return "AddressComponentBean{" +
                        "country='" + country + '\'' +
                        ", country_code=" + country_code +
                        ", country_code_iso='" + country_code_iso + '\'' +
                        ", country_code_iso2='" + country_code_iso2 + '\'' +
                        ", province='" + province + '\'' +
                        ", city='" + city + '\'' +
                        ", city_level=" + city_level +
                        ", district='" + district + '\'' +
                        ", town='" + town + '\'' +
                        ", adcode='" + adcode + '\'' +
                        ", street='" + street + '\'' +
                        ", street_number='" + street_number + '\'' +
                        ", direction='" + direction + '\'' +
                        ", distance='" + distance + '\'' +
                        '}';
            }
        }

        public static class PoiRegionsBean {
            /**
             * direction_desc : 内
             * name : 四川交投大厦
             * tag : 房地产;写字楼
             * uid : b7021bf837d2fdfbf4e73be9
             */

            private String direction_desc;
            private String name;
            private String tag;
            private String uid;

            public String getDirection_desc() {
                return direction_desc;
            }

            public void setDirection_desc(String direction_desc) {
                this.direction_desc = direction_desc;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getTag() {
                return tag;
            }

            public void setTag(String tag) {
                this.tag = tag;
            }

            public String getUid() {
                return uid;
            }

            public void setUid(String uid) {
                this.uid = uid;
            }

            @Override
            public String toString() {
                return "PoiRegionsBean{" +
                        "direction_desc='" + direction_desc + '\'' +
                        ", name='" + name + '\'' +
                        ", tag='" + tag + '\'' +
                        ", uid='" + uid + '\'' +
                        '}';
            }
        }
    }
}
