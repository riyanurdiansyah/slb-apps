package com.riyanurdiansyah.skripsidisya.api;

import com.riyanurdiansyah.skripsidisya.Question;
import com.riyanurdiansyah.skripsidisya.model.Baca;
import com.riyanurdiansyah.skripsidisya.model.Gambar;
import com.riyanurdiansyah.skripsidisya.model.Kategori;
import com.riyanurdiansyah.skripsidisya.model.Tebak;
import com.riyanurdiansyah.skripsidisya.model.login.LoginResponse;
import com.riyanurdiansyah.skripsidisya.model.User;
import com.riyanurdiansyah.skripsidisya.model.registrasi.AllResponse;
import com.riyanurdiansyah.skripsidisya.model.registrasi.RegistrasiRequest;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiInterface {

    //admin
    @FormUrlEncoded
    @POST("login")
    Call<LoginResponse> login(@Field("username") String username,
                              @Field("password") String password);

    @GET("user")
    Call<List<User>> getUser(@Query("key") String key,
                             @Query("kode") String kode);

    @GET("userbyid")
    Call<List<User>> getUserById(@Query("key") String key);

    @FormUrlEncoded
    @POST("registrasi")
    Call<AllResponse> register(@Field("username") String username,
                               @Field("nama") String nama,
                               @Field("password") String password,
                               @Field("no_hp") String no_hp,
                               @Field("jenis_kelamin") String jenis_kelamin,
                               @Field("tgl_lahir") String tgl_lahir,
                               @Field("alamat") String alamat,
                               @Field("kelas") String kelas,
                               @Field("foto") String foto,
                               @Field("role_id") String role_id);

    @FormUrlEncoded
    @POST("deleteuser")
    Call<AllResponse> deleteUser(@Field("username") String username);

    @FormUrlEncoded
    @POST("deletebaca")
    Call<AllResponse> deleteBacaHuruf(@Field("id") String id);

    //siswa
    @GET("soal")
    Call<List<Question>> question(@Query("metode_id") String metode_id);

    @GET("baca")
    Call<List<Baca>> getBaca(@Query("metode_id") String metode_id);

    @FormUrlEncoded
    @PUT("ubahpass")
    Call<AllResponse> ubahPass(@Field("username") String username,
                               @Field("password") String password);

    @GET("kategori")
    Call<List<Kategori>> getKategori();

    @FormUrlEncoded
    @PUT("updatesiswa")
    Call<AllResponse> updateSiswa(@Field("username") String username,
                                  @Field("no_hp") String no_hp,
                                  @Field("jenis_kelamin") String jenis_kelamin,
                                  @Field("tgl_lahir") String tgl_lahir,
                                  @Field("alamat") String alamat);

    @Multipart
    @POST("tambahbaca")
    Call<AllResponse> tambahBaca(@Part MultipartBody.Part materi,
                                 @Part("penjelasan") RequestBody penjelasan,
                                 @Part("metode_id") RequestBody metode_id);

    @Multipart
    @POST("updatebacafoto")
    Call<AllResponse> updateFotoBaca(@Part MultipartBody.Part materi,
                                     @Part("id") RequestBody id,
                                     @Part("penjelasan") RequestBody penjelasan,
                                     @Part("metode_id") RequestBody metode_id);

    @FormUrlEncoded
    @POST("updatebaca")
    Call<AllResponse> updateBaca(@Field("id") String id,
                                 @Field("penjelasan") String penjelasan);

    @Multipart
    @POST("tambahkategori")
    Call<AllResponse> tambahKategori(@Part MultipartBody.Part foto,
                                     @Part("nama") RequestBody nama);

    @Multipart
    @POST("tambahgambar")
    Call<AllResponse> tambahGambar(@Part MultipartBody.Part foto,
                                   @Part("nama") RequestBody nama,
                                   @Part("kategori_id") RequestBody kategori_id);

    @GET("gambar")
    Call<List<Gambar>> getGambar(@Query("kategori_id") String kategori_id);

    @FormUrlEncoded
    @POST("deletegambar")
    Call<AllResponse> deleteGambar(@Field("id") String id);

    @GET("tebakhuruf")
    Call<List<Tebak>> getTebakHuruf(@Query("metode_id") String metode_id);

    @Multipart
    @POST("tambahtbkhuruf")
    Call<AllResponse> tambahTbkHuruf(@Part MultipartBody.Part soal,
                                     @Part("jawaban") RequestBody jawaban,
                                     @Part("metode_id") RequestBody metode_id);

    @GET("tebakkata")
    Call<List<Tebak>> getTebakKata(@Query("metode_id") String metode_id);

    @Multipart
    @POST("tambahtbkkata")
    Call<AllResponse> tambahTbkKata(@Part MultipartBody.Part soal,
                                     @Part("jawaban") RequestBody jawaban,
                                     @Part("metode_id") RequestBody metode_id);

    @FormUrlEncoded
    @POST("deletetebak")
    Call<AllResponse> deleteTbkHuruf(@Field("id") String id);

    @FormUrlEncoded
    @POST("deletetebakkata")
    Call<AllResponse> deleteTbkKata(@Field("id") String id);
}
