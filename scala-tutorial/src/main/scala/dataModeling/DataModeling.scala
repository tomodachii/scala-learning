package dataModeling

object model {
  opaque type Location = String
  object Location {
    def apply(value: String): Location = value
    extension (a: Location) def name: String = a
  }

  opaque type Genre = String
  object Genre {
    def apply(value: String): Genre = value
    extension (a: Genre) def name: String = a
  }

  // opaque type YearsActiveStart = Int
  // object YearsActiveStart {
  //   def apply(value: Int): YearsActiveStart = value
  //   extension (a: YearsActiveStart) def value: Int = a
  // }

  // opaque type YearsActiveEnd = Int
  // object YearsActiveEnd {
  //   def apply(value: Int): YearsActiveEnd = value
  //   extension (a: YearsActiveEnd) def value: Int = a
  // }

  // the combination of yearsActiveStart and yearsActiveEnd to indicate the years that an artist active or retired
  // -> coupled inside a product type
  // case class PeriodInYears(start: Int, end: Option[Int])

  // Yes, it's enum in Scala
  enum MusicGenre {
    case HeavyMetal
    case Pop
    case HardRock
  }

  case class PeriodInYears(start: Int, end: Int)

  // sum type
  enum YearsActive {
    case StillActive(
        since: Int,
        previousPeriods: List[PeriodInYears]
    ) // product type
    case ActivedInPast(periods: List[PeriodInYears])
  }

  case class Artist(
      name: String,
      genre: MusicGenre,
      origin: Location,
      yearsActive: YearsActive
  )

  case class Song(
      artist: Artist,
      name: String
  )

  // case class User (
  //   name: String
  // )
  opaque type User = String
  object User {
    def apply(value: String): User = value
    extension (a: User) def name: String = a
  }

  enum PlaylistKind {
    case CuratedByUser(user: User)
    case BasedOnArtist(playlistArtist: Artist)
    case BasedOnGenres(genres: Set[MusicGenre])
  }

  case class Playlist(
      name: String,
      kind: PlaylistKind,
      songs: List[Song]
  )
}

import model._
import MusicGenre._
import YearsActive._
import PlaylistKind._

enum SearchCondition {
  case SearchByGenre(genres: List[MusicGenre])
  case SearchByOrigin(locations: List[Location])
  case SearchByActiveYears(period: PeriodInYears)
  case SearchByNumberOfActiveYears(howLong: Int, until: Int)
}

import SearchCondition._

// def searchArtists(
//     artists: List[Artist],
//     genres: List[MusicGenre],
//     locations: List[String],
//     searchByActiveYears: Boolean,
//     activeAfter: Int,
//     activeBefore: Int
// ): List[Artist] = {
//   artists.filter(artist =>
//     (genres.isEmpty || genres.contains(artist.genre)) &&
//     (locations.isEmpty || locations.contains(artist.origin.name)) &&
//     (!searchByActiveYears || wasArtistActive(artist, activeAfter, activeBefore))
//   )
// }

def searchArtists(
    artists: List[Artist],
    requiredConditions: List[SearchCondition]
): List[Artist] =
  artists.filter(artist => {
    // wrong approach since for comprehension return a list of boolean
    // for {
    // 	searchCondition <- requiredConditions
    // } yield searchCondition match {
    // 	case SearchByGenre(genres) => genres.contains(artist.genre)
    // 	case SearchByOrigin(locations) => locations.contains(artist.origin)
    // 	case  SearchByActiveYears(start, end) => wasArtistActive(artist, start, end)
    // }

    // forall is obviously for the logic all the conditions must sastisfied in order to be considered true
    // if we want to search by at least one of the condition (the others can failed) -> exists
    requiredConditions.forall(_ match {
      case SearchByGenre(genres)     => genres.contains(artist.genre)
      case SearchByOrigin(locations) => locations.contains(artist.origin)
      case SearchByActiveYears(period) =>
        wasArtistActive(artist, period)
      case SearchByNumberOfActiveYears(howLong, until) => howLong <= activeLength(artist, until)
    })
  })

def periodOverlapWithPeriods(checkedPeriod: PeriodInYears, periods: List[PeriodInYears]): Boolean = {
  periods.exists(p =>
    p.start <= checkedPeriod.end &&
    p.end >= checkedPeriod.start  
  )
}

def wasArtistActive(
    artist: Artist,
    searchedPeriodInYears: PeriodInYears
): Boolean = {
  artist.yearsActive match {
    case StillActive(since, periods) => since <= searchedPeriodInYears.end || periodOverlapWithPeriods(searchedPeriodInYears, periods)
    case ActivedInPast(periods) => periodOverlapWithPeriods(searchedPeriodInYears, periods)
  }
}

def activeLength(
    artist: Artist,
    currentYear: Int,
): Int = {
    val periods = artist.yearsActive match {
      case ActivedInPast(periods) => periods
      case StillActive(since, previousPeriods) => previousPeriods.appended(PeriodInYears(since, currentYear))
    }
    periods.map(p => p.end - p.start).foldLeft(0)((x, y) => x + y)
}

val artists = List(
  Artist(
    "Metallica",
    HeavyMetal,
    Location("U.S."),
    StillActive(since = 1981, List.empty)
  ),
  Artist(
    "Led Zeppelin",
    HardRock,
    Location("England"),
    ActivedInPast(List(PeriodInYears(1900, 1950), PeriodInYears(1960, 2010)))
  ),
  Artist(
    "Bee Gees",
    Pop,
    Location("England"),
    ActivedInPast(List(PeriodInYears(1999, 2023)))
  )
)

val us: Location = Location("U.S.")
// This below won't compile
// val wontCompile: Location = "U.S."

val fooFighters = Artist(
  "Foo Fighters",
  HardRock,
  Location("Moon"),
  ActivedInPast(List(PeriodInYears(1900, 2000)))
)
val magneticFields = Artist(
  "Magnetic Fields",
  Pop,
  Location("The cactus where your heart should be"),
  StillActive(since = 1999, List.empty)
)
val daftPunk = Artist(
  "Daft Punk",
  Pop,
  Location("Space"),
  StillActive(since = 1990, List.empty)
)

val playlist2 = Playlist(
  "Deep Focus",
  BasedOnGenres(Set(Pop, HardRock)),
  List(Song(daftPunk, "One More Time"), Song(daftPunk, "Hey Boy Hey Girl"))
)

val thisIsFoofighters = Playlist(
  "This is Foo Fighters",
  BasedOnArtist(fooFighters),
  List(Song(fooFighters, "Breakout"), Song(fooFighters, "Learn to Fly"))
)

val myPlaylist = Playlist(
  "Sex",
  CuratedByUser(User("Jaccuzi")),
  List(Song(magneticFields, "Absolutely Cuckoo"))
)
// val popSongs = Playlist("Pop69", BasedOnGenres(Set(Pop, HardRock)), List(Song(magneticFields, "Absolutely Cuckoo")))

val listOfPlaylists: List[Playlist] =
  List(thisIsFoofighters, playlist2, myPlaylist)

def gatherSongs(
    playlists: List[Playlist],
    artist: Artist,
    genre: MusicGenre
): List[Song] = {
  playlists.foldLeft(List.empty[Song])((songs, playlist) =>
    val matchingSongs = playlist.kind match {
      case CuratedByUser(user) => playlist.songs.filter(_.artist == artist)
      case BasedOnGenres(genres) =>
        if (genres.contains(genre)) {
          playlist.songs
        } else {
          List.empty
        }
      case BasedOnArtist(playlistArtist) =>
        if (playlistArtist == artist) playlist.songs else List.empty
    }
    songs.appendedAll(matchingSongs)
  )
}

val gatheredSongs =
  gatherSongs(listOfPlaylists, artist = magneticFields, genre = HeavyMetal)

val oneConditionSearch =
  searchArtists(artists, List(SearchByActiveYears(PeriodInYears(1950, 1980))))
val twoConditionsSearch = searchArtists(
  artists,
  List(SearchByGenre(List(Pop)), SearchByOrigin(List(Location("England"))))
)
val noConditionSearch = searchArtists(artists, List.empty)

val searchByNumberOfactiveYears = searchArtists(artists, List(SearchByNumberOfActiveYears(100, 2023)))

val fooActiveLength = activeLength(fooFighters, 2023)