import 'dart:convert';

import 'package:client/domain/models/entity/timestamp_converter.dart';
import 'package:client/domain/models/entity/video_model.dart';
import 'package:cloud_firestore/cloud_firestore.dart';
import 'package:json_annotation/json_annotation.dart';

part 'channel_model.g.dart';

@JsonSerializable(explicitToJson: true)
class ChannelModel{
  String id, name, avatarURL, headerURL, about;
  @JsonKey(fromJson: DateTimeConverter.convert)
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