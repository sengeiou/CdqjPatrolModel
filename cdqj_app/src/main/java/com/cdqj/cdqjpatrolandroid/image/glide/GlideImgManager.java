package com.cdqj.cdqjpatrolandroid.image.glide;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.cdqj.cdqjpatrolandroid.R;

import java.io.File;


public class GlideImgManager {

    public static int emptyImg = R.mipmap.ic_image_empty;
    public static int errorImg = R.mipmap.ic_image_empty;

    static RequestOptions options = new RequestOptions()
            //.circleCrop()
            .placeholder(emptyImg)
            .error(errorImg);

    /**
     * 原生 API
     *
     * @param context  context
     * @param url      url
     * @param errorImg errorImg
     * @param emptyImg emptyImg
     * @param iv       iv
     */
    public static void loadImage(Context context, String url, int errorImg, int emptyImg, ImageView iv) {
        //原生 API
        RequestOptions optionsTo = new RequestOptions().placeholder(emptyImg).error(errorImg);
        Glide.with(context).load(url).apply(optionsTo).into(iv);
    }

    /**
     * 加载网络图片（普通）
     *
     * @param context context
     * @param url     url
     * @param iv      iv
     */
    public static void loadImage(Context context, String url, ImageView iv) {
        //原生 API
        Glide.with(context).load(url).apply(options).into(iv);
    }

    /**
     * 加载网络图片（GIF）
     *
     * @param context context
     * @param url     url
     * @param iv      iv
     */
    public static void loadGifImage(Context context, String url, ImageView iv) {
        RequestOptions optionsTo = new RequestOptions()
                .placeholder(emptyImg)
                .error(errorImg)
                .diskCacheStrategy(DiskCacheStrategy.ALL);
        Glide.with(context).asGif().load(url).apply(optionsTo).into(iv);
    }

    /**
     * 加载网络图片（圆形）
     *
     * @param context context
     * @param url     url
     * @param iv      iv
     */
    public static void loadCircleImage(Context context, String url, ImageView iv) {
        loadCircleImage(context, url, iv, 1F);
    }

    /**
     * 加载网络图片（圆形） 缩略图
     *
     * @param context    context
     * @param url        url
     * @param iv         iv
     * @param thumbnails 缩略图倍数
     */
    public static void loadCircleImage(Context context, String url, ImageView iv, float thumbnails) {
        RequestOptions optionsTo = new RequestOptions()
                .placeholder(emptyImg)
                .error(errorImg)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .transform(new GlideCircleTransform(context));
        Glide.with(context).load(url).apply(options).thumbnail(thumbnails).apply(optionsTo).into(iv);
    }

    /**
     * 加载本地图片（圆形）
     *
     * @param context context
     * @param url     url
     * @param iv      iv
     */
    public static void loadCircleImageForResourse(Context context, int url, ImageView iv) {
        RequestOptions optionsTo = new RequestOptions()
                .placeholder(emptyImg)
                .error(errorImg)
                .transform(new GlideCircleTransform(context));
        Glide.with(context).load(url).apply(optionsTo).into(iv);
    }

    /**
     * 加载网络图片（圆角）
     *
     * @param context context
     * @param url     url
     * @param iv      iv
     * @param round   round
     */
    public static void loadRoundCornerImage(Context context, String url, ImageView iv, int round) {
        loadRoundCornerImage(context, url, iv, round, 1F);
    }

    /**
     * 加载网络图片（圆角）-缩略图
     *
     * @param context    context
     * @param url        url
     * @param iv         iv
     * @param round      round
     * @param thumbnails 缩略图比例
     */
    public static void loadRoundCornerImage(Context context, String url, ImageView iv, int round, float thumbnails) {
        RequestOptions optionsTo = new RequestOptions()
                .placeholder(emptyImg)
                .error(errorImg)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .transform(new GlideRoundTransform(context, round));
        Glide.with(context).load(url).thumbnail(thumbnails).apply(optionsTo).into(iv);
    }


    /**
     * 加载资源图片（圆角）
     *
     * @param context context
     * @param resId   resId
     * @param iv      iv
     * @param round   round
     */
    public static void loadRoundCornerImage(Context context, int resId, ImageView iv, int round) {
        RequestOptions optionsTo = new RequestOptions()
                .centerCrop()
                .placeholder(emptyImg)
                .error(errorImg)
                .transform(new GlideRoundTransform(context, round));
        Glide.with(context).load(resId).apply(optionsTo).into(iv);
    }

    /**
     * 加载资源图片（圆角）-指定宽高
     *
     * @param context context
     * @param resId   resId
     * @param iv      iv
     * @param round   round
     */
    public static void loadRoundCornerImage(Context context, int resId, ImageView iv, int round, int width, int height) {
        RequestOptions optionsTo = new RequestOptions()
                .centerCrop()
                .placeholder(emptyImg)
                .error(errorImg)
                .override(width, height)
                .transform(new GlideRoundTransform(context, round));
        Glide.with(context).load(resId).apply(optionsTo).into(iv);
    }

    /**
     * 加载本地图片（圆角）
     *
     * @param context context
     * @param file    file
     * @param iv      iv
     * @param round   round
     */
    public static void loadRoundCornerImageForFile(Context context, File file, ImageView iv, int round) {
        RequestOptions optionsTo = new RequestOptions()
                .placeholder(emptyImg)
                .error(errorImg)
                .transform(new GlideRoundTransform(context, round));
        Glide.with(context).load(file).apply(optionsTo).into(iv);
    }

    /**
     * 加载本地图片
     *
     * @param context   context
     * @param file      file
     * @param imageView imageView
     */
    public static void loadImage(Context context, final File file, final ImageView imageView) {
        Glide.with(context)
                .load(file)
                .into(imageView);
    }

    /**
     * 加载项目中的图片
     *
     * @param context    context
     * @param resourceId resourceId
     * @param imageView  imageView
     */
    public static void loadImage(Context context, final int resourceId, final ImageView imageView) {
        Glide.with(context)
                .load(resourceId)
                .into(imageView);
    }

    /**
     * 是否禁止磁盘缓存加载图片
     *
     * @param url       url
     * @param context   context
     * @param imageView imageView
     * @param type      缓存的类型
     *                  <li>磁盘缓存全部 DiskCacheStrategy.ALL</li>
     *                  <li>磁盘禁止缓存DiskCacheStrategy.NONE</li>
     */
    public static void loadImage(String url, Context context, ImageView imageView, DiskCacheStrategy type) {
        RequestOptions optionsTo = new RequestOptions()
                .placeholder(emptyImg)
                .error(errorImg)
                .diskCacheStrategy(type);
        Glide.with(context).load(url).apply(optionsTo).into(imageView);
    }

    /**
     * 是否禁止内存缓存加载图片
     *
     * @param url             url
     * @param context         context
     * @param imageView       imageView
     * @param skipMemoryCache 禁止内存缓存 true为禁止
     */
    public static void loadImage(String url, Context context, ImageView imageView, boolean skipMemoryCache) {
        RequestOptions optionsTo = new RequestOptions()
                .placeholder(emptyImg)
                .error(errorImg)
                .skipMemoryCache(skipMemoryCache);
        Glide.with(context).load(url).apply(optionsTo).into(imageView);
    }

    /**
     * 是否禁止内存/磁盘缓存加载图片
     *
     * @param url             url
     * @param context         context
     * @param imageView       imageView
     * @param type            缓存的类型
     *                        <li>磁盘缓存全部 DiskCacheStrategy.ALL</li>
     *                        <li>磁盘禁止缓存DiskCacheStrategy.NONE</li>
     * @param skipMemoryCache 禁止内存缓存 true为禁止
     */
    public static void loadImage(String url, Context context, ImageView imageView, DiskCacheStrategy type,
                                 boolean skipMemoryCache) {
        RequestOptions optionsTo = new RequestOptions()
                .placeholder(emptyImg)
                .error(errorImg)
                .skipMemoryCache(skipMemoryCache)
                .diskCacheStrategy(type);
        Glide.with(context).load(url).apply(optionsTo).into(imageView);
    }

    /**
     * 清除内存中的缓存 必须在UI线程中调用
     *
     * @param context context
     */
    public static void clearMemory(Context context) {
        Glide.get(context).clearMemory();
    }

    /**
     * 清除磁盘中的缓存 必须在后台线程中调用，建议同时clearMemory()
     *
     * @param context context
     */
    public static void clearDiskCache(Context context) {
        Glide.get(context).clearDiskCache();
    }

    /**
     * 优先级加载图片
     *
     * @param url       url
     * @param context   context
     * @param imageView imageView
     * @param priority  优先级  Priority.LOW/Priority.HIGH
     */
    public static void loadImageWithPriority(String url, Context context, ImageView imageView, Priority priority) {
        RequestOptions optionsTo = new RequestOptions()
                .placeholder(emptyImg)
                .error(errorImg)
                .priority(priority);
        Glide.with(context).load(url).apply(optionsTo).into(imageView);
    }
} 