import 'package:flutter/material.dart';

class HomepageScreen extends StatelessWidget {
  static const String routeName = '/homepage';

  const HomepageScreen({Key? key}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        actions: <Widget>[
          IconButton(
              icon: Icon(Icons.close),
              onPressed: () {
                // Implement logout functionality

                Navigator.pop(context);
              }),
        ],
        title: Text(
          'Homepage',
          style: TextStyle(color: Colors.white),
        ),
        backgroundColor: Colors.lightBlueAccent,
      ),
      body: Center(child: Text('Homepage')),
    );
  }
}
