package com.dikproj.myprofile;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class DashboardAct extends AppCompatActivity {

    ImageView ivrek,ivpel;
    TextView tvrek,tvple;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dashboard_menu);

        ivrek=findViewById(R.id.iv_rek);
        ivpel=findViewById(R.id.iv_pel);
        tvrek=findViewById(R.id.tv_trek);
        tvple=findViewById(R.id.tv_tpel);

        ivrek.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent rekmenu = new Intent(DashboardAct.this,JenisListAct.class);
                startActivity(rekmenu);
                finish();
            }
        });

        ivpel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pelmenu = new Intent(DashboardAct.this,PelangganListAct.class);
                startActivity(pelmenu);
                finish();
            }
        });

        tvrek.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent rekmenu = new Intent(DashboardAct.this,JenisListAct.class);
                startActivity(rekmenu);
                finish();
            }
        });

        tvple.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pelmenu = new Intent(DashboardAct.this,PelangganListAct.class);
                startActivity(pelmenu);
                finish();
            }
        });

    }
}
