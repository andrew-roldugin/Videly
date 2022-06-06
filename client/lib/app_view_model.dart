import 'package:client/main.dart';
import 'package:client/repositories/channel_repository.dart';
import 'package:client/repositories/user_repository.dart';

class AppViewModel {
  ChannelRepository channelRepository = ChannelRepository();
  UserRepository userRepository = UserRepository();
}