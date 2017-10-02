/**
 * Created by Weikai Lu on Oct 2nd 1017
 * All right reserved
 * CCID: weikai
 * **/

package com.example.weikailu.weikai_countbook;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


/**
 * @author weikailu
 * **/
public class CounterActivity extends AppCompatActivity {

    private int count;
    private String name;
    private int position;
    private String initialDate;
    private String comment;
    private int initial_count;
    private EditText countView;
    private EditText nameView;
    private TextView initial_dateView;
    private EditText initial_countView;
    private EditText commentView;
    int delete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_counter);

        // Indicate if delete this counter
        delete = 0;

        // set up views
        countView = (EditText) findViewById(R.id.count);
        nameView = (EditText) findViewById(R.id.count_name);
        initial_dateView = (TextView) findViewById(R.id.initial_date);
        initial_countView = (EditText) findViewById(R.id.initial_count);
        commentView = (EditText) findViewById(R.id.comment);

        // get variables from bundle
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        name = bundle.getString("EXTRA_NAME");
        count = bundle.getInt("EXTRA_COUNT");
        position = bundle.getInt("EXTRA_INDEX");
        initialDate = bundle.getString("EXTRA_INITIAL");
        comment = bundle.getString("EXTRA_COMMENT");
        initial_count = bundle.getInt("EXTRA_ICOUNT");


        // Set view values
        countView.setText(Integer.toString(count));
        nameView.setText(name);
        initial_dateView.setText(initialDate);
        commentView.setText(comment);
        initial_countView.setText(Integer.toString(initial_count));

        // set up button
        Button plusButton = (Button) findViewById(R.id.plus);
        Button minusButton = (Button) findViewById(R.id.minus);
        Button changeNameButton = (Button) findViewById(R.id.change_name);
        Button setCountButton = (Button) findViewById(R.id.set_count);
        Button resetButton = (Button) findViewById(R.id.RESET);
        Button commentButton = (Button)findViewById(R.id.comment_button);
        Button deleteButton = (Button)findViewById(R.id.Delete);
        Button setInitialButton = (Button) findViewById(R.id.set_initital_count);

        // PLUS(+) button was clicked
        plusButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                count++;
                update();
            }
        });

        // MINUS(-) button was clicked
        minusButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (count > 0) {
                    count--;
                    update();
                }else{
                    Toast.makeText(CounterActivity.this,"Error: Illegal count value!",Toast.LENGTH_LONG).show();
                }
            }
        });

        // Change Name button was clicked
        changeNameButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                name = nameView.getText().toString();
                update();
            }
        });

        // Set count button was clicked
        setCountButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                try {
                    int temp = Integer.parseInt(countView.getText().toString());
                    if (temp >= 0) {
                        count = temp;
                        update();
                    }else{
                        Toast.makeText(CounterActivity.this,"Error: Illegal count value!",Toast.LENGTH_LONG).show();
                    }

                } catch (Exception e) {
                    Toast.makeText(CounterActivity.this,"Error: Illegal initial value!",Toast.LENGTH_LONG).show();
                }
            }
        });

        setInitialButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    int temp = Integer.parseInt(initial_countView.getText().toString());
                    if (temp >= 0) {
                        initial_count = temp;
                        update();
                    }else{
                        Toast.makeText(CounterActivity.this,"Error: Illegal count value!",Toast.LENGTH_LONG).show();
                    }

                } catch (Exception e) {
                    Toast.makeText(CounterActivity.this,"Error: Illegal initial value!",Toast.LENGTH_LONG).show();
                }
            }
        });

        // reset button was clicked
        resetButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                count = initial_count;
                update();
            }
        });

        // comment button was clicked
        commentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                comment = commentView.getText().toString();
                update();
            }
        });

        // delete button was clicked
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                delete = 1;
                update();
            }
        });

    }

    public void update(){
        // Update to current changes to MainActivity, and exit CounterActivity

        Intent intent = new Intent(CounterActivity.this, MainActivity.class);
        Bundle returnBundle = new Bundle();

        returnBundle.putString("RETURN_NAME", name);
        returnBundle.putInt("RETURN_COUNT", count);
        returnBundle.putInt("RETURN_INDEX", position);
        returnBundle.putString("EXTRA_COMMENT",comment);
        returnBundle.putInt("EXTRA_DELETE",delete);
        returnBundle.putInt("EXTRA_INITIAL",initial_count);
        intent.putExtras(returnBundle);
        setResult(RESULT_OK, intent);
        finish();
    }

}
