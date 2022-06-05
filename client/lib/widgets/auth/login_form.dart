import 'package:client/domain/models/login_form_data.dart';
import 'package:client/screens/main_screen.dart';
import 'package:client/screens/signup_screen.dart';
import 'package:client/services/auth_service.dart';
import 'package:client/widgets/auth/reset_password_form.dart';
import 'package:flutter/material.dart';
import 'package:get_it/get_it.dart';
import 'package:provider/provider.dart';
import 'package:validators/validators.dart' as validator;

class _LoginFormViewModel {
  final LoginFormData _formData = LoginFormData();
  final _formKey = GlobalKey<FormState>();
  late final AuthService _authService;

  _LoginFormViewModel() {
    _authService = GetIt.I<AuthService>();
  }

  get formKey => _formKey;

  set password(String newPassword) {
    _formData.password = newPassword;
  }

  set login(String login) {
    _formData.email = login;
  }

  bool _validate() {
    final isValid = formKey.currentState?.validate() ?? false;
    if (!isValid) {
      return false;
    }
    formKey.currentState?.save();
    return true;
  }

  void onLoginButtonClick(BuildContext context) {
    if (_validate()) {
      _authService
          .signIn(formData: _formData)
          .then((value) => {
        if (value)
          {
            Navigator.pushReplacementNamed(
              context,
              MainScreen.routeName,
            )
          }
      });
    }
  }

  void onLoginGuestModeButton(BuildContext context) {
    _authService.signInAnonymously();
    Navigator.pushNamed(context, MainScreen.routeName);
  }

  void switchToRegisterForm(BuildContext context) {
    Navigator.pushNamed(context, SignUpScreen.routeName, arguments: {"login": _formData.email});
  }
}

class LoginForm extends StatelessWidget {
  const LoginForm({Key? key}) : super(key: key);

  static Widget create() {
    return Provider(create: (_) => _LoginFormViewModel(), child: const LoginForm(),);
  }

  @override
  Widget build(BuildContext context) {
    var model = Provider.of<_LoginFormViewModel>(context);
    //blockRotation();
    return Form(
      key: model.formKey,
      child: Column(
        children: [
          TextFormField(
            //autofocus: true,
            //autovalidateMode: AutovalidateMode.onUserInteraction,
            validator: (v) => v != null && validator.isEmail(v)
                ? null
                : "Введите корректный Email",
            textInputAction: TextInputAction.next,
            keyboardType: TextInputType.emailAddress,
            decoration: InputDecoration(
              fillColor: Colors.white,
              filled: true,
              prefixIcon: const Icon(Icons.account_circle),
              // label: const Text(
              //   "Логин",
              //   style: TextStyle(color: Colors.black),
              // ),
              hintText: "Email",
              border: OutlineInputBorder(
                borderRadius: BorderRadius.circular(10),
              ),
            ),
            onChanged: (v) => model.login = v,
          ),
          const SizedBox(
            height: 25,
          ),
          TextFormField(
            //autovalidateMode: AutovalidateMode.onUserInteraction,
            keyboardType: TextInputType.visiblePassword,
            validator: (v) => v == null || v.length < 6
                ? "Длина пароля должна составлять не менее 6 символов"
                : null,
            decoration: InputDecoration(
              fillColor: Colors.white,
              filled: true,
              prefixIcon: const Icon(Icons.lock),
              // label: const Text(
              //   "Пароль",
              //   style: TextStyle(color: Colors.black),
              // ),
              helperText: "Не менее 6 символов",
              hintText: "Пароль",
              border: OutlineInputBorder(
                borderRadius: BorderRadius.circular(10),
              ),
            ),
            obscureText: true,
            onChanged: (v) => model.password = v,
          ),
          const SizedBox(
            height: 25,
          ),
          Row(
            mainAxisAlignment: MainAxisAlignment.spaceBetween,
            children: [
              MaterialButton(
                height: 35,
                color: const Color.fromRGBO(65, 75, 178, 1),
                child: const Text(
                  "Войти",
                  style: TextStyle(color: Colors.white),
                ),
                onPressed: () => model.onLoginButtonClick(context),
              ),
              MaterialButton(
                onPressed: () {
                  showPopUpForm(context);
                },
                child: Text(
                  "Забыли пароль?",
                  style: Theme.of(context).textTheme.bodyText1,
                ),
              )
            ],
          ),
          Padding(
            padding: const EdgeInsets.symmetric(vertical: 5),
            child: Center(
              child: Text("или", style: Theme.of(context).textTheme.bodyText1),
            ),
          ),
          MaterialButton(
            //minWidth: double.infinity,
            height: 35,
            color: const Color.fromRGBO(65, 75, 178, 1),
            child: const Text(
              "Войти как гость",
              style: TextStyle(color: Colors.white),
            ),
            onPressed: () {
              model.onLoginGuestModeButton(context);
            },
          ),
          const _BottomBar()
        ],
      ),
    );
  }

  Future<dynamic> showPopUpForm(BuildContext context) {
    return showModalBottomSheet(
      isScrollControlled: false,
      context: context,
      builder: (ctx) => const ResetPasswordForm(),
      shape: const RoundedRectangleBorder(
        borderRadius: BorderRadius.vertical(
          top: Radius.circular(20),
        ),
      ),
    );
  }
}

class _BottomBar extends StatelessWidget {
  const _BottomBar({Key? key}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    var model = Provider.of<_LoginFormViewModel>(context);
    return Row(
      mainAxisAlignment: MainAxisAlignment.center,
      children: [
        Text(
          "Нет аккаунта?",
          style: Theme.of(context).textTheme.bodyText1,
        ),
        TextButton(
          child: Text(
            "Создать",
            style: Theme.of(context).textTheme.bodyText2,
          ),
          onPressed: () {
            model.switchToRegisterForm(context);
          },
        )
      ],
    );
  }
}