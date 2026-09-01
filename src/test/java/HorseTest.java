import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;



import static org.junit.jupiter.api.Assertions.*;
@ExtendWith(MockitoExtension.class)
class HorseTest {


    private Horse horse = new Horse("Ace of Spades", 2.5);
    private Horse horse2 = new Horse("Ace of Spades", 2.5, 10.0);

    @Test
    void constructor_whenNameIsNull_throwsIllegalArgumentException() {
        IllegalArgumentException IAException = assertThrows(IllegalArgumentException.class, () -> {
            new Horse(null, 2.5);
        });

        assertEquals("Name cannot be null.", IAException.getMessage());
    }

    @Test
    void constructor_whenSpeedIsNegative_throwsIllegalArgumentException() {

        IllegalArgumentException IAException2 = assertThrows(IllegalArgumentException.class, () -> {
            new Horse("Ace of Spades", -2.5);
        });
        assertEquals("Speed cannot be negative.", IAException2.getMessage());
    }

    @Test
    void constructor_whenDistanceIsNegative_throwsIllegalArgumentException() {

        IllegalArgumentException IAException3 = assertThrows(IllegalArgumentException.class, () -> {
            new Horse("Ace of Spades", 2.5, -10.0);
        });
        assertEquals("Distance cannot be negative.", IAException3.getMessage());
    }

    @ParameterizedTest
    @ValueSource(strings = {"", " ", "  "})
    void constructorInvalidName(String invalidName) {
        IllegalArgumentException IAException = assertThrows(IllegalArgumentException.class, () -> {
            new Horse(invalidName, 2.5);
        });
        assertEquals("Name cannot be blank.", IAException.getMessage());
    }

    @Test
    void getName() {
        assertEquals("Ace of Spades", horse.getName());
    }

    @Test
    void getSpeed() {
        assertEquals(2.5, horse.getSpeed());
    }

    @Test
    void getDistance_returnsThirdParam() {
            assertEquals(10.0, horse2.getDistance());
    }

    @Test
    void getDistance_returnsZero() {
        assertEquals(0, horse.getDistance());
    }

    @Test
    void move_checkIfGetRandomDoubleIsCalled() {
        try(MockedStatic<Horse> mockedHorse = Mockito.mockStatic(Horse.class)) {

            mockedHorse.when(() -> Horse.getRandomDouble(0.2, 0.9)).thenReturn(0.5);
            horse2.move();
            mockedHorse.verify(() -> Horse.getRandomDouble(0.2, 0.9), Mockito.times(1));
        }
    }

    @ParameterizedTest
    @CsvSource({"10.0, 5.0, 0.2, 11.0",
            "10.0, 5.0, 0.9, 14.5",
            "0.0,  10.0, 0.5, 5.0" })
    void move(double distance, double speed, double mockedRandom, double expectedDistance) {
        try (MockedStatic<Horse> mockedHorse = Mockito.mockStatic(Horse.class)) {
            mockedHorse.when(() -> Horse.getRandomDouble(Mockito.anyDouble(), Mockito.anyDouble())).thenReturn(mockedRandom);
            double result = distance + speed * Horse.getRandomDouble(0.2, 0.9);
            assertEquals(expectedDistance, result, 0.0001);
        }
    }
}