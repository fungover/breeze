package org.fungover.breeze.collection;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class FSetTest {

    @Nested
    class FSetAddingItemsToFSetTest {
        FSet<Integer> fSet;
        FSet<Integer> fSet2;


        @Test
        @DisplayName("Adding one element to a FSet test")
        void addingOneElementToAFSetTest() {
            fSet = new FSet<>();
            fSet = fSet.add(1);
            assertThat(fSet.size()).isEqualTo(1);

        }

        @Test
        @DisplayName("Adding to an empty FSet object")
        void addingToAnEmptyFSetObject() {

            fSet = new FSet<>();
            fSet.add(5).add(2);
            assertThat(fSet.size()).isZero();

            var fSet5 = fSet.add(5);
            assertThat(fSet5.size()).isEqualTo(1);


        }

        @Test
        @DisplayName("Duplicate Values should not be added to FSetTest")
        void DuplicateValuesShouldNotBeAddedToFSetTest() {
            fSet = new FSet<>();
            fSet = fSet.add(5);
            fSet = fSet.add(5);
            assertThat(fSet.size()).isEqualTo(1);

        }


        @Test
        @DisplayName("Contains method isFalse for empty FSet Test")
        void containsMethodIsFalseForEmptyFSetTest() {

            fSet = new FSet<>();

            assertThat(fSet.contains(null)).isFalse();


        }

        @Test
        @DisplayName("Contains method is True if value Present Test")
        void containsMethodIsTrueIfValuePresentTest() {

            fSet = new FSet<>();
            var intFSet = fSet.add(1).add(15).add(20).add(100);
            assertThat(intFSet.contains(1)).isTrue();
            assertThat(intFSet.contains(15)).isTrue();
            assertThat(intFSet.contains(100)).isTrue();
            assertThat(intFSet.contains(20)).isTrue();
            assertThat(intFSet.contains(1000)).isFalse();

        }

        @Test
        @DisplayName("FSet union test")
        void fSetUnionTest() {
            fSet = new FSet<>();
            fSet2 = new FSet<>();
            fSet = fSet.add(1).add(15).add(20).add(100);
            fSet2 = fSet2.add(1).add(15).add(20).add(1000);
            var unionSet = fSet2.union(fSet);
            assertThat(unionSet.size()).isEqualTo(5);
            assertThat(unionSet.contains(100)).isTrue();


        }


        @Test
        @DisplayName("Empty FSet isEmpty is True test")
        void emptyFSetIsEmptyIsTrueTest() {
            fSet = new FSet<>();
            assertThat(fSet.isEmpty()).isTrue();
            fSet.add(12);
            assertThat(fSet.isEmpty()).isTrue();
            fSet = fSet.add(1);
            assertThat(fSet.isEmpty()).isFalse();

        }


        @Test
        @DisplayName("FSet intersect Test")
        void fSetIntersectTest() {
            fSet = new FSet<>();
            fSet2 = new FSet<>();
            fSet = fSet.add(1).add(15).add(20).add(100);
            fSet2 = fSet2.add(1).add(10).add(20).add(1000);

            var fIntersect = fSet.intersection(fSet2);
            assertThat(fIntersect.size()).isEqualTo(2);
            assertThat(fIntersect.contains(1)).isTrue();
            assertThat(fIntersect.contains(20)).isTrue();

        }

        @Test
        @DisplayName("FSet intersect Test with one value")
        void fSetIntersectTestWithOneValue() {
            fSet = new FSet<>();
            fSet2 = new FSet<>();
            FSet<Integer> fSet3 = new FSet<>();
            fSet = fSet.add(1);
            fSet2 = fSet2.add(1);
            var fIntersect = fSet.intersection(fSet2);
            assertThat(fIntersect.size()).isEqualTo(1);

            var intersect = fSet.intersection(fSet3);
            assertThat(intersect.size()).isZero();

        }

        @Test
        @DisplayName("FSet symmetric difference test")
        void fSetSymmetricDifferenceTest() {
            FSet<Integer> fSet3 = new FSet<>();
            FSet<Integer> fSet4 = new FSet<>();
            fSet3 = fSet3.add(1).add(15).add(20).add(5);
            fSet4= fSet4.add(1).add(15).add(20).add(9);

            var fsymmdiff = fSet3.symmetricDifference(fSet4);
            assertThat(fsymmdiff.size()).isEqualTo(2);
            assertThat(fsymmdiff.contains(9)).isTrue();
            assertThat(fsymmdiff.contains(5)).isTrue();
        }


        @Test
        @DisplayName("Removal by skip test")
        void removalBySkipTest() {
            fSet = new FSet<>();
            fSet =  fSet.add(1).add(15).add(20).add(100);
            var removeSet = fSet.remove(15);
            System.out.println(removeSet);

            assertThat(removeSet.contains(15)).isFalse();
            assertThat(removeSet.size()).isEqualTo(3);


        }

        @Test
        @DisplayName("FSet equals and hashcode test ")
        void fSetEqualsAndHashcodeTest() {

            fSet = new FSet<>();
            fSet2 = new FSet<>();
            fSet = fSet.add(1).add(15).add(20).add(100);
            fSet2 = fSet2.add(1).add(15).add(20).add(100);

            assertThat(fSet.equals(fSet2)).isTrue();
            assertThat(fSet.hashCode()).hasSameHashCodeAs(fSet2.hashCode());

            
        }

        @Test
        @DisplayName("FSet with different data type isFalse")
        void fSetWithDifferentDataTypeIsFalse() {
            fSet = new FSet<>();
            String fSetString = "";
            assertThat(fSet).isNotSameAs(fSetString);
            var expected = fSet.equals(fSetString);
            assertThat(expected).isFalse();

        }


        @Test
        @DisplayName("Trying to add null throws exception test")
        void tryingToAddNullThrowsExceptionTest() {
            fSet = new FSet<>();
            assertThatThrownBy(() -> fSet.add(null))
                    .isInstanceOf(NullPointerException.class)
            .hasMessage("Cannot add null element to FSet");

        }

        @Test
        @DisplayName("Cannot remove null from FSet Test")
        void cannotRemoveNullFromFSetTest() {
            fSet = new FSet<>();
            assertThatThrownBy(() -> fSet.remove(null)).
                    isInstanceOf(NullPointerException.class)
                    .hasMessage("Cannot remove null element from FSet");

        }



    }
}
