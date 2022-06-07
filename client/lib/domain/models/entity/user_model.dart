import 'package:client/domain/models/entity/channel_model.dart';
import 'package:client/utils/converters.dart';

import 'package:cloud_firestore/cloud_firestore.dart';
import 'package:json_annotation/json_annotation.dart';

part 'user_model.g.dart';

@JsonEnum()
enum UserRole {USER, MODERATOR}

@JsonSerializable(explicitToJson: true)
class UserModel {
  String email;
  String id;

  @JsonKey(fromJson: DateTimeSerializer.serialize)
  DateTime createdAt;

  bool banned, accountDeleted;
  UserRole role;
  // @JsonKey(fromJson: ChannelModel.fromJson)
  ChannelModel? channel;

  UserModel({
    required this.id,
    required this.email,
    required this.createdAt,
    required this.accountDeleted,
    required this.role,
    required this.banned,
    required this.channel
  });

  factory UserModel.fromJson(Map<String, dynamic> json) =>
      _$UserModelFromJson(json);

  Map<String, dynamic> toJson() => _$UserModelToJson(this);
}
