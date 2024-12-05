package com.gridnine.model;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * Класс, представляющий сегмент перелёта, с указанием времени вылета и прилёта.
 */
public class Segment {

    private LocalDateTime departureTime;
    private LocalDateTime arrivalTime;

    public Segment(LocalDateTime departureTime, LocalDateTime arrivalTime) {
        this.departureTime = departureTime;
        this.arrivalTime = arrivalTime;
    }

    public LocalDateTime getDepartureTime() {
        return departureTime;
    }

    public LocalDateTime getArrivalTime() {
        return arrivalTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Segment segment = (Segment) o;
        return Objects.equals(departureTime, segment.departureTime) && Objects.equals(arrivalTime, segment.arrivalTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(departureTime, arrivalTime);
    }

    @Override
    public String toString() {
        DateTimeFormatter fmt =
                DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
        return '[' + departureTime.format(fmt) + '|' + arrivalTime.format(fmt)
                + ']';
    }
}
