package com.developer.edu.app_arco.conectionAPI;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;

public interface RetrofitService {


    @Multipart
    @POST("/usuario/inserir/{NOME}/{IDADE}/{SEXO}/{ESCOLARIDADE}/{EMAIL}/{SENHA}/{TIPO}")
    Call<String> inserirUsuario(@Path("NOME") String NOME,
                                @Path("IDADE") String IDADE,
                                @Path("SEXO") String SEXO,
                                @Path("ESCOLARIDADE") String ESCOLARIDAE,
                                @Path("EMAIL") String EMAIL,
                                @Path("SENHA") String SENHA,
                                @Path("TIPO") String TIPO,
                                @Part MultipartBody.Part file,
                                @Part("file") RequestBody name);

    @GET("usuario/buscarUsuarioEmailSenha/{EMAIL}/{SENHA}")
    Call<String> buscarUsuarioEmailSenha(@Path("EMAIL") String EMAIL,
                              @Path("SENHA") String SENHA);

    @GET("usuario/buscar/{ID}")
    Call<String> buscarUsuario(@Path("ID") String ID);

    @Multipart
    @PUT("usuario/alterarComFoto/{ID}/{NOME}/{IDADE}/{SEXO}/{ESCOLARIDADE}")
    Call<String> alterarComFotoUsuario(@Path("ID") String ID,
                                       @Path("NOME") String NOME,
                                       @Path("IDADE") String IDADE,
                                       @Path("SEXO") String SEXO,
                                       @Path("ESCOLARIDADE") String ESCOLARIDAE,
                                       @Part MultipartBody.Part file,
                                       @Part("file") RequestBody name);


    @PUT("usuario/alterar/{ID}/{NOME}/{IDADE}/{SEXO}/{ESCOLARIDADE}")
    Call<String> alterarUsuario(@Path("ID") String ID,
                                       @Path("NOME") String NOME,
                                       @Path("IDADE") String IDADE,
                                       @Path("SEXO") String SEXO,
                                       @Path("ESCOLARIDADE") String ESCOLARIDAE);

    @GET("tematica/listar/")
    Call<String> buscarTematicas();

    @POST("arco/novoArco/{ID_TEMATICA}/{ID_LIDER}")
    Call<String> novoArco(@Path("ID_TEMATICA") String ID,
                          @Path("ID_LIDER") String NOME);

    @GET("arco/buscarMeusArcos/{ID_USUARIO}")
    Call<String> buscarMeusArcos(@Path("ID_USUARIO") String ID_USUARIO);

    @POST("arco/denunciarArco/{ID_USUARIO}/{ID_ARCO}/{DESCRICAO}")
    Call<String> denunciarArco(@Path("ID_USUARIO") String ID_USUARIO,
                               @Path("ID_ARCO") String ID_ARCO,
                               @Path("DESCRICAO") String DESCRICAO);

    @GET("usuario/listar/{ID_ARCO}")
    Call<String> buscarUsuarios(@Path("ID_ARCO") String ID_ARCOO);

    @POST("usuario/novoMenbro/{ID_USUARIO}/{ID_ARCO}")
    Call<String> novoMenbro(@Path("ID_USUARIO") String ID_USUARIO,
                          @Path("ID_ARCO") String ARCO);

    @GET("etapa/buscar/{ID_ARCO}")
    Call<String> buscarEtapas(@Path("ID_ARCO") String ID_ARCO);

}
