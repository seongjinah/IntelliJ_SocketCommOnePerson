package object;

import java.io.Serializable;

public class UserInfo implements Serializable {

    private int p_id;
    private String login_id;
    private String login_password;
    private String name;
    private String building;
    int floor;
    boolean self_monitor;

    private static final long serialVersionUID = 1L;

    public UserInfo(){
        this.p_id = -1;
    }

    public UserInfo(int p_id, String login_id, String login_password, String name, String building, int floor, boolean self_monitor){
        this.p_id = p_id;
        this.login_id = login_id;
        this.login_password = login_password;
        this.name = name;
        this.building = building;
        this.floor = floor;
        this.self_monitor = self_monitor;
    }

    public String toString(){
        return "p_id: " +  p_id + "\nid: " + login_id + "\npassword: " + login_password +
                "name: "+ name + "\nbuilding: " + building + "\nfloor: " + floor + "\nmonitor: " + self_monitor + "\n-------------\n";
    }

    public void print(){
        System.out.println("p_id: "+ p_id + "\nid: " + login_id + "\npassword: " + login_password);
        System.out.println("name: "+ name + "\nbuilding: " + building + "\nfloor: " + floor + "\nmonitor: " + self_monitor + "\n-------------\n");
    }

    public int getP_id() {
        return p_id;
    }

    public void setP_id(int p_id) {
        this.p_id = p_id;
    }

    public String getLogin_id() {
        return login_id;
    }

    public void setLogin_id(String login_id) {
        this.login_id = login_id;
    }

    public String getLogin_password() {
        return login_password;
    }

    public void setLogin_password(String login_password) {
        this.login_password = login_password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBuilding() {
        return building;
    }

    public void setBuilding(String building) {
        this.building = building;
    }

    public int getFloor() {
        return floor;
    }

    public void setFloor(int floor) {
        this.floor = floor;
    }

    public boolean isSelf_monitor() {
        return self_monitor;
    }

    public void setSelf_monitor(boolean self_monitor) {
        this.self_monitor = self_monitor;
    }
}