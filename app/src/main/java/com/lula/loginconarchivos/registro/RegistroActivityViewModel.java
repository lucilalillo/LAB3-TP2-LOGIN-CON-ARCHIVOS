package com.lula.loginconarchivos.registro;

import android.app.Application;
import android.content.Context;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.lula.loginconarchivos.databinding.ActivityRegistroBinding;
import com.lula.loginconarchivos.modelo.Usuario;

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

public class RegistroActivityViewModel extends AndroidViewModel {
    private MutableLiveData<String> usuarioMutable;
    public RegistroActivityViewModel(@NonNull Application application) {
        super(application);
    }

    public LiveData<String> getUsuarioMutable()
    {
        if(usuarioMutable == null)
        {
            usuarioMutable = new MutableLiveData<>();
        }
        return usuarioMutable;
    }
    public void guardarUsuario(String nombre, String apellido, String mail, String pass, long dni){
        Usuario usu = new Usuario(nombre, apellido, mail, pass ,dni);
        File archivo = new File(getApplication().getFilesDir(), "usuarios.dat");
        if(archivo.length() == 0) {
            try {
                //creo el nodo
                FileOutputStream fos = new FileOutputStream(archivo, false);//si quiero q funcione en modo append, pongo true
                //lo envuelvo en un buffer
                BufferedOutputStream bos = new BufferedOutputStream(fos);
                //lo envuelvo con el objectOutputstream
                ObjectOutputStream oos = new ObjectOutputStream(bos);
                //metodo para escribir un objeto
                oos.writeObject(usu);
                //limpio el buffer
                bos.flush();
                //cierro el nodo
                fos.close();
                usu = null;

                Toast.makeText(getApplication(), "Usuario Guardado", Toast.LENGTH_LONG).show();
            } catch (FileNotFoundException e) {
                Toast.makeText(getApplication(), "Error al acceder al archivo FileNotFound", Toast.LENGTH_LONG).show();
            } catch (IOException e) {
                Toast.makeText(getApplication(), "Error al acceder al archivo IOExcep", Toast.LENGTH_LONG).show();
            }
        }
        else {
            try{
                //creo el nodo
                FileOutputStream fos = new FileOutputStream(archivo, false);//si quiero q funcione en modo append, pongo true
                //lo envuelvo en un buffer
                BufferedOutputStream bos = new BufferedOutputStream(fos);
                MyObjectOutputStream oos = new MyObjectOutputStream(bos);
                //metodo para escribir un objeto
                oos.writeObject(usu);
                //limpio el buffer
                bos.flush();
                //cierro el nodo
                fos.close();
                usu = null;
                Toast.makeText(getApplication(), "Usuario Guardado", Toast.LENGTH_LONG).show();

            } catch (FileNotFoundException e) {
                Toast.makeText(getApplication(), "Error al acceder al archivo FileNotFound", Toast.LENGTH_LONG).show();
            } catch (IOException e) {
                Toast.makeText(getApplication(), "Error al acceder al archivo IOExcep", Toast.LENGTH_LONG).show();
            }
        }
    }

    public void recuperarUsuario()
    {
        StringBuilder sb = new StringBuilder();
        File archivo = new File(getApplication().getFilesDir(), "usuarios.dat");

        try {
            //creo el nodo
            FileInputStream fis = new FileInputStream(archivo);
            //lo envulevo en el buffer
            BufferedInputStream bis = new BufferedInputStream(fis);
            //lo envuelvo en el object
            ObjectInputStream ois = new ObjectInputStream(bis);

            while(true)
            {
                try{
                    Usuario usu = (Usuario)ois.readObject();
                    String nombre = usu.getNombre();
                    String apellido = usu.getApellido();
                    String mail = usu.getMail();
                    String pass = usu.getPass();
                    long dni = usu.getDni();
                    sb.append(nombre + " " + apellido + " " + mail + " " + dni + " " + pass + "\n");
                } catch (EOFException eof) {
                    usuarioMutable.setValue(sb.toString());
                    fis.close();
                    break;
                } catch (ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }
            }

        } catch (FileNotFoundException e) {
            Toast.makeText(getApplication(), "Error al acceder al archivo FileNotFound", Toast.LENGTH_LONG).show();
        } catch (IOException e) {
            Toast.makeText(getApplication(), "Error al acceder al archivo IOExcep", Toast.LENGTH_LONG).show();
        }
    }

    class MyObjectOutputStream extends ObjectOutputStream{

        public MyObjectOutputStream() throws IOException {
            super();
        }

        public MyObjectOutputStream(OutputStream out) throws IOException {
            super(out);
        }

        public void writeStreamHeader() throws IOException
        {
            return;
        }
    }
}
