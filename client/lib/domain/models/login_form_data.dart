import 'package:json_annotation/json_annotation.dart';
import 'package:validators/validators.dart' as validator;

part 'login_form_data.g.dart';

@JsonSerializable( createFactory: true, createToJson: true)
class LoginFormData {
  String? login;
  String? password;

  LoginFormData({this.login, this.password});

  bool validate(){
    return password != null && password!.length >= 6
        && login != null && validator.isEmail(login!);
  }

  factory LoginFormData.fromJson(Map<String, dynamic> json) => _$LoginFormDataFromJson(json);
  Map<String, dynamic> toJson() => _$LoginFormDataToJson(this);
}

