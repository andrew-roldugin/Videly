import 'dart:developer';
import 'dart:io';

import 'package:client/domain/models/entity/comment_model.dart';
import 'package:client/http/api_exception.dart';
import 'package:dio/dio.dart';
import 'package:get_it/get_it.dart';

import '../http/custom_http_client.dart';

class CommentService {
  late final Dio dioClient;

  CommentService() {
    dioClient = GetIt.instance<CustomHttpClient>().dioInstance;
  }

  Future<Iterable<CommentModel>?> loadAllCommentsAtVideo({
    required String videoId,
    int limit = 25,
    int offset = 0,
  }) {
    try {
      return dioClient.get("/comment/all", queryParameters: {
        "videoId": videoId,
        "limit": limit,
        "offset": offset
      }).then((res) {
        if (res.statusCode == 200) {
          log("Комментарии под видео $videoId загружены");
          return (res.data as List<dynamic>).map((e) => CommentModel.fromJson(e));
        }
      });
    } on DioError catch (err) {
      throw ApiException(messages: err.response?.data['messages']);
    } on SocketException catch (_) {
      throw const SocketException("Вы не подключены к интеренету");
    } on FormatException catch (_) {
      throw const FormatException("Невозможно обработать данные");
    }
  }
}
