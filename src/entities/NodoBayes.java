package entities;
import java.util.ArrayList;
import java.util.HashMap;

public class NodoBayes {
    private String nombre;
    private HashMap<String, HashMap<String, Double>> probabilidades;
    private ArrayList<NodoBayes> predecesores;

    // Constructores
    protected NodoBayes() {
    }

    public NodoBayes(String nombre, ArrayList<String> valores) {
        this.nombre = nombre;
        this.probabilidades = new HashMap<>();
        this.predecesores = new ArrayList<>();
        for(String v : valores)
            probabilidades.put(v, new HashMap<>());
    }

    public NodoBayes(String nombre, HashMap<String, HashMap<String, Double>> probabilidades) {
        this.nombre = nombre;
        this.probabilidades = probabilidades;
        this.predecesores = new ArrayList<>();
    }

    // Metodos Funcionales
    protected void addPredecesor(NodoBayes pred){
        predecesores.add(pred);
    }

    // Add probabilidad
    protected void addProb(String req, HashMap<String, Double> vals){
        for (String clave:vals.keySet()) {
            Double valor = vals.get(clave);
            probabilidades.get(clave).put(req, valor);
        }
    }

    protected void printProbs(){
        for (String clave:probabilidades.keySet()) {
            HashMap<String, Double> valor = probabilidades.get(clave);
            System.out.println("PROBABILIDAD DE " + clave + " DADO:");
            for(String clave2: valor.keySet()) {
                System.out.println(clave2 + " = " + String.valueOf(valor.get(clave2)));
            }
        }
    }

    // Getters y Setters
    protected String getNombre() {
        return nombre;
    }

    protected void setNombre(String nombre) {
        this.nombre = nombre;
    }

    protected HashMap<String, HashMap<String, Double>> getProbabilidades() {
        return probabilidades;
    }

    protected void setProbabilidades(HashMap<String, HashMap<String, Double>> probabilidades) {
        this.probabilidades = probabilidades;
    }

    protected ArrayList<NodoBayes> getPredecesores() {
        return predecesores;
    }

    protected void setPredecesores(ArrayList<NodoBayes> predecesores) {
        this.predecesores = predecesores;
    }
}
