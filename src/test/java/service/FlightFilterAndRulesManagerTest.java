package service;

import com.gridnine.model.*;
import com.gridnine.service.*;
import org.junit.jupiter.api.*;

import java.time.*;
import java.util.*;
import java.util.function.*;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Тестовый класс для проверки работы классов FlightFilter и RulesManager.
 * Осуществляется проверка фильтрации перелётов на основе различных правил.
 * Правила включают фильтрацию по времени вылета, времени прилёта, а также по времени ожидания на земле.
 */
public class FlightFilterAndRulesManagerTest {

    private List<Flight> flights;
    private RulesManager rulesManager;
    private FlightFilter flightFilter;

    /**
     * Метод, выполняемый перед каждым тестом.
     * Инициализирует тестовые данные и объекты классов, которые будут тестироваться.
     */
    @BeforeEach
    public void setUp() {
        flights = createFlightsInit();
        rulesManager = new RulesManager();
        flightFilter = new FlightFilter();
    }

    /**
     * Проверяет корректность фильтрации перелётов, используя правило, исключающее перелёты с вылетом в прошлом.
     */
    @Test
    @DisplayName("Return filtered flights when using a valid initial flight and a single rule")
    public void filter_returnFilteredFlights_WhenUsedValidInitialFlightAndOneRule() {
        List<Flight> expected = createFlightsExpected();
        Predicate<Flight> rule = rulesManager.createRules().get(0);
        List<Flight> actual = flightFilter.filterFlights(flights, rule);

        assertEquals(expected, actual, "Должны быть исключены перелёты с вылетом в прошлом");
    }

    /**
     * Проверяет, что список перелётов не изменяется, если ни одно из правил фильтрации не выполнено.
     */
    @Test
    @DisplayName("Returns an unchanged list when the filtering rules are not met")
    public void filter_returnsUnchangedList_whenFilteringRulesNotMet() {
        LocalDateTime dtNow = LocalDateTime.now();
        List<Flight> flightsInit = List.of(
                new Flight(List.of(new Segment(dtNow.minusHours(10), dtNow.minusHours(9)))),
                new Flight(List.of(new Segment(dtNow.plusHours(1), dtNow.plusHours(2))))
        );

        Predicate<Flight> rule = rulesManager.createRules().get(1);
        List<Flight> actual = flightFilter.filterFlights(flightsInit, rule);
        assertEquals(flightsInit, actual, "Список не должен изменяться, если ни одно правило не выполнено");
    }

    /**
     * Проверяет, что при передаче пустого списка перелётов возвращается пустой список.
     */
    @Test
    @DisplayName("Returns an empty collection when the input collection is empty")
    public void filter_returnsEmptyList_whenInputListIsEmpty() {
        List<Flight> flightsInit = new ArrayList<>();
        List<Flight> expected = new ArrayList<>();
        Predicate<Flight> rule = rulesManager.createRules().get(0);
        List<Flight> actual = flightFilter.filterFlights(flightsInit, rule);

        assertEquals(expected, actual, "Должен возвращаться пустой список, если входной список пуст");
    }

    /**
     * Проверяет, что метод выбрасывает NullPointerException, если входной список перелётов равен null.
     */
    @Test
    @DisplayName("Throws a NullPointerException if the input list is null")
    public void filter_throwsNullPointerExceptionIfInputListIsNull() {
        List<Flight> flightsInit = null;
        Predicate<Flight> rule = rulesManager.createRules().get(0);
        assertThrows(NullPointerException.class, () -> flightFilter.filterFlights(flightsInit, rule), "Ожидается NullPointerException, если входной список равен null");
    }

    /**
     * Проверяет, что метод выбрасывает NullPointerException, если список правил фильтрации равен null.
     */
    @Test
    @DisplayName("Throws a NullPointerException if the rules are null")
    public void filter_throwsNullPointerExceptionIfRulesIsNull() {
        List<Predicate<Flight>> rules = null;
        assertThrows(NullPointerException.class, () -> flightFilter.filterFlights(flights, rules.get(0)), "Ожидается NullPointerException, если список правил равен null");
    }

    /**
     * Проверяет, что правило 1 корректно фильтрует перелёты с вылетом в прошлом.
     */
    @Test
    @DisplayName("Rule 1 correctly filters flights with departure in the past")
    public void createRules_createdRule1IsCorrectWhenConditionIsTrue() {
        Flight flight = createFlightWithDepartureInPast();
        Predicate<Flight> rule = rulesManager.createRules().get(0);
        assertTrue(rule.test(flight), "Правило 1 должно корректно фильтровать перелёты с вылетом в прошлом");
    }

    /**
     * Проверяет, что правило 2 корректно фильтрует перелёты, у которых время прилёта раньше времени вылета.
     */
    @Test
    @DisplayName("Rule 2 correctly filters flights with arrival before departure")
    public void createRules_createdRule2IsCorrectWhenConditionIsTrue() {
        Flight flight = createFlightWithArrivalBeforeDeparture();
        Predicate<Flight> rule = rulesManager.createRules().get(1);
        assertTrue(rule.test(flight), "Правило 2 должно корректно фильтровать перелёты с прилётом раньше вылета");
    }

    /**
     * Проверяет, что правило 3 корректно фильтрует перелёты с временем ожидания на земле, превышающим два часа.
     */
    @Test
    @DisplayName("Rule 3 correctly filters flights with ground time exceeding two hours")
    public void createRules_createdRule3IsCorrectWhenConditionIsTrue() {
        Flight flight = createFlightWithLongGroundTime();
        Predicate<Flight> rule = rulesManager.createRules().get(2);
        assertTrue(rule.test(flight), "Правило 3 должно корректно фильтровать перелёты с временем ожидания на земле более двух часов");
    }

    /**
     * Создаёт начальный список перелётов для тестов.
     *
     * @return Список перелётов, содержащий как корректные, так и некорректные данные.
     */
    private List<Flight> createFlightsInit() {
        LocalDateTime dtNow = LocalDateTime.now();
        return List.of(
                new Flight(
                        List.of(
                                new Segment(dtNow.minusHours(10), dtNow.minusHours(9)),
                                new Segment(dtNow.minusHours(2), dtNow.minusHours(3)),
                                new Segment(dtNow.minusHours(4), dtNow.minusHours(3))
                        )
                ),
                new Flight(
                        List.of(
                                new Segment(dtNow.plusHours(1), dtNow.plusHours(2)),
                                new Segment(dtNow.plusHours(2), dtNow.plusHours(3)),
                                new Segment(dtNow.plusHours(3), dtNow.plusHours(4))
                        )
                )
        );
    }

    /**
     * Создаёт список ожидаемых перелётов после фильтрации.
     *
     * @return Список перелётов, которые должны остаться после применения фильтрации.
     */
    private List<Flight> createFlightsExpected() {
        LocalDateTime dtNow = LocalDateTime.now();
        return List.of(
                new Flight(
                        List.of(
                                new Segment(dtNow.plusHours(1), dtNow.plusHours(2)),
                                new Segment(dtNow.plusHours(2), dtNow.plusHours(3)),
                                new Segment(dtNow.plusHours(3), dtNow.plusHours(4))
                        )
                )
        );
    }

    /**
     * Создаёт перелёт с временем вылета в прошлом.
     *
     * @return Перелёт с вылетом в прошлом.
     */
    private Flight createFlightWithDepartureInPast() {
        LocalDateTime dtNow = LocalDateTime.now();
        return new Flight(List.of(new Segment(dtNow.minusHours(10), dtNow.minusHours(9))));
    }

    /**
     * Создаёт перелёт с временем прилёта раньше времени вылета.
     *
     * @return Перелёт с некорректным временем прилёта.
     */
    private Flight createFlightWithArrivalBeforeDeparture() {
        LocalDateTime dtNow = LocalDateTime.now();
        return new Flight(List.of(new Segment(dtNow.plusHours(2), dtNow.plusHours(1))));
    }

    /**
     * Создаёт перелёт с временем ожидания на земле, превышающим два часа.
     *
     * @return Перелёт с длительным временем ожидания между сегментами.
     */
    private Flight createFlightWithLongGroundTime() {
        LocalDateTime dtNow = LocalDateTime.now();
        return new Flight(List.of(
                new Segment(dtNow.plusHours(1), dtNow.plusHours(2)),
                new Segment(dtNow.plusHours(5), dtNow.plusHours(6))
        ));
    }
}

