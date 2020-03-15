## Composite Pattern

> 어떤 객체의 그룹을 단일 객체처럼 Handling 할 수 있게 하는 것

가장 유명한 유즈케이스는 Directory - File 구조라고 한다

Directory - File 구조를 Composite Pattern으로 구현해보자

```
interface Node {
  val name: String
  val path: String
  val size: Int

  fun delete()
} 

class Directory(
  override val path: String,
  override val name: String
) : Node {

  override val size: Int = children.sumBy { it.size }
  
  fun createFile(name: String): File {
    val newFile = File("$path/$name", name)
    add(newFile)
  }

  fun createDirectory(name: String): Directory {
    val newDirectory = Directory("$path/$name", name)
    add(newDirectory)
  }

  fun add(node: Node) {
    children.add(node)
  }
  
  override fun delete() {
    children.foreach {
      it.delete()
    }
  }

  var children: MutableList<Node> = mutableListOf()
}

class File(
  override val path: String,
  override val name: String
) : Node {
  override val size: Int = java.io.File(path).getSize()

  override fun delete() {
    java.io.File(path).delete()
  }
}

val rootDirectory = new Directory("~/Documents", "Documents")
val childFile = rootDirectory.createFile("file1")
val childDirectory = rootDirectory.createDirectory("directory1")
childDirectory.createFile("childFile1")

```

rootDirectory의 size를 가져올 때 Directory냐, File이냐의 구분없이 size의 합을 가져올 수 있고 