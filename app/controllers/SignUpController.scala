package controllers

import javax.inject.Inject
import models.{LoginDetails, SignUpDetails}
import play.api.mvc.{AbstractController, Action, AnyContent, ControllerComponents, Request}

class SignUpController @Inject()(cc: ControllerComponents) extends AbstractController(cc) with play.api.i18n.I18nSupport {

  def signUp(): Action[AnyContent] = Action { implicit request: Request[AnyContent] =>
    Ok(views.html.signup(SignUpDetails.signUpForm))
  }

  def signUpSubmit(): Action[AnyContent] = Action { implicit request: Request[AnyContent] =>
    SignUpDetails.signUpForm.bindFromRequest.fold({ formWithErrors =>
      BadRequest(views.html.signup(formWithErrors))
    }, { signUpDetails =>
      if (SignUpDetails.checkIfUserAlreadyExists(signUpDetails)) {
        Redirect(routes.HomeController.index()).withSession(request.session + ("username" -> signUpDetails.username))
      }
      else {
        SignUpDetails.addUser(signUpDetails)
      Redirect(routes.HomeController.index()).withSession(request.session + ("username" -> signUpDetails.username))
    }
    })
  }


}
