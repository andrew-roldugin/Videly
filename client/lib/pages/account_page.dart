import 'package:flutter/material.dart';

import '../widgets/video/video.dart';

class AccountPage extends StatefulWidget {
  const AccountPage({Key? key}) : super(key: key);

  @override
  State<AccountPage> createState() => _AccountPageState();
}

class _AccountPageState extends State<AccountPage> {
  @override
  Widget build(BuildContext context) {
    return SafeArea(child: VideoWidget());
    // return SafeArea(
    //   child: Row(
    //     mainAxisAlignment: MainAxisAlignment.center,
    //     children: [] //const [Text("Аккаунт")],
    //   ),
    // );
  }
}