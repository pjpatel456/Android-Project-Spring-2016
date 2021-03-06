package com.example.android.moveyourthings.Utility;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.android.moveyourthings.model.VideoListModel;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class SqlDatabase extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;

    private static final String DATABASE_NAME = "MYT";

    private static final String video_table = "videos";
    private static final String id = "id";
    private static final String name = "name";
    private static final String video_path = "video_path";
    private static final String video_duration = "video_duration";
    private static final String video_thumb = "video_thumb";
    private static final String datetime = "datetime";
    private static final String user_id = "user_id";
    private static final String uplodedStatus = "status";

    private static final String CreateVideoTable = "CREATE TABLE IF NOT EXISTS "
            + video_table + "("

            + id + " INT PRIMARY KEY,"

            + name + " TEXT,"

            + video_path + " TEXT, "

            + video_duration + " TEXT, "

            + video_thumb + " TEXT, "

            + user_id + " TEXT, "

            + uplodedStatus + " TEXT, "

            + datetime + " DATETIME "

            + ")";


    public SqlDatabase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CreateVideoTable);
    }


    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + video_table);
        onCreate(db);
    }


    public void createDB() {
        SQLiteDatabase db = this.getWritableDatabase();
        onCreate(db);
    }


    public boolean saveVideo(String v_name, String v_path, String v_duration,
                             String v_thumb, String v_user_id, String v_status) {
        SQLiteDatabase db = this.getWritableDatabase();
        try {
            DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
            Date date = new Date();
            String timestamp = dateFormat.format(date);

            ContentValues values = new ContentValues();
            values.put(name, v_name);
            values.put(video_path, v_path);
            values.put(video_duration, v_duration);
            values.put(user_id, v_user_id);
            values.put(uplodedStatus, v_status);
            values.put(video_thumb, v_thumb);
            values.put(datetime, timestamp);

            db.insert(video_table, null, values);
            db.close();
            return true;
        } catch (Exception e) {
            return false;
        }
    }


    public ArrayList<VideoListModel> getVideoList() {
        ArrayList<VideoListModel> arrayList = new ArrayList<VideoListModel>();
        Cursor cursor = null;
        String selectQuery = "SELECT  * FROM " + video_table + " ORDER BY "+ datetime +" DESC ";

        SQLiteDatabase db = this.getWritableDatabase();
        cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                VideoListModel videoListModel = new VideoListModel();
                videoListModel.setName(cursor.getString(cursor
                        .getColumnIndex(name)));
                videoListModel.setVideo_path(cursor.getString(cursor
                        .getColumnIndex(video_path)));
                videoListModel.setVideo_duration(cursor.getString(cursor
                        .getColumnIndex(video_duration)));
                videoListModel.setDatetime(cursor.getString(cursor
                        .getColumnIndex(datetime)));
                videoListModel.setUser_id(cursor.getString(cursor
                        .getColumnIndex(user_id)));
                videoListModel.setUplodedStatus(cursor.getString(cursor
                        .getColumnIndex(uplodedStatus)));
                videoListModel.setVideo_thumb(cursor.getString(cursor
                        .getColumnIndex(video_thumb)));

                arrayList.add(videoListModel);

            } while (cursor.moveToNext());
        } else {
            VideoListModel ListModel = new VideoListModel();
        }
        cursor.close();
        db.close();
        return arrayList;
    }

}
