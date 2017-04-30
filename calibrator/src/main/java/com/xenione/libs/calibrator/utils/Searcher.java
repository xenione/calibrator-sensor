package com.xenione.libs.calibrator.utils;
/*
Copyright 30/04/2017 Eugeni Josep Senent i Gabriel

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
*/

import android.util.SparseArray;


public class Searcher {

    public static <T> T binarySearch(SparseArray<T> array, int key) {
        T searched = array.get(key);
        if (searched != null) {
            return searched;
        }
        int lo = 0;
        int hi = array.size() - 1;
        int lastKey = 0;
        while (lo < hi) {
            int mid = (lo + hi) / 2;
            lastKey = array.keyAt(mid);
            if (key < lastKey) {
                hi = mid - 1;
            } else {
                lo = mid + 1;
            }
        }
        return array.get(lastKey);
    }

    public static int binarySearch(int[] array, int value) {
        int lo = 0;
        int hi = array.length - 1;

        while (lo <= hi) {
            final int mid = (lo + hi) >>> 1;
            final int midVal = array[mid];

            if (midVal < value) {
                lo = mid + 1;
            } else if (midVal > value) {
                hi = mid - 1;
            } else {
                return mid;  // value found
            }
        }
        return -1;  // value not present
    }
}
