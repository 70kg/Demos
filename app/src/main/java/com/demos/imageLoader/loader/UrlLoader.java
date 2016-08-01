package com.demos.imageLoader.loader;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.demos.imageLoader.core.BitmapRequest;
import com.demos.imageLoader.disklrucache.IOUtil;
import com.socks.library.KLog;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Mr_Wrong on 16/3/21.
 */
public class UrlLoader extends AbsLoader {
    @Override
    public Bitmap onLoadImage(BitmapRequest request) {
        final String imageUrl = request.imageUri;
        FileOutputStream fos = null;
        InputStream is = null;
        try {
            URL url = new URL(imageUrl);
            final HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            is = new BufferedInputStream(conn.getInputStream());
            is.mark(is.available());

//            final InputStream inputStream = is;
//            BitmapDecoder bitmapDecoder = new BitmapDecoder() {
//                @Override
//                public Bitmap decodeBitmapWithOption(BitmapFactory.Options options) {
//                    Bitmap bitmap = BitmapFactory.decodeStream(inputStream, null, options);
//                    if (options.inJustDecodeBounds) {
//                        try {
//                            inputStream.reset();
//                        } catch (IOException e) {
//                            e.printStackTrace();
//                        }
//                    } else {
//                        // 关闭流
//                        conn.disconnect();
//                    }
//                    return bitmap;
//                }
//            };
            return  BitmapFactory.decodeStream(is);
//            return bitmapDecoder.decodeBitmap(request.getImageViewWidth(),
//                    request.getImageViewHeight());
        } catch (Exception e) {
            KLog.e(e);
        } finally {
            IOUtil.closeQuietly(is);
            IOUtil.closeQuietly(fos);
        }

        return null;
    }
}
