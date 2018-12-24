package brand.common.tools

import org.junit.{Assert, Test}

/**
  * @author robin
  */
@Test
class DataKitTest extends Assert {

  @Test def parse(): Unit = {
    val pattern = "yyyy-MM-dd=HH:mm:ss"
    val str = "2018-02-25=00:00:00"
    val date = DateKit.parse(pattern, str)
    println(date)
  }

}
