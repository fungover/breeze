package vector;

import org.fungover.breeze.vector.Vector2;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;


class Vector2Test {


    @Test
    @DisplayName("Adds to vector X")
    void addsToVectorX() {
        Vector2 vector = new Vector2(3, 3);
        Vector2 vector2 = new Vector2(3, 3);
        vector.add(vector2);
        assertThat(vector).extracting(Vector2::getX).isEqualTo(6.0f);
    }

    @Test
    @DisplayName("Adds to vector Y")
    void addsToVectorY() {
        Vector2 vector = new Vector2(3, 3);
        Vector2 vector2 = new Vector2(3, 3);
        vector.add(vector2);
        assertThat(vector).extracting(Vector2::getY).isEqualTo(6.0f);
    }

    @Test
    @DisplayName("Sub from vector X")
    void subFromVectorX() {
        Vector2 vector = new Vector2(6, 6);
        Vector2 vector2 = new Vector2(3, 3);
        vector.sub(vector2);
        assertThat(vector).extracting(Vector2::getX).isEqualTo(3.0f);
    }

    @Test
    @DisplayName("Sub from vector y")
    void subFromVectorY() {
        Vector2 vector = new Vector2(6, 6);
        Vector2 vector2 = new Vector2(3, 3);
        vector.sub(vector2);
        assertThat(vector).extracting(Vector2::getY).isEqualTo(3.0f);
    }

    @Test
    @DisplayName("Mul vector X")
    void mulVectorX() {
        Vector2 vector = new Vector2(3, 3);
        vector.mul(2);
        assertThat(vector).extracting(Vector2::getX).isEqualTo(6.0f);
    }

    @Test
    @DisplayName("Mul vector Y")
    void mulVectorY() {
        Vector2 vector = new Vector2(3, 3);
        vector.mul(2);
        assertThat(vector).extracting(Vector2::getY).isEqualTo(6.0f);
    }

    @Test
    @DisplayName("Div vector X")
    void divVectorX() {
        Vector2 vector = new Vector2(6, 6);
        vector.div(2);
        assertThat(vector).extracting(Vector2::getX).isEqualTo(3.0f);
    }

    @Test
    @DisplayName("Div vector Y")
    void divVectorY() {
        Vector2 vector = new Vector2(6, 6);
        vector.div(2);
        assertThat(vector).extracting(Vector2::getY).isEqualTo(3.0f);
    }
    @Test
    @DisplayName("Correct dot product")
    void correctDotProduct(){
        Vector2 vector = new Vector2(3, 3);
        Vector2 vector2 = new Vector2(3, 3);
        assertThat(vector.dot(vector2)).isEqualTo(18.0f);
    }

    @Test
    @DisplayName("Correct length")
    void correctLength(){
        Vector2 vector = new Vector2(3, 4);
        assertThat(vector.length()).isEqualTo(5.0f);
    }

    @Test
    @DisplayName("normalize vector X")
    void normalizeVectorX(){
        Vector2 vector = new Vector2(3, 4);
        assertThat(vector.normalize()).extracting(Vector2::getX).isEqualTo(0.6f);
    }
    @Test
    @DisplayName("normalize vector Y")
    void normalizeVectorY(){
        Vector2 vector = new Vector2(3, 4);
        assertThat(vector.normalize()).extracting(Vector2::getY).isEqualTo(0.8f);
    }






}
