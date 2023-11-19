import ranking.* 

@main def hello: Unit =
  println("Hello world!")
  println(msg)
  println(score("Hello alabama"))
  
  val words = List("rust", "jsava", "somesome")
  println(rankedWords((s: String) => s.length() - s.replaceAll("s", "").length(), words))
  println(rankedWords2(negativeNumberOfS, words))
  println(words.sortBy(score))

def msg = "I was compiled by Scala 3. :)"
