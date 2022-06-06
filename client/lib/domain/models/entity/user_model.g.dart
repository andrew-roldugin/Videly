// GENERATED CODE - DO NOT MODIFY BY HAND

part of 'user_model.dart';

// **************************************************************************
// JsonSerializableGenerator
// **************************************************************************

UserModel _$UserModelFromJson(Map<String, dynamic> json) => UserModel(
      id: json['id'] as String,
      email: json['email'] as String,
      createdAt:
          DateTimeConverter.convert(json['createdAt'] as Map<String, dynamic>),
      accountDeleted: json['accountDeleted'] as bool,
      role: $enumDecode(_$UserRoleEnumMap, json['role']),
      banned: json['banned'] as bool,
      channel: json['channel'] == null
          ? null
          : ChannelModel.fromJson(json['channel'] as Map<String, dynamic>),
    );

Map<String, dynamic> _$UserModelToJson(UserModel instance) => <String, dynamic>{
      'email': instance.email,
      'id': instance.id,
      'createdAt': instance.createdAt.toIso8601String(),
      'banned': instance.banned,
      'accountDeleted': instance.accountDeleted,
      'role': _$UserRoleEnumMap[instance.role],
      'channel': instance.channel?.toJson(),
    };

const _$UserRoleEnumMap = {
  UserRole.USER: 'USER',
  UserRole.MODERATOR: 'MODERATOR',
};
