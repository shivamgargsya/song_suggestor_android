package com.example.music;

import android.annotation.TargetApi;
import android.app.ActionBar;
import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;


import com.example.music.Player.LocalBinder;

@TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
public class MainActivity extends FragmentActivity implements android.app.ActionBar.TabListener {

	 private ViewPager viewPager;
	    private TabsPagerAdapter mAdapter;
	    private android.app.ActionBar actionBar;
	    private String[] tabs = { "Songs", "Albums", "Artists" };
	    int progressStatus=0;;
	    private Handler handler = new Handler();

	   private ProgressBar progressBar=null;
	   private int current=0;
	   private int position=0;
	   
	   
	  
	    

	    
	    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		 if (Build.VERSION.SDK_INT < 16) {
	            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
	                    WindowManager.LayoutParams.FLAG_FULLSCREEN);
	        }
		 View decorView = getWindow().getDecorView();
		// Hide the status bar.
		int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
	
		
		progressBar = (ProgressBar) findViewById(R.id.progressBar1);
		decorView.setSystemUiVisibility(uiOptions);
	
		
				decorView.setOnSystemUiVisibilityChangeListener
		        (new View.OnSystemUiVisibilityChangeListener() {
		    @Override
		    public void onSystemUiVisibilityChange(int visibility) {
		        // Note that system bars will only be "visible" if none of the
		        // LOW_PROFILE, HIDE_NAVIGATION, or FULLSCREEN flags are set.
		        if ((visibility & View.SYSTEM_UI_FLAG_FULLSCREEN) == 0) {
		        	 View decorView = getWindow().getDecorView();
		        	 int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
		        	decorView.setSystemUiVisibility(uiOptions);
		        	actionBar.show();
		        	
		        } else {
		            // TODO: The system bars are NOT visible. Make any desired
		            // adjustments to your UI, such as hiding the action bar or
		            // other navigational controls.
		        	 View decorView = getWindow().getDecorView();
		        	 int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
		        	decorView.setSystemUiVisibility(uiOptions);
		        	
		        }
		    }
		});
		//controlling the functionality of UI buttons
		findViewById(R.id.btnPlay).setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Player p=(Player)mService;
				if(p.mplayer.isPlaying())
				{
					try
					{
						p.mplayer.pause();
						ImageButton im=(ImageButton)findViewById(R.id.btnPlay);
						im.setImageResource(R.drawable.play_button);
					}
					catch(Exception e)
					{
						
					}
					
					
				}
				else
				{
					try
					{
						p.mplayer.start();
						ImageButton im=(ImageButton)findViewById(R.id.btnPlay);
						im.setImageResource(R.drawable.pause_button);
					}
					catch(Exception e)
					{
						
					}
				}
				
			}
		});


		/*if (savedInstanceState == null) {
			getSupportFragmentManager().beginTransaction()
					.add(R.id.container, new PlaceholderFragment()).commit();
		}*/
		 viewPager = (ViewPager) findViewById(R.id.pager);
	        actionBar = getActionBar();
	        mAdapter = new TabsPagerAdapter(getSupportFragmentManager());
	 
	        viewPager.setAdapter(mAdapter);
	        actionBar.setHomeButtonEnabled(false);
	        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
	        for (String tab_name : tabs) {
	            actionBar.addTab(actionBar.newTab().setText(tab_name)
	                    .setTabListener((android.app.ActionBar.TabListener) this));
	        }
	        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
	        	 
	            @Override
	            public void onPageSelected(int position) {
	                // on changing the page
	                // make respected tab selected
	                actionBar.setSelectedNavigationItem(position);
	            }
	         
	            @Override
	            public void onPageScrolled(int arg0, float arg1, int arg2) {
	            }
	         
	            @Override
	            public void onPageScrollStateChanged(int arg0) {
	            }
	        }); 
	       /* actionBar.setDisplayShowHomeEnabled(false);              
	        actionBar.setDisplayShowTitleEnabled(false);*/
	        //progressbar....
	        
	       ImageButton b1=(ImageButton)findViewById(R.id.song_art);
	       b1.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent i = new Intent(MainActivity.this,SecondActivity.class);
				TextView tv=(TextView)findViewById(R.id.song_name);
				
				i.putExtra("song",(CharSequence)tv.getText());
				startActivity(i);
				
			}
		});
	       
	        
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		/*getMenuInflater().inflate(R.menu.main, menu);*/
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	

	@Override
	public void onTabReselected(android.app.ActionBar.Tab tab,
			android.app.FragmentTransaction ft) {
		// TODO Auto-generated method stub
	
		//sdfasdf
		
	}

	@Override
	public void onTabSelected(android.app.ActionBar.Tab tab,
			android.app.FragmentTransaction ft) {
		// TODO Auto-generated method stub
		 viewPager.setCurrentItem(tab.getPosition());
		
	}

	@Override
	public void onTabUnselected(android.app.ActionBar.Tab tab,
			android.app.FragmentTransaction ft) {
		// TODO Auto-generated method stub
		
	}
	
	
	 /** Defines callbacks for service binding, passed to bindService() */
	
	private Boolean mBound;
    public  Service mService;
    private ServiceConnection mConnection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName className,
                IBinder service) {
            // We've bound to LocalService, cast the IBinder and get LocalService instance
            LocalBinder binder = (LocalBinder) service;
            mService = binder.getService();
            mBound = true;
            
            //*******************updating the progress bar....*********************
            new Thread(new Runnable() {
             	
                 public void run() {
                 	
                 	final playback list=(playback)getApplicationContext();
                 	ImageButton but=(ImageButton)findViewById(R.id.btnPlay);
      				but.setImageResource(R.drawable.pause_button);
      				ImageView im=(ImageView)findViewById(R.id.song_art);
      				String s=null;
      				if(list.songart!=null)
      				s=list.songart[current];
      				if(s!=null)
      				{
      					Bitmap bitmap;
      					BitmapFactory.Options option = new BitmapFactory.Options();
      					bitmap=BitmapFactory.decodeFile(s, option);
      					Bitmap bit=Bitmap.createScaledBitmap(bitmap, 56,56,false);
      					im.setImageBitmap(bit);
      				}
      				else
      				{
      					im.setImageResource(R.drawable.icon_tumblr);
      				}
      				
      				TextView tv=(TextView)findViewById(R.id.song_name);
      				if(list.title!=null)
      				tv.setText(list.title[current]);
                 	
                    while (true) {
                      if(((Player) mService).check())
                      {
                     	 progressStatus=((Player) mService).getposition();
                     	 position=((Player) mService).getcurrentsong();
                      }
                     
                // Update the progress bar and display the 
                                     //current value in the text view
                handler.post(new Runnable() {
                public void run() {
                   progressBar.setProgress(progressStatus);
                   if(position!=current)
              	 {
              		 ImageButton but=(ImageButton)findViewById(R.id.btnPlay);
      					but.setImageResource(R.drawable.pause_button);
      					ImageView im=(ImageView)findViewById(R.id.song_art);
      					String s=null;
      					if(list.songart!=null)
      					s=list.songart[position];
      					if(s!=null)
      					{
      						Bitmap bitmap;
      						BitmapFactory.Options option = new BitmapFactory.Options();
      						bitmap=BitmapFactory.decodeFile(s, option);
      						Bitmap bit=Bitmap.createScaledBitmap(bitmap, 56,56,false);
      						im.setImageBitmap(bit);
      					}
      					else
      					{
      						im.setImageResource(R.drawable.icon_tumblr);
      					}
      					
      					TextView tv=(TextView)findViewById(R.id.song_name);
      					if(list.title!=null)
      					tv.setText(list.title[position]);
              	 }
                  
                }
                    });
                    try {
                       // Sleep for 200 milliseconds. 
                                     //Just to display the progress slowly
                       Thread.sleep(100);
                    } catch (InterruptedException e) {
                       e.printStackTrace();
                    }
                 }
              }
              }).start();
           
            
        }

        @Override
        public void onServiceDisconnected(ComponentName arg0) {
            mBound = false;
        }
    };
    @Override
    protected void onStart() {
        super.onStart();
        // Bind to LocalService
        Intent intent = new Intent(this, Player.class);
        bindService(intent, mConnection, Context.BIND_AUTO_CREATE);
       
    }
   


}
