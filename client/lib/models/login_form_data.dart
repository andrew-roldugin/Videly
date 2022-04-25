import 'package:json_annotation/json_annotation.dart';
import 'package:validators/validators.dart' as validator;

part 'login_form_data.g.dart';

@JsonSerializable( createFactory: true, createToJson: true)
class LoginFormData {
  String? email, password;

  LoginFormData({this.email, this.password});

  bool validate(){
    return password != null && password!.length >= 6
        && email != null && validator.isEmail(email!);
  }

  factory LoginFormData.fromJson(Map<String, dynamic> json) => _$LoginFormDataFromJson(json);
  Map<String, dynamic> toJson() => _$LoginFormDataToJson(this);
}

