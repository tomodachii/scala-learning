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

  // the combination of yearsActiveStart and yearsActiveEnd to indicate the years that an artist active or retired
  // -> coupled inside a product type
  case class PeriodInYears(start: Int, end: Option[Int])

  // Yes, it's enum in Scala
  enum MusicGenre {
    case HeavyMetal
    case Pop
    case HardRock
  }

  // sum type
  enum YearsActive {
    case StillActive(since: Int) // product type
    case ActiveBetween(start: Int, end: Int)
  }

  case class Artist(
    name: String,
    genre: MusicGenre,
    origin: Location,
    yearsActive: YearsActive
  )

  case class Song (
    artist: Artist,
    name: String
  )

  // case class User (
  //   name: String
  // )
  opaque type User = String
  object User {
    def apply(value: String): User = value
    extension(a: User) def name: String = a
  }

  enum PlaylistKind {
    case CuratedByUser(user: User)
    case BasedOnArtist(playlistArtist: Artist)
    case BasedOnGenres(genres: Set[MusicGenre])
  }

  case class Playlist (
    name: String,
    kind: PlaylistKind,
    songs: List[Song]
  )
}

import model._
import MusicGenre._
import YearsActive._
import PlaylistKind._

def searchArtists(
    artists: List[Artist],
    genres: List[MusicGenre],
    locations: List[String],
    searchByActiveYears: Boolean,
    activeAfter: Int,
    activeBefore: Int
): List[Artist] = {
  artists.filter(artist =>
    (genres.isEmpty || genres.contains(artist.genre)) &&
    (locations.isEmpty || locations.contains(artist.origin.name)) &&
    (!searchByActiveYears || wasArtistActive(artist, activeAfter, activeBefore))
  )
}

def wasArtistActive (
  artist: Artist, yearStart: Int, yearEnd: Int
): Boolean = {
  artist.yearsActive match {
    case StillActive(since) => since <= yearEnd
    case ActiveBetween(start, end) => start <= yearEnd && end >= yearStart
  }
}

val artists = List(
  Artist("Metallica", HeavyMetal, Location("U.S."), StillActive(since = 1981)),
  Artist("Led Zeppelin", HardRock, Location("England"), ActiveBetween(1986, 1980)),
  Artist("Bee Gees", Pop, Location("England"), ActiveBetween(1959, 2003))
)

val us: Location = Location("U.S.")
// This below won't compile
// val wontCompile: Location = "U.S."

val fooFighters = Artist("Foo Fighters", HardRock, Location("Moon"), ActiveBetween(1969, 2023))
val magneticFields = Artist("Magnetic Fields", Pop, Location("The cactus where your heart should be"), StillActive(since = 1999))
val daftPunk = Artist("Daft Punk", Pop, Location("Space"), StillActive(since = 1990))

val playlist2 = Playlist("Deep Focus", BasedOnGenres(Set(Pop, HardRock)),
List(Song(daftPunk, "One More Time"),Song(daftPunk, "Hey Boy Hey Girl")))


val thisIsFoofighters = Playlist("This is Foo Fighters", BasedOnArtist(fooFighters), List(Song(fooFighters, "Breakout"), Song(fooFighters, "Learn to Fly")))

val myPlaylist = Playlist("Sex", CuratedByUser(User("Jaccuzi")), List(Song(magneticFields, "Absolutely Cuckoo")))
// val popSongs = Playlist("Pop69", BasedOnGenres(Set(Pop, HardRock)), List(Song(magneticFields, "Absolutely Cuckoo")))

val listOfPlaylists: List[Playlist] = List(thisIsFoofighters, playlist2, myPlaylist)

def gatherSongs(playlists: List[Playlist], artist: Artist, genre: MusicGenre): List[Song] = {
  playlists.foldLeft(List.empty[Song])((songs, playlist) => 
    val matchingSongs = playlist.kind match {
      case CuratedByUser(user) => playlist.songs.filter(_.artist == artist)
      case BasedOnGenres(genres) => if (genres.contains(genre)) {
        playlist.songs
      } else {
        List.empty
      }
      case BasedOnArtist(playlistArtist) => if (playlistArtist == artist) playlist.songs else List.empty
    }
    songs.appendedAll(matchingSongs)
  )
}

val gatheredSongs = gatherSongs(listOfPlaylists, artist = magneticFields, genre = HeavyMetal)