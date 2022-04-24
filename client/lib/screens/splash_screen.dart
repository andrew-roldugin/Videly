import 'dart:async';

import 'package:flutter/material.dart';
import 'package:videly/screens/signin_screen.dart';
import 'package:videly/widgets/common/app_title.dart';

class StartScreen extends StatefulWidget {
  const StartScreen({Key? key}) : super(key: key);

  static const String routeName = "/start";

  @override
  State<StartScreen> createState() => _StartScreenState();
}

class _StartScreenState extends State<StartScreen> {
  @override
  void initState() {
    // TODO: implement initState
    super.initState();
    Future.delayed(const Duration(seconds: 5)).then((value) => {
          Navigator.push(
              context, MaterialPageRoute(builder: (_) => const SignInScreen()))
        });
  }

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
            children: [
              Row(
                mainAxisAlignment: MainAxisAlignment.spaceEvenly,
                children: <Widget>[
                  Image.asset(
                    "assets/images/main_logo.png",
                    width: 128,
                    height: 128,
                    fit: BoxFit.scaleDown,
                  ),
                  const AppTitle(),
                ],
              ),
              const SizedBox(
                height: 25,
              ),
              const Center(
                child: CircularProgressIndicator(
                  color: Colors.black,
                  strokeWidth: 3,
                ),
              ),
            ],
          ),
        ),
      ),
    );
  }
}
