package fr.fengdavid.matchplayer.structs;

import android.os.Parcel;
import android.os.Parcelable;

public class Event implements Parcelable {

    private String name;
    private String sport;
    private String place;
    private String date;
    private int nPlayers;
    private int maxPlayers;
    private int id;

    public Event(String name, String sport, String place, String date, int nPlayers, int maxPlayers, int id){
        this.name = name;
        this.sport = sport;
        this.place = place;
        this.date = date;
        this.nPlayers = nPlayers;
        this.maxPlayers = maxPlayers;
        this.id=id;
    }


    protected Event(Parcel in) {
        name = in.readString();
        sport = in.readString();
        place = in.readString();
        date = in.readString();
        nPlayers = in.readInt();
        maxPlayers = in.readInt();
        id = in.readInt();
    }

    public static final Creator<Event> CREATOR = new Creator<Event>() {
        @Override
        public Event createFromParcel(Parcel in) {
            return new Event(in);
        }

        @Override
        public Event[] newArray(int size) {

            return new Event[size];
        }
    };

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

    public int getId() { return id; }

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

    public void setId(int id) { this.id=id; }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(sport);
        dest.writeString(place);
        dest.writeString(date);
        dest.writeInt(nPlayers);
        dest.writeInt(maxPlayers);
        dest.writeInt(id);
    }
}
