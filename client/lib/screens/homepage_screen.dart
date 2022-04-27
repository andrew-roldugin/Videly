import 'dart:io';

import 'package:firebase_auth/firebase_auth.dart';
import 'package:flutter/material.dart';
import 'package:get_it/get_it.dart';
import 'package:http/io_client.dart';

import '../http/auth.dart';

class HomepageScreen extends StatelessWidget {
  static const String routeName = '/homepage';

  const HomepageScreen({Key? key}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        actions: <Widget>[
          IconButton(
              icon: const Icon(Icons.close),
              onPressed: () {
                // Implement logout functionality

                Navigator.pop(context);
              }),
        ],
        title: const Text(
          'Homepage',
          style: TextStyle(color: Colors.white),
        ),
        backgroundColor: Colors.lightBlueAccent,
      ),
      body: const TstWgt(),
    );
  }
}

class TstWgt extends StatefulWidget {
  const TstWgt({Key? key}) : super(key: key);

  @override
  State<TstWgt> createState() => _TstWgtState();
}

class _TstWgtState extends State<TstWgt> {
  String msg = "qq";
  TextEditingController c = TextEditingController(text: "пока нет токена");

  _sendRequest() async {
    var _client = HttpClient();
    _client.badCertificateCallback =
        (X509Certificate cert, String host, int port) => true;

    // var url = Uri.https("45.8.248.185:8443", "/api/test/delete");

    final http = IOClient(_client);

    await http
        .get(Uri.https("45.8.248.185:8443", "/api/test/hello"))
        .then((res) {
      if (res.statusCode == 200) {
        msg = res.body + "@" + DateTime.now().toString();
      } else {
        msg = "Error!!!";
      }
    }).onError((error, stackTrace) {
      msg = error.toString();
    });
    setState(() {});
  }

  void _logout(){
    var dio = GetIt.instance<AuthenticationService>();
    dio.signOut();
  }

  @override
  Widget build(BuildContext context) {
    FirebaseAuth.instance.currentUser
        ?.getIdToken()
        .then((value) => c.text = value);
    return Column(
      children: [
        MaterialButton(
          child: const Text('Send request Delete'),
          onPressed: () => _sendRequest(),
        ),
        MaterialButton(
          child: const Text('Logout'),
          onPressed: _logout,
        ),
        Padding(
          padding: const EdgeInsets.symmetric(vertical: 16.0),
          child: Text(
            msg,
            textAlign: TextAlign.center,
            style: const TextStyle(
              color: Colors.black,
            ),
          ),
        ),
        Text("Пользователь " +
            (FirebaseAuth.instance.currentUser?.email ??
                "Вы не выполнили вход")),
        TextFormField(
          controller: c,
        )
      ],
    );
  }
}
