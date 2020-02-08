package com.yckim.designpattern.behavioral.iterator

class UsersIterator(
    private val users: Users
) : Iterator {

    private var index = 0

    override fun hasNext() = users.length() > index

    override fun next(): User {
        val user = users.get(index)
        index++
        return user
    }
}