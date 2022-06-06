import 'package:client/domain/models/entity/timestamp_converter.dart';
import 'package:client/domain/models/entity/video_model.dart';
import 'package:cloud_firestore/cloud_firestore.dart';
import 'package:json_annotation/json_annotation.dart';

part 'history_model.g.dart';

@JsonSerializable(explicitToJson: true)
class HistoryModel {
  // @JsonKey(fromJson: VideoModel.fromJson)
  VideoModel video;

  @JsonKey(fromJson: DateTimeConverter.convert)
  DateTime ts;

  HistoryModel({required this.video, required this.ts});
}