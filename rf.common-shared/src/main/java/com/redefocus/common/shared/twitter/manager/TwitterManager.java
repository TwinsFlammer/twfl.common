package com.redefocus.common.shared.twitter.manager;

import com.google.common.collect.Maps;
import com.redefocus.common.shared.permissions.user.data.User;
import com.redefocus.common.shared.twitter.storage.TwitterStorage;
import lombok.Getter;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.RequestToken;
import twitter4j.conf.ConfigurationBuilder;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by @SrGutyerrez
 */
public class TwitterManager {
    public static final String oAuthConsumerKey = "Fs9796KhpzGwFeCD1sD5WGlMR",
            oAuthConsumerSecret = "5wjjIFqbRS3xxNtdqozbHUPyU6jbpGJxmiVznR0ahcZpDirk7O",
            oAuthAccessToken = "793524431041822721-Yh4DobGQvEf2stjzkMSsAw43i4QCbyf",
            oAuthAccessTokenSecret = "2S0TotmWm0EVDDfVBOVd6XTTB3RxXZSQophGYgz2wfTgi";

    private static final HashMap<Integer, RequestToken> REQUEST_TOKEN_HASH_MAP = Maps.newHashMap();

    @Getter
    private final static Twitter twitter;

    static {
        TwitterFactory twitterFactory = new TwitterFactory(
                new ConfigurationBuilder()
                        .setDebugEnabled(false)
                        .setOAuthConsumerKey(TwitterManager.oAuthConsumerKey)
                        .setOAuthConsumerSecret(TwitterManager.oAuthConsumerSecret)
                        .setOAuthAccessToken(TwitterManager.oAuthAccessToken)
                        .setOAuthAccessTokenSecret(TwitterManager.oAuthAccessTokenSecret)
                        .build()
        );

        twitter = twitterFactory.getInstance();
    }

    public static Twitter getDefaultTwitter() {
        TwitterFactory twitterFactory = new TwitterFactory();

        return twitterFactory.getInstance();
    }

    public static RequestToken getRequestToken(Integer userId) {
        return TwitterManager.REQUEST_TOKEN_HASH_MAP
                .entrySet()
                .stream()
                .filter(entrySet -> entrySet.getKey().equals(userId))
                .map(Map.Entry::getValue)
                .findFirst()
                .orElse(null);
    }

    public static RequestToken removeRequestToken(Integer userId) {
        return TwitterManager.REQUEST_TOKEN_HASH_MAP.remove(userId);
    }

    public static URL getAuthorizationURL(User user) throws TwitterException, MalformedURLException {
        Twitter twitter = new TwitterFactory().getInstance();

        twitter.setOAuthConsumer(
                TwitterManager.oAuthConsumerKey,
                TwitterManager.oAuthConsumerSecret
        );

        RequestToken requestToken = twitter.getOAuthRequestToken();

        TwitterManager.REQUEST_TOKEN_HASH_MAP.put(
                user.getId(),
                requestToken
        );

        String oAuthToken = requestToken.getAuthorizationURL().split("\\?")[1];

        TwitterStorage twitterStorage = new TwitterStorage();

        twitterStorage.insert(
                user,
                oAuthToken
        );

        return new URL(
                requestToken.getAuthorizationURL()
        );
    }
}
