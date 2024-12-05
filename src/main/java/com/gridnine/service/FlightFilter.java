package com.gridnine.service;

import com.gridnine.model.*;

import java.util.*;
import java.util.function.*;

/**
 * Класс для фильтрации перелётов с использованием заданных правил.
 */
public class FlightFilter {
    /**
     * Метод для фильтрации перелётов по заданным правилам.
     *
     * @param flights Список перелётов для фильтрации.
     * @param rules   Правила фильтрации (в виде предикатов).
     * @return Отфильтрованный список перелётов.
     */
    @SafeVarargs
    public final List<Flight> filterFlights(List<Flight> flights, Predicate<Flight>... rules) {
        List<Flight> filteredFlights = new LinkedList<>();

        for (Flight flight : flights) {
            boolean shouldInclude = true;
            for (Predicate<Flight> rule : rules) {
                if (rule.test(flight)) {
                    shouldInclude = false;
                    break;
                }
            }
            if (shouldInclude) {
                filteredFlights.add(flight);
            }
        }
        return filteredFlights;
    }
}
