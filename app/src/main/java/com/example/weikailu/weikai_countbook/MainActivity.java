/**
 * Created by Weikai Lu
 * On Oct 2nd 2017
 * CCID: weikai
 * **/

package com.example.weikailu.weikai_countbook;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.Type;
import java.util.ArrayList;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import java.util.Date;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;



public class MainActivity extends AppCompatActivity {

    private static final String FILENAME = "file_count.sav";
    private EditText new_name;
    private EditText new_initial;
    private ListView oldCounterList;

    public ArrayList<Counter> counterList;
    private ArrayAdapter<Counter> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // View components
        new_name = (EditText) findViewById(R.id.new_name);
        new_initial = (EditText) findViewById(R.id.initial);
        Button addButton = (Button) findViewById(R.id.save);
        oldCounterList = (ListView) findViewById(R.id.counterList);

        addButton.setOnClickListener(new View.OnClickListener() {
            // handle add button
            public void onClick(View v) {
                setResult(RESULT_OK);
                // Validate input
                try {
                    String name = new_name.getText().toString();
                    Integer count = Integer.parseInt(new_initial.getText() .toString());
                    if (count >= 0) {

                        Counter newCounter = new Counter(name, count, new Date(System.currentTimeMillis()));

                        counterList.add(newCounter);
                        adapter.notifyDataSetChanged();

                        saveInFile();
                    }else {
                        Toast.makeText(MainActivity.this,"Error: Illegal initial value! \n Initial value must be non-negative",Toast.LENGTH_LONG).show();
                    }
                }catch (Exception e){
                    Toast.makeText(MainActivity.this,"Error: Illegal initial value!",Toast.LENGTH_LONG).show();
                }

            }
        });

        oldCounterList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // handle click on ListView

                // Get info from counter
                String name = counterList.get(position).getName();
                int count = counterList.get(position).getCount();
                String initialDate = counterList.get(position).getInitialDate().toString();
                String modifiedDate = counterList.get(position).getUpdateDate().toString();
                String comment = counterList.get(position).getComment();
                int initial_count = counterList.get(position).getInitial_count();

                Intent intent = new Intent(MainActivity.this, CounterActivity.class);

                // pack info in bundle and pass to CounterActivity
                Bundle bundle = new Bundle();
                bundle.putString("EXTRA_NAME",name);
                bundle.putInt("EXTRA_COUNT",count);
                bundle.putInt("EXTRA_INDEX",position);
                bundle.putString("EXTRA_INITIAL",initialDate);
                bundle.putString("EXTRA_MODIFIED",modifiedDate);
                bundle.putString("EXTRA_COMMENT",comment);
                bundle.putInt("EXTRA_ICOUNT",initial_count);
                intent.putExtras(bundle);

                // go to CounterActivity
                startActivityForResult(intent,1);
                // taken from https://stackoverflow.com/questions/37768604/how-to-use-startactivityforresult
                // 2017-Oct-02



            }
        });
    }

    @Override
    protected void onStart() {
        // TODO Auto-generated method stub
        super.onStart();
        loadFromFile();

        adapter = new ArrayAdapter<>(this, R.layout.list_item, counterList);
        oldCounterList.setAdapter(adapter);


    }

    private void loadFromFile() {
        // Read current counters from file
        try{
            FileInputStream fis = openFileInput(FILENAME);
            BufferedReader in = new BufferedReader(new InputStreamReader(fis));

            Gson gson = new Gson();

            Type listType = new TypeToken<ArrayList<Counter>>(){}.getType();
            counterList = gson.fromJson(in,listType);

        } catch (IOException e) {
            // TODO Auto-generated catch block
            counterList = new ArrayList<>();
        }
    }

    private void saveInFile() {
        // Save current counters to file
        try {
            FileOutputStream fos = openFileOutput(FILENAME,Context.MODE_PRIVATE);

            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(fos));

            Gson gson = new Gson();
            gson.toJson(counterList, out);
            out.flush();
            fos.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            throw new RuntimeException();
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Handle return value from CounterActivity
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK) {
            Bundle returnBuddle = data.getExtras();
            String name = returnBuddle.getString("RETURN_NAME");
            int count = returnBuddle.getInt("RETURN_COUNT");
            int position = returnBuddle.getInt("RETURN_INDEX");
            String comment = returnBuddle.getString("EXTRA_COMMENT");
            int initial_count = returnBuddle.getInt("EXTRA_INITIAL");
            int delete = returnBuddle.getInt("EXTRA_DELETE");

            if (delete == 1){
                // delete this counter
                counterList.remove(position);
            }else{
                // update changes
                counterList.get(position).countUpdate(count);
                counterList.get(position).changeName(name);
                counterList.get(position).setComment(comment);
                counterList.get(position).setInitial_count(initial_count);
            }

            adapter.notifyDataSetChanged();
            saveInFile();
        }
    }

}
