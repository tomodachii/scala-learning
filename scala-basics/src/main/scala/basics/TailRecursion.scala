package basics
import scala.annotation.tailrec

// This is a Tail recursion
// A func calls itself as its LAST ACTION
@tailrec
def gcd(a: Int, b: Int): Int = if b == 0 then a else gcd(b, a % b)

// @tailrec
def factorial(n: Int): Int = if n == 0 then 1 else n * factorial(n - 1)