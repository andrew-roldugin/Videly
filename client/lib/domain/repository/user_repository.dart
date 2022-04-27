import 'package:client/domain/models/user_model.dart';
import 'package:flutter/cupertino.dart';

abstract class UserRepository {
  Future<UserModel> getUser({
    @required String id,
  });
}
