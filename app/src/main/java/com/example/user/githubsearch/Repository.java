package com.example.user.githubsearch;

import android.util.Log;

import com.example.user.githubsearch.Author;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by arry0716 on 23.07.2017.
 */
public class Repository {
    String name;
    String html_url;
    String description;
    Author author;

    Repository(JSONObject rep) throws JSONException {
        name = rep.getString(GitHubConstants.NAME);
        html_url = rep.getString(GitHubConstants.HTML_URL);
        if (!rep.isNull(GitHubConstants.DESCRIPTION)) {
            description = rep.getString(GitHubConstants.DESCRIPTION);
        } else {
            description = GitHubConstants.NO_DESCRIPTION;
        }
        author = new Author(rep.getJSONObject(GitHubConstants.OWNER));
    }

    @Override
    public String toString() {
        return name+"\r\n"+html_url+"\r\n"+description+"\r\n";
    }
}
