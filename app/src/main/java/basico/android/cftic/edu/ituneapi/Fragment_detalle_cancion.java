package basico.android.cftic.edu.ituneapi;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class Fragment_detalle_cancion extends Fragment {


    public Fragment_detalle_cancion() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v=inflater.inflate(R.layout.fragment_detalle_cancion, container, false);
        TextView textView=v.findViewById(R.id.texto);

        Bundle loadInfo = getActivity().getIntent().getExtras();
        Cancion cancion= (Cancion) loadInfo.getSerializable("cancion");
        String id=cancion.getTrackId();
        textView.setText("ID cancion Seleccionada: "+id);
        return v;
    }
}

