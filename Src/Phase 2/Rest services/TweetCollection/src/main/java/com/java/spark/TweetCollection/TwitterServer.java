package com.java.spark.TweetCollection;

import java.io.IOException;

import org.json.JSONException;

public class TwitterServer {
	public static void main(String arg[]) throws IOException, JSONException
	{
	TweetCollector tc = new TweetCollector();
Thread twitterCollector1 = new Thread(tc);
twitterCollector1.start();
//	Thread twitterCollector2 = new Thread(tc);
//twitterCollector2.start();
//	TweetReader rd = new TweetReader();
//rd.readTweeets();

	}


} 
