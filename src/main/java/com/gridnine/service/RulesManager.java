package com.gridnine.service;

import com.gridnine.model.*;

import java.time.*;
import java.util.*;
import java.util.function.*;

/**
 * Класс для создания правил фильтрации перелётов.
 */
public class RulesManager {

    private static final int MAX_HOURS_EARTH = 2;

    /**
     * Создаёт список правил фильтрации перелётов в виде предикатов.
     *
     * @return Список предикатов для фильтрации.
     */
    public List<Predicate<Flight>> createRules() {
        return List.of(
//                Правило исключающее рейсы с вылетом в прошлом
                flight -> flight.getSegments().stream()
                        .anyMatch(segment -> segment.getDepartureTime().isBefore(LocalDateTime.now())),
//                Правило исключающее перелёты, где дата прилёта раньше даты вылета
                flight -> flight.getSegments().stream()
                        .anyMatch(segment -> segment.getArrivalTime().isBefore(segment.getDepartureTime())),
//                Правило исключающее рейсы, где время на земле между перелётами превышает заданное кол-во часов
                flight -> {
                    List<Segment> segments = flight.getSegments();
                    Segment previousSegment = null;
                    Duration totalTimeEarth = Duration.ZERO;
                    for (Segment segment : segments) {
                        if (previousSegment != null) {
                            Duration earthTime = Duration.between(previousSegment.getArrivalTime(), segment.getDepartureTime());
                            totalTimeEarth = totalTimeEarth.plus(earthTime);
                        }
                        previousSegment = segment;
                    }
                    return totalTimeEarth.toHours() > MAX_HOURS_EARTH;
                }
        );
    }
}
