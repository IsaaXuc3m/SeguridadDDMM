package uc3m.practica;

import android.content.Intent;
import android.content.res.Configuration;
import android.database.Cursor;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import net.sqlcipher.database.SQLiteDatabase;

public class ListarHorizontal extends AppCompatActivity
{
    private static final String TAG = "ListarHorizontal";
    DataBase baseDatos;
    private TableLayout tabla;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listar_horizontal);
        tabla = (TableLayout) findViewById(R.id.tabla);
        SQLiteDatabase.loadLibs(this);
        baseDatos = new DataBase(this);
        rellenarTabla();
    }



    private void rellenarTabla()
    {
        Log.d(TAG, "Vamos a hacer el select *");
        SQLiteDatabase.loadLibs(this);
        Cursor data = baseDatos.getData(this);

        while(data.moveToNext())
        {
            TableRow fila = new TableRow(this);
            //fila.setLayoutParams(new TableRow.LayoutParams( TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.MATCH_PARENT));
            TextView nombre = new TextView(this);
            nombre.setText(data.getString(1));
            nombre.setWidth(90);
            nombre.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            TextView fecha = new TextView(this);
            fecha.setText(data.getString(3));
            fecha.setWidth(90);
            fecha.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            TextView genero = new TextView(this);
            //genero.setText(data.getString(2));
            if(data.getString(2).equals("male"))
            {
                genero.setText("M");
            }
            else if(data.getString(2).equals("female"))
            {
                genero.setText("F");
            }
            else
            {
                genero.setText(data.getString(2));
            }
            genero.setWidth(90);
            genero.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            ImageView imagen = new ImageView(this);
            Picasso.with(this).load(data.getString(6)).into(imagen);
            imagen.setMaxWidth(90);
            TextView usuario = new TextView(this);
            usuario.setText(data.getString(7));
            usuario.setWidth(90);
            usuario.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            TextView pass = new TextView(this);
            pass.setText(data.getString(8));
            pass.setWidth(90);
            pass.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            fila.addView(nombre);
            fila.addView(fecha);
            fila.addView(genero);
            fila.addView(imagen);
            fila.addView(usuario);
            fila.addView(pass);
            tabla.addView(fila);
        }
    }



    public void onConfigurationChanged(Configuration newConfig)
    {
        super.onConfigurationChanged(newConfig);

        Log.d(TAG,"Orientacion");
        // Checks the orientation of the screen
        if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT)
        {
            Log.d(TAG,"Orientacion2");
            Intent intent = new Intent(this, ListarVertical.class);
            startActivity(intent);
            finish();
        }
    }
}
