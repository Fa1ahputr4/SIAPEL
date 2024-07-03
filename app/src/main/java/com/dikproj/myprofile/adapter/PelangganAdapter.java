package com.dikproj.myprofile.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.dikproj.myprofile.R;
import com.dikproj.myprofile.model.PelangganModel;

import java.util.List;

public class PelangganAdapter extends BaseAdapter {

    Activity activity;
    LayoutInflater inflater;
    List<PelangganModel> pelanggans;

    public PelangganAdapter(Activity activity, List<PelangganModel> pelanggans){
        this.activity = activity;
        this.pelanggans = pelanggans;
    }

    @Override
    public int getCount(){return  pelanggans.size();}

    @Override
    public Object getItem(int i){return pelanggans.get(i);}

    @Override
    public long getItemId(int i){return i;}

    @Override
    public View getView(int i, View view, ViewGroup viewGroup){
        if(inflater==null){
            inflater=(LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }
        if(view==null && inflater!=null){
            view = inflater.inflate(R.layout.list_pelanggan,null);
        }
        if(view!=null){
            TextView tvNama=view.findViewById(R.id.tv_Nama);
            TextView tvId=view.findViewById(R.id.tv_Id);
            TextView tvJenis=view.findViewById(R.id.tv_Jenis);

            PelangganModel pelanggan=pelanggans.get(i);
            tvNama.setText(pelanggan.get_nama_pel());
            tvId.setText(pelanggan.get_id_pel());
            tvJenis.setText(pelanggan.get_jenis());
        }

        return view;
    }

}
