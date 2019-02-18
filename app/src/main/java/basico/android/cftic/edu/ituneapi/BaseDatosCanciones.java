package basico.android.cftic.edu.ituneapi;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class BaseDatosCanciones extends SQLiteOpenHelper {

    private final String sql = "CREATE TABLE FAVORITOS ( ID_ARTISTA INTEGER, ARTISTNAME TEXT,TRACKNAME TEXT,TRACKID TEXT,ARTWORKURL100 TEXT, PREVIEWURL TEXT,COLLECTIONNAME TEXT)";

    public BaseDatosCanciones(Context contexto, String nombre, SQLiteDatabase.CursorFactory factory, int version) {
        super(contexto, nombre, factory, version); //el método padre, llamará a Oncreate o OnUpgrade, segn corresponda)
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // creamos la tabla de la bbdd
        db.execSQL(sql);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    // cerramos la bbdd
    private void cerrarBaseDatos(SQLiteDatabase db){
        db.close();
    }

    // cogemos el objeto cancion pasado como parametro y lo insertamos en la tabla
    public void insertarCancion(Cancion cancion)
    {
        // buscamos que no este ya el registro en la tabla
        boolean encontrado = false;

        String trackId = cancion.getTrackId();
        encontrado = buscarCancion(trackId);
        // si no lo hemos encontrado insertamos , en caso contrario no hacemos nada ya que ya estaria en la tabla de favoritos ( y se supone que los datos no han variado)
        if (encontrado == false) {
            SQLiteDatabase db = this.getWritableDatabase();
            db.execSQL("INSERT INTO FAVORITOS (ID_ARTISTA, ARTISTNAME,TRACKNAME,TRACKID,ARTWORKURL100,PREVIEWURL, COLLECTIONNAME) " +
                    "VALUES (" + cancion.getArtistId() + ", '" + cancion.getArtistName() + "' , '" + cancion.getTrackName() + "', '" + cancion.getTrackId() + "','" + cancion.getArtworkUrl100() + "', '"
                    + cancion.getPreviewUrl() + "', '" + cancion.getCollectionName() + "')");
            this.cerrarBaseDatos(db);
            Log.d("MIAPP","se ha insertado el registro con clave " + cancion.getArtistId());
        }
        else {
            Log.d("MIAPP", "el registro ya estaba en la tabla");
        }
    }

    // eliminamos la cancion solicitada
    public void eliminarCancion(String id_trackID)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM FAVORITOS WHERE TRACKID = " + id_trackID);
        this.cerrarBaseDatos(db);
        Log.d("MIAPP","registro eliminado clave: " + id_trackID);
    }

    // borramos todos los datos de la tabla
    public  void eliminarTodos()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM FAVORITOS");
        this.cerrarBaseDatos(db);
        Log.d("MIAPP","tabla borrada");
    }

    // buscamos la cancion solicitada, si esta en favoritos devuelve true
    public boolean buscarCancion(String id_trackID)
    {
        String sql = "SELECT * FROM FAVORITOS WHERE TRACKID = " + id_trackID;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(sql, null);
        boolean resultado = false;

        if( cursor != null && cursor.getCount() >0)
        {
            resultado = true;
            Log.d("MIAPP","registro encontrado");
        }
        else
        {
            resultado = false;
            Log.d("MIAPP","registro no encontrado");
        }
        cursor.close();
        this.cerrarBaseDatos(db);
        return resultado;

    }

    // devolvemos una lista de objetos cancion con todos los registros de la tabla
    public List<Cancion> cargarFavoritos()
    {
        List<Cancion> lista = null;
        String sql = "SELECT * FROM FAVORITOS";
        String artistName;
        String trackName;
        String trackId;
        int artistId;
        String artworkUrl100;
        String previewUrl;
        String collectionName;
        Cancion cancion_aux = null;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(sql,null);

        if (cursor != null && cursor.getCount() > 0)
        {
            lista = new ArrayList<Cancion>(cursor.getCount());
            cursor.moveToFirst();

            do {

                artistId = cursor.getInt(0);
                artistName = cursor.getString(1);
                trackName = cursor.getString(2);
                trackId = cursor.getString(3);
                artworkUrl100 = cursor.getString(4);
                previewUrl = cursor.getString(5);
                collectionName = cursor.getString(6);

                cancion_aux = new Cancion(artistName,trackName,artistId,artworkUrl100,previewUrl,trackId,collectionName);

                lista.add(cancion_aux);



            } while (cursor.moveToNext());


        }
        cursor.close();
        cerrarBaseDatos(db);
        return lista;

    }
}
