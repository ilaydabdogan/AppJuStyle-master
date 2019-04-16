package com.pro.android.justyle;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URLEncoder;
import java.text.DecimalFormat;
import java.util.ArrayList;


import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

public class ActivityMenuList extends Activity {

    ListView listMenu;
    ProgressBar prgLoading; //loading page icon
    //TextView txtTitle;
    EditText edtKeyword;
    ImageButton btnSearch;
    TextView txtAlert;

    // declare static variable to store tax and currency symbol
    static double Tax;
    static String Currency;

    // declare adapter object to create custom menu list
    AdapterMenuList mla;

    // create arraylist variables to store data from server
    static ArrayList<Long> Menu_ID = new ArrayList<Long>();
    static ArrayList<String> Menu_name = new ArrayList<String>();
    static ArrayList<Double> Menu_price = new ArrayList<Double>();
    static ArrayList<String> Menu_image = new ArrayList<String>();

    String MenuAPI;
    String TaxCurrencyAPI;
    int IOConnect = 0;
    long Category_ID;
    String Category_name;
    String Keyword;

    // create price format
    DecimalFormat formatData = new DecimalFormat("#.##");


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_marketplace);

        ActionBar bar = getActionBar();
        bar.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.header)));
        bar.setTitle("Product");
        bar.setDisplayHomeAsUpEnabled(true);
        bar.setHomeButtonEnabled(true);


        prgLoading = (ProgressBar) findViewById(R.id.prgLoading);
        listMenu = (ListView) findViewById(R.id.listMenu);
        edtKeyword = (EditText) findViewById(R.id.edtKeyword);
        btnSearch = (ImageButton) findViewById(R.id.btnSearch);



        // get category id and category name that sent from previous page
        Intent iGet = getIntent();
        Category_ID = iGet.getLongExtra("category_id",0);
        Category_name = iGet.getStringExtra("category_name");
        MenuAPI += Category_ID;

        // set category name to textview
//        txtTitle.setText(Category_name);

        mla = new AdapterMenuList(ActivityMenuList.this);

        // call asynctask class to request tax and currency data from server

        // event listener to handle search button when clicked
        btnSearch.setOnClickListener(new OnClickListener() {

            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                // get keyword and send it to server
                try {
                    Keyword = URLEncoder.encode(edtKeyword.getText().toString(), "utf-8");
                } catch (UnsupportedEncodingException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                MenuAPI += "&keyword="+Keyword;
                IOConnect = 0;
                listMenu.invalidateViews();
            }
        });

        // event listener to handle list when clicked
        listMenu.setOnItemClickListener(new OnItemClickListener() {

            public void onItemClick(AdapterView<?> arg0, View arg1, int position,
                                    long arg3) {
                // TODO Auto-generated method stub
                // go to menu detail page
                Intent iDetail = new Intent(ActivityMenuList.this, ActivityMenuDetail.class);
                iDetail.putExtra("menu_id", Menu_ID.get(position));
                startActivity(iDetail);
                overridePendingTransition(R.anim.open_next, R.anim.close_next);
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_category, menu);


        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

                return super.onOptionsItemSelected(item);
        }
    }





    // method to parse json data from server






