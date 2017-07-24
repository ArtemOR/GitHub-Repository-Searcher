package com.example.user.githubsearch;

import java.util.HashMap;
import java.util.Map;


public class GithubSearch {


    public static void search(String keyword, String sort, String order) {
        Map<String, String> urlParams = new HashMap<String, String>();
        urlParams.put(GitHubConstants.QUALIFIERS, keyword);
        urlParams.put(GitHubConstants.SORT, sort);
        urlParams.put(GitHubConstants.ORDER, order);
        GitHubSearchHelper gitHubSearchHelper = new GitHubSearchHelper(urlParams);
        gitHubSearchHelper.execute();
    }
}