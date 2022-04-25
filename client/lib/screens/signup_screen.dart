import 'package:flutter/material.dart';
import 'package:client/models/register_form_data.dart';
import 'package:client/screens/homepage_screen.dart';
import 'package:client/screens/signin_screen.dart';
import 'package:client/widgets/common/app_title.dart';
import 'package:client/widgets/common/orientation_mode.dart';
import 'package:validators/validators.dart' as validator;

class SignUpScreen extends StatelessWidget {
  const SignUpScreen({Key? key}) : super(key: key);

  static const String routeName = "/signup";

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
                  _Header(
                    message: "Зарегистрироваться на Videly",
                  ),
                  Padding(
                    padding:
                        EdgeInsets.symmetric(vertical: 25, horizontal: 16.0),
                    child: RegisterForm(),
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
          "Уже есть аккаунт?",
          style: Theme.of(context).textTheme.bodyText1,
        ),
        TextButton(
          onPressed: () {
            Navigator.pushReplacementNamed(context, SignInScreen.routeName);
          },
          child: Text(
            "Войти",
            style: Theme.of(context).textTheme.bodyText2,
          ),
        )
      ],
    );
  }
}

class _Header extends StatelessWidget {
  const _Header({Key? key, required this.message}) : super(key: key);

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
          Text(
            message,
            style: Theme.of(context).textTheme.headline2,
          ),
        ],
      ),
    );
  }
}

class RegisterForm extends StatefulWidget {
  const RegisterForm({Key? key}) : super(key: key);

  @override
  State<RegisterForm> createState() => _RegisterFormState();
}

class _RegisterFormState extends State<RegisterForm>
    with PortraitStatefulModeMixin {
  final RegisterFormData _formData = RegisterFormData();

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
            validator: (v) => v != null && validator.isEmail(v)
                ? null
                : "Введите корректный Email",
            textInputAction: TextInputAction.next,
            keyboardType: TextInputType.emailAddress,
            decoration: InputDecoration(
              fillColor: Colors.white,
              filled: true,
              prefixIcon: const Icon(Icons.email),
              // label: Text(
              //   "Логин",
              //   style: TextStyle(color: Colors.black),
              // ),
              hintText: "Логин",
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
            //autofocus: true,
            textInputAction: TextInputAction.next,
            keyboardType: TextInputType.text,
            validator: (v) =>
                v == null || v.isEmpty ? "Заполните это поле" : null,
            decoration: InputDecoration(
              fillColor: Colors.white,
              filled: true,
              // label: Text(
              //   "Логин",
              //   style: TextStyle(color: Colors.black),
              // ),
              prefixIcon: const Icon(Icons.account_circle),
              hintText: "Никнейм",
              helperText: "Используется как имя канала (можно будет изменить)",
              border: OutlineInputBorder(
                borderRadius: BorderRadius.circular(10),
              ),
            ),
            onChanged: (v) => _formData.nickname = v,
          ),
          const SizedBox(
            height: 25,
          ),
          TextFormField(
            textInputAction: TextInputAction.next,
            keyboardType: TextInputType.visiblePassword,
            validator: (v) => v == null || v.length < 6
                ? "Длина пароля должна составлять не менее 6 символов"
                : null,
            decoration: InputDecoration(
              fillColor: Colors.white,
              filled: true,
              prefixIcon: const Icon(Icons.lock),

              // label: Text(
              //   "Пароль",
              //   style: TextStyle(color: Colors.black),
              // ),
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
          TextFormField(
            keyboardType: TextInputType.visiblePassword,
            validator: (v) => v == null || v.compareTo(_formData.password!) != 0
                ? "Пароли не совпадают"
                : null,
            decoration: InputDecoration(
              fillColor: Colors.white,
              filled: true,
              prefixIcon: const Icon(Icons.lock),
              // label: Text(
              //   "Подтвердите пароль",
              //   style: TextStyle(color: Colors.black),
              // ),
              hintText: "Подтверждение пароля",
              border: OutlineInputBorder(
                borderRadius: BorderRadius.circular(10),
              ),
            ),
            obscureText: true,
            onChanged: (v) => _formData.confirmPassword = v,
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
                  "Завершить регистрацию",
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
                //minWidth: double.infinity,
                height: 35,
                color: const Color.fromRGBO(65, 75, 178, 1),
                child: const Text(
                  "Войти как гость",
                  style: TextStyle(color: Colors.white),
                ),
                onPressed: () {
                  Navigator.pushReplacementNamed(
                      context, HomepageScreen.routeName);
                },
              ),
            ],
          ),
        ],
      ),
    );
  }
}
