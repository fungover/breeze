package org.fungover.breeze.vector;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class Vector3Test {

    @Test
    @DisplayName("Adds to vector")
    void addsToVector() {
        Vector3 vector = new Vector3(3, 3,3);
        Vector3 vector3 = new Vector3(3, 3,3);
        vector.add(vector3);
        assertAll(
                () -> assertThat(vector.getX()).isEqualTo(6.0f),
                () -> assertThat(vector.getY()).isEqualTo(6.0f),
                () -> assertThat(vector.getZ()).isEqualTo(6.0f)
        );
    }
    @Test
    @DisplayName("Sub from vector")
    void subFromVector() {
        Vector3 vector = new Vector3(6, 6,6);
        Vector3 vector2 = new Vector3(3, 3,3);
        vector.sub(vector2);
        assertAll(
                () -> assertThat(vector.getX()).isEqualTo(3.0f),
                () -> assertThat(vector.getY()).isEqualTo(3.0f),
                () -> assertThat(vector.getZ()).isEqualTo(3.0f)
        );
    }

    @Test
    @DisplayName("Mul vector")
    void mulVector() {
        Vector3 vector = new Vector3(3, 3,3);
        vector.mul(2);
        assertAll(
                () -> assertThat(vector.getX()).isEqualTo(6.0f),
                () -> assertThat(vector.getY()).isEqualTo(6.0f),
                () -> assertThat(vector.getZ()).isEqualTo(6.0f)
        );
    }

    @Test
    @DisplayName("Div vector")
    void divVector() {
        Vector3 vector = new Vector3(6, 6,6);
        vector.div(2);
        assertAll(
                () -> assertThat(vector.getX()).isEqualTo(3.0f),
                () -> assertThat(vector.getY()).isEqualTo(3.0f),
                () -> assertThat(vector.getZ()).isEqualTo(3.0f)
        );
    }
    @Test
    @DisplayName("Correct dot product")
    void correctDotProduct() {
        Vector3 vector = new Vector3(3, 3,3);
        Vector3 vector3 = new Vector3(3, 3,3);
        assertThat(vector.dot(vector3)).isEqualTo(27.0f);
    }
    
    @Test
    @DisplayName("new vector from cross product")
    void newVectorFromCrossProduct(){
        Vector3 vector = new Vector3(1, 2,3);
        Vector3 vector2 = new Vector3(4, 5,6);
        var v = vector.cross(vector2);
        assertAll(
                () -> assertThat(v.getX()).isEqualTo(-3.0f),
                () -> assertThat(v.getY()).isEqualTo(6.0f),
                () -> assertThat(v.getZ()).isEqualTo(-3.0f)
        );
    }
    
    

}
