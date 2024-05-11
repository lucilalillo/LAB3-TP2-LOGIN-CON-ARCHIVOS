package com.lula.loginconarchivos.login;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.lula.loginconarchivos.modelo.Usuario;
import com.lula.loginconarchivos.registro.RegistroActivity;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;

public class MainActivityViewModel extends AndroidViewModel {

    public MainActivityViewModel(@NonNull Application application) {
        super(application);
    }

    public void login(String mail, String contra) {
        //StringBuilder sb = new StringBuilder();
        File archivo = new File(getApplication().getFilesDir(), "usuarios.dat");

        try {
            //creo el nodo
            FileInputStream fis = new FileInputStream(archivo);
            //lo envuelvo en el buffer
            BufferedInputStream bis = new BufferedInputStream(fis);
            //lo envuelvo en el object
            ObjectInputStream ois = new ObjectInputStream(bis);

            while (true) {
                try {
                    Usuario usu = (Usuario) ois.readObject();
                    //String nombre = usu.getNombre();
                    //String apellido = usu.getApellido();
                    String email = usu.getMail();
                    String pass = usu.getPass();
                    //long dni = usu.getDni();
                    //sb.append(nombre + " " + apellido + " " + email + " " + dni + " " + pass + "\n");
                    if(mail.equals(email) && contra.equals(pass))
                    {
                        Intent intent = new Intent(getApplication(), RegistroActivity.class);
                        //pongo esta bandera porque lanzo la activity desde un ViewModel
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        getApplication().startActivity(intent);
                    }
                    else {
                        Toast.makeText(getApplication(), "Email o Usuario incorrecto", Toast.LENGTH_LONG).show();
                    }
                } catch (EOFException eof) {
                    //usuarioMutable.setValue(sb.toString());
                    fis.close();
                    break;
                } catch (ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
