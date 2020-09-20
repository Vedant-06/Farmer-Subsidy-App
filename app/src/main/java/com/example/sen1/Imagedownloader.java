package com.example.sen1;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;
import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.net.URL;

public class Imagedownloader extends AsyncTask<String,Void, Bitmap>
{
    private final WeakReference<ImageView> imageViewReference;

    Imagedownloader(ImageView imageView) {
        imageViewReference = new WeakReference<>(imageView);
    }

    protected void onPostExecute(Bitmap bitmap)
    {
        if(isCancelled()){
            bitmap = null;
        }
        ImageView imageView = imageViewReference.get();
        if(imageView != null)
        {
            if(bitmap != null)
            {
                imageView.setImageBitmap(bitmap);
            }
        }
    }

    @Override
    protected Bitmap doInBackground(String... strings) {
        return downloadBitmap(strings[0]);
    }

    private Bitmap downloadBitmap(String url)
    {
        HttpURLConnection urlConnection = null;
        try{

            URL uri = new URL(url);
            urlConnection = (HttpURLConnection) uri.openConnection();
            int statusCode = urlConnection.getResponseCode();
            if(statusCode != HttpURLConnection.HTTP_OK){
                return null;
            }
            InputStream inputStream = urlConnection.getInputStream();
            if(inputStream != null){
                return BitmapFactory.decodeStream(inputStream);
            }
        }
        catch (Exception e){
            if (null == urlConnection) {
            } else {
                urlConnection.disconnect();
            }
        } finally {
            if(urlConnection != null){
                urlConnection.disconnect();
            }
        }
        return null;
    }

}
