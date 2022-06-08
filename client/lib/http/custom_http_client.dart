import 'dart:developer';
import 'dart:io';

import 'package:dio/adapter.dart';
import 'package:dio/dio.dart';
import 'package:firebase_auth/firebase_auth.dart';
import 'package:http/io_client.dart';

// IOClient createClient() {
//   var _client = HttpClient();
//   _client.badCertificateCallback =
//       (X509Certificate cert, String host, int port) => true;
//   _client.idleTimeout = const Duration(seconds: 3);
//   final http = IOClient(_client);
//   return http;
// }

class CustomHttpClient {
  final _dio = Dio();

  CustomHttpClient() {
    setup();
  }

  Dio get dioInstance => _dio;

  void setup() {
    // (_dio.httpClientAdapter as DefaultHttpClientAdapter).onHttpClientCreate =
    //     (client) {
    //   client.badCertificateCallback =
    //       (X509Certificate cert, String host, int port) => true;
    // };
    _dio.options.contentType = "application/json";
    // _dio.options.baseUrl = "https://45.8.248.185:8443/api";
    // _dio.options.baseUrl = "https://192.168.43.203:8443/api";
    _dio.options.baseUrl = "https://videly-server.herokuapp.com/api";
    _dio.options.responseType = ResponseType.json;

    _dio.interceptors.add(InterceptorsWrapper(onRequest: (RequestOptions options, RequestInterceptorHandler handler) async {
      await FirebaseAuth.instance.currentUser?.getIdToken().then((value) {
        if(value.isNotEmpty){
          final token = "Bearer " + value.toString();
          options.headers[HttpHeaders.authorizationHeader] = token;
          log("В заголовки записан токен");
          log("interceptor: token = $token");
        }
      });
      handler.next(options);
    }));

  }
}
