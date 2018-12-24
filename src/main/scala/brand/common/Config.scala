package brand.common

import brand.common.tools.PropKit

/**
  * @author robin
  */
trait Config extends Serializable {

  private val props = PropKit.load("/config.properties")


  def load(configFile: String): Unit = {
    val p = PropKit.load(configFile)
    props.putAll(p)
  }


  def getString(key: String, defaultValue: String = null): String =
    PropKit.getString(props, key, defaultValue)


  def getInt(key: String, defaultValue: Int = 0): Int =
    PropKit.getInt(props, key, defaultValue)


  def getLong(key: String, defaultValue: Long = 0L): Long =
    PropKit.getLong(props, key, defaultValue)


  def getDouble(key: String, defaultValue: Double = 0.0): Double =
    PropKit.getDouble(props, key, defaultValue)


  def getBool(key: String, defaultValue: Boolean = false): Boolean =
    PropKit.getBool(props, key, defaultValue)
}
