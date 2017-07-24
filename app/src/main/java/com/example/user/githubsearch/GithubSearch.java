package com.example.user.githubsearch;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class GithubSearch {


    public static void search(String keyword, String language) {
        //String a = "https://api.github.com/search/repositories?q=tetris+language:Java";
        Map<String, String> urlParams = new HashMap<String, String>();
        urlParams.put(GitHubConstants.KEYWORDS, keyword);
        urlParams.put(GitHubConstants.LANGUAGE, language);
        GitHubSearchHelper gitHubSearchHelper = new GitHubSearchHelper(urlParams);
        gitHubSearchHelper.execute();
    }
}