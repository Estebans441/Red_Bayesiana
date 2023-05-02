import entities.RedBayes;

public class Main {
    public static void main(String[] args) {
        // Prueba con ejemplo dado en clase (ej.txt)
        System.out.println("------------------------------");
        System.out.println("Red de probabilidad de Tren (ej.txt)");
        System.out.println("------------------------------");
        RedBayes red = new RedBayes("Tren", "ej.txt");
        // Probabilidad sin condicionales
        System.out.println("P(Appointment = Attend)=");
        System.out.println(red.Prob("Appointment=Attend"));
        System.out.println("P(Appointment = Miss)=");
        System.out.println(red.Prob("Appointment=Miss"));
        // Comprobación (la suma de la probabilidad los valores de la VA debe sumar uno)
        System.out.println("P(Appointment = Attend) + P(Appointment = Miss) = ");
        System.out.println(red.Prob("Appointment=Attend")+red.Prob("Appointment=Miss"));

        // Probabilidad con condicionales
        System.out.println("P(Appointment=Attend | Maintenance=No, Rain=Light)=");
        System.out.println(red.Prob("Appointment=Attend | Maintenance=No, Rain=Light"));
        System.out.println("P(Appointment=Miss | Maintenance=No, Rain=Light)=");
        System.out.println(red.Prob("Appointment=Miss | Maintenance=No, Rain=Light"));

        // Prueba con otro ejemplo (en el archivo ej2.txt)
        System.out.println("------------------------------");
        System.out.println("Red de probabilidad de Lluvia (ej2.txt)");
        System.out.println("------------------------------");
        RedBayes red2 = new RedBayes("Lluvia", "ej2.txt");
        System.out.println("P(HierbaHumeda=T | Rociador=F, Lluvia=T)=");
        System.out.println(red2.Prob("HierbaHumeda=T | Rociador=F, Lluvia=T"));

    }
}