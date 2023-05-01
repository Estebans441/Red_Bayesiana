package entities;

import java.io.FileNotFoundException;
import java.util.*;
import java.io.File;

public class RedBayes {
    private String nombre; // Nombre descriptivo de la red
    private ArrayList<NodoBayes> nodos; // Nodos

    // Constructores

    public RedBayes(String nombre) {
        this.nombre = nombre;
        this.nodos = new ArrayList<>();
    }

    public RedBayes(String nombre, ArrayList<NodoBayes> nodos) {
        this.nombre = nombre;
        this.nodos = nodos;
    }

    public RedBayes(String nombre, String file) {
        this.nombre = nombre;
        this.nodos = new ArrayList<>();
        try {
            nodosFromArchivo(file);
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }

    // Metodos Funcionales
    public Double Prob(String query){
        Double res = 0.0;
        // NODO=VALOR | NODOX=X,NODOY=Y
        query = query.replace(" ", "");

        // Nodo objetivo para calcular su probabilidad
        String nombreObj = query.split("\\|")[0].split("=")[0];
        String valObj = query.split("\\|")[0];
        NodoBayes objetivo = nombreToNodo(nombreObj);

        String evDado = "";
        if(query.split("\\|").length > 1)
            evDado = query.split("\\|")[1];
        ArrayList<NodoBayes> visitados = new ArrayList<>();
        res = objetivo.prob(valObj, evDado, visitados);


        return res;
    }


    public void addNodo(String nombre, ArrayList<String> valores) {
        nodos.add(new NodoBayes(nombre, valores));
    }

    public void addNodo(NodoBayes nodo) {
        nodos.add(nodo);
    }


    public NodoBayes nombreToNodo(String nNodo) {
        return nodos.stream()
                .filter(nod -> nod.getNombre().equals(nNodo))
                .findAny().orElse(null);
    }

    public void addArco(String origen, String dest) {
        NodoBayes n = nombreToNodo(dest);
        int i = nodos.indexOf(n);
        n.addPredecesor(nombreToNodo(origen));
        nodos.set(i, n);
    }


    // Imprime recursivamente los nodos de predecesores
    public void preds(NodoBayes nodo, ArrayList<NodoBayes> visitados) {
        System.out.println(nodo.getNombre());
        for (NodoBayes n : nodo.getPredecesores()) {
            if(!visitados.contains(n)) {
                visitados.add(n);
                preds(n, visitados);
            }
        }
    }

    public void preds(String nNodo) {
        NodoBayes nodo = nombreToNodo(nNodo);
        System.out.println(nodo.getNombre());
        ArrayList<NodoBayes> visitados = new ArrayList<>();
        for (NodoBayes n : nodo.getPredecesores()) {
            if(!visitados.contains(n)) {
                visitados.add(n);
                preds(n, visitados);
            }
        }
    }


    // Inicializa los nodos de acuerdo a un archivo de texto dado
    private void nodosFromArchivo(String file) throws FileNotFoundException {
        // Abrir el archivo de texto
        File archivo = new File(file);
        Scanner lector = new Scanner(archivo);

        // Variables para almacenar los valores de cada tabla
        String nombreTabla = "";
        String dep = "";
        ArrayList<String> valores = new ArrayList<>();
        HashMap<String, Double> probs = new HashMap<>();
        NodoBayes n = new NodoBayes();

        // Leer el archivo línea por línea
        while (lector.hasNextLine()) {
            String linea = lector.nextLine().trim();
            // Si la línea es un punto, significa que se ha terminado la tabla actual
            if (linea.equals(".")) {
                nodos.add(n);
                if(!dep.equals("")){
                    String[] dependencias = dep.split(",");
                    for(String d : dependencias)
                        addArco(d, nombreTabla);
                }
                continue;
            }

            // Si la línea no es un guión, significa que es una línea de valores
            String[] valoresLinea = linea.split(";");
            if (valoresLinea.length == 1) {
                // La primera línea de la tabla contiene solo el nombre de la tabla
                nombreTabla = valoresLinea[0];
                // La siguiente linea contiene los nombres de las columnas
                linea = lector.nextLine().trim();
                // Nodos de los que depende
                dep = linea.split(";")[0];
                if(dep.equals("-"))
                    dep = "";
                // Valores del nodo
                valores = new ArrayList<>(Arrays.asList(linea.split(";")[1].split(",")));
                // Creacion del nodo
                n = new NodoBayes(nombreTabla, valores);
            } else {
                // Eventos de la probabilidad
                String ev = valoresLinea[0];
                // Si no tiene dependencias, no tiene eventos
                if(ev.equals("-"))
                    ev = "";
                // Los valores de probabilidad para los eventos anteriores
                String[] vals = valoresLinea[1].split(",");
                probs = new HashMap<>();
                // Añade la probabilidad para cada valor de la VA
                for(int i = 0 ; i < vals.length ; i++)
                    probs.put(valores.get(i), Double.valueOf(vals[i]));
                n.addProb(ev, probs);
            }
        }

        // Cerrar el archivo
        lector.close();
    }

    // Getters y Setters
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public ArrayList<NodoBayes> getNodos() {
        return nodos;
    }

    public void setNodos(ArrayList<NodoBayes> nodos) {
        this.nodos = nodos;
    }

    public void setNodos(String file) {
        this.nodos = new ArrayList<>();
        try {
            nodosFromArchivo(file);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
