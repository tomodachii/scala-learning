package basics

def abs(x: Double): Double = if x > 0 then x else -x

def sqrt(guess: Double, x: Double) = {
  def isGoodEnough(guess: Double): Boolean = abs(guess * guess - x) < 0.000001

  def improve(guess: Double): Double = (guess + x / guess) / 2

  def sqrtIter(guess: Double): Double = 
    if isGoodEnough(guess) then guess
    else sqrtIter(improve(guess))

  sqrtIter(guess)
}

