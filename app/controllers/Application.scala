package controllers

import play.api.mvc._
import play.api.data._
import play.api.data.Forms._

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

  def new_account = Action {
    Ok(views.html.new_account(newAccountForm))
  }

  def post_new_account = Action { implicit request =>
    val (username, password) = newAccountForm.bindFromRequest.get
    Ok("hello, " + username)
  }
}