import MultipleTypesQueries.{salva_CSV, salva_CSV_S}
import org.apache.spark.sql.{Encoders, SparkSession}

object TypeAdviceQueries {
  case class TweetDataSandy (ind:String,advice:String,tipo:String,tweet:String,tipo_info:String,user:String)
  case class TweetDataJoplin (ind:String,advice:String,tipo:String,tweet:String,user:String)
  val spark = SparkSession.builder().appName("TypeAdviceQueries").config("spark.master","local").getOrCreate();
  import spark.implicits._
  val schema1 = Encoders.product[TweetDataSandy].schema
  val schema2 = Encoders.product[TweetDataJoplin].schema
  val df1 = spark.read.format("csv").option("header", "true").option("multiLine","true").schema(schema1).load("dataset/typeAdvice.csv").as[TweetDataSandy]
  val df2 = spark.read.format("csv").option("header", "true").option("multiLine","true").schema(schema2).load("dataset/typeAdvice2.csv").as[TweetDataJoplin]

  def sandyStatistics() = {
    val dir=df1.filter("tipo='Informative (Direct)'")
    val ind=df1.filter("tipo='Informative (Indirect)'")
    val both=df1.filter("tipo='Informative (Direct or Indirect)'")
    val infIndSh= ind.map(d=>d.advice).filter(d=>d.matches("A shelter.+")).count()
    val infDiSh= dir.map(d=>d.advice).filter(d=>d.matches("A shelter.+")).count()
    val infBothSh= both.map(d=>d.advice).filter(d=>d.matches("A shelter.+")).count()
    val infIndWarn = ind.map(d=>d.advice).filter(d=>d.matches("A hurricane warning.+")).count()
    val infDiWarn= dir.map(d=>d.advice).filter(d=>d.matches("A hurricane warning.+")).count()
    val infBothWarn= both.map(d=>d.advice).filter(d=>d.matches("A hurricane warning.+")).count()
    val infIndSight =ind.map(d=>d.advice).filter(d=>d.matches("A hurricane sight.+")).count()
    val infDiSight = dir.map(d=>d.advice).filter(d=>d.matches("A hurricane sight.+")).count()
    val infBothSight = both.map(d=>d.advice).filter(d=>d.matches("A hurricane sight.+")).count()
    val infIndOther= ind.map(d=>d.advice).filter(d=>d.matches("Other")).count()
    val infDiOther = dir.map(d=>d.advice).filter(d=>d.matches("Other")).count()
    val infBothOther = both.map(d=>d.advice).filter(d=>d.matches("Other")).count()
    val res= Seq(("Shelter",infIndSh,infDiSh,infBothSh),("Other",infIndOther,infDiOther,infBothOther),
      ("Hurricane warning",infIndWarn,infDiWarn,infBothWarn),("Hurricane sighting",infIndSight,infDiSight,infBothSight)).toDF("TipoAvviso","num_Inf_IND","num_Inf_DIR","num_inf_Unknown")
    //res.show()
    salva_CSV(res,"StatisticheTipoAvvertimentoTweetSandy")
  }
  def confronto()={
    val shelter_sandy = df1.map(d=>d.advice).filter(d=>d.matches("A shelter.+")).count()
    val shelter_joplin = df2.map(d=>d.advice).filter(d=>d.matches("A shelter.+")).count()
    val warning_sandy = df1.map(d=>d.advice).filter(d=>d.matches("A hurricane warning.+")).count()
    val warning_joplin = df2.map(d=>d.advice).filter(d=>d.matches("A tornado/thunderstorm warning.+")).count()
    val sighting_sandy = df1.map(d=>d.advice).filter(d=>d.matches("A hurricane sight.+")).count()
    val sighting_joplin = df2.map(d=>d.advice).filter(d=>d.matches("A tornado sight.+")).count()
    val other_sandy = df1.map(d=>d.advice).filter(d=>d.matches("Other")).count()
    val other_joplin = df2.map(d=>d.advice).filter(d=>d.matches("Other")).count()
    val res= Seq(("Shelter",shelter_sandy,shelter_joplin),("Warning",warning_sandy,warning_joplin),
      ("Sighting",sighting_sandy,sighting_joplin),("Other",other_sandy,other_joplin)).toDF("Tipo","Sandy_count","Joplin_count")
    //res.show()
    salva_CSV(res,"ConfrontoTipoAvvertimentoTweetSandyJoplin")
  }
  def joplinStatistics()={
    val shelter_joplin = df2.map(d=>d.advice).filter(d=>d.matches("A shelter.+")).count()
    val warning_joplin = df2.map(d=>d.advice).filter(d=>d.matches("A tornado/thunderstorm warning.+")).count()
    val sighting_joplin = df2.map(d=>d.advice).filter(d=>d.matches("A tornado sight.+")).count()
    val other_joplin = df2.map(d=>d.advice).filter(d=>d.matches("Other")).count()
    val res= Seq(("Shelter",shelter_joplin),("Warning",warning_joplin),
      ("Sighting",sighting_joplin),("Other",other_joplin)).toDF("Tipo","Joplin_tweet_count")
    //res.show()
    salva_CSV(res,"StatisticheTipoAvvertimentoTweetJoplin")
  }
  def shelterTweetsSandy()={
    val res = df1.filter(d=>d.advice.matches("A shelter.+")).map(d=>d.tweet)
    //res.show()
    salva_CSV_S(res,"TweetRifugioSandy")
  }
  def shelterTweetsJoplin()={
    val res = df2.filter(d=>d.advice.matches("A shelter.+")).map(d=>d.tweet)
    //res.show()
    salva_CSV_S(res,"TweetRifugioJoplin")
  }
  def warningTweetsSandy()={
    val res = df1.filter(d=>d.advice.matches("A hurricane warning.+")).map(d=>d.tweet)
    //res.show()
    salva_CSV_S(res,"TweetAllarmeSandy")
  }
  def warningTweetsJoplin()={
    val res = df2.filter(d=>d.advice.matches("A tornado/thunderstorm warning.+")).map(d=>d.tweet)
    //res.show()
    salva_CSV_S(res,"TweetAllarmeJoplin")
  }
  def sightingTweetsSandy()={
    val res = df1.filter(d=>d.advice.matches("A hurricane sight.+")).map(d=>d.tweet)
    //res.show()
    salva_CSV_S(res,"TweetAvvistamentoSandy")
  }
  def sightingTweetsJoplin()={
    val res = df2.filter(d=>d.advice.matches("A tornado sight.+")).map(d=>d.tweet)
    //res.show()
    salva_CSV_S(res,"TweetAvvistamentoJoplin")
  }
  def otherTweetsSandy()={
    val res = df1.filter(d=>d.advice.matches("Other")).map(d=>d.tweet)
    //res.show()
    salva_CSV_S(res,"TweetAltriAvvertimentiSandy")
  }
  def otherTweetsJoplin()={
    val res = df2.filter(d=>d.advice.matches("Other")).map(d=>d.tweet)
    //res.show()
    salva_CSV_S(res,"TweetAltriAvvertimentiJoplin")
  }
  def sirenTweetsJoplin()={
    val res = df2.filter(d=>d.advice.matches("A siren.+")).map(d=>d.tweet)
    //res.show()
    salva_CSV_S(res,"TweetUditaSirenaJoplin")
  }
}
