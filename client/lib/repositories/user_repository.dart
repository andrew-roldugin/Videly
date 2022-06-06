import 'package:client/domain/models/entity/user_model.dart';

class UserRepository {
  late UserModel _user;

  UserModel get user => _user;
  set user(u) => _user = u;
}