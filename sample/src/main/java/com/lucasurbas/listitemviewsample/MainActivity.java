package com.lucasurbas.listitemviewsample;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.lucasurbas.listitemview.ListItemView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author Lucas Urbas
 */
public class MainActivity extends AppCompatActivity {

    @BindView(R.id.sample_general)
    ListItemView sampleGeneralAttrsView;

    @BindView(R.id.sample_checkable)
    ListItemView sampleCheckableAttrsView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        sampleGeneralAttrsView.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, GeneralAttrsActivity.class);
            startActivity(intent);
        });

        sampleCheckableAttrsView.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, CheckableAttrsActivity.class);
            startActivity(intent);
        });
    }
}
