// import ranking.* 
// import foldLeftTestDrive.*
// import productType.*
// import flatMapVsForComprehensions.*
import forComprehension.*
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
  
  // val books = List(
  //   Book("FP in Scala", List("Chiusano", "Bjarnason")), 
  //   Book("The Hobbit", List("Tolkien"))
  // )

  // println(recommendationFeed(books))

  // case class Point(x: Int, y: Int)
  // val test = 
  //   List(1, 2).flatMap(x =>
  //     List(1, 7).map(y =>
  //       Point(x, y)
  //     )
  //   )
  // println(test)

  // val bookRecommedations = for {
  //   book <- books
  //   author <- book.authors
  //   movie <- bookAdaptations(author)
  // } yield s"You may like ${movie.title}, " + s"because you liked $author's ${book.title}"
  // println(bookRecommedations)
  
  // flatMap vs For comprehension
  // println(flatMapListOfPoints)
  // println(forComprehensionListOfPoints)
  // println(forComprehensionListOf3dPoints)
  // println(flatMapListOf3dPoints)

  // for comprehension
  // println(insideCircle)
  // println(insideCircle2)

  // println(filterUsingFilterFunctionOnList)
  // println(filterUsingGuardExpression)
  // println(filterUsingFunctionPassedToFlatMapFunction)
  
  // println(listAndSetForComprehensionWListReturnedType)
  // println(listAndSetForComprehensionWSetReturnedType)
  // println(parse("Apollo Program", 1961, 1972))
  // println(parse("", 1961, 1972))
  // println(parseLongEvent("Apollo Program", 1961, 1972, 10))
  println()
def msg = "I was compiled by Scala 3. :)"
