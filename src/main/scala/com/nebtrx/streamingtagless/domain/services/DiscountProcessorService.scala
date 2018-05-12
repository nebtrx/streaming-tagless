package com.nebtrx.streamingtagless.domain.services

import cats.{Functor, Traverse}
import cats.effect.IO
import cats.syntax.functor._
import com.nebtrx.streamingtagless.domain.algebras.higherkinded.ItemRepository
import com.nebtrx.streamingtagless.domain.model.Item
import fs2.Stream

class DiscountProcessorService[F[_], G[_]: Functor](repo: ItemRepository[F, G], join: G[F[Unit]] => F[Unit]) {

  def process(discount: Double): F[Unit] = {
    val items: G[Item] = repo.findAll.map(item => item.copy(price = item.price * (1 - discount)))
    val saved: G[F[Unit]] = items.map(repo.save)
    join(saved)
  }
}

object StreamingDiscountProcessorService {

  private val join: Stream[IO, IO[Unit]] => IO[Unit] = _.evalMap(identity).compile.drain

  def apply(repo: ItemRepository[IO, Stream[IO, ?]]): DiscountProcessorService[IO, Stream[IO, ?]] =
    new DiscountProcessorService[IO, Stream[IO, ?]](repo, join)

}

object ListDiscountProcessorService{

  private val join: List[IO[Unit]] => IO[Unit] = list => Traverse[List].sequence(list).void

  def apply(repo: ItemRepository[IO, List]): DiscountProcessorService[IO, List] =
    new DiscountProcessorService[IO, List](repo, join)

}
