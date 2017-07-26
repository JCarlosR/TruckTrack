package com.programacionymas.trucktrack.model;

public class Travel {

    private int id;
    private String route_name;
    private String departure_date;
    private String departure_time;
    private int user_id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRouteName() {
        return route_name;
    }

    public void setRouteName(String route_name) {
        this.route_name = route_name;
    }

    public String getDepartureDate() {
        return departure_date;
    }

    public void setDepartureDate(String departure_date) {
        this.departure_date = departure_date;
    }

    public String getDepartureTime() {
        return departure_time;
    }

    public void setDepartureTime(String departure_time) {
        this.departure_time = departure_time;
    }

    public int getUserId() {
        return user_id;
    }

    public void setUserId(int user_id) {
        this.user_id = user_id;
    }
}
