package com.spark
import org.apache.spark.SparkConf
import org.apache.spark.streaming.twitter.TwitterUtils
import org.apache.spark.streaming.{Seconds, StreamingContext}
import java.io.FileWriter
import twitter4j._
import org.apache.spark.SparkContext
import twitter4j.internal.org.json.JSONArray
import scala.util.parsing.json.JSON
import org.codehaus.jettison.json.JSONWriter

import scala.collection.mutable.StringBuilder

object sparkProcess {
   def main(args: String) : String = {

			val query:String = args
			    // Set the system properties so that Twitter4j library used by twitter stream
			    // can use them to generate OAuth credentials
			    System.setProperty("twitter4j.oauth.consumerKey", "lSrcIcCmXrfu521LQ84zorR8H")
			    System.setProperty("twitter4j.oauth.consumerSecret", "WSxL7XtriomFJGAjjjXaP3WLsB9UWKB8fbAbyNdDSK6noopfup")
			    System.setProperty("twitter4j.oauth.accessToken", "3403644072-3eHEB0R46iMkT502x7x8s6bH4TH33jWOHY6yk9U")
			    System.setProperty("twitter4j.oauth.accessTokenSecret", "TGG5rLM2CBA53reI9UcKn2KdAjfAtjAADYEKa2kvkF5s9")
			    //Create a spark configuration with a custom name and master
			    //spark://nishit-Inspiron-5547:7077 
			    // For more master configuration see  https://spark.apache.org/docs/1.2.0/submitting-applications.html#master-urls
			    val sqlQuery:String = createQueryBasedOnFilter(query)
			    if(sqlQuery!=null && sqlQuery!=""){
			    val sparkConf = new SparkConf().setAppName("STweetsApp").setMaster("local[*]").set("spark.driver.allowMultipleContexts", "true")
			 val sc = new SparkContext(sparkConf);
			 val ssql = new org.apache.spark.sql.SQLContext(sc)
			 val df = ssql.jsonFile("/home/nishit/Project/Tweets/TweetSet.json");
			df.registerTempTable("Tweets")
			val countOfTweets =ssql.sql(sqlQuery)
			//countOfTweets.printSchema
			countOfTweets.show()
			val count = countOfTweets.count().toInt
			val output=new StringBuilder()
			output.append("[")
			countOfTweets.toJSON.take(count).foreach(item=>output.append(item.toString()+","))
		  val original =output.toString()
      val editedResult = original.substring(0,original.toString().length() - 1) + "]"
  	  editedResult		
			    }
			    else ""
   }
   
    def createQueryBasedOnFilter(query: String) : String = {
      var sqlStmt: String =""
      if(query.toLowerCase()=="sourceanalysis")
       {
       sqlStmt = "SELECT source, COUNT(*) AS total_count FROM Tweets WHERE  source IS NOT NULL  and user.lang = 'en' and (retweeted_status IS NULL or retweeted_status='') GROUP BY source  ORDER BY total_count DESC LIMIT 10"
       }
      else if(query.toLowerCase()=="geoanalysis")
      {
        sqlStmt = "select user.screen_name as name,text,user.profile_image_url as userimage, coordinates from Tweets where coordinates IS NOT NULL and user.screen_name IS NOT NULL"
      }
      else if(query.toLowerCase() == "imageanalysis")
      {
      sqlStmt = "select user.screen_name as username, user.profile_image_url as userimage, user.followers_count as followers from Tweets order by user.followers_count desc limit 1"
      }
      else if(query.toLowerCase() == "timezoneanalysis")
      {
        sqlStmt = "SELECT user.time_zone as timezone, SUBSTR(created_at, 0, 10) as postedtime, COUNT(*) AS total_count FROM Tweets WHERE user.time_zone IS NOT NULL AND  SUBSTR(created_at, 0, 10) in ('Sat Nov 28') GROUP BY user.time_zone,SUBSTR(created_at, 0, 10) ORDER BY total_count DESC LIMIT 5"  
      }
       sqlStmt
   }
}

//countOfTweets.foreach { row => row. }
//ssql.sql("select user.lang, count(*) as tweetsCount from Tweets where user.lang!='null' group by user.lang") 
			  //ssql.sql("SELECT t.retweeted_screen_name, sum(retweets) AS total_retweets,count(*) AS tweet_count FROM (SELECT retweeted_status.user.screen_name as retweeted_screen_name, retweeted_status.text, max(retweet_count) as retweets FROM Tweets GROUP BY retweeted_status.user.screen_name, retweeted_status.text) t GROUP BY t.retweeted_screen_name ORDER BY total_retweets DESC LIMIT 10") 
			//extract(created_at, 'hour') as hourlyData, count(*) as tweets from Tweets group by extract(created_at, 'hour')	
		//countOfTweets.toJSON.take(count).foreach(item=>output.append(item.toString()))
		//	output.toString()
			//return "Table shown"
			//countOfTweets.write.mode("append").json("/home/nishit/tweetsByTime/output.json")
			//countOfTweets.save("/home/nishit/tweetsByTime")