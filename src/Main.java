import entities.RedBayes;

public class Main {
    public static void main(String[] args) {
        RedBayes red = new RedBayes("Tren", "ej.txt");
        System.out.println(red.Prob("Appointment=Attend | Maintenance=No, Rain=Light"));

        RedBayes red2 = new RedBayes("Lluvia", "ej2.txt");
        System.out.println(red2.Prob("HierbaHumeda=T | Rociador=F, Lluvia=T"));
    }
}