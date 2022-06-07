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
          Padding(
            padding: const EdgeInsets.symmetric(vertical: 8.0),
            child: Text(
              "Название НазваниеНазваниеНазваниеНазваниеНазваниеНазваниеНазваниеНазваниеНазваниеНазвание",
              maxLines: 3,
            ),
          ),
          Row(
            mainAxisAlignment: MainAxisAlignment.spaceBetween,
            children: [
              Text("просм"),
              Text("дата"),
            ],
          ),
          Row(
            children: [
              Icon(Icons.account_circle),
              SizedBox(
                width: 10.0,
              ),
              Text("Канал ..."),
            ],
          ),
          SizedBox(
            height: 10.0,
          ),
          Stack(children: [
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
                Text("10"),
                MaterialButton(
                  minWidth: 10,
                  onPressed: () {
                    print("object");
                  },
                  child: Icon(
                    Icons.favorite,
                    size: 30,
                  ),
                )
              ],
            )
          ],),
        ],
      ),
    );
  }
}
