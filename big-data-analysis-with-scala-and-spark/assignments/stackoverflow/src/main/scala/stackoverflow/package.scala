package object stackoverflow {

  case class Posting(postingType: Int, id: Int, acceptedAnswer: Option[Int], parentId: Option[QID], score: Int, tags: Option[String]) extends Serializable

  type Question = Posting
  type Answer = Posting
  type QID = Int
  type HighScore = Int
  type LangIndex = Int
}
