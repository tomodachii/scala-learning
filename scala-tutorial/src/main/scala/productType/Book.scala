package productType

case class Book(title: String, authors: List[String]) 

case class Movie(title: String)

def bookAdaptations(author: String): List[Movie] = {
  if (author == "Tolkien") 
    List(Movie("An Unexpected Journey"), Movie("The Desolation of Smaug"))
  else 
    List.empty
}

def recommendationFeed(books: List[Book]) = {
  books.flatMap(book =>
    book.authors.flatMap(author =>
      bookAdaptations(author).map(
        movie => s"You may like ${movie.title}, " + s"because you liked $author's ${book.title}"
      )
    )
  )
}

def recommendedBooks(friend: String): List[Book] = {
  val scala = List(
    Book("FP in Scala", List("Chiusano", "Bjarnason")), 
    Book("Get Programming with Scala", List("Sfregola")))

  val fiction = List(
    Book("Harry Potter", List("Rowling")), 
    Book("The Lord of the Rings", List("Tolkien")))
    
  if(friend == "Alice") scala
  else if(friend == "Bob") fiction 
  else List.empty
} 

def recommendations(friends: List[String]): List[String] = {
  friends.flatMap(recommendedBooks).flatMap(_.authors)
}