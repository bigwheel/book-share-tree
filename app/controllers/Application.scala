package controllers

import play.api.mvc._
import play.api.data._
import play.api.data.Forms._
import play.api.db.DB
import anorm._
import play.api.Play.current

object Application extends Controller {

  val newAccountForm = Form(
    tuple(
      "username" -> text,
      "password" -> text
    )
  )

  def index = Action {
    Ok(views.html.index("Your new application is ready."))
  }

  def login = Action {
    Ok(views.html.login(newAccountForm))
  }

  def post_login = Action { implicit request =>
    val (username, password) = newAccountForm.bindFromRequest.get
    DB.withConnection { implicit connection =>
      if (SQL("SELECT username, password FROM User")().exists(row =>
        row[String]("username") == username && row[String]("password") == password)) {
        Ok("hello, " + username)
      } else {
        Ok("login failed") // 原義的にはauthentication failed(401だっけ)にするべき？
      }
    }
  }

  def new_account = Action {
    Ok(views.html.new_account(newAccountForm))
  }

  def post_new_account = Action { implicit request =>
    val (username, password) = newAccountForm.bindFromRequest.get
    DB.withConnection { implicit connection =>
      // FIXME: もろSQLインジェクションの元だがonメソッドの挙動がおかしいので一旦放置
      SQL("INSERT INTO User(username, password) values('" + username + "', '" + password + "')")
        .executeInsert()
    }
    Ok("hello, " + username)
  }

  def users = Action {
    DB.withConnection { implicit connection =>
      val usersQuery = SQL("SELECT username FROM User")
      Ok(usersQuery().map(row => row[String]("username")).mkString(","))
    }
  }
}