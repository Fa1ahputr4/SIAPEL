package com.dikproj.myprofile;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.dikproj.myprofile.adapter.JenisRekAdapter;
import com.dikproj.myprofile.adapter.PelangganAdapter;
import com.dikproj.myprofile.model.JenisModel;
import com.dikproj.myprofile.model.PelangganModel;
import com.dikproj.myprofile.services.JenisRekService;
import com.dikproj.myprofile.services.PelangganService;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class JenisListAct extends AppCompatActivity {

    ListView listView;
    AlertDialog.Builder dialog;
    List<JenisModel> jeniss=new ArrayList<>();
    JenisRekAdapter jenisAdp;
    JenisRekService db=new JenisRekService(this);
    Button btn_Tambah;
    TextInputEditText tiCari;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.jenis_list);

        listView = findViewById(R.id.lv_rek);
        btn_Tambah = findViewById(R.id.btn_Tambah);
        tiCari = findViewById(R.id.tie_Cari);

        btn_Tambah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pel=new Intent(JenisListAct.this,JenisAct.class);
                startActivity(pel);
            }
        });

        tiCari.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                getData(tiCari.getText().toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        jenisAdp=new JenisRekAdapter(JenisListAct.this,jeniss);
        listView.setDivider(null);
        listView.setAdapter(jenisAdp);

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                final String kode = jeniss.get(position).get_id_rek();
                final String nama = jeniss.get(position).get_nama_rek();

                final CharSequence[] dialogItem={"Ubah", "Hapus", "Copy"};
                dialog=new AlertDialog.Builder(JenisListAct.this);

                dialog.setItems(dialogItem, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which){
                            case 0:
                                Intent rek=new Intent(JenisListAct.this,JenisAct.class);
                                rek.putExtra("id_rek",kode);
                                rek.putExtra("nm_rek",nama);
                                startActivity(rek);
                                break;
                            case 1:
                                konfirmHapus(kode);
                                break;
                            case 2:
                                copyToClipBoard(kode);
                                tiCari.setText("");
                                break;
                        }
                    }
                }).show();
                return false;
            }
        });

    }

    @Override
    protected void onResume(){
        super.onResume();
        jeniss.clear();

        if(tiCari.getText().equals("")){
            getData();
        }
        else{
            getData(tiCari.getText().toString());
        }
    }

    public void getData(){
        ArrayList<HashMap<String,String>> daftar=db.getAll();

        jeniss.clear();
        for(int i=0;i<daftar.size();i++){
            String kode=daftar.get(i).get("id_rek");
            String nama=daftar.get(i).get("nm_rek");

            JenisModel jenis=new JenisModel();
            jenis.set_id_rek(kode);
            jenis.set_nama_rek(nama);
            jeniss.add(jenis);
        }
        jenisAdp.notifyDataSetChanged();
    }

    public void getData(String namaCari){
        ArrayList<HashMap<String,String>> daftar=db.getAllByNama(namaCari);

        jeniss.clear();
        for(int i=0;i<daftar.size();i++){
            String kode=daftar.get(i).get("id_rek");
            String nama=daftar.get(i).get("nm_rek");

            JenisModel jenis=new JenisModel();
            jenis.set_id_rek(kode);
            jenis.set_nama_rek(nama);
            jeniss.add(jenis);
        }
        jenisAdp.notifyDataSetChanged();
    }

    private void copyToClipBoard(String data){
        String ambildata = data;
        ClipboardManager clipboardManager = (ClipboardManager)getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clipData = ClipData.newPlainText("copy data", ambildata);
        clipboardManager.setPrimaryClip(clipData);
        Toast.makeText(this,"Kode Rekening di copy", Toast.LENGTH_SHORT).show();
    }

    private void konfirmHapus(String kode){
        AlertDialog.Builder alert=new AlertDialog.Builder(this);

        alert.setTitle("Konfirmasi Hapus")
                .setMessage("Yakin Data Akan Dihapus")
                .setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        db.delete(kode);
                        jeniss.clear();
                        getData();
                    }
                })

                .setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .setCancelable(false);
        alert.show();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(this, DashboardAct.class);
        startActivity(intent);
        finish();
    }

}
