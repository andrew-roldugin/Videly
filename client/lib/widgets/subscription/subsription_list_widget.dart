import 'package:flutter/material.dart';

class SubscriptionListWidget extends StatelessWidget {
  const SubscriptionListWidget({Key? key}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return ListView.builder(
        itemCount: 25,
        itemExtent: 150,
        itemBuilder: (BuildContext ctx, int idx) {
          return Container(
            color: Colors.red,
          );
        });
  }
}
