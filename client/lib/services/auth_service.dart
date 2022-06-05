import 'dart:developer';
import 'dart:io';

import 'package:client/domain/models/login_form_data.dart';
import 'package:client/domain/models/register_form_data.dart';
import 'package:client/http/custom_http_client.dart';
import 'package:firebase_auth/firebase_auth.dart';
import 'package:get_it/get_it.dart';

class AuthService {
  final FirebaseAuth _firebaseAuth;

  AuthService(this._firebaseAuth);

  Future<bool> signIn({required LoginFormData formData}) async {
    try {
      var dio = GetIt.instance<CustomHttpClient>().dioInstance;

      return await dio.post("/auth/login", data: formData.toJson()).then(
        (res) async {
          if (res.statusCode == 200) {
            var token = res.data['tokens']['customToken'];
            return token;
          }
          print(res);
        },
      ).then((token) async {
        await _firebaseAuth.signInWithCustomToken(token);
        return true;
      });
    } on SocketException catch (_) {
      throw const SocketException("Вы не подключены к интеренету");
    } on FormatException catch (_) {
      throw const FormatException("Невозможно обработать данные");
    }
  }

  Future<bool> signUp({required RegisterFormData formData}) async {
    try {
      var dio = GetIt.instance<CustomHttpClient>().dioInstance;

      return await dio.post("/auth/register", data: formData.toJson()).then(
        (res) async {
          if (res.statusCode == 200) {
            var token = res.data['refreshToken'];
            return token;
          }
        },
      ).then((token) async {
        return true;
      });
    } on SocketException catch (_) {
      throw const SocketException("Вы не подключены к интеренету");
    } on FormatException catch (_) {
      throw const FormatException("Невозможно обработать данные");
    }
  }

  Future<void> signOut() async {
    await _firebaseAuth.signOut();
  }

  bool checkAuth() {
    return _firebaseAuth.currentUser != null;
  }

  bool isGuest() {
    return _firebaseAuth.currentUser!.isAnonymous;
  }

  void resetPassword({required String email}) {
    _firebaseAuth
        .sendPasswordResetEmail(email: email)
        .then((value) => log("Письмо отправлено"));
  }


  void signInAnonymously() {
    _firebaseAuth.signInAnonymously();
  }
}
