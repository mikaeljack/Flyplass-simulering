package no.hiof.mikaelaj.flyplass;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;
import java.util.Scanner;

//Har samarbeidet med André Skjelin Ottosen på noe av koden
public class Main {

    public static void simulation (int maxQueueLength, int timeUnits) {

        double avgWaitingTimeLanding = 0;
        double avgWaitingTimeTakeoff = 0;
        int timesEmpty = 0;
        int takeOffs = 0;
        int landings = 0;
        int allPlanes = 0;
        int declined = 0;

        //Tomme køer. Bruker Javas implementasjon
        Queue<Plane> landingQueue = new LinkedList<>();
        Queue<Plane> takeOffQueue = new LinkedList<>();

        //For antall tidssteg i simuleringen
        for(int i=0;i<timeUnits;i++) {

            System.out.println("------------------------------------------------");
            System.out.println("Time unit: " + i);

            // Trekk et tilfeldig antall nye fly som kommer for å lande
            // For hvert nytt fly som kommer for å lande
            for(int j=0;j<getPoissonRandom(0.7);j++) {
                Plane plane = new Plane(i);
                // Hvis landingskøen er full, avvis det nye flyet
                if (landingQueue.size() == maxQueueLength) {
                    System.out.println("Plane " + plane.getFlightID() + " was declined. Please try another airport");
                    allPlanes++;
                    declined++;
                }
                // Ellers sett det nye flyet sist i landingskøen
                else {
                    landingQueue.add(plane);
                    System.out.println("Plane " + plane.getFlightID() + " is added to the landing queue");
                    allPlanes++;
                }
            }
            // Trekk et tilfeldig antall nye fly som kommer for å ta av
            // For hvert nytt fly som kommer til å ta av
            for(int l=0;l<getPoissonRandom(0.7);l++) {
                Plane plane = new Plane(i);
                // Hvis avgangskøen er full, avvis det nye flyet
                if(takeOffQueue.size() == maxQueueLength) {
                    System.out.println("Plane " + plane.getFlightID() + " could not be added to the takeoff queue.");
                    allPlanes++;
                    declined++;
                }
                // Eller sett det nye flyet sist i avgangskøen
                else {
                    takeOffQueue.add(plane);
                    System.out.println("Plane " + plane.getFlightID() + " is added to the takeoff queue");
                    allPlanes++;
                }
            }
                // Hvis landingskøen ikke er tom
                // Ta ut første fly i landingskøen og la det få lande
                if (!landingQueue.isEmpty()) {
                    landings++;
                    Plane plane = landingQueue.remove();
                    avgWaitingTimeLanding += i - plane.getTimeUnitEntered();
                   int waitingTimeForLanding = i - plane.getTimeUnitEntered();
                    System.out.println(plane.toString() + " is landing. Waiting time: " + waitingTimeForLanding);
                    System.out.println("Planes in landing queue: " + landingQueue.size());
                }
                // Ellers hvis avgangskøen ikke er tom
                // Ta ut første fly i avgangskøen og la det få lande
                else if (!takeOffQueue.isEmpty()) {
                takeOffs++;
                Plane plane = takeOffQueue.remove();
                avgWaitingTimeTakeoff += i - plane.getTimeUnitEntered();
                int waitingTimeForTakeoffs = i - plane.getTimeUnitEntered();
                    System.out.println(plane.toString() +" is taking off. Waiting time: " + waitingTimeForTakeoffs);
                    System.out.println("Planes in takeoff queue: " + takeOffQueue.size());
                }
                // Ellers flyplassen er tom for fly
                else {
                landingQueue.isEmpty();
                takeOffQueue.isEmpty();
                timesEmpty++;
                    System.out.println("No activity");
                }
        }
        //Skriver ut resultater fra kjøringen
        System.out.println("------------------------------------------------");
        System.out.println("Total amount of planes: " + allPlanes);
        System.out.println("Total takeoffs: " + takeOffs);
        System.out.println("Total landings: " + landings);
        System.out.println("Total times empty: " + timesEmpty);
        System.out.println("Declined planes: " + declined);
        System.out.println("Average waiting time for takeoff: " + avgWaitingTimeTakeoff/timeUnits);
        System.out.println("Average waiting time for landings: " + avgWaitingTimeLanding/timeUnits);
        System.out.println("Planes waiting to take off: " + takeOffQueue.size());
        System.out.println("Planes waiting to land: " + landingQueue.size());
    }

    private static int getPoissonRandom(double mean) {
        Random r = new Random();
        double L = Math.exp(-mean);
        int k = 0;
        double p = 1.0;
        do
        {
            p = p * r.nextDouble();
            k++;
        } while (p > L);
        return k - 1;
        }

    public static void main(String[] args) {

        System.out.println("Please select how many time units to simulate: ");
        Scanner inputUser = new Scanner(System.in);
        String inputTimeUnits = inputUser.nextLine();

        System.out.println("Please select the maximum value for the queue: ");
        String inputQueueLimit = inputUser.nextLine();

        simulation(Integer.parseInt(inputQueueLimit),Integer.parseInt(inputTimeUnits));
    }
}
