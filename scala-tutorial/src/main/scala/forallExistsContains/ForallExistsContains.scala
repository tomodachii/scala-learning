package forallExistsContains

case class User(name: String, city: Option[String], favoriteArtists: List[String])

val users = List(
  User("Alice", Some("Melbourne"), List("Bee Gees")), 
  User("Bob", Some("Lagos"), List("Bee Gees")), 
  User("Eve", Some("Tokyo"), List.empty), 
  User("Mallory", None, List("Metallica", "Bee Gees")),
  User("Trent", Some("Buenos Aires"), List("Led Zeppelin"))
)

type SearchUser = (List[User]) => List[User]

// f1: users that havent specified their city or live in Melbourne
val searchUsersHaveNotSpecifiedTheirCity: SearchUser = (users) => {
  users.filter(_.city.isEmpty)
}

val searchUsersLiveInMelbourne: SearchUser = (users) => {
  users.filter(_.city.exists(_ == "Melbourne"))
}

// forall will return true if the Option is None so the filter will get a true value if the city is not specified
val searchUsersHaveNotSpecifiedTheirCityOrUsersLiveInMelbourne: SearchUser = (users) => {
  users.filter(_.city.forall(_ == "Melbourne"))
}

val searchUsersLiveInLagos: SearchUser = (users) => {
  users.filter(_.city.exists(_ == "Lagos"))
}

val searchUsersLikeBeeGees: SearchUser = (users) => {
  users.filter(_.favoriteArtists.contains("Bee Gees"))
}

val searchUsersLiveInCitiesThatStartWithLetterT: SearchUser = (users) => {
  users.filter(_.city.exists(_.startsWith("T")))
}

val searchUserThatLikeArtistsWithNameLongerThanEightOrNoFavoriteArtistsAtAll: SearchUser = (users) => {
  users.filter(_.favoriteArtists.forall(_.length > 8))
}

val searchUserLikeArtistsWhoseNamesStartWithM: SearchUser = (users) => {
  users.filter(_.favoriteArtists.exists(_.startsWith("M")))
}

val usersWhoHaveNotSpecifiedTheirCity = searchUsersHaveNotSpecifiedTheirCity(users)
val usersWhoLiveInMelbourne= searchUsersLiveInMelbourne(users)
val usersHaveNotSpecifiedTheirCityOrUsersLiveInMelbourne = searchUsersHaveNotSpecifiedTheirCityOrUsersLiveInMelbourne(users)
val usersLiveInLagos = searchUsersLiveInLagos(users)
val usersLikeBeeGees = searchUsersLikeBeeGees(users)
val usersLiveInCitiesThatStartWithLetterT = searchUsersLiveInCitiesThatStartWithLetterT(users)
val userThatLikeArtistsWithNameLongerThanEightOrNoFavoriteArtistsAtAll = searchUserThatLikeArtistsWithNameLongerThanEightOrNoFavoriteArtistsAtAll(users)
val userLikeArtistsWhoseNamesStartWithM = searchUserLikeArtistsWhoseNamesStartWithM(users)