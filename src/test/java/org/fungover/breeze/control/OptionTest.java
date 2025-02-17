package org.fungover.breeze.control;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.*;

import java.io.Serializable;
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
        Option<Integer> option2 = Option.none();

        assertThat(option1).isSameAs(option2);
        assertThat(option1).isInstanceOf(None.class);
        assertThat(option1.isDefined()).isFalse();
        assertThat(option2.isEmpty()).isTrue();


    }

    @Test
    void ofShouldCreateSomeIfValueIsNotNull() {

        Option<Serializable> option = Option.of(42);

        assertThat(option).isInstanceOf(Some.class);
        assertThat(option.isDefined()).isTrue();
    }

    @Test
    void ofShouldReturnNoneIfValueIsNull() {
        Option<Integer> option = Option.of(null);
        assertThat(option).isInstanceOf(None.class);
        assertThat(option.isEmpty()).isTrue();
    }

    @Test
    void ofNullableShouldBehaveLikeOf() {

        Option<String> someOption = Option.ofNullable("avalue");
        Option<Integer> noneOption = Option.ofNullable(null);

        assertThat(someOption).isInstanceOf(Some.class);
        assertThat(noneOption).isInstanceOf(None.class);


    }


}
