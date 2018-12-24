package org.chobit.jobs.common.tools

import java.util.Properties

import org.apache.hadoop.io.IOUtils

import scala.tools.nsc.interpreter.InputStream


/**
  * @author robin
  */
object PropKit {


  def load(path: String): Properties = {
    val props = new Properties()
    var in: InputStream = null
    try {
      in = PropKit.getClass.getResourceAsStream(path)
      props.load(in)
    } finally {
      IOUtils.closeStream(in)
    }
    props
  }

  def getString(props: Properties, key: String, defaultValue: String = null): String = {
    if (props.containsKey(key))
      props.getProperty(key)
    else
      defaultValue
  }


  def getInt(props: Properties, key: String, defaultValue: Int = 0): Int = {
    if (props.containsKey(key))
      props.getProperty(key).toInt
    else
      defaultValue
  }


  def getLong(props: Properties, key: String, defaultValue: Long = 0L): Long = {
    if (props.containsKey(key))
      props.getProperty(key).toLong
    else
      defaultValue
  }


  def getDouble(props: Properties, key: String, defaultValue: Double = 0.0): Double = {
    if (props.containsKey(key))
      props.getProperty(key).toDouble
    else
      defaultValue
  }


  def getBool(props: Properties, key: String, defaultValue: Boolean = false): Boolean = {
    if (props.containsKey(key))
      props.getProperty(key).toBoolean
    else
      defaultValue
  }
}