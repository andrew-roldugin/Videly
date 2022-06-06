import 'package:json_annotation/json_annotation.dart';

part 'edit_user_request.g.dart';

@JsonSerializable()
class EditUserRequest{
  String password;

  EditUserRequest({required this.password});

  factory EditUserRequest.fromJson(Map<String, dynamic> json) => _$EditUserRequestFromJson(json);
  Map<String, dynamic> toJson() => _$EditUserRequestToJson(this);

}