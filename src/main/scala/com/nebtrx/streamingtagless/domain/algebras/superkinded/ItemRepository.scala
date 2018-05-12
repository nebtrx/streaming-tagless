package com.nebtrx.streamingtagless.domain.algebras.superkinded

import com.nebtrx.streamingtagless.domain.model.{Item, ItemName}

trait ItemRepository[F[_], S[_[_], _]] {
  def findAll: S[F, Item]
}
