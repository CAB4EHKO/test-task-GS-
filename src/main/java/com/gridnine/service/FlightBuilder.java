package com.gridnine.service;

import com.gridnine.model.*;

import java.time.*;
import java.util.*;

/**
 * Класс для создания тестовых данных по перелётам.
 */
public class FlightBuilder {
    /**
     * Создаёт список примеров перелётов с различными сценариями для тестирования.
     *
     * @return Список тестовых перелётов.
     */
    public static List<Flight> createFlights() {
        LocalDateTime threeDaysFromNow = LocalDateTime.now().plusDays(3);
        return Arrays.asList(
//                Обычный двухчасовой рейс
                createFlight(threeDaysFromNow, threeDaysFromNow.plusHours(2)),
//                Обычный рейс с пересадкой
                createFlight(threeDaysFromNow, threeDaysFromNow.plusHours(2),
                        threeDaysFromNow.plusHours(3), threeDaysFromNow.plusHours(5)),
//                Рейс, с вылетом в прошлом
                createFlight(threeDaysFromNow.minusDays(3), threeDaysFromNow),
//                Рейс, который прилетает раньше своего вылета
                createFlight(threeDaysFromNow, threeDaysFromNow.minusHours(3)),
//                Рейс с пересадкой более двух часов
                createFlight(threeDaysFromNow, threeDaysFromNow.plusHours(2),
                        threeDaysFromNow.plusHours(5), threeDaysFromNow.plusHours(7))
        );
    }

    /**
     * Вспомогательный метод для создания перелёта с использованием списка дат.
     *
     * @param dates Последовательность дат (вылет-прилёт для каждого сегмента).
     * @return Объект перелёта.
     */
    public static Flight createFlight(LocalDateTime... dates) {
        if ((dates.length % 2) != 0) {
            throw new IllegalArgumentException("Передайте чётное количество дат для создания сегмента перелёта");
        }
        List<Segment> segments = new ArrayList<>(dates.length / 2);
        for (int i = 0; i < dates.length; i += 2) {
            segments.add(new Segment(dates[i], dates[i + 1]));
        }
        return new Flight(segments);
    }
}
