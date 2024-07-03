package com.dikproj.myprofile.model;

public class PelangganModel {

private String _id_pel;
private String _nama_pel;
private String _jenis;
public PelangganModel(){}

    public PelangganModel(String _id_pel, String _nama_pel, String _jenis){
        this._id_pel = _id_pel;
        this._nama_pel = _nama_pel;
        this._jenis = _jenis;
    }

    public String get_id_pel(){
        return _id_pel;
    }

    public void set_id_pel(String _id_pel) {
        this._id_pel = _id_pel;
    }

    public String get_nama_pel() {
        return _nama_pel;
    }

    public void set_nama_pel(String _nama_pel) {
        this._nama_pel = _nama_pel;
    }

    public String get_jenis() {
        return _jenis;
    }

    public void set_jenis(String _jenis) {
        this._jenis = _jenis;
    }

}
