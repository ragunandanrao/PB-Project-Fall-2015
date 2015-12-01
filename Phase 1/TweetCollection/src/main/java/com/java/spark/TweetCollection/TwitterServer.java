package com.java.spark.TweetCollection;

public class TwitterServer {
	public static void main(String arg[])
	{
	TweetCollector tc = new TweetCollector();
	Thread twitterCollector1 = new Thread(tc);
	twitterCollector1.start();
	Thread twitterCollector2 = new Thread(tc);
twitterCollector2.start();
	
	}

} 
