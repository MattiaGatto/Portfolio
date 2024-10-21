import MultipleTypesQueries.{salva_CSV, salva_CSV_S}
import org.apache.spark.sql.{Encoders, SparkSession}

object JoplinTypeDonationQueries {
  case class TweetDataJoplin(ind:String,intention:String,tipo_donation:String,tipo:String,tweet:String,user:String)
  val spark = SparkSession.builder().appName("JoplinTypeDonationQueies").config("spark.master","local").getOrCreate();
  import spark.implicits._
  val schema1 = Encoders.product[TweetDataJoplin].schema
  val df = spark.read.format("csv").option("header", "true").option("multiLine","true").schema(schema1).load("dataset/typeDonation.csv").as[TweetDataJoplin]

  def joplinStatistics()={
    val both=df.filter(d=>d.intention.matches("Both.+"))
    val invites=df.filter(d=>d.intention.matches("Invites/asks peop.+"))
    val reports=df.filter(d=>d.intention.matches("Reports that some.+"))
    val none=df.filter(d=>d.intention.matches("None of the above"))
    val tipo_eq_both=both.map(d=>d.tipo_donation).filter(d=>d.matches("Equipment.+")).count()
    val tipo_fo_both=both.map(d=>d.tipo_donation).filter(d=>d.matches("Food")).count()
    val tipo_bl_both=both.map(d=>d.tipo_donation).filter(d=>d.matches("Blood")).count()
    val tipo_mo_both=both.map(d=>d.tipo_donation).filter(d=>d.matches("Money")).count()
    val tipo_ot_both=both.map(d=>d.tipo_donation).filter(d=>d.matches("Other.+")).count()
    val tipo_sh_both=both.map(d=>d.tipo_donation).filter(d=>d.matches("Shelter")).count()
    val tipo_di_both=both.map(d=>d.tipo_donation).filter(d=>d.matches("Discount.+")).count()
    val tipo_vo_both=both.map(d=>d.tipo_donation).filter(d=>d.matches("Volunteers.+")).count()

    val tipo_eq_invites=invites.map(d=>d.tipo_donation).filter(d=>d.matches("Equipment.+")).count()
    val tipo_fo_invites=invites.map(d=>d.tipo_donation).filter(d=>d.matches("Food")).count()
    val tipo_bl_invites=invites.map(d=>d.tipo_donation).filter(d=>d.matches("Blood")).count()
    val tipo_mo_invites=invites.map(d=>d.tipo_donation).filter(d=>d.matches("Money")).count()
    val tipo_ot_invites=invites.map(d=>d.tipo_donation).filter(d=>d.matches("Other.+")).count()
    val tipo_sh_invites=invites.map(d=>d.tipo_donation).filter(d=>d.matches("Shelter")).count()
    val tipo_di_invites=invites.map(d=>d.tipo_donation).filter(d=>d.matches("Discount.+")).count()
    val tipo_vo_invites=invites.map(d=>d.tipo_donation).filter(d=>d.matches("Volunteers.+")).count()

    val tipo_eq_reports=reports.map(d=>d.tipo_donation).filter(d=>d.matches("Equipment.+")).count()
    val tipo_fo_reports=reports.map(d=>d.tipo_donation).filter(d=>d.matches("Food")).count()
    val tipo_bl_reports=reports.map(d=>d.tipo_donation).filter(d=>d.matches("Blood")).count()
    val tipo_mo_reports=reports.map(d=>d.tipo_donation).filter(d=>d.matches("Money")).count()
    val tipo_ot_reports=reports.map(d=>d.tipo_donation).filter(d=>d.matches("Other.+")).count()
    val tipo_sh_reports=reports.map(d=>d.tipo_donation).filter(d=>d.matches("Shelter")).count()
    val tipo_di_reports=reports.map(d=>d.tipo_donation).filter(d=>d.matches("Discount.+")).count()
    val tipo_vo_reports=reports.map(d=>d.tipo_donation).filter(d=>d.matches("Volunteers.+")).count()

    val tipo_eq_none=none.map(d=>d.tipo_donation).filter(d=>d.matches("Equipment.+")).count()
    val tipo_fo_none=none.map(d=>d.tipo_donation).filter(d=>d.matches("Food")).count()
    val tipo_bl_none=none.map(d=>d.tipo_donation).filter(d=>d.matches("Blood")).count()
    val tipo_mo_none=none.map(d=>d.tipo_donation).filter(d=>d.matches("Money")).count()
    val tipo_ot_none=none.map(d=>d.tipo_donation).filter(d=>d.matches("Other.+")).count()
    val tipo_sh_none=none.map(d=>d.tipo_donation).filter(d=>d.matches("Shelter")).count()
    val tipo_di_none=none.map(d=>d.tipo_donation).filter(d=>d.matches("Discount.+")).count()
    val tipo_vo_none=none.map(d=>d.tipo_donation).filter(d=>d.matches("Volunteers.+")).count()



    val res= Seq(("Equipment ",tipo_eq_both,tipo_eq_invites,tipo_eq_reports,tipo_eq_none),("Food",tipo_fo_both,tipo_fo_invites,tipo_fo_reports,tipo_fo_none),
      ("Blood",tipo_bl_both,tipo_bl_invites,tipo_bl_reports,tipo_bl_none),("Money",tipo_mo_both,tipo_mo_invites,tipo_mo_reports,tipo_mo_none),("Other or not Specified",tipo_ot_both,tipo_ot_invites,tipo_ot_reports,tipo_ot_none),
      ("Shelter",tipo_sh_both,tipo_sh_invites,tipo_sh_reports,tipo_sh_none),("Discount",tipo_di_both,tipo_di_invites,tipo_di_reports,tipo_di_none),("Volunteers/work",tipo_vo_both,tipo_vo_invites,tipo_vo_reports,tipo_vo_none)).toDF("Tipo_donations","num_Intention_Both(invites and...","num_Intention_Invites/asks peop..."
      ,"num_Intention_Reports that some...","num_Intention_None of the above")
    //res.show()
    salva_CSV(res,"StatisticheDonazioniJoplin")
  }

  def joplinDonationEquipmentTweets()={
    val res = df.filter(d=>d.tipo_donation.matches("Equipment.+")).map(d=>d.tweet)
    //res.show()
    salva_CSV_S(res,"TweetDonazioneAttrezzaturaJoplin")
  }
  def joplinDonationFoodTweets()={
    val res = df.filter(d=>d.tipo_donation.matches("Food")).map(d=>d.tweet)
    //res.show()
    salva_CSV_S(res,"TweetDonazioneCiboJoplin")
  }
  def joplinDonationBloodTweets()={
    val res = df.filter(d=>d.tipo_donation.matches("Blood")).map(d=>d.tweet)
    //res.show()
    salva_CSV_S(res,"TweetDonazioneSangueJoplin")
  }
  def joplinDonationMoneyTweets()={
    val res = df.filter(d=>d.tipo_donation.matches("Money")).map(d=>d.tweet)
    //res.show()
    salva_CSV_S(res,"TweetDonazioneDenaroJoplin")
  }
  def joplinDonationOtherTweets()={
    val res = df.filter(d=>d.tipo_donation.matches("Other.+")).map(d=>d.tweet)
    //res.show()
    salva_CSV_S(res,"TweetAltreDonazioniJoplin")
  }
  def joplinDonationShelterTweets()={
    val res = df.filter(d=>d.tipo_donation.matches("Shelter")).map(d=>d.tweet)
    //res.show()
    salva_CSV_S(res,"TweetDonazioneRifugioJoplin")
  }
  def joplinDonationDiscountTweets()={
    val res = df.filter(d=>d.tipo_donation.matches("Discount.+")).map(d=>d.tweet)
    //res.show()
    salva_CSV_S(res,"TweetDonazioneScontoJoplin")
  }
  def joplinDonationVolunteersTweets()={
    val res = df.filter(d=>d.tipo_donation.matches("Volunteers.+")).map(d=>d.tweet)
    //res.show()
    salva_CSV_S(res,"TweetDonazioneVolontariJoplin")
  }
}