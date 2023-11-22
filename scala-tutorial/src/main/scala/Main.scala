import ranking.* 
import foldLeftTestDrive.*

@main def hello: Unit =  
  val words = List("russ", "aaas", "c")
  val words2 = List("yo", "abcd", "bulba")
  // println(rankedWords((s: String) => s.length() - s.replaceAll("s", "").length(), words))
  // println(rankedWords2(numberOfS, words))
  // println(words.sortBy(scoreWithBonus).reverse)
  println(wordScores(scoreWithBonus, words))
  // val wordsWithScoreHigherThan = highScoringWords(scoreWithBonus)(words)
  // val words2WithScoreHigherThan = highScoringWords(scoreWithBonus)(words2)
  // println(wordsWithScoreHigherThan(2))
  // println(words2WithScoreHigherThan(3))
  println(cumulativeScore(scoreWithBonus, words))
  println(sumListInt(List(5, 1, 2, 4, 100)))
  println(totalLengthOfWords(List("scala", "rust", "ada")))
def msg = "I was compiled by Scala 3. :)"
