import MultipleTypesQueries.salva_CSV
import org.apache.spark.sql.{Dataset, Encoders, SparkSession}

object InfoTypesQueries {

  case class TweetDataSandy (ind:String,tipo:String,tweet:String,Tinf:String,user:String)
  case class TweetDataJoplin(ind:String,tipo:String,tweet:String,user:String)
  val spark = SparkSession.builder().appName("InfoTypesQueries").config("spark.master","local").getOrCreate();
  import spark.implicits._
  val schema1 = Encoders.product[TweetDataSandy].schema
  val schema2 = Encoders.product[TweetDataJoplin].schema
  val df1 = spark.read.format("csv").option("header", "true").option("multiLine","true").schema(schema1).load("dataset/infoType.csv").as[TweetDataSandy]
  val df2 = spark.read.format("csv").option("header", "true").option("multiLine","true").schema(schema2).load("dataset/infoType2.csv").as[TweetDataJoplin]
  def sandyStatistics() = {
    val dir=df1.filter("Tinf='Informative (Direct)'")
    val ind=df1.filter("Tinf='Informative (Indirect)'")
    val both=df1.filter("Tinf='Informative (Direct or Indirect)'")
    val infSInd = ind.map(d=>d.tipo).filter(d=>d.matches("Information Source")).count()
    val infSDi = dir.map(d=>d.tipo).filter(d=>d.matches("Information Source")).count()
    val infSBoth = both.map(d=>d.tipo).filter(d=>d.matches("Information Source")).count()
    val unInd = ind.map(d=>d.tipo).filter(d=>d.matches("Unknown")).count()
    val unDi = dir.map(d=>d.tipo).filter(d=>d.matches("Unknown")).count()
    val unBoth = both.map(d=>d.tipo).filter(d=>d.matches("Unknown")).count()
    val c_and_D_Ind =ind.map(d=>d.tipo).filter(d=>d.matches("Casualties.+")).count()
    val c_and_D_Di = dir.map(d=>d.tipo).filter(d=>d.matches("Casualties.+")).count()
    val c_and_D_Both = both.map(d=>d.tipo).filter(d=>d.matches("Casualties.+")).count()
    val c_and_A_Ind= ind.map(d=>d.tipo).filter(d=>d.matches("Caution and advice")).count()
    val c_and_A_Di = dir.map(d=>d.tipo).filter(d=>d.matches("Caution and advice")).count()
    val c_and_A_Both = both.map(d=>d.tipo).filter(d=>d.matches("Caution and advice")).count()
    val don_Mon_Ind = ind.map(d=>d.tipo).filter(d=>d.matches("Donations of.+")).count()
    val don_Mon_Di = dir.map(d=>d.tipo).filter(d=>d.matches("Donations of.+")).count()
    val don_Mon_Both = both.map(d=>d.tipo).filter(d=>d.matches("Donations of.+")).count()
    val res= Seq(("Information Source",infSInd,infSDi,infSBoth),("Unknown",unInd,unDi,unBoth),
      ("Casualties and damage",c_and_D_Ind,c_and_D_Di,c_and_D_Both),("Caution and advice",c_and_A_Ind,c_and_A_Di,c_and_A_Both),
      ("Donations of money, goods of services",don_Mon_Ind,don_Mon_Di,don_Mon_Both)).toDF("Tipo","num_Inf_IND","num_Inf_DIR","num_Inf_Unknown")
    //res.show()
    salva_CSV(res,"StatisticheTipoInformazioneTweetSandy")
  }

  def confronto()={
    val info_src_sandy = df1.map(d=>d.tipo).filter(d=>d.matches("Information.+")).count()
    val info_src_joplin = df2.map(d=>d.tipo).filter(d=>d.matches("Information.+")).count()
    val unknown_sandy = df1.map(d=>d.tipo).filter(d=>d.matches("Unknown")).count()
    val unknown_joplin = df2.map(d=>d.tipo).filter(d=>d.matches("Unknown")).count()
    val casualties_sandy = df1.map(d=>d.tipo).filter(d=>d.matches("Casualties.+")).count()
    val casualties_joplin = df2.map(d=>d.tipo).filter(d=>d.matches("Casualties.+")).count()
    val c_and_a_sandy = df1.map(d=>d.tipo).filter(d=>d.matches("Caution and advice")).count()
    val c_and_a_joplin = df2.map(d=>d.tipo).filter(d=>d.matches("Caution and advice")).count()
    val donations_sandy =  df1.map(d=>d.tipo).filter(d=>d.matches("Donations of.+")).count()
    val donations_joplin = df2.map(d=>d.tipo).filter(d=>d.matches("Donations of.+")).count()
    val res= Seq(("Information Source",info_src_sandy,info_src_joplin),("Unknown",unknown_sandy,unknown_joplin),
      ("Casualties and damages",casualties_sandy,casualties_joplin),("Caution and advice",c_and_a_sandy,c_and_a_joplin),
      ("Donations of money, goods of services",donations_sandy,donations_joplin)).toDF("Tipo","Sandy_count","Joplin_count")
    //res.show()
    salva_CSV(res,"ConfrontoTipoInformazioneTweetSandyJoplin")
  }

  def joplinStatistics()={
    val info_src_joplin = df2.map(d=>d.tipo).filter(d=>d.matches("Information.+")).count()
    val unknown_joplin = df2.map(d=>d.tipo).filter(d=>d.matches("Unknown")).count()
    val casualties_joplin = df2.map(d=>d.tipo).filter(d=>d.matches("Casualties.+")).count()
    val c_and_a_joplin = df2.map(d=>d.tipo).filter(d=>d.matches("Caution and advice")).count()
    val donations_joplin = df2.map(d=>d.tipo).filter(d=>d.matches("Donations of.+")).count()
    val res= Seq(("Information Source",info_src_joplin),("Unknown",unknown_joplin),
      ("Casualties and damages",casualties_joplin),("Caution and advice",c_and_a_joplin),
      ("Donations of money, goods of services",donations_joplin)).toDF("Tipo","Joplin_tweet_count")
    res.show()
    //res.show()
    salva_CSV(res,"StatisticheTipoInformazioneTweetJoplin")
  }

}
