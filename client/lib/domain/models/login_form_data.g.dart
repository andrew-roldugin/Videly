// GENERATED CODE - DO NOT MODIFY BY HAND

part of 'login_form_data.dart';

// **************************************************************************
// JsonSerializableGenerator
// **************************************************************************

LoginFormData _$LoginFormDataFromJson(Map<String, dynamic> json) =>
    LoginFormData(
      email: json['email'] as String? ?? "",
      password: json['password'] as String? ?? "",
    );

Map<String, dynamic> _$LoginFormDataToJson(LoginFormData instance) =>
    <String, dynamic>{
      'email': instance.email,
      'password': instance.password,
    };
