import 'package:cloud_firestore/cloud_firestore.dart';
import 'package:intl/intl.dart';
import 'package:intl/date_symbol_data_local.dart';

class DateTimeSerializer {
  static DateTime serialize(Map<String, dynamic> json) =>
      Timestamp(json['seconds'], json['nanos']).toDate();
}

extension DateTimeExtension on DateTime {
  String format([String pattern = "yMMMEd", String? locale = "ru_RU"]) {
    // if (locale != null && locale.isNotEmpty) {
    //   initializeDateFormatting(locale);
    // }
    return DateFormat(pattern, locale).format(this);
  }

// class DateTimeFormatter {
//   static String format(DateTime dateTime) {
//     initializeDateFormatting('ru');
//     final DateFormat formatter = DateFormat(DateFormat.yMMMEd("ru").pattern);
//     return formatter.format(dateTime);
//   }
      // initializeDateFormatting('ru_RU', null)
      //     .then((_) => DateFormat.yMMMd("ru_RU").format(dateTime));
}
