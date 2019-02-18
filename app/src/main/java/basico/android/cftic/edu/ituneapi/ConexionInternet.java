package basico.android.cftic.edu.ituneapi;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class ConexionInternet {


    public static boolean hayInternet(Context contexto) {
        boolean hay_internet = false;

        ConnectivityManager con_manager=(ConnectivityManager)contexto.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo ni = con_manager.getActiveNetworkInfo();

        if (ni!=null)
        {
            hay_internet = ni.isAvailable()&&ni.isConnected();
        }
        return hay_internet;
    }
}
