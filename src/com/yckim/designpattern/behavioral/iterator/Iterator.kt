package com.yckim.designpattern.behavioral.iterator

interface Iterator {
    fun hasNext(): Boolean
    fun next(): Any
}