package com.example.music;

public class listdata {
	String title;
	String stream_url;
	String id;
	listdata(String title,String stream_url,String id)
	{
		this.title=title;
		this.stream_url=stream_url;
		this.id=id;
	}
	public String toString()
	{
		return this.title+"\n";
	}

}
