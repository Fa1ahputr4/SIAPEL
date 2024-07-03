package com.dikproj.myprofile.services;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.HashMap;

public class PelangganService extends SQLiteOpenHelper {

    public PelangganService(Context context){
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
        db.execSQL("DROP TABLE IF EXISTS "+Services.TABEL_PELANGGAN);
        onCreate(db);
    }

    public void insert(String id,String nama,String jenis){
        String QUERY="INSERT INTO "+Services.TABEL_PELANGGAN + " VALUES('" + id +
                "','" +nama+"','"+jenis+"')";
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL(QUERY);
    }

    public void update(String id,String nama,String jenis,String old_id){
        String QUERY="UPDATE "+Services.TABEL_PELANGGAN + " SET id_pelanggan='" + id +
                "',nama_pelanggan='"+nama+"',jenis_rek='"+jenis+"' WHERE id_pelanggan='"+old_id +"'";
        SQLiteDatabase db=this.getWritableDatabase();
        db.execSQL(QUERY);
    }

    public void delete(String id){
        String QUERY="DELETE FROM "+Services.TABEL_PELANGGAN +
                " WHERE id_pelanggan='"+id +"'";
        SQLiteDatabase db=this.getWritableDatabase();
        db.execSQL(QUERY);
    }

    public ArrayList<HashMap<String,String>> getAll(){
        ArrayList<HashMap<String,String >> daftar=new ArrayList<>();
        String QUERY="SELECT * FROM "+Services.TABEL_PELANGGAN;
        SQLiteDatabase db=this.getWritableDatabase();
        Cursor cursor=db.rawQuery(QUERY,null);

        if(cursor.moveToFirst()){
            do{
                HashMap<String,String> temp=new HashMap<>();
                temp.put("id_pel",cursor.getString(0));
                temp.put("nama_pel",cursor.getString(1));
                temp.put("jenis",cursor.getString(2));
                daftar.add(temp);
            }while (cursor.moveToNext());
        }
        cursor.close();

        return daftar;
    }

    public ArrayList<HashMap<String,String>> getAllByNama(String nama){
        ArrayList<HashMap<String,String >> daftar=new ArrayList<>();
        String QUERY="SELECT * FROM "+Services.TABEL_PELANGGAN +
                " WHERE nama_pelanggan like '%"+ nama +"%'";
        SQLiteDatabase db=this.getWritableDatabase();
        Cursor cursor=db.rawQuery(QUERY,null);

        if(cursor.moveToFirst()){
            do{
                HashMap<String,String> temp=new HashMap<>();
                temp.put("id_pel",cursor.getString(0));
                temp.put("nama_pel",cursor.getString(1));
                temp.put("jenis",cursor.getString(2));
                daftar.add(temp);
            }while (cursor.moveToNext());
        }
        cursor.close();

        return daftar;
    }

    public boolean isExists(String kd){
        boolean cek=false;

        String QUERY="SELECT * FROM "+Services.TABEL_PELANGGAN +
                " WHERE id_pelanggan='"+ kd + "'";
        SQLiteDatabase db=this.getWritableDatabase();
        Cursor cursor=db.rawQuery(QUERY,null);

        if(cursor.getCount()>0){
            cek=true;
        }

        return cek;
    }

}
