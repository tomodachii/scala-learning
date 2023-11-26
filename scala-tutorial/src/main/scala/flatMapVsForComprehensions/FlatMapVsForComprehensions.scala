package flatMapVsForComprehensions

case class Point(x: Int, y: Int)

val xs = List(1)
val ys = List(-2, 7)

val flatMapListOfPoints = xs.flatMap(x =>
  ys.map(y =>
    Point(x, y)
  )
)

val forComprehensionListOfPoints = for {
  x <- xs
  y <- ys
} yield Point(x, y)

case class Point3d(x: Int, y: Int, z: Int)

val zs = List(3, 4)

val forComprehensionListOf3dPoints = for {
  x <- xs
  y <- ys
  z <- zs
} yield Point3d(x, y, z)

val flatMapListOf3dPoints = xs.flatMap(x =>
  ys.flatMap(y =>
    zs.map(z =>
      Point3d(x, y, z)  
    )  
  )
)