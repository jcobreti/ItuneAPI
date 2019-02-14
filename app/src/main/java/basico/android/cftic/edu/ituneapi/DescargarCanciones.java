package basico.android.cftic.edu.ituneapi;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.UnknownHostException;

public class DescargarCanciones extends AsyncTask<String, Void, ResultadoCanciones> {

    //Por ser Asynctask tiene Tres parametros: que envio, si quiero controlar el tiempo y que recibo
    //Envio un String que es la url
    //El segundo parametro es para visualizar lo que queda y no lo voy a usar es un número que va de "0" a "100"
    //El tercero es que recibo en este caso es resultado Canciones que es la clase que he creado
    //https://itunes.apple.com/search/?media=music&term=chiquetete
    //http://jsonviewer.stack.hu/  --> con esto veo el JSON

    private static final String URI_ITUNES="https://itunes.apple.com/search/?media=music&limit=20&term=";

    //Lo siguiente que hago es obtener una referencia de la pantalla de la que vengo para poder llamar a un metodo suyo y poder enviarla datos.
    private MainActivity mainActivity;
    public DescargarCanciones (MainActivity mA) { this.mainActivity=mA; }



    @Override
    protected ResultadoCanciones doInBackground(String... canciones) {
        //Advance REST CLIENT para CHROME
        //Este es el que hace la conexion, recibe los datos y luego llama a "onPostExecute" de esta misma clase.
        ResultadoCanciones resultadoCanciones=null;

        //Preparo la conexion
        URL url=null;
        HttpURLConnection httpURLConnection=null;
        InputStreamReader iSR=null;

        //Puede que no haya conexion de internet--> ponemos un try catch con finally
        try
            {url=new URL(URI_ITUNES+canciones[0]);
            httpURLConnection=(HttpURLConnection) url.openConnection();
            if (httpURLConnection.getResponseCode()==httpURLConnection.HTTP_OK)
                {  //Get type MIME para identificar que tipo de informacion me viene
                    Log.d  ("JNG", "MIME-->"+httpURLConnection.getContentType());


                    //Respuesta correcta
                    //Accedo al cuerpo del mensaje que es un input (entrada) y es Stream-->get Input Stream
                    //Con httpURLConnection.getInputStream()
                    //Como devuelve un inputStreamReader
                    //preparo la lectura
                    iSR=new InputStreamReader(httpURLConnection.getInputStream());

                    //Ahora vamos a usar GSON para manipular el JSON
                 //Build>Edit Libraries and dependencies>+
                    Gson gson=new Gson();

                    //Leo el JSON de iSR y parseandolo como ResultadoCanciones.class se lo asigno a resultadoCanciones
                    //Que era un resultado y una lista de canciones
                  resultadoCanciones=gson.fromJson(iSR,ResultadoCanciones.class);
                    //Ya lo tenemos en formato ResultadoCanciones
                }
           }
        catch (UnknownHostException e)
            {
                Log.d ("JNG","NO HAY CONEXION INTERNET");
            }
           catch (Exception e)
            {Log.d("JNG","ERROR LLAMADA A ITUNES-->"+e);

            }
        finally
            {   //Esto siempre lo hace,
                // lo usaremos para liberar recursos
                //1.- el iSR
                //iSR.close puede fallar tambien entonces lo tengo que meter en un try-+-catch
                try {
                    iSR.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                //2.- la conexion http
                httpURLConnection.disconnect();
            }

        return resultadoCanciones;
    }

    @Override
    protected void onPostExecute(ResultadoCanciones resultadoCanciones) {

        //Este es ejecutado cuando acaba doInBackground y aqui es donde hay que actualziar los layouts
        super.onPostExecute(resultadoCanciones);
        Gson gson=new GsonBuilder().setPrettyPrinting().create();
        String stringCanciones=gson.toJson(resultadoCanciones);
        mainActivity.mostrarResultados(resultadoCanciones);
        Log.d("JNG","CANCIONES= "+stringCanciones);

        //OBS si le paso CONTEXT en vez de MainActivity y lo guardo como context
       /*
          if (this.context instanceof MainActivity)
            {
                MainActivity ma=(MainActivity) this.context;
                ma.mostrarResultados(resultadoCanciones);
            }
       */
    }
}
