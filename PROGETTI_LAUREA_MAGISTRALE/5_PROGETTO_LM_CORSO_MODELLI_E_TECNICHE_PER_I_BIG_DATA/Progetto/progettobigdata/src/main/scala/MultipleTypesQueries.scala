import org.apache.hadoop.fs.{FileSystem, Path}
import org.apache.spark.sql.{DataFrame, Dataset, Encoders, SparkSession}

object MultipleTypesQueries {

  case class TweetData (ind:String,tipo:String,tweet:String,user:String)
  val spark = SparkSession.builder().appName("MultipleTypesQueries").config("spark.master","local").getOrCreate();
  import spark.implicits._
  val schema = Encoders.product[TweetData].schema
  val df1 = spark.read.format("csv").option("header", "true").option("multiLine","true").schema(schema).load("dataset/ipoType.csv").as[TweetData]
  val df2 = spark.read.format("csv").option("header", "true").option("multiLine","true").schema(schema).load("dataset/ipoType2.csv").as[TweetData]

  def sandyStatistics() = {
    var informative=df1.map(d=>d.tipo).filter(
      d => d.matches("Informative.+")
    ).count()
    var personal=df1.map(d=>d.tipo).filter(d => d.matches("Personal Only")).count()
    var other = df1.map(d=>d.tipo).filter(d=>d.matches("Other")).count()
    val res = Seq(("Informative",informative),("Personal Only",personal),("Other",other)).toDF("Tipo","numTweet")
    //res.show()
    salva_CSV(res,"StatisticheAttinenzaTweetSandy")

  }

  def joplinStatistics()={
    var informative=df2.map(d=>d.tipo).filter(
      d => d.matches("Informative.+")
    ).count()
    var personal=df2.map(d=>d.tipo).filter(d => d.matches("Personal Only")).count()
    var other = df2.map(d=>d.tipo).filter(d=>d.matches("Other")).count()
    val res = Seq(("Informative",informative),("Personal Only",personal),("Other",other)).toDF("Tipo","numTweet")
    //res.show()
    salva_CSV(res,"StatisticheAttinenzaTweetJoplin")
  }

  def confrontoTweets()={
    val sandy = df1.count()
    val joplin = df2.count()
    val res= Seq(("Sandy",sandy),("Joplin",joplin)).toDF("Disaster","Num_Tweets")
    //res.show()
    salva_CSV(res,"ConfrontoNumeroTweetsSandyJoplin")
  }

  def salva_CSV(res:DataFrame, nome:String): Unit ={
    val path= "results/"
    val path_res= path+nome
    res.write.format("csv").save(path_res)
    val sc=spark.sparkContext
    val fs=FileSystem.get(sc.hadoopConfiguration)
    val f = fs.globStatus(new Path(path_res+"/part*"))(0).getPath().getName()
    fs.rename(new Path(path_res+"/"+f),new Path(path_res+".csv"))
    fs.delete(new Path(path_res),true)
  }

  def salva_CSV_S(res:Dataset[String], nome:String): Unit ={
    val path= "results/"
    val path_res= path+nome
    res.write.format("csv").save(path_res)
    val sc=spark.sparkContext
    val fs=FileSystem.get(sc.hadoopConfiguration)
    val f = fs.globStatus(new Path(path_res+"/part*"))(0).getPath().getName()
    fs.rename(new Path(path_res+"/"+f),new Path(path_res+".csv"))
    fs.delete(new Path(path_res),true)
  }

}
