package models

import play.api.data.Form
import play.api.data.Forms._
import models.LoginDetails

import scala.collection.mutable.ListBuffer

case class SignUpDetails(username: String, password: String)
object SignUpDetails {

  val signUpForm = Form(
    mapping(
      "username" -> nonEmptyText,
      "password" -> nonEmptyText,
    ) (SignUpDetails.apply)(SignUpDetails.unapply)
  )

  def checkIfUserAlreadyExists(userDetails: SignUpDetails): Boolean = LoginDetails.userList.contains(userDetails)

  def addUser(signUpDetails: SignUpDetails):ListBuffer[LoginDetails] =  {
    LoginDetails.userList+=(LoginDetails(signUpDetails.username,signUpDetails.password))
  }
}

