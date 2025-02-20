package org.fungover.breeze.control;

import org.junit.jupiter.api.*;
import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.*;
import java.util.Optional;

public class NoneTest {

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
    void ofShouldReturnNoneIfValueIsNull() {
        Option<Integer> option = Option.of(null);
        assertThat(option).isInstanceOf(None.class);
        assertThat(option.isEmpty()).isTrue();
    }

    @Test
    void getShouldThrowExceptionForNone() {
        Option<Integer> none = Option.none();
        assertThrows(UnsupportedOperationException.class, none::get);
    }

    @Test
    void getOrElseShouldReturnDefaultForNone() {
        Option<String> noneOption = Option.none();
        assertThat(noneOption.getOrElse("default")).isEqualTo("default");
    }

    @Test
    void getOrElseGetShouldReturnComputedValueForNone() {
        Option<Integer> noneOption = Option.none();
        assertThat(noneOption.getOrElseGet(() -> 20)).isEqualTo(20);
    }

    @Test
    void orElseThrowShouldThrowForNone() {
        Option<Integer> none = Option.none();
        assertThrows(IllegalStateException.class, () -> none.orElseThrow(IllegalStateException::new));
    }

    @Test
    void mapShouldReturnNoneForNone() {
        Option<Integer> none = Option.none();
        assertEquals(Option.none(), none.map(x -> x * 2));
    }

    @Test
    void flatMapShouldReturnNoneForNone() {
        Option<Integer> none = Option.none();
        assertEquals(Option.none(), none.flatMap(x -> Option.some(x * 2)));
    }

    @Test
    void forEachShouldDoNothingForNone() {
        Option<Integer> none = Option.none();
        final int[] result = {0};
        none.forEach(x -> result[0] = x);
        assertEquals(0, result[0]);
    }

    @Test
    void noneInstancesShouldAlwaysBeEqual() {
        Option<Integer> none1 = Option.none();
        Option<Integer> none2 = Option.none();

        assertThat(none1).isEqualTo(none2);
        assertThat(none1.hashCode()).isEqualTo(none2.hashCode());
    }

    @Test
    void someShouldNeverBeEqualToNone() {
        Option<Integer> some = Option.some(10);
        Option<Integer> none = Option.none();

        assertThat(some).isNotEqualTo(none);
    }

    @Test
    void orElseThrowWithoutSupplierShouldThrowExceptionForNone() {
        Option<Integer> none = Option.none();
        assertThrows(UnsupportedOperationException.class, none::orElseThrow);
    }

    @Test
    void toOptionalShouldConvertNoneCorrectly() {
        Option<Integer> none = Option.none();
        assertThat(none.toOptional()).isEqualTo(Optional.empty());
    }

    @Test
    void peekShouldReturnSameInstanceForNone() {
        Option<Integer> none = Option.none();
        assertThat(none.peek(System.out::println)).isSameAs(none);
    }

    @Test
    void filterShouldReturnSameInstanceForNone() {
        Option<Integer> none = Option.none();
        assertThat(none.filter(x -> x > 5)).isSameAs(none);
    }

    @Test
    void toListShouldReturnEmptyListForNone() {
        Option<Integer> none = Option.none();
        assertThat(none.toList()).isEmpty();
    }


    @Test
    void toStreamShouldReturnEmptyStreamForNone() {
        Option<Integer> none = Option.none();
        assertThat(none.toStream().count()).isEqualTo(0);
    }

    @Test
    void toEitherShouldReturnLeftValueForNone() {
        Option<Integer> none = Option.none();
        assertThat(none.toEither(() -> "LeftValue").isLeft()).isTrue();
    }

    @Test
    void toEitherShouldThrowIfSupplierIsNull() {
        Option<Integer> none = Option.none();
        assertThrows(NullPointerException.class, () -> none.toEither(null));
    }

    @Test
    void toEitherShouldThrowIfSupplierReturnsNull() {
        Option<Integer> none = Option.none();
        assertThrows(NullPointerException.class, () -> none.toEither(() -> null));
    }

    @Test
    @SuppressWarnings("unchecked")
    void serializationShouldPreserveSingletonProperty() throws IOException, ClassNotFoundException {
        Option<Integer> none = Option.none();

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(baos);
        oos.writeObject(none);
        oos.close();

        ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
        ObjectInputStream ois = new ObjectInputStream(bais);
        Option<Integer> deserializedNone = (Option<Integer>) ois.readObject();

        assertThat(deserializedNone).isSameAs(none);
    }

    @Test
    void foldShouldReturnIfNoneSupplierValueForNone() {
        Option<Integer> none = Option.none();

        String result = none.fold(() -> "Default Value", val -> "Value: " + val);

        assertThat(result).isEqualTo("Default Value");
    }

    @Test
    void foldShouldThrowExceptionIfNoneSupplierIsNull() {
        Option<Integer> none = Option.none();

        assertThrows(NullPointerException.class, () -> none.fold(null, val -> "Value: " + val));
    }

    @Test
    void foldShouldThrowExceptionIfPresentFunctionIsNull() {
        Option<Integer> none = Option.none();

        assertThrows(NullPointerException.class, () -> none.fold(() -> "Default Value", null));
    }


}

