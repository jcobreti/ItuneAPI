package basico.android.cftic.edu.ituneapi;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.media.MediaPlayer;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

public class Fragment_main extends Fragment  {
    RecyclerAdapter adapter;
    android.support.v7.widget.RecyclerView recyclerView;
    private static ImageView playButton;
    private static MediaPlayer reproductor;
    private static boolean play;
    private static ProgressBar progressBar;
    private static TextView sinConexion;
    private static BaseDatosCanciones baseDatosCanciones;
    private static View vista;

    public static ImageView getPlayButton() {return playButton;}

    public static void setPlayButton(ImageView playButton0) {playButton = playButton0;}

    public static MediaPlayer getReproductor() {return reproductor;}

    public static void setReproductor(MediaPlayer reproductor0) {reproductor = reproductor0;}

    public static boolean isPlay() {return play; }

    public static void setPlay(boolean play0) { play = play0; }

    public static BaseDatosCanciones getBaseDatosCanciones() {return baseDatosCanciones;}

    public Fragment_main() { }

    @Override
    public View onCreateView (LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        vista=inflater.inflate(R.layout.fragment_main, container, false);

        progressBar = vista.findViewById(R.id.progressBar);
        sinConexion = vista.findViewById(R.id.sinConexionTxt);
        sinConexion.setVisibility(View.INVISIBLE);

        if (ConexionInternet.hayInternet(getContext())) {
            String artista = "AC/DC";
            setReproductor(new MediaPlayer());
            setPlay(false);
            //El execute llama a doInBackground de la clase de Fragment_DescargarCanciones
            new Fragment_DescargarCanciones(this).execute(artista);
        }
        else {//Toast.makeText(getApplicationContext(),"NO HAY CONEXION A INTERNET",Toast.LENGTH_LONG).show();
            sinConexion.setVisibility(View.VISIBLE);
            }
        progressBar.setVisibility(View.GONE);
        return vista;
    }

    public void mostrarResultados(ResultadoCanciones rc) {   //Aqui quitamos el cargador

        progressBar.setVisibility(View.GONE);
        //Toast.makeText(getApplicationContext(), "DESCARGA COMPLETA", Toast.LENGTH_SHORT).show();

        recyclerView = vista.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(vista.getContext()));
        adapter = new RecyclerAdapter(vista.getContext(), rc.getResults());
        int m=rc.getResults().size();
        if (m==0) {
            Intent intent = new Intent(vista.getContext(), BusquedaSinResultados.class);
            startActivity(intent);
        }
        else
        {  baseDatosCanciones = new BaseDatosCanciones(vista.getContext(),"ITUNESBD",null,1);
            recyclerView.setAdapter(adapter);
        }
    }

}
