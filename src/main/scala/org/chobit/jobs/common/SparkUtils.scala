package org.chobit.jobs.common

import org.apache.spark.internal.Logging
import org.apache.spark.sql.SparkSession
import org.apache.spark.{SparkConf, SparkContext}


/**
  * @author robin
  */
object SparkUtils extends Logging {


  /**
    * 执行作业
    */
  def runWithContext[C <: Config](job: (C, SparkContext) => Unit,
                                  config: C,
                                  context: SparkContext,
                                  errorMsg: String = "任务执行失败"): Unit = {
    val start = System.currentTimeMillis()
    try {
      job(config, context)
    } catch {
      case ex: Throwable => ex.printStackTrace(); print(errorMsg); throw ex
    } finally {
      logInfo(s"Cost Time: ${System.currentTimeMillis() - start}")
    }
  }

  /**
    * 执行作业
    */
  def runWithSession[C <: Config](job: (C, SparkSession) => Unit,
                                  config: C,
                                  session: SparkSession,
                                  errorMsg: String = "任务执行失败"): Unit = {
    val start = System.currentTimeMillis()
    try {
      job(config, session)
    } catch {
      case ex: Throwable => ex.printStackTrace(); print(errorMsg); throw ex
    } finally {
      logInfo(s"Cost Time: ${System.currentTimeMillis() - start}")
    }
  }


  /**
    * 初始化spark上下文
    */
  def newContext(appName: String, master: String = null): SparkContext = {
    val sparkConf = new SparkConf().setAppName(appName)
    sparkConf.set("spark.driver.userClassPathFirst", "true")
    sparkConf.set("spark.executor.userClassPathFirst", "true")

    if (null == master && null == sparkConf.get("spark.master", null))
      sparkConf.setMaster("local[*]")
    if (null != master)
      sparkConf.setMaster(master)

    new SparkContext(sparkConf)
  }


  /**
    * 初始化SparkSession
    */
  def newSession(appName: String, master: String = null): SparkSession = {
    val builder = SparkSession.builder()
      .appName(appName)
      .config("spark.driver.userClassPathFirst", value = true)
      .config("spark.executor.userClassPathFirst", value = true)

    if (null != master)
      builder.master(master)

    builder.getOrCreate()
  }
}
