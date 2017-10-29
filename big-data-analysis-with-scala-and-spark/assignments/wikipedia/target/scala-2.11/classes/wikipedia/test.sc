import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.SparkContext._

val x = 5;

val conf: SparkConf = new SparkConf().setMaster("local").setAppName("wikipedia")
val sc: SparkContext = new SparkContext(conf)

sc.stop()
