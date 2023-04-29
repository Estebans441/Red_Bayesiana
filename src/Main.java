import entities.RedBayes;

public class Main {
    public static void main(String[] args) {
        RedBayes red = new RedBayes("Tren", "ej.txt");
        red.preds("Appointment");
    }
}