import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.SparkContext._
import org.apache.spark.rdd.RDD
import wikipedia.WikipediaArticle
import wikipedia.WikipediaData

val conf: SparkConf = new SparkConf().setMaster("local").setAppName("wikipedia")
val sc: SparkContext = new SparkContext(conf)

val langs = List(
  "JavaScript", "Java", "PHP", "Python", "C#", "C++", "Ruby", "CSS",
  "Objective-C", "Perl", "Scala", "Haskell", "MATLAB", "Clojure", "Groovy")

def parse(line: String): WikipediaArticle = {
  val subs = "</title><text>"
  val i = line.indexOf(subs)
  val title = line.substring(14, i)
  val text  = line.substring(i + subs.length, line.length-16)
  WikipediaArticle(title, text)
}

// Hint: use a combination of `sc.textFile`, `WikipediaData.filePath` and `WikipediaData.parse`
val wikiRdd: RDD[WikipediaArticle] =
  sc.textFile("/Users/yuanruoliang/Developer/coursera/big-data-analysis-with-scala-and-spark/assignments/wikipedia/src/main/resources/wikipedia/wikipedia.dat")
    .map(parse)

/** Returns the number of articles on which the language `lang` occurs.
  *  Hint1: consider using method `aggregate` on RDD[T].
  *  Hint2: consider using method `mentionsLanguage` on `WikipediaArticle`
  */
def occurrencesOfLang(lang: String, rdd: RDD[WikipediaArticle]): Int = {
  1
}

wikiRdd.take(5).foreach(println)

sc.stop()

