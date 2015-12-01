//The package for scribe was used from the git hub https://github.com/guardian/scribe
package com.java.spark.TweetCollection;
import java.io.*;

import org.json.*;
import org.scribe.builder.ServiceBuilder;
import org.scribe.builder.api.TwitterApi;
import org.scribe.model.OAuthRequest;
import org.scribe.model.Response;
import org.scribe.model.Token;
import org.scribe.model.Verb;
import org.scribe.oauth.OAuthService;

public class TweetCollector extends Thread{
	private static final String STREAM_URI = "https://stream.twitter.com/1.1/statuses/filter.json";
static int threadCount=0;
StringBuilder sb;
    public void run(){
        try{
        System.out.println("Starting Twitter public stream consumer thread.");
        OAuthService service = new ServiceBuilder().provider(TwitterApi.class).apiKey("lSrcIcCmXrfu521LQ84zorR8H").apiSecret("WSxL7XtriomFJGAjjjXaP3WLsB9UWKB8fbAbyNdDSK6noopfup").build();
        Token accessTkn = new Token("3403644072-3eHEB0R46iMkT502x7x8s6bH4TH33jWOHY6yk9U","TGG5rLM2CBA53reI9UcKn2KdAjfAtjAADYEKa2kvkF5s9");
        System.out.println("Creating a connection to twitter stream");
        OAuthRequest tweetRequest = new OAuthRequest(Verb.POST,STREAM_URI);
        tweetRequest.addHeader("version", "HTTP/1.1");
        tweetRequest.addHeader("host", "stream.twitter.com");
        tweetRequest.setConnectionKeepAlive(true);
        tweetRequest.setCharset("UTF-8");
        
        tweetRequest.addHeader("user-agent", "Twitter Stream Reader");
         tweetRequest.addBodyParameter("track","#ThanksGiving"); // Keyword to be tracked
        service.signRequest(accessTkn, tweetRequest);
        Response tweetResponse = tweetRequest.send();
             
        BufferedReader tweetReader = new BufferedReader(new InputStreamReader(tweetResponse.getStream()));
        String tweet;
        
        sb = new StringBuilder();
        int count=0;
        System.out.println("Response is awaited");
        while((tweet=tweetReader.readLine())!=null)
        {
        
        if(tweet!=null)
        {
        	System.out.println(tweet);
        	        sb.append(tweet + "\n");   
        }
        count++;
        if(count>2)
        	
        	break;
        }
        
        }
      
        catch(Exception ex)
        {
           
        ex.printStackTrace();
        }
       finally
       { 
    	   try{
    	   File tweetFile = new File("/home/nishit/Project/TweetFile.json");
           if(!tweetFile.exists())
           {
           	tweetFile.createNewFile();
           }
           FileWriter writer = new FileWriter(tweetFile.getAbsolutePath());
            BufferedWriter tweetWriter = new BufferedWriter(writer);
            tweetWriter.write(sb.toString());
            tweetWriter.close();
    	   }
    	   catch (Exception ex)
    	   {
    		   
    	   }
       }
    }

}
