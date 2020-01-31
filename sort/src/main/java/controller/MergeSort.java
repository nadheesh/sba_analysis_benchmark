package controller;

import java.util.ArrayList;
import java.util.Random;

class MergeSort {
    public void merge(double[] arr, int l, int m, int r) {
        // Find sizes of two subarrays to be merged
        int n1 = m - l + 1;
        int n2 = r - m;

        /* Create temp arrays */
        double[] L = new double[n1];
        double[] R = new double[n2];

        /*Copy data to temp arrays*/
        for (int i = 0; i < n1; ++i)
            L[i] = arr[l + i];
        for (int j = 0; j < n2; ++j)
            R[j] = arr[m + 1 + j];


        int i = 0, j = 0;

        int k = l;
        while (i < n1 && j < n2) {
            if (L[i] <= R[j]) {
                arr[k] = L[i];
                i++;
            } else {
                arr[k] = R[j];
                j++;
            }
            k++;
        }

        while (i < n1) {
            arr[k] = L[i];
            i++;
            k++;
        }

        while (j < n2) {
            arr[k] = R[j];
            j++;
            k++;
        }
    }

    public void sort(double[] arr, int l, int r) {
        if (l < r) {
            int m = (l + r) / 2;

            sort(arr, l, m);
            sort(arr, m + 1, r);

            merge(arr, l, m, r);
        }
    }

    public double[] getRandomNumberArray(int number) {

        double[] numbers = new double[number];
        Random random = new Random();

        for (int i = 0; i < number; i++) {
            numbers[i] = random.nextDouble();
        }
        return numbers;
    }

    public double[] sortNNumbers(int n) {
        double[] arr = getRandomNumberArray(n);
        sort(arr, 0, arr.length - 1);
        return arr;
    }
}