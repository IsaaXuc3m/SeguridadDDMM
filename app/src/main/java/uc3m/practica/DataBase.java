package uc3m.practica;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Isaac on 17/02/2018.
 */

public class DataBase extends SQLiteOpenHelper
{
    private static final String TAG = "Database";
    private static final String TABLE_NAME = "usuarios";
    private static final String COL0 = "id";
    private static final String COL1 = "nombre";
    private static final String COL2 = "sexo";
    private static final String COL3 = "fecha";
    private static final String COL4 = "nacionalidad";
    private static final String COL5 = "localizacion";
    private static final String COL6 = "imagen";
    private static final String COL7 = "user";
    private static final String COL8 = "pass";

    public DataBase(Context context) {
        super(context, TABLE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        String createTable = "CREATE TABLE " + TABLE_NAME + "(ID INTEGER PRIMARY KEY AUTOINCREMENT, " + COL1 + " TEXT, " + COL2 + "TEXT, " +
                COL3 + " TEXT, " + COL4 + "TEXT, " + COL5 + " TEXT, " + COL6 + "TEXT, " + COL7 + " TEXT, " + COL8 + "TEXT)";
        db.execSQL(createTable);
        Log.d(TAG, "creada");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String prueba = "DROP IF TABLE EXISTS ";
        db.execSQL(prueba + TABLE_NAME);
    }

    public boolean insert(Usuario item)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        //contentValues.put(COL1, item);
        String nombre = item.getName().getTitle() + " " + item.getName().getFirst()  + " " + item.getName().getLast();
        contentValues.put(COL1, nombre);
        contentValues.put(COL2, item.getGender());
        contentValues.put(COL3, item.getRegister());
        contentValues.put(COL4, item.getNat());
        String localizacion = item.getLocation().getStreet() + " " +item.getLocation().getCity();
        contentValues.put(COL5, localizacion);
        contentValues.put(COL6, item.getPhone());
        contentValues.put(COL7, item.getLogin().getUsername());
        contentValues.put(COL8, item.getLogin().getPassword());

        long result = db.insert(TABLE_NAME, null, contentValues);

        //if date as inserted incorrectly
        if(result == -1)
        {
            return false;
        }
        else
        {
            return true;
        }
    }

    public Cursor getData()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME;
        Cursor data = db.rawQuery(query, null);
        return data;
    }
}
