// GENERATED CODE - DO NOT MODIFY BY HAND

part of 'video_model.dart';

// **************************************************************************
// JsonSerializableGenerator
// **************************************************************************

VideoModel _$VideoModelFromJson(Map<String, dynamic> json) => VideoModel(
      id: json['id'] as String,
      title: json['title'] as String,
      previewURL: json['previewURL'] as String,
      description: json['description'] as String,
      videoURL: json['videoURL'] as String,
      likes: json['likes'] as int,
      views: json['views'] as int,
      visible: json['visible'] as bool,
      deleted: json['deleted'] as bool,
      allowComments: json['allowComments'] as bool,
      allowRating: json['allowRating'] as bool,
      uploadTS:
          DateTimeConverter.convert(json['uploadTS'] as Map<String, dynamic>),
      comments: (json['comments'] as List<dynamic>?)
          ?.map((e) => CommentModel.fromJson(e as Map<String, dynamic>))
          .toList(),
      channel: ChannelModel.fromJson(json['channel'] as Map<String, dynamic>),
    );

Map<String, dynamic> _$VideoModelToJson(VideoModel instance) =>
    <String, dynamic>{
      'id': instance.id,
      'title': instance.title,
      'previewURL': instance.previewURL,
      'description': instance.description,
      'videoURL': instance.videoURL,
      'likes': instance.likes,
      'views': instance.views,
      'visible': instance.visible,
      'deleted': instance.deleted,
      'allowComments': instance.allowComments,
      'allowRating': instance.allowRating,
      'uploadTS': instance.uploadTS.toIso8601String(),
      'comments': instance.comments?.map((e) => e.toJson()).toList(),
      'channel': instance.channel.toJson(),
    };
