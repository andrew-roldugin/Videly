// GENERATED CODE - DO NOT MODIFY BY HAND

part of 'register_form_data.dart';

// **************************************************************************
// JsonSerializableGenerator
// **************************************************************************

RegisterFormData _$RegisterFormDataFromJson(Map<String, dynamic> json) =>
    RegisterFormData(
      email: json['email'] as String?,
      nickname: json['nickname'] as String?,
      password: json['password'] as String?,
      confirmPassword: json['confirmPassword'] as String?,
    );

Map<String, dynamic> _$RegisterFormDataToJson(RegisterFormData instance) =>
    <String, dynamic>{
      'email': instance.email,
      'nickname': instance.nickname,
      'password': instance.password,
      'confirmPassword': instance.confirmPassword,
    };
