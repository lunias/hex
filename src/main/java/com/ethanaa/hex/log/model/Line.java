package com.ethanaa.hex.log.model;

import java.time.LocalDateTime;

public class Line {

    private long number;

    private LocalDateTime time;

    private String text;

    public Line(long number, String line) {

        this.number = number;

        this.time = parseTime(line);

        this.text = cutTime(line);
    }

    private LocalDateTime parseTime(String line) {
        return null;
    }

    private String cutTime(String line) {
        return null;
    }

    public long getNumber() {
        return number;
    }

    public void setNumber(long number) {
        this.number = number;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
