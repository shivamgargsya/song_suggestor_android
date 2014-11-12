package com.example.music;

import java.io.IOException;
import java.util.Map;

import android.app.Service;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnPreparedListener;
import android.media.TimedText;
import android.net.Uri;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.PowerManager;
import android.provider.MediaStore;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

public class Player extends Service implements OnPreparedListener,MediaPlayer.OnCompletionListener,MediaPlayer.OnErrorListener
,MediaPlayer.OnSeekCompleteListener{

	 private final IBinder mBinder = new LocalBinder();
	 private playback list;
	 private int current=0;
	 String [] art;
	 boolean streaming=false;
	@Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
		if(mplayer==null)
		{mplayer=new MediaPlayer();
		mplayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
		mplayer.setOnPreparedListener(this);
		mplayer.setOnCompletionListener(this);
		mplayer.setOnErrorListener(this);
		mplayer.setOnSeekCompleteListener(this);
		mplayer.setWakeMode(getApplicationContext(), PowerManager.PARTIAL_WAKE_LOCK);
		}
		return mBinder;
	}
	MediaPlayer mplayer;
	 @Override
	    public int onStartCommand(Intent intent, int flags, int startId) {
		 
		
		 return super.onStartCommand(intent,flags,startId);
	 }
	 
	 
	@Override
	public void onPrepared(MediaPlayer arg0) {
		// TODO Auto-generated method stub
		arg0.start();
		
	}
	@Override
	public void onCompletion(MediaPlayer mp) {
		// TODO Auto-generated method stub
		if(!streaming)
		{
			if(current<list.title.length)
			{
				current++;
				startit(current);
			}
			else 
				startit(0);
		}
		else
			streaming=false;
	}
	@Override
	public boolean onError(MediaPlayer mp, int what, int extra) {
		// TODO Auto-generated method stub
		return false;
	}
	
	@Override
	public void onSeekComplete(MediaPlayer mp) {
		// TODO Auto-generated method stub
		streaming=false;
		
	}
	public class LocalBinder extends Binder {
        Player getService() {
            // Return this instance of LocalService so clients can call public methods
            return Player.this;
        }
    }
	public void startit(int position)
	{	Uri contentUri;
		list=(playback)getApplicationContext();
		if(list.title.length>0)
		{
				if(position<list.title.length)
				{
						
						
						contentUri = list.songuri[position];
						
						
							
						mplayer.reset();
						if(contentUri!=null)
						{
							try {
								mplayer.setDataSource(getApplicationContext(), contentUri);
								mplayer.prepareAsync();
								current=position;
							} catch (IllegalArgumentException
									| SecurityException | IllegalStateException
									| IOException e) {
								// TODO Auto-generated catch block
								//e.printStackTrace();
							}
							
						}
				}
		}
		else
			startit(0);
	
	}
	public void start_streaming(Context c,Uri uri,Map<String,String>m)
	{
		mplayer.reset();
		try {
			mplayer.setDataSource(c,uri,m);
		} catch (IllegalArgumentException | SecurityException
				| IllegalStateException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			mplayer.prepareAsync();
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} // might take long! (for buffering, etc)
		
		streaming=true;
		
	}
	public boolean check()
	{
		return mplayer.isPlaying();
	}
	public void onDestroy()
	{
		mplayer.release();
	}
	public int getposition()
	{
		int pos=mplayer.getCurrentPosition();
		int dur=mplayer.getDuration();
		int val=pos*100/dur;
		return val;
		
	}
	public int getcurrentsong()
	{
		return current;
	}
}
