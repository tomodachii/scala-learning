package ranking

def inc(x: Int): Int = x + 1

type Score = String => Int
def score: Score = word => word.replaceAll("a", "").length

type Bonus = String => Int
def bonus: Bonus = word => if (word.contains("c")) 5 else 0

def createScoreWithBonus(score: Score, bonus: Bonus) = (word: String) => score(word) + bonus(word)
val scoreWithBonus = createScoreWithBonus(score = score, bonus = bonus)

type RankedWords = (wordScore: String => Int, words: List[String]) => List[String]
def rankedWords: RankedWords = (wordScore, words) => {
  def negativeScore(word: String): Int = -wordScore(word)
  words.sortBy(negativeScore)
}

type WordScores = (wordScore: String => Int, words: List[String]) => List[Int]
def wordScores: WordScores = (wordsScore, words) => words.map(wordsScore)

type Len = String => Int
def len: Len = s => s.length

type NumberOfS = String => Int
def numberOfS: NumberOfS = s => s.length() - s.replaceAll("s", "").length

type Negative = Int => Int
def negative: Negative = i => -i

val negativeNumberOfS = negative compose numberOfS

def rankedWords2: RankedWords = (wordScore, words) => {
  words.sortBy(wordScore).reverse
}

type HighScoringWords = (wordScore: String => Int) => (words: List[String]) => (higherThan: Int) => List[String]
def highScoringWords: HighScoringWords = (wordScore) => (words) => (higherThan) => words.filter(word => wordScore(word) > higherThan)

def cumulativeScore(wordScore: String => Int, words: List[String]): Int = {
  words.foldLeft(0)((total, word) => total + wordScore(word))
}