import 'package:json_annotation/json_annotation.dart';

part 'user_model.g.dart';

@JsonSerializable()
class UserModel{
  String email;
  String? uid;
  String username;
  DateTime? timestamp;

  UserModel({this.uid, required this.email, required this.username, this.timestamp});

  factory UserModel.fromJson(Map<String, dynamic> json) => _$UserModelFromJson(json);
  Map<String, dynamic> toJson() => _$UserModelToJson(this);
}