package entities;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
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

    public Double prob(String valObj, String evDad, ArrayList<NodoBayes> visitados){
        double res = 0.0;

        // Valor objetivo del nodo actual
        valObj = valObj.split("=")[1];

        // Se agrega a nodos visitados
        visitados.add(this);

        // Si no tiene predecesores
        if(predecesores.isEmpty()) {
            return probabilidades.get(valObj).get("");
        }

        // Separa los eventos dados por coma y los almacena en un ArrayList
        ArrayList<String> eventosDados = new ArrayList<>();
        Collections.addAll(eventosDados, evDad.split(","));

        // Filas que coinciden con los eventos dados
        HashMap<String, Double> obj = probabilidades.get(valObj);
        ArrayList<String> evDadosCoin = new ArrayList<>(obj.keySet());
        evDadosCoin.removeIf(e -> !coincideEventos(e, eventosDados));

        // Por cada evento dado que coincida
        for(String e : evDadosCoin){
            ArrayList<NodoBayes> visitadosE = new ArrayList<>(visitados);
            // Probabilidad parcial
            Double prob = probabilidades.get(valObj).get(e);
            /* Impresion de los datos (para fines de prueba)
            System.out.println("Nodo: " + this.getNombre());
            System.out.println("Evento Obj: " + valObj);
            System.out.println("Eventos dados: " + e);
            System.out.println("Prob = " + prob);
            System.out.println("------------------");*/

            // Visita los nodos predecesores
            for(NodoBayes n : predecesores){
                String evDadoPred = "";
                ArrayList<String> evsObjPred = new ArrayList<>();

                // Se toman los eventos dados (de la tabla del nodo actual) y se aregan a los eventos dados para
                // la consulta del nodo predecesor
                ArrayList<String> eventos = new ArrayList<>(Arrays.asList(e.split(",")));
                for(String evDado : eventosDados)
                    if(!eventos.contains(evDado))
                        eventos.add(evDado);

                // Si los eventos dados incluyen al nodo, significa que se le calcula la prob de algun valor especifico
                if(e.contains(n.getNombre())){
                    // Se arma la query para el nodo predecesor tomando como valor obj el que se encuentra dentro de
                    // los eventos dados
                    for(String ei : eventos){
                        if(ei.contains(n.getNombre()))
                            evsObjPred.add(ei);
                        else
                            evDadoPred = evDadoPred + ei + ",";
                    }
                    // Se elimina la ultima coma
                    if(evDadoPred.length() > 0)
                        evDadoPred = evDadoPred.substring(0,evDadoPred.length()-1);
                }

                // Una vez armada la query se procede a calcular el valor de probabilidad de esta
                if(!visitadosE.contains(n)){
                    for(String eOb : evsObjPred){
                        Double probPred = n.prob(eOb, evDadoPred, visitadosE);
                        prob *= probPred;
                    }
                }
            }
            // Se suma el valor de esta probabilidad a la probabilidad total
            res += prob;
        }
        return res;
    }

    public boolean coincideEventos(String str, ArrayList<String> substrings) {
        for (String substr : substrings) {
            for(NodoBayes n : predecesores){
                if (substr.contains(n.getNombre()) && !str.contains(substr)) {
                    return false;
                }
            }
        }
        return true;
    }

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
    protected ArrayList<String> getValores(){
        return new ArrayList<>(probabilidades.keySet());
    }

    public String getNombre() {
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
