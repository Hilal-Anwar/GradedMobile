package org.helal.gradedclasses.seating_plan;

public class SeatingPlanInfo {

    private final String time,_class,subject,room_no;

    public SeatingPlanInfo(String time, String _class, String subject, String room_no) {
        this.time = time;
        this._class = _class;
        this.subject = subject;
        this.room_no = room_no;
    }

    public String getTime() {
        return time;
    }

    public String get_class() {
        return _class;
    }

    public String getSubject() {
        return subject;
    }

    public String getRoom_no() {
        return room_no;
    }
}
