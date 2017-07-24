package com.example.user.githubsearch;

import com.example.user.githubsearch.GitHubConstants;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by arry0716 on 23.07.2017.
 */
public class Author {

    String login;
    String avatar_url;
    String html_url;
    String type;

    Author(JSONObject author) throws JSONException {
        login = author.getString(GitHubConstants.LOGIN);
        avatar_url = author.getString(GitHubConstants.AVATAR_URL);
        html_url = author.getString(GitHubConstants.HTML_URL);
        type = author.getString(GitHubConstants.TYPE);
    }


        @Override
        public String toString() {
            return "Author:\r\n"+"login: "+ login+"\r\n"+"avatar_url: "+ avatar_url+"\r\n"+"html_url: "+ html_url+"\r\n"+"type: "+ type+"\r\n";
        }

}
