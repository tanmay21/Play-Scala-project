package controllers

import javax.inject._
import play.api.Play.current
import play.api.mvc._
import play.api._
import play.api.db._
import anorm._
import anorm.SqlParser._
import play.Logger
import play.api.libs.json.{JsResult, JsSuccess, JsValue, Json}


@Singleton
class TestController @Inject()(cc: ControllerComponents, db: Database) extends AbstractController(cc) {


  def read(i:Int) = Action {
		val parser = scalar[String].single
  	db.withConnection { implicit c =>
	    val result = SQL(s"select first_name from name where id = $i").as(parser)
			Ok(result)
  	}
  }

	def delete(i:Int) = Action {
		db.withConnection { implicit c =>
			val result: Boolean = SQL(s"delete from name where id = $i").execute()
		}
		Ok(s"Row $i has been deleted!")
	}

  /*def create(i:Int) = Action {
    db.withConnection { implicit c =>
      val result: Boolean = SQL(s"insert into name values ($i,'','')").execute()
    }
    Ok(s"Row $i has been created!")
  }*/

  /*def update(i:Int, first:String, last:String) = Action {
    val parser = scalar[String].single
    db.withConnection { implicit c =>
      val result = SQL(s"update name set first_name = '$first', last_name = '$last' where id = $i").execute()
      Ok(s"Row $i has been updated!")
    }
  }*/

  /*case class Stock(symbol: String, price: Double)
  object Stock {
    implicit val formatter = Json.format[Stock]
  }
	def update = Action { request =>
      val json = request.body.asJson.get
      val stock = json.as[Stock]
      println(stock)
      Ok
  }*/

  case class Stock(id: Int, first_name: String, last_name: String)
  object Stock {
    implicit val formatter = Json.format[Stock]
  }
	def update = Action { request =>
      val json = request.body.asJson.get
      val stock = json.as[Stock]
      val first = stock.first_name
      val last = stock.last_name
      val i = stock.id
      db.withConnection { implicit c =>
	      val result = SQL(s"update name set first_name = '$first', last_name = '$last' where id = $i").execute()
	      Ok
   	 	}
  }

  def create = Action { request =>
      val json = request.body.asJson.get
      val stock = json.as[Stock]
      val first = stock.first_name
      val last = stock.last_name
      val i = stock.id
      db.withConnection { implicit c =>
      	val result: Boolean = SQL(s"insert into name values ($i,'$first','$last')").execute()
 	      Ok
   	 	}
  }


  

}