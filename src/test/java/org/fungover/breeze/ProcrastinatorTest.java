package org.fungover.breeze;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ProcrastinatorTest {

    @DisplayName("doItLater should consistently return the same excuse")
    @Test
    void doItLater_alwaysReturnsTheSameExcuse() {
        assertThat(Procrastinator.doItLater()).isEqualTo("I'll do it tomorrow");
        // Do it twice to make sure procrastination is consistent
        assertThat(Procrastinator.doItLater()).isEqualTo("I'll do it tomorrow");
    }
}
