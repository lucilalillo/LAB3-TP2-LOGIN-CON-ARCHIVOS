package com.lula.loginconarchivos.login;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;

import com.lula.loginconarchivos.R;
import com.lula.loginconarchivos.databinding.ActivityMainBinding;
import com.lula.loginconarchivos.registro.RegistroActivity;

public class MainActivity extends AppCompatActivity {
    private MainActivityViewModel viewModel;
    private ActivityMainBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            binding = ActivityMainBinding.inflate(getLayoutInflater());
            setContentView(binding.getRoot());
            viewModel = ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication()).create(MainActivityViewModel.class);

            binding.btLogin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String usu = binding.etUsuario.getText().toString();
                    String contra = binding.etContra.getText().toString();
                    viewModel.login(usu, contra);
                }
            });

            binding.btRegistrarse.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //creo un intent para iniciar la otra activity
                    Intent intent = new Intent(MainActivity.this, RegistroActivity.class);
                    startActivity(intent);
                }
            });
    }
}