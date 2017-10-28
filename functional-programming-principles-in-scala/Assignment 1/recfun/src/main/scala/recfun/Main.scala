package recfun

import scala.annotation.tailrec

object Main {
  def main(args: Array[String]) {
    println("Pascal's Triangle")
    for (row <- 0 to 10) {
      for (col <- 0 to row)
        print(pascal(col, row) + " ")
      println()
    }
  }

  /**
   * Exercise 1
   */

    def pascal(c: Int, r: Int): Int = {

      if (c==0 || c == r) 1 //Sides of Pascal's triangle including head
      else pascal(c-1, r-1) + pascal(c, r-1) //Recursive implementation

    }
  
  /**
   * Exercise 2
   */
    def balance(chars: List[Char]): Boolean = {
      def balancer(chars: List[Char], counter: Int): Boolean = {
        //counter keeps track of brackets
        // '(': +1, ')': -1, others: 0
        if (counter < 0) false //base case for having a ')' without corresponding '(' at any time
        else if (chars.isEmpty && counter != 0) false //base case for not balancing
        else if (chars.isEmpty && counter == 0) true //base case for balancing
        else if (chars.head == '(') balancer(chars.tail, counter+1) //if encountered a '('
        else if (chars.head == ')') balancer(chars.tail, counter-1) //if encountered a ')'
        else balancer(chars.tail, counter) //all other characters
      }

      balancer(chars, 0)
    }
  
  /**
   * Exercise 3
   */
    def countChange(money: Int, coins: List[Int]): Int = {

      if (money == 0) 1 //Success
      else if (money < 0) 0 //Can't have negative money
      else if (coins.isEmpty) 0 //No coins left
      else //Ways to get change = ways that involve 1st kind of coin + ways that don't
        countChange(money - coins.head, coins) + //Ways that use the first kind of coin
        countChange(money, coins.tail) //Ways that don't use the first kind of coin
    }
  }
