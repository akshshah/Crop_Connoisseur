package com.example.cropconnoisseur;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Adapter;
import android.widget.TextView;

import com.example.cropconnoisseur.Model.Test;
import com.example.cropconnoisseur.Utils.TestCardAdapter;
import com.xwray.groupie.GroupAdapter;

import java.util.ArrayList;

public class TestResultsCompareActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private RecyclerView testRecView;
    private String testName;
    private ArrayList<Test> testArrayList;
    private GroupAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_results_compare);

        try{
            Intent intent = getIntent();
            testName = intent.getStringExtra("testName");
            toolbar = findViewById(R.id.toolbar);
            testRecView = findViewById(R.id.testRecView);

            if(testName.equals("ph")){
                testArrayList = getPHList();
                toolbar.setTitle("pH Level Test");
            }
            else if(testName.equals("potassium")){
                toolbar.setTitle("Potassium(K) Test");
                testArrayList = getPotassiumList();
            }
            else if(testName.equals("nitrogen")){
                toolbar.setTitle("Nitrogen(N) Test");
                testArrayList = getNitrogenList();
            }
            else if(testName.equals("phosphorus")){
                toolbar.setTitle("Phosphorus(P) Test");
                testArrayList = getPhosphorusList();
            }

            testRecView.setLayoutManager(new LinearLayoutManager(this));
            testRecView.setHasFixedSize(true);
            adapter = new GroupAdapter();
            testRecView.setAdapter(adapter);
            adapter.clear();
            for(Test t:testArrayList){
                adapter.add(new TestCardAdapter(this,t));
            }
            adapter.notifyDataSetChanged();

        }catch (Exception e){
            e.printStackTrace();
        }


    }

    public ArrayList<Test> getPHList(){
        testArrayList = new ArrayList<>();
        testArrayList.add(new Test("#017441","Alkaline"));
        testArrayList.add(new Test("#3c7d26","Neutral"));
        testArrayList.add(new Test("#948d02","Slight Acidic"));
        testArrayList.add(new Test("#c39c0c","Acidic"));
        testArrayList.add(new Test("#f99c1a","Acidic"));
        testArrayList.add(new Test("#f68927","Very Acidic"));
        testArrayList.add(new Test("#ec4531","Very Acidic"));

        return testArrayList;
    }

    public ArrayList<Test> getNitrogenList(){

        testArrayList = new ArrayList<>();
        testArrayList.add(new Test("#a1237d","Surplus"));
        testArrayList.add(new Test("#af5790","Sufficient"));
        testArrayList.add(new Test("#c07ea6","Adequate"));
        testArrayList.add(new Test("#c199af","Deficient"));
        testArrayList.add(new Test("#d2c2c3","Depleted"));

        return testArrayList;
    }

    public ArrayList<Test> getPhosphorusList(){

        testArrayList = new ArrayList<>();
        testArrayList.add(new Test("#0268a2","Surplus"));
        testArrayList.add(new Test("#2c7ba9","Sufficient"));
        testArrayList.add(new Test("#6794b7","Adequate"));
        testArrayList.add(new Test("#7f94ab","Deficient"));
        testArrayList.add(new Test("#b2c4c5","Depleted"));

        return testArrayList;
    }

    public ArrayList<Test> getPotassiumList(){

        testArrayList = new ArrayList<>();
        testArrayList.add(new Test("#ac5f16","Surplus"));
        testArrayList.add(new Test("#b85e1f","Sufficient"));
        testArrayList.add(new Test("#d18922","Adequate"));
        testArrayList.add(new Test("#e1a147","Deficient"));
        testArrayList.add(new Test("#e4b66e","Depleted"));

        return testArrayList;
    }
}