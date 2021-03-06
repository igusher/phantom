/*
 * Copyright 2013-2016 Websudos, Limited.
 *
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * - Redistributions of source code must retain the above copyright notice,
 * this list of conditions and the following disclaimer.
 *
 * - Redistributions in binary form must reproduce the above copyright
 * notice, this list of conditions and the following disclaimer in the
 * documentation and/or other materials provided with the distribution.
 *
 * - Explicit consent must be obtained from the copyright owner, Websudos Limited before any redistribution is made.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 */
package com.websudos.phantom.builder.query.db.crud

import com.websudos.phantom.PhantomFreeSuite
import com.websudos.phantom.builder.query.Caching
import com.websudos.phantom.tables.TestDatabase
import com.websudos.phantom.dsl._

class CreateTest extends PhantomFreeSuite {

  "The create query builder" - {

    "should generate CQL queries for custom caching properties" - {
      "serialize and create a table with Caching.None" in {

        val query = TestDatabase.timeSeriesTable
          .create.`with`(caching eqs Caching.None)

        info(query.queryString)

        val chain = for {
          drop <- TestDatabase.timeSeriesTable.alter.dropIfExists().future()
          create <- query.future()
        } yield create

        whenReady(chain) {
          res => {
            res.wasApplied() shouldEqual true
          }
        }
      }

      "serialize and create a table with Caching.KeysOnly" in {

        val query = TestDatabase.timeSeriesTable
          .create.`with`(caching eqs Caching.KeysOnly)

        info(query.queryString)

        val chain = for {
          drop <- TestDatabase.timeSeriesTable.alter.dropIfExists().future()
          create <- query.future()
        } yield create

        whenReady(chain) {
          res => {
            res.wasApplied() shouldEqual true
          }
        }
      }

      "serialize and create a table with Caching.RowsOnly" in {

        val query = TestDatabase.timeSeriesTable
          .create.`with`(caching eqs Caching.RowsOnly)

        info(query.queryString)

        val chain = for {
          drop <- TestDatabase.timeSeriesTable.alter.dropIfExists().future()
          create <- query.future()
        } yield create

        whenReady(chain) {
          res => {
            res.wasApplied() shouldEqual true
          }
        }
      }

      "serialize and create a table with Caching.All" in {
        val query = TestDatabase.timeSeriesTable
          .create.`with`(caching eqs Caching.All)

        info(query.queryString)

        val chain = for {
          drop <- TestDatabase.timeSeriesTable.alter.dropIfExists().future()
          create <- query.future()
        } yield create

        whenReady(chain) {
          res => {
            res.wasApplied() shouldEqual true
          }
        }
      }
    }

  }

}
