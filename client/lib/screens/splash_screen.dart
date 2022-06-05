import 'dart:async';

import 'package:client/resources/resources.dart';
import 'package:client/screens/main_screen.dart';
import 'package:client/services/auth_service.dart';
import 'package:client/widgets/common/app_title.dart';
import 'package:firebase_auth/firebase_auth.dart';
import 'package:flutter/material.dart';
import 'package:client/screens/signin_screen.dart';
import 'package:get_it/get_it.dart';
import 'package:provider/provider.dart';

class _SplashScreenViewModel {
  final BuildContext context;
  late final AuthService _authService;

  _SplashScreenViewModel(this.context) {
    _authService = GetIt.I<AuthService>();
    checkAuth();
  }

  void checkAuth() async {
    Future.delayed(const Duration(seconds: 2)).then((value) {
      var nextScreen =
          _authService.checkAuth() ? const MainScreen() : const SignInScreen();

      Navigator.pushReplacement(
        context,
        MaterialPageRoute(builder: (_) => nextScreen),
      );
    });
  }
}

class SplashScreen extends StatelessWidget {
  static const String routeName = "/start";

  const SplashScreen({Key? key}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return Provider(
      create: (ctx) => _SplashScreenViewModel(ctx),
      child: const _SplashScreenWidget(),
      lazy: false,
    );
  }
}

class _SplashScreenWidget extends StatelessWidget {
  const _SplashScreenWidget({
    Key? key,
  }) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return SafeArea(
      child: Scaffold(
        backgroundColor: Colors.white,
        body: Container(
          height: MediaQuery.of(context).size.height - 50,
          decoration: const BoxDecoration(
            color: Colors.blue,
            borderRadius: BorderRadius.only(
              bottomLeft: Radius.circular(30),
              bottomRight: Radius.circular(30),
            ),
          ),
          child: Column(
            mainAxisAlignment: MainAxisAlignment.center,
            children: const [
              _AppHeaderWidget(),
              SizedBox(
                height: 25,
              ),
              _LoaderWidget(),
            ],
          ),
        ),
      ),
    );
  }
}

class _LoaderWidget extends StatelessWidget {
  const _LoaderWidget({
    Key? key,
  }) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return const Center(
      child: CircularProgressIndicator(
        color: Colors.black,
        strokeWidth: 3,
      ),
    );
  }
}

class _AppHeaderWidget extends StatelessWidget {
  const _AppHeaderWidget({
    Key? key,
  }) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return Row(
      mainAxisAlignment: MainAxisAlignment.spaceEvenly,
      children: <Widget>[
        Image.asset(
          AppImages.mainLogo,
          width: 128,
          height: 128,
          fit: BoxFit.scaleDown,
        ),
        const AppTitle()
      ],
    );
  }
}
