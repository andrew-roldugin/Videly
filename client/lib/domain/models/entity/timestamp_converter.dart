
import 'package:cloud_firestore/cloud_firestore.dart';

class DateTimeConverter{
  static DateTime convert(Map<String, dynamic> json) => Timestamp(json['seconds'], json['nanos']).toDate();
}
