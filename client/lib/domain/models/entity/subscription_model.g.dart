// GENERATED CODE - DO NOT MODIFY BY HAND

part of 'subscription_model.dart';

// **************************************************************************
// JsonSerializableGenerator
// **************************************************************************

SubscriptionModel _$SubscriptionModelFromJson(Map<String, dynamic> json) =>
    SubscriptionModel(
      id: json['id'] as String,
      followedSince: DateTimeConverter.convert(
          json['followedSince'] as Map<String, dynamic>),
      channel: ChannelModel.fromJson(json['channel'] as Map<String, dynamic>),
    );

Map<String, dynamic> _$SubscriptionModelToJson(SubscriptionModel instance) =>
    <String, dynamic>{
      'id': instance.id,
      'followedSince': instance.followedSince.toIso8601String(),
      'channel': instance.channel.toJson(),
    };
