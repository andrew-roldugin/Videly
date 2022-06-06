import 'dart:developer';
import 'dart:io';

import 'package:client/domain/models/edit_channel_request.dart';
import 'package:client/domain/models/entity/channel_model.dart';
import 'package:client/http/api_exception.dart';
import 'package:client/http/custom_http_client.dart';
import 'package:dio/dio.dart';
import 'package:get_it/get_it.dart';

class ChannelService {
  late final Dio dioClient;

  ChannelService() {
    dioClient = GetIt.instance<CustomHttpClient>().dioInstance;
  }

  Future<String?> editChannel(String id, EditChannelRequest req) {
    try {
      return dioClient.patch("/channel/update/$id", data: req.toJson()).then(
        (res) {
          if (res.statusCode == 200) {
            log("Данные о канале успешно обновлены");
            return res.data['messages'][0] as String;
            // return ChannelModel.fromJson(res.data);
          }
          // throw ApiException(messages: res.data['messages']);
        },
      );
    } on SocketException catch (_) {
      throw const SocketException("Вы не подключены к интеренету");
    } on FormatException catch (_) {
      throw const FormatException("Невозможно обработать данные");
    }
  }
}