import 'package:client/domain/models/entity/channel_model.dart';
import 'package:json_annotation/json_annotation.dart';

part 'edit_channel_request.g.dart';

@JsonSerializable(explicitToJson: true)
class EditChannelRequest {
  String name;
  String? about, avatarURL, headerURL;
  bool allowComments = true, allowRating = true;

  EditChannelRequest({
    required this.name,
    required this.about,
    required this.avatarURL,
    required this.headerURL,
    required this.allowComments,
    required this.allowRating,
  });

  EditChannelRequest.fromChannelModel(ChannelModel channel)
      : name = channel.name,
        about = channel.about,
        avatarURL = channel.avatarURL,
        headerURL = channel.headerURL,
        allowComments = channel.allowComments,
        allowRating = channel.allowRating;

  factory EditChannelRequest.fromJson(Map<String, dynamic> json) => _$EditChannelRequestFromJson(json);
  Map<String, dynamic> toJson() => _$EditChannelRequestToJson(this);
}
