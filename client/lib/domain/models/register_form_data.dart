import 'package:json_annotation/json_annotation.dart';

part 'register_form_data.g.dart';

@JsonSerializable()
class RegisterFormData {
  String? email, nickname, password, confirmPassword;

  RegisterFormData({this.email, this.nickname, this.password, this.confirmPassword});

  factory RegisterFormData.fromJson(Map<String, dynamic> json) => _$RegisterFormDataFromJson(json);
  Map<String, dynamic> toJson() => _$RegisterFormDataToJson(this);
}

