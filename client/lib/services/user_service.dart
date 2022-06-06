import 'dart:developer';
import 'dart:io';

import 'package:client/domain/models/edit_user_request.dart';
import 'package:client/domain/models/entity/user_model.dart';
import 'package:client/http/api_exception.dart';
import 'package:client/http/custom_http_client.dart';
import 'package:cloud_firestore/cloud_firestore.dart';
import 'package:dio/dio.dart';
import 'package:get_it/get_it.dart';

class UserService {
  late final Dio dioClient;

  UserService() {
    dioClient = GetIt.instance<CustomHttpClient>().dioInstance;
  }

  Future<UserModel?> getUserData() {
    try {
      return dioClient.get("/user/").then(
        (res) {
          if (res.statusCode == 200) {
            log("Данные о пользователе загружены");
            return UserModel.fromJson(res.data);
          }
        },
      );
    } on DioError catch (err) {
      throw ApiException(messages: err.response?.data['messages']);
    } on SocketException catch (_) {
      throw const SocketException("Вы не подключены к интеренету");
    } on FormatException catch (_) {
      throw const FormatException("Невозможно обработать данные");
    }
  }

  Future<String?> edit(String userId, EditUserRequest req) {
    try {
      return dioClient.patch("/user/$userId", data: req.toJson()).then(
        (res) {
          if (res.statusCode == 200) {
            log("Данные о пользователе обновлены");
            return res.data['messages'][0];
            //return UserModel.fromJson(res.data);
          }
        },
      );
    } on SocketException catch (_) {
      throw const SocketException("Вы не подключены к интеренету");
    } on FormatException catch (_) {
      throw const FormatException("Невозможно обработать данные");
    }
  }

  Future<String?> deleteAccount(String userId) {
    try {
      return dioClient.delete("/user/$userId").then(
        (res) {
          if (res.statusCode == 200) {
            log("Аккаунт удален");
            return res.data['messages'][0];
          }
          throw ApiException(messages: res.data['messages']);
        },
      );
    } on SocketException catch (_) {
      throw const SocketException("Вы не подключены к интеренету");
    } on FormatException catch (_) {
      throw const FormatException("Невозможно обработать данные");
    }
  }
}
