package com.example.user.githubsearch;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button searchByParamsButton;
    EditText qualifiers;
    Spinner sort;
    Spinner order;

    List<Repository> repositories;
    String[] sortData = {"", "stars", "forks", "updated"};
    String[] orderData = {"desc", "asc"};

    ConnectionChecker connectionChecker;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        switchToMainLayout("", 0, 0);
    }

    private void switchToMainLayout(String q, int s, int o) {
        setContentView(R.layout.activity_main);

        qualifiers = (EditText) findViewById(R.id.qualifiers);
        qualifiers.setText(q);

        ArrayAdapter<String> sortAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, sortData);
        sortAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        sort = (Spinner) findViewById(R.id.sortSelect);
        sort.setAdapter(sortAdapter);
        sort.setPrompt(getString(R.string.sort));
        sort.setSelection(s);

        ArrayAdapter<String> orderAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, orderData);
        orderAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        order = (Spinner) findViewById(R.id.orderSelect);
        order.setAdapter(orderAdapter);
        order.setPrompt(getString(R.string.order));
        order.setSelection(o);


        sort.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {

                order.setEnabled(!sort.getSelectedItem().toString().isEmpty());
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });


        searchByParamsButton = (Button) findViewById(R.id.searchButton);
        searchByParamsButton.setOnClickListener(this);
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
            textViewTitle.setText("\"" + repository.name + "\"" + " by " + repository.author.login);

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
                String keywordsStr = qualifiers.getText().toString();
                String sortStr = sort.getSelectedItem().toString();
                String orderStr = order.getSelectedItem().toString();

                if (keywordsStr.isEmpty()) {
                    Toast toast = Toast.makeText(this, R.string.all_fields_is_empty, Toast.LENGTH_LONG);
                    toast.show();
                } else {
                    if (connectionChecker.isConnect()) {
                        _this = this;
                        GithubSearch.search(keywordsStr, sortStr, orderStr);
                    } else {
                        Toast toast = Toast.makeText(this, R.string.no_internet_connection, Toast.LENGTH_LONG);
                        toast.show();
                    }
                }
                break;
            case R.id.buttonReturn:
                switchToMainLayout(qualifiers.getText().toString(), sort.getSelectedItemPosition(), order.getSelectedItemPosition());
                break;
            case R.id.result:
                String url = (String) view.getTag();
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                startActivity(intent);
                break;
        }
    }

}
