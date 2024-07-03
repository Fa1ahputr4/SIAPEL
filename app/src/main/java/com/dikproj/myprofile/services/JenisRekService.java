package com.dikproj.myprofile.services;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.HashMap;

public class JenisRekService extends SQLiteOpenHelper {

    public JenisRekService(Context context){
        super(context,Services.NAMA_DATABASE,null,Services.VERSI_DATABASE);
    }

    @Override
    public void onCreate(SQLiteDatabase db){
        final String BUAT_TABEL="CREATE TABLE " + Services.TABEL_PELANGGAN +
                "(id_pelanggan TEXT PRIMARY KEY NOT NULL,"+
                "nama_pelanggan TEXT NOT NULL,jenis_rek TEXT NOT NULL)";
        db.execSQL(BUAT_TABEL);
        final String BUAT_TABEL2="CREATE TABLE " + Services.TABEL_JENIS+
                "(id_rek TEXT PRIMARY KEY NOT NULL,"+
                "nama_rek TEXT NOT NULL)";
        db.execSQL(BUAT_TABEL2);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1){
        db.execSQL("DROP TABLE IF EXISTS "+Services.TABEL_JENIS);
        onCreate(db);
    }

    public void insert(String kd,String nama){
        String QUERY="INSERT INTO "+Services.TABEL_JENIS + " VALUES('" + kd +
                "','" +nama+"')";
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL(QUERY);
    }

    public void update(String kd,String nama,String old_kd){
        String QUERY="UPDATE "+Services.TABEL_JENIS + " SET id_rek='" + kd +
                "',nama_rek='"+nama+"' WHERE id_rek='"+old_kd +"'";
        SQLiteDatabase db=this.getWritableDatabase();
        db.execSQL(QUERY);
    }

    public void delete(String kd){
        String QUERY="DELETE FROM "+Services.TABEL_JENIS +
                " WHERE id_rek='"+kd +"'";
        SQLiteDatabase db=this.getWritableDatabase();
        db.execSQL(QUERY);
    }

    public ArrayList<HashMap<String,String>> getAll(){
        ArrayList<HashMap<String,String >> daftar=new ArrayList<>();
        String QUERY="SELECT * FROM "+Services.TABEL_JENIS;
        SQLiteDatabase db=this.getWritableDatabase();
        Cursor cursor=db.rawQuery(QUERY,null);

        if(cursor.moveToFirst()){
            do{
                HashMap<String,String> temp=new HashMap<>();
                temp.put("id_rek",cursor.getString(0));
                temp.put("nm_rek",cursor.getString(1));
                daftar.add(temp);
            }while (cursor.moveToNext());
        }
        cursor.close();

        return daftar;
    }

    public ArrayList<String> getAllJenis() {
        ArrayList<String> namajenis = new ArrayList<>();
        String QUERY = "SELECT nama_rek FROM " + Services.TABEL_JENIS;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(QUERY, null);

        if (cursor.moveToFirst()) {
            do {
                namajenis.add(cursor.getString(0));
            } while (cursor.moveToNext());
        }
        cursor.close();

        return namajenis;
    }

    public ArrayList<HashMap<String,String>> getAllByNama(String nama){
        ArrayList<HashMap<String,String >> daftar=new ArrayList<>();
        String QUERY="SELECT * FROM "+Services.TABEL_JENIS +
                " WHERE nama_rek like '%"+ nama +"%'";
        SQLiteDatabase db=this.getWritableDatabase();
        Cursor cursor=db.rawQuery(QUERY,null);

        if(cursor.moveToFirst()){
            do{
                HashMap<String,String> temp=new HashMap<>();
                temp.put("id_rek",cursor.getString(0));
                temp.put("nm_rek",cursor.getString(1));
                daftar.add(temp);
            }while (cursor.moveToNext());
        }
        cursor.close();

        return daftar;
    }

    public boolean isExists(String kd){
        boolean cek=false;

        String QUERY="SELECT * FROM "+Services.TABEL_JENIS +
                " WHERE id_rek='"+ kd + "'";
        SQLiteDatabase db=this.getWritableDatabase();
        Cursor cursor=db.rawQuery(QUERY,null);

        if(cursor.getCount()>0){
            cek=true;
        }

        return cek;
    }

}
