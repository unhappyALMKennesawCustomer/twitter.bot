import java.util.ArrayList;
import java.util.List;
import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.Status;
import twitter4j.StatusUpdate;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
/**
 * Created by sams on 9/28/2017.
 */
public class TwitterBot {
    public static void main(String [] args){
        try{

            // Reply with variety
            List<String> searches = new ArrayList<String>();
            searches.add("@AtlantaLuxuryMotors");
            searches.add("ALM Kennesaw");

            List<String> replies = new ArrayList<String>();
            replies.add("A vehicle I purchased from ALM Kennesaw in July 2017 now requires a jump every 1-3 days. Their response has been to ask me to trade in the car and have an upside-down car loan.");
            replies.add("A vehicle I purchased from ALM Kennesaw in July 2017 had faulty after market parts. Their response has been to ask me to trade in the car and have an upside-down car loan.");
            replies.add("A vehicle I purchased from ALM Kennesaw in July 2017 had faulty after market parts. Check under the hood of car and make sure no extra wires are coming from the battery.");

            replyToTweetWithVariety(searches, replies);

        } catch (TwitterException e) {
            e.printStackTrace();
        }
    }

    private static void replyToTweetWithVariety(List<String> searches,
                                                List<String> replies) throws TwitterException {
        // access the twitter API using your twitter4j.properties file
        Twitter twitter = TwitterFactory.getSingleton();

        // keep tweeting forever
        while (true) {
            // create a new search, choose from random searches
            Query query = new Query(searches.get((int) (searches.size() * Math
                    .random())));

            // get the results from that search
            QueryResult result = twitter.search(query);

            // get a random tweet from those results
            Status tweetResult = result.getTweets().get((int) (result.getCount() * Math.random()));
            System.out.println("Selected tweet: " + tweetResult.getText());
            // reply to that tweet, choose from random replies
            StatusUpdate statusUpdate = new StatusUpdate(".@"
                    + tweetResult.getUser().getScreenName()
                    + replies.get((int) (replies.size() * Math.random())));
            statusUpdate.inReplyToStatusId(tweetResult.getId());
            System.out.println("Selected Response: " + statusUpdate.getStatus());
            Status status = twitter.updateStatus(statusUpdate);
            System.out.println("Sleeping.");

            // go to sleep for an hour
            try {
                Thread.sleep(60 * 60 * 1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }
}
