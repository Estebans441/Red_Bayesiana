import entities.RedBayes;

public class Main {
    public static void main(String[] args) {
        RedBayes red = new RedBayes("Tren", "ej.txt");
        System.out.println(red.Prob("Appointment=Attend | Maintenance=No, Rain=Light"));

    }
}