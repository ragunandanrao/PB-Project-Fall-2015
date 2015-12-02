
# coding: utf-8

# In[36]:

from pyspark.sql import SQLContext
from pyspark.sql.types import *


# In[37]:

sqlContext = SQLContext(sc)


# In[36]:

followers = sqlContext.read.parquet("swift://notebooks.spark/followers.parquet")


# In[76]:

countries = sqlContext.read.parquet("swift://notebooks.spark/Countrylanguages.parquet")


# In[68]:

Gender = sqlContext.read.parquet("swift://notebooks.spark/tweetsFull4.parquet")


# In[85]:

Sentiment = sqlContext.read.parquet("swift://notebooks.spark/sentiment.parquet")


# In[62]:

print Gender


# In[37]:

followers.registerTempTable("followers");


# In[38]:

sqlContext.cacheTable("followers")
plt1 = sqlContext.sql("SELECT * FROM followers where followers not in (2870776) order by userDisplayName desc")


# In[39]:

df1 = plt1.toPandas()
df1=df1.set_index('userDisplayName')


# In[75]:

from pylab import rcParams
rcParams['figure.figsize'] = 20,10
Line=df1.plot(kind='line',title='Count by followers of users',stacked=False)
Line.set_ylabel("No.of follwers") 
Line.set_xlabel("Users")


# In[77]:

print countries


# In[78]:


import numpy as np
import pandas as pd


# In[79]:

countries.registerTempTable("countries");


# In[80]:

sqlContext.cacheTable("countries")
plt2 = sqlContext.sql("SELECT * FROM countries where TweetsC!=852 ")


# In[81]:

import matplotlib.pyplot as plt
import numpy as np


# In[82]:

df2 = plt2.toPandas()
df2=df2.set_index('smaAuthorCountry')


# In[84]:

from pylab import rcParams
rcParams['figure.figsize'] = 15,5
Area=df2.plot(kind='area',title='Count of tweets in Engish from countries excluding India',stacked=False)
Area.set_ylabel("No.of Tweets") 
Area.set_xlabel("Countries")


# In[ ]:




# In[97]:

Sentiment.registerTempTable("Sentiment");


# In[98]:

sqlContext.cacheTable("Sentiment")
plt3 = sqlContext.sql("SELECT smaSentiment,count1 as TweetsCount FROM Sentiment")


# In[99]:

df3 = plt3.toPandas()


# In[100]:

from pylab import rcParams
rcParams['figure.figsize'] = 15,5
df3=df3.set_index('smaSentiment')
barh = df3.plot(kind='bar',title='count of tweets on Sentiment',stacked=False)
barh.set_ylabel("No of tweets") 
barh.set_xlabel("sentiment") 


# In[69]:

Gender.registerTempTable("Gender")
sqlContext.cacheTable("Gender")


# In[71]:

plt4 = sqlContext.sql("SELECT * FROM Gender where smaAuthorGender='female'")
plt5 = sqlContext.sql("SELECT * FROM Gender where smaAuthorGender='male'")


# In[72]:

import matplotlib.pyplot as plt
import numpy as np


# In[73]:

df4 = plt4.toPandas()
df5 = plt5.toPandas()


# In[74]:

from pylab import rcParams
rcParams['figure.figsize'] = 15, 10

fig, (ax1,ax2) = plt.subplots(1,2,sharex=True)

ax1.pie(df4['count1'],labels=df4['smaSentiment'],autopct='%1.1f%%')
ax2.pie(df5['count1'],labels=df5['smaSentiment'],autopct='%1.1f%%')
ax1.set_title('Sentiment analysis on tweets by Females')
ax2.set_title('Sentiment analysis on tweets by Males')
plt.show()

