import 'package:client/domain/models/entity/comment_model.dart';
import 'package:client/utils/converters.dart';
import 'package:json_annotation/json_annotation.dart';

import 'channel_model.dart';

part 'video_model.g.dart';

@JsonSerializable(explicitToJson: true)
class VideoModel{

  late String id, title, previewURL, description, videoURL;
  late int likes, views;
  late bool visible, deleted, allowComments, allowRating;

   @JsonKey(fromJson: DateTimeSerializer.serialize)
  late DateTime uploadTS;

  // @JsonKey(fromJson: CommentModel.fromJson)
  late List<CommentModel>? comments;
  late ChannelModel channel;

  late bool canEdit;

  VideoModel({
      required this.id,
      required this.title,
      required this.previewURL,
      required this.description,
      required this.videoURL,
      required this.likes,
      required this.views,
      required this.visible,
      required this.deleted,
      required this.allowComments,
      required this.allowRating,
      required this.uploadTS,
      this.comments,
      required this.canEdit,
      required this.channel});

  VideoModel.ctor();

  factory VideoModel.fromJson(Map<String, dynamic> json) =>
      _$VideoModelFromJson(json);

  Map<String, dynamic> toJson() => _$VideoModelToJson(this);

}