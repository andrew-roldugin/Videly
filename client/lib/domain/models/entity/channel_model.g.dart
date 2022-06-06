// GENERATED CODE - DO NOT MODIFY BY HAND

part of 'channel_model.dart';

// **************************************************************************
// JsonSerializableGenerator
// **************************************************************************

ChannelModel _$ChannelModelFromJson(Map<String, dynamic> json) => ChannelModel(
      id: json['id'] as String,
      name: json['name'] as String,
      avatarURL: json['avatarURL'] as String,
      headerURL: json['headerURL'] as String,
      about: json['about'] as String,
      createdAt:
          DateTimeConverter.convert(json['createdAt'] as Map<String, dynamic>),
      videos: (json['videos'] as List<dynamic>?)
          ?.map((e) => VideoModel.fromJson(e as Map<String, dynamic>))
          .toList(),
      deleted: json['deleted'] as bool,
      allowComments: json['allowComments'] as bool,
      allowRating: json['allowRating'] as bool,
    );

Map<String, dynamic> _$ChannelModelToJson(ChannelModel instance) =>
    <String, dynamic>{
      'id': instance.id,
      'name': instance.name,
      'avatarURL': instance.avatarURL,
      'headerURL': instance.headerURL,
      'about': instance.about,
      'createdAt': instance.createdAt.toIso8601String(),
      'videos': instance.videos?.map((e) => e.toJson()).toList(),
      'deleted': instance.deleted,
      'allowComments': instance.allowComments,
      'allowRating': instance.allowRating,
    };
