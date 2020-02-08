## Facade Pattern
- 여러 서비스에 걸쳐 얽힌 기능들을 aggregation해서 사용하는 패턴
- API를 간결화해서 제공할 수 있다
- 서비스들의 결합도를 낮출 수 있다
    1. 자러 가기 전의 행동들을 생각해보자
        1. 샤워를 한다
        1. 불을 끈다
        1. 침대에 눕는다
        1. 눈을 감는다
    1. facade layer가 없을 경우 4개의 서비스를 의존성 주입을 해서 각각의 기능을 순서대로 실행해야 한다
    
```
class PersonService {
    fun closeEyes(): Boolean
}

class LightService {
    fun turnOff(): Boolean
}

class ShowerService {
    fun clean(): Boolean
}

class BedService {
    fun layDown(): Boolean
}

showerService.clean()
lightService.turnOff()
bedService.layDown()
personService.closeEyes()
```

```
class PersonFacade {
    fun sleep() {
        showerService.clean()
        lightService.turnOff()
        bedService.layDown()
        personService.closeEyes()
    }
}

personFacade.sleep()
```