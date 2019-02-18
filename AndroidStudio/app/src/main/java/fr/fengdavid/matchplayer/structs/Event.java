package fr.fengdavid.matchplayer.structs;

public class Event {

    private String name;
    private String sport;
    private String place;
    private String date;
    private int nPlayers;
    private int maxPlayers;

    public Event(String name, String sport, String place, String date, int nPlayers, int maxPlayers){
        this.name = name;
        this.sport = sport;
        this.place = place;
        this.date = date;
        this.nPlayers = nPlayers;
        this.maxPlayers = maxPlayers;
    }


    public String getName() {
        return name;
    }

    public String getSport() {
        return sport;
    }

    public String getPlace() {
        return place;
    }

    public String getDate() {
        return date;
    }

    public int getnPlayers() { return nPlayers; }

    public int getMaxPlayers() { return maxPlayers; }

    public void setName(String name) {
        this.name = name;
    }

    public void setSport(String sport) {
        this.sport = sport;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public void setDate(String date ) {
        this.date = date;
    }

    public void setnPlayers(int nPlayers) {
        this.nPlayers = nPlayers;
    }

    public void setMaxPlayers(int maxPlayers) {
        this.maxPlayers = maxPlayers;
    }


}
