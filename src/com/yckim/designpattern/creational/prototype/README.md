

## Prototype Pattern

Prototype Pattern은 **생성(Creational)** 패턴으로 **객체(Instance)**를 생성하는 방법 중의 하나이다
대표적인 Prototype-based language로는 `Javascript`, `Perl`, `R` 등이 있다

한줄 요약부터 먼저 하자면,
> 이미 만들어져있는 객체를 **복사해서 객체를 생성**하는 패턴이다


Prototype Pattern을 자세히 알아보기에 앞서 내가 주로 사용하는 언어인 Java, Kotlin은 어떤 방법으로 객체를 생성하냐를 먼저 알아보겠다

Java와 Kotlin은 모두 Class-based language이다

Class-based language는 객체의 스펙을 정의해둔 Class를 new 연산자를 통해 객체를 생성하게 된다

```
class Person {
	String name;
    
	Person(name) {
    	this.name = name;
    }
}

Person person = new Person("영채");
```

그리고, Javscript의 객체 생성 코드이다
```
function Person(name) {
	this.name = name
}

const person = new Person('영채');
```

아래는 console로 person을 찍은 결과이다
```
Person {name: "영채"}
	name: "영채"
	__proto__:
        constructor: ƒ Person(name)
        __proto__: Object
```
`__proto__` 객체가 생겨 있는 걸 볼 수 있고 `__proto__`는 모든 Perosn의 instance에서 `Singleton`으로 동작한다.

`Singleton` 으로 동작함에 따라 Runtime시에 Person.prototype.으로 어떤 동작 또는 프로퍼티를 추가 했을 때 이미 생성 되어있는 객체에서조차 접근, 실행이 가능해진다.

Function 객체의 `__proto__`를 추가해주는 동작이 Javascript 실행 엔진 내부 어딘가에 밑에 코드와 같은 동작이 있을거라고 유추 할 수 있다.
```
funtion Prototype(function, key, value) {
    this.prototypeMapByFunction = new Map();
	  this.prototypeMapByFunction.set(function, new Map())
    this.prototypeMapByFunction.get(function).set(key, value);
}

// 프로토타입 객체는 싱글턴으로 존재할 것 같다
const prototype = Prototype()

function Person(name) {
	this.name = name
    this.__proto__ = prototype('Person')
}

Person.prototype.height = 181; // 를 실행시키면
person.__proto__.set('Person', 'height', 181); // 내부적으로 이렇게 동작하지 않을까?
```
---

#### 궁금해서 v8 엔진 소스를 봤다
```
Handle<JSObject> Factory::NewFunctionPrototype(Handle<JSFunction> function) {

  // 함수 때문에 전역 컨텍스트를 함수 컨텍스트에서 사용해야합니다.
  // 다른 상황에있을 수 있습니다.
  Handle<NativeContext> native_context(function->context().native_context(),
                                       isolate());
  Handle<Map> new_map;
  
  if (V8_UNLIKELY(IsAsyncGeneratorFunction(function->shared().kind()))) {
    new_map = handle(native_context->async_generator_object_prototype_map(),
                     isolate());
  } else if (IsResumableFunction(function->shared().kind())) {
    // 생성기 및 비동기 함수 프로토 타입은 맵을 공유 할 수 있습니다.
    // "생성자" 속성이 없습니다
    new_map =
        handle(native_context->generator_object_prototype_map(), isolate());
  } else {
    // 각 함수 프로토 타입은 원치 않는 공유를 피하기 위해 새로운 맵을 얻습니다.
    // 다른 생성자의 프로토 타입간에 매핑합니다.
    Handle<JSFunction> object_function(native_context->object_function(),
                                       isolate());
    DCHECK(object_function->has_initial_map());
    new_map = handle(object_function->initial_map(), isolate());
  }

  DCHECK(!new_map->is_prototype_map());
  Handle<JSObject> prototype = NewJSObjectFromMap(new_map);

  if (!IsResumableFunction(function->shared().kind())) {
    JSObject::AddProperty(isolate(), prototype, constructor_string(), function,
                          DONT_ENUM);
  }

  return prototype;
}
```

딴길로 샌 느낌이다

---
- 참고자료
	-  https://developer.mozilla.org/ko/docs/Web/JavaScript/Guide/%EA%B0%9D%EC%B2%B4_%EB%AA%A8%EB%8D%B8%EC%9D%98_%EC%84%B8%EB%B6%80%EC%82%AC%ED%95%AD
	-  https://github.com/v8/v8