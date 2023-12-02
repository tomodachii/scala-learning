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

val rawShows = List("Breaking Bad (2008-2013)", "The Wire (2008)", "Mad Men (2007-2015)")

// best-effort error handling
// type ParseShows = (rawShows: List[String]) => List[TvShow]
// val parseShows: ParseShows = (rawShows) => rawShows.flatMap(parseshow).toList

// all-or-nothing error handling
type ParseShows = (rawShows: List[String]) => Option[List[TvShow]]
val parseShows: ParseShows = (rawShows) => {
  val initialResult: Option[List[TvShow]] = Some(List.empty)
  
  rawShows
    .map(parseshow)
    .foldLeft(initialResult)(addOrResign)
}

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
  yearStart <- extractYearStart(rawShow).orElse(extractSingleYear(rawShow))
  yearEnd <- extractYearEnd(rawShow).orElse(extractSingleYear(rawShow))
} yield TvShow(name, yearStart, yearEnd)

def extractSingleYear(rawShow: String): Option[Int] = {
  val dash = rawShow.indexOf('-')
  val bracketOpen = rawShow.indexOf('(')
  val bracketClose = rawShow.indexOf(')')

  for {
    yearStr <- if (dash == -1 && bracketOpen != -1 && bracketClose > bracketOpen + 1) {
      Some(rawShow.substring(bracketOpen + 1, bracketClose))
    } else {
      None
    }
    year <- yearStr.toIntOption
  } yield year
}

def extractSingleYearOrEndYear(rawShow: String): Option[Int] = {
  extractSingleYear(rawShow).orElse(extractYearEnd(rawShow))
}

def extractAnyYear(rawShow: String): Option[Int] = {
  extractYearStart(rawShow)
    .orElse(extractYearEnd(rawShow))
    .orElse(extractSingleYear(rawShow))
}

def extractSingleYearIfNameExists(rawShow: String): Option[Int] = {
  extractName(rawShow).flatMap(name => extractSingleYear(rawShow))
}

def extractAnyYearIfNameExists(rawShow: String): Option[Int] = {
  extractName(rawShow).flatMap(name => extractAnyYear(rawShow))
}

def addOrResign(parsedShows: Option[List[TvShow]], newParsedShow: Option[TvShow]): Option[List[TvShow]] = {
  for {
    shows <- parsedShows
    newShow <- newParsedShow
  } yield shows.appended(newShow)
}

val parsedShows = parseShows(rawShows)