import 'package:client/utils/converters.dart';
import 'package:client/utils/converters.dart';
import 'package:json_annotation/json_annotation.dart';

import 'channel_model.dart';

part 'comment_model.g.dart';

@JsonSerializable(explicitToJson: true)
class CommentModel {
  String id, content;
  @JsonKey(fromJson: DateTimeSerializer.serialize)
  DateTime ts;
  // @JsonKey(fromJson: ChannelModel.fromJson)
  @JsonKey(name: "channelDTO")
  ChannelModel? channel;
  bool deleted;

  CommentModel({
    required this.id,
    required this.content,
    required this.ts,
    required this.deleted,
    this.channel
  });

  factory CommentModel.fromJson(Map<String, dynamic> json) =>
      _$CommentModelFromJson(json);

  Map<String, dynamic> toJson() => _$CommentModelToJson(this);
}
