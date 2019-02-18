package basico.android.cftic.edu.ituneapi;

import android.content.Intent;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.support.v7.widget.LinearLayoutManager;


public class MainActivity extends AppCompatActivity {
    RecyclerAdapter adapter;
    android.support.v7.widget.RecyclerView recyclerView;
    private static ImageView playButton;
    private static MediaPlayer reproductor;
    private static boolean play;
    private static ProgressBar progressBar;
    private static TextView sinConexion;
    private static BaseDatosCanciones baseDatosCanciones;

    public static ImageView getPlayButton() {return playButton;}

    public static void setPlayButton(ImageView playButton) {MainActivity.playButton = playButton;}

    public static MediaPlayer getReproductor() {return reproductor;}

    public static void setReproductor(MediaPlayer reproductor) {MainActivity.reproductor = reproductor;}

    public static boolean isPlay() {return play; }

    public static void setPlay(boolean play) { MainActivity.play = play; }

    public static BaseDatosCanciones getBaseDatosCanciones() {return baseDatosCanciones;}



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        progressBar = findViewById(R.id.progressBar);
        sinConexion = findViewById(R.id.sinConexionTxt);
        sinConexion.setVisibility(View.INVISIBLE);

        if (ConexionInternet.hayInternet(getApplicationContext())) {
            String artista = "AC/DC";
            setReproductor(new MediaPlayer());
            setPlay(false);
            //El execute llama a doInBackground de la clase de Fragment_DescargarCanciones
            new DescargarCanciones(this).execute(artista);
        }
        else
            {//Toast.makeText(getApplicationContext(),"NO HAY CONEXION A INTERNET",Toast.LENGTH_LONG).show();
            sinConexion.setVisibility(View.VISIBLE);

            progressBar.setVisibility(View.GONE);

        }
    }
    public void mostrarResultados(ResultadoCanciones rc) {   //Aqui quitamos el cargador

        progressBar.setVisibility(View.GONE);

        //Toast.makeText(getApplicationContext(), "DESCARGA COMPLETA", Toast.LENGTH_SHORT).show();

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        adapter = new RecyclerAdapter(this, rc.getResults());
        int m=rc.getResults().size();
        if (m==0) {
            Intent intent = new Intent(this, BusquedaSinResultados.class);
            startActivity(intent);
        }
        else
        {  baseDatosCanciones = new BaseDatosCanciones(this,"ITUNESBD",null,1);
            recyclerView.setAdapter(adapter);
        }
    }
}

