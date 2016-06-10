package com.example.android.moveyourthings;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.hardware.Camera;
import android.media.MediaPlayer;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.TextView;

import com.example.android.moveyourthings.Utility.Constant;
import com.example.android.moveyourthings.Utility.SqlDatabase;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.concurrent.TimeUnit;


public class Home extends Fragment{

    Context mContext;
    static final int REQUEST_VIDEO_CAPTURE = 1;
    public TextView user_id_text;
    private Uri fileUri;
    public String filepath;
    byte[] byteArray;
    byte[] inputDatavideo;
    public String video_duration;
    SqlDatabase sql;
    private Camera mCamera;

    public Home() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        if (mCamera != null){
            mCamera.release();        // release the camera for other applications
            mCamera = null;
        }
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.home, container, false);
        mContext = view.getContext();
        user_id_text = (TextView) view.findViewById(R.id.user_id);
        user_id_text.setText(Constant.user_id);
        sql = new SqlDatabase(getActivity());

        Button record = (Button) view.findViewById(R.id.recoding);


        record.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, CameraActivity.class);
                startActivityForResult(intent,
                        REQUEST_VIDEO_CAPTURE);
            }
        });


        return view;
    }

    // http://stackoverflow.com/questions/34915150/how-to-convert-bitmap-to-base64-string
    public String encodeTobase64(Bitmap image) {
        Bitmap immagex = image;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        immagex.compress(Bitmap.CompressFormat.JPEG, 10, baos);
        Bitmap.createScaledBitmap(immagex, 40, 40, true);
        byte[] b = baos.toByteArray();
        String imageEncoded = Base64.encodeToString(b, Base64.DEFAULT);
        return imageEncoded;
    }


    public String getDuration(String filepath){
        MediaPlayer mp = MediaPlayer.create(getActivity(), Uri.parse(filepath));
        int duration = mp.getDuration();
        mp.release();
        return String.format("%d min, %d sec",
                TimeUnit.MILLISECONDS.toMinutes(duration),
                TimeUnit.MILLISECONDS.toSeconds(duration) -
                        TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(duration))
        );
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode,
                                    final Intent data) {
        if (data == null) {
            return;
        }
        if (requestCode == 1) {

            String result = data.getStringExtra("result");
            Log.i("result", result);
            fileUri = Uri.parse("file:///mnt" + result);
            filepath = result;
            video_duration = getDuration(result);

            String v_file_name = filepath.substring(filepath
                            .lastIndexOf("/") + 1);
            String ext = MimeTypeMap.getFileExtensionFromUrl(filepath);

            try {
                ByteReadAndWrite bytearraydata = new ByteReadAndWrite();
                inputDatavideo = bytearraydata.readbytedata(filepath,
                                100000);
            } catch (IOException e) {
                 // TODO Auto-generated catch block
                 e.printStackTrace();
            }

            final Bitmap thumbnail = ThumbnailUtils
                            .createVideoThumbnail(filepath,
                                    MediaStore.Video.Thumbnails.MINI_KIND);
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            thumbnail.compress(Bitmap.CompressFormat.PNG, 100, stream);
            byteArray = stream.toByteArray();

            String image_encode_data = encodeTobase64(thumbnail);

            Boolean saveVideo = sql.saveVideo(
                    v_file_name, filepath, video_duration, image_encode_data, Constant.user_id, "0");
            if (saveVideo) {
                Intent intent = new Intent(mContext,
                        UserPage.class);
                mContext.startActivity(intent);
                ((Activity) mContext).finish();
            }


        }
    }

}