package com.nebtrx.streamingtagless.domain.algebras.higherkinded

import com.nebtrx.streamingtagless.domain.model.{Item, ItemName}

trait ItemRepository[F[_], G[_]] {
  def findAll: G[Item]
  def find(name: ItemName): F[Option[Item]]
  def save(item: Item): F[Unit]
  def remove(name: ItemName): F[Unit]
}
