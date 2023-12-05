package dataModeling

object model {
  opaque type Location = String
  object Location {
    def apply(value: String): Location = value
    extension(a: Location) def name: String = a
  }

  opaque type Genre = String
  object Genre {
    def apply(value: String): Genre = value
    extension (a: Genre) def name: String = a
  }

  opaque type YearsActiveStart = Int
  object YearsActiveStart {
    def apply(value: Int): YearsActiveStart = value
    extension (a: YearsActiveStart) def value: Int = a
  }

  opaque type YearsActiveEnd = Int
  object YearsActiveEnd {
    def apply(value: Int): YearsActiveEnd = value
    extension (a: YearsActiveEnd) def value: Int = a
  }

  case class Artist(
    name: String,
    genre: Genre,
    origin: Location,
    yearsActiveStart: YearsActiveStart,
    yearsActiveEnd: Option[Int]
  )
}

import model._

def searchArtists(
    artists: List[Artist],
    genres: List[String],
    locations: List[String],
    searchByActiveYears: Boolean,
    activeAfter: Int,
    activeBefore: Int
): List[Artist] = {
  artists.filter(artist =>
    (genres.isEmpty || genres.contains(artist.genre.name)) &&
    (locations.isEmpty || locations.contains(artist.origin.name)) &&
    (!searchByActiveYears || (
      (artist.yearsActiveEnd.forall(_ >= activeAfter)) &&
      (artist.yearsActiveStart.value <= activeBefore)
    ))
  )
}

val artists = List(
  Artist("Metallica", Genre("Heavy Metal"), Location("U.S."), YearsActiveStart(1981), None),
  Artist("Led Zeppelin", Genre("Hard Rock"), Location("England"), YearsActiveStart(1968), Some(1980)),
  Artist("Bee Gees", Genre("Pop"), Location("England"), YearsActiveStart(1958), Some(2003))
)

val us: Location = Location("U.S.")
// This below won't compile
// val wontCompile: Location = "U.S."
