package com.project.coink;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.project.coink.fragment.FragmenLogo;
import com.project.coink.fragment.FragmentCelular;
import com.project.coink.fragment.FragmentContrato;

public class MainRegistro extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_registro);

        callCelularFragment();

    }

    private void callCelularFragment() {
        getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment,
                new FragmentCelular()).commit();
    }



    @Override
    public void onClick(View view) {

        switch (view.getId())
        {
            case R.id.iv_back:
                final Intent homeIntent = new Intent(this, MainHome.class);
                this.finish();
                startActivity(homeIntent);
                break;
        }

    }
}