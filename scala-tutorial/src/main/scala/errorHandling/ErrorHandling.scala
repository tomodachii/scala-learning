package errorHandling

case class TvShow(name: String, start: Int, end: Int)

type SortShows = (shows: List[TvShow]) => List[TvShow]
def sortShows: SortShows = (shows) => shows.sortBy(show => show.end - show.start).reverse

val shows = List(
  TvShow("Breaking Bad", 2008, 2013), 
  TvShow("The Wire", 2002, 2008),
  TvShow("Mad Men", 2007, 2015)
)

val sortedShowsDesc = sortShows(shows)

val rawShows: List[String] = List(
  "Breaking Bad (2008-2013)",
  "The Wire (2002-2008)",
  "Mad Men (2007-2015)"
)

type ParseShows = (rawShows: List[String]) => List[Option[TvShow]]
val parseShows: ParseShows = (rawShows) => rawShows.map(parseshow)

// type ParseShow = (rawShow: String) => TvShow
// val parseshow: ParseShow = (rawShow) => {
//   val bracketOpen = rawShow.indexOf('(')
//   val bracketClose = rawShow.indexOf(')')
//   val dash = rawShow.indexOf('-')

//   val name = rawShow.substring(0, bracketOpen).trim
//   val yearStart = Integer.parseInt(rawShow.substring(bracketOpen + 1, dash).trim)
//   val yearEnd = Integer.parseInt(rawShow.substring(dash + 1, bracketClose).strip)

//   TvShow(name, yearStart, yearEnd)
// }

type ExtractName = (rawShow: String) => Option[String]
val extractName: ExtractName = (rawShow) => {
  val bracketOpen = rawShow.indexOf('(')
  
  for {
    name <- if (bracketOpen != -1) Some(rawShow.substring(0, bracketOpen)) else None
  } yield name.trim
}

type ExtractYearStart = (rawShow: String) => Option[Int]
val extractYearStart: ExtractYearStart = (rawShow) => {
  val bracketOpen = rawShow.indexOf('(')
  val dash = rawShow.indexOf('-')
  // val yearStrOpt = if (bracketOpen != -1 && dash > bracketOpen + 1) {
  //   Some(rawShow.substring(bracketOpen + 1, dash))
  // } else {
  //   None
  // }
  // yearStrOpt.map(yearStr => yearStr.toInt)
  // yearStrOpt.map(yearStr => yearStr.toIntOption).flatten

  for {
    yearStr <- if (bracketOpen != -1 && dash > bracketOpen + 1) {
      Some(rawShow.substring(bracketOpen + 1, dash))
    } else {
      None
    }
    year <- yearStr.toIntOption
  } yield year
}

type ExtractYearEnd = (rawshow: String) => Option[Int]
val extractYearEnd: ExtractYearStart = (rawShow) => {
  val dash = rawShow.indexOf('-')
  val bracketClose = rawShow.indexOf(')')

  for {
    yearStr <- if (bracketClose != -1 && dash + 1 < bracketClose) {
      Some(rawShow.substring(dash + 1, bracketClose))
    } else {
      None
    }
    year <- yearStr.toIntOption
  } yield year
}

type ParseShow = (rawShow: String) => Option[TvShow]
val parseshow: ParseShow = (rawShow) => for {
  name <- extractName(rawShow)
  yearStart <- extractYearStart(rawShow)
  yearEnd <- extractYearEnd(rawShow)
} yield TvShow(name, yearStart, yearEnd)


val parsedShows = parseShows(rawShows)