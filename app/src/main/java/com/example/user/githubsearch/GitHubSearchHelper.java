package com.example.user.githubsearch;

import android.os.AsyncTask;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

class GitHubSearchHelper {

    private String url;
    GitHubSearchHelper(Map<String, String> urlParams) {
        url = getUrl(urlParams);
    }

    void execute() {
        executeHttpRequest(url);
    }

    private String getUrl(Map<String, String> urlParams) {
        String qualifiers = urlParams.get(GitHubConstants.QUALIFIERS);
        String sort = urlParams.get(GitHubConstants.SORT);
        String order = urlParams.get(GitHubConstants.ORDER);
        String sortString = "";
        if (sort != null && !sort.isEmpty()) {
            sortString = "&" + GitHubConstants.SORT + "=" + sort + "&" + GitHubConstants.ORDER + "=" + order;
        }
        String rightPartOfUrl = qualifiers + sortString;
        String URL = GitHubConstants.MAIN_URL + rightPartOfUrl;
        System.out.println(URL);
        return URL;
    }

    private void executeHttpRequest(String urlStr) {
        new GetMethod().execute(urlStr);
    }

    private List<Repository> getRepositories(JSONObject obj) {
        List<Repository> repositories = new ArrayList<>();
        try {
            JSONArray arr = obj.getJSONArray(GitHubConstants.ITEMS);
            for (int i = 0; i < arr.length(); i++) {
                Repository repository = new Repository(arr.getJSONObject(i));
                repositories.add(repository);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return repositories;
    }


    private class GetMethod extends AsyncTask<String, Void, String> {
        String server_response;

        @Override
        protected String doInBackground(String... strings) {

            URL url;
            HttpURLConnection urlConnection;

            try {
                url = new URL(strings[0]);
                urlConnection = (HttpURLConnection) url.openConnection();

                int responseCode = urlConnection.getResponseCode();

                if (responseCode == HttpURLConnection.HTTP_OK) {
                    server_response = readStream(urlConnection.getInputStream());
                    showResponse(server_response);
                } else {
                    showResponse(null);
                }

            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }

    }

    private void showResponse(final String server_response) {
        MainActivity._this.runOnUiThread(new Runnable() {
            public void run() {
                try {
                    if (server_response == null || getRepositories(new JSONObject(server_response)).size() == 0) {
                        Toast toast = Toast.makeText(MainActivity._this, R.string.no_objects_found, Toast.LENGTH_LONG);
                        toast.show();
                    } else {
                        MainActivity._this.repositories = getRepositories(new JSONObject(server_response));
                        MainActivity._this.switchToResultsLayout();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }


    private static String readStream(InputStream in) {
        BufferedReader reader = null;
        StringBuilder response = new StringBuilder();
        try {
            reader = new BufferedReader(new InputStreamReader(in));
            String line;
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return response.toString();
    }


}
