package com.developer.edu.app_arco.conectionAPI;

import java.util.List;

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
    @POST("/usuario/cadastrar/{BIO}/{NOME}/{SOBRENOME}/{CPF}/{SEXO}/{DATA_NASC}/{ESCOLARIDADE}/{EMAIL}/{SENHA}/{TIPO}")
    Call<String> cadastrarUsuario(@Path("BIO") String BIO,
                                @Path("NOME") String NOME,
                                @Path("SOBRENOME") String SOBRENOME,
                                @Path("CPF") String CPF,
                                @Path("SEXO") String SEXO,
                                @Path("DATA_NASC") String DATA_NASC,
                                @Path("ESCOLARIDADE") String ESCOLARIDADE,
                                @Path("EMAIL") String EMAIL,
                                @Path("SENHA") String SENHA,
                                @Path("TIPO") String TIPO,
                                @Part MultipartBody.Part file,
                                @Part("file") RequestBody name);

    @GET("usuario/logar/{EMAIL}/{SENHA}")
    Call<String> logar(@Path("EMAIL") String EMAIL,
                              @Path("SENHA") String SENHA);


    @POST("usuario/recuperarSenha/{EMAIL}/{DATA_NASC}")
    Call<String> recuperarSenha(@Path("EMAIL") String EMAIL,
                                @Path("DATA_NASC") String DATA_NASC);


    @GET("usuario/buscarUsuario/{ID}")
    Call<String> buscarUsuario(@Path("ID") String ID);


    @Multipart
    @PUT("usuario/alterarComFoto/{ID}/{BIO}/{NOME}/{SOBRENOME}/{CPF}/{SEXO}/{DATA_NASC}/{ESCOLARIDADE}")
    Call<String> alterarComFotoUsuario(@Path("ID") String ID,
                                       @Path("BIO") String BIO,
                                       @Path("NOME") String NOME,
                                       @Path("SOBRENOME") String SOBRENOME,
                                       @Path("CPF") String CPF,
                                       @Path("SEXO") String SEXO,
                                       @Path("DATA_NASC") String DATA_NASC,
                                       @Path("ESCOLARIDADE") String ESCOLARIDADE,
                                       @Part MultipartBody.Part file,
                                       @Part("file") RequestBody name);

    @PUT("usuario/alterar/{ID}/{BIO}/{NOME}/{SOBRENOME}/{CPF}/{SEXO}/{DATA_NASC}/{ESCOLARIDADE}")
    Call<String> alterarUsuario(@Path("ID") String ID,
                                @Path("BIO") String BIO,
                                @Path("NOME") String NOME,
                                @Path("SOBRENOME") String SOBRENOME,
                                @Path("CPF") String CPF,
                                @Path("SEXO") String SEXO,
                                @Path("DATA_NASC") String DATA_NASC,
                                @Path("ESCOLARIDADE") String ESCOLARIDADE);


    @GET("escolaridade/buscarEscolaridade/{ID_USUARIO}")
    Call<String> buscarEscolaridade(@Path("ID_USUARIO") String ID_USUARIO);


    @Multipart
    @PUT("escolaridade/alterarComFoto/{ID_USUARIO}/{INSTITUICAO}/{AREA}/{ANO}/{GRUPOS}/{DESCRICAO}")
    Call<String> alterarComFotoEscolaridade(@Path("ID_USUARIO") String ID_USUARIO,
                                       @Path("INSTITUICAO") String INSTITUICAO,
                                       @Path("AREA") String AREA,
                                       @Path("ANO") String ANO,
                                       @Path("GRUPOS") String GRUPOS,
                                       @Path("DESCRICAO") String DESCRICAO,
                                       @Part MultipartBody.Part file,
                                       @Part("file") RequestBody name);

    @PUT("escolaridade/alterar/{ID_USUARIO}/{INSTITUICAO}/{AREA}/{ANO}/{GRUPOS}/{DESCRICAO}")
    Call<String> alterarEscolaridade(@Path("ID_USUARIO") String ID_USUARIO,
                                     @Path("INSTITUICAO") String INSTITUICAO,
                                     @Path("AREA") String AREA,
                                     @Path("ANO") String ANO,
                                     @Path("GRUPOS") String GRUPOS,
                                     @Path("DESCRICAO") String DESCRICAO);

    @GET("tematica/listar/")
    Call<String> buscarTematicas();

    @POST("arco/novoArco/{ID_LIDER}/{ID_TEMATICA}")
    Call<String> novoArcoLider(@Path("ID_LIDER") String ID_LIDER,
                          @Path("ID_TEMATICA") String TEMATICA);


    @GET("arco/buscarMeusArcos/{ID_USUARIO}")
    Call<String> buscarMeusArcos(@Path("ID_USUARIO") String ID_USUARIO);

    @POST("equipe/inserirMenbro/{CODIGO}/{ID_USUARIO}")
    Call<String> novoArcoMenbro(@Path("CODIGO") String CODIGO_EQUIPE,
                          @Path("ID_USUARIO") String ID_USUARIO);

















    @POST("arco/denunciarArco/{ID_USUARIO}/{ID_ARCO}/{DESCRICAO}")
    Call<String> denunciarArco(@Path("ID_USUARIO") String ID_USUARIO,
                               @Path("ID_ARCO") String ID_ARCO,
                               @Path("DESCRICAO") String DESCRICAO);

    @GET("usuario/listar/{ID_ARCO}")
    Call<String> buscarUsuarios(@Path("ID_ARCO") String ID_ARCOO);

    @POST("usuario/novoMenbro/{ID_USUARIO}/{ID_ARCO}")
    Call<String> novoMenbro(@Path("ID_USUARIO") String ID_USUARIO,
                          @Path("ID_ARCO") String ARCO);

    @GET("etapa/buscar/{ID_ARCO}/{ID_USUARIO}")
    Call<String> buscarEtapas(@Path("ID_ARCO") String ID_ARCO,
                              @Path("ID_USUARIO") String ID_USUARIO);

    @GET("arco/buscarArcosComaprtilhados")
    Call<String> buscarArcosComaprtilhados();


    @GET("arco/buscarRanking")
    Call<String> buscarRanking();

    @GET("arco/gerarmedia/{list}")
    Call<String> gerarMediaArcos(@Path("list")List array);


}
