package basico.android.cftic.edu.ituneapi;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class DetalleCancion extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_cancion);
        TextView textView=findViewById(R.id.texto);
        Bundle loadInfo = getIntent().getExtras();
        Cancion cancion= (Cancion) loadInfo.getSerializable("cancion");
        String id=cancion.getTrackId();
        textView.setText("ID cancion Seleccionada: "+id);
    }
}
