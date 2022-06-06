import 'package:flutter/material.dart';
import 'package:flutter/widgets.dart';

class ErrorPage extends StatefulWidget {
  final String? message;

  const ErrorPage({Key? key, this.message}) : super(key: key);

  @override
  State<ErrorPage> createState() => _ErrorPageState();
}

class _ErrorPageState extends State<ErrorPage> {
  @override
  Widget build(BuildContext context) {
    return Center(
      child: Column(
        mainAxisAlignment: MainAxisAlignment.center,
        children: [
          const Icon(Icons.error, size: 80, color: Colors.red,),
          const Padding(
            padding: EdgeInsets.symmetric(vertical: 15),
            child: Text(
              "Произошла ошибка",
              style: TextStyle(
                  fontSize: 30.0,
                  color: Colors.black,
                  fontFamily: "Roboto",
                  fontWeight: FontWeight.w500,
                  decoration: TextDecoration.none),
            ),
          ),
          Text(widget.message!,
              overflow: TextOverflow.visible,
              style: const TextStyle(
                  fontSize: 18.0,
                  color: Colors.black,
                  fontFamily: "Roboto",
                  fontWeight: FontWeight.w500,
                  decoration: TextDecoration.none),
          ),
        ],
      ),
    );
  }
}
