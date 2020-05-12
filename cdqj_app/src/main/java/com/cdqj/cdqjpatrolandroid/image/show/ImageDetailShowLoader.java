package com.cdqj.cdqjpatrolandroid.image.show;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.support.v4.app.Fragment;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.load.resource.gif.GifDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.cdqj.cdqjpatrolandroid.image.glide.GlideImgManager;
import com.previewlibrary.loader.IZoomMediaLoader;
import com.previewlibrary.loader.MySimpleTarget;

/**
 * Created by lyf on 2018/5/24 09:38
 *
 * @author lyf
 * desc：图片加载
 */
public class ImageDetailShowLoader implements IZoomMediaLoader {

    @Override
    public void displayImage(Fragment context, String path, ImageView imageView, MySimpleTarget simpleTarget) {
        RequestOptions options = new RequestOptions()
                //.circleCrop()
                .placeholder(GlideImgManager.emptyImg)
                .error(GlideImgManager.errorImg)
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE);
        Glide.with(context)
                .asBitmap()
                .load(path)
                .apply(options)
                .listener(new RequestListener<Bitmap>() {
                    @Override
                    public boolean onLoadFailed(GlideException e, Object model, Target<Bitmap> target, boolean isFirstResource) {
                        simpleTarget.onLoadFailed(null);
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Bitmap resource, Object model, Target<Bitmap> target, DataSource dataSource, boolean isFirstResource) {
                        simpleTarget.onResourceReady();
                        return false;
                    }
                })
                .into(imageView);
    }

    @Override
    public void displayGifImage(Fragment context, String path, ImageView imageView, MySimpleTarget simpleTarget) {
        RequestOptions options = new RequestOptions()
                //.circleCrop()
                .placeholder(GlideImgManager.emptyImg)
                .error(GlideImgManager.errorImg)
                //去掉显示动画
                .dontAnimate()
                //可以解决gif比较几种时 ，加载过慢  //DiskCacheStrategy.NONE
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE);
        Glide.with(context)
                .asGif()
                .load(path)
                .apply(options)
                .listener(new RequestListener<GifDrawable>() {
                    @Override
                    public boolean onLoadFailed(GlideException e, Object model, Target<GifDrawable> target, boolean isFirstResource) {
                        simpleTarget.onLoadFailed(null);
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(GifDrawable resource, Object model, Target<GifDrawable> target, DataSource dataSource, boolean isFirstResource) {
                        simpleTarget.onResourceReady();
                        return false;
                    }
                })
                .into(imageView);
    }

    @Override
    public void onStop(Fragment context) {
        Glide.with(context).onStop();
    }

    @Override
    public void clearMemory(Context c) {
        Glide.get(c).clearMemory();
    }
}
