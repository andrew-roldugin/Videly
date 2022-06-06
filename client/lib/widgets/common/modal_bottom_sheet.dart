import 'package:flutter/material.dart';

class MyBottomSheet {
  static void show(BuildContext context, String message) {
    showModalBottomSheet(
      isScrollControlled: false,
      context: context,
      builder: (ctx) => Padding(
        padding: const EdgeInsets.all(8.0),
        child: Wrap(children: [
          Center(
            child: Text(
              message,
              style: const TextStyle(
                  fontSize: 18.0,
                  color: Colors.green,
                  fontFamily: "Roboto",
                  fontWeight: FontWeight.w500,
                  decoration: TextDecoration.none),
            ),
          ),
        ]),
      ),
      shape: const RoundedRectangleBorder(
        borderRadius: BorderRadius.vertical(
          top: Radius.circular(20),
        ),
      ),
    );
  }
}
