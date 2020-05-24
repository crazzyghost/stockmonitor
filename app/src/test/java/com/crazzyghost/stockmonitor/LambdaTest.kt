package com.crazzyghost.stockmonitor

class LambdaTest {

    fun function(a: Int, b: Int, operator: (Int, Int) -> Int): Int {
           return operator(a, b);
    }
}