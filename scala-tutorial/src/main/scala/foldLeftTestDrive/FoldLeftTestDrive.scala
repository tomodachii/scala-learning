package foldLeftTestDrive
import ranking.*


type SumListInt = (numbers: List[Int]) => Int
def sumListInt: SumListInt = (numbers) => numbers.foldLeft(0)((total, number) => total + number)

type TotalLengthOfWords = (words: List[String]) => Int
def totalLengthOfWords: TotalLengthOfWords = (words) => words.foldLeft(0)((total, word) => total + word.length)