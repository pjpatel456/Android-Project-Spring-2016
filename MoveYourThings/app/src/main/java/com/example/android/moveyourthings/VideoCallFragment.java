package com.example.android.moveyourthings;

import android.graphics.Point;
import android.hardware.Camera;
import android.media.AudioManager;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.android.moveyourthings.Utility.Constant;

import java.util.Date;

import sg.com.temasys.skylink.sdk.config.SkylinkConfig;
import sg.com.temasys.skylink.sdk.listener.LifeCycleListener;
import sg.com.temasys.skylink.sdk.listener.MediaListener;
import sg.com.temasys.skylink.sdk.listener.RemotePeerListener;
import sg.com.temasys.skylink.sdk.rtc.SkylinkConnection;
import sg.com.temasys.skylink.sdk.rtc.UserInfo;

public class VideoCallFragment extends Fragment implements LifeCycleListener, MediaListener, RemotePeerListener {
    private static final String TAG = VideoCallFragment.class.getCanonicalName();
    public static final String ROOM_NAME = Constant.user_id;
    public static final String MY_USER_NAME = "videoCallUser";
    private static final String ARG_SECTION_NUMBER = "section_number";
    //set height width for self-video when in call
    public static final int WIDTH = 500;
    public static final int HEIGHT = 300;
    private LinearLayout parentFragment;
    private Button toggleAudioButton;
    private Button toggleVideoButton;
    private Button btnEnterRoom;
    private EditText etRoomName;
    private SkylinkConnection skylinkConnection;
    private String roomName;
    private String peerId;
    private ViewGroup.LayoutParams selfLayoutParams;
    private boolean audioMuted;
    private boolean videoMuted;
    private boolean connected;
    private Camera mCamera;

    @Override
    public void onPause() {
        // TODO Auto-generated method stub
        super.onPause();
        if (mCamera != null) {
            mCamera.stopPreview();
            mCamera.release();
            mCamera = null;
        }
        skylinkConnection.disconnectFromRoom();
        skylinkConnection.setLifeCycleListener(null);
        skylinkConnection.setMediaListener(null);
        skylinkConnection.setRemotePeerListener(null);
        connected = false;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Initialize views
        View rootView = inflater.inflate(R.layout.video_call, container, false);
        parentFragment = (LinearLayout) rootView.findViewById(R.id.ll_video_call);



                    roomName = ROOM_NAME;

                String appKey = getString(R.string.app_key);
                String appSecret = getString(R.string.app_secret);

                // Initialize the skylink connection
                initializeSkylinkConnection();

                // Obtaining the Skylink connection string done locally
                // In a production environment the connection string should be given
                // by an entity external to the App, such as an App server that holds the Skylink App secret
                // In order to avoid keeping the App secret within the application

        String skylinkConnectionString = Utils.
                getSkylinkConnectionString(roomName, appKey,
                        appSecret, new Date(), SkylinkConnection.DEFAULT_DURATION);
                skylinkConnection.connectToRoom(skylinkConnectionString,
                        MY_USER_NAME);

        connected = true;
        return rootView;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Allow volume to be controlled using volume keys
        getActivity().setVolumeControlStream(AudioManager.STREAM_VOICE_CALL);
    }


    private void initializeSkylinkConnection() {
        if (skylinkConnection == null) {
            skylinkConnection = SkylinkConnection.getInstance();
            //the app_key and app_secret is obtained from the temasys developer console.
            skylinkConnection.init(getString(R.string.app_key),
                    getSkylinkConfig(), this.getActivity().getApplicationContext());
            //set listeners to receive callbacks when events are triggered
            skylinkConnection.setLifeCycleListener(this);
            skylinkConnection.setMediaListener(this);
            skylinkConnection.setRemotePeerListener(this);
        }
    }

    private SkylinkConfig getSkylinkConfig() {
        SkylinkConfig config = new SkylinkConfig();
        //AudioVideo config options can be NO_AUDIO_NO_VIDEO, AUDIO_ONLY, VIDEO_ONLY, AUDIO_AND_VIDEO;
        config.setAudioVideoSendConfig(SkylinkConfig.AudioVideoConfig.AUDIO_AND_VIDEO);
        config.setAudioVideoReceiveConfig(SkylinkConfig.AudioVideoConfig.AUDIO_AND_VIDEO);
        return config;
    }

    @Override
    public void onDetach() {
        //close the connection when the fragment is detached, so the streams are not open.
        super.onDetach();
        if (skylinkConnection != null && connected) {
            skylinkConnection.disconnectFromRoom();
            skylinkConnection.setLifeCycleListener(null);
            skylinkConnection.setMediaListener(null);
            skylinkConnection.setRemotePeerListener(null);
            connected = false;
        }
    }

    /***
     * Lifecycle Listener Callbacks -- triggered during events that happen during the SDK's lifecycle
     */

    /**
     * Triggered when connection is successful
     *
     * @param isSuccess
     * @param message
     */

    @Override
    public void onConnect(boolean isSuccess, String message) {
        if (isSuccess) {
            Toast.makeText(getActivity(), "Connected to room " + roomName + " as " + MY_USER_NAME, Toast.LENGTH_SHORT).show();
        } else {
            Log.e(TAG, "Skylink Failed " + message);
            Toast.makeText(getActivity(), "Skylink Connection Failed\nReason : "
                    + message, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onLockRoomStatusChange(String remotePeerId, boolean lockStatus) {
        Toast.makeText(getActivity(), "Peer " + remotePeerId +
                " has changed Room locked status to " + lockStatus, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onWarning(int errorCode, String message) {
        Log.d(TAG, message + "warning");
        Toast.makeText(getActivity(), "Warning is errorCode" + errorCode, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDisconnect(int errorCode, String message) {
        Log.d(TAG, message + " disconnected");
        Toast.makeText(getActivity(), "onDisconnect " + message, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onReceiveLog(String message) {
        Log.d(TAG, message + " on receive log");
    }

    /**
     * Media Listeners Callbacks - triggered when receiving changes to Media Stream from the remote peer
     */

    /**
     * Triggered after the user's local media is captured.
     *
     * @param videoView
     */
    @Override
    public void onLocalMediaCapture(GLSurfaceView videoView) {
        if (videoView != null) {
            View self = parentFragment.findViewWithTag("self");
            videoView.setTag("self");
            // Allow self view to switch between different cameras (if any) when tapped.
            videoView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    skylinkConnection.switchCamera();
                }
            });

            if (self == null) {
                //show media on screen
                parentFragment.removeView(videoView);
                parentFragment.addView(videoView);
            } else {
                videoView.setLayoutParams(self.getLayoutParams());

                // If peer video exists, remove it first.
                View peer = parentFragment.findViewWithTag("peer");
                if (peer != null) {
                    parentFragment.removeView(peer);
                }

                // Remove the old self video and add the new one.
                parentFragment.removeView(self);
                parentFragment.addView(videoView);

                // Return the peer video, if it was there before.
                if (peer != null) {
                    parentFragment.addView(peer);
                }
            }

        }
    }

    @Override
    public void onVideoSizeChange(String peerId, Point size) {
        Log.d(TAG, "PeerId: " + peerId + " got size " + size.toString());
    }

    @Override
    public void onRemotePeerAudioToggle(String remotePeerId, boolean isMuted) {
        String message = null;
        if (isMuted) {
            message = "Your peer muted their audio";
        } else {
            message = "Your peer unmuted their audio";
        }

        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRemotePeerVideoToggle(String peerId, boolean isMuted) {
        String message = null;
        if (isMuted)
            message = "Your peer muted video";
        else
            message = "Your peer unmuted their video";

        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
    }

    /**
     * Remote Peer Listener Callbacks - triggered during events that happen when data or connection
     * with remote peer changes
     */

    @Override
    public void onRemotePeerJoin(String remotePeerId, Object userData, boolean hasDataChannel) {
        Toast.makeText(getActivity(), "Your peer has just connected", Toast.LENGTH_SHORT).show();
        UserInfo remotePeerUserInfo = skylinkConnection.getUserInfo(remotePeerId);
        Log.d(TAG, "isAudioStereo " + remotePeerUserInfo.isAudioStereo());
        Log.d(TAG, "video height " + remotePeerUserInfo.getVideoHeight());
        Log.d(TAG, "video width " + remotePeerUserInfo.getVideoHeight());
        Log.d(TAG, "video frameRate " + remotePeerUserInfo.getVideoFps());
    }

    @Override
    public void onRemotePeerMediaReceive(String remotePeerId, GLSurfaceView videoView) {
        if (videoView == null) {
            return;
        }

        if (!TextUtils.isEmpty(this.peerId) && !remotePeerId.equals(this.peerId)) {
            Toast.makeText(getActivity(), " You are already in connection with two peers",
                    Toast.LENGTH_SHORT).show();
            return;
        }

        // Resize self view
        View self = parentFragment.findViewWithTag("self");
        if (this.selfLayoutParams == null) {
            // Record the original size of the layout
            this.selfLayoutParams = self.getLayoutParams();
        }

        self.setLayoutParams(new ViewGroup.LayoutParams(WIDTH, HEIGHT));
        parentFragment.removeView(self);
        parentFragment.addView(self);

        // Remove previous peer video if it exist
        View viewToRemove = parentFragment.findViewWithTag("peer");
        if (viewToRemove != null) {
            parentFragment.removeView(viewToRemove);
        }

        // Add new peer video
        videoView.setTag("peer");
        parentFragment.addView(videoView);

        this.peerId = remotePeerId;
    }

    @Override
    public void onRemotePeerLeave(String remotePeerId, String message) {
        Toast.makeText(getActivity(), "Your peer has left the room", Toast.LENGTH_SHORT).show();
        if (remotePeerId != null && remotePeerId.equals(this.peerId)) {
            this.peerId = null;
            View peerView = parentFragment.findViewWithTag("peer");
            parentFragment.removeView(peerView);

            // Resize self view to original size
            if (this.selfLayoutParams != null) {
                View self = parentFragment.findViewWithTag("self");
                self.setLayoutParams(selfLayoutParams);
            }
        }
    }

    @Override
    public void onRemotePeerUserDataReceive(String remotePeerId, Object userData) {
        Log.d(TAG, "onRemotePeerUserDataReceive " + remotePeerId);
    }

    @Override
    public void onOpenDataConnection(String peerId) {
        Log.d(TAG, "onOpenDataConnection");
    }
}
