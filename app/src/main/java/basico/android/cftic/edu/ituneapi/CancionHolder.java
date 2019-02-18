package basico.android.cftic.edu.ituneapi;

import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;


public class CancionHolder extends android.support.v7.widget.RecyclerView.ViewHolder
{
    private TextView id, artista, cancion;
    private ImageView imageView,imagenMedia,imagenPlaying,imageFavorito;
    private Context contexto;
    private String cancionURL;
    private boolean playingThis,play,favorita;
    private Cancion cancionObj;
    private MediaPlayer mp;
    private static BaseDatosCanciones baseDatosCanciones;


    //CONSTRUCTOR


    CancionHolder(final View itemView, final Context contexto) {
        super(itemView);
        this.id = itemView.findViewById(R.id.id);
        this.artista = itemView.findViewById(R.id.artista);
        this.cancion = itemView.findViewById(R.id.cancion);
        this.imageView = itemView.findViewById(R.id.imageView);
        this.imagenMedia = itemView.findViewById(R.id.imagenMedia);
        this.imageFavorito=itemView.findViewById(R.id.imagenFavorito);
        this.contexto=contexto;
        this.favorita=false;

        this.cancionURL="";
        this.play=false;
        this.playingThis=false;

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //irDetalle();
            }});

        imageFavorito.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (favorita)
                {
                    Picasso.with(contexto).load(R.drawable.faviconoff).into(imageFavorito);
                    baseDatosCanciones=Fragment_main.getBaseDatosCanciones();
                    baseDatosCanciones.eliminarCancion(cancionObj.getTrackId());
                }
                else
                    {
                    Picasso.with(contexto).load(R.drawable.faviconon).into(imageFavorito);
                    baseDatosCanciones=Fragment_main.getBaseDatosCanciones();
                    baseDatosCanciones.insertarCancion(cancionObj);
                }
                favorita=!favorita;
             }
        });

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                irDetalle();
            }});

        imagenMedia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Toast.makeText(contexto,"En el PLAY",Toast.LENGTH_SHORT).show();

                   mp=Fragment_main.getReproductor();
                   imagenPlaying=Fragment_main.getPlayButton();
                   play=playingThis;

                   if (Fragment_main.isPlay())
                    {   pararCancion(v);
                        if (!play)
                            {//Pulso play en cancion nueva
                                reproducirCancion(v);
                            }
                    }
                   else {
                        reproducirCancion(v);
                    }
                }
        });
    }
private void pararCancion(View v)
{   //Esta sonando y hay que pararlo
    //Toast.makeText(contexto,"NO",Toast.LENGTH_SHORT).show();
   try {
       mp.stop();
       mp.reset();
       playingThis = false;
       Fragment_main.setPlay(false);

       if (imagenPlaying != null)
           Picasso.with(contexto).load(android.R.drawable.ic_media_play).into(imagenPlaying);

       Picasso.with(contexto).load(android.R.drawable.ic_media_play).into((ImageView) v);
   }catch (Exception e)
        {
            Log.e("Error" , "EXCEPCION al reproducir", e);
        }
}
private void reproducirCancion (View v)
{
    //No esta reproduciendo -->play
    //Toast.makeText(contexto,"SI",Toast.LENGTH_SHORT).show();

   try {
       mp.setDataSource(cancionURL);

       mp.prepare();
       mp.setVolume(100, 100);
       playingThis = true;
       Fragment_main.setPlay(true);
       mp.start();

       Fragment_main.setPlayButton((ImageView) v);
       Picasso.with(contexto).load(android.R.drawable.ic_media_pause).into((ImageView) v);
   }
   catch (IllegalStateException  e)
   {
       Log.e("Error" , "ILLEGAL al reproducir", e);
   }
   catch (Exception e)
   {
       Log.e("Error" , "EXCEPCION al reproducir", e);
   }
}

//ACTUALIZAR HOLDER
public void actualizarHolder (CancionHolder cancionHolder, Cancion c)
    {   //TODO ACTUALIZAR TODOS LOS CAMPOS
        String imagenTxt=c.getArtworkUrl100();
        cancionHolder.id.setText(c.getTrackId());
        cancionHolder.artista.setText(c.getArtistName());
        cancionHolder.cancion.setText(c.getTrackName());
        cancionHolder.cancionURL=c.getPreviewUrl();
        cancionHolder.cancionObj=c;
        try
            {
                this.favorita=Fragment_main.getBaseDatosCanciones().buscarCancion(cancionObj.getTrackId());
            }
        catch (Exception e)
            {
            this.favorita=false;
            }

        if (this.favorita)
            Picasso.with(contexto).load(R.drawable.faviconon).into(imageFavorito);
        else
            Picasso.with(contexto).load(R.drawable.faviconoff).into(imageFavorito);

        Picasso.with(contexto).load(imagenTxt).into(imageView);

    }
    private void irDetalle()
    {   //Toast.makeText(contexto,"Ir DETALLE",Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(contexto,DetalleCancion.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("cancion",cancionObj);
        intent.putExtras(bundle);
        //Detengo la cancion y pongo el boton de PAUSE a PLAY
        pararCancion(Fragment_main.getPlayButton());

        contexto.startActivity(intent);
    }
}//FIN CLASE CANCIONHOLDER
