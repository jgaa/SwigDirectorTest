package eu.lastviking.testswig;

import android.os.Bundle;
import android.widget.TextView;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import eu.lastviking.core.NativeRestApi;
import eu.lastviking.core.RequestCompletion;

public class MainActivity extends AppCompatActivity {

    static {
        System.loadLibrary("NativeRestApi_Wrapper");
    }

    NativeRestApi restApi = new NativeRestApi();
    Completion completion = new Completion();

    class Completion extends RequestCompletion {

        Completion() {
            super();
        }

        @Override
        public void onComplete(int httpCode, String body) {
            runOnUiThread(() -> {
                TextView tv = (TextView) findViewById(R.id.editText);
                tv.append("\nRest result: "
                        + String.valueOf(httpCode)
                        + " "
                        + body);
            });
        }
    }
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Initiate an async 'rest' request with a completion
                restApi.sendPostRequest("https://api.example.com/v1/testme",
                        "{}", completion.createWrapper());
            }
        });

        // Example of a call to a native method
        TextView tv = (TextView) findViewById(R.id.editText);
        tv.setText("The answer to * is " + String.valueOf(NativeRestApi.getAnswer()));

        // Example, send a text string and display the result.
        // quoteMe() is a normal instance method that return a value.
        tv.append(restApi.quoteMe("\nThanks for all the fish"));

        // Initiate an async 'rest' request with a completion
        restApi.sendPostRequest("https://api.example.com/v1/testme",
                "{}", completion.createWrapper());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
