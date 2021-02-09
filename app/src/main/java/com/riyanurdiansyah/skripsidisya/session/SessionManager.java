package com.riyanurdiansyah.skripsidisya.session;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.riyanurdiansyah.skripsidisya.model.login.LoginRequest;

import java.util.HashMap;

public class SessionManager {

    Context _context;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    //user login
    private static final String IS_LOGIN = "isLoggedIn";
    private static final String ID = "id";
    private static final String USERNAME = "username";
    private static final String NAMA = "nama";
    private static final String NO_HP = "no_hp";
    private static final String JENIS_KELAMIN = "jenis_kelamin";
    private static final String TGL_LAHIR = "tgl_lahir";
    private static final String ALAMAT = "alamat";
    private static final String KELAS = "kelas";
    private static final String FOTO = "foto";
    private static final String ROLE_ID = "role_id";

    @SuppressLint("CommitPrefEdits")
    public SessionManager(Context context) {
        this._context = context;
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        editor = sharedPreferences.edit();
    }

    public void createSession(LoginRequest user) {
        editor.putBoolean(IS_LOGIN, true);
        editor.putString(ID, String.valueOf(user.getId()));
        editor.putString(USERNAME, user.getUsername());
        editor.putString(NAMA, user.getNama());
        editor.putString(NO_HP, user.getNo_hp());
        editor.putString(JENIS_KELAMIN, user.getJenis_kelamin());
        editor.putString(TGL_LAHIR, user.getTgl_lahir());
        editor.putString(ALAMAT, user.getAlamat());
        editor.putString(KELAS, user.getKelas());
        editor.putString(FOTO, user.getFoto());
        editor.putString(ROLE_ID, String.valueOf(user.getRole_id()));
        editor.commit();
    }

    public HashMap<String, String> getUserDetail() {
        HashMap<String, String> user = new HashMap<>();
        user.put(ID, sharedPreferences.getString(ID, null));
        user.put(USERNAME, sharedPreferences.getString(USERNAME, null));
        user.put(NAMA, sharedPreferences.getString(NAMA, null));
        user.put(NO_HP, sharedPreferences.getString(NO_HP, null));
        user.put(JENIS_KELAMIN, sharedPreferences.getString(JENIS_KELAMIN, null));
        user.put(TGL_LAHIR, sharedPreferences.getString(TGL_LAHIR, null));
        user.put(ALAMAT, sharedPreferences.getString(ALAMAT, null));
        user.put(KELAS, sharedPreferences.getString(KELAS, null));
        user.put(FOTO, sharedPreferences.getString(FOTO, null));
        user.put(ROLE_ID, sharedPreferences.getString(ROLE_ID, null));
        return user;
    }

    public void logoutSession() {
        editor.clear();
        editor.commit();
    }

    public boolean isLoggedIn(){
        return sharedPreferences.getBoolean(IS_LOGIN, false);
    }
}
