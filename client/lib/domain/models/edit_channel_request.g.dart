// GENERATED CODE - DO NOT MODIFY BY HAND

part of 'edit_channel_request.dart';

// **************************************************************************
// JsonSerializableGenerator
// **************************************************************************

EditChannelRequest _$EditChannelRequestFromJson(Map<String, dynamic> json) =>
    EditChannelRequest(
      name: json['name'] as String,
      about: json['about'] as String?,
      avatarURL: json['avatarURL'] as String?,
      headerURL: json['headerURL'] as String?,
      allowComments: json['allowComments'] as bool,
      allowRating: json['allowRating'] as bool,
    );

Map<String, dynamic> _$EditChannelRequestToJson(EditChannelRequest instance) =>
    <String, dynamic>{
      'name': instance.name,
      'about': instance.about,
      'avatarURL': instance.avatarURL,
      'headerURL': instance.headerURL,
      'allowComments': instance.allowComments,
      'allowRating': instance.allowRating,
    };
