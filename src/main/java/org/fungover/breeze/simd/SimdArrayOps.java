package org.fungover.breeze.simd;
import jdk.incubator.vector.*;

import java.util.Arrays;

/*
Features:
Array addition/subtraction
Array multiplication
Dot product
Element-wise operations
Bulk transformations

Implementation:
Use FloatVector from Vector API
Support different vector sizes
Provide both sequential and parallel versions
Include benchmarking tools
*/

public class SimdArrayOps {

    static final VectorSpecies<Integer> SPECIES = IntVector.SPECIES_PREFERRED;

    public int[] addTwoVectorArrays(int[] arr1, int[] arr2) {
        var v1 = IntVector.fromArray(SPECIES, arr1, 0);
        var v2 = IntVector.fromArray(SPECIES, arr2, 0);
        var result = v1.add(v2);
        return result.toArray();
    }

    public int[] subTwoVectorArrays(int[] arr1, int[] arr2) {
        var v1 = IntVector.fromArray(SPECIES, arr1, 0);
        var v2 = IntVector.fromArray(SPECIES, arr2, 0);
        var result = v1.sub(v2);
        return result.toArray();
    }


    public static int[] mulTwoVectorArrays(int[] arr1, int[] arr2) {
        var v1 = IntVector.fromArray(SPECIES, arr1, 0);
        var v2 = IntVector.fromArray(SPECIES, arr2, 0);
        var result = v1.sub(v2);
        return result.toArray();
    }

    public static void main(String[] args) {
        int[] arr1 = {1,2,3,4,5};
        int[] arr2 = {2,2,2,2,2};
        mulTwoVectorArrays(arr1, arr2);
        System.out.println(Arrays.toString(arr1));
    }

    public int[] dotTwoVectorArrays(int[] arr1, int[] arr2) {
        var a = IntVector.fromArray(SPECIES, arr1, 0);
        var b = IntVector.fromArray(SPECIES, arr2, 0);
        int n = arr1.length;
        int sum = 0;
        //for (int i = 0; i < n; i++) {
       //     sum += a[i] * b[i];
        //}
        return new int[]{1, 2};
    }

}
