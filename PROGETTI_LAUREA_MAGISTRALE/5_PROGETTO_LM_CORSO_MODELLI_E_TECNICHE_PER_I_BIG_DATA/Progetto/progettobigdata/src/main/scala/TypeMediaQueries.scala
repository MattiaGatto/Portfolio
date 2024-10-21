import MultipleTypesQueries.{salva_CSV, salva_CSV_S}
import org.apache.spark.sql.{Encoders, SparkSession}

object TypeMediaQueries {
  case class TweetData (ind:String,tipo_media:String,tipo:String,tweet:String,tipo_info:String,user:String)
  case class TweetDataJoplin(ind:String,tipo_media:String,tipo_info:String,tweet:String,user:String)
  val spark = SparkSession.builder().appName("TypeMediaQueries").config("spark.master","local").getOrCreate();
  import spark.implicits._
  val schema = Encoders.product[TweetData].schema
  val schema2 = Encoders.product[TweetDataJoplin].schema
  val df = spark.read.format("csv").option("header", "true").option("multiLine","true").schema(schema).load("dataset/typeMedia.csv").as[TweetData]
  val df2 = spark.read.format("csv").option("header", "true").option("multiLine","true").schema(schema2).load("dataset/typeMedia2.csv").as[TweetDataJoplin]

  def sandystatistics()={
    val dir=df.filter("tipo='Informative (Direct)'")
    val ind=df.filter("tipo='Informative (Indirect)'")
    val both=df.filter("tipo='Informative (Direct or Indirect)'")
    val infIndPh= ind.map(d=>d.tipo_media).filter(d=>d.matches("Look at this photo.+")).count()
    val infDiPh= dir.map(d=>d.tipo_media).filter(d=>d.matches("Look at this photo.+")).count()
    val infBothPh= both.map(d=>d.tipo_media).filter(d=>d.matches("Look at this photo.+")).count()
    val infIndVid = ind.map(d=>d.tipo_media).filter(d=>d.matches("Look at this video.+")).count()
    val infDiVid = dir.map(d=>d.tipo_media).filter(d=>d.matches("Look at this video.+")).count()
    val infBothVid= both.map(d=>d.tipo_media).filter(d=>d.matches("Look at this video.+")).count()
    val infIndWeb =ind.map(d=>d.tipo_media).filter(d=>d.matches("Look at this web.+")).count()
    val infDiWeb = dir.map(d=>d.tipo_media).filter(d=>d.matches("Look at this web.+")).count()
    val infBothWeb = both.map(d=>d.tipo_media).filter(d=>d.matches("Look at this web.+")).count()
    val infIndNone= ind.map(d=>d.tipo_media).filter(d=>d.matches("None.+")).count()
    val infDiNone = dir.map(d=>d.tipo_media).filter(d=>d.matches("None.+")).count()
    val infBothNone = both.map(d=>d.tipo_media).filter(d=>d.matches("None.+")).count()
    val infIndTv= ind.map(d=>d.tipo_media).filter(d=>d.matches("Watch this TV.+")).count()
    val infDiTv = dir.map(d=>d.tipo_media).filter(d=>d.matches("Watch this TV.+")).count()
    val infBothTv = both.map(d=>d.tipo_media).filter(d=>d.matches("Watch this TV.+")).count()
    val res= Seq(("Photo",infIndPh,infDiPh,infBothPh),("Video",infIndVid,infDiVid,infBothVid),
      ("Web",infIndWeb,infDiWeb,infBothWeb),("Other",infIndNone,infDiNone,infBothNone),("TV",infIndTv,infDiTv,infBothTv)).toDF("TipoMedia","num_Inf_IND","num_Inf_DIR","num_inf_Unknown")
    //res.show()
    salva_CSV(res,"StatisticheTipoMediaSandy")
  }
  def confronto()={
    val infPhJ= df2.map(d=>d.tipo_media).filter(d=>d.matches("Look at this photo.+")).count()
    val infVidJ = df2.map(d=>d.tipo_media).filter(d=>d.matches("Look at this video.+")).count()
    val infWebJ =df2.map(d=>d.tipo_media).filter(d=>d.matches("Look at this web.+")).count()
    val infNoneJ= df2.map(d=>d.tipo_media).filter(d=>d.matches("None.+")).count()
    val infTvJ= df2.map(d=>d.tipo_media).filter(d=>d.matches("Watch this TV.+")).count()
    val infPhS= df.map(d=>d.tipo_media).filter(d=>d.matches("Look at this photo.+")).count()
    val infVidS = df.map(d=>d.tipo_media).filter(d=>d.matches("Look at this video.+")).count()
    val infWebS =df.map(d=>d.tipo_media).filter(d=>d.matches("Look at this web.+")).count()
    val infNoneS= df.map(d=>d.tipo_media).filter(d=>d.matches("None.+")).count()
    val infTvS= df.map(d=>d.tipo_media).filter(d=>d.matches("Watch this TV.+")).count()
    val res= Seq(("Photo",infPhJ,infPhS),("Video",infVidJ,infVidS),
      ("Web",infWebJ,infWebS),("Other",infNoneJ,infNoneS),("TV",infTvJ,infTvS)).toDF("TipoMedia","num_Inf_Joplin","num_Inf_Sandy")
    //res.show()
    salva_CSV(res,"ConfrontoTipoMediaSandyJoplin")
  }

  def joplinStatistics()={
    val infPh= df2.map(d=>d.tipo_media).filter(d=>d.matches("Look at this photo.+")).count()
    val infVid = df2.map(d=>d.tipo_media).filter(d=>d.matches("Look at this video.+")).count()
    val infWeb =df2.map(d=>d.tipo_media).filter(d=>d.matches("Look at this web.+")).count()
    val infNone= df2.map(d=>d.tipo_media).filter(d=>d.matches("None.+")).count()
    val infTv= df2.map(d=>d.tipo_media).filter(d=>d.matches("Watch this TV.+")).count()
    val res= Seq(("Photo",infPh),("Video",infVid),
      ("Web",infWeb),("Other",infNone),("TV",infTv)).toDF("TipoMedia","num_Inf_")
    //res.show()
    salva_CSV(res,"StatisticheTipoMediaJoplin")
  }
  def sandyphotoTweets()={
    val res = df.filter(d=>d.tipo_media.matches("Look at this photo.+")).map(d=>d.tweet)
    //res.show()
    salva_CSV_S(res,"TweetPhotoSandy")
  }

  def sandyvideoTweets()={
    val res = df.filter(d=>d.tipo_media.matches("Look at this video.+")).map(d=>d.tweet)
    //res.show()
    salva_CSV_S(res,"TweetVideoSandy")
  }

  def sandywebTweets()={
    val res = df.filter(d=>d.tipo_media.matches("Look at this web.+")).map(d=>d.tweet)
    //res.show()
    salva_CSV_S(res,"TweetWebSandy")
  }

  def sandyotherTweets()={
    val res = df.filter(d=>d.tipo_media.matches("None.+")).map(d=>d.tweet)
    //res.show()
    salva_CSV_S(res,"TweetAltriMediaSandy")
  }

  def sandytvTweets()={
    val res = df.filter(d=>d.tipo_media.matches("Watch this TV.+")).map(d=>d.tweet)
    //res.show()
    salva_CSV_S(res,"TweetTvSandy")
  }
  def joplinphotoTweets()={
    val res = df2.filter(d=>d.tipo_media.matches("Look at this photo.+")).map(d=>d.tweet)
    //res.show()
    salva_CSV_S(res,"TweetPhotoJoplin")
  }

  def joplinvideoTweets()={
    val res = df2.filter(d=>d.tipo_media.matches("Look at this video.+")).map(d=>d.tweet)
    //res.show()
    salva_CSV_S(res,"TweetVideoJoplin")
  }

  def joplinwebTweets()={
    val res = df2.filter(d=>d.tipo_media.matches("Look at this web.+")).map(d=>d.tweet)
    //res.show()
    salva_CSV_S(res,"TweetWebJoplin")
  }

  def joplinotherTweets()={
    val res = df2.filter(d=>d.tipo_media.matches("None.+")).map(d=>d.tweet)
    //res.show()
    salva_CSV_S(res,"TweetAltriMediaJoplin")
  }

  def joplintvTweets()={
    val res = df2.filter(d=>d.tipo_media.matches("Watch this TV.+")).map(d=>d.tweet)
    //res.show()
    salva_CSV_S(res,"TweetTvJoplin")
  }

}