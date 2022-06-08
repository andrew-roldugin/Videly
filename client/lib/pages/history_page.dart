import 'package:client/domain/models/entity/channel_model.dart';
import 'package:client/domain/models/entity/history_model.dart';
import 'package:client/domain/models/entity/video_model.dart';
import 'package:client/utils/converters.dart';
import 'package:flutter/material.dart';

class HistoryPage extends StatefulWidget {
  const HistoryPage({Key? key}) : super(key: key);

  @override
  State<HistoryPage> createState() => _HistoryPageState();
}

class _HistoryPageState extends State<HistoryPage> {
  @override
  Widget build(BuildContext context) {
    // return SafeArea(
    //   child: Row(
    //     mainAxisAlignment: MainAxisAlignment.center,
    //     children: const [Text("История просмотров")],
    //   ),
    // );
    return SafeArea(
      child: Column(
        children: [
          SizedBox(height: 10),
          Container(
            child: Text(
              'История просмотров',
              textAlign: TextAlign.center,
              style: Theme.of(context).textTheme.headline3,
            ),
          ),
          SizedBox(height: 10),
          Expanded(
            child: _VideoList(),
          ),
        ],
      ),
    );
  }
}

class _VideoList extends StatelessWidget {
  _VideoList({
    Key? key,
  }) : super(key: key);

  final history = <HistoryModel>[
    HistoryModel(
      video: VideoModel(
        id: "id",
        title: "Первое видео на канале",
        previewURL: '',
        description: "Тестовое видео для проверки работы плеера",
        videoURL: "",
        likes: 0,
        views: 0,
        visible: true,
        deleted: false,
        allowComments: true,
        allowRating: true,
        uploadTS: DateTime.now(),
        canEdit: true,
        channel: ChannelModel.ctor(),
      ),
      ts: DateTime.now(),
    ),
    HistoryModel(
      video: VideoModel(
        id: "id",
        title: "Обзор Videly",
        previewURL: '',
        description: "Обзор возможностей приложения Videly",
        videoURL: "",
        likes: 0,
        views: 0,
        visible: true,
        deleted: false,
        allowComments: true,
        allowRating: true,
        uploadTS: DateTime.now(),
        canEdit: true,
        channel: ChannelModel.ctor(),
      ),
      ts: DateTime.now(),
    ),
    HistoryModel(
      video: VideoModel(
        id: "id",
        title: "Виджеты во flutter",
        previewURL: '',
        description: "Обзор виджетов flutter. Stateless и Statefull. Методы. Жизненный цикл Statefull виджетов",
        videoURL: "",
        likes: 0,
        views: 0,
        visible: true,
        deleted: false,
        allowComments: true,
        allowRating: true,
        uploadTS: DateTime.now(),
        canEdit: true,
        channel: ChannelModel.ctor(),
      ),
      ts: DateTime.now(),
    ),
    HistoryModel(
      video: VideoModel(
        id: "id",
        title: "Java Spring. Введение",
        previewURL: '',
        description: "Изучаем Java Spring. Часть 1",
        videoURL: "",
        likes: 0,
        views: 0,
        visible: true,
        deleted: false,
        allowComments: true,
        allowRating: true,
        uploadTS: DateTime.now(),
        canEdit: true,
        channel: ChannelModel.ctor(),
      ),
      ts: DateTime.now(),
    ),
  ];

  @override
  Widget build(BuildContext context) {
    return ListView.builder(
      padding: const EdgeInsets.only(bottom: 50),
      itemCount: history.length,
      itemExtent: 150,
      itemBuilder: (BuildContext context, int index) {
        final item = history[index];
        return Padding(
          padding: const EdgeInsets.symmetric(horizontal: 15, vertical: 5),
          child: Stack(
            children: [
              Container(
                decoration: BoxDecoration(
                  color: Colors.white,
                  border: Border.all(color: Colors.black.withOpacity(0.2)),
                  borderRadius: BorderRadius.all(Radius.circular(10)),
                  boxShadow: [
                    BoxShadow(
                      color: Colors.black.withOpacity(0.1),
                      blurRadius: 8,
                      offset: Offset(0, 2),
                    )
                  ],
                ),
                clipBehavior: Clip.hardEdge,
                child: Row(
                  children: [
                    Padding(
                      padding: const EdgeInsets.symmetric(horizontal: 15.0),
                      child: Image.asset("assets/images/no_video_preview.png",
                          width: 80, height: 80),
                    ),
                    SizedBox(
                      height: 15,
                    ),
                    Expanded(
                        child: Column(
                      crossAxisAlignment: CrossAxisAlignment.start,
                      children: [
                        SizedBox(
                          height: 20,
                        ),
                        Text(
                          item.video.title,
                          style: TextStyle(fontWeight: FontWeight.bold),
                          maxLines: 1,
                          overflow: TextOverflow.ellipsis,
                        ),
                        SizedBox(
                          height: 5,
                        ),
                        Text(
                          item.video.uploadTS.format(),
                          style: TextStyle(color: Colors.grey),
                          maxLines: 1,
                          overflow: TextOverflow.ellipsis,
                        ),
                        SizedBox(
                          height: 20,
                        ),
                        Text(
                          //"Описание тестового видео",
                          item.video.description,
                          maxLines: 2,
                          overflow: TextOverflow.ellipsis,
                        ),
                      ],
                    )),
                    SizedBox(
                      width: 10,
                    ),
                  ],
                ),
              ),
              Material(
                color: Colors.transparent,
                child: InkWell(
                  borderRadius: BorderRadius.all(Radius.circular(10)),
                  onTap: () {
                    print(111);
                  },
                ),
              ),
            ],
          ),
        );
      },
    );
  }
}
