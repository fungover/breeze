package org.fungover.breeze.control;

import org.junit.jupiter.api.*;
import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.*;
import java.util.Optional;

public class OptionTest {

    // ================================================
    // Creation & Presence Checks
    // ================================================

    @Test
    void ofShouldCreateSomeIfValueIsNotNull() {
        Option<Serializable> option = Option.of(42);
        assertThat(option.isDefined()).isTrue();
    }

    @Test
    void ofNullableShouldBehaveLikeOf() {
        Option<String> someOption = Option.ofNullable("avalue");
        Option<Integer> noneOption = Option.ofNullable(null);
        assertThat(someOption.isDefined()).isTrue();
        assertThat(noneOption.isDefined()).isFalse();
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

    // ================================================
    // Retrieval & Transformation
    // ================================================

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
    void mapShouldApplyFunctionOrReturnNone() {
        Option<Integer> some = Option.some(10);
        Option<Integer> none = Option.none();
        assertThat(some.map(x -> x * 2)).isEqualTo(Option.some(20));
        assertThat(none.map(x -> x * 2)).isEqualTo(Option.none());
    }

    @Test
    void flatMapShouldApplyFunctionOrReturnNone() {
        Option<Integer> some = Option.some(10);
        Option<Integer> none = Option.none();
        assertThat(some.flatMap(x -> Option.some(x * 2))).isEqualTo(Option.some(20));
        assertThat(none.flatMap(x -> Option.some(x * 2))).isEqualTo(Option.none());
    }

    // ================================================
    // Serialization & Conversion
    // ================================================

    @Test
    void optionShouldBeSerializable() throws IOException, ClassNotFoundException {
        Option<String> some = Option.some("test");
        Option<String> none = Option.none();

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(baos);
        oos.writeObject(some);
        oos.writeObject(none);
        oos.close();

        ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
        ObjectInputStream ois = new ObjectInputStream(bais);

        Option<?> deserializedSome = (Option<?>) ois.readObject();
        Option<?> deserializedNone = (Option<?>) ois.readObject();

        assertThat(deserializedSome).isEqualTo(some);
        assertThat(deserializedNone).isSameAs(none);
    }

    @Test
    void toOptionalShouldConvertSomeAndNoneCorrectly() {
        Option<Integer> some = Option.some(10);
        Option<Integer> none = Option.none();
        assertThat(some.toOptional()).isEqualTo(Optional.of(10));
        assertThat(none.toOptional()).isEqualTo(Optional.empty());
    }


    // ==============================
    // Exception Handling Tests
    // ==============================
    @Test
    void someShouldThrowIfValueIsNull() {
        assertThatThrownBy(() -> Option.some(null))
                .isInstanceOf(NullPointerException.class)
                .hasMessage("Cannot create 'Some' with null");
    }


}