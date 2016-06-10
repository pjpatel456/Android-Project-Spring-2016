package com.example.android.moveyourthings.adapter;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.android.moveyourthings.R;
import com.example.android.moveyourthings.model.VideoListModel;

import java.io.File;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class VideoAdapter extends BaseAdapter implements OnClickListener {
	private Context mContext;
	private ArrayList<VideoListModel> videomodel = new ArrayList<VideoListModel>();
	private static LayoutInflater inflater = null;
	int loader;
	public Button cust;
	Dialog custom, custom_rename;
	EditText re_video_name;
	TextView txt;
	public TextView total_video_view, uploaded_video_view;
	private VideoAdapter video_adpter;
	private ListView list_video;

	public VideoAdapter(Context context,
						ArrayList<VideoListModel> videoListModel,
						VideoAdapter video_adpter_via, ListView list_video_via) {
		mContext = context;
		this.videomodel = videoListModel;
		inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        video_adpter = video_adpter_via;
		list_video = list_video_via;
	}

	@Override
	public int getCount() {
		return videomodel.size();
	}

	@Override
	public Object getItem(int position) {
		return videomodel.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	static class ViewHolder {
		TextView video_title_, upload_text_, time_text_, date_text_;
		ImageView img_profile, video_play;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;

		LayoutInflater mInflater = (LayoutInflater) mContext
				.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.video_adpter, null);
			holder = new ViewHolder();
            holder.video_title_ = (TextView) convertView
                    .findViewById(R.id.room_title);
			holder.time_text_ = (TextView) convertView
					.findViewById(R.id.time_text);
			holder.date_text_ = (TextView) convertView
					.findViewById(R.id.date_text);
			holder.img_profile = (ImageView) convertView
					.findViewById(R.id.video_image);
			holder.video_play = (ImageView) convertView
					.findViewById(R.id.video_play);
			holder.video_title_.setSelected(true);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		// object of model class

		VideoListModel videos_list = (VideoListModel) getItem(position);
		final String video_path = videos_list.getVideo_path();
		final String video_name = videos_list.getName();
		final String video_duration = videos_list.getVideo_duration();
		final String Local_id = videos_list.getUser_id();
		final String video_date = videos_list.getDatetime();
		final String video_status = videos_list.getUplodedStatus();
		final String video_thumbnail = videos_list.getVideo_thumb();

		byte[] decodedString = Base64.decode(video_thumbnail, Base64.DEFAULT);
		Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0,
				decodedString.length);

		holder.video_title_.setText(video_name);
		holder.time_text_.setText(video_duration);
		holder.date_text_.setText(video_date);
		holder.img_profile.setImageBitmap(decodedByte);

		holder.video_play.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Uri url_path;
				 Pattern pattern = Pattern.compile("://");
				    Matcher matcher = pattern.matcher(video_path);
				    if (matcher.find()){
				    	url_path = Uri.parse(video_path);
				    }else{
				    	File f = new File(video_path);
						url_path = Uri.fromFile(f);
				  } 				
				Intent intent = new Intent(android.content.Intent.ACTION_VIEW);
				intent.setDataAndType(url_path, "video/*");
				mContext.startActivity(intent);
			}
		});
		return convertView;
	}

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
	}
}
