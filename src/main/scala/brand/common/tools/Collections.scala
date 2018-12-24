package brand.common.tools

/**
  * @author robin
  */
object Collections {

  /**
    * 取一个集合中的最大值
    */
  def maxOf[T <: Comparable[T]](all: Iterable[_ <: T], default: T): T = {
    if (all.isEmpty)
      default
    else
      all.toList.sortWith { (a, b) => a.compareTo(b) > 0 }.head
  }
  
}
