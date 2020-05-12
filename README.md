# CdqjPatrolModel
智能巡检项目
# 使用方法
## 1. 项目build中添加realm及arcgis支持 
 `classpath "io.realm:realm-gradle-plugin:2.2.1"`

 `maven { url 'https://esri.bintray.com/arcgis' }`

## 2. APP build中添加最新版本
导入`implementation 'com.github.liuyifang1121:CdqjPatrolModel:1.0.002'` 最新版本

### 防止基础包冲突，android内添加

    configurations {
        cleanedAnnotations
        compile.exclude group: 'org.jetbrains' , module:'annotations'
    }

### 项目支持，android内其他配置

    android {
        defaultConfig {
            ...
            multiDexEnabled true
    
            ndk {
                abiFilters "armeabi", "armeabi-v7a", "x86", "mips"
            }

         }

        compileOptions {
            sourceCompatibility = '1.8'
            targetCompatibility = '1.8'
        }
    }
## 3. 本地项目application继承自巡检APP
`import com.cdqj.cdqjpatrolandroid.base.App`
## 4. 项目清单自行解决冲突，配置权限（定位，手机信息，拍照，存储卡等权限）等
`tools:replace="android:theme,android:name"`
## 5. 初始化相关
### 提供两种初始化方法(进入巡线主界面时调用)

参数为上下文对象，用户名，密码

`CdqjInitDataConfig.initPatrolData(Context context, String username, String psw);`

参数为上下文对象，请求地址，图片地址，用户名，密码

`CdqjInitDataConfig.initPatrolData(Context context, String requestUrl, String imgUrl, String userName, String psw);`

### 可配置参数
    /**
     * 常量标识
     * 判断是否是Lib库
     * 是否不需要登录界面及升级功能等
     * 默认是
     */
    public static boolean isLib = true;

    /**
     * 是否需要上下班 默认不需要
     */
    public static boolean isNeedWork = false;

## 导包较多，冲突自行解决
