package haythem.hd.customprogress;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import haythem.hd.customprogress.Util.Utils;

public class DownloadPicActivity extends AppCompatActivity {

    private ImageView mImageView;
    private Button mLoadImage;
    private Button mLoadPicasso;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_download_pic);

        findByID();

        mLoadPicasso.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Picasso.with(getApplicationContext()).load(Constantes.URL_IMAGE).resize(mImageView.getWidth(), mImageView.getHeight()).into(mImageView);

            }
        });


        mLoadImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DownloadImageTask(mImageView)
                        .execute(Constantes.URL_IMAGE);
            }
        });
    }

    private void findByID() {
        mImageView = findViewById(R.id.img);
        mLoadImage = findViewById(R.id.charger);
        mLoadPicasso = findViewById(R.id.picasso_btn);
    }

    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;

        private DownloadImageTask(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        protected Bitmap doInBackground(String... urls) {
            Bitmap result = null;
            try {
                URL url = new URL(Constantes.URL_IMAGE);
                int height = bmImage.getHeight();
                int width = bmImage.getWidth();

                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                InputStream inputStream = httpURLConnection.getInputStream();

                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inJustDecodeBounds = true;
                BitmapFactory.decodeStream(inputStream, null, options);
                options.inJustDecodeBounds = false;

                options.inSampleSize = Utils.calculateInSampleSize(options, width, height);

                HttpURLConnection httpURLConnection1 = (HttpURLConnection) url.openConnection();
                InputStream inputStream1 = httpURLConnection1.getInputStream();

                result = BitmapFactory.decodeStream(inputStream1, null, options);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return result;
        }

        protected void onPostExecute(Bitmap result) {
            bmImage.setImageBitmap(result);
        }
    }


}
