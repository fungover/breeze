package org.fungover.breeze.vector;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertThrows;

class Vector4Test {

    @Test
    @DisplayName("Adds to vector")
    void addsToVector(){
        Vector4 vector = new Vector4(3,3,3,3);
        Vector4 vector2 = new Vector4(3,3,3,3);
        vector.add(vector2);
        assertAll(
                () -> assertThat(vector.getX()).isEqualTo(6.0f),
                () -> assertThat(vector.getY()).isEqualTo(6.0f),
                () -> assertThat(vector.getW()).isEqualTo(6.0f),
                () -> assertThat(vector.getZ()).isEqualTo(6.0f)
        );
    }

    @Test
    @DisplayName("Sub from vectpr")
    void subFromVector(){
        Vector4 vector = new Vector4(6,6,6,6);
        Vector4 vector2 = new Vector4(3,3,3,3);
        vector.sub(vector2);
        assertAll(
                () -> assertThat(vector.getX()).isEqualTo(3.0f),
                () -> assertThat(vector.getY()).isEqualTo(3.0f),
                () -> assertThat(vector.getW()).isEqualTo(3.0f),
                () -> assertThat(vector.getZ()).isEqualTo(3.0f)
        );
    }

    @Test
    @DisplayName("Mul vector")
    void mulVector(){
        Vector4 vector = new Vector4(3,3,3,3);
        vector.mul(2);
        assertAll(
                () -> assertThat(vector.getX()).isEqualTo(6.0f),
                () -> assertThat(vector.getY()).isEqualTo(6.0f),
                () -> assertThat(vector.getW()).isEqualTo(6.0f),
                () -> assertThat(vector.getZ()).isEqualTo(6.0f)
        );
    }

    @Test
    @DisplayName("Div vector")
    void divVector(){
        Vector4 vector = new Vector4(6,6,6,6);
        vector.div(2);
        assertAll(
                () -> assertThat(vector.getX()).isEqualTo(3.0f),
                () -> assertThat(vector.getY()).isEqualTo(3.0f),
                () -> assertThat(vector.getW()).isEqualTo(3.0f),
                () -> assertThat(vector.getZ()).isEqualTo(3.0f)
        );
    }


}
