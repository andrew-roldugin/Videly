import 'package:flutter/material.dart';
import 'package:client/models/login_form_data.dart';
import 'package:client/screens/homepage_screen.dart';
import 'package:client/screens/signup_screen.dart';
import 'package:client/widgets/common/app_title.dart';
import 'package:validators/validators.dart' as validator;
import 'package:client/widgets/common/orientation_mode.dart';

class SignInScreen extends StatelessWidget {
  const SignInScreen({Key? key}) : super(key: key);

  static const String routeName = "/signin";

  @override
  Widget build(BuildContext context) {
    return SafeArea(
      child: Scaffold(
        backgroundColor: Colors.white,
        body: Scrollbar(
          child: SingleChildScrollView(
            child: Container(
              height: MediaQuery.of(context).size.height - 50,
              decoration: const BoxDecoration(
                color: Colors.blue,
                borderRadius: BorderRadius.only(
                  bottomLeft: Radius.circular(30),
                  bottomRight: Radius.circular(30),
                ),
              ),
              child: Column(
                children: const [
                  _LoginScreenHeader(
                    message: "Войти на Videly",
                  ),
                  Padding(
                    padding:
                        EdgeInsets.fromLTRB(16, 100, 16, 70),
                    child: LoginForm(),
                  ),
                  _BottomBar()
                ],
              ),
            ),
          ),
        ),
      ),
    );
  }
}

class _BottomBar extends StatelessWidget {
  const _BottomBar({Key? key}) : super(key: key);

  @override
  Widget build(BuildContext context) {
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
            Navigator.pushNamed(context, SignUpScreen.routeName);
          },
        )
      ],
    );
  }
}

class _LoginScreenHeader extends StatelessWidget {
  const _LoginScreenHeader({Key? key, required this.message}) : super(key: key);

  final String message;

  @override
  Widget build(BuildContext context) {
    return Center(
      child: Column(
        children: [
          const Padding(
            padding: EdgeInsets.symmetric(vertical: 25),
            child: AppTitle(),
          ),
          Text(message, style: Theme.of(context).textTheme.headline2),
        ],
      ),
    );
  }
}

class LoginForm extends StatefulWidget {
  const LoginForm({Key? key}) : super(key: key);

  @override
  State<LoginForm> createState() => _LoginFormState();
}

class _LoginFormState extends State<LoginForm> with PortraitStatefulModeMixin {
  final LoginFormData _formData = LoginFormData();

  final _formKey = GlobalKey<FormState>();

  bool _validate() {
    final isValid = _formKey.currentState?.validate() ?? false;
    if (!isValid) {
      return false;
    }
    _formKey.currentState?.save();
    return true;
  }

  @override
  Widget build(BuildContext context) {
    blockRotation();
    return Form(
      key: _formKey,
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
            onChanged: (v) => _formData.email = v,
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
            onChanged: (v) => _formData.password = v,
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
                onPressed: () {
                  if (_validate()) {
                    Navigator.pushReplacementNamed(
                        context, HomepageScreen.routeName);
                  }
                },
              ),
              MaterialButton(
                onPressed: () {},
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
              Navigator.pushReplacementNamed(context, HomepageScreen.routeName);
            },
          ),
        ],
      ),
    );
  }
}
