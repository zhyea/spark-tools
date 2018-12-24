package brand.common.tools

import java.io.Closeable

/**
  * @author robin
  */
object IOKit {


  def using[A <: Closeable, B](a: A)(func: A => B): B = {
    try {
      func(a)
    } finally {
      if (null != a) a.close()
    }
  }


}
