package com.lula.loginconarchivos.registro;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.lula.loginconarchivos.R;
import com.lula.loginconarchivos.databinding.ActivityRegistroBinding;
import com.lula.loginconarchivos.modelo.Usuario;

public class RegistroActivity extends AppCompatActivity {
    private RegistroActivityViewModel viewModel;
    private ActivityRegistroBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRegistroBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        viewModel = ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication()).create(RegistroActivityViewModel.class);

        binding.btGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                long dni = Long.parseLong(binding.etDni.getText().toString());
                String nombre = binding.etNombre.getText().toString();
                String apellido = binding.etApellido.getText().toString();
                String mail = binding.etMail.getText().toString();
                String pass = binding.etPassword.getText().toString();

                viewModel.guardarUsuario(nombre, apellido, mail, pass, dni);
            }
        });

        viewModel.getUsuarioMutable().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                binding.tvListadoUsuarios.setText("Usuario: " + s);
            }
        });
        if(getIntent().getFlags() == Intent.FLAG_ACTIVITY_NEW_TASK)
        {
            viewModel.recuperarUsuario();
        }
    }
}