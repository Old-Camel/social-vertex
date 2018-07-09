package cn.net.polyglot.testframework

import io.vertx.core.Verticle
import io.vertx.core.Vertx
import io.vertx.ext.unit.TestContext
import io.vertx.ext.unit.junit.VertxUnitRunner
import org.junit.After
import org.junit.Before
import org.junit.runner.RunWith

/**
 * New a TestClass extends `VertxTestBase`, then override the `currentPort`
 *        and initialize the `vertx` in `init` block of the class. For example:
 * ```Kotlin
 * override var currentPort = 8088
 * init{
 *   setVerticle<SecondVerticle>()
 * }
 *
 * @Test
 * override fun testApplication(context: TestContext) {
 *   val async = context.async()
 *   // ...... test content
 *   async.complete()
 * }
 * ```
 *
 * @see [cn.net.polyglot.SecondVerticleTest2] or [cn.net.polyglot.CoroutineVerticleTest2]
 * @property verticle Class<*>
 * @property vertx Vertx
 * @property currentPort Int
 * @author zxj5470
 * @date 2018/7/9
 */
@RunWith(VertxUnitRunner::class)
abstract class VertxTestBase {
  lateinit var verticle: Class<out Verticle>
  lateinit var vertx: Vertx
  abstract var currentPort: Int

  inline fun <reified T : Verticle> setVerticle() {
    verticle = T::class.java
  }

  @Before
  fun setUp(context: TestContext) {
    vertx = Vertx.vertx()
    val currentOptions = configPort(currentPort)
    vertx.deployVerticle(verticle.name, currentOptions, context.asyncAssertSuccess())
  }

  @After
  fun tearDown(context: TestContext) {
    vertx.close(context.asyncAssertSuccess())
  }

  abstract fun testApplication(context: TestContext)
}