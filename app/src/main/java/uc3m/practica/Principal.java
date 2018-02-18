package uc3m.practica;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class Principal extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);
    }

    public void clickNuevosUsuarios(View view) {
        Intent intent = new Intent(this, Insertar.class);
        startActivity(intent);
    }

    public void irAListar(View view) {
        Intent intent = new Intent(this, ListarVertical.class);
        startActivity(intent);
    }
}
