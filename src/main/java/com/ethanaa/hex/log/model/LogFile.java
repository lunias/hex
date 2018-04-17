package com.ethanaa.hex.log.model;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class LogFile {

    private String path;

    private String name;

    private List<Line> lines;

    private Map<Class<? extends LogMetric>, LogMetric> logMetrics;

    public LogFile(File file, String name) throws FileNotFoundException {

        this.path = file.getPath();

        this.name = name;

        this.lines = new ArrayList<>();

        Scanner scanner = new Scanner(file).useDelimiter(System.lineSeparator());
        long lineNumber = 0;
        while (scanner.hasNext()) {

            this.lines.add(new Line(++lineNumber, scanner.next()));
        }

        this.logMetrics = new HashMap<>();
    }

    public LogFile(String name) {

        this.name = name;
    }

    public void putLogMetric(LogMetric logMetric) {

        logMetrics.put(logMetric.getClass(), logMetric);
    }

    public void updateLogMetric(Class<? extends LogMetric> clazz) {

        LogMetric logMetric = logMetrics.get(clazz);
        logMetric.calculate();
        logMetric.render();
    }

    public void updateAllLogMetrics() {

        logMetrics.forEach((k, v) -> updateLogMetric(k));
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Line> getLines() {
        return lines;
    }

    public void setLines(List<Line> lines) {
        this.lines = lines;
    }
}
