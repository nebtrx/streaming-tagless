package com.nebtrx.streamingtagless.domain.model

case class ItemName(value: String) extends AnyVal

case class Item(name: ItemName, price: BigDecimal)
