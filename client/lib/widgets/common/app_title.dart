import 'package:flutter/material.dart';

class AppTitle extends StatelessWidget {
  const AppTitle({Key? key}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return const Text(
      "Videly",
      style: TextStyle(
        fontFamily: "OpenSans",
        fontSize: 70,
        color: Colors.white,
      ),
    );
  }
}
