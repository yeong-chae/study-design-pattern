## Iterator Pattern
1. 어떤 Collection인지 구현 방법을 노출 시키지 않고도, 모든 Element에 접근할 수 있는 방법을 제공
1. 자료구조의 **동작과 접근 알고리즘을 분리**
1. iterator를 구현하면 `for`, `foreach` 반복문을 사용할 수 있다 
1. 문득 드는 생각은 이것도 자료구조의 응집도와, 결합도를 생각해서 나온 패턴이 아닐까 싶다
 
```
// 동작과 접근을 연결시켜주는 역할
interface Aggregate {
    fun iterator(): Iterator
}

// Collection의 접근을 추상화한 interface
interface Iterator {
    fun hasNext(): Boolean
    fun next(): Any
}
```

```
// Element 역할
data class User(
    val id: Long,
    val name: String
)

// User의 집합인 Users Collection, Collection의 동작 로직 구현
class Users : Aggregate {

    private val users: MutableList<User> = mutableListOf()

    fun add(user: User) {
        users.add(user)
    }

    fun get(index: Int) = users[index]

    fun length(): Int = users.size

    override fun iterator(): Iterator = UsersIterator(this)
}

// Users Collection의 접근 로직 구현
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
```

```
fun main() {
    val users = Users()
    users.add(User(1, "홍길동"))
    users.add(User(2, "James"))

    val iterator = users.iterator()

    while(iterator.hasNext()) {
        val user = iterator.next()
        println(user)
    }
}

// 실행 결과
User(id=1, name=홍길동)
User(id=2, name=James)
```