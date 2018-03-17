package uc3m.practica;

import android.content.Intent;
import android.content.res.Configuration;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import net.sqlcipher.database.SQLiteDatabase;

import java.io.InputStream;
import java.net.URL;

public class ListarVertical extends AppCompatActivity
{
    private static final String TAG = "ListarVertical";
    DataBase baseDatos;
    private TableLayout tabla;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listar_vertical);
        tabla = (TableLayout) findViewById(R.id.tabla);
        SQLiteDatabase.loadLibs(this);
        baseDatos = new DataBase(this);
        rellenarTabla();
    }

    private void rellenarTabla()
    {
        Log.d(TAG, "Vamos a hacer el select *");
        SQLiteDatabase.loadLibs(this);
        Cursor data = baseDatos.getData();

        while(data.moveToNext())
        {
            TableRow fila = new TableRow(this);
            //fila.setLayoutParams(new TableRow.LayoutParams( TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.MATCH_PARENT));
            TextView nombre = new TextView(this);
            nombre.setText(data.getString(1));
            nombre.setWidth(80);
            nombre.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            TextView fecha = new TextView(this);
            fecha.setText(data.getString(3));
            fecha.setWidth(70);
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
            genero.setWidth(65);
            genero.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            ImageView imagen = new ImageView(this);
            Picasso.with(this).load(data.getString(6)).into(imagen);
            imagen.setMaxWidth(70);
            //TextView localizacion = new TextView(this);
            Button localizacion = new Button(this);
            localizacion.setText("Ver Mapa");
            localizacion.setContentDescription(data.getString(5));
            localizacion.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View view)
                {
                    Button b = (Button) view;
                    Intent i = new Intent(Intent.ACTION_VIEW);
                    String uri = "geo:0,0?q=" + b.getContentDescription();
                    i.setData(Uri.parse(uri));
                    startActivity(i);
                }
            });
            //localizacion.setText(data.getString(5));
            localizacion.setWidth(70);
            fila.addView(nombre);
            fila.addView(fecha);
            fila.addView(genero);
            fila.addView(imagen);
            fila.addView(localizacion);
            tabla.addView(fila);
        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig)
    {
        super.onConfigurationChanged(newConfig);

        Log.d(TAG,"Orientacion");
        // Checks the orientation of the screen
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE)
        {
            Log.d(TAG,"Orientacion2");
            Intent intent = new Intent(this, ListarHorizontal.class);
            startActivity(intent);
            finish();
        }
    }
}
