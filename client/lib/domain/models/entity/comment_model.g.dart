// GENERATED CODE - DO NOT MODIFY BY HAND

part of 'comment_model.dart';

// **************************************************************************
// JsonSerializableGenerator
// **************************************************************************

CommentModel _$CommentModelFromJson(Map<String, dynamic> json) => CommentModel(
      id: json['id'] as String,
      content: json['content'] as String,
      ts: DateTimeConverter.convert(json['ts'] as Map<String, dynamic>),
      deleted: json['deleted'] as bool,
      channel: json['channelDTO'] == null
          ? null
          : ChannelModel.fromJson(json['channelDTO'] as Map<String, dynamic>),
    );

Map<String, dynamic> _$CommentModelToJson(CommentModel instance) =>
    <String, dynamic>{
      'id': instance.id,
      'content': instance.content,
      'ts': instance.ts.toIso8601String(),
      'channelDTO': instance.channel?.toJson(),
      'deleted': instance.deleted,
    };
