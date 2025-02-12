package vector;

import org.fungover.breeze.vector.Vector2;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertThrows;


class Vector2Test {


    @Test
    @DisplayName("Adds to vector X")
    void addsToVectorX() {
        Vector2 vector = new Vector2(3, 3);
        Vector2 vector2 = new Vector2(3, 3);
        vector.add(vector2);
        assertThat(vector).returns(6.0f, Vector2::getX);
    }

    @Test
    @DisplayName("Adds to vector Y")
    void addsToVectorY() {
        Vector2 vector = new Vector2(3, 3);
        Vector2 vector2 = new Vector2(3, 3);
        vector.add(vector2);
        assertThat(vector).returns(6.0f, Vector2::getY);
    }

    @Test
    @DisplayName("Sub from vector X")
    void subFromVectorX() {
        Vector2 vector = new Vector2(6, 6);
        Vector2 vector2 = new Vector2(3, 3);
        vector.sub(vector2);
        assertThat(vector).returns(3.0f, Vector2::getX);
    }

    @Test
    @DisplayName("Sub from vector y")
    void subFromVectorY() {
        Vector2 vector = new Vector2(6, 6);
        Vector2 vector2 = new Vector2(3, 3);
        vector.sub(vector2);
        assertThat(vector).returns(3.0f, Vector2::getY);
    }

    @Test
    @DisplayName("Mul vector X")
    void mulVectorX() {
        Vector2 vector = new Vector2(3, 3);
        vector.mul(2);
        assertThat(vector).returns(6.0f, Vector2::getX);
    }

    @Test
    @DisplayName("Mul vector Y")
    void mulVectorY() {
        Vector2 vector = new Vector2(3, 3);
        vector.mul(2);
        assertThat(vector).returns(6.0f, Vector2::getY);
    }

    @Test
    @DisplayName("Div vector")
    void divVector(){
        Vector2 vector = new Vector2(6, 6);
        vector.div(2);
        assertAll(
                () -> assertThat(vector.getX()).isEqualTo(3.0f),
                () -> assertThat(vector.getY()).isEqualTo(3.0f)
        );
    }

    @Test
    @DisplayName("Correct dot product")
    void correctDotProduct() {
        Vector2 vector = new Vector2(3, 3);
        Vector2 vector2 = new Vector2(3, 3);
        assertThat(vector.dot(vector2)).isEqualTo(18.0f);
    }

    @Test
    @DisplayName("Correct length")
    void correctLength() {
        Vector2 vector = new Vector2(3, 4);
        assertThat(vector.length()).isEqualTo(5.0f);
    }

    @Test
    @DisplayName("Normalize vector")
    void normalizeVector(){
        Vector2 vector = new Vector2(3, 4);
        var v = vector.normalize();
        assertAll(
                () -> assertThat(v.getX()).isEqualTo(0.6f),
                () -> assertThat(v.getY()).isEqualTo(0.8f)
        );
    }


    @Test
    @DisplayName("Distance between vectors")
    void distanceBetweenVectors() {
        Vector2 vector = new Vector2(3, 3);
        Vector2 vector2 = new Vector2(6, 6);
        assertThat(vector.distance(vector2,vector)).isEqualTo((float)Math.sqrt(18));
    }



    @Test
    @DisplayName("Linear interpolation find point between vectors")
    void linearInterpolationFindPointBetweenVectors(){
        Vector2 vector = new Vector2(3, 3);
        Vector2 vector2 = new Vector2(6, 6);
        var v = vector.linear(vector,vector2, 0.5f);
        assertAll(
                () -> assertThat(v.getX()).isEqualTo(4.5f),
                () -> assertThat(v.getY()).isEqualTo(4.5f)
        );
    }

    @Test
    @DisplayName("Linear interpolation with lerp larger than 1 throw exception")
    void linearInterpolationWithLerpLargerThan1ThrowException(){
       var exception = assertThrows(IllegalArgumentException.class, () -> {
           Vector2 vector = new Vector2(3, 3);
           Vector2 vector2 = new Vector2(6, 6);
           vector.linear(vector,vector2, 1.1f);
       });
       assertThat(exception.getMessage()).isEqualTo("lerp can not be larger than 1");
    }
    @Test
    @DisplayName("Linear interpolation with lerp less than 0 throw exception")
    void linearInterpolationWithLerpLessThan0ThrowException(){
        var exception = assertThrows(IllegalArgumentException.class, () -> {
            Vector2 vector = new Vector2(3, 3);
            Vector2 vector2 = new Vector2(6, 6);
            vector.linear(vector,vector2, -0.1f);
        });
        assertThat(exception.getMessage()).isEqualTo("lerp can not be negative");
    }


    @Test
    @DisplayName("Find the min value of two vectors")
    void findTheMinValueOfTwoVectors(){
        Vector2 vector = new Vector2(3, 6);
        Vector2 vector2 = new Vector2(4, 5);
        var v = vector.min(vector, vector2);

        assertAll(
                () -> assertThat(v.getX()).isEqualTo(3.0f),
                () -> assertThat(v.getY()).isEqualTo(5.0f)
        );
    }

    @Test
    @DisplayName("Find the max value of two vectors")
    void findTheMaxValueOfTwoVectors(){
        Vector2 vector = new Vector2(3, 6);
        Vector2 vector2 = new Vector2(4, 5);
        var v = vector.max(vector, vector2);

        assertAll(
                () -> assertThat(v.getX()).isEqualTo(4.0f),
                () -> assertThat(v.getY()).isEqualTo(6.0f)
        );
    }


}
