package duke.task;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.List;
import java.util.stream.Stream;

class TaskTest {
    @Test
    public void validateComponentKeys_noDescription_invalidComponentsThrown() {
        Task.InvalidComponents e = Assertions.assertThrows(Task.InvalidComponents.class,
                () -> Task.validateComponentKeys(new HashSet<>(), new HashSet<>()));

        Assertions.assertEquals("No description given", e.getMessage());
    }

    @Test
    public void validateComponentKeys_notMatch_invalidComponentsThrown() {
        List<HashSet<String>> expecteds = List.of(
                new HashSet<>(),
                new HashSet<>(List.of("/by", "/from"))
        );

        List<HashSet<String>> actuals = List.of(
                new HashSet<>(List.of("DESCRIPTION", "/by", "/from")),
                new HashSet<>(List.of("DESCRIPTION", "/by"))
        );

        for (int i = 0; i < expecteds.size(); i++) {
            var expected = expecteds.get(i);
            var actual = actuals.get(i);

            Task.InvalidComponents e = Assertions.assertThrows(Task.InvalidComponents.class,
                    () -> Task.validateComponentKeys(expected, actual));

            Assertions.assertEquals("Invalid task components: " + actual + "; expected: " + expected, e.getMessage());
        }
    }

    @Test
    public void validateComponentKeys_edgeCaseInputs_nothingThrown() {
        List<HashSet<String>> expecteds = List.of(
                new HashSet<>(),
                new HashSet<>(List.of("/from", "/to", "/from"))
        );

        List<HashSet<String>> actuals = List.of(
                new HashSet<>(List.of("DESCRIPTION")),
                new HashSet<>(List.of("DESCRIPTION", "/from", "/to"))
        );

        for (int i = 0; i < expecteds.size(); i++) {
            var expected = expecteds.get(i);
            var actual = actuals.get(i);

            Assertions.assertDoesNotThrow(() -> Task.validateComponentKeys(expected, actual));
        }

    }
}