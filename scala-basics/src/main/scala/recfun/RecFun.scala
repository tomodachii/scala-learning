package recfun

object RecFun extends RecFunInterface:

  def main(args: Array[String]): Unit =
    println("Pascal's Triangle")
    for row <- 0 to 10 do
      for col <- 0 to row do
        print(s"${pascal(col, row)} ")
      println()

  /**
   * Exercise 1
   */
  def pascal(c: Int, r: Int): Int = {
    if c == r then 1
    else if c == 0 then 1
    else pascal(c - 1, r - 1) + pascal(c, r - 1)
  }

  /**
   * Exercise 2
   */
  def balance(chars: List[Char]): Boolean = {
    // checks if string contains matching amount of opening and closing parentheses by calling isBalanced() on the string without first element
    // Expectancy of parentheses in the string is kept in a kind of balance indicator open - positives indicate amount of needed ')' and negatives amount of needed '('. Initial balance is 0
    // When recursion reaches end of string it checks if balance is ok (open == 0), e.g. there was matching amount of parentheses seen.
    // There is also a check (open > 0) to ensure that ')' wasn't encountered before there was '(' it could close.
    def isBalanced(chars: List[Char], open: Int): Boolean = {
      if chars.isEmpty then open == 0
      else if chars.head == '(' then isBalanced(chars.tail, open + 1)
      else if chars.head == ')' then open > 0 && isBalanced(chars.tail, open - 1)
      else isBalanced(chars.tail, open)
    }
    isBalanced(chars, 0)
  }

  /**
   * Exercise 3
   */
  def countChange(money: Int, coins: List[Int]): Int = {
    if(money == 0)
      1
    else if(money > 0 && !coins.isEmpty)
      countChange(money - coins.head, coins) + countChange(money, coins.tail)
    else
      0
  }
