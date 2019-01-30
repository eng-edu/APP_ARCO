package com.developer.edu.app_arco;

import android.content.Context;
import android.widget.Toast;

import io.socket.client.Socket;

public class Util {

    public static boolean verificarConexaoSocket(Socket socket, Context context) {


        boolean result = false;

        if(socket.connected()){
            result = true;
        }else {
            Toast.makeText(context,"Verifique sua conexão e tente novamente!", Toast.LENGTH_SHORT).show();
        }


        return result;
    }


}
