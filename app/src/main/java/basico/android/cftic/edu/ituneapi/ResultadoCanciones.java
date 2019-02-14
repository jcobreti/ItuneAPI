package basico.android.cftic.edu.ituneapi;

import java.util.List;

public class ResultadoCanciones {

    //Estas dos variables son las que tiene el JSON y se tiene que llamar TAL CUAL

    private int resulCount;
    private List <Cancion> results;

    //Implementamos los GETTER y SETTER

    public int getResulCount() { return resulCount; }

    public void setResulCount(int resulCount) { this.resulCount = resulCount; }

    public List<Cancion> getResults() { return results; }

    public void setResults(List<Cancion> results) { this.results = results; }

}
