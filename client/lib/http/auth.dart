import 'dart:async';
import 'dart:convert';
import 'dart:developer';
import 'dart:ffi';
import 'dart:io';

import 'package:client/domain/models/login_form_data.dart';
import 'package:client/domain/models/register_form_data.dart';
import 'package:client/domain/models/user_model.dart';
import 'package:client/http/custom_http_client.dart';
import 'package:cloud_firestore/cloud_firestore.dart';
import 'package:dio/adapter.dart';
import 'package:dio/dio.dart';
import 'package:firebase_auth/firebase_auth.dart';
import 'package:flutter/cupertino.dart';
import 'package:get_it/get_it.dart';
import 'package:http/io_client.dart';

import '../domain/models/token_response.dart';

class AuthenticationService {
  final FirebaseAuth _firebaseAuth;
  final _userRef = FirebaseFirestore.instance.collection("users");

  AuthenticationService(this._firebaseAuth);

  // managing the user state via stream.
  // stream provides an immediate event of
  // the user's current authentication state,
  // and then provides subsequent events whenever
  // the authentication state changes.
  Stream<User?> get authStateChanges => _firebaseAuth.authStateChanges();

  //1
  Future<bool> signIn({required LoginFormData formData}) async {
    try {
      var dio = GetIt.instance<CustomHttpClient>().dioInstance;

      return await dio.post("/auth/login", data: formData.toJson()).then(
        (res) async {
          if (res.statusCode == 200) {
            var token = res.data['customToken'];
            return token;
          }
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

  // Future<String?> signIn({required LoginFormData formData}) async {
  //   try {
  //     // var _client = HttpClient();
  //     // _client.badCertificateCallback =
  //     //     (X509Certificate cert, String host, int port) => true;
  //     // //_client.idleTimeout = const Duration(seconds: 3);
  //     // final http = IOClient(_client);
  //
  //     // var dio = Dio();
  //     //
  //     // (dio.httpClientAdapter as DefaultHttpClientAdapter).onHttpClientCreate =
  //     //     (client) {
  //     //   client.badCertificateCallback =
  //     //       (X509Certificate cert, String host, int port) => true;
  //     // };
  //
  //     // var jsonBody = jsonEncode(formData.toJson());
  //     // await http.post(
  //     //   Uri.https("45.8.248.185:8443", "/api/auth/login"),
  //     //   body: jsonBody,
  //     //   headers: {"Content-Type": "application/json"},
  //     // )
  //
  //     var dio = GetIt.instance<CustomHttpClient>().dio;
  //
  //     return await dio
  //         .post("/auth/login", data: formData.toJson())
  //         .then((res) async {
  //       print("/auth/login");
  //       if (res.statusCode == 200) {
  //         var atoken = res.data['accessToken'];
  //         print("accessToken: " + atoken);
  //         var token = res.data['refreshToken'];
  //         return token;
  //       }
  //     }).then((token) async {
  //       await _firebaseAuth.signInWithCustomToken(token);
  //       print("Signed In");
  //       return "Signed In";
  //     });
  //     // } on FirebaseAuthException catch (e) {
  //     //   if (e.code == 'user-not-found') {
  //     //     return "No user found for that email.";
  //     //   } else if (e.code == 'wrong-password') {
  //     //     return "Wrong password provided for that user.";
  //     //   }
  //   } on SocketException catch (_) {
  //     throw const SocketException("Вы не подключены к интеренету");
  //   } on FormatException catch (_) {
  //     throw const FormatException("Невозможно обработать данные");
  //   }
  // }

  //2
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

  //3
  Future<void> addUserToDB({
    String? uid,
    required String username,
    required String email,
    DateTime? timestamp,
  }) async {
    final userModel = UserModel(
        uid: uid, username: username, email: email, timestamp: timestamp);

    await _userRef.doc(uid).set(userModel.toJson());
  }

  //4
  // Future<UserModel> getUserFromDB({String uid}) async {
  //   final DocumentSnapshot doc = await userRef.document(uid).get();
  //
  //   //print(doc.data());
  //
  //   return UserModel.fromMap(doc.data());
  // }

  //5
  Future<void> signOut() async {
    await _firebaseAuth.signOut();
  }
}

// import 'dart:convert';
//
// import 'package:flutter/material.dart';
// import 'package:http/http.dart' as http;
// import 'package:videly/models/login_form_data.dart';
//
// class AuthRequests {
//   static http.Client httpClient = http.Client();
//
//   static void signIn(BuildContext context, LoginFormData formData) async {
//     var result = await httpClient.post(
//       Uri.parse('https://example.com/signin'),
//       body: json.encode(formData.toJson()),
//       headers: {'content-type': 'application/json'},
//     );
//
//     if (result.statusCode == 200) {
//       _showDialog(context, 'Successfully signed in.');
//     } else if (result.statusCode == 401) {
//       _showDialog(context, 'Unable to sign in.');
//     } else {
//       _showDialog(context, 'Something went wrong. Please try again.');
//     }
//   }
//
//   static void signUp(BuildContext context, LoginFormData formData) async {}
//
//   static void resetPassword(BuildContext context) async {}
//
//   static void _showDialog(BuildContext context, String message) {
//     showDialog<void>(
//       context: context,
//       builder: (context) => AlertDialog(
//         title: Text(message),
//         actions: [
//           TextButton(
//             child: const Text('OK'),
//             onPressed: () => Navigator.of(context).pop(),
//           ),
//         ],
//       ),
//     );
//   }
// }
