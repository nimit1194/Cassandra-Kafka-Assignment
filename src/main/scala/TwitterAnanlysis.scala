import twitter4j.{Query, TwitterFactory}
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
import scala.collection.JavaConverters._

class TwitterAnalysis {

  private val tweetObj = TwitterFactory.getSingleton

  /** Method to Retrieve tweet on the basis of HASH-TAG(#hashtag)
    *
    * @param hashTag :> Query word on Twitter to be analysed
    * @return
    */
  def tweetsForQuery (hashTag: String): Future[List[String]] = {
    Future {
      val resultOfQuery = tweetObj.search(new Query(hashTag))

      for (tweet <- resultOfQuery.getTweets.asScala.toList)
        yield "##" + tweet.getUser.getScreenName + " ===> " + tweet.getText + "\n"
    }
  }
}


