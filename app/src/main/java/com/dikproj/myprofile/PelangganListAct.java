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

import com.dikproj.myprofile.adapter.PelangganAdapter;
import com.dikproj.myprofile.model.PelangganModel;
import com.dikproj.myprofile.services.PelangganService;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class PelangganListAct extends AppCompatActivity {

    ListView listView;
    AlertDialog.Builder dialog;
    List<PelangganModel> pelanggans=new ArrayList<>();
    PelangganAdapter pelangganAdp;
    PelangganService db=new PelangganService(this);
    Button btn_Tambah;
    TextInputEditText tiCari;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pelanggan_list);

        listView = findViewById(R.id.lv_pelanggan);
        btn_Tambah = findViewById(R.id.btn_Tambah);
        tiCari = findViewById(R.id.tie_Cari);

        btn_Tambah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pel=new Intent(PelangganListAct.this,PelangganAct.class);
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

        pelangganAdp=new PelangganAdapter(PelangganListAct.this,pelanggans);
        listView.setDivider(null);
        listView.setAdapter(pelangganAdp);

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                final String kode = pelanggans.get(position).get_id_pel();
                final String nama = pelanggans.get(position).get_nama_pel();
                final String jenis = pelanggans.get(position).get_jenis();

                final CharSequence[] dialogItem={"Ubah", "Hapus", "Copy"};
                dialog=new AlertDialog.Builder(PelangganListAct.this);

                dialog.setItems(dialogItem, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which){
                            case 0:
                                Intent pel=new Intent(PelangganListAct.this,PelangganAct.class);
                                pel.putExtra("id_pel",kode);
                                pel.putExtra("nama_pel",nama);
                                pel.putExtra("jenis",jenis);
                                startActivity(pel);
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
        pelanggans.clear();

        if(tiCari.getText().equals("")){
            getData();
        }
        else{
            getData(tiCari.getText().toString());
        }
    }

    public void getData(){
        ArrayList<HashMap<String,String>> daftar=db.getAll();

        pelanggans.clear();
        for(int i=0;i<daftar.size();i++){
            String kode=daftar.get(i).get("id_pel");
            String nama=daftar.get(i).get("nama_pel");
            String jenis=daftar.get(i).get("jenis");

            PelangganModel pelanggan=new PelangganModel();
            pelanggan.set_id_pel(kode);
            pelanggan.set_nama_pel(nama);
            pelanggan.set_jenis(jenis);
            pelanggans.add(pelanggan);
        }
        pelangganAdp.notifyDataSetChanged();
    }

    public void getData(String namaCari){
        ArrayList<HashMap<String,String>> daftar=db.getAllByNama(namaCari);

        pelanggans.clear();
        for(int i=0;i<daftar.size();i++){
            String kode=daftar.get(i).get("id_pel");
            String nama=daftar.get(i).get("nama_pel");
            String jenis=daftar.get(i).get("jenis");

            PelangganModel pelanggan=new PelangganModel();
            pelanggan.set_id_pel(kode);
            pelanggan.set_nama_pel(nama);
            pelanggan.set_jenis(jenis);
            pelanggans.add(pelanggan);
        }
        pelangganAdp.notifyDataSetChanged();
    }

    private void copyToClipBoard(String data){
        String ambildata = data;
        ClipboardManager clipboardManager = (ClipboardManager)getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clipData = ClipData.newPlainText("copy data", ambildata);
        clipboardManager.setPrimaryClip(clipData);
        Toast.makeText(this,"ID pelanggan di copy", Toast.LENGTH_SHORT).show();
    }

    private void konfirmHapus(String kode){
        AlertDialog.Builder alert=new AlertDialog.Builder(this);

        alert.setTitle("Konfirmasi Hapus")
                .setMessage("Yakin Data Akan Dihapus")
                .setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        db.delete(kode);
                        pelanggans.clear();
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
