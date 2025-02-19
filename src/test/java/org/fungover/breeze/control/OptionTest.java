package org.fungover.breeze.control;

import org.junit.jupiter.api.*;
import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.*;
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

    @Test
    void getOrElseGetShouldNotEvaluateSupplierForSome() {
        Option<Integer> some = Option.some(10);
        assertEquals(10, some.getOrElseGet(() -> 42));
    }

    @Test
    void orElseThrowShouldThrowForNone() {
        Option<Integer> none = Option.none();
        assertThrows(IllegalStateException.class, () -> none.orElseThrow(IllegalStateException::new));
    }

    @Test
    void mapShouldApplyFunctionForSome() {
        Option<Integer> some = Option.some(10);
        Option<Integer> mapped = some.map(x -> x * 2);
        assertEquals(Option.some(20), mapped);
    }

    @Test
    void mapShouldReturnNoneForNone() {
        Option<Integer> none = Option.none();
        assertEquals(Option.none(), none.map(x -> x * 2));
    }

    @Test
    void flatMapShouldApplyFunctionForSome() {
        Option<Integer> some = Option.some(10);
        Option<Integer> mapped = some.flatMap(x -> Option.some(x * 2));
        assertEquals(Option.some(20), mapped);
    }

    @Test
    void flatMapShouldReturnNoneForNone() {
        Option<Integer> none = Option.none();
        assertEquals(Option.none(), none.flatMap(x -> Option.some(x * 2)));
    }

    @Test
    void filterShouldRetainSomeIfPredicateIsTrue() {
        Option<Integer> some = Option.some(10);
        assertEquals(Option.some(10), some.filter(x -> x > 5));
    }

    @Test
    void filterShouldReturnNoneIfPredicateIsFalse() {
        Option<Integer> some = Option.some(10);
        assertEquals(Option.none(), some.filter(x -> x < 5));
    }

    @Test
    void forEachShouldExecuteForSome() {
        Option<Integer> some = Option.some(10);
        final int[] result = {0};
        some.forEach(x -> result[0] = x);
        assertEquals(10, result[0]);
    }

    @Test
    void forEachShouldDoNothingForNone() {
        Option<Integer> none = Option.none();
        final int[] result = {0};
        none.forEach(x -> result[0] = x);
        assertEquals(0, result[0]);
    }

    @Test
    void someInstanceForSomeValueShouldBeEqual() {

        Option<Integer> some1 = Option.some(10);
        Option<Integer> some2 = Option.some(10);

        assertThat(some1).isEqualTo(some2);
        assertThat(some1.hashCode()).isEqualTo(some2.hashCode());

    }

    @Test
    void noneInstancesShouldAlwaysBeEqual() {

        Option<Integer> none = Option.none();
        Option<Integer> none2 = Option.none();

        assertThat(none).isEqualTo(none2);
        assertThat(none2.hashCode()).isEqualTo(none2.hashCode());

    }

    @Test
    void someShouldNeverBeEqualToNone() {
        Option<Integer> some = Option.some(10);
        Option<Integer> none = Option.none();

        assertThat(some).isNotEqualTo(none);
    }

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
    void orElseThrowWithoutSupplierShouldThrowExceptionForNone() {
        Option<Integer> none = Option.none();
        assertThrows(UnsupportedOperationException.class, none::orElseThrow);
    }


}
