class ApiException implements Exception{
  int? errorCode;
  List<String> messages;

  ApiException({this.errorCode, required this.messages});
}