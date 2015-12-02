package com.java.spark.TweetCollection;
import org.json.*;
import org.mortbay.util.ajax.JSON;

//import org.scribe.builder.ServiceBuilder;
//import org.scribe.builder.api.TwitterApi;
//import org.scribe.model.OAuthRequest;
//import org.scribe.model.Response;
//import org.scribe.model.Token;
//import org.scribe.model.Verb;
//import org.scribe.oauth.OAuthService;
import java.io.*;
public class TweetReader {
	public void readTweeets() throws IOException,JSONException{
		try{
FileReader reader = new FileReader("/home/nishit/Project/TweetFile2.json");
BufferedReader tweetReader = new BufferedReader(reader);
String tweetText;
JSONArray userTweets = new JSONArray();
while((tweetText = tweetReader.readLine())!=null)
{
	Object tweet =  JSON.parse(tweetText);
	userTweets.put(tweet);
}
if(userTweets!=null)
{
JSONObject randomTweet = (JSONObject) userTweets.get(5);
 	System.out.println(randomTweet.get("text"));
}
tweetReader.close();
		}
		catch(Exception ex)
		{
		ex.printStackTrace();
		}
		
	}

}

