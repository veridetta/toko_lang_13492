package com.example.toko_lang_13492;

public class Cart {
    String nama, kategori, harga, deskripsi, gambar, qt,id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getQt() {
        return qt;
    }

    public void setQt(String qt) {
        this.qt = qt;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getKategori() {
        return kategori;
    }

    public void setKategori(String kategori) {
        this.kategori = kategori;
    }

    public String getHarga() {
        return harga;
    }

    public void setHarga(String harga) {
        this.harga = harga;
    }

    public String getDeskripsi() {
        return deskripsi;
    }

    public void setDeskripsi(String deskripsi) {
        this.deskripsi = deskripsi;
    }

    public String getGambar() {
        return gambar;
    }

    public void setGambar(String gambar) {
        this.gambar = gambar;
    }
    public Cart(String nama, String kategori, String harga, String deskripsi, String gambar, String qt ,String id){
        this.nama=nama;
        this.kategori=kategori;
        this.harga=harga;
        this.deskripsi=deskripsi;
        this.gambar=gambar;
        this.qt=qt;
        this.id=id;
    }

}
