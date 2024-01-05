import basics.*

@main def hello: Unit =
  // basics
  // call by value and will be evaluated 
  // println(and(false, loop))
  // call by name and the loop will not be evaluated!
  println(andCBN(false, loop))
  print(sqrt(guess=1, x=2))

def msg = "I was compiled by Scala 3. :)"
