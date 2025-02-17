package org.fungover.breeze.control;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.*;
import java.util.Optional;

public class OptionTest {

    @Test
    void someShouldCreateSomeInstance() {
        Option<String> option = Option.of("avalue");

        assertThat(option).isInstanceOf(Some.class);
        assertThat(option.get()).isEqualTo("avalue");
        assertThat(option.isDefined()).isTrue();
        assertThat(option.isEmpty()).isFalse();
    }

    @Test
    void noneShouldReturnNone() {

        Option<String> option1 = Option.none();


    }



}
