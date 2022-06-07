import 'package:client/utils/converters.dart';
import 'package:json_annotation/json_annotation.dart';

import 'channel_model.dart';

part 'subscription_model.g.dart';

@JsonSerializable(explicitToJson: true)
class SubscriptionModel {
  String id;

  @JsonKey(fromJson: DateTimeSerializer.serialize)
  DateTime followedSince;
  // @JsonKey(fromJson: ChannelModel.fromJson)
  ChannelModel channel;

  SubscriptionModel({required this.id, required this.followedSince, required this.channel});
}
