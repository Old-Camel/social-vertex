package cn.net.polyglot

import cn.net.polyglot.testframework.VertxTestBase
import io.vertx.ext.unit.TestContext
import org.junit.Test


/**
 * @author zxj5470
 * @date 2018/7/8
 */
class FileSystemCoroutineVerticleTest : VertxTestBase() {
  override var currentPort = 8083

  init {
  	setVerticle<FileSystemCoroutineVerticle>()
  }

  @Test
  override fun testApplication(context: TestContext) {
    val async = context.async()
    val allContent = StringBuilder()
    vertx.createHttpClient().getNow(currentPort, "localhost", "/") { response ->

      response.handler { body ->
        val ret = body.bytes.toKString()
        allContent.append(ret)
      }

      response.endHandler {
        println(allContent)
        context.assertTrue(allContent.contains("ConfigLoader"))
        async.complete()
      }
    }
  }
}