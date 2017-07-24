package com.example.user.githubsearch;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button buttonParamsSearch;
    EditText keywords;
    EditText language;
    ConnectionChecker connectionChecker;

    private static final String LOG = "mainLogs";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        switchToMainLayout();
    }

    private void switchToMainLayout() {
        setContentView(R.layout.activity_main);

        buttonParamsSearch = (Button) findViewById(R.id.searchButton);
        keywords = (EditText) findViewById(R.id.keywords);
        language = (EditText) findViewById(R.id.language);
        buttonParamsSearch.setOnClickListener(this);
        connectionChecker = new ConnectionChecker(this);

    }

    void switchToResultsLayout() {
        setContentView(R.layout.search_results);
        Button buttonReturn = (Button) findViewById(R.id.buttonReturn);
        buttonReturn.setOnClickListener(this);
        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.results);
        for (Repository repository : repositories) {
            View resultView = View.inflate(linearLayout.getContext(), R.layout.search_result, null);

            TextView textViewTitle = (TextView) resultView.findViewById(R.id.textViewTitle);
            textViewTitle.setText(repository.name);

            TextView textViewDescription = (TextView) resultView.findViewById(R.id.textViewDescription);
            textViewDescription.setText(repository.description);

            resultView.setTag(repository.html_url);

            resultView.setOnClickListener(this);

            linearLayout.addView(resultView);
        }
    }

    static MainActivity _this;

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.searchButton:
                String keywordsStr = keywords.getText().toString();
                String languageStr = language.getText().toString();

                if (keywordsStr.isEmpty() && languageStr.isEmpty()) {
                    Toast toast = Toast.makeText(this, R.string.allFieldsIsEmpty, Toast.LENGTH_LONG);
                    toast.show();
                } else {
                    if (connectionChecker.isConnect()) {
                        _this = this;
                        GithubSearch.search(keywordsStr, languageStr);
                    } else {
                        Toast toast = Toast.makeText(this, R.string.noInternetConnection, Toast.LENGTH_LONG);
                        toast.show();
                    }
                }
                break;
            case R.id.buttonReturn:
                switchToMainLayout();
                break;
            case R.id.result:
                String url = (String) view.getTag();
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                startActivity(intent);
                break;
        }
    }

    List<Repository> repositories;
}
