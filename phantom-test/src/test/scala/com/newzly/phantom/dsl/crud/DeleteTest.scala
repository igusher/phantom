package com.newzly.phantom.dsl.crud

import java.net.InetAddress
import org.scalatest.{ Assertions, Matchers }
import org.scalatest.concurrent.{ AsyncAssertions, PatienceConfiguration }
import org.scalatest.time.SpanSugar._
import com.newzly.phantom.helper.AsyncAssertionsHelper._
import com.newzly.phantom.helper.{ BaseTest, Primitive }
import com.newzly.phantom.tables.Primitives

class DeleteTest extends BaseTest with Matchers with Assertions with AsyncAssertions {
  implicit val s: PatienceConfiguration.Timeout = timeout(10 seconds)
  val keySpace: String = "deleteTest"

  "Delete" should "work fine, when deleting the whole row" in {
    object Primitives extends Primitives {
      override def tableName = "PrimitivesDeleteTest"
    }
    val row = Primitive("myString", 2.toLong, boolean = true, BigDecimal("1.1"), 3.toDouble, 4.toFloat,
      InetAddress.getByName("127.0.0.1"), 9, new java.util.Date, com.datastax.driver.core.utils.UUIDs.timeBased(),
      BigInt(1002))
    val rcp = Primitives.create(_.pkey,
      _.long,
      _.boolean,
      _.bDecimal,
      _.double,
      _.float,
      _.inet,
      _.int,
      _.date,
      _.uuid,
      _.bi).execute() flatMap {_ => Primitives.insert
      .value(_.pkey, row.pkey)
      .value(_.long, row.long)
      .value(_.boolean, row.boolean)
      .value(_.bDecimal, row.bDecimal)
      .value(_.double, row.double)
      .value(_.float, row.float)
      .value(_.inet, row.inet)
      .value(_.int, row.int)
      .value(_.date, row.date)
      .value(_.uuid, row.uuid)
      .value(_.bi, row.bi).execute() }

    val result = rcp flatMap {
      _ => {
        for {
          inserted <- Primitives.select.fetch
          delete <- Primitives.delete.where(_.pkey eqs "myString").execute()
          deleted <- Primitives.select.where(_.pkey eqs "myString").one
        } yield (inserted.contains(row), deleted.isEmpty)
      }
    }

    result successful {
      r => {
        assert(r._1)
        assert(r._2)
        info("success")
      }
    }

  }

}
