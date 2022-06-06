// GENERATED CODE - DO NOT MODIFY BY HAND

part of 'history_model.dart';

// **************************************************************************
// JsonSerializableGenerator
// **************************************************************************

HistoryModel _$HistoryModelFromJson(Map<String, dynamic> json) => HistoryModel(
      video: VideoModel.fromJson(json['video'] as Map<String, dynamic>),
      ts: DateTimeConverter.convert(json['ts'] as Map<String, dynamic>),
    );

Map<String, dynamic> _$HistoryModelToJson(HistoryModel instance) =>
    <String, dynamic>{
      'video': instance.video.toJson(),
      'ts': instance.ts.toIso8601String(),
    };
