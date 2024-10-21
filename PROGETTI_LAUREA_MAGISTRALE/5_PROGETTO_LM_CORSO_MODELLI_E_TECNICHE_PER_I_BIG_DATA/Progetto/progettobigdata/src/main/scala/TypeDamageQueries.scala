import InfoTypesQueries.{TweetDataJoplin, df1, df2, schema2, spark}
import MultipleTypesQueries.{salva_CSV, salva_CSV_S}
import org.apache.spark.sql.{Encoders, SparkSession}

object TypeDamageQueries {

  case class TweetData (ind:String,tipo_danno:String,tipo:String,tweet:String,tipo_info:String,user:String)
  case class TweetDataJoplin(ind:String,tipo_danno:String,tipo_info:String,tweet:String,user:String)
  val spark = SparkSession.builder().appName("TypeDamageQueries").config("spark.master","local").getOrCreate();
  import spark.implicits._
  val schema = Encoders.product[TweetData].schema
  val schema2 = Encoders.product[TweetDataJoplin].schema
  val df = spark.read.format("csv").option("header", "true").option("multiLine","true").schema(schema).load("dataset/typeDamage.csv").as[TweetData]
  val df2 = spark.read.format("csv").option("header", "true").option("multiLine","true").schema(schema2).load("dataset/typeDamage2.csv").as[TweetDataJoplin]

  def sandyStatistics() = {
    val dir=df.filter("tipo='Informative (Direct)'")
    val ind=df.filter("tipo='Informative (Indirect)'")
    val both=df.filter("tipo='Informative (Direct or Indirect)'")
    val infIndPeople= ind.map(d=>d.tipo_danno).filter(d=>d.matches("People.+")).count()
    val infDiPeople = dir.map(d=>d.tipo_danno).filter(d=>d.matches("People.+")).count()
    val infBothPeople = both.map(d=>d.tipo_danno).filter(d=>d.matches("People.+")).count()
    val infIndInfra = ind.map(d=>d.tipo_danno).filter(d=>d.matches("Infrastructure.+")).count()
    val infDiInfra = dir.map(d=>d.tipo_danno).filter(d=>d.matches("Infrastructure.+")).count()
    val infBothInfra = both.map(d=>d.tipo_danno).filter(d=>d.matches("Infrastructure.+")).count()
    val infIndBoth = ind.map(d=>d.tipo_danno).filter(d=>d.matches("Both.+")).count()
    val infDiBoth = dir.map(d=>d.tipo_danno).filter(d=>d.matches("Both.+")).count()
    val infBothBoth = both.map(d=>d.tipo_danno).filter(d=>d.matches("Both.+")).count()
    val infIndNspec= ind.map(d=>d.tipo_danno).filter(d=>d.matches("Not specified.+")).count()
    val infDiNspec = dir.map(d=>d.tipo_danno).filter(d=>d.matches("Not specified.+")).count()
    val infBothNspec = both.map(d=>d.tipo_danno).filter(d=>d.matches("Not specified.+")).count()
    val res= Seq(("People",infIndPeople,infDiPeople,infBothPeople),("Infrastructure",infIndInfra,infDiInfra,infBothInfra),
      ("Both",infIndBoth,infDiBoth,infBothBoth),("Not specified",infIndNspec,infDiNspec,infBothNspec)).toDF("TipoDanno","num_Inf_IND","num_Inf_DIR","num_inf_Unknown")
    //res.show()
    salva_CSV(res,"StatisticheTipoDiDannoTweetSandy")
  }
  def confronto()={
    val infIndPeopleJ= df2.map(d=>d.tipo_danno).filter(d=>d.matches("People.+")).count()
    val infIndInfraJ = df2.map(d=>d.tipo_danno).filter(d=>d.matches("Infrastructure.+")).count()
    val infIndBothJ = df2.map(d=>d.tipo_danno).filter(d=>d.matches("Both.+")).count()
    val infIndNspecJ= df2.map(d=>d.tipo_danno).filter(d=>d.matches("Not specified.+")).count()
    val infIndPeopleS= df.map(d=>d.tipo_danno).filter(d=>d.matches("People.+")).count()
    val infIndInfraS = df.map(d=>d.tipo_danno).filter(d=>d.matches("Infrastructure.+")).count()
    val infIndBothS= df.map(d=>d.tipo_danno).filter(d=>d.matches("Both.+")).count()
    val infIndNspecS= df.map(d=>d.tipo_danno).filter(d=>d.matches("Not specified.+")).count()
    val res= Seq(("People",infIndPeopleJ,infIndPeopleS),("Infrastructure",infIndInfraJ,infIndInfraS),
      ("Both",infIndBothJ,infIndBothS),("Not specified",infIndNspecJ,infIndNspecS)).toDF("TipoDanno","num_Inf_Joplin","num_Inf_Sandy")
    //res.show()
    salva_CSV(res,"ConfrontoTipoDiDannoSandyJoplin")
  }

  def joplinStatistics()={
    val infIndPeople= df2.map(d=>d.tipo_danno).filter(d=>d.matches("People.+")).count()
    val infIndInfra = df2.map(d=>d.tipo_danno).filter(d=>d.matches("Infrastructure.+")).count()
    val infIndBoth = df2.map(d=>d.tipo_danno).filter(d=>d.matches("Both.+")).count()
    val infIndNspec= df2.map(d=>d.tipo_danno).filter(d=>d.matches("Not specified.+")).count()
    val res= Seq(("People",infIndPeople),("Infrastructure",infIndInfra),
      ("Both",infIndBoth),("Not specified",infIndNspec)).toDF("TipoDanno","num_Inf_")
    //res.show()
    salva_CSV(res,"StatisticheTipoDiDannoTweetJoplin")
  }

  def sandypeopleTweets()={
    val res = df.filter(d=>d.tipo_danno.matches("People.+")||d.tipo_danno.matches("Both.+")).map(d=>d.tweet)
    //res.show()
    salva_CSV_S(res,"TweetPersoneCoinvolteSandy")
  }

  def sandyinfrastructureTweets()={
    val res = df.filter(d=>d.tipo_danno.matches("Infrastructure.+")||d.tipo_danno.matches("Both.+")).map(d=>d.tweet)
    //res.show()
    salva_CSV_S(res,"TweetInfrastruttureCoinvolteSandy")
  }

  def sandynotSpecifiedTweets()={
    val res = df.filter(d=>d.tipo_danno.matches("Not specified.+")).map(d=>d.tweet)
    //res.show()
    salva_CSV_S(res,"TweetDannoNonSpecificoSandy")
  }
  def joplinpeopleTweets()={
    val res = df2.filter(d=>d.tipo_danno.matches("People.+")||d.tipo_danno.matches("Both.+")).map(d=>d.tweet)
    //res.show()
    salva_CSV_S(res,"TweetPersoneCoinvolteJoplin")
  }

  def joplininfrastructureTweets()={
    val res = df2.filter(d=>d.tipo_danno.matches("Infrastructure.+")||d.tipo_danno.matches("Both.+")).map(d=>d.tweet)
    //res.show()
    salva_CSV_S(res,"TweetInfrastruttureCoinvolteJoplin")
  }

  def joplinnotSpecifiedTweets()={
    val res = df2.filter(d=>d.tipo_danno.matches("Not specified.+")).map(d=>d.tweet)
    //res.show()
    salva_CSV_S(res,"TweetDannoNonSpecificoJoplin")
  }
}
