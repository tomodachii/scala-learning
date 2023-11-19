package ranking

def inc(x: Int): Int = x + 1

def score(word: String): Int = word.replaceAll("a", "").length()

type RankedWords = (wordScore: String => Int, words: List[String]) => List[String]
def rankedWords: RankedWords = (wordScore, words) => {
  def negativeScore(word: String): Int = -wordScore(word)
  words.sortBy(negativeScore)
}

type Len = String => Int
def len: Len = s => s.length()

type NumberOfS = String => Int
def numberOfS: NumberOfS = s => s.length() - s.replaceAll("s", "").length()

type Negative = Int => Int
def negative: Negative = i => -i

var negativeNumberOfS = negative compose numberOfS

def rankedWords2: RankedWords = (wordScore, words) => {
  words.sortBy(wordScore)
}