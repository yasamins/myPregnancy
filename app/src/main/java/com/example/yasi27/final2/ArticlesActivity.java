package com.example.yasi27.final2;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by yasi27 on 27.9.2016.
 */
public class ArticlesActivity extends Activity {
    String[] articles = {
            "What to eat during pregnancy",
            "5 best exercises during pregnancy",
            "10 steps to a healthy pregnancy",
            "Nutrition during pregnancy",
            "What is new in exercise in pregnancy",
            "8 common mistakes every pregnant woman makes!",
            "Sleep tips for pregnant women",
            "Safe, Healthy birth",
            "Diet and Lifestyle during Pregnancy",
            "How to feel good about your pregnant body"

    };


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_articles);

        ListView listView = (ListView) findViewById(R.id.listView);


        //simeple_list_item_1 is a built-in xml layout of android
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, articles) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {

                View view = super.getView(position, convertView, parent);

                TextView textview = (TextView) view.findViewById(android.R.id.text1);

                //Set your Font Size Here.
                textview.setTextSize(28);
                textview.setTextColor(Color.BLACK);
                textview.setTypeface(null,Typeface.BOLD);


                return view;
            }
        };



        listView.setAdapter(adapter);


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {



            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                //int position is the position of items in the listview

                String articles = (String) adapterView.getItemAtPosition(position);
//                TextView tv = (TextView)findViewById(android.R.id.text1);
//                tv.setTextSize(TypedValue.COMPLEX_UNIT_DIP,35);
                Toast.makeText(view.getContext(), articles, Toast.LENGTH_LONG).show();

                if (articles.equals("What to eat during pregnancy")) {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.medicalnewstoday.com/articles/246404.php")));
                } else if (articles.equals("5 best exercises during pregnancy")) {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.medicalnewstoday.com/articles/290217.php")));
                } else if (articles.equals("10 steps to a healthy pregnancy")) {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.babycentre.co.uk/a536361/10-steps-to-a-healthy-pregnancy")));
                } else if (articles.equals("Nutrition during pregnancy")){
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.midwiferytoday.com/articles/nutritionpreg.asp")));
                } else if (articles.equals("What is new in exercise in pregnancy")){
                    startActivity(new Intent(Intent.ACTION_VIEW,Uri.parse("https://www.ncbi.nlm.nih.gov/pubmed/25569010")));
                } else if (articles.equals("8 common mistakes every pregnant woman makes!")){
                    startActivity(new Intent(Intent.ACTION_VIEW,Uri.parse("http://www.thehealthsite.com/pregnancy/common-mistakes-that-woman-make-during-pregnancy-d214/")));

                } else if (articles.equals("Sleep tips for pregnant women")){
                    startActivity(new Intent(Intent.ACTION_VIEW,Uri.parse("https://sleepfoundation.org/sleep-news/sleep-tips-pregnant-women")));
                } else if (articles.equals("Safe, Healthy birth")){
                    startActivity(new Intent(Intent.ACTION_VIEW,Uri.parse("https://www.ncbi.nlm.nih.gov/pmc/articles/PMC2730905/")));
                } else if (articles.equals("Diet and Lifestyle during Pregnancy")){
                    startActivity(new Intent(Intent.ACTION_VIEW,Uri.parse("http://patient.info/health/diet-and-lifestyle-during-pregnancy")));
                }

                else {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.babycenter.com/0_how-to-feel-good-about-your-pregnant-body_9180.bc")));
                }

            }
        });
    }
}


