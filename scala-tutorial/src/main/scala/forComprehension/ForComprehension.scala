package forComprehension

case class Point(x: Int, y: Int)

def isInside(point: Point, radius: Int): Boolean = {
  radius * radius >= point.x * point.x + point.y * point.y
}

val points = List(Point(5, 2), Point(1, 1)) 
val radiuses = List(2, 1)

val insideCircle = for {
  point <- points
  radius <- radiuses
} yield s"$point is within a radius of ${radius}: ${isInside(point, radius)}"

val insideCircle2 = 
  for(point <- points; radius <- radiuses)
  yield s"$point is within a radius of ${radius}: ${isInside(point, radius)}"

// filtering techniques
val riskyRadiuses = List(-10, 0, 2)

val filterUsingFilterFunctionOnList = for {
  radius <- riskyRadiuses.filter(radius => radius >= 0)
  point <- points.filter(p => isInside(p, radius))
} yield s"$point is within a radius of ${radius}}"

val filterUsingGuardExpression = for {
  radius <- riskyRadiuses
  if radius >= 0
  point <- points
  if isInside(point, radius)
} yield s"$point is within a radius of ${radius}}"

def insideFilter(point: Point, radius: Int): List[Point] = {
  if (isInside(point, radius)) List(point) else List.empty
}

def riskyRadiusFilter(radius: Int): List[Int] = {
  if(radius > 0) List(radius) else List.empty
}

val filterUsingFunctionPassedToFlatMapFunction = for {
  radius <- riskyRadiuses
  validatedRadius <- riskyRadiusFilter(radius)
  point <- points
  inPoint <- insideFilter(point, validatedRadius)
} yield s"$inPoint is within a radius of ${validatedRadius}}"

// Using for comprehensions with many types
val listAndSetForComprehensionWListReturnedType = for {
  a <- List(1, 2)
  b <- Set(2, 1)
} yield a * b

val listAndSetForComprehensionWSetReturnedType = for {
  a <- Set(1, 2)
  b <- List(2, 1)
} yield a * b

case class Event(name: String, start: Int, end: Int)

def validateName(name: String): Option[String] = {
  if (name.size > 0) Some(name) else None
}

def validateEnd(end: Int): Option[Int] = {
  if (end < 3000) Some(end) else None
}

def validateStart(start: Int, end: Int): Option[Int] = {
  if (start <= end) Some(start) else None
}

def parse(name: String, start: Int, end: Int): Option[Event] =
  for {
    validName <- validateName(name)
    validEnd <- validateEnd(end)
    validStart <- validateStart(start, end)
  } yield Event(validName, validStart, validEnd)

def validateLength(start: Int, end: Int, minLength: Int): Option[Int] = {
  if ((end - start) >= minLength) Some(end - start) else None
}

def parseLongEvent(name: String, start: Int, end: Int, minLength: Int): Option[Event] = {
  for {
    validEvent <- parse(name, start, end)
    validLength <- validateLength(start, end, minLength)
  } yield validEvent
}