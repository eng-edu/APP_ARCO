package com.developer.edu.app_arco.conectionAPI;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.DELETE;
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

    @PUT("equipe/aceitarSolicitacao/{CODIGO}/{ID_USUARIO}")
    Call<String> aceitarSolicitacao(@Path("CODIGO") String CODIGO_EQUIPE,
                                    @Path("ID_USUARIO") String ID_USUARIO);

    @PUT("equipe/recusarSolicitacao/{CODIGO}/{ID_USUARIO}")
    Call<String> recusarSolicitacao(@Path("CODIGO") String CODIGO_EQUIPE,
                                    @Path("ID_USUARIO") String ID_USUARIO);

    @DELETE("equipe/removerMenbro/{CODIGO}/{ID_USUARIO}")
    Call<String> removerMenbro(@Path("CODIGO") String CODIGO_EQUIPE,
                               @Path("ID_USUARIO") String ID_USUARIO);

    @GET("etapa/buscarEtapa/{ID_ETAPA}")
    Call<String> buscarEtapa(@Path("ID_ETAPA") String ID_ETAPA);

    @GET("opiniao/buscarOpiniao/{ID_USUARIO}/{ID_ETAPA}")
    Call<String> buscarOpiniao(@Path("ID_USUARIO") String ID_USUARIO,
                               @Path("ID_ETAPA") String CODIGO_ETAPA);

    @PUT("opiniao/atualizarOpiniao/{ID_USUARIO}/{ID_ETAPA}/{TEXTO}")
    Call<String> atualizarOpiniao(@Path("ID_USUARIO") String ID_USUARIO,
                                  @Path("ID_ETAPA") String CODIGO_ETAPA,
                                  @Path("TEXTO") String TEXTO);


    @POST("opiniao/denunciar/{ID_OPINIAO}/{ID_USUARIO}/{TEXTO}")
    Call<String> denunciar(@Path("ID_OPINIAO") String ID_OPINIAO,
                           @Path("ID_USUARIO") String ID_USUARIO,
                           @Path("TEXTO") String TEXTO);

    @PUT("etapa/finalizarEtapa/{ID}/{CODIGO}")
    Call<String>finalizarEtapa(@Path("ID") String ID,
                           @Path("CODIGO") String CODIGO);
}
