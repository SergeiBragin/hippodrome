import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@ExtendWith(MockitoExtension.class)
class HippodromeTest {

    @Test
    void constructor_whenInputIsNull_throwsIllegalArgumentException() {
        IllegalArgumentException IAException  = assertThrows(IllegalArgumentException.class, () -> {
            new Hippodrome(null);});
        assertEquals("Horses cannot be null", IAException.getMessage());
    }

    @Test
    void constructor_whenInputIsEmpty_throwsIllegalArgumentException() {
        IllegalArgumentException IAException  = assertThrows(IllegalArgumentException.class, () -> {
            new Hippodrome(new ArrayList<>());});
        assertEquals("Horses cannot be empty", IAException.getMessage());
    }


    @Test
    void getHorses_checkIfReturnsCorrectListAndOrder() {
        List<Horse> expectedHorses = new ArrayList<>();
        for (int i = 0; i <30; i++) {
            expectedHorses.add(new Horse("Horse_" + i, 1.0, 1.0));
        }

        Hippodrome hippodrome = new Hippodrome(expectedHorses);
        List<Horse> actualHorses = hippodrome.getHorses();
        assertEquals(expectedHorses, actualHorses);
    }

    @Test
    void hippodromeMoveTest() {
        List<Horse> horsesList = new ArrayList<>();
        for (int i = 0; i < 50; i++) {
            horsesList.add(Mockito.mock(Horse.class));
        }

        Hippodrome hippodrome = new Hippodrome(horsesList);
        hippodrome.move();

        for (Horse horse : horsesList) {
            Mockito.verify(horse, Mockito.times(1)).move();
        }
    }

    @Test
    void getWinner() {
        Horse horse1 = new Horse("Horse_1", 1.0, 1.0);
        Horse horse2 = new Horse("Horse_2", 1.0, 10.0);

        List<Horse> horsesList = List.of(horse1, horse2);

        Hippodrome hippodrome = new Hippodrome(horsesList);
        assertEquals(horse2, hippodrome.getWinner());
    }
}