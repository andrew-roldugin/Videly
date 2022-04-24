import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:test_app/widgets/common/app_title.dart';
import 'package:json_annotation/json_annotation.dart';

class SigInScreen extends StatelessWidget {
  const SigInScreen({Key? key}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return Container(
      child: Column(
        children: [
          _LoginScreenHeader(
            message: "Войти на Videly",
          ),
          LoginForm(),
        ],
      ),
    );
  }
}

class _LoginScreenHeader extends StatelessWidget {
  const _LoginScreenHeader({Key? key, required this.message}) : super(key: key);

  final String message;

  @override
  Widget build(BuildContext context) {
    return Center(
      child: Column(
        children: [
          SizedBox(
            height: 25,
          ),
          const AppTitle(),
          SizedBox(
            height: 25,
          ),
          Text(
            message,
            style: TextStyle(fontSize: 16),
          ),
          SizedBox(
            height: 50,
          ),
        ],
      ),
    );
  }
}

class LoginForm extends StatefulWidget {
  const LoginForm({Key? key}) : super(key: key);

  @override
  State<LoginForm> createState() => _LoginFormState();
}

class _LoginFormState extends State<LoginForm> {
  FormData formData = FormData();

  @override
  Widget build(BuildContext context) {
    return Form(
        child: Column(
      children: [
        TextFormField(
          autofocus: true,
          textInputAction: TextInputAction.next,
          decoration: InputDecoration(
            filled: true,
            hintText: "Ваш Email",
            labelText: "Логин",
          ),
          onChanged: (v) => formData.email = v,
        )
      ],
    ));
  }
}

@JsonSerializable( createFactory: true, createToJson: true)
class FormData {
  String? email, password;

  FormData({this.email, this.password});

  // factory FormData.fromJson(Map<String, dynamic> json) {
  //   return FormData(
  //     email: json['email'] as String,
  //     password: json['password'] as String,
  //   );
  // }
  //
  // Map<String, dynamic> toJson(FormData formData) => {
  //   'email': formData.email,
  //   'password': formData.password,
  // };
}
