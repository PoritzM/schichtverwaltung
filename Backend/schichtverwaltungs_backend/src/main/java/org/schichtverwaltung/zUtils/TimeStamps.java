package org.schichtverwaltung.zUtils;

import java.util.Date;

import static org.schichtverwaltung.zUtils.GetTimeStamp.getTimeStamp;

public class TimeStamps {
    private Date timeStampCreate;
    private Date timeStampEdit;

    public TimeStamps() {
        timeStampCreate = getTimeStamp();
        timeStampEdit = getTimeStamp();
    }

    public void setTimeStampEdit(Date timeStampEdit) {
        timeStampEdit = getTimeStamp();
    }

    public Date getTimeStampCreate() {
        return timeStampCreate;
    }

    public Date getTimeStampEdit() {
        return timeStampEdit;
    }
}
