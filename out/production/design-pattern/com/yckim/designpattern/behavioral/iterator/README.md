## 이터레이터 패턴
1. 어떤 Collection인지 구현 방법을 노출 시키지 않고도, 모든 Element에 접근할 수 있는 방법을 제공
1. 자료구조의 **동작과 접근을 분리**
1. iterator를 구현하면 `for`, `foreach` 반복문을 사용할 수 있다 
 
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

```