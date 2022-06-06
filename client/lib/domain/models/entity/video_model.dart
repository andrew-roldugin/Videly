import 'package:client/domain/models/entity/comment_model.dart';
import 'package:client/domain/models/entity/timestamp_converter.dart';
import 'package:cloud_firestore/cloud_firestore.dart';
import 'package:json_annotation/json_annotation.dart';

import 'channel_model.dart';

part 'video_model.g.dart';

@JsonSerializable(explicitToJson: true)
class VideoModel{

  String id, title, previewURL, description, videoURL;
  int likes, views;
  bool visible, deleted, allowComments, allowRating;

  @JsonKey(fromJson: DateTimeConverter.convert)
  DateTime uploadTS;

  // @JsonKey(fromJson: CommentModel.fromJson)
  List<CommentModel>? comments;
  ChannelModel channel;

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
      required this.channel});

  factory VideoModel.fromJson(Map<String, dynamic> json) =>
      _$VideoModelFromJson(json);

  Map<String, dynamic> toJson() => _$VideoModelToJson(this);

}