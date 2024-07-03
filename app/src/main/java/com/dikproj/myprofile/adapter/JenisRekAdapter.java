package com.dikproj.myprofile.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.dikproj.myprofile.R;
import com.dikproj.myprofile.model.JenisModel;
import com.dikproj.myprofile.model.PelangganModel;

import java.util.List;

public class JenisRekAdapter extends BaseAdapter {

    Activity activity;
    LayoutInflater inflater;
    List<JenisModel> jeniss;

    public JenisRekAdapter(Activity activity, List<JenisModel> jeniss){
        this.activity = activity;
        this.jeniss = jeniss;
    }

    @Override
    public int getCount(){return  jeniss.size();}

    @Override
    public Object getItem(int i){return jeniss.get(i);}

    @Override
    public long getItemId(int i){return i;}

    @Override
    public View getView(int i, View view, ViewGroup viewGroup){
        if(inflater==null){
            inflater=(LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }
        if(view==null && inflater!=null){
            view = inflater.inflate(R.layout.list_jenisrek,null);
        }
        if(view!=null){
            TextView tvNjenis=view.findViewById(R.id.tv_Njenis);
            TextView tvkd=view.findViewById(R.id.tv_Kdrek);

            JenisModel jenis=jeniss.get(i);
            tvNjenis.setText(jenis.get_nama_rek());
            tvkd.setText(jenis.get_id_rek());
        }

        return view;
    }

}
