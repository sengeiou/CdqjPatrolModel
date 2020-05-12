package com.cdqj.cdqjpatrolandroid.utils;

import com.blankj.utilcode.util.ObjectUtils;
import com.blankj.utilcode.util.StringUtils;
import com.cdqj.cdqjpatrolandroid.bean.DicCacheDao;

import java.io.File;
import java.io.FileOutputStream;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * 字符串,数据相关判断
 *
 * @author lyf
 */
public class DataUtils {


    /**
     * List转换为String
     * @param lists lists
     * @return String
     */
    public static String listToString(List<String> lists) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < lists.size(); i++) {
            sb.append(lists.get(i));
            if (i != lists.size() - 1) {
                sb.append(",");
            }
        }
        return sb.toString();
    }


    /**
     * Copy 数据库
     */
    public static void copyfile(File fromFile, File toFile, Boolean rewrite) {
        if (!fromFile.exists()) {
            return;
        }
        if (!fromFile.isFile()) {
            return;
        }

        if (!fromFile.canRead()) {
            return;
        }
        if (!toFile.getParentFile().exists()) {
            toFile.getParentFile().mkdirs();
        }
        if (toFile.exists() && rewrite) {
            toFile.delete();
        }
        try {
            java.io.FileInputStream fosfrom = new java.io.FileInputStream(
                    fromFile);
            FileOutputStream fosto = new FileOutputStream(toFile);
            byte bt[] = new byte[1024];
            int c;
            while ((c = fosfrom.read(bt)) > 0) {
                // 将内容写到新文件当中
                fosto.write(bt, 0, c);
            }
            fosfrom.close();
            fosto.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * 根据业务标识获取业务字符串
     *
     * @param busType 业务标识
     * @param sonType 子类型标识
     * @return 业务字符串
     */
    public static String getBusinessStr(String busType, String sonType) {
        String busTypeStr = "001".endsWith(busType) ? "挂表勘察" : "002".endsWith(busType) ? "挂表业务" :
                "003".endsWith(busType) ? "非勘察挂表业务" :
                        "004".endsWith(busType) ? "启封通气" :
                                "005".endsWith(busType) ? "改造勘察" :
                                        "006".endsWith(busType) ? "现场改造" :
                                                "007".endsWith(busType) ? "换表维修" :
                                                        "008".endsWith(busType) ? "非换表维修" : // 以上华油业务
                                                                "009".endsWith(busType) ? "暗埋业务" : "不锈钢管安装业务";// 成燃业务
        // 1 勘察 2 布管 3 试压
        String sonTypeStr = "1".equals(sonType) ? "勘察" : "2".equals(sonType) ? "布管" : "3".equals(sonType) ? "试压" : "";
        return busTypeStr + (StringUtils.isTrimEmpty(sonTypeStr) ? "" : ("(" + sonTypeStr + ")"));
    }

    /**
     * 根据管线标识获取管线类型
     *
     * @param lineTyp 标识
     * @return 管线类型串
     */
    public static String getLineTypeStr(Object lineTyp, Realm realm) {
        /*
         * 管线[301501: 中压B管线] , [301401: 中压A管线] , [301601: 低压管线],
         *      阀门[302001: 闸阀] , [302002: 蝶阀], [302003: 球阀]
         *      调压设备[303001: 调压箱] , [303002: 调压站] , [303003: 调压柜]
         *      极性保护[306003: 牺牲阳极]
         *      气源[308001: 门站] , [308002: 气源站] , [308003: 储配站]
         *      监测点[309001: RTU监测点]
         *      引入管[307001: 立管] , [307002: 测试]
         *      节点 [305002: 四通] , [305003: 变径] , [305004: 弯头]
         *      [301101: 高压A管线] , [301201: 高压B管线] , [301301: 次高压管线]
         *      调压设备[303001: 调压箱] , [303002: 调压站] , [303003: 调压柜]
         *      阀门[302001: 闸阀] , [302002: 蝶阀] , [302003: 球阀]
         *      其他
         */
        String lineTypStr = "未知";
        if (ObjectUtils.isEmpty(lineTyp)) {
            return lineTypStr;
        }
        if (!StringUtils.isTrimEmpty(lineTyp.toString())){
            RealmResults<DicCacheDao> postResponse = realm.where(DicCacheDao.class)
                    .equalTo("moduleCode", "PAT_LINE").findAll();
            if (ObjectUtils.isEmpty(postResponse)) {
                return "";
            }
            for (DicCacheDao dao : postResponse) {
                if (lineTyp.equals(dao.getDicCode())) {
                    lineTypStr = dao.getDicName();
                    return lineTypStr;
                }
            }
        }
        return lineTypStr;
    }

    /**
     * 获取当前域并判断域ID
     * @param doMainName 域名字
     * @return 自定义域ID
     *
     * 泸州ID 1
     * 1	3			2019/6/17 10:09:02
     * 2	1	SYSTEM	系统管理	2018/5/23 11:03:02
     * 3	2	泸州华润兴泸燃气	燃气公司	2018/12/13 16:57:27
     * 4	6681	合江星焰燃气		2019/6/17 10:09:09
     *
     * 其他
     * 大同，绵阳等
     */
    public static int getDoMainId(String doMainName) {
        int id = 0;
        if ("纳溪兴燃燃气公司".equals(doMainName) || "泸州华润兴泸燃气".equals(doMainName)
                || "合江星焰燃气".equals(doMainName)) {
            id = 1;
        }
        return id;
    }
}
