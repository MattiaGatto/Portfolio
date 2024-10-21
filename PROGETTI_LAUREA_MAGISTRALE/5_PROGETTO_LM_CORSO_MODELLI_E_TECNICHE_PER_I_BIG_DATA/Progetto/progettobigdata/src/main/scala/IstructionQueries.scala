import MultipleTypesQueries.salva_CSV
import org.apache.spark.sql.{Encoders, SparkSession}

object IstructionQueries {

  case class TweetData (ind:String,instruction:String,tweet:String,tipo:String)
  val spark = SparkSession.builder().appName("istructionQueries").config("spark.master","local").getOrCreate();
  import spark.implicits._
  val schema = Encoders.product[TweetData].schema
  val df = spark.read.format("csv").option("header", "true").option("multiLine","true").schema(schema).load("dataset/instruction.csv").as[TweetData]

  def statistics() = {
    var informative_peop=df.filter(d=>d.tipo.matches("Informative: peop.+")).map(d=>d.tweet).first()
    var informative_cele=df.filter(d=>d.tipo.matches("Informative: cele.+")).map(d=>d.tweet).first()
    var informative_requ=df.filter(d=>d.tipo.matches("Informative: requ.+")).map(d=>d.tweet).first()
    var informative_info=df.filter(d=>d.tipo.matches("Informative: info.+")).map(d=>d.tweet).first()
    var informative_caut=df.filter(d=>d.tipo.matches("Informative: caut.+")).map(d=>d.tweet).first()
    var informative_dama=df.filter(d=>d.tipo.matches("Informative: dama.+")).map(d=>d.tweet).first()
    var informative_othe=df.filter(d=>d.tipo.matches("Informative: othe.+")).map(d=>d.tweet).first()
    var informative_offe=df.filter(d=>d.tipo.matches("Informative: offe.+")).map(d=>d.tweet).first()
    var informative_casu=df.filter(d=>d.tipo.matches("Informative: casu.+")).map(d=>d.tweet).first()

    val res= Seq(("informative people missing",informative_peop,informative_peop_Instruction()),("informative celebrities or authorities react",informative_cele,informative_cele_Instruction()),
      ("informative request donation of money",informative_requ,informative_requ_Instruction()),("informative information source",informative_info,informative_info_Instruction()),
      ("informative caution or advice",informative_caut,informative_caut_Instruction()),("informative damage  ",informative_dama,informative_dama_Instruction()),
      ("informative other type of photos/videos",informative_othe,informative_othe_Instruction()),("informative offers/gives donations of money",informative_offe,informative_offe_Instruction()),
      ("informative casualities",informative_casu,informative_casu_Instruction())).toDF("Tipo","tweet","Istruction")
    res.show()
    //res.show()
    salva_CSV(res,"StatisticheIndicazioniTweetSandy")
  }
  def informative_peop_Instruction(): String ={
    var res=df.filter(d=>d.tipo.matches("Informative: peop.+")).map(d=>d.instruction).first()
    return res
  }
  def informative_cele_Instruction(): String ={
    var res=df.filter(d=>d.tipo.matches("Informative: cele.+")).map(d=>d.instruction).first()
    return res
  }
  def informative_requ_Instruction(): String ={
    var res=df.filter(d=>d.tipo.matches("Informative: requ.+")).map(d=>d.instruction).first()
    return res
  }
  def informative_info_Instruction(): String ={
    var res=df.filter(d=>d.tipo.matches("Informative: info.+")).map(d=>d.instruction).first()
    return res
  }
  def informative_caut_Instruction(): String ={
    var res=df.filter(d=>d.tipo.matches("Informative: caut.+")).map(d=>d.instruction).first()
    return res
  }
  def informative_dama_Instruction(): String ={
    var res=df.filter(d=>d.tipo.matches("Informative: dama.+")).map(d=>d.instruction).first()
    return res
  }
  def informative_othe_Instruction(): String ={
    var res=df.filter(d=>d.tipo.matches("Informative: othe.+")).map(d=>d.instruction).first()
    return res
  }
  def informative_offe_Instruction(): String ={
    var res=df.filter(d=>d.tipo.matches("Informative: offe.+")).map(d=>d.instruction).first()
    return res
  }
  def informative_casu_Instruction(): String = {
    var res = df.filter(d => d.tipo.matches("Informative: casu.+")).map(d=>d.instruction).first()
    return res
  }
}
