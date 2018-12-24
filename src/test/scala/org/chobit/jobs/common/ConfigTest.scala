package org.chobit.jobs.common

import org.junit.{Assert, Test}


/**
  * @author robin
  */
@Test
class ConfigTest extends Assert {

  val config = new Config {}


  @Test def getString: Unit = {
    val v = config.getString("a")
    assert(v == "abs")
  }

  @Test def getInt: Unit = {
    val v = config.getInt("b")
    assert(v == 1)
  }

  @Test def getLong: Unit = {
    val v = config.getLong("b")
    assert(v == 1L)
  }

  @Test def getDouble: Unit = {
    val v = config.getDouble("c")
    assert(v == 1.2)
  }

  @Test def getBoolean: Unit = {
    val v = config.getBool("d")
    assert(v)
  }


}
