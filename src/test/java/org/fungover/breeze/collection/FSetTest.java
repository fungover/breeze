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
            fSet = new FSet<>();
            fSet2 = new FSet<>();
            fSet = fSet.add(1).add(15).add(20).add(100);
            fSet2 = fSet2.add(1).add(10).add(20);
            var fSymmetricDifference = fSet.symmetricDifference(fSet2);

            // Done to show that regardless of orientation the results is the same.
            var fSymmetricDifference2 = fSet2.symmetricDifference(fSet);

            assertThat(fSymmetricDifference.size()).isEqualTo(3);
            assertThat(fSymmetricDifference.contains(15)).isTrue();
            assertThat(fSymmetricDifference.contains(20)).isFalse();
            assertThat(fSymmetricDifference).isEqualTo(fSymmetricDifference2);



        }


        @Test
        @DisplayName("FSet difference test")
        void fSetDifferenceTest() {
            fSet = new FSet<>();
            fSet2 = new FSet<>();
            fSet = fSet.add(1).add(15).add(20).add(100);
            fSet2 = fSet2.add(1).add(10).add(20);
            var fSetDifference = fSet.difference(fSet2);
            var fSetDifference2 = fSet2.difference(fSet);

            FSet<Integer> expectedFSet = new FSet<>();
            FSet<Integer> expectedFSet2 = new FSet<>();
            expectedFSet2 = expectedFSet2.add(10);
            expectedFSet = expectedFSet.add(15).add(100);
            assertThat(fSetDifference).isEqualTo(expectedFSet);
            assertThat(fSetDifference2).isEqualTo(expectedFSet2);
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


        @Test
        @DisplayName("FSet add operations return new instances")
        void addOperationsReturnNewInstances() {
            var originalSet = new FSet<Integer>();
            var setWithOne = originalSet.add(1);
            var setWithTwo = setWithOne.add(2);

            assertThat(originalSet.size()).isZero();
            assertThat(setWithOne.size()).isEqualTo(1);
            assertThat(setWithOne.contains(1)).isTrue();
            assertThat(setWithTwo.size()).isEqualTo(2);
            assertThat(setWithTwo.contains(1)).isTrue();
            assertThat(setWithTwo.contains(2)).isTrue();

            // Trying to add new elements inline does not alter the originalSet

            originalSet.add(1); // Immutable object is not changed
            originalSet.add(2);
            assertThat(originalSet.size()).isZero();
            assertThat(originalSet.contains(1)).isFalse();
        }



    }

    @Nested
    class FSetTestRemovalTest{
        FSet<Integer> fSet;

        @Test
        @DisplayName("Removal by skip test")
        void removalBySkipTest() {
            fSet = new FSet<>();
            fSet =  fSet.add(1).add(15).add(20).add(100);
            var removeSet = fSet.remove(15);

            assertThat(removeSet.contains(15)).isFalse();
            assertThat(removeSet.size()).isEqualTo(3);

        }

        @Test
        @DisplayName("Removal of all elements in a FSet object isTrue Test")
        void removalOfAllElementsInAFSetObjectIsTrueTest() {
            fSet = new FSet<>();
            fSet =  fSet.add(1).add(2).add(3);
            var removeSet = fSet.remove(3).remove(2).remove(1);
            assertThat(removeSet.contains(3)).isFalse();
            assertThat(removeSet.contains(2)).isFalse();
            assertThat(removeSet.contains(1)).isFalse();
            assertThat(removeSet.isEmpty()).isTrue();
            assertThat(removeSet.size()).isZero();

        }

    }

    @Test
    @DisplayName("FSet toString method test")
    void fSetToStringMethodTest() {
        FSet<Integer> fSet = new FSet<>();
        fSet =  fSet.add(1).add(2).add(3);
        assertThat(fSet.toString()).matches("FSet \\{\\d+, \\d+, \\d+}");

    }
}
