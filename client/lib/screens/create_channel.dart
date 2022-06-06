import 'package:flutter/material.dart';

class CreateChannelScreen extends StatelessWidget {

  static const routeName = "/create-channel";

  const CreateChannelScreen({Key? key}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return const Scaffold(body: Center(child: Text("Канал не создан")),);
  }
}
