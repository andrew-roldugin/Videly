import 'package:client/utils/converters.dart';
import 'package:client/domain/models/entity/video_model.dart';
import 'package:json_annotation/json_annotation.dart';

part 'history_model.g.dart';

@JsonSerializable(explicitToJson: true)
class HistoryModel {
  // @JsonKey(fromJson: VideoModel.fromJson)
  VideoModel video;

  @JsonKey(fromJson: DateTimeSerializer.serialize)
  DateTime ts;

  HistoryModel({required this.video, required this.ts});
}