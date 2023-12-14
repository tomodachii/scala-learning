package semigroupMonoid

import cats.Monoid
import cats.instances.int._ // Import int instances for Monoid
import cats.instances.string._ // Import string instances for Monoid
import cats.instances.option._
import cats.syntax.semigroup._



val intResult = Monoid[Int].combine(1, 2) // Result is 3
val stringResult = Monoid[String].combine("Hello, ", "world!") // Result is "Hello, world!"

val opt1: Option[Int] = Some(10)
val opt2: Option[Int] = Some(20)
val combinedOpts = opt1 |+| opt2 // Output: Some(30)

case class Money(amount: Int)

implicit val moneyMonoid: Monoid[Money] = new Monoid[Money] {
  def empty: Money = Money(0)
  def combine(x: Money, y: Money): Money = Money(x.amount + y.amount)
}

val money1 = Money(100)
val money2 = Money(200)
val combinedMoney = money1 |+| money2 // Output: Money(300)