import 'package:client/domain/models/entity/video_model.dart';
import 'package:flutter/material.dart';

class VideoDescriptionWidget extends StatelessWidget {
  final VideoModel? video;

  const VideoDescriptionWidget({Key? key, required this.video})
      : super(key: key);

  @override
  Widget build(BuildContext context) {
    return Padding(
      padding: const EdgeInsets.all(8.0),
      child: Column(
        crossAxisAlignment: CrossAxisAlignment.start,
        children: [
          const Padding(
            padding: EdgeInsets.symmetric(vertical: 8.0),
            child: Text(
              "Первое видео на платформе",
              maxLines: 3,
              style: const TextStyle(
                fontSize: 20.0,
                color: Colors.black,
                fontFamily: "Roboto",
                fontWeight: FontWeight.w700,
                decoration: TextDecoration.none,
              ),
            ),
          ),
          Padding(
            padding: const EdgeInsets.symmetric(vertical: 8.0),
            child: Row(
              mainAxisAlignment: MainAxisAlignment.spaceBetween,
              children: [
                Text(
                  "5 просмотров",
                  style: const TextStyle(
                    fontSize: 16.0,
                    color: Colors.black,
                    fontFamily: "Roboto",
                    fontWeight: FontWeight.w500,
                    decoration: TextDecoration.none,
                  ),
                ),
                Text(
                  "7 июня 2022г.",
                  style: const TextStyle(
                    fontSize: 16.0,
                    color: Colors.black,
                    fontFamily: "Roboto",
                    fontWeight: FontWeight.w500,
                    decoration: TextDecoration.none,
                  ),
                ),
              ],
            ),
          ),
          Padding(
            padding: const EdgeInsets.symmetric(vertical: 8.0),
            child: Row(
              children: [
                Icon(
                  Icons.account_circle,
                  size: 30,
                ),
                SizedBox(
                  width: 10.0,
                ),
                Text(
                  "Тестовый канал",
                  style: const TextStyle(
                    fontSize: 16.0,
                    color: Colors.black,
                    fontFamily: "Roboto",
                    fontWeight: FontWeight.w500,
                    decoration: TextDecoration.none,
                  ),
                ),
              ],
            ),
          ),
          Stack(
            children: [
              MaterialButton(
                minWidth: 10,
                onPressed: () {
                  print("settings");
                },
                child: Column(
                  children: [
                    Text("Настройки"),
                    Icon(
                      Icons.settings,
                      size: 30,
                    ),
                  ],
                ),
              ),
              Row(
                mainAxisAlignment: MainAxisAlignment.end,
                children: [
                  Text("2"),
                  MaterialButton(
                    minWidth: 10,
                    onPressed: () {
                      print("object");
                    },
                    child: Icon(
                      Icons.favorite,
                      size: 30,
                      color: const Color.fromRGBO(120, 65, 178, 1.0),
                    ),
                  )
                ],
              )
            ],
          ),
        ],
      ),
    );
  }
}
