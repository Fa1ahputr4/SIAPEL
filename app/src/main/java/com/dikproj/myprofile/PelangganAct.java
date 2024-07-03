package com.dikproj.myprofile;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.dikproj.myprofile.services.JenisRekService;
import com.dikproj.myprofile.services.PelangganService;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class PelangganAct extends AppCompatActivity {

    private TextInputEditText inpId,inpNama;
    private AutoCompleteTextView inpJenis;
    private Button btnSimpan;
    private PelangganService db=new PelangganService(this);
    private JenisRekService bd=new JenisRekService(this);
    String kd,nm,jn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pelanggan_lay);

        inpId=findViewById(R.id.tie_Id);
        inpNama=findViewById(R.id.tie_Nama);
        inpJenis=findViewById(R.id.tie_Jenis);
        btnSimpan=findViewById(R.id.btn_Simpan);

        kd=getIntent().getStringExtra("id_pel");
        nm=getIntent().getStringExtra("nama_pel");
        jn=getIntent().getStringExtra("jenis");



        if(kd==null || kd.equals("")){
            setTitle("Tambah Pelanggan");
            inpId.requestFocus();
        }else{
            setTitle("Ubah Pelanggan");
            inpId.setText(kd);
            inpNama.setText(nm);
            inpJenis.setText(jn);
        }

        isiJenis();

        btnSimpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(kd==null||kd.equals("")){
                    simpan();
                }else{
                    ubah(kd);
                }
            }
        });
    }

    public void simpan(){
        if(inpId.getText().equals("")||inpNama.getText().equals("")||inpJenis.getText().equals("")){
            Toast.makeText(getApplicationContext(), "Silahkan isi semua data !", Toast.LENGTH_SHORT).show();
        }else{
            if(!db.isExists(inpId.getText().toString())){
                db.insert(inpId.getText().toString(), inpNama.getText().toString(), inpJenis.getText().toString());
                finish();
            }else{
                Toast.makeText(getApplicationContext(), "ID pelanggan sudah terdaftar !", Toast.LENGTH_SHORT).show();
                inpId.selectAll();
                inpId.requestFocus();
            }
        }
    }

    public void ubah(String kode){
        if(inpId.getText().equals("")||inpNama.getText().equals("")||inpJenis.getText().equals("")){
            Toast.makeText(getApplicationContext(), "Silahkan isi semua data !", Toast.LENGTH_SHORT).show();
        }else{
            if(db.isExists(kd)){
                db.update(inpId.getText().toString(), inpNama.getText().toString(), inpJenis.getText().toString(),kode);
                finish();
            }else{
                Toast.makeText(getApplicationContext(), "ID pelanggan tidak terdaftar !", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void isiJenis(){
        ArrayList<String> jenisList = bd.getAllJenis();
        ArrayAdapter<String> combo = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, jenisList);
        inpJenis.setAdapter(combo);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(this, PelangganListAct.class);
        startActivity(intent);
        finish();
    }

}
