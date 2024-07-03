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
    import java.util.List;

    public class JenisAct extends AppCompatActivity {

        private TextInputEditText inpkd,inpNama;
        private Button btnSimpan;
        private JenisRekService db=new JenisRekService(this);
        String kd,nm;

        @Override
        protected void onCreate(@Nullable Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.jenis_lay);

            inpkd=findViewById(R.id.tie_Kd);
            inpNama=findViewById(R.id.tie_NRek);
            btnSimpan=findViewById(R.id.btn_Simpan2);

            kd=getIntent().getStringExtra("id_rek");
            nm=getIntent().getStringExtra("nm_rek");


            if(kd==null || kd.equals("")){
                setTitle("Tambah Rekening");
                inpkd.requestFocus();
            }else{
                setTitle("Ubah Jenis Rekening");
                inpkd.setText(kd);
                inpNama.setText(nm);
            }

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
            if(inpkd.getText().equals("")||inpNama.getText().equals("")){
                Toast.makeText(getApplicationContext(), "Silahkan isi semua data !", Toast.LENGTH_SHORT).show();
            }else{
                if(!db.isExists(inpkd.getText().toString())){
                    db.insert(inpkd.getText().toString(), inpNama.getText().toString());
                    finish();
                }else{
                    Toast.makeText(getApplicationContext(), "Kode Rekening sudah terdaftar !", Toast.LENGTH_SHORT).show();
                    inpkd.selectAll();
                    inpkd.requestFocus();
                }
            }
        }

        public void ubah(String kode){
            if(inpkd.getText().equals("")||inpNama.getText().equals("")){
                Toast.makeText(getApplicationContext(), "Silahkan isi semua data !", Toast.LENGTH_SHORT).show();
            }else{
                if(db.isExists(kd)){
                    db.update(inpkd.getText().toString(), inpNama.getText().toString(), kode);
                    finish();
                }else{
                    Toast.makeText(getApplicationContext(), "Kode Rekening tidak terdaftar !", Toast.LENGTH_SHORT).show();
                }
            }
        }

        @Override
        public void onBackPressed() {
            super.onBackPressed();
            Intent intent = new Intent(this, JenisListAct.class);
            startActivity(intent);
            finish();
        }

    }
