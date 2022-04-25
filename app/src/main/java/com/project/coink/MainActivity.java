 package com.project.coink;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.project.coink.fragment.FragmentBienvenido;
import com.project.coink.fragment.FragmentCelular;
import com.project.coink.utilitarios.EnumTextos;

 public class MainActivity extends AppCompatActivity {

     private boolean montrasbienvenida=true;
     private Bundle params;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

            params = this.getIntent().getExtras();
            if (params != null)
            {
            montrasbienvenida = params.getString(EnumTextos.MENSAJE.getValue()).equals(EnumTextos.SI.getValue())
                    ? true : false;
            }

        if (montrasbienvenida)
        {
            cargarMensaje();
        }


    }

     private void cargarMensaje() {

         getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment,
                 new FragmentBienvenido()).commit();

     }
 }