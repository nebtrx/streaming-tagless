package com.nebtrx.streamingtagless.infrastructure.interpreters.simple

import cats.effect.Sync
import com.nebtrx.streamingtagless.domain.algebras.simple.ItemRepository
import com.nebtrx.streamingtagless.domain.model.{Item, ItemName}
import doobie.implicits._
import doobie.util.transactor.Transactor

// Doobie implementation (not fully implemented, what matters here are the types).
class PostgresSQLItemRepository[F[_]](xa: Transactor[F])
                                    (implicit F: Sync[F]) extends ItemRepository[F] {

  override def findAll: F[List[Item]] = sql"select name, price from items"
    .query[Item]
    .to[List]
    .transact(xa)

  override def find(name: ItemName): F[Option[Item]] = F.pure(None)
  override def save(item: Item): F[Unit] = F.unit
  override def remove(name: ItemName): F[Unit] = F.unit
}
