package com.gridnine;

import com.gridnine.model.*;
import com.gridnine.service.*;

import java.util.*;
import java.util.function.*;

/**
 * Основной класс для запуска приложения и демонстрации фильтрации перелётов.
 */
public class Main {
    public static void main(String[] args) {
//        Создание тестовых данных по перелётам
        List<Flight> flights = FlightBuilder.createFlights();
        System.out.printf("Исходные перелёты: %s\n", flights);

//        Создание правил для фильтрации перелётов
        RulesManager rulesManager = new RulesManager();
        List<Predicate<Flight>> rules = rulesManager.createRules();

//        Фильтрация перелётов по разным правилам и вывод результатов
        FlightFilter flightFilter = new FlightFilter();

        List<Flight> filteredByRule1 = flightFilter.filterFlights(flights, rules.get(0));
        System.out.printf("Перелёты после применения правила 1: %s%n", filteredByRule1);

        List<Flight> filteredByRule2 = flightFilter.filterFlights(flights, rules.get(1));
        System.out.printf("Перелёты после применения правила 2: %s%n", filteredByRule2);

        List<Flight> filteredByRule3 = flightFilter.filterFlights(flights, rules.get(2));
        System.out.printf("Перелёты после применения правила 3: %s%n", filteredByRule3);
    }
}