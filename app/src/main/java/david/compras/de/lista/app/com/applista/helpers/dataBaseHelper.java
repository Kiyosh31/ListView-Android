package david.compras.de.lista.app.com.applista.helpers;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by david on 14/12/2016.
 */

public class dataBaseHelper extends SQLiteOpenHelper {

    //Direccion de la db
    private static String DB_PATH = "/data/data/david.compras.de.lista.app.com.applista/databases/";

    //nombre de la db
    private static String DB_NAME = "bdistas.sqlite";
    private final Context myContext;

    private SQLiteDatabase myDataBase;

    public dataBaseHelper(Context context){
        super(context, DB_NAME, null, 1);
        this.myContext = context;
    }

    public void createDataBase() throws IOException{
        boolean dbExists = checkDataBase();
        SQLiteDatabase db_read = null;

        if(!dbExists)
        {
            db_read = this.getReadableDatabase();
            db_read.close();

            try
            {
                copyDataBase();
            }
            catch (IOException e)
            {
                throw new Error("Error copiando base de datos");
            }
        }
    }

    public boolean checkDataBase(){
        SQLiteDatabase checkDb = null;

        try
        {
            String myPath = DB_PATH + DB_NAME;
            checkDb = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READWRITE);
        }
        catch (Exception e)
        {
            File dbFile = new File(DB_PATH + DB_NAME);
            return dbFile.exists();
        }

        if(checkDb != null)
        {
            checkDb.close();
            return true;
        }
        else
        {
            return false;
        }
    }

    private void copyDataBase() throws IOException{
        InputStream myInput = myContext.getAssets().open(DB_NAME);

        String outFileName = DB_PATH + DB_NAME;
        OutputStream myOutput = new FileOutputStream(outFileName);

        byte[] buffer = new byte[2014];
        int length;

        while ((length = myInput.read(buffer)) != -1)
        {
            if(length > 0)
            {
                myOutput.write(buffer, 0, length);
            }
        }

        myOutput.flush();
        myOutput.close();
        myInput.close();
    }

    public void openDataBase() throws SQLException{
        String myPath = DB_PATH + DB_NAME;
        myDataBase = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);
    }

    public synchronized void close(){

        if(myDataBase != null)
        {
            myDataBase.close();
        }

        super.close();
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        //Codigo para la creacion de la DB
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        //Codigo de actualizacion
        try
        {
            createDataBase();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public Cursor fetchAllList() throws SQLException{

        //rawQUery = hace querys a la DB
        Cursor myCursor = myDataBase.rawQuery("SELECT * FROM listas", null);

        if(myCursor != null)
        {
            myCursor.moveToFirst();
        }

        return myCursor;
    }

    public Cursor fetchItemsList(String lista) throws SQLException{
        Cursor cursor = myDataBase.rawQuery("SELECT * FROM" + lista, null);

        if(cursor != null)
        {
            cursor.moveToFirst();
        }

        return cursor;
    }
}
