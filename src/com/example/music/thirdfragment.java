package com.example.music;

import java.util.Hashtable;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.GridLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

public class thirdfragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor>  {
	
@Override
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.third, container,
				false);
		return rootView;
	}
@Override public void onActivityCreated(Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);
    getLoaderManager().initLoader(0, null,(LoaderManager.LoaderCallbacks<Cursor>)this);
}


//************************THE DATA FOR PROVIDER
String[] mProjection =
{
	
	MediaStore.Audio.Albums.ALBUM,
	MediaStore.Audio.Albums.ALBUM_ART,
	MediaStore.Audio.Albums.ARTIST,
	MediaStore.Audio.Albums.NUMBER_OF_SONGS
	
   
};
static public Cursor mcursor;
String s="";
String mSelectionClause = null;
String[] mSelectionArgs = {""};
@Override
public android.support.v4.content.Loader<Cursor> onCreateLoader(int arg0,
		Bundle arg1) {
	// TODO Auto-generated method stub
	mSelectionClause = null;
    mSelectionArgs[0] = "";
    Cursor mCursor=null;
    
    return new CursorLoader(getActivity(),MediaStore.Audio.Albums.EXTERNAL_CONTENT_URI,null, null, null, null);
	
}
@Override
public void onLoadFinished(android.support.v4.content.Loader<Cursor> arg0,
		Cursor data) {
	
	
	GridView lv=(GridView)getActivity().findViewById(R.id.grv3);
	 if(data!=null)
     {
     	 int index = data.getColumnIndex(MediaStore.Audio.Albums.ALBUM_ART);
     	 int i=data.getColumnIndex(MediaStore.Audio.Albums.ARTIST);
     	 String[] artist=new String[data.getCount()];
     	 String [] art=new String[data.getCount()];
     	 int k=0;
     	while(data.moveToNext())
     	{
     	
     	    art[k]=data.getString(index);
     		
     		
     		artist[k]=data.getString(i);
     		
     		k++;
     		
     		
     		
     	}
     	Myadapter adapter=new Myadapter(getActivity(),artist,art);
     	lv.setAdapter(adapter);
     }
    
	
}
@Override
public void onLoaderReset(android.support.v4.content.Loader<Cursor> arg0) {
	// TODO Auto-generated method stub
	
	GridView lv=(GridView)getView().findViewById(R.id.grv3);
	lv.setAdapter(null);
	 getLoaderManager().restartLoader(0, null, this);


	
}
public class Myadapter extends ArrayAdapter<String>{
	String title[];
	String i_uri[];
	Context context;
	public Myadapter(Context c,String[] sa,String[] uri)
	{
		super(c,R.layout.custom,sa);
		title=sa;
		i_uri=uri;
		context=c;
	}
	@Override
	public View getView(int position, View convertView, ViewGroup parent) 
	{
		LayoutInflater inflater = (LayoutInflater) context
		        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View mv=inflater.inflate(R.layout.custom, parent, false);
		 TextView textView = (TextView) mv.findViewById(R.id.tv1);
		 textView.setHeight(96);
		 textView.setWidth(230);
		    ImageView imageView = (ImageView) mv.findViewById(R.id.im1);
		    if(title[position]!=null)
		    textView.setText(title[position]);
		    imageView.setMinimumHeight(182);
		    imageView.setMinimumWidth(230);
		    if(i_uri[position]!=null)
		    {
		    	 Bitmap bitmap;
					BitmapFactory.Options option = new BitmapFactory.Options();
					bitmap=BitmapFactory.decodeFile(i_uri[position], option);
					Bitmap bit=Bitmap.createScaledBitmap(bitmap, 230,182,false);
					imageView.setImageBitmap(bit);
		    }
		    else
		    {
		    	imageView.setImageResource(R.drawable.icon);
		    }
		   
		    return mv;
		
		
	}
	@Override
	public int getCount()
	{
		return title.length;
	}
}

@Override
public void onDestroyView()
{
	super.onDestroyView();
	getLoaderManager().destroyLoader(0);
	
	
}
}
