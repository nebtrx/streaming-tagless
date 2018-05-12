package com.nebtrx.streamingtagless.infrastructure.interpreters.higherkinded


import cats.effect.Sync
import com.nebtrx.streamingtagless.domain.algebras.higherkinded.ItemRepository
import com.nebtrx.streamingtagless.domain.model.{Item, ItemName}
import doobie.implicits._
import doobie.util.transactor.Transactor
import fs2.Stream

// Doobie implementation (not fully implemented, what matters here are the types).
class StreamingPostgresSQLItemRepository[F[_]](xa: Transactor[F])
                                              (implicit F: Sync[F]) extends ItemRepository[F, Stream[F, ?]] {

  override def findAll: Stream[F, Item] = sql"select name, price from items"
    .query[Item]
    .stream
    .transact(xa)

  override def find(name: ItemName): F[Option[Item]] = F.pure(None)
  override def save(item: Item): F[Unit] = F.delay(println(s"Saving item: $item"))
  override def remove(name: ItemName): F[Unit] = F.delay(println(s"Removing item: $name"))
}
