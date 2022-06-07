import 'package:client/utils/converters.dart';
import 'package:client/domain/models/entity/video_model.dart';
import 'package:json_annotation/json_annotation.dart';

part 'channel_model.g.dart';

@JsonSerializable(explicitToJson: true)
class ChannelModel{
  String? id, avatarURL, headerURL, about;
  String name;
  @JsonKey(fromJson: DateTimeSerializer.serialize)
  DateTime createdAt;

  // @JsonKey(fromJson: VideoModel.fromJson)
  List<VideoModel>? videos;
  bool deleted, allowComments, allowRating;

  ChannelModel({
      required this.id,
      required this.name,
      required this.avatarURL,
      required this.headerURL,
      required this.about,
      required this.createdAt,
      this.videos,
      required this.deleted,
      required this.allowComments,
      required this.allowRating});

  factory ChannelModel.fromJson(Map<String, dynamic> json) =>
      _$ChannelModelFromJson(json);

  Map<String, dynamic> toJson() => _$ChannelModelToJson(this);

}