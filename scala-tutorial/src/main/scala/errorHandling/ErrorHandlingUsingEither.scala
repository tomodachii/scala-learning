package errorHandling

import errorHandling.*

// best-effort error handling
// type ParseShows = (rawShows: List[String]) => List[TvShow]
// val parseShows: ParseShows = (rawShows) => rawShows.flatMap(parseshow).toList

// all-or-nothing error handling
type ParseShowsUsingEither = (rawShows: List[String]) => Either[String, List[TvShow]]
val parseShowsUsingEither: ParseShowsUsingEither = (rawShows) => {
  val initialResult: Either[String, List[TvShow]] = Right(List.empty)
  
  rawShows
    .map(parseShowUsingEither)
    .foldLeft(initialResult)(addOrResignUsingEither)
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

type ExtractNameUsingEither = (rawShow: String) => Either[String, String]
val extractNameUsingEither: ExtractNameUsingEither = (rawShow) => {
  val bracketOpen = rawShow.indexOf('(')
  
  for {
    name <- if (bracketOpen != -1) Right(rawShow.substring(0, bracketOpen)) else Left(s"Cannot Extract name from ${rawShow}")
  } yield name.trim
}

type ExtractYearStartUsingEither = (rawShow: String) => Either[String, Int]
val extractYearStartUsingEither: ExtractYearStartUsingEither = (rawShow) => {
  val bracketOpen = rawShow.indexOf('(')
  val dash = rawShow.indexOf('-')
  val yearStrEither = if (bracketOpen != -1 && dash > bracketOpen + 1) {
    Right(rawShow.substring(bracketOpen + 1, dash))
  } else {
    Left(s"Cannot Extract Year start from ${rawShow}")
  }

  yearStrEither.flatMap(yearStr =>
    yearStr.toIntOption.toRight(s"Can't parse $yearStr")
  )

  // for {
  //   yearStr <- if (bracketOpen != -1 && dash > bracketOpen + 1) {
  //     Right(rawShow.substring(bracketOpen + 1, dash))
  //   } else {
  //     Left(s"Cannot Extract Year start from ${rawShow}")
  //   }
  //   year <- yearStr.toIntOption.toRight(s"Can't parse $yearStr")
  // } yield year
}

type ExtractYearEndUsingEither = (rawshow: String) => Either[String, Int]
val extractYearEndUsingEither: ExtractYearEndUsingEither = (rawShow) => {
  val dash = rawShow.indexOf('-')
  val bracketClose = rawShow.indexOf(')')

  for {
    yearStr <- if (bracketClose != -1 && dash + 1 < bracketClose) {
      Right(rawShow.substring(dash + 1, bracketClose))
    } else {
      Left(s"Cannot Extract Year End from ${rawShow}")
    }
    year <- yearStr.toIntOption.toRight(s"Can't parse $yearStr")
  } yield year
}

type ParseShowUsingEither = (rawShow: String) => Either[String, TvShow]
val parseShowUsingEither: ParseShowUsingEither = (rawShow) => for {
  name <- extractNameUsingEither(rawShow)
  yearStart <- extractYearStartUsingEither(rawShow).orElse(extractSingleYearUsingEither(rawShow))
  yearEnd <- extractYearEndUsingEither(rawShow).orElse(extractSingleYearUsingEither(rawShow))
} yield TvShow(name, yearStart, yearEnd)

def extractSingleYearUsingEither(rawShow: String): Either[String, Int] = {
  val dash = rawShow.indexOf('-')
  val bracketOpen = rawShow.indexOf('(')
  val bracketClose = rawShow.indexOf(')')

  for {
    yearStr <- if (dash == -1 && bracketOpen + 1 < bracketClose) {
      Right(rawShow.substring(bracketOpen + 1, bracketClose))
    } else {
      Left(s"Cannot Extract Single Year from ${rawShow}")
    }
    year <- yearStr.toIntOption.toRight(s"extract single Year: Can't parse $yearStr")
  } yield year
}

def extractSingleYearOrEndYearUsingEither(rawShow: String): Either[String, Int] = {
  extractSingleYearUsingEither(rawShow).orElse(extractYearEndUsingEither(rawShow))
}

def extractAnyYearUsingEither(rawShow: String): Either[String, Int] = {
  extractYearStartUsingEither(rawShow)
    .orElse(extractYearEndUsingEither(rawShow))
    .orElse(extractSingleYearUsingEither(rawShow))
}

def extractSingleYearIfNameExistsUsingEither(rawShow: String): Either[String, Int] = {
  extractNameUsingEither(rawShow).flatMap(name => extractSingleYearUsingEither(rawShow))
}

def extractAnyYearIfNameExistsUsingEither(rawShow: String): Either[String, Int] = {
  extractNameUsingEither(rawShow).flatMap(name => extractAnyYearUsingEither(rawShow))
}

def addOrResignUsingEither(parsedShows: Either[String, List[TvShow]], newParsedShow: Either[String, TvShow]): Either[String, List[TvShow]] = {
  for {
    shows <- parsedShows
    newShow <- newParsedShow
  } yield shows.appended(newShow)
}

val parsedShowsUsingEither = parseShowsUsingEither(rawShows)