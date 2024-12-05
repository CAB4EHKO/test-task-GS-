package com.gridnine.model;

import java.util.*;
import java.util.stream.*;

/**
 * Класс, представляющий перелёт, который состоит из одного или нескольких сегментов.
 */
public class Flight {

    private List<Segment> segments;

    public Flight(List<Segment> segments) {
        this.segments = segments;
    }

    public List<Segment> getSegments() {
        return segments;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Flight flight = (Flight) o;
        return Objects.equals(segments, flight.segments);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(segments);
    }

    @Override
    public String toString() {
        return segments.stream().map(Object::toString)
                .collect(Collectors.joining(" "));
    }
}
