import 'dart:developer';

import 'package:client/models/user_model.dart';
import 'package:cloud_firestore/cloud_firestore.dart';
import 'package:firebase_auth/firebase_auth.dart';

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
  Future<String> signIn(
      {required String email, required String password}) async {
    try {
      await _firebaseAuth.signInWithEmailAndPassword(
          email: email, password: password);

      return "Signed In";
    } on FirebaseAuthException catch (e) {
      if (e.code == 'user-not-found') {
        return "No user found for that email.";
      } else if (e.code == 'wrong-password') {
        return "Wrong password provided for that user.";
      } else {
        return "Something Went Wrong.";
      }
    }
  }

  //2
  Future<String> signUp(
      {required String email, required String password}) async {
    try {
      await _firebaseAuth.createUserWithEmailAndPassword(
          email: email, password: password);
      return "Signed Up";
    } on FirebaseAuthException catch (e) {
      if (e.code == 'weak-password') {
        return "The password provided is too weak.";
      } else if (e.code == 'email-already-in-use') {
        return "The account already exists for that email.";
      }
    } catch (e) {
      log("При регистрации произошла неизвестная ошибка", error: e);
    }
    return "Something Went Wrong.";
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
