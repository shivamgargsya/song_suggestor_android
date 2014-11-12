package com.example.music;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.R.string;
import android.os.Build;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

@TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
public class myview extends LinearLayout{
	private TextView tv1;

	private ImageView im;

	myview(Context context)
	{
		super(context);
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		inflater.inflate(R.layout.my_view,null);
		tv1=new TextView(context);
		im=new ImageView(context);
		this.setOrientation(VERTICAL);
		tv1.setHeight(96);
		tv1.setWidth(230);
		tv1.setTextSize(16);
		tv1.setText("empty");
		tv1.setTextColor(getResources().getColor(R.color.white));
		tv1.setBackgroundColor(getResources().getColor(R.color.black));
		tv1.setAlpha((float)0.50);
		im.setImageResource(R.drawable.icon_tumblr);
		im.setMinimumHeight(230);
		im.setMinimumWidth(182);
		im.setPaddingRelative(2,2,2,2);
		
		LinearLayout.LayoutParams parm=new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT);
		
		parm.setMargins(2,2,2,2);
		
		this.setLayoutParams(parm);
		
		
		this.setPadding(2,2,2,2);
		
		this.addView(im);
		this.addView(tv1);
	}
	@TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
	myview(Context context,AttributeSet attr)
	{
		super(context,attr);
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		inflater.inflate(R.layout.my_view,null);
		tv1=new TextView(context);
		im=new ImageView(context);
		this.setOrientation(VERTICAL);
		tv1.setHeight(96);
		tv1.setWidth(230);
		tv1.setTextSize(16);
		tv1.setText("empty");
		tv1.setTextColor(getResources().getColor(R.color.white));
		tv1.setBackgroundColor(getResources().getColor(R.color.black));
		tv1.setAlpha((float)0.50);
		im.setImageResource(R.drawable.icon_tumblr);
		im.setMinimumHeight(182);
		im.setMinimumWidth(230);
		im.setPaddingRelative(2,2,2,2);
		
		LinearLayout.LayoutParams parm=new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT);
		
		parm.setMargins(2,2,2,2);
		
		this.setLayoutParams(parm);
		
		
		this.setPadding(2,2,2,2);
		
		this.addView(im);
		this.addView(tv1);
		
		
	}
	String settext(String s)
	{
		
		if(tv1==null)
		{
			
			
			return "found";
		}
		
		tv1.setText(s);
		return "not found";
	}
	
	void setimage(String uri)
	{
		Bitmap bitmap;
		BitmapFactory.Options option = new BitmapFactory.Options();
		bitmap=BitmapFactory.decodeFile(uri, option);
		Bitmap bit=Bitmap.createScaledBitmap(bitmap, 230,182,false);
		im.setImageBitmap(bit);
		
		
	}
	

}
