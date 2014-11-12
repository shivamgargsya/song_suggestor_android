package com.example.music;


import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;




















import com.example.music.Player.LocalBinder;

import android.app.Activity;
import android.app.ProgressDialog;
import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.net.Uri;
import android.net.http.HttpResponseCache;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;


public class SecondActivity extends Activity {
	String song=null;
	String db=null;
	TextView ts=null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_second);
		Intent in=getIntent();
		song=(String) in.getCharSequenceExtra("song");
		song=song.replaceAll(" ","%20");
		
		ts=(TextView)findViewById(R.id.textView1);
		//ts.setText(song);
		//controlling the functionality of UI buttons
				findViewById(R.id.btnPlay2).setOnClickListener(new View.OnClickListener() {
					
					@Override
					public void onClick(View arg0) {
						// TODO Auto-generated method stub
						Player p=(Player)mService;
						if(p.mplayer.isPlaying())
						{
							try
							{
								p.mplayer.pause();
								ImageButton im=(ImageButton)findViewById(R.id.btnPlay2);
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
								ImageButton im=(ImageButton)findViewById(R.id.btnPlay2);
								im.setImageResource(R.drawable.pause_button);
							}
							catch(Exception e)
							{
								
							}
						}
						
					}
				});
		
		
		new Submit().execute();
		
	}
	 DefaultHttpClient client=null;
	 //String address="https://api.soundcloud.com/tracks.json?q="+song+"&client_id=5693e69205ebf08f274c62be2c04bb7d&limit=10";
	public JSONArray service() throws ClientProtocolException,IOException,JSONException{
		JSONArray json=null;
		String jsonstr="";
		InputStream is=null;
		try{
		client = new DefaultHttpClient();
		 String address="https://api.soundcloud.com/tracks.json?q="+song+"&client_id=5693e69205ebf08f274c62be2c04bb7d&limit=10";
		HttpGet p = new HttpGet(address);
		Log.i("JSONinTranSum", "passed number,operatrId,tt,amount : ");
		/*List<NameValuePair> params = new ArrayList<NameValuePair>();
		
		
		
		params.add(new BasicNameValuePair("date", date.getText().toString()));
		params.add(new BasicNameValuePair("waiter_id", waiter_no));
		
		
		
		p.setEntity(new UrlEncodedFormEntity(params));*/
		
		
		HttpResponse r = client.execute(p);
		HttpEntity entity = r.getEntity();
		is = entity.getContent();
		Log.i("json","resopnse = \n*" + is.toString() + "*");
		}catch (UnsupportedEncodingException e) {
			Log.i("UnsoppertedEncodingException","Error : " + e.toString());
            e.printStackTrace();
        } catch (ClientProtocolException e) {
        	Log.i("ClientProtocolException","Error : " + e.toString());
            e.printStackTrace();
        } catch (IOException e) {
        	Log.i("IOException","Error : " + e.toString());
            e.printStackTrace();
        }
		try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(
                    is, "iso-8859-1"), 8);
            StringBuilder sb = new StringBuilder();
            String line = null;
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
            is.close();
            jsonstr = sb.toString();
            db=jsonstr;
            
            Log.i("JSON", jsonstr);
        } catch (Exception e) {
            Log.i("Buffer Error", "Error converting result " + e.toString());
        }
		try {
            //JSONArray array = new JSONArray(jsonstr);
			//json = array.getJSONObject(0);
			json = new JSONArray(jsonstr);
        } catch (JSONException e) {
            Log.i("JSON Parser", "Error parsing data " + e.toString());
        } //To here
		return json;
		
	}

	public class Submit extends AsyncTask<String, Integer, JSONObject>{

		ProgressDialog dialog;
		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			dialog = new ProgressDialog(SecondActivity.this);
			dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
			dialog.setMessage("Please Wait...");
			dialog.show();
		}
		
		@Override
		protected JSONObject doInBackground(String... params) {
			// TODO Auto-generated method stub
			try {
				
				JSONArray x = service();
				Log.i("json","ok upto return");
				JSONObject obj = new JSONObject();
				 obj.put("bills", x);
				return obj;
			} catch (ClientProtocolException e) {
				// TODO Auto-generated catch block
				Log.i("Client Protocol Exception", "Error : " + e.toString());
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				Log.i("IO Exception", "Error : " + e.toString());
				e.printStackTrace();
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				Log.i("JSON Exception", "Error : " + e.toString());
				e.printStackTrace();
			}
			return null;
		}
		protected void onPostExecute(JSONObject result) {
			// TODO Auto-generated method stub
			dialog.dismiss();
			
			if(result==null){
				Log.i("JSONinMain", "Null returned in onPostExecute ");
				Toast.makeText(SecondActivity.this, "Error in Connection!", Toast.LENGTH_SHORT).show();
			}else{
				Log.i("atul",result.toString());
				try {
                   
                     
                    // Getting JSON Array node
                    JSONArray bills = result.getJSONArray("bills");
                    //listdata [] list=new listdata[bills.length()];
                    final List<listdata> list=new ArrayList<listdata>();
                  
                    // looping through All Contacts
                    for (int i = 0; i < bills.length(); i++) {
                        JSONObject c = bills.getJSONObject(i);
                         
                        String id = c.getString("id");
                        Log.i("atul",id);
                        String streamable = c.getString("streamable");
                        Log.i("atul",streamable);
                        String title = c.getString("title");
                        Log.i("atul",title);
                        String stream_url = c.getString("stream_url");
                        Log.i("atul",stream_url);
                        
 
                     
                        listdata item=new listdata(title,stream_url,id);
                        
                        
                        
                       list.add(item);
                      
                        
                       
                      
                    }
                    
                    
        			
        			db.concat(list.toString());
        			//ts.setText((CharSequence)list.toString());
                    MySimpleArrayAdapter adapter;
                    adapter=new  MySimpleArrayAdapter(SecondActivity.this,list);
                    Log.i("atul",list.toString());
                    //Log.i("atul",values[0]);
                    ListView lv=(ListView)findViewById(R.id.song_list);
                   // if(lv!=null)
                    //setListAdapter(adapter);
                    lv.setAdapter(adapter);
                    //Log.i("atul",values[0]);
                    
                    lv.setOnItemClickListener(new OnItemClickListener()
                    {

						@Override
						public void onItemClick(AdapterView<?> arg0, View arg1,
								int arg2, long arg3) {
							// TODO Auto-generated method stub
							Player p=(Player)mService;
							listdata item=list.get(arg2);
						
							String uri=item.stream_url+"?client_id=5693e69205ebf08f274c62be2c04bb7d";
									Uri url=Uri.parse(uri);
						
							Map<String,String> m= new HashMap<String, String>();
							m.put("android-allow-cross-domain-redirect","0");
							p.start_streaming(SecondActivity.this,url,m);
							TextView tvs=(TextView)findViewById(R.id.song_name2);
							tvs.setText((CharSequence)item.title);
							
						}
                    	
                    });
                } catch (Exception e
                		) {
                    e.printStackTrace();
                }
			}
		}
		//Array adapter for listview
		public class MySimpleArrayAdapter extends ArrayAdapter<listdata> {
			  private final Context context;
			  private final List<listdata> data;

			  public MySimpleArrayAdapter(Context context,List<listdata> data) {
			    super(context, R.layout.list_item,data);
			    this.data=data;
			    //Log.i("atul",data[0].date);
			    this.context = context;
			  
			  }
			 /* @Override
			  public int getCount()
			  {
				  return data.size();
			  }*/

			  @Override
			  public View getView(int position, View convertView, ViewGroup parent) {
			    LayoutInflater inflater = (LayoutInflater) context
			        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			    View rowView = inflater.inflate(R.layout.list_item,parent,false);
			    listdata x = data.get(position);
			    Log.i("atul",data.toString());
			    TextView title=(TextView)rowView.findViewById(R.id.song_title);
			   
			    title.setText(x.title);
			   
			    return rowView;
			  }
			} 
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
	
	@Override
	 protected void onStop() {
	       
			super.onStop();
	       
	   }
}
