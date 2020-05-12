package com.cdqj.cdqjpatrolandroid.utils;

import android.media.MediaPlayer;

import com.cdqj.cdqjpatrolandroid.base.App;

import java.io.IOException;

/**
 * Created by lyf on 2018/11/2 17:05
 *
 * @author lyf
 * desc：
 */
public class ComUtil {

    private static  MediaPlayer mediaPlayer;

    /**
     * 初始化MediaPlayer
     */
    private static void initMediaPlayer() {
        if (mediaPlayer == null)
            mediaPlayer = new MediaPlayer();
        // 设置音量，参数分别表示左右声道声音大小，取值范围为0~1
        mediaPlayer.setVolume(0.5f, 0.5f);
        // 设置是否循环播放
        mediaPlayer.setLooping(false);
    }

    /**
     * 语音提示
     *
     * @param raw            raw
     */
    public static void showSound(int raw) {
        MediaPlayer mediaPlayer = MediaPlayer.create(App.getInstance(), raw);
        mediaPlayer.start();
    }

    /**
     * 播放音频流
     *
     * @param url               url
     */
    public static void playNetAudio(String url) throws IOException {
        if (mediaPlayer == null) {
            initMediaPlayer();
        }
        // 重置mediaPlayer
        mediaPlayer.reset();
        // 方式一
        //    Uri uri = Uri.parse("http://ibooker.cc/ibooker/musics/sound_music.mp3");
        //    mediaPlayer.setDataSource(this, uri);
        // 方式二
        mediaPlayer.setDataSource(url);
        // 准备播放（同步）-预期准备，因为setDataSource()方法之后，MediaPlayer并未真正的去装载那些音频文件，需要调用prepare()这个方法去准备音频
        //  mediaPlayer.prepare();
        // 准备播放（异步）
        mediaPlayer.prepareAsync();
    }


    /**
     * 经纬度是否为(0,0)点
     *
     * @return boolean
     */
    public static boolean isZeroPoint(double latitude, double longitude) {
        return isEqualToZero(latitude) && isEqualToZero(longitude);
    }

    /**
     * 校验double数值是否为0
     *
     * @param value value
     * @return boolean
     */
    public static boolean isEqualToZero(double value) {
        return Math.abs(value - 0.0) < 0.01;
    }
}
