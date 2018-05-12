package com.nebtrx.streamingtagless.infrastructure.interpreters.higherkinded

import cats.Id
import com.nebtrx.streamingtagless.domain.algebras.higherkinded.ItemRepository
import com.nebtrx.streamingtagless.domain.model.{Item, ItemName}

import scala.collection.mutable.{Map => MutableMap}

object StreamingMemoryRepository extends ItemRepository[Id, List] {
  private val mem = MutableMap.empty[String, Item]

  override def findAll: List[Item] = mem.headOption.map(_._2).toList
  override def find(name: ItemName): Id[Option[Item]] = mem.get(name.value)
  override def save(item: Item): Id[Unit] = mem.update(item.name.value, item)
  override def remove(name: ItemName): Id[Unit] = {
    mem.remove(name.value)
    ()
  }
}
