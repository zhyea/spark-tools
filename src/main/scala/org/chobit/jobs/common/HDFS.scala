package org.chobit.jobs.common

import java.io.{ByteArrayOutputStream, FilterOutputStream}
import java.nio.charset.Charset

import org.apache.hadoop.conf.Configuration
import org.apache.hadoop.fs._
import org.apache.hadoop.io.SequenceFile.Writer
import org.apache.hadoop.io.{BytesWritable, IOUtils, SequenceFile, Text}

/**
  * @author robin
  */
object HDFS {

  /**
    * 打开文件，获取InputStream
    */
  def open(path: String): FSDataInputStream = open(new Path(path))


  /**
    * 打开文件，获取InputStream
    */
  def open(path: Path): FSDataInputStream = fs.open(path)


  /**
    * 判断路径在HDFS中是否存在
    */
  def exists(path: String): Boolean = exists(new Path(path))


  /**
    * 判断路径在HDFS中是否存在
    */
  def exists(path: Path): Boolean = {
    try {
      fs.exists(path)
    } catch {
      case e: Exception => false
    }
  }


  /**
    * 执行删除操作
    */
  def delete(path: String): Boolean = fs.delete(new Path(path), true)


  /**
    * 列出指定目录下的所有文件
    */
  def files(parentPath: String): Iterable[Path] = files(new Path(parentPath))

  /**
    * 列出指定目录下的所有文件
    *
    * 文件名为路径（非URI格式）
    */
  @Deprecated def files2(parentPath: String): Iterable[String] = files(new Path(parentPath)).map(p => p.toUri.getPath)

  /**
    * 列出指定目录下的所有文件
    */
  def files(parent: Path): Iterable[Path] = fs.listStatus(parent).map(f => f.getPath)

  /**
    * 列出指定目录下的所有文件
    */
  def files(parents: Iterable[Path]): Iterable[Path] = fs.listStatus(parents.toArray).map(f => f.getPath)

  /**
    * 列出指定目录下的所有文件
    *
    * 文件名为路径（非URI格式）
    */
  def childrenPaths(parentPath: String): Iterable[String] = files(new Path(parentPath)).map(p => p.toUri.getPath)


  /**
    * 保存文本信息到指定的目录
    */
  def saveText(path: String, text: String, charset: Charset = Charset.defaultCharset(), overwrite: Boolean = true): Unit = {
    checkOverwrite(path, overwrite)

    var out: FilterOutputStream = null
    try {
      out = new FilterOutputStream(fs.create(new Path(path)))
      out.write(text.getBytes(charset))
    } finally {
      IOUtils.closeStream(out)
    }
  }


  /**
    * 读取文本文件
    */
  def readText(path: String, charset: Charset = Charset.defaultCharset()): String = {
    val bytes = get(path)
    new String(bytes, charset)
  }


  /**
    * 从HDFS get文件
    */
  def get(path: String): Array[Byte] = {
    var in: FSDataInputStream = null
    var output: ByteArrayOutputStream = null
    try {
      in = open(path)
      output = new ByteArrayOutputStream
      val buff = new Array[Byte](100)
      var rc = in.read(buff, 0, 100)
      while (rc > 0) {
        output.write(buff, 0, rc)
        rc = in.read(buff, 0, 100)
      }

      output.toByteArray
    } finally {
      IOUtils.closeStream(in)
      IOUtils.closeStream(output)
    }
  }


  /**
    * 文件重命名
    */
  def rename(src: Path, dest: String): Boolean = {
    fs.rename(src, new Path(dest))
  }


  /**
    * put文件到HDFS
    */
  def put(path: String, bytes: Array[Byte], overwrite: Boolean = false): Unit = {
    checkOverwrite(path, overwrite)
    var out: FSDataOutputStream = null
    try {
      out = fs.create(new Path(path))
      out.write(bytes)
      out.flush()
    } finally IOUtils.closeStream(out)
  }


  /**
    * 保存为SequenceFile
    */
  def saveAsSequenceFile(path: String, key: String, value: Array[Byte]): Unit = {
    val writer = SequenceFile.createWriter(config,
      Writer.file(new Path(path)),
      Writer.keyClass(classOf[Text]),
      Writer.valueClass(classOf[BytesWritable]))
    try {
      writer.append(new Text(key), new BytesWritable(value))
    } finally {
      IOUtils.closeStream(writer)
    }
  }

  def getFs(conf: Configuration = config): FileSystem = FileSystem.get(conf)

  def configuration: Configuration = config

  private val config = new Configuration()

  private val fs: FileSystem = getFs()


  private def checkOverwrite(path: String, overwrite: Boolean): Unit = {
    if (exists(path)) {
      if (overwrite) {
        delete(path)
      } else {
        throw new IllegalAccessError(s"'$path' already exists.")
      }
    }
  }

}
