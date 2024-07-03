package com.dikproj.myprofile.model;

public class JenisModel {

    private String _id_rek;
    private String _nama_rek;
    public JenisModel(){}

    public JenisModel(String _id_rek, String _nm_rek){
        this._id_rek = _id_rek;
        this._nama_rek = _nm_rek;
    }

    public String get_id_rek(){
        return _id_rek;
    }

    public void set_id_rek(String _id_rek) {
        this._id_rek = _id_rek;
    }

    public String get_nama_rek() {
        return _nama_rek;
    }

    public void set_nama_rek(String _nm_rek) {
        this._nama_rek = _nm_rek;
    }



}
