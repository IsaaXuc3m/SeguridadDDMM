package uc3m.practica;

import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

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
        baseDatos = new DataBase(this);
        rellenarTabla();
    }

    private void rellenarTabla()
    {
        Log.d(TAG, "Vamos a hacer el select *");

        Cursor data = baseDatos.getData();

        while(data.moveToNext())
        {
            TableRow fila = new TableRow(this);
            //fila.setLayoutParams(new TableRow.LayoutParams( TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.MATCH_PARENT));
            TextView nombre = new TextView(this);
            nombre.setText(data.getString(1));
            TextView fecha = new TextView(this);
            fecha.setText(data.getString(3));
            TextView genero = new TextView(this);
            genero.setText(data.getString(2));
            TextView imagen = new TextView(this);
            imagen.setText(data.getString(6));
            TextView localizacion = new TextView(this);
            localizacion.setText(data.getString(5));
            fila.addView(nombre);
            fila.addView(fecha);
            fila.addView(genero);
            fila.addView(imagen);
            fila.addView(localizacion);
            tabla.addView(fila);
        }
    }
}
