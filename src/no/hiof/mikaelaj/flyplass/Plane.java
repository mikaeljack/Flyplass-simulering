package no.hiof.mikaelaj.flyplass;

public class Plane {

    private int FlightID;
    private int timeUnitEntered;
    private static int IDCount;

    public Plane(int timeUnitEntered) {
        FlightID = IDCount;
        IDCount++;
        this.timeUnitEntered = timeUnitEntered;
    }

    public int getFlightID() {
        return FlightID;
    }

    public int getTimeUnitEntered() {
        return timeUnitEntered;
    }

    @Override
    public String toString() {
        return "Plane " + FlightID;
    }
}
