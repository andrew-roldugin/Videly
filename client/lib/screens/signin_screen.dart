import 'package:client/screens/signup_screen.dart';
import 'package:client/services/auth_service.dart';
import 'package:client/widgets/auth/login_form.dart';
import 'package:client/widgets/common/app_title.dart';
import 'package:flutter/material.dart';
import 'package:provider/provider.dart';

class SignInScreen extends StatelessWidget {
  const SignInScreen({Key? key}) : super(key: key);

  static const String routeName = "/signin";

  @override
  Widget build(BuildContext context) {
    return const _LoginWidget();
  }
}

class _LoginWidget extends StatelessWidget {
  const _LoginWidget({
    Key? key,
  }) : super(key: key);

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
                children: [
                  const _LoginScreenHeader(
                    message: "Войти на Videly",
                  ),
                  Padding(
                    padding: const EdgeInsets.fromLTRB(16, 100, 16, 70),
                    child: LoginForm.create(),
                  ),
                  //_BottomBar()
                ],
              ),
            ),
          ),
        ),
      ),
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
