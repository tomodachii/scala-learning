import ranking.* 
import foldLeftTestDrive.*
import productType.*
import productType.scalalang
import productType.javalang

@main def hello: Unit =  
  // val words = List("russ", "aaas", "c")
  // val words2 = List("yo", "abcd", "bulba")
  // println(rankedWords((s: String) => s.length() - s.replaceAll("s", "").length(), words))
  // println(rankedWords2(numberOfS, words))
  // println(words.sortBy(scoreWithBonus).reverse)
  // println(wordScores(scoreWithBonus, words))
  // val wordsWithScoreHigherThan = highScoringWords(scoreWithBonus)(words)
  // val words2WithScoreHigherThan = highScoringWords(scoreWithBonus)(words2)
  // println(wordsWithScoreHigherThan(2))
  // println(words2WithScoreHigherThan(3))
  // println(cumulativeScore(scoreWithBonus, words))
  // println(sumListInt(List(5, 1, 2, 4, 100)))
  // println(totalLengthOfWords(List("scala", "rust", "ada")))
  // val languages = List(javalang, scalalang)
  // println(languages.map(lang => lang.name))
  // println(languages.map(_.name))
  // println(languages.filter(_.year > 2000))

  // val friends = List("Alice", "Bob", "Charlie")
  // println(recommendations(friends))
  
  val books = List(
    Book("FP in Scala", List("Chiusano", "Bjarnason")), 
    Book("The Hobbit", List("Tolkien"))
  )

  println(recommendationFeed(books))
def msg = "I was compiled by Scala 3. :)"