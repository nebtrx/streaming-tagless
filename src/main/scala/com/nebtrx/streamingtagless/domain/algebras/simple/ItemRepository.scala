package com.nebtrx.streamingtagless.domain.algebras.simple

import com.nebtrx.streamingtagless.domain.model.{Item, ItemName}

trait ItemRepository[F[_]] {
  def findAll: F[List[Item]]
  def find(name: ItemName): F[Option[Item]]
  def save(item: Item): F[Unit]
  def remove(name: ItemName): F[Unit]
}
