package com.yckim.designpattern.behavioral.iterator

class Users : Aggregate {

    private val users: MutableList<User> = mutableListOf()

    fun add(user: User) {
        users.add(user)
    }

    fun get(index: Int) = users[index]

    fun length(): Int = users.size

    override fun iterator(): Iterator = UsersIterator(this)
}
