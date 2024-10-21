import MultipleTypesQueries.{salva_CSV, salva_CSV_S}
import org.apache.spark.sql.{Encoders, SparkSession}

object eyeOnEventQueries {

  case class TweetData (ind:String,eye:String,tweet:String,user:String)
  val spark = SparkSession.builder().appName("eyeOnEventQueries").config("spark.master","local").getOrCreate();
  import spark.implicits._
  val schema = Encoders.product[TweetData].schema
  val df = spark.read.format("csv").option("header", "true").option("multiLine","true").schema(schema).load("dataset/eyeOnEvent.csv").as[TweetData]

  def statistics() = {
    val eye=df.map(d=>d.eye).filter(d=>d.matches("True")).count()
    val Noteye=df.filter("eye is null").count()

    val res= Seq(("Persone che hanno visto l'evento con i propri occhi",eye),
      ("Persone che non hanno visto l'evento con i propri occhi",Noteye)).toDF("Evento","num_Persone")
    //res.show()
    salva_CSV(res,"StatisticheTestimoniSandy")
  }
  def userTweets()={
    val res = df.filter(("eye ='True'")).map(d=>d.user)
    //res.show()
    salva_CSV_S(res,"UtentiTestimoniSandy")
  }
  def eyeTweets()={
    val res = df.filter(("eye ='True'")).map(d=>d.tweet)
    //res.show()
    salva_CSV_S(res,"TweetUtentiTestimoniSandy")
  }
  def main(args: Array[String]): Unit = {
    statistics()
  }
}
