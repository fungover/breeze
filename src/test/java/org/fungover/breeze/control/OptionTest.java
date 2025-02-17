package org.fungover.breeze.control;

import org.junit.jupiter.api.*;
import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

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

    @Test
    void isEmptyAndIsDefinedShouldBeConsistent() {

        Option<String> someOption = Option.of("avalue");
        Option<String> noneOption = Option.none();

        assertThat(someOption.isEmpty()).isFalse();
        assertThat(noneOption.isEmpty()).isTrue();
            assertThat(someOption.isDefined()).isTrue();
            assertThat(noneOption.isDefined()).isFalse();

    }

    @Test
    void getOrElseShouldReturnValueIfPresentOrDefaultOtherwise() {
        Option<String> someOption = Option.of("value");
        Option<String> noneOption = Option.none();

        assertThat(someOption.getOrElse("default")).isEqualTo("value");
        assertThat(noneOption.getOrElse("default")).isEqualTo("default");
    }

    @Test
    void getOrElseGetShouldReturnValueIfPresentOrComputedOtherwise() {
        Option<Integer> someOption = Option.of(10);
        Option<Integer> noneOption = Option.none();

        assertThat(someOption.getOrElseGet(() -> 20)).isEqualTo(10);
        assertThat(noneOption.getOrElseGet(() -> 20)).isEqualTo(20);
    }

    @Test
    void someShouldThrowExceptionIfNull() {
        assertThrows(NullPointerException.class, () -> Option.some(null));
    }

    @Test
    void getShouldReturnValueForSome() {
        Option<Integer> some = Option.some(10);
        assertEquals(10, some.get());
    }

    @Test
    void getShouldThrowExceptionForNone() {
        Option<Integer> none = Option.none();
        assertThrows(UnsupportedOperationException.class, none::get);
    }


}
