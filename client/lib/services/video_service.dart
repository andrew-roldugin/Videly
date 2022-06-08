import 'dart:developer';
import 'dart:io';

import 'package:client/domain/models/entity/video_model.dart';
import 'package:client/http/api_exception.dart';
import 'package:client/http/custom_http_client.dart';
import 'package:dio/dio.dart';
import 'package:get_it/get_it.dart';

class VideoService{
  late final Dio dioClient;

  VideoService() {
    dioClient = GetIt.instance<CustomHttpClient>().dioInstance;
  }

  Future<VideoModel?> getVideoData({required String videoId}) {
    try {
      return dioClient.get("/video/$videoId").then(
            (res) {
          if (res.statusCode == 200) {
            log("Данные о видео загружены");
            return VideoModel.fromJson(res.data);
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

  Future<int?> likeVideo({required videoId}) async {
    try {
      return dioClient.get("/video/like", queryParameters: {"videoId": videoId}).then(
            (res) {
          if (res.statusCode == 200) {
            log("лайк");
            return res.data;
          }
          return null;
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

}