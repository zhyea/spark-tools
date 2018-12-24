package org.chobit.jobs.common.tools

/**
  * @author robin
  */
object Args {

  def check(isSuccess: Boolean, message: String): Unit = {
    if (!isSuccess)
      throw new IllegalArgumentException(message)
  }

}
