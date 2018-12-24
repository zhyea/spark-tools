package brand.common.tools

import com.fasterxml.jackson.core.`type`.TypeReference
import com.fasterxml.jackson.databind.{DeserializationFeature, ObjectMapper}
import com.fasterxml.jackson.module.scala.DefaultScalaModule


/**
  * @author robin
  */
object JSON {


  val mapper = new ObjectMapper()
    .registerModule(DefaultScalaModule)
    .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
    .configure(DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES, false)
    .configure(DeserializationFeature.FAIL_ON_INVALID_SUBTYPE, false)
    .configure(DeserializationFeature.FAIL_ON_MISSING_CREATOR_PROPERTIES, false)
    .configure(DeserializationFeature.FAIL_ON_NULL_FOR_PRIMITIVES, false)


  def toJson(value: AnyRef): String = {
    mapper.writeValueAsString(value)
  }


  def fromJson[T](json: String, tr: TypeReference[T]): T = {
    mapper.readValue(json, tr)
  }


  def fromJson[T](json: String, valueType: Class[T]): T = {
    mapper.readValue(json, valueType)
  }


  def toMap[T](json: String): Map[String, T] = {
    val tr = new TypeReference[Map[String, T]] {}
    fromJson(json, tr)
  }


  def toBytes(value: AnyRef): Array[Byte] = {
    mapper.writeValueAsBytes(value)
  }

}
