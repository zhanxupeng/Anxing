package com.example.navigationapp.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.widget.TextView;

import com.example.navigationapp.R;
import com.example.navigationapp.model.Mycoin;
import com.example.navigationapp.model.Person;

public class CoinShopActivity extends AppCompatActivity {

    private TextView my_have_coin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.coin_shop);
        my_have_coin=(TextView)findViewById(R.id.my_have_coin);
        my_have_coin.setText(String.format("%d", Mycoin.coin));
    }
}
